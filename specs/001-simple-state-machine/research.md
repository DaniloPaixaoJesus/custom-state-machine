# Pesquisa: simple-state-machine

## Decisão 1: Núcleo Java 21 puro sem framework de execução
- Decisão: Implementar o núcleo da máquina de estados usando apenas Java 21 e construções de biblioteca padrão.
- Fundamentação: Satisfaz diretamente restrições de constituição (sem Spring, sem Lombok, sem reflection, sem geração de código) e mantém comportamento explícito.
- Alternativas consideradas: Composição estilo Spring state machine (rejeitada: viola regra framework-free); modelo orientado por anotações (rejeitada: incentiva reflection/geração de código).

## Decisão 2: Definição imutável, modelo declarativo de consulta
- Decisão: Manter `StateMachineDefinition` imutável. Expor API de consulta `getAvailableEvents(currentState, context)` sem mutação interna de state da máquina.
- Fundamentação: Preserva configuração determinística e oferece modelo declarativo puro para integrações verificarem disponibilidade antes de agir.
- Alternativas consideradas: Execução de ciclo de workflow completo com fire(event) (rejeitada: fora de escopo e adiciona responsabilidade de estado); definição mutaável (rejeitada: risco de drift e viola constituição).

## Decisão 3: Lookup de regras indexado por (sourceState, event, tipoDeTransition)
- Decisão: Validar e armazenar transitions/internalTransitions com exclusividade em `(sourceState, event, transitionType)` para impor detecção de duplicatas.
- Fundamentação: Torna verificações de duplicatas determinísticas e diretamente alinhadas com requisitos de feature.
- Alternativas consideradas: Varredura baseada em lista por event (rejeitada: garantías mais fracas de duplicatas e erro menos claro).

## Decisão 4: Guard como política componível sobre regra
- Decisão: Modelar `Guard` como conceito de domínio explícito avaliado em consulta de disponibilidade com entrada de GuardContext tipado.
- Fundamentação: Design composition-first e ponto explícito de extensão para comportamento de aprovação/negação.
- Alternativas consideradas: Subclasses de transition baseadas em herança (rejeitada: adiciona complexidade de hierárquia sem benefício).

## Decisão 5: Modelo de resultado explícito para consultas
- Decisão: Retornar `AvailableEvent` estruturado que inclua event, transição subjacente, e indicação de guard (se houver).
- Fundamentação: Impede comportamento ambíguo e torna testes claros e determinísticos.
- Alternativas consideradas: Sinalização apenas por exceção (rejeitada: mistura preocupações de negado-por-guard com erros de validação).

## Decisão 6: Estratégia de teste centrada em matriz de comportamento obrigatório
- Decisão: Implementar suite de teste JUnit 5 com camadas focadas unit/integration/contract e AssertJ para assertivas legíveis.
- Fundamentação: Satisfaz lista de cobertura obrigatória e suporta fluxo test-first ou test-alongside.
- Alternativas consideradas: Testes pesados com Mockito (rejeitada a não ser que estritamente necessário); apenas um único teste de integração grande (rejeitada: baixa localização de falha).

## Decisão 7: Organização de pacote para legibilidade
- Decisão: Usar raíz de pacote `br.com.danilo.customstatemachine` com subpacotes `model`, `query` e `validation`.
- Fundamentação: Mantém conceitos de primeira classe visíveis e evita buckets de utilitário genéricos.
- Alternativas consideradas: Pacote flat com muitas classes (rejeitada: navegabilidade fraca conforme projeto cresce); pacote genérico `utils` (rejeitada por constituição).
