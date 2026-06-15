# Especificação da Feature: simple-state-machine

**Feature Branch**: `[001-simple-state-machine]`

**Criado**: 2026-06-14

**Status**: Rascunho

**Input**: Descrição do usuário: "Evoluir a feature para um modelo declarativo de metadados de máquina de estados, usado para determinar eventos/ações disponíveis para um state e context atuais, sem executar ciclo de vida completo de workflow."

## Cenários de Usuário e Testes *(obrigatório)*

### História de Usuário 1 - Definir Metadados Declarativos Imutáveis (Prioridade: P1)

Como consumidor da biblioteca, quero definir um catálogo declarativo de states, events,
guards e transitions (incluindo InternalTransition), para centralizar regras de disponibilidade de ações.

**Por que esta prioridade**: Sem metadados declarativos e imutáveis, não há base confiável para consultar ações disponíveis por state/context.

**Teste Independente**: Pode ser totalmente testada criando StateMachineDefinition com transitions externas e internas e validando imutabilidade e unicidade de regras.

**Cenários de Aceitação**:

1. **Given** uma definição com states DRAFT, SUBMITTED, APPROVED e REJECTED, e events SUBMIT, APPROVE, REJECT e VIEW_DETAILS, **When** transitions externas e InternalTransition são registradas para esses elementos, **Then** a definição é aceita como válida.
2. **Given** uma regra existente para o mesmo par (sourceState, event) dentro do mesmo tipo de transition, **When** o par é registrado novamente, **Then** a criação da definição falha com erro de regra duplicada.
3. **Given** uma definição concluída, **When** um consumidor tenta alterar states, events, guards ou transitions após a criação, **Then** a definição permanece inalterada.

---

### História de Usuário 2 - Consultar Eventos/Ações Disponíveis (Prioridade: P2)

Como usuário da biblioteca, quero consultar getAvailableEvents(currentState, context) para descobrir quais events/actions estão disponíveis no state atual.

**Por que esta prioridade**: A consulta declarativa de disponibilidade é o principal valor da feature e substitui a execução de ciclo de vida completo.

**Teste Independente**: Pode ser totalmente testada consultando eventos disponíveis para diferentes combinations de state/context e verificando inclusão/exclusão por guard.

**Cenários de Aceitação**:

1. **Given** currentState DRAFT e regra não guardada DRAFT + SUBMIT, **When** getAvailableEvents(DRAFT, context) é chamado, **Then** SUBMIT é retornado como disponível.
2. **Given** currentState SUBMITTED e regra guardada para APPROVE com guard aprovando o GuardContext, **When** getAvailableEvents(SUBMITTED, contextAprovado) é chamado, **Then** APPROVE é retornado como disponível.
3. **Given** currentState SUBMITTED e regra guardada para REJECT com guard negando o GuardContext, **When** getAvailableEvents(SUBMITTED, contextNegado) é chamado, **Then** REJECT não é retornado como disponível.

---

### História de Usuário 3 - Tratar Entradas Inválidas de Consulta (Prioridade: P3)

Como consumidor da biblioteca, quero tratamento explícito para state desconhecido e entradas inválidas nas consultas de disponibilidade.

**Por que esta prioridade**: Falha rápida e explícita reduz erro de integração e evita decisões incorretas de UI/serviço sobre ações disponíveis.

**Teste Independente**: Pode ser totalmente testada com currentState desconhecido e entradas nulas, verificando resposta explícita e determinística.

**Cenários de Aceitação**:

1. **Given** currentState desconhecido pela definição, **When** getAvailableEvents(UNKNOWN, context) é chamado, **Then** a operação retorna resultado explícito de state inválido ou lista vazia com motivo explícito.
2. **Given** uma solicitação de consulta, **When** entradas obrigatórias são nulas, **Then** a operação falha com erro explícito de validação.

---

### Casos de Borda

- O que acontece quando uma regra não tem guard configurado? O event é considerado disponível quando currentState corresponde ao sourceState.
- Como o sistema trata InternalTransition? O event/ação deve poder ser retornado como disponível mesmo sem mudança de state.
- O que acontece quando guard nega a regra? O event não é retornado na lista de disponíveis.
- O que acontece com state desconhecido ou entradas nulas? O sistema retorna tratamento explícito e determinístico.

## Requisitos *(obrigatório)*

### Requisitos Funcionais

