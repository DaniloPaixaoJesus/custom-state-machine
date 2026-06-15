package br.com.danilo.customstatemachine.model;

import br.com.danilo.customstatemachine.validation.DefinitionValidator;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

public final class StateMachineDefinition {

    private final Set<State> states;
    private final Set<Event> events;
    private final Set<Transition> transitions;
    private final State initialState;
    private final Map<TransitionKey, Transition> transitionsByKey;

    public StateMachineDefinition(Collection<State> states,
                                  Collection<Event> events,
                                  Collection<Transition> transitions,
                                  State initialState) {
        DefinitionValidator.validate(states, events, transitions, initialState);
        this.states = Collections.unmodifiableSet(new LinkedHashSet<>(states));
        this.events = Collections.unmodifiableSet(new LinkedHashSet<>(events));
        this.transitions = Collections.unmodifiableSet(new LinkedHashSet<>(transitions));
        this.initialState = initialState;
        this.transitionsByKey = indexTransitions(this.transitions);
    }

    public Set<State> states() {
        return states;
    }

    public Set<Event> events() {
        return events;
    }

    public Set<Transition> transitions() {
        return transitions;
    }

    public State initialState() {
        return initialState;
    }

    public Optional<Transition> transitionFor(State sourceState, Event event) {
        Objects.requireNonNull(sourceState, "source state must not be null");
        Objects.requireNonNull(event, "event must not be null");
        return Optional.ofNullable(transitionsByKey.get(new TransitionKey(sourceState, event)));
    }

    private Map<TransitionKey, Transition> indexTransitions(Collection<Transition> transitions) {
        Map<TransitionKey, Transition> indexed = new LinkedHashMap<>();
        for (Transition transition : transitions) {
            indexed.put(new TransitionKey(transition.sourceState(), transition.event()), transition);
        }
        return Map.copyOf(indexed);
    }

    private record TransitionKey(State sourceState, Event event) {
    }
}