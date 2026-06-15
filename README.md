# custom-state-machine

Proof of concept for a simple, framework-free state machine in pure Java 21.

## Technical Baseline

- Java 21
- Maven
- Package root: `br.com.danilo.customstatemachine`
- No external runtime dependencies

## Build and Test

Run the full test suite:

```bash
mvn test
```

Run a single test class:

```bash
mvn -Dtest=StateMachineContractTest test
```

## Engineering Rules

Project governance and non-negotiable architecture/testing rules are defined in
[.specify/memory/constitution.md](.specify/memory/constitution.md).

All implementation work must pass the Constitution Check before coding starts.
