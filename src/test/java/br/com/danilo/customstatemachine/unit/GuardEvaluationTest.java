package br.com.danilo.customstatemachine.unit;

import br.com.danilo.customstatemachine.model.StateMachineDefinition;
import br.com.danilo.customstatemachine.model.Transition;
import br.com.danilo.customstatemachine.runtime.StateMachine;
import br.com.danilo.customstatemachine.runtime.TransitionResult;
import br.com.danilo.customstatemachine.support.StateMachineFixtures;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class GuardEvaluationTest {

    @Test
    void approvesTransitionWhenGuardAcceptsContext() {
        StateMachine machine = StateMachineFixtures.exampleMachine();

        machine.fire(StateMachineFixtures.SUBMIT);
        TransitionResult result = machine.fire(StateMachineFixtures.APPROVE, StateMachineFixtures.approvalContext());

        assertThat(result.status()).isEqualTo(TransitionResult.Status.SUCCESS);
        assertThat(machine.currentState()).isEqualTo(StateMachineFixtures.APPROVED);
    }

    @Test
    void deniesTransitionWhenGuardRejectsContext() {
        StateMachineDefinition definition = new StateMachineDefinition(
            List.of(StateMachineFixtures.DRAFT, StateMachineFixtures.SUBMITTED, StateMachineFixtures.APPROVED),
            List.of(StateMachineFixtures.SUBMIT, StateMachineFixtures.APPROVE),
            List.of(
                new Transition(StateMachineFixtures.DRAFT, StateMachineFixtures.SUBMIT, StateMachineFixtures.SUBMITTED),
                new Transition(StateMachineFixtures.SUBMITTED, StateMachineFixtures.APPROVE, StateMachineFixtures.APPROVED,
                    context -> context.booleanAttribute("approved"))
            ),
            StateMachineFixtures.DRAFT
        );
        StateMachine machine = new StateMachine(definition);

        machine.fire(StateMachineFixtures.SUBMIT);
        TransitionResult result = machine.fire(StateMachineFixtures.APPROVE, StateMachineFixtures.denialContext());

        assertThat(result.status()).isEqualTo(TransitionResult.Status.GUARD_DENIED);
        assertThat(machine.currentState()).isEqualTo(StateMachineFixtures.SUBMITTED);
    }
}