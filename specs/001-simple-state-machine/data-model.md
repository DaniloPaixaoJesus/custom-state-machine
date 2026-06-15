# Modelo de Dados: simple-state-machine

## Entidade: State
- Propósito: Representa um state nomeado de fluxo de trabalho.
- Campos:
  - `name` (String, obrigatório, não vazio)
- Restrições:
  - Igualdade por valor (`name`).
  - Objeto de valor imutável.

## Entidade: Event
- Propósito: Representa um gatilho nomeado para consultas de disponibilidade.
- Campos:
  - `name` (String, obrigatório, não vazio)
- Restrições:
  - Igualdade por valor (`name`).
  - Objeto de valor imutável.

## Entidade: GuardContext
- Propósito: Carrega atributos tipados fornecidos pelo chamador para avaliação de guard.
- Campos:
  - `attributes` (Map<String, ?>, somente leitura, opcional)
- Restrições:
  - Deve tolerar contexto vazio.
  - Manipulação de contexto nulo é explícita e validada por contrato de API.
  - Sem acesso a mudança de state.

## Entidade: Guard
- Propósito: Avalia se um event pode ser retornado como disponível para um contexto fornecido.
- Campos:
  - `name` (String, opcional, descritivo)
  - `predicate` (contrato de função: GuardContext -> boolean)
- Restrições:
  - Conceito explícito no modelo de domínio.
  - Uso baseado em composição dentro de Transition/InternalTransition.

## Entidade: Transition
- Propósito: Define uma regra declarativa de move potencial de source state para target state em um event.
- Campos:
  - `sourceState` (State, obrigatório)
  - `event` (Event, obrigatório)
  - `targetState` (State, obrigatório)
  - `guard` (Guard, opcional)
- Restrições:
  - Pair `(sourceState, event, EXTERNAL_TYPE)` deve ser único em uma definição.
  - Objeto de valor imutável.

## Entidade: InternalTransition
- Propósito: Define uma regra declarativa de ação/event sem mudança de state.
- Campos:
  - `sourceState` (State, obrigatório)
  - `event` (Event, obrigatório)
  - `guard` (Guard, opcional)
- Restrições:
  - Pair `(sourceState, event, INTERNAL_TYPE)` deve ser único em uma definição.
  - Objeto de valor imutável.

## Entidade: AvailableEvent
- Propósito: Representa um event/ação retornado como disponível para um currentState e GuardContext.
- Campos:
  - `event` (Event, obrigatório)
  - `sourceState` (State, obrigatório)
  - `isGuarded` (boolean, indica se guard foi aplicado e aprovou)
  - `transitionType` (enum: EXTERNAL ou INTERNAL)
- Restrições:
  - Resultado imutável de uma consulta.
  - Preserva informação de origem para rastreamento.

## Entidade: StateMachineDefinition
- Propósito: Metadados imutáveis de configuração para um tipo de máquina de estados.
- Campos:
  - `states` (Set<State>, obrigatório)
  - `events` (Set<Event>, obrigatório)
  - `transitions` (Set<Transition>, obrigatório)
  - `internalTransitions` (Set<InternalTransition>, obrigatório)
  - `guards` (Set<Guard>, obrigatório)
- Restrições:
  - Sem mutação após a construção.
  - Todo state/event/guard referenciado em transitions/internalTransitions deve ser declarado.
  - Pares duplicados `(sourceState, event, transitionType)` são rejeitados.

## Entidade: StateMachine
- Propósito: Serviço de consulta sobre um StateMachineDefinition imutável.
- Campos:
  - `definition` (StateMachineDefinition, obrigatório)
- Métodos Principais:
  - `getAvailableEvents(currentState, guardContext)` -> Collection<AvailableEvent>
- Restrições:
  - Sem estado interno mutável.
  - Valida entradas nulas explicitamente.
  - Sem execução de ciclo de workflow.

## Relacionamentos
- `StateMachineDefinition` contém muitos `State`, `Event`, `Transition`, `InternalTransition`.
- `Transition` referencia um source `State`, um `Event`, um target `State` e `Guard` opcional.
- `InternalTransition` referencia um source `State`, um `Event` e `Guard` opcional.
- `StateMachine` depende de um `StateMachineDefinition` imutável.
- `StateMachine.getAvailableEvents(...)` produz uma Collection<AvailableEvent> por chamada.

## Fluxo de Estados (exemplo)
- `DRAFT + SUBMIT -> SUBMITTED` (Transition)
- `SUBMITTED + APPROVE -> APPROVED` (Transition)
- `SUBMITTED + REJECT -> REJECTED` (Transition)
- `SUBMITTED + VIEW_DETAILS` (InternalTransition, sem mudança de state)
