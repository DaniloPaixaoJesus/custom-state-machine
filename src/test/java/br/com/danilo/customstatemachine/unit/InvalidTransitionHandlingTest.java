package br.com.danilo.customstatemachine.unit;

import br.com.danilo.customstatemachine.runtime.StateMachine;
import br.com.danilo.customstatemachine.runtime.TransitionResult;
import br.com.danilo.customstatemachine.support.StateMachineFixtures;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class InvalidTransitionHandlingTest {

    @Test
    void returnsInvalidTransitionAndPreservesCurrentState() {
        StateMachine machine = StateMachineFixtures.exampleMachine();
        machine.fire(StateMachineFixtures.SUBMIT);
        machine.fire(StateMachineFixtures.APPROVE, StateMachineFixtures.approvalContext());

        TransitionResult result = machine.fire(StateMachineFixtures.REJECT);

        assertThat(result.status()).isEqualTo(TransitionResult.Status.INVALID_TRANSITION);
        assertThat(machine.currentState()).isEqualTo(StateMachineFixtures.APPROVED);
    }
}