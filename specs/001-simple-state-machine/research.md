# Research: simple-state-machine

## Decision 1: Pure Java 21 core with no runtime framework
- Decision: Implement the state machine core using only Java 21 and standard library constructs.
- Rationale: Directly satisfies constitution constraints (no Spring, no Lombok, no reflection, no code generation) and keeps behavior explicit.
- Alternatives considered: Spring state machine style composition (rejected: violates framework-free rule); annotation-driven model (rejected: encourages reflection/code generation).

## Decision 2: Immutable definition, mutable runtime state
- Decision: Keep `StateMachineDefinition` immutable and isolate runtime progression in `StateMachine` instance state.
- Rationale: Preserves deterministic configuration while allowing valid runtime transition evolution.
- Alternatives considered: Fully mutable definition (rejected: risks transition drift and violates constitution); fully immutable runtime machine returning copies only (deferred: unnecessary complexity for POC).

## Decision 3: Transition lookup keyed by (sourceState, event)
- Decision: Validate and store transitions with uniqueness on `(sourceState, event)` to enforce duplicate detection.
- Rationale: Makes duplicate transition checks deterministic and directly aligned with feature requirements.
- Alternatives considered: List-based scan per event (rejected: weaker duplicate guarantees and less clear error behavior).

## Decision 4: Guard as composable policy on transition
- Decision: Model `Guard` as explicit domain concept evaluated before state change with context input.
- Rationale: Composition-first design and clear extension point for approval/denial behavior.
- Alternatives considered: Inheritance-based transition subclasses (rejected: adds hierarchy complexity without benefit).

## Decision 5: Explicit transition outcome model
- Decision: Return a structured transition result that distinguishes success, invalid transition, guard denied, and validation error.
- Rationale: Prevents ambiguous behavior and makes tests clear and deterministic.
- Alternatives considered: Exception-only signaling for all outcomes (rejected: mixes business-invalid and exceptional validation concerns).

## Decision 6: Test strategy centered on mandatory behavior matrix
- Decision: Implement JUnit 5 test suite with focused unit/integration/contract layers and optional AssertJ for readable assertions.
- Rationale: Satisfies mandatory coverage list and supports test-first or test-alongside workflow.
- Alternatives considered: Mockito-heavy tests (rejected unless strictly necessary); single large integration test only (rejected: low failure localization).

## Decision 7: Package organization for readability
- Decision: Use package root `br.com.danilo.customstatemachine` with `model`, `runtime`, and `validation` subpackages.
- Rationale: Keeps first-class concepts visible and avoids generic utility buckets.
- Alternatives considered: Flat package with many classes (rejected: poor navigability as project grows); generic `utils` package (rejected by constitution).
