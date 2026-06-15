package br.com.danilo.customstatemachine.model;

@FunctionalInterface
public interface Guard {

    boolean allows(TransitionContext context);
}