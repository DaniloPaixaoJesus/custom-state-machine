package br.com.danilo.customstatemachine.model;

import java.util.Objects;

public record Event(String name) {

    public Event {
        name = Objects.requireNonNull(name, "event name must not be null").trim();
        if (name.isEmpty()) {
            throw new IllegalArgumentException("event name must not be blank");
        }
    }

    @Override
    public String toString() {
        return name;
    }
}