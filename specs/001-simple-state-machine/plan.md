# Plano de Implementação: simple-state-machine

**Branch**: `[main]` | **Data**: 2026-06-14 | **Spec**: [spec.md](./spec.md)

**Input**: Especificação de feature de `/specs/001-simple-state-machine/spec.md`

## Resumo

Construir uma biblioteca Java 21 framework-free que modele uma máquina de estados
com modelo declarativo de disponibilidade de eventos/ações. A consulta
`getAvailableEvents(currentState, context)` retorna o conjunto de AvailableEvent
filtrado por guard. Configuração imutável em definição, sem execução de ciclo de
workflow. Validação e segurança de comportamento impostas por testes automatizados
JUnit 5 mandatoriais.

## Contexto Técnico

**Linguagem/Versão**: Java 21

**Dependências Principais**: Maven + JUnit 5 (teste), AssertJ (assertivas de teste opcionais), Mockito (apenas se estritamente necessário)

**Armazenamento**: N/A

**Testes**: JUnit 5 obrigatório; AssertJ permitido; Mockito apenas se necessário

**Plataforma-alvo**: JVM (Java 21), desenvolvimento local e execução em CI

**Tipo de Projeto**: Biblioteca (Java puro, framework-free)

**Objetivos de Performance**: Avaliação de disponibilidade determinística; média sub-milissegundo em cenários de teste unitário

**Restrições**: Sem Spring, sem Lombok, sem reflection, sem geração de código, definição de máquina imutável, conceitos de domínio explícitos, nomenclatura descritiva, métodos pequenos

**Escala/Escopo**: Pequena biblioteca POC com pacote de domínio central único e suite de testes focada

## Verificação da Constituição

*GATE: Deve passar antes da Fase 0 pesquisa. Reverificar após design Fase 1.*

- Verificação framework-free Java: sem Spring, Lombok, wiring por reflection,
  ou geração de código em abordagem planejada.
- Verificação do modelo de domínio: State, Event, Transition, Guard, InternalTransition e AvailableEvent são conceitos
  explícitos de primeira classe no design.
- Verificação de imutabilidade: definição de máquina é imutável após construção.
- Verificação de composição: abordagem composition-first; qualquer herança é justificada no Complexity Tracking.
- Verificação de testes: plano de teste JUnit 5 cobre consulta de eventos, aprovação de guard, negação de guard, regras duplicadas, state desconhecido e entradas nulas.
- Verificação de legibilidade: nomenclatura e estrutura de módulos evitam Utils/Helpers/Managers/Processors genéricos.
- Verificação de disciplina de agente: plano referencia rastreabilidade spec-plan-tasks e inclui expectativas de atualização de doc para mudanças de comportamento.

### Avaliação do Gate Pré-Fase 0

- Verificação framework-free Java: PASSAR. Design restrito à biblioteca padrão Java e dependências de teste Maven.
- Verificação do modelo de domínio: PASSAR. Plano define classes/interfaces explícitas para State,
  Event, Transition, Guard, InternalTransition, AvailableEvent, GuardContext, StateMachineDefinition e separação de consulta.
- Verificação de imutabilidade: PASSAR. Objetos de definição são objetos de valor imutáveis com validação em tempo de construção.
- Verificação de composição: PASSAR. Comportamento de guard e avaliação de disponibilidade são compostos;
  herança não é obrigatória.
- Verificação de testes: PASSAR. Estratégia de teste inclui explicitamente todos os cenários obrigatórios.
- Verificação de legibilidade: PASSAR. Nomenclatura e layout de pacotes evitam classes de utilitário genérico.
- Verificação de disciplina de agente: PASSAR. Artefatos incluem contracts, modelo de dados, quickstart e verificações rastreavéis.

## Estrutura do Projeto

### Documentação (esta feature)

```text
specs/001-simple-state-machine/
├── plan.md              # Este arquivo (saída do comando /speckit.plan)
├── research.md          # Saída da Fase 0 (comando /speckit.plan)
├── data-model.md        # Saída da Fase 1 (comando /speckit.plan)
├── quickstart.md        # Saída da Fase 1 (comando /speckit.plan)
├── contracts/           # Saída da Fase 1 (comando /speckit.plan)
└── tasks.md             # Saída da Fase 2 (comando /speckit.tasks - NÃO criada por /speckit.plan)
```

### Código-Fonte (raíz do repositório)

```text
pom.xml
src/
└── main/
  └── java/
    └── br/
      └── com/
        └── danilo/
          └── customstatemachine/
            ├── model/
            │   ├── State.java
            │   ├── Event.java
            │   ├── Transition.java
            │   ├── InternalTransition.java
            │   ├── Guard.java
            │   ├── GuardContext.java
            │   ├── AvailableEvent.java
            │   └── StateMachineDefinition.java
            ├── query/
            │   ├── StateMachine.java
            │   └── AvailableEventResult.java
            └── validation/
              └── DefinitionValidator.java

src/
└── test/
  └── java/
    └── br/
      └── com/
        └── danilo/
          └── customstatemachine/
            ├── contract/
            │   └── StateMachineQueryContractTest.java
            ├── unit/
            │   ├── DefinitionValidationTest.java
            │   ├── GuardEvaluationTest.java
            │   └── NullInputValidationTest.java
            └── integration/
              └── AvailableEventQueryIntegrationTest.java
```

**Decisão de Estrutura**: Projeto de biblioteca Maven único selecionado. Esta estrutura mantém
conceitos de domínio explícitos e isolados enquanto preserva um layout de pacote pequeno e legível apropriado para um POC.

## Rastreamento de Complexidade

Nenhuma violação da constituição requer justificação de complexidade para este plano.

### Reverificação da Constituição Pós-Fase 1

- Verificação framework-free Java: PASSAR. Artefatos de pesquisa e design especificam nenhuma dependência de tempo de execução de framework.
- Verificação do modelo de domínio: PASSAR. Modelo de dados e contracts mantêm conceitos de domínio de primeira classe e explícitos.
- Verificação de imutabilidade: PASSAR. StateMachineDefinition e objetos de valor relacionados permanecem imutáveis após criação.
- Verificação de composição: PASSAR. Guard e comportamento de avaliação de disponibilidade usam composição; nenhuma dependência de herança introduzida.
- Verificação de testes: PASSAR. Quickstart e contracts definem cenários obrigatórios de JUnit 5.
- Verificação de legibilidade: PASSAR. Nomenclatura de artefato é descritiva e evita abstrações genéricas de auxiliar.
- Verificação de disciplina de agente: PASSAR. Alinhamento spec-plan-research-contract-data model quickstart é mantido.
