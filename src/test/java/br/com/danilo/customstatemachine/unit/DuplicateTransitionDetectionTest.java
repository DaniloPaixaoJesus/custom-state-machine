package br.com.danilo.customstatemachine.unit;

import br.com.danilo.customstatemachine.model.StateMachineDefinition;
import br.com.danilo.customstatemachine.model.Transition;
import br.com.danilo.customstatemachine.support.StateMachineFixtures;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class DuplicateTransitionDetectionTest {

    @Test
    void rejectsDuplicateTransitionForSameSourceStateAndEvent() {
        Transition first = new Transition(StateMachineFixtures.DRAFT, StateMachineFixtures.SUBMIT, StateMachineFixtures.SUBMITTED);
        Transition duplicate = new Transition(StateMachineFixtures.DRAFT, StateMachineFixtures.SUBMIT, StateMachineFixtures.REJECTED);

        assertThatThrownBy(() -> new StateMachineDefinition(
            List.of(StateMachineFixtures.DRAFT, StateMachineFixtures.SUBMITTED, StateMachineFixtures.REJECTED),
            List.of(StateMachineFixtures.SUBMIT),
            List.of(first, duplicate),
            StateMachineFixtures.DRAFT
        ))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("duplicate transition");
    }
}