- **FR-001**: O sistema DEVE permitir definição explícita de State como conceito de domínio de primeira classe.
- **FR-002**: O sistema DEVE permitir definição explícita de Event como conceito de domínio de primeira classe.
- **FR-003**: O sistema DEVE permitir definição explícita de Transition (externa) ligando sourceState, event e targetState.
- **FR-004**: O sistema DEVE permitir definição explícita de InternalTransition para ações/eventos que não alteram currentState.
- **FR-005**: O sistema DEVE permitir definição explícita de Guard como conceito de domínio que avalia GuardContext.
- **FR-006**: O sistema DEVE suportar criação de StateMachineDefinition imutável após a construção.
- **FR-007**: O sistema DEVE expor operação de consulta getAvailableEvents(currentState, context).
- **FR-008**: O sistema DEVE retornar apenas events/actions cujo sourceState corresponda ao currentState informado.
- **FR-009**: O sistema DEVE incluir no resultado eventos não guardados quando houver match de state.
- **FR-010**: O sistema DEVE incluir no resultado eventos guardados apenas quando o guard aprovar o GuardContext.
- **FR-011**: O sistema DEVE excluir do resultado eventos guardados quando o guard negar o GuardContext.
- **FR-012**: O sistema DEVE tratar explicitamente state desconhecido e entradas nulas com resultado/erro determinístico.
- **FR-013**: O sistema DEVE rejeitar regras duplicadas por combinação inválida de (sourceState, event, transitionType) durante a criação da definição.

### Entidades-Chave *(incluir se a feature envolver dados)*

- **State**: Representa um identificador de etapa de fluxo usado como origem ou destino em transitions.
- **Event**: Representa um gatilho/ação nomeado para consulta de disponibilidade.
- **Transition**: Representa regra declarativa externa de source state + event para target state, com guard opcional.
- **InternalTransition**: Representa regra declarativa de ação/evento sem mudança de state.
- **Guard**: Representa regra que recebe GuardContext e aprova ou nega disponibilidade de event.
- **StateMachineDefinition**: Representa metadados imutáveis contendo states, events e regras de disponibilidade.
- **AvailableEvent**: Representa event/ação retornado como disponível para um currentState e GuardContext.
- **GuardContext**: Representa atributos tipados e somente leitura fornecidos pelo chamador para avaliação de guard.

## Critérios de Sucesso *(obrigatório)*

### Resultados Mensuráveis

- **SC-001**: 100% dos cenários com regras não guardadas retornam os events esperados em getAvailableEvents(currentState, context).
- **SC-002**: 100% dos cenários com guard aprovado retornam os events esperados como disponíveis.
- **SC-003**: 100% dos cenários com guard negado não retornam os events correspondentes.
- **SC-004**: 100% das definições com regras duplicadas inválidas são rejeitadas no momento de criação da definição.
- **SC-005**: 100% dos cenários com state desconhecido ou entradas nulas produzem tratamento explícito e determinístico.

## Premissas

- A primeira versão tem como alvo uso em processo único e não requer coordenação distribuída.
- Consumidores fornecem identificadores estáveis para states e events dentro de uma definição de máquina.
- O schema de GuardContext é definido pelo código consumidor e tratado como tipado e somente leitura pelo núcleo.
- Execução completa de ciclo de vida de workflow (com mutação interna de state da máquina) está fora de escopo desta feature.

## Alinhamento com a Constituição *(obrigatório)*

- **CA-001 Framework-Free**: A feature permanece framework-free e não usa Spring, Lombok, wiring por reflection ou geração de código.
- **CA-002 Domain Visibility**: State, Event, Transition, InternalTransition, Guard, GuardContext e AvailableEvent são entidades explícitas em requisitos, cenários e resultados de aceitação.
- **CA-003 Immutability**: StateMachineDefinition permanece fixo após a criação; qualquer mudança requer nova instância de definição.
- **CA-004 Composition**: O comportamento de Guard é composto ao comportamento de Transition/InternalTransition; nenhum requisito de herança é introduzido por esta feature.
- **CA-005 Automated Tests**: O escopo de testes inclui consulta de events disponíveis, aprovação de guard, negação de guard, regras duplicadas, state desconhecido e entradas nulas.
- **CA-006 Readability**: O escopo da feature favorece nomenclatura explícita e responsabilidades restritas em vez de abstrações genéricas.
