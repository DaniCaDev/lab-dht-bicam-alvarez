package problem.definition;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertSame;

import factory_method.FactorySolutionMethod;
import java.util.ArrayList;
import java.util.List;
import metaheurictics.strategy.Strategy;
import org.junit.jupiter.api.Test;
import problem.extension.FactoresPonderados;
import problem.extension.TypeSolutionMethod;

class ProblemTest {
    @Test
    void evaluatesTheFirstObjectiveByDefault() throws ReflectiveOperationException {
        Problem problem = problemWithObjectives();
        State state = new State(new ArrayList<>(List.of(4)));
        problem.Evaluate(state);
        assertEquals(List.of(4.0), state.getEvaluation());
    }

    @Test
    void evaluatesMultipleObjectivesUsingTheSelectedMethod() throws ReflectiveOperationException {
        Problem problem = problemWithObjectives();
        problem.setTypeSolutionMethod(TypeSolutionMethod.MultiObjetivoPuro);
        Strategy strategy = Strategy.getStrategy();
        Problem previous = strategy.getProblem();
        strategy.setProblem(problem);
        try {
            State state = new State(new ArrayList<>(List.of(4)));
            problem.Evaluate(state);
            assertEquals(List.of(4.0, 8.0), state.getEvaluation());
            assertInstanceOf(FactorySolutionMethod.class, problem.getFactorySolutionMethod());
        } finally {
            strategy.setProblem(previous);
        }
    }

    @Test
    void configuresStateAndSearchOperator() {
        Problem problem = problemWithObjectives();
        State state = new State();
        Operator operator = new Operator() {
            @Override
            public List<State> generatedNewState(State current, Integer count) {
                return List.of(current);
            }

            @Override
            public List<State> generateRandomState(Integer count) {
                return List.of(state);
            }
        };
        Codification codification = new Codification() {
            public boolean validState(State candidate) { return candidate.getCode().size() == 1; }
            public Object getVariableAleatoryValue(int key) { return 0; }
            public int getAleatoryKey() { return 0; }
            public int getVariableCount() { return 1; }
        };
        problem.setState(state);
        problem.setOperator(operator);
        problem.setCodification(codification);
        problem.setPossibleValue(10);

        assertEquals(2, problem.getFunction().size());
        assertSame(state, problem.getState());
        assertSame(operator, problem.getOperator());
        assertSame(codification, problem.getCodification());
        assertEquals(10, problem.getPossibleValue());
        assertEquals(Problem.ProblemType.Maximizar, problem.getTypeProblem());
    }

    @Test
    void createsTheRequestedSolutionMethod() throws ReflectiveOperationException {
        Problem problem = new Problem();
        problem.setTypeSolutionMethod(TypeSolutionMethod.FactoresPonderados);
        assertInstanceOf(FactoresPonderados.class, problem.newSolutionMethod(problem.getTypeSolutionMethod()));
    }

    private static Problem problemWithObjectives() {
        Problem problem = new Problem();
        problem.setTypeProblem(Problem.ProblemType.Maximizar);
        problem.setFunction(new ArrayList<>(List.of(objective(1), objective(2))));
        return problem;
    }

    private static ObjetiveFunction objective(int multiplier) {
        ObjetiveFunction function = new ObjetiveFunction() {
            @Override
            public Double Evaluation(State state) {
                return ((Integer) state.getCode().get(0)).doubleValue() * multiplier;
            }
        };
        function.setTypeProblem(Problem.ProblemType.Maximizar);
        return function;
    }
}
