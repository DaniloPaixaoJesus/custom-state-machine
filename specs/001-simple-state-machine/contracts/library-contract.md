# Contrato de Biblioteca: simple-state-machine

## Escopo de Contrato Público
Este contrato descreve comportamento esperado para uma biblioteca declarativa de máquina de estados
Java pura. É intencionalmente framework-free e focada em comportamento de domínio determinístico
de consulta de disponibilidade de eventos.

## Conceitos Obrigatórios
O contrato de biblioteca DEVE expor estes conceitos explícitos de primeira classe:
- `State`
- `Event`
- `Transition`
- `InternalTransition`
- `Guard`
- `GuardContext`
- `AvailableEvent`
- `StateMachineDefinition`
- `StateMachine` (serviço de consulta)

## Contrato de Construção de Definição
- Consumidores podem construir um `StateMachineDefinition` a partir de states, events,
  transitions externas, internalTransitions, guards e seus relacionamentos.
- Criação de definição falha quando:
  - Uma entrada obrigatória é nula.
  - Qualquer transition/internalTransition referencia state/event não declarado.
  - Pares duplicados `(sourceState, event, transitionType)` são fornecidos.
- Após criação, definição é imutável.

## Contrato de Consulta de Disponibilidade
- `StateMachine.getAvailableEvents(currentState, guardContext)` retorna Collection<AvailableEvent>.
- Retorna apenas `AvailableEvent` cuja transition.sourceState corresponde ao currentState fornecido.
- Para transitions não guardadas: event é sempre incluído quando state corresponde.
- Para transitions guardadas: event é incluído apenas se guard aprova o guardContext.
- Para internalTransitions não guardadas: event é sempre incluído.
- Para internalTransitions guardadas: event é incluído apenas se guard aprova o guardContext.
- Se currentState é desconhecido ou nulo: retorna lista vazia ou resultado explícito de invalid state.
- Se guardContext é nulo: tratamento explícito conforme contrato de implementação.

## Contrato de Guard
- Guard recebe `GuardContext` tipado e retorna decisão de aprovação/negação.
- Lógica de guard não deve mutar definição de máquina.
- Lógica de guard não deve tentar acessar/mutar state actual.
- Guard é opcional no tempo de definição de transition/internalTransition.

## Matriz de Comportamento Testável Obrigatório
- Consulta de eventos não guardados
- Aprovação de guard em consulta
- Negação de guard em consulta
- Detecção de transition duplicada
- Internalransition em consulta de disponibilidade
- State desconhecido/nulo em consulta
- Validação de entrada nula

## Não-Objetivos (Fora de Escopo)
- Endpoints de web ou contratos de API HTTP
- Persistência ou integração de armazenamento
- Integração de framework (Spring, Jakarta EE, etc.)
- Execução de ciclo de workflow com mutação de state interna
