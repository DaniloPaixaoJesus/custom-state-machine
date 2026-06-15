package br.com.danilo.customstatemachine.runtime;

import br.com.danilo.customstatemachine.model.State;

import java.util.Objects;

public record TransitionResult(Status status, State fromState, State toState, String message) {

    public enum Status {
        SUCCESS,
        INVALID_TRANSITION,
        GUARD_DENIED,
        VALIDATION_ERROR
    }

    public TransitionResult {
        status = Objects.requireNonNull(status, "status must not be null");
    }

    public static TransitionResult success(State fromState, State toState) {
        return new TransitionResult(Status.SUCCESS, fromState, toState, null);
    }

    public static TransitionResult invalidTransition(State fromState, String message) {
        return new TransitionResult(Status.INVALID_TRANSITION, fromState, null, message);
    }

    public static TransitionResult guardDenied(State fromState, String message) {
        return new TransitionResult(Status.GUARD_DENIED, fromState, null, message);
    }

    public static TransitionResult validationError(State fromState, String message) {
        return new TransitionResult(Status.VALIDATION_ERROR, fromState, null, message);
    }

    public boolean isSuccess() {
        return status == Status.SUCCESS;
    }
}