package evolutionary_algorithms.complement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import problem.definition.State;

class OnePointCrossoverTest {
    private final OnePointCrossover crossover = new OnePointCrossover();

    @Test
    void producesAnIndependentCopyWhenNoCrossoverOccurs() {
        State first = state(1, 1);
        State result = crossover.crossover(first, state(2, 2), 0.0);
        assertEquals(first.getCode(), result.getCode());
        result.getCode().set(0, 2);
        assertEquals(List.of(1, 1), first.getCode());
        assertEquals(List.of(1), crossover.crossover(state(1), state(2), 1.0).getCode());
    }

    @Test
    void crossesTwoParentsAtAnInternalPoint() {
        State first = state(1, 1, 1);
        State second = state(2, 2, 2);
        List<Object> result = crossover.crossover(first, second, 1.0).getCode();
        assertTrue(List.of(List.of(1, 1, 2), List.of(1, 2, 2), List.of(2, 1, 1), List.of(2, 2, 1))
                .contains(result));
        assertEquals(List.of(1, 1, 1), first.getCode());
        assertEquals(List.of(2, 2, 2), second.getCode());
    }

    @Test
    void rejectsIncompatibleParents() {
        assertThrows(IllegalArgumentException.class, () -> crossover.crossover(state(1), state(2, 2), 1.0));
    }

    private static State state(Integer... values) {
        return new State(new ArrayList<>(List.of(values)));
    }
}
