# Library Contract: simple-state-machine

## Public Contract Scope
This contract describes expected behavior for a pure Java state machine library.
It is intentionally framework-free and focused on deterministic domain behavior.

## Required Concepts
The library contract MUST expose these explicit first-class concepts:
- `State`
- `Event`
- `Transition`
- `Guard`
- `StateMachine`
- `StateMachineDefinition`
- `TransitionContext`
- `TransitionResult`

## Definition Construction Contract
- Consumers can construct a `StateMachineDefinition` from explicit states, events,
  transitions, and an initial state.
- Definition creation fails when:
  - A required input is null.
  - Initial state is not declared in states.
  - Any transition references undeclared state/event.
  - Duplicate transitions are provided for same `(sourceState, event)`.
- After creation, definition is immutable.

## Runtime Execution Contract
- `StateMachine` starts at definition initial state.
- `fire(event, context)` attempts transition using current state + event key.
- If matching transition exists and guard approves (or guard is absent), transition
  succeeds and current state updates to target state.
- If no matching transition exists, result status is `INVALID_TRANSITION` and state
  remains unchanged.
- If guard denies, result status is `GUARD_DENIED` and state remains unchanged.
- If input validation fails (e.g., null event), result status is `VALIDATION_ERROR`
  or explicit validation error signaling as defined in implementation.

## Guard Contract
- Guard receives `TransitionContext` and returns approval/denial decision.
- Guard logic must not mutate machine definition.
- Guard is optional at transition definition time.

## Mandatory Testable Behavior Matrix
- Valid transition execution
- Invalid transition handling
- Guard approval path
- Guard denial path
- Duplicate transition detection
- Missing transition path
- Null input validation

## Non-Goals (Out of Scope)
- Web endpoints or HTTP API contracts
- Persistence or storage integration
- Framework integration (Spring, Jakarta EE, etc.)
