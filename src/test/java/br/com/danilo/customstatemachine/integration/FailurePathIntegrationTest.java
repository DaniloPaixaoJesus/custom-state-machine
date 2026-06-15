package br.com.danilo.customstatemachine.integration;

import br.com.danilo.customstatemachine.runtime.StateMachine;
import br.com.danilo.customstatemachine.runtime.TransitionResult;
import br.com.danilo.customstatemachine.support.StateMachineFixtures;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class FailurePathIntegrationTest {

    @Test
    void preservesStateWhenTransitionIsRejectedOrMissing() {
        StateMachine machine = StateMachineFixtures.exampleMachine();

        TransitionResult invalidResult = machine.fire(StateMachineFixtures.REJECT);

        assertThat(invalidResult.status()).isEqualTo(TransitionResult.Status.INVALID_TRANSITION);
        assertThat(machine.currentState()).isEqualTo(StateMachineFixtures.DRAFT);

        machine.fire(StateMachineFixtures.SUBMIT);
        TransitionResult deniedResult = machine.fire(StateMachineFixtures.APPROVE, StateMachineFixtures.denialContext());

        assertThat(deniedResult.status()).isEqualTo(TransitionResult.Status.GUARD_DENIED);
        assertThat(machine.currentState()).isEqualTo(StateMachineFixtures.SUBMITTED);
    }
}