package br.com.danilo.customstatemachine.unit;

import br.com.danilo.customstatemachine.model.Event;
import br.com.danilo.customstatemachine.model.State;
import br.com.danilo.customstatemachine.model.StateMachineDefinition;
import br.com.danilo.customstatemachine.model.Transition;
import br.com.danilo.customstatemachine.support.StateMachineFixtures;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class DefinitionValidationTest {

    @Test
    void createsImmutableDefinitionWithValidInitialState() {
        StateMachineDefinition definition = StateMachineFixtures.exampleDefinition();

        assertThat(definition.states()).contains(StateMachineFixtures.DRAFT);
        assertThatThrownBy(() -> definition.states().add(new State("OTHER")))
            .isInstanceOf(UnsupportedOperationException.class);
        assertThatThrownBy(() -> definition.events().add(new Event("OTHER")))
            .isInstanceOf(UnsupportedOperationException.class);
        assertThatThrownBy(() -> definition.transitions().add(new Transition(StateMachineFixtures.DRAFT, StateMachineFixtures.SUBMIT, StateMachineFixtures.SUBMITTED)))
            .isInstanceOf(UnsupportedOperationException.class);
    }

    @Test
    void rejectsInitialStateThatIsNotDeclared() {
        assertThatThrownBy(() -> new StateMachineDefinition(
            List.of(StateMachineFixtures.DRAFT),
            List.of(StateMachineFixtures.SUBMIT),
            List.of(new Transition(StateMachineFixtures.DRAFT, StateMachineFixtures.SUBMIT, StateMachineFixtures.DRAFT)),
            StateMachineFixtures.SUBMITTED
        ))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("initial state");
    }
}