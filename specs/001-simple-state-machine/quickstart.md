# Guia Rápido: Validação simple-state-machine

## Propósito
Validar a feature end-to-end como uma biblioteca Java pura usando cenários obrigatórios de consulta
e validação.

## Pré-requisitos
- Java 21 instalado e ativo
- Maven disponível em PATH

## Configuração
1. Do diretório raiz do repositório, confirme versão Java:
   - `java -version`
2. Execute comando de teste de referência:
   - `mvn -q test`

## Cenários de Validação

### Cenário 1: Consulta de eventos disponíveis em fluxo binário
- Construir definição de máquina contendo:
  - States: DRAFT, SUBMITTED, APPROVED, REJECTED
  - Events: SUBMIT, APPROVE, REJECT, VIEW_DETAILS
  - Transitions externas:
    - DRAFT + SUBMIT -> SUBMITTED
    - SUBMITTED + APPROVE -> APPROVED
    - SUBMITTED + REJECT -> REJECTED
  - InternalTransitions:
    - SUBMITTED + VIEW_DETAILS (sem mudança de state)
- Consultar `getAvailableEvents(DRAFT, context)`.
- Consultar `getAvailableEvents(SUBMITTED, context)`.
- Resultado esperado:
  - Para DRAFT: [AvailableEvent(SUBMIT, ...)]
  - Para SUBMITTED: [AvailableEvent(APPROVE, ...), AvailableEvent(REJECT, ...), AvailableEvent(VIEW_DETAILS, ...)]

### Cenário 2: Aprovação e negação de guard
- Configurar transitions guardadas com regra baseada em context.
- Chamar `getAvailableEvents(state, contextAprovado)`.
- Chamar `getAvailableEvents(state, contextNegado)`.
- Resultado esperado:
  - Context aprovado inclui o event retornado como disponível.
  - Context negado exclui o event da lista disponível.

### Cenário 3: Tratamento de state desconhecido
- Chamar `getAvailableEvents(UNKNOWN_STATE, context)`.
- Resultado esperado:
  - Resultado explícito de state inválido ou lista vazia com motivo explícito.

### Cenário 4: Detecção de transition/internalTransition duplicada
- Tentar criar definição com par duplicado `(sourceState, event, transitionType)`.
- Resultado esperado:
  - Criação de definição falha deterministicamente.

### Cenário 5: Validação de entrada nula
- Tentar operações de definição/consulta com entradas nulas obrigatórias.
- Resultado esperado:
  - Erros de validação explícitos.

## Comandos de Teste Recomendados
- Todos os testes:
  - `mvn test`
- Classe de teste simples exemplo:
  - `mvn -Dtest=StateMachineQueryContractTest test`

## Evidência Esperada
- Todos os cenários da matriz de comportamento obrigatório passam.
- Nenhum teste depende de Spring, Lombok, reflection ou geração de código.
- Conceitos de domínio permanecem explícitos e visíveis em nomes de teste e assertivas.

## Artefatos Relacionados
- Spec: [spec.md](./spec.md)
- Plano: [plan.md](./plan.md)
- Pesquisa: [research.md](./research.md)
- Modelo de dados: [data-model.md](./data-model.md)
- Contrato: [contracts/library-contract.md](./contracts/library-contract.md)
