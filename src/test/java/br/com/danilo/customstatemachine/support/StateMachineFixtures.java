package br.com.danilo.customstatemachine.support;

import br.com.danilo.customstatemachine.model.Event;
import br.com.danilo.customstatemachine.model.State;
import br.com.danilo.customstatemachine.model.StateMachineDefinition;
import br.com.danilo.customstatemachine.model.Transition;
import br.com.danilo.customstatemachine.model.TransitionContext;
import br.com.danilo.customstatemachine.runtime.StateMachine;

import java.util.List;
import java.util.Map;

public final class StateMachineFixtures {

    public static final State DRAFT = new State("DRAFT");
    public static final State SUBMITTED = new State("SUBMITTED");
    public static final State APPROVED = new State("APPROVED");
    public static final State REJECTED = new State("REJECTED");

    public static final Event SUBMIT = new Event("SUBMIT");
    public static final Event APPROVE = new Event("APPROVE");
    public static final Event REJECT = new Event("REJECT");

    private StateMachineFixtures() {
    }

    public static StateMachineDefinition exampleDefinition() {
        return new StateMachineDefinition(
            List.of(DRAFT, SUBMITTED, APPROVED, REJECTED),
            List.of(SUBMIT, APPROVE, REJECT),
            List.of(
                new Transition(DRAFT, SUBMIT, SUBMITTED),
                new Transition(SUBMITTED, APPROVE, APPROVED, context -> context.booleanAttribute("approved")),
                new Transition(SUBMITTED, REJECT, REJECTED)
            ),
            DRAFT
        );
    }

    public static StateMachine exampleMachine() {
        return new StateMachine(exampleDefinition());
    }

    public static TransitionContext approvalContext() {
        return TransitionContext.of(Map.of("approved", Boolean.TRUE));
    }

    public static TransitionContext denialContext() {
        return TransitionContext.of(Map.of("approved", Boolean.FALSE));
    }
}