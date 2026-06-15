package br.com.danilo.customstatemachine.model;

import java.util.Objects;
import java.util.Optional;

public record Transition(State sourceState, Event event, State targetState, Guard guard) {

    public Transition {
        sourceState = Objects.requireNonNull(sourceState, "source state must not be null");
        event = Objects.requireNonNull(event, "event must not be null");
        targetState = Objects.requireNonNull(targetState, "target state must not be null");
    }

    public Transition(State sourceState, Event event, State targetState) {
        this(sourceState, event, targetState, null);
    }

    public Optional<Guard> guardOptional() {
        return Optional.ofNullable(guard);
    }

    public boolean hasGuard() {
        return guard != null;
    }
}