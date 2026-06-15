package br.com.danilo.customstatemachine.model;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public record TransitionContext(Map<String, Object> attributes) {

    public TransitionContext {
        Objects.requireNonNull(attributes, "attributes must not be null");
        attributes = Map.copyOf(attributes);
    }

    public static TransitionContext empty() {
        return new TransitionContext(Map.of());
    }

    public static TransitionContext of(Map<String, Object> attributes) {
        return new TransitionContext(attributes);
    }

    public Optional<Object> attribute(String name) {
        Objects.requireNonNull(name, "attribute name must not be null");
        return Optional.ofNullable(attributes.get(name));
    }

    public boolean booleanAttribute(String name) {
        return attribute(name)
            .filter(Boolean.class::isInstance)
            .map(Boolean.class::cast)
            .orElse(Boolean.FALSE);
    }
}