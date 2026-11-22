package local_search.acceptation_type;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import metaheurictics.strategy.Strategy;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import problem.definition.Problem;
import problem.definition.Problem.ProblemType;
import problem.definition.State;

class DominanceTest {
    private final Strategy strategy = Strategy.getStrategy();
    private final Dominance dominance = new Dominance();
    private Problem previous;
    private Problem problem;

    @BeforeEach
    void setup() {
        previous = strategy.getProblem();
        problem = new Problem();
        problem.setTypeProblem(ProblemType.Maximizar);
        strategy.setProblem(problem);
    }

    @AfterEach
    void restoreProblem() {
        strategy.setProblem(previous);
    }

    @Test
    void comparesAllObjectivesInBothDirections() {
        State first = state(1, 4.0, 2.0);
        State second = state(2, 3.0, 2.0);
        assertTrue(dominance.dominance(first, second));
        assertFalse(dominance.dominance(first, first));
        assertFalse(dominance.dominance(first, state(3, 2.0, 3.0)));
        problem.setTypeProblem(ProblemType.Minimizar);
        assertTrue(dominance.dominance(second, first));
        assertFalse(dominance.dominance(first, second));
    }

    @Test
    void handlesIncompleteOrInvalidObjectiveValues() {
        assertThrows(IllegalArgumentException.class,
                () -> dominance.dominance(state(1, 1.0), state(2, 2.0, 3.0)));
        assertFalse(dominance.dominance(state(1, Double.NaN), state(2, 3.0)));
    }

    @Test
    void removesAllDominatedMembersAndStoresAnIndependentCandidate() {
        List<State> front = new ArrayList<>(List.of(state(1, 2.0, 1.0), state(2, 1.0, 2.0)));
        State candidate = state(3, 3.0, 3.0);
        assertTrue(dominance.ListDominance(candidate, front));
        assertEquals(1, front.size());
        assertTrue(candidate.Comparator(front.get(0)));
        assertNotSame(candidate, front.get(0));
        assertFalse(dominance.ListDominance(candidate, front));
        assertFalse(dominance.ListDominance(state(4, 1.0, 1.0), front));
    }

    private static State state(int value, Double... objectives) {
        State state = new State(new ArrayList<>(List.of(value)));
        state.setEvaluation(new ArrayList<>(List.of(objectives)));
        return state;
    }
}
