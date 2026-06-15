# Feature Specification: simple-state-machine

**Feature Branch**: `[001-simple-state-machine]`

**Created**: 2026-06-14

**Status**: Draft

**Input**: User description: "Create the first feature specification for this repository for a simple state machine proof of concept with explicit state machine concepts, immutable definition, guarded transitions, and robust validation."

## User Scenarios & Testing *(mandatory)*

### User Story 1 - Define A Deterministic Machine (Priority: P1)

As a library consumer, I want to define a state machine with explicit states, events,
transitions, and an initial state so that I can model a business flow unambiguously.

**Why this priority**: Without deterministic machine definition, no runtime behavior can be trusted or tested.

**Independent Test**: Can be fully tested by creating a machine definition for the sample flow and verifying that all expected transitions are registered exactly once.

**Acceptance Scenarios**:

1. **Given** a definition with states DRAFT, SUBMITTED, APPROVED and REJECTED, and events SUBMIT, APPROVE and REJECT, **When** transitions are registered for DRAFT + SUBMIT and SUBMITTED + APPROVE and SUBMITTED + REJECT, **Then** the definition is accepted as valid.
2. **Given** an existing transition for SUBMITTED + APPROVE, **When** the same source-state and event pair is registered again, **Then** definition creation fails with a duplicate transition error.
3. **Given** a completed machine definition, **When** a consumer attempts to change transitions or initial state after creation, **Then** the definition remains unchanged.

---

### User Story 2 - Move Between States Safely (Priority: P2)

As a runtime user of the state machine, I want to fire events and move between states only when allowed so that current state always reflects valid workflow progress.

**Why this priority**: Runtime transition execution is the main user value delivered by the library.

**Independent Test**: Can be fully tested by starting in DRAFT, firing SUBMIT then APPROVE, and verifying resulting states after each event.

**Acceptance Scenarios**:

1. **Given** current state DRAFT and a valid transition DRAFT + SUBMIT -> SUBMITTED, **When** SUBMIT is fired, **Then** current state becomes SUBMITTED.
2. **Given** current state SUBMITTED and transition SUBMITTED + APPROVE -> APPROVED with a guard that approves the context, **When** APPROVE is fired with that context, **Then** current state becomes APPROVED.
3. **Given** current state SUBMITTED and transition SUBMITTED + REJECT -> REJECTED with a guard that denies the context, **When** REJECT is fired with that context, **Then** state remains SUBMITTED and denial is returned explicitly.

---

### User Story 3 - Fail Fast On Invalid Usage (Priority: P3)

As a library consumer, I want explicit handling for invalid transitions and invalid inputs so that integration mistakes are detected immediately.

**Why this priority**: Fast, explicit failure prevents hidden state corruption and reduces debugging effort.

**Independent Test**: Can be fully tested by attempting unsupported events and null inputs and verifying deterministic errors without state mutation.

**Acceptance Scenarios**:

1. **Given** current state APPROVED with no APPROVED + REJECT transition, **When** REJECT is fired, **Then** operation returns invalid transition outcome and state remains APPROVED.
2. **Given** a transition operation request, **When** required inputs are null, **Then** operation fails with explicit validation errors.

---

### Edge Cases

- What happens when a transition has no guard configured? The transition proceeds based on state and event match only.
- How does system handle guard evaluation that denies transition? Transition is blocked and current state remains unchanged.
- What happens when an event is fired from a state with no matching transition? A deterministic invalid transition outcome is returned.
- What happens when state machine is initialized with an initial state not present in definition? Initialization fails with explicit validation error.

## Requirements *(mandatory)*

### Functional Requirements

- **FR-001**: System MUST allow explicit definition of State as a first-class domain concept.
- **FR-002**: System MUST allow explicit definition of Event as a first-class domain concept.
- **FR-003**: System MUST allow explicit definition of Transition as a first-class domain concept linking source state, event, and target state.
- **FR-004**: System MUST allow explicit definition of Guard as a first-class domain concept that evaluates a transition context.
- **FR-005**: System MUST expose StateMachine as a first-class domain concept containing runtime current state.
- **FR-006**: System MUST support creation of a machine definition that becomes immutable after creation.
- **FR-007**: System MUST initialize runtime current state from a valid configured initial state.
- **FR-008**: System MUST attempt state transition when an event is fired from the current state.
- **FR-009**: System MUST evaluate guard before applying guarded transitions and only change state on guard approval.
- **FR-010**: System MUST return explicit invalid transition handling when no matching transition exists for current state and event.
- **FR-011**: System MUST detect duplicate transition definitions for the same source-state and event combination during definition creation.
- **FR-012**: System MUST validate required inputs and reject null values with explicit validation errors.
- **FR-013**: System MUST support the example flow DRAFT + SUBMIT -> SUBMITTED, SUBMITTED + APPROVE -> APPROVED, and SUBMITTED + REJECT -> REJECTED.

### Key Entities *(include if feature involves data)*

- **State**: Represents a workflow stage identifier used as source or target in transitions.
- **Event**: Represents a trigger that requests movement from one state to another.
- **Transition**: Represents a mapping of source state + event to target state, with optional guard.
- **Guard**: Represents a rule that receives context and approves or denies a candidate transition.
- **StateMachineDefinition**: Represents immutable configuration containing states, events, transitions, and initial state.
- **StateMachine**: Represents runtime instance holding current state and exposing event firing behavior.
- **TransitionContext**: Represents caller-provided data evaluated by guards during transition attempts.

## Success Criteria *(mandatory)*

### Measurable Outcomes

- **SC-001**: 100% of configured valid transition scenarios in acceptance tests move to the expected target state.
- **SC-002**: 100% of unsupported state-event combinations in acceptance tests return explicit invalid transition outcomes without state change.
- **SC-003**: 100% of guard-denied transition attempts in acceptance tests preserve current state and return explicit denial outcomes.
- **SC-004**: 100% of duplicate transition definitions in acceptance tests are rejected at definition creation time.
- **SC-005**: 100% of null-input scenarios defined in acceptance tests are rejected with explicit validation outcomes.

## Assumptions

- The first release targets single-process usage and does not require distributed coordination.
- Consumers provide stable identifiers for states and events within a machine definition.
- Guard context schema is defined by consuming code and treated as opaque by the state machine core.
- Persistence and visualization of machine state are out of scope for this feature.

## Constitution Alignment *(mandatory)*

- **CA-001 Framework-Free**: Feature remains framework-free and uses no Spring, Lombok, reflection wiring, or code generation.
- **CA-002 Domain Visibility**: State, Event, Transition, Guard, and StateMachine are explicit entities in requirements, scenarios, and acceptance outcomes.
- **CA-003 Immutability**: StateMachineDefinition is fixed after creation; any change requires a new definition instance.
- **CA-004 Composition**: Guard behavior is composed into Transition behavior; no inheritance requirement is introduced by this feature.
- **CA-005 Automated Tests**: Test scope includes valid transitions, invalid transitions, guard approval, guard denial, duplicate transitions, missing transitions, and null inputs.
- **CA-006 Readability**: Feature scope favors explicit naming and constrained responsibilities over generic abstractions.
