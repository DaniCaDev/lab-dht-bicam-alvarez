package local_search.acceptation_type;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import metaheurictics.strategy.Strategy;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import problem.definition.Problem;
import problem.definition.Problem.ProblemType;
import problem.definition.State;

class AcceptBestTest {
    @ParameterizedTest
    @CsvSource({
        "Maximizar, 12, true", "Maximizar, 10, true", "Maximizar, 8, false",
        "Minimizar, 8, true", "Minimizar, 10, true", "Minimizar, 12, false"
    })
    void acceptsTiesAndImprovements(ProblemType type, double candidate, boolean expected)
            throws ReflectiveOperationException {
        Strategy strategy = Strategy.getStrategy();
        Problem previous = strategy.getProblem();
        Problem problem = new Problem();
        problem.setTypeProblem(type);
        strategy.setProblem(problem);
        try {
            assertEquals(expected, new AcceptBest().acceptCandidate(state(10), state(candidate)));
        } finally {
            strategy.setProblem(previous);
        }
    }

    private static State state(double evaluation) {
        State state = new State();
        state.setEvaluation(new ArrayList<>(List.of(evaluation)));
        return state;
    }
}
