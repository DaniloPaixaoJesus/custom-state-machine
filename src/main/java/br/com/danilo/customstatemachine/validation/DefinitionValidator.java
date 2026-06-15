package br.com.danilo.customstatemachine.validation;

import br.com.danilo.customstatemachine.model.Event;
import br.com.danilo.customstatemachine.model.State;
import br.com.danilo.customstatemachine.model.Transition;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public final class DefinitionValidator {

    private DefinitionValidator() {
    }

    public static void validate(Collection<State> states,
                                Collection<Event> events,
                                Collection<Transition> transitions,
                                State initialState) {
        Objects.requireNonNull(states, "states must not be null");
        Objects.requireNonNull(events, "events must not be null");
        Objects.requireNonNull(transitions, "transitions must not be null");
        Objects.requireNonNull(initialState, "initial state must not be null");

        Set<State> stateSet = new HashSet<>();
        for (State state : states) {
            stateSet.add(Objects.requireNonNull(state, "states must not contain null values"));
        }

        Set<Event> eventSet = new HashSet<>();
        for (Event event : events) {
            eventSet.add(Objects.requireNonNull(event, "events must not contain null values"));
        }

        if (!stateSet.contains(initialState)) {
            throw new IllegalArgumentException("initial state must be included in the configured states");
        }

        Set<TransitionKey> transitionKeys = new HashSet<>();
        for (Transition transition : transitions) {
            Transition candidate = Objects.requireNonNull(transition, "transitions must not contain null values");
            if (!stateSet.contains(candidate.sourceState())) {
                throw new IllegalArgumentException("transition source state must be declared in states: " + candidate.sourceState());
            }
            if (!stateSet.contains(candidate.targetState())) {
                throw new IllegalArgumentException("transition target state must be declared in states: " + candidate.targetState());
            }
            if (!eventSet.contains(candidate.event())) {
                throw new IllegalArgumentException("transition event must be declared in events: " + candidate.event());
            }
            TransitionKey key = new TransitionKey(candidate.sourceState(), candidate.event());
            if (!transitionKeys.add(key)) {
                throw new IllegalArgumentException("duplicate transition detected for source state and event: "
                    + candidate.sourceState() + " + " + candidate.event());
            }
        }
    }

    private record TransitionKey(State sourceState, Event event) {
    }
}