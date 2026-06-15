# Quickstart: simple-state-machine Validation

## Purpose
Validate the feature end-to-end as a pure Java library using mandatory transition and
validation scenarios.

## Prerequisites
- Java 21 installed and active
- Maven available in PATH

## Setup
1. From repository root, confirm Java version:
   - `java -version`
2. Run baseline test command:
   - `mvn -q test`

## Validation Scenarios

### Scenario 1: Happy path transition flow
- Build a machine definition containing:
  - States: DRAFT, SUBMITTED, APPROVED, REJECTED
  - Events: SUBMIT, APPROVE, REJECT
  - Transitions:
    - DRAFT + SUBMIT -> SUBMITTED
    - SUBMITTED + APPROVE -> APPROVED
    - SUBMITTED + REJECT -> REJECTED
- Initialize machine at DRAFT.
- Fire SUBMIT then APPROVE.
- Expected outcome:
  - Transition statuses indicate success.
  - Final state is APPROVED.

### Scenario 2: Guard approval and denial
- Configure guarded transitions with context-based rule.
- Fire event with approval context.
- Fire event with denial context.
- Expected outcome:
  - Approval context transitions to target state.
  - Denial context returns guard-denied outcome with unchanged state.

### Scenario 3: Invalid and missing transition handling
- Attempt an event not configured for the current state.
- Expected outcome:
  - Explicit invalid-transition outcome.
  - Current state remains unchanged.

### Scenario 4: Duplicate transition detection
- Attempt to create definition with duplicate `(sourceState, event)` pair.
- Expected outcome:
  - Definition creation fails deterministically.

### Scenario 5: Null input validation
- Attempt definition/runtime operations with null required inputs.
- Expected outcome:
  - Explicit validation errors or validation result outcomes.

## Recommended Test Commands
- All tests:
  - `mvn test`
- Single test class example:
  - `mvn -Dtest=StateMachineContractTest test`

## Expected Evidence
- All mandatory behavior matrix scenarios pass.
- No test relies on Spring, Lombok, reflection, or code generation.
- Domain concepts remain explicit and visible in test names and assertions.

## Related Artifacts
- Spec: [spec.md](./spec.md)
- Plan: [plan.md](./plan.md)
- Research: [research.md](./research.md)
- Data model: [data-model.md](./data-model.md)
- Contract: [contracts/library-contract.md](./contracts/library-contract.md)
