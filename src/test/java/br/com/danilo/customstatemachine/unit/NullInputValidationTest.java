package br.com.danilo.customstatemachine.unit;

import br.com.danilo.customstatemachine.model.StateMachineDefinition;
import br.com.danilo.customstatemachine.runtime.StateMachine;
import br.com.danilo.customstatemachine.runtime.TransitionResult;
import br.com.danilo.customstatemachine.support.StateMachineFixtures;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class NullInputValidationTest {

    @Test
    void returnsValidationErrorWhenRuntimeInputsAreNull() {
        StateMachine machine = StateMachineFixtures.exampleMachine();

        TransitionResult nullEventResult = machine.fire(null, StateMachineFixtures.approvalContext());
        TransitionResult nullContextResult = machine.fire(StateMachineFixtures.SUBMIT, null);

        assertThat(nullEventResult.status()).isEqualTo(TransitionResult.Status.VALIDATION_ERROR);
        assertThat(nullContextResult.status()).isEqualTo(TransitionResult.Status.VALIDATION_ERROR);
        assertThat(machine.currentState()).isEqualTo(StateMachineFixtures.DRAFT);
    }

    @Test
    void rejectsNullInputsWhenBuildingDefinition() {
        assertThatThrownBy(() -> new StateMachineDefinition(
            null,
            List.of(StateMachineFixtures.SUBMIT),
            List.of(),
            StateMachineFixtures.DRAFT
        )).isInstanceOf(NullPointerException.class);

        assertThatThrownBy(() -> new StateMachine(null))
            .isInstanceOf(NullPointerException.class);
    }
}