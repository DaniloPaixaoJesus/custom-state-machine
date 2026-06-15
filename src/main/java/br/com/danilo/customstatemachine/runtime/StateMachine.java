package br.com.danilo.customstatemachine.runtime;

import br.com.danilo.customstatemachine.model.Event;
import br.com.danilo.customstatemachine.model.State;
import br.com.danilo.customstatemachine.model.StateMachineDefinition;
import br.com.danilo.customstatemachine.model.Transition;
import br.com.danilo.customstatemachine.model.TransitionContext;

import java.util.Objects;

public final class StateMachine {

    private final StateMachineDefinition definition;
    private State currentState;

    public StateMachine(StateMachineDefinition definition) {
        this.definition = Objects.requireNonNull(definition, "definition must not be null");
        this.currentState = definition.initialState();
    }

    public StateMachineDefinition definition() {
        return definition;
    }

    public State currentState() {
        return currentState;
    }

    public TransitionResult fire(Event event) {
        return fire(event, TransitionContext.empty());
    }

    public TransitionResult fire(Event event, TransitionContext context) {
        if (event == null) {
            return TransitionResult.validationError(currentState, "event must not be null");
        }
        if (context == null) {
            return TransitionResult.validationError(currentState, "context must not be null");
        }

        return definition.transitionFor(currentState, event)
            .map(transition -> evaluateTransition(transition, context))
            .orElseGet(() -> TransitionResult.invalidTransition(currentState,
                "no transition defined for " + currentState + " + " + event));
    }

    private TransitionResult evaluateTransition(Transition transition, TransitionContext context) {
        if (transition.hasGuard() && !transition.guardOptional().orElseThrow().allows(context)) {
            return TransitionResult.guardDenied(currentState,
                "guard denied transition from " + transition.sourceState() + " to " + transition.targetState());
        }

        State previousState = currentState;
        currentState = transition.targetState();
        return TransitionResult.success(previousState, currentState);
    }
}