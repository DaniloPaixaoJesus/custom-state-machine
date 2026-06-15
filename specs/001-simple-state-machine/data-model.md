# Data Model: simple-state-machine

## Entity: State
- Purpose: Represents a named workflow state.
- Fields:
  - `name` (String, required, non-blank)
- Constraints:
  - Equality by value (`name`).
  - Immutable value object.

## Entity: Event
- Purpose: Represents a named trigger for transition attempts.
- Fields:
  - `name` (String, required, non-blank)
- Constraints:
  - Equality by value (`name`).
  - Immutable value object.

## Entity: TransitionContext
- Purpose: Carries caller-provided data used by guard evaluation.
- Fields:
  - `attributes` (Map<String, Object>, optional, immutable view)
- Constraints:
  - Must tolerate empty context.
  - Null context handling is explicit and validated by API contract.

## Entity: Guard
- Purpose: Evaluates whether a candidate transition is allowed for a given context.
- Fields:
  - `name` (String, optional, descriptive)
  - `predicate` (function contract: context -> boolean)
- Constraints:
  - Explicit concept in domain model.
  - Composition-based usage inside Transition.

## Entity: Transition
- Purpose: Defines an allowed move from source state to target state on an event.
- Fields:
  - `sourceState` (State, required)
  - `event` (Event, required)
  - `targetState` (State, required)
  - `guard` (Guard, optional)
- Constraints:
  - `(sourceState, event)` pair must be unique in one definition.
  - Immutable value object.

## Entity: StateMachineDefinition
- Purpose: Immutable configuration for one state machine type.
- Fields:
  - `states` (Set<State>, required)
  - `events` (Set<Event>, required)
  - `transitions` (Set<Transition>, required)
  - `initialState` (State, required)
- Constraints:
  - No mutation after construction.
  - `initialState` must belong to `states`.
  - Every transition state/event references declared entities.
  - Duplicate `(sourceState, event)` transitions are rejected.

## Entity: StateMachine
- Purpose: Runtime executor using immutable definition and current state.
- Fields:
  - `definition` (StateMachineDefinition, required)
  - `currentState` (State, mutable runtime field)
- Constraints:
  - Initialized at definition initial state.
  - `fire(event, context)` validates nulls and resolves transition deterministically.

## Entity: TransitionResult
- Purpose: Standardized outcome for transition attempts.
- Fields:
  - `status` (enum-like: SUCCESS, INVALID_TRANSITION, GUARD_DENIED, VALIDATION_ERROR)
  - `fromState` (State)
  - `toState` (State, optional)
  - `message` (String, optional)
- Constraints:
  - Must preserve current state on non-success outcomes.

## Relationships
- `StateMachineDefinition` contains many `State`, `Event`, `Transition`.
- `Transition` references one source `State`, one `Event`, one target `State`, and optional `Guard`.
- `StateMachine` depends on one immutable `StateMachineDefinition`.
- `StateMachine.fire(...)` produces one `TransitionResult` per call.

## State Transitions (example flow)
- `DRAFT + SUBMIT -> SUBMITTED`
- `SUBMITTED + APPROVE -> APPROVED`
- `SUBMITTED + REJECT -> REJECTED`
