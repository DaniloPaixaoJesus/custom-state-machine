# Implementation Plan: simple-state-machine

**Branch**: `[main]` | **Date**: 2026-06-14 | **Spec**: [spec.md](./spec.md)

**Input**: Feature specification from `/specs/001-simple-state-machine/spec.md`

## Summary

Build a framework-free Java 21 library proof of concept that models a deterministic
state machine with explicit State, Event, Transition, Guard, and StateMachine
concepts. The plan uses immutable configuration objects for definition-time data and
separates runtime state handling from configuration. Validation and behavior safety
are enforced through mandatory JUnit 5 automated tests covering valid transitions,
invalid transitions, guard approval and denial, duplicate transitions, missing
transitions, and null input validation.

## Technical Context

**Language/Version**: Java 21

**Primary Dependencies**: Maven + JUnit 5 (test), AssertJ (optional test assertions), Mockito (only if strictly needed)

**Storage**: N/A

**Testing**: JUnit 5 mandatory; AssertJ allowed; Mockito only if necessary

**Target Platform**: JVM (Java 21), local development and CI execution

**Project Type**: Library (pure Java, framework-free)

**Performance Goals**: Deterministic transition evaluation; sub-millisecond average transition evaluation in unit test scenarios

**Constraints**: No Spring, no Lombok, no reflection, no code generation, immutable machine definition, explicit domain concepts, descriptive naming, small methods

**Scale/Scope**: Small proof of concept library with one core domain package and focused test suite

## Constitution Check

*GATE: Must pass before Phase 0 research. Re-check after Phase 1 design.*

- Framework-free Java gate: no Spring, Lombok, reflection-based runtime wiring,
  or code generation in planned approach.
- Domain model gate: State, Event, Transition, Guard, and StateMachine are explicit
  first-class concepts in design artifacts.
- Immutability gate: machine definition is immutable after construction.
- Composition gate: composition-first approach; any inheritance is justified in
  Complexity Tracking.
- Testing gate: JUnit 5 test plan covers valid/invalid transitions, guard
  approval/denial, duplicate transitions, missing transitions, and null inputs.
- Readability gate: naming and module structure avoid generic Utils/Helpers/
  Managers/Processors.
- Agent discipline gate: plan references spec and tasks traceability, and includes
  doc update expectations for behavior changes.

### Pre-Phase 0 Gate Assessment

- Framework-free Java gate: PASS. Design constrained to Java standard library and
  Maven test dependencies.
- Domain model gate: PASS. Plan defines explicit classes/interfaces for State,
  Event, Transition, Guard, StateMachine, plus definition/runtime separation.
- Immutability gate: PASS. Definition objects are immutable value objects with
  constructor-time validation.
- Composition gate: PASS. Guard behavior and transition evaluation are composed;
  inheritance is not required.
- Testing gate: PASS. Test strategy explicitly includes all mandatory scenarios.
- Readability gate: PASS. Naming and package layout avoid generic utility classes.
- Agent discipline gate: PASS. Artifacts include contracts, data model, quickstart,
  and traceable checks.

## Project Structure

### Documentation (this feature)

```text
specs/001-simple-state-machine/
├── plan.md              # This file (/speckit.plan command output)
├── research.md          # Phase 0 output (/speckit.plan command)
├── data-model.md        # Phase 1 output (/speckit.plan command)
├── quickstart.md        # Phase 1 output (/speckit.plan command)
├── contracts/           # Phase 1 output (/speckit.plan command)
└── tasks.md             # Phase 2 output (/speckit.tasks command - NOT created by /speckit.plan)
```

### Source Code (repository root)

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
            │   ├── Guard.java
            │   ├── TransitionContext.java
            │   └── StateMachineDefinition.java
            ├── runtime/
            │   ├── StateMachine.java
            │   └── TransitionResult.java
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
            │   └── StateMachineContractTest.java
            ├── unit/
            │   ├── DefinitionValidationTest.java
            │   ├── GuardEvaluationTest.java
            │   └── NullInputValidationTest.java
            └── integration/
              └── ExampleFlowIntegrationTest.java
```

**Structure Decision**: Single Maven library project selected. This structure keeps
domain concepts explicit and isolated from runtime behavior while preserving a small,
readable package layout suitable for a POC.

## Complexity Tracking

No constitution violations require complexity justification for this plan.

### Post-Phase 1 Constitution Re-Check

- Framework-free Java gate: PASS. Research and design artifacts specify no framework
  runtime dependencies.
- Domain model gate: PASS. Data model and contract keep domain concepts first-class
  and explicit.
- Immutability gate: PASS. StateMachineDefinition and related value objects remain
  immutable after creation.
- Composition gate: PASS. Guard and transition behavior use composition; no
  inheritance dependency introduced.
- Testing gate: PASS. Quickstart and contracts define mandatory JUnit 5 scenarios.
- Readability gate: PASS. Artifact naming is descriptive and avoids generic helper
  abstractions.
- Agent discipline gate: PASS. Spec-plan-research-contract-data model quickstart
  alignment is maintained.
