<!--
Sync Impact Report
- Version change: N/A -> 1.0.0
- Modified principles:
	- Template Principle Slot 1 -> I. Framework-Free Java
	- Template Principle Slot 2 -> II. Explicit Domain Model
	- Template Principle Slot 3 -> III. Immutable Configuration
	- Template Principle Slot 4 -> IV. Composition Over Inheritance
	- Template Principle Slot 5 -> V. Test-First or Test-Alongside Development
- Added principles:
	- VI. Readability Over Cleverness
	- VII. Agent Discipline
- Added sections:
	- Architecture & Quality Constraints
	- Development Workflow & Constitution Gates
- Removed sections:
	- None
- Templates requiring updates:
	- updated: .specify/templates/plan-template.md
	- updated: .specify/templates/spec-template.md
	- updated: .specify/templates/tasks-template.md
	- pending: .specify/templates/commands/*.md (directory does not exist)
- Follow-up TODOs:
	- None
-->

# custom-state-machine Constitution

## Core Principles

### I. Framework-Free Java
The codebase MUST use pure Java 21 with no application frameworks. Spring, Lombok,
reflection-based runtime wiring, and code generation are prohibited. Rationale:
the proof of concept exists to validate explicit state machine design, not framework
integration behavior.

### II. Explicit Domain Model
State, Event, Transition, Guard, and StateMachine MUST be modeled as first-class
domain concepts with clear APIs and names. These concepts MUST remain visible in
code structure and documentation. Rationale: domain clarity is the primary learning
goal of this project.

### III. Immutable Configuration
State machine definitions MUST become immutable after creation. Any change to a
machine configuration MUST require constructing a new definition instance instead of
mutating existing objects. Rationale: immutability reduces runtime ambiguity and
prevents hidden transition drift.

### IV. Composition Over Inheritance
Composition MUST be the default mechanism for behavior reuse and extension.
Inheritance MAY be used only when composition is demonstrably inferior and the
justification is documented in the plan/spec/task artifacts. Rationale: composition
keeps the model simpler and easier to evolve safely.

### V. Test-First or Test-Alongside Development
Every behavior MUST have automated tests written before or alongside implementation.
JUnit 5 is required; AssertJ is allowed; Mockito MAY be used only when necessary.
At minimum, tests MUST cover valid transitions, invalid transitions, guard approval,
guard denial, duplicate transitions, missing transitions, and null inputs.
Rationale: behavior correctness is central to a state machine implementation.

### VI. Readability Over Cleverness
Code MUST prioritize readability over generic or clever abstractions. Descriptive
names and small methods are required. Generic names such as Utils, Helpers,
Managers, and Processors are prohibited unless a specific domain meaning is proven.
Rationale: the project should be understandable quickly by humans and AI agents.

### VII. Agent Discipline
AI agents and human contributors MUST follow spec, plan, and tasks artifacts before
implementation. When behavior changes, related documentation MUST be updated in the
same change set. If requirements are unclear or conflicting, work MUST stop and a
clarification request MUST be made before coding continues.

## Architecture & Quality Constraints

- Runtime dependencies: No external runtime dependencies are allowed.
- Build system: Maven is mandatory.
- Language version: Java 21 is mandatory.
- Package root: br.com.danilo.customstatemachine.
- Data style: prefer immutable value objects.
- Naming and structure: prefer descriptive names and small methods.
- Design hygiene: avoid generic abstractions with vague names.

## Development Workflow & Constitution Gates

- Every implementation effort MUST pass a Constitution Check before coding starts.
- Spec, plan, and tasks artifacts MUST explicitly capture how each principle is met.
- Pull requests MUST include evidence that required test scenarios are automated and
	passing.
- Any principle violation MUST include explicit justification in plan complexity
	tracking and be reviewed before merge.

## Governance

This constitution is the authoritative engineering policy for this repository and
supersedes local conventions when conflicts occur.

Amendment procedure:
- Any change to architectural principles requires a constitution update in the same
	change stream.
- Amendments MUST include a Sync Impact Report and updates to affected templates.
- Amendments MUST be reviewed for backward impact on existing specs/plans/tasks.

Versioning policy:
- MAJOR: backward-incompatible governance or principle redefinition/removal.
- MINOR: new principle/section or materially expanded mandatory guidance.
- PATCH: clarifications, wording improvements, or typo fixes with no policy impact.

Compliance review expectations:
- Constitution compliance MUST be checked during planning and before implementation.
- Reviews MUST reject changes that violate non-negotiable principles without
	documented exception and approval.

**Version**: 1.0.0 | **Ratified**: 2026-06-14 | **Last Amended**: 2026-06-14
