package br.com.danilo.customstatemachine.contract;

import br.com.danilo.customstatemachine.model.StateMachineDefinition;
import br.com.danilo.customstatemachine.runtime.StateMachine;
import br.com.danilo.customstatemachine.runtime.TransitionResult;
import br.com.danilo.customstatemachine.support.StateMachineFixtures;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class StateMachineContractTest {

    @Test
    void exposesExplicitDomainConceptsAndInitialState() {
        StateMachineDefinition definition = StateMachineFixtures.exampleDefinition();

        assertThat(definition.states()).containsExactlyInAnyOrder(
            StateMachineFixtures.DRAFT,
            StateMachineFixtures.SUBMITTED,
            StateMachineFixtures.APPROVED,
            StateMachineFixtures.REJECTED
        );
        assertThat(definition.events()).containsExactlyInAnyOrder(
            StateMachineFixtures.SUBMIT,
            StateMachineFixtures.APPROVE,
            StateMachineFixtures.REJECT
        );
        assertThat(definition.transitions()).hasSize(3);
        assertThat(definition.initialState()).isEqualTo(StateMachineFixtures.DRAFT);
    }

    @Test
    void stateMachineStartsAtInitialStateAndReturnsTransitionResults() {
        StateMachine machine = StateMachineFixtures.exampleMachine();

        assertThat(machine.currentState()).isEqualTo(StateMachineFixtures.DRAFT);

        TransitionResult result = machine.fire(StateMachineFixtures.SUBMIT);

        assertThat(result.status()).isEqualTo(TransitionResult.Status.SUCCESS);
        assertThat(result.fromState()).isEqualTo(StateMachineFixtures.DRAFT);
        assertThat(result.toState()).isEqualTo(StateMachineFixtures.SUBMITTED);
    }
}