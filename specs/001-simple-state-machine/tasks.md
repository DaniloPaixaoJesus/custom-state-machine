# Tarefas: simple-state-machine

**Input**: Documentos de design de `/specs/001-simple-state-machine/`

**Pré-requisitos**: plan.md (obrigatório), spec.md (obrigatório), research.md, data-model.md, contracts/, quickstart.md

**Testes**: Testes são obrigatórios para esta feature por especificação e constituição.

**Organização**: As tarefas são agrupadas por história de usuário para permitir implementação independente e teste de cada história.

## Fase 1: Setup (Infraestrutura Compartilhada)

**Propósito**: Inicializar estrutura do projeto Maven e configuração de build/teste de referência.

- [ ] T001 Criar descritor de projeto Maven com configurações Java 21 em pom.xml
- [ ] T002 Criar diretórios de pacotes src e test em src/main/java/br/com/danilo/customstatemachine/ e src/test/java/br/com/danilo/customstatemachine/
- [ ] T003 [P] Configurar JUnit 5, AssertJ (uso opcional) e plugin Surefire em pom.xml
- [ ] T004 [P] Adicionar entradas .gitignore do projeto para saídas de build Maven em .gitignore
- [ ] T005 Adicionar seção de uso de feature e execução de teste local em README.md

---

## Fase 2: Fundacional (Pré-requisitos Bloqueadores)

**Propósito**: Implementar infraestrutura compartilhada exigida por todas as histórias de usuário.

**CRÍTICO**: Nenhuma implementação de história de usuário começa antes desta fase estar completa.

- [ ] T006 Criar contrato de status/resultado de consulta em src/main/java/br/com/danilo/customstatemachine/query/AvailableEventResult.java
- [ ] T007 Criar esqueléto de validador de definição para invariantes compartilhados em src/main/java/br/com/danilo/customstatemachine/validation/DefinitionValidator.java
- [ ] T008 [P] Criar abstrações de guard e context em src/main/java/br/com/danilo/customstatemachine/model/Guard.java e src/main/java/br/com/danilo/customstatemachine/model/GuardContext.java
- [ ] T009 Criar fixtures de teste reutilizáveis para states/events canônicos em src/test/java/br/com/danilo/customstatemachine/support/StateMachineFixtures.java
- [ ] T010 Estabelecer shell de classe de teste de contrato em src/test/java/br/com/danilo/customstatemachine/contract/StateMachineQueryContractTest.java

**Checkpoint**: Fundos de runtime e teste compartilhados estão prontos.

---

## Fase 3: História de Usuário 1 - Definir Metadados Declarativos Imutáveis (Prioridade: P1) MVP

**Objetivo**: Fornecer definição de máquina explícita e imutável com detecção de regras duplicadas.

**Teste Independente**: Construir definição de máquina para DRAFT/SUBMITTED/APPROVED/REJECTED e verificar imutabilidade mais rejeição de regra duplicada.

### Testes para História de Usuário 1

- [ ] T011 [P] [US1] Adicionar testes de definição imutável e validação de estado inicial em src/test/java/br/com/danilo/customstatemachine/unit/DefinitionValidationTest.java
- [ ] T012 [P] [US1] Adicionar testes de detecção de regra duplicada em src/test/java/br/com/danilo/customstatemachine/unit/DuplicateRuleDetectionTest.java
- [ ] T013 [P] [US1] Adicionar testes de contrato para conceitos de domínio explícitos em src/test/java/br/com/danilo/customstatemachine/contract/StateMachineQueryContractTest.java

### Implementação para História de Usuário 1

- [ ] T014 [P] [US1] Implementar objeto de valor imutável State em src/main/java/br/com/danilo/customstatemachine/model/State.java
- [ ] T015 [P] [US1] Implementar objeto de valor imutável Event em src/main/java/br/com/danilo/customstatemachine/model/Event.java
- [ ] T016 [P] [US1] Implementar Transition imutável com Guard opcional em src/main/java/br/com/danilo/customstatemachine/model/Transition.java
- [ ] T017 [P] [US1] Implementar InternalTransition imutável em src/main/java/br/com/danilo/customstatemachine/model/InternalTransition.java
- [ ] T018 [US1] Implementar construtor e getters de StateMachineDefinition imutável em src/main/java/br/com/danilo/customstatemachine/model/StateMachineDefinition.java
- [ ] T019 [US1] Implementar regras de duplicata e pertencimento em src/main/java/br/com/danilo/customstatemachine/validation/DefinitionValidator.java

**Checkpoint**: Definição de máquina é explícita, imutável e validada.

---

## Fase 4: História de Usuário 2 - Consultar Eventos Disponíveis (Prioridade: P2)

**Objetivo**: Executar consulta declarativa de disponibilidade de eventos com comportamento ciente de guard.

**Teste Independente**: Chamar getAvailableEvents(DRAFT, context), getAvailableEvents(SUBMITTED, context) e verificar disponibilidade correta com lógica de guard.

### Testes para História de Usuário 2

- [ ] T020 [P] [US2] Adicionar teste de fluxo de integração feliz em src/test/java/br/com/danilo/customstatemachine/integration/AvailableEventQueryIntegrationTest.java
- [ ] T021 [P] [US2] Adicionar testes de unit de aprovação e negação de guard em src/test/java/br/com/danilo/customstatemachine/unit/GuardEvaluationTest.java
- [ ] T022 [P] [US2] Adicionar testes de unit de internalTransition em src/test/java/br/com/danilo/customstatemachine/unit/InternalTransitionQueryTest.java

### Implementação para História de Usuário 2

- [ ] T023 [US2] Implementar serviço de consulta StateMachine em src/main/java/br/com/danilo/customstatemachine/query/StateMachine.java
- [ ] T024 [US2] Implementar método getAvailableEvents com filtro de rules por sourceState em src/main/java/br/com/danilo/customstatemachine/query/StateMachine.java
- [ ] T025 [US2] Implementar avaliação de guard e criação de AvailableEvent em src/main/java/br/com/danilo/customstatemachine/query/StateMachine.java
- [ ] T026 [US2] Implementar AvailableEvent como resultado de consulta em src/main/java/br/com/danilo/customstatemachine/model/AvailableEvent.java
- [ ] T027 [US2] Finalizar mapeamento de resultado para sucesso, estado desconhecido e validação em src/main/java/br/com/danilo/customstatemachine/query/AvailableEventResult.java

**Checkpoint**: Comportamento de consulta declarativa e lógica de guard são determinísticos e cobertos por teste.

---

## Fase 5: História de Usuário 3 - Falhar Rápido em Uso Inválido (Prioridade: P3)

**Objetivo**: Fornecer validação explícita nula e comportamento de falha.

**Teste Independente**: Tentar entradas nulas e state desconhecido; verificar falhas explícitas.

### Testes para História de Usuário 3

- [ ] T028 [P] [US3] Adicionar testes de validação de entrada nula para definição e APIs de consulta em src/test/java/br/com/danilo/customstatemachine/unit/NullInputValidationTest.java
- [ ] T029 [P] [US3] Adicionar testes de integração de caminho de falha em src/test/java/br/com/danilo/customstatemachine/integration/FailurePathIntegrationTest.java
- [ ] T030 [P] [US3] Adicionar assertivas de contrato de validação em src/test/java/br/com/danilo/customstatemachine/contract/StateMachineQueryContractTest.java

### Implementação para História de Usuário 3

- [ ] T031 [US3] Implementar validações nulas em nível de definição com mensagens explícitas em src/main/java/br/com/danilo/customstatemachine/model/StateMachineDefinition.java
- [ ] T032 [US3] Implementar validações nulas de tempo de consulta para entradas currentState/context em src/main/java/br/com/danilo/customstatemachine/query/StateMachine.java
- [ ] T033 [US3] Implementar tratamento de state desconhecido com resultado explícito em src/main/java/br/com/danilo/customstatemachine/query/AvailableEventResult.java

**Checkpoint**: Uso inválido é rejeitado explicitamente e com segurança.

---

## Fase 6: Polish & Preocupações Trans-Cortes

**Propósito**: Consistência final, alinhamento de documentação e verificação end-to-end.

- [ ] T034 [P] Documentar exemplos de uso final e matriz de comportamento em README.md
- [ ] T035 [P] Alinhar etapas de execução/verificação com comportamento implementado em specs/001-simple-state-machine/quickstart.md
- [ ] T036 [P] Atualizar notas de implementação se o comportamento mudou durante a construção em specs/001-simple-state-machine/plan.md e specs/001-simple-state-machine/research.md
- [ ] T037 Executar suite de teste completa e capturar evidência de conclusão em specs/001-simple-state-machine/checklists/requirements.md

---

## Dependências & Ordem de Execução

### Dependências de Fase

- Fase 1 (Setup): sem dependências.
- Fase 2 (Fundacional): depende da Fase 1 e bloqueia todas as histórias de usuário.
- Fase 3 (US1): depende da Fase 2.
- Fase 4 (US2): depende da Fase 2 e reutiliza componentes de definição US1.
- Fase 5 (US3): depende da Fase 2 e estende comportamento de falha de caminhos de consulta US2.
- Fase 6 (Polish): depende de conclusão de histórias de usuário desejadas.

### Dependências de História de Usuário

- US1 (P1): primeiro entregavel e linha de base MVP.
- US2 (P2): depende de artefatos de definição de domínio US1.
- US3 (P3): depende de surface de consulta US2.

### Dentro de Cada História de Usuário

- Testes são escritos antes ou ao lado de implementação e devem falhar antes da conclusão do código.
- Objetos de valor e contratos antes de comportamento de orquestração.
- Comportamento de consulta antes de atualizações finais de documentação.

## Oportunidades Paralelas

- T003 e T004 podem rodar em paralelo durante setup.
- T008 pode rodar em paralelo com T007 em fase fundacional.
- Tarefas US1 T011/T012/T013 e T014/T015/T016/T017 podem rodar em paralelo por arquivo.
- Testes US2 T020/T021/T022 podem rodar em paralelo.
- Testes US3 T028/T029/T030 podem rodar em paralelo.
- Tarefas Polish T034/T035/T036 podem rodar em paralelo.

## Exemplo Paralelo: História de Usuário 1

```bash
# Autoria de teste paralela
Task: "T011 [US1] Testes de validação de definição em src/test/java/br/com/danilo/customstatemachine/unit/DefinitionValidationTest.java"
Task: "T012 [US1] Testes de detecção de regra duplicada em src/test/java/br/com/danilo/customstatemachine/unit/DuplicateRuleDetectionTest.java"
Task: "T013 [US1] Testes de contrato em src/test/java/br/com/danilo/customstatemachine/contract/StateMachineQueryContractTest.java"

# Implementação de modelo paralela
Task: "T014 [US1] Objeto de valor State em src/main/java/br/com/danilo/customstatemachine/model/State.java"
Task: "T015 [US1] Objeto de valor Event em src/main/java/br/com/danilo/customstatemachine/model/Event.java"
Task: "T016 [US1] Objeto de valor Transition em src/main/java/br/com/danilo/customstatemachine/model/Transition.java"
Task: "T017 [US1] Objeto de valor InternalTransition em src/main/java/br/com/danilo/customstatemachine/model/InternalTransition.java"
```

## Exemplo Paralelo: História de Usuário 2

```bash
Task: "T020 [US2] Teste de integração de fluxo de consulta em src/test/java/br/com/danilo/customstatemachine/integration/AvailableEventQueryIntegrationTest.java"
Task: "T021 [US2] Testes de avaliação de guard em src/test/java/br/com/danilo/customstatemachine/unit/GuardEvaluationTest.java"
Task: "T022 [US2] Testes de consulta de internalTransition em src/test/java/br/com/danilo/customstatemachine/unit/InternalTransitionQueryTest.java"
```

## Exemplo Paralelo: História de Usuário 3

```bash
Task: "T028 [US3] Testes de validação de entrada nula em src/test/java/br/com/danilo/customstatemachine/unit/NullInputValidationTest.java"
Task: "T029 [US3] Testes de integração de caminho de falha em src/test/java/br/com/danilo/customstatemachine/integration/FailurePathIntegrationTest.java"
Task: "T030 [US3] Assertivas de contrato de validação em src/test/java/br/com/danilo/customstatemachine/contract/StateMachineQueryContractTest.java"
```

## Estratégia de Implementação

### MVP Primeiro (Apenas História de Usuário 1)

1. Completar Fase 1 e Fase 2.
2. Entregar US1 em Fase 3.
3. Validar imutabilidade e detecção de duplicata como aceitação MVP.

### Entrega Incremental

1. Entregar US1 (correção de definição e imutabilidade).
2. Entregar US2 (consulta de disponibilidade e lógica de guard).
3. Entregar US3 (validação nula e garantías de caminho de falha).
4. Finalizar com Fase 6 documentação e verificação de suite completa.

### Alinhamento de Constituição Durante Execução

1. Manter conceitos de modelo explícitos em nomenclatura de pacote/classe.
2. Rejeitar adições de framework ou geração de código.
3. Preservar comportamento de definição imutável através de refatorações.
4. Manter documentação atualizada quando o comportamento muda.
