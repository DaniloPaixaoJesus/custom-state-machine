package br.com.danilo.customstatemachine.model;

import java.util.Objects;

public record State(String name) {

    public State {
        name = Objects.requireNonNull(name, "state name must not be null").trim();
        if (name.isEmpty()) {
            throw new IllegalArgumentException("state name must not be blank");
        }
    }

    @Override
    public String toString() {
        return name;
    }
}