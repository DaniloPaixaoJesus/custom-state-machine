# Tasks: simple-state-machine

**Input**: Design documents from `/specs/001-simple-state-machine/`

**Prerequisites**: plan.md (required), spec.md (required), research.md, data-model.md, contracts/, quickstart.md

**Tests**: Tests are mandatory for this feature by specification and constitution.

**Organization**: Tasks are grouped by user story to enable independent implementation and testing of each story.

## Phase 1: Setup (Shared Infrastructure)

**Purpose**: Initialize Maven project structure and baseline build/test configuration.

- [ ] T001 Create Maven project descriptor with Java 21 settings in pom.xml
- [ ] T002 Create source and test package directories under src/main/java/br/com/danilo/customstatemachine/ and src/test/java/br/com/danilo/customstatemachine/
- [ ] T003 [P] Configure JUnit 5, AssertJ (optional usage), and Surefire plugin in pom.xml
- [ ] T004 [P] Add project .gitignore entries for Maven build outputs in .gitignore
- [ ] T005 Add feature usage and local test execution section in README.md

---

## Phase 2: Foundational (Blocking Prerequisites)

**Purpose**: Implement shared infrastructure required by all user stories.

**CRITICAL**: No user story implementation starts before this phase is complete.

- [ ] T006 Create transition status/result contract in src/main/java/br/com/danilo/customstatemachine/runtime/TransitionResult.java
- [ ] T007 Create definition validator skeleton for shared invariants in src/main/java/br/com/danilo/customstatemachine/validation/DefinitionValidator.java
- [ ] T008 [P] Create guard and context abstractions in src/main/java/br/com/danilo/customstatemachine/model/Guard.java and src/main/java/br/com/danilo/customstatemachine/model/TransitionContext.java
- [ ] T009 Create reusable test fixtures for canonical states/events in src/test/java/br/com/danilo/customstatemachine/support/StateMachineFixtures.java
- [ ] T010 Establish contract test class shell in src/test/java/br/com/danilo/customstatemachine/contract/StateMachineContractTest.java

**Checkpoint**: Shared runtime and test foundations are ready.

---

## Phase 3: User Story 1 - Define A Deterministic Machine (Priority: P1) MVP

**Goal**: Provide explicit immutable machine definition with duplicate transition detection.

**Independent Test**: Build a machine definition for DRAFT/SUBMITTED/APPROVED/REJECTED and verify immutability plus duplicate transition rejection.

### Tests for User Story 1

- [ ] T011 [P] [US1] Add immutable definition and initial-state validation tests in src/test/java/br/com/danilo/customstatemachine/unit/DefinitionValidationTest.java
- [ ] T012 [P] [US1] Add duplicate transition detection tests in src/test/java/br/com/danilo/customstatemachine/unit/DuplicateTransitionDetectionTest.java
- [ ] T013 [P] [US1] Add contract tests for explicit domain concepts in src/test/java/br/com/danilo/customstatemachine/contract/StateMachineContractTest.java

### Implementation for User Story 1

- [ ] T014 [P] [US1] Implement immutable State value object in src/main/java/br/com/danilo/customstatemachine/model/State.java
- [ ] T015 [P] [US1] Implement immutable Event value object in src/main/java/br/com/danilo/customstatemachine/model/Event.java
- [ ] T016 [P] [US1] Implement immutable Transition with optional Guard in src/main/java/br/com/danilo/customstatemachine/model/Transition.java
- [ ] T017 [US1] Implement immutable StateMachineDefinition constructor and getters in src/main/java/br/com/danilo/customstatemachine/model/StateMachineDefinition.java
- [ ] T018 [US1] Implement duplicate and membership rules in src/main/java/br/com/danilo/customstatemachine/validation/DefinitionValidator.java

**Checkpoint**: Machine definition is explicit, immutable, and validated.

---

## Phase 4: User Story 2 - Move Between States Safely (Priority: P2)

**Goal**: Execute valid transitions at runtime with guard-aware behavior.

**Independent Test**: Start at DRAFT, fire SUBMIT then APPROVE, and verify successful state progression and guard behavior.

### Tests for User Story 2

- [ ] T019 [P] [US2] Add happy-path integration flow test for DRAFT->SUBMITTED->APPROVED in src/test/java/br/com/danilo/customstatemachine/integration/ExampleFlowIntegrationTest.java
- [ ] T020 [P] [US2] Add guard approval and denial unit tests in src/test/java/br/com/danilo/customstatemachine/unit/GuardEvaluationTest.java
- [ ] T021 [P] [US2] Add invalid and missing transition unit tests in src/test/java/br/com/danilo/customstatemachine/unit/InvalidTransitionHandlingTest.java

### Implementation for User Story 2

- [ ] T022 [US2] Implement StateMachine runtime holder and current-state initialization in src/main/java/br/com/danilo/customstatemachine/runtime/StateMachine.java
- [ ] T023 [US2] Implement transition lookup by source-state and event in src/main/java/br/com/danilo/customstatemachine/runtime/StateMachine.java
- [ ] T024 [US2] Implement guard evaluation and transition application flow in src/main/java/br/com/danilo/customstatemachine/runtime/StateMachine.java
- [ ] T025 [US2] Finalize TransitionResult outcome mapping for success, invalid transition, and guard denial in src/main/java/br/com/danilo/customstatemachine/runtime/TransitionResult.java

**Checkpoint**: Runtime transition behavior and guard logic are deterministic and test-covered.

---

## Phase 5: User Story 3 - Fail Fast On Invalid Usage (Priority: P3)

**Goal**: Provide explicit null validation and failure behavior with state preservation.

**Independent Test**: Attempt null inputs and unsupported events; verify explicit failures and unchanged current state.

### Tests for User Story 3

- [ ] T026 [P] [US3] Add null input validation tests for definition and runtime APIs in src/test/java/br/com/danilo/customstatemachine/unit/NullInputValidationTest.java
- [ ] T027 [P] [US3] Add failure-path integration tests for state preservation in src/test/java/br/com/danilo/customstatemachine/integration/FailurePathIntegrationTest.java
- [ ] T028 [P] [US3] Add contract assertions for validation-error outcomes in src/test/java/br/com/danilo/customstatemachine/contract/StateMachineContractTest.java

### Implementation for User Story 3

- [ ] T029 [US3] Implement definition-level null validations with explicit messages in src/main/java/br/com/danilo/customstatemachine/model/StateMachineDefinition.java
- [ ] T030 [US3] Implement runtime null validations for event/context inputs in src/main/java/br/com/danilo/customstatemachine/runtime/StateMachine.java
- [ ] T031 [US3] Implement validation error result creation and message standardization in src/main/java/br/com/danilo/customstatemachine/validation/DefinitionValidator.java and src/main/java/br/com/danilo/customstatemachine/runtime/TransitionResult.java

**Checkpoint**: Invalid usage is rejected explicitly and safely.

---

## Phase 6: Polish & Cross-Cutting Concerns

**Purpose**: Final consistency, documentation alignment, and end-to-end verification.

- [ ] T032 [P] Document final usage examples and behavior matrix in README.md
- [ ] T033 [P] Align execution/verification steps with implemented behavior in specs/001-simple-state-machine/quickstart.md
- [ ] T034 [P] Update implementation notes if behavior changed during build in specs/001-simple-state-machine/plan.md and specs/001-simple-state-machine/research.md
- [ ] T035 Run full test suite and capture completion evidence in specs/001-simple-state-machine/checklists/requirements.md

---

## Dependencies & Execution Order

### Phase Dependencies

- Phase 1 (Setup): no dependencies.
- Phase 2 (Foundational): depends on Phase 1 and blocks all user stories.
- Phase 3 (US1): depends on Phase 2.
- Phase 4 (US2): depends on Phase 2 and reuses US1 definition components.
- Phase 5 (US3): depends on Phase 2 and extends failure behavior from US2 runtime paths.
- Phase 6 (Polish): depends on completion of desired user stories.

### User Story Dependencies

- US1 (P1): first deliverable and MVP baseline.
- US2 (P2): depends on US1 domain definition artifacts.
- US3 (P3): depends on US2 runtime execution surface.

### Within Each User Story

- Tests are written before or alongside implementation and must fail before code completion.
- Value objects and contracts before orchestration behavior.
- Runtime behavior before final documentation updates.

## Parallel Opportunities

- T003 and T004 can run in parallel during setup.
- T008 can run in parallel with T007 in foundational phase.
- US1 tasks T011/T012/T013 and T014/T015/T016 can run in parallel by file.
- US2 tests T019/T020/T021 can run in parallel.
- US3 tests T026/T027/T028 can run in parallel.
- Polish tasks T032/T033/T034 can run in parallel.

## Parallel Example: User Story 1

```bash
# Parallel test authoring
Task: "T011 [US1] Definition validation tests in src/test/java/br/com/danilo/customstatemachine/unit/DefinitionValidationTest.java"
Task: "T012 [US1] Duplicate transition tests in src/test/java/br/com/danilo/customstatemachine/unit/DuplicateTransitionDetectionTest.java"
Task: "T013 [US1] Contract tests in src/test/java/br/com/danilo/customstatemachine/contract/StateMachineContractTest.java"

# Parallel model implementation
Task: "T014 [US1] State value object in src/main/java/br/com/danilo/customstatemachine/model/State.java"
Task: "T015 [US1] Event value object in src/main/java/br/com/danilo/customstatemachine/model/Event.java"
Task: "T016 [US1] Transition value object in src/main/java/br/com/danilo/customstatemachine/model/Transition.java"
```

## Parallel Example: User Story 2

```bash
Task: "T019 [US2] Example flow integration test in src/test/java/br/com/danilo/customstatemachine/integration/ExampleFlowIntegrationTest.java"
Task: "T020 [US2] Guard evaluation tests in src/test/java/br/com/danilo/customstatemachine/unit/GuardEvaluationTest.java"
Task: "T021 [US2] Invalid transition tests in src/test/java/br/com/danilo/customstatemachine/unit/InvalidTransitionHandlingTest.java"
```

## Parallel Example: User Story 3

```bash
Task: "T026 [US3] Null input tests in src/test/java/br/com/danilo/customstatemachine/unit/NullInputValidationTest.java"
Task: "T027 [US3] Failure-path integration tests in src/test/java/br/com/danilo/customstatemachine/integration/FailurePathIntegrationTest.java"
Task: "T028 [US3] Validation contract assertions in src/test/java/br/com/danilo/customstatemachine/contract/StateMachineContractTest.java"
```

## Implementation Strategy

### MVP First (User Story 1 Only)

1. Complete Phase 1 and Phase 2.
2. Deliver US1 in Phase 3.
3. Validate immutability and duplicate detection as MVP acceptance.

### Incremental Delivery

1. Deliver US1 (definition correctness and immutability).
2. Deliver US2 (runtime transitions and guard logic).
3. Deliver US3 (null validation and failure-path guarantees).
4. Finish with Phase 6 documentation and full-suite verification.

### Constitution Alignment During Execution

1. Keep model concepts explicit in package/class naming.
2. Reject framework or code-generation additions.
3. Preserve immutable definition behavior across refactors.
4. Keep documentation updated when behavior changes.
