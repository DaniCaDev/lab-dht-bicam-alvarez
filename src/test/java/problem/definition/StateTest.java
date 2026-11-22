package problem.definition;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import metaheuristics.generators.GeneratorType;
import org.junit.jupiter.api.Test;

class StateTest {
    @Test
    void startsWithEmptyCodeAndNoEvaluation() {
        State state = new State();
        assertTrue(state.getCode().isEmpty());
        assertNull(state.getEvaluation());
    }

    @Test
    void copiesInputListsAndPreservesMutableState() {
        ArrayList<Object> code = new ArrayList<>(List.of("A", "B"));
        ArrayList<Double> evaluation = new ArrayList<>(List.of(2.0));
        State state = new State(code);
        state.setEvaluation(evaluation);

        code.set(0, "X");
        evaluation.set(0, 9.0);
        assertEquals(List.of("A", "B"), state.getCode());
        assertEquals(List.of(2.0), state.getEvaluation());
        state.getCode().set(0, "C");
        assertEquals(List.of("C", "B"), state.getCode());
    }

    @Test
    void clonesAndCopyConstructsWithIndependentLists() {
        State original = new State(new ArrayList<>(List.of(5)));
        original.setNumber(7);
        original.setTypeGenerator(GeneratorType.RandomSearch);
        original.setEvaluation(new ArrayList<>(List.of(3.0)));

        for (State copy : List.of(new State(original), original.clone())) {
            assertNotSame(original, copy);
            assertTrue(original.Comparator(copy));
            assertEquals(7, copy.getNumber());
            assertEquals(GeneratorType.RandomSearch, copy.getTypeGenerator());
            copy.getCode().set(0, 8);
            copy.getEvaluation().set(0, 4.0);
            assertFalse(original.Comparator(copy));
        }
        assertEquals(List.of(5), original.getCode());
        assertEquals(List.of(3.0), original.getEvaluation());
    }

    @Test
    void preparesAnIndependentUnevaluatedCandidate() {
        State original = new State(new ArrayList<>(List.of(5)));
        original.setEvaluation(new ArrayList<>(List.of(2.0)));
        State copy = (State) original.getCopy();
        copy.getCode().set(0, 9);
        assertEquals(List.of(5), original.getCode());
        assertNull(copy.getEvaluation());
        assertNull(new State(copy).getEvaluation());
    }

    @Test
    void countsDifferencesAndRejectsDifferentDimensions() {
        State first = new State(new ArrayList<>(Arrays.asList(null, "A", "B")));
        State second = new State(new ArrayList<>(Arrays.asList(null, "B", "A")));
        assertEquals(2.0, first.Distance(second));
        assertEquals(0.0, first.Distance(first));
        assertThrows(IllegalArgumentException.class, () -> first.Distance(new State()));
    }
}
