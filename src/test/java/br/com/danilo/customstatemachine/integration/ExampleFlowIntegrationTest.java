package br.com.danilo.customstatemachine.integration;

import br.com.danilo.customstatemachine.runtime.StateMachine;
import br.com.danilo.customstatemachine.runtime.TransitionResult;
import br.com.danilo.customstatemachine.support.StateMachineFixtures;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ExampleFlowIntegrationTest {

    @Test
    void executesExampleFlowFromDraftToApproved() {
        StateMachine machine = StateMachineFixtures.exampleMachine();

        TransitionResult submitResult = machine.fire(StateMachineFixtures.SUBMIT);
        TransitionResult approveResult = machine.fire(StateMachineFixtures.APPROVE, StateMachineFixtures.approvalContext());

        assertThat(submitResult.status()).isEqualTo(TransitionResult.Status.SUCCESS);
        assertThat(approveResult.status()).isEqualTo(TransitionResult.Status.SUCCESS);
        assertThat(machine.currentState()).isEqualTo(StateMachineFixtures.APPROVED);
    }
}