package biciam;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.ToDoubleFunction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import evolutionary_algorithms.complement.Univariate;
import local_search.candidate_type.CandidateType;
import local_search.candidate_type.CandidateValue;
import local_search.complement.StopExecute;
import local_search.complement.StrategyType;
import metaheurictics.strategy.Strategy;
import metaheuristics.generators.DistributionEstimationAlgorithm;
import metaheuristics.generators.GeneratorType;
import metaheuristics.generators.HillClimbingRestart;
import metaheuristics.generators.RandomSearch;
import problem.definition.Codification;
import problem.definition.ObjetiveFunction;
import problem.definition.Operator;
import problem.definition.Problem;
import problem.definition.State;
import problem.extension.MetricasMultiobjetivo;
import problem_operators.MutationOperator;

class ReliabilityRegressionTest {
    private Strategy strategy;
    private int previousRestartCount;
    private int previousRestartInterval;

    @BeforeEach
    void prepareStrategy() {
        previousRestartCount = HillClimbingRestart.count;
        previousRestartInterval = HillClimbingRestart.countCurrent;
        Strategy.destroyExecute();
        strategy = Strategy.getStrategy();
        strategy.mapGenerators = new TreeMap<>();
    }

    @AfterEach
    void resetStrategy() {
        Strategy.destroyExecute();
        HillClimbingRestart.count = previousRestartCount;
        HillClimbingRestart.countCurrent = previousRestartInterval;
    }

    @Test
    void candidateSelectionRejectsMissingCandidatesAndPreservesAValidCandidate() throws Exception {
        CandidateValue selector = new CandidateValue();
        State initial = state(1);

        assertThrows(IllegalArgumentException.class,
                () -> selector.stateCandidate(initial, CandidateType.GreaterCandidate, StrategyType.NORMAL, 1, null));
        assertThrows(IllegalArgumentException.class,
                () -> selector.stateCandidate(initial, CandidateType.GreaterCandidate, StrategyType.NORMAL, 1, List.of()));
        assertSame(initial,
                selector.stateCandidate(initial, CandidateType.GreaterCandidate, StrategyType.NORMAL, 1, List.of(initial)));
    }

    @Test
    void randomSearchReceivesValidCandidatesFromTheMutationOperator() throws Exception {
        AtomicInteger firstVariable = new AtomicInteger();
        AtomicBoolean allowValidStates = new AtomicBoolean(true);
        MutationOperator operator = new MutationOperator();
        Problem problem = problem(operator, sample -> (Integer) sample.getCode().get(0));
        problem.setCodification(new Codification() {
            @Override
            public boolean validState(State candidate) {
                return allowValidStates.get() && (Integer) candidate.getCode().get(0) > 1;
            }

            @Override
            public Object getVariableAleatoryValue(int key) {
                return key == 0 ? firstVariable.incrementAndGet() : 37;
            }

            @Override
            public int getAleatoryKey() {
                return 0;
            }

            @Override
            public int getVariableCount() {
                return 2;
            }
        });
        strategy.setProblem(problem);

        assertEquals(List.of(2, 37), new RandomSearch().generate(1).getCode());
        assertEquals(List.of(List.of(3, 37), List.of(4, 37)),
                operator.generateRandomState(2).stream().map(State::getCode).toList());
        assertEquals(List.of(), operator.generateRandomState(0));
        assertThrows(IllegalArgumentException.class, () -> operator.generateRandomState(-1));
        assertThrows(IllegalArgumentException.class, () -> operator.generateRandomState(null));
        allowValidStates.set(false);
        assertThrows(IllegalStateException.class, () -> operator.generateRandomState(1));
    }

    @Test
    void maximumRequiresAnObjectiveAndReturnsACopyOfTheBestCandidate() {
        DistributionEstimationAlgorithm algorithm = new DistributionEstimationAlgorithm();
        State best = evaluatedState(9);
        State result = algorithm.MaxValue(List.of(evaluatedState(2), best, evaluatedState(4)));

        assertNotSame(best, result);
        assertEquals(best.getCode(), result.getCode());
        assertEquals(best.getEvaluation(), result.getEvaluation());
        assertThrows(IllegalArgumentException.class, () -> algorithm.MaxValue(null));
        assertThrows(IllegalArgumentException.class, () -> algorithm.MaxValue(List.of()));
        assertThrows(IllegalArgumentException.class, () -> algorithm.MaxValue(Collections.singletonList(null)));
        assertThrows(IllegalArgumentException.class, () -> algorithm.MaxValue(List.of(state(3))));

        State pending = state(5);
        pending.setEvaluation(new ArrayList<>());
        assertThrows(IllegalArgumentException.class, () -> algorithm.MaxValue(List.of(pending)));
        pending.setEvaluation(new ArrayList<>(Collections.singletonList(null)));
        assertThrows(IllegalArgumentException.class, () -> algorithm.MaxValue(List.of(pending)));
    }

    @Test
    void distributionEvaluatesEveryCandidateBeforeSelectingTheMaximum() throws Exception {
        AtomicInteger objectiveCalls = new AtomicInteger();
        Problem problem = problem(new FixedSamples(List.of()), sample -> objectiveCalls.incrementAndGet());
        problem.setPossibleValue(2);
        strategy.setProblem(problem);
        DistributionEstimationAlgorithm algorithm = new DistributionEstimationAlgorithm() {
            @Override
            public List<State> getfathersList() {
                return List.of(state(1));
            }
        };

        assertThrows(IllegalStateException.class, () -> algorithm.generate(0));
        assertNull(algorithm.generate(1).getEvaluation());
        assertEquals(List.of(3.0), algorithm.generate(3).getEvaluation());
        assertEquals(3, objectiveCalls.get());
    }

    @Test
    void failedRestartKeepsThePreviousReferenceAndValidRestartEvaluatesTheNewOne() throws Exception {
        Problem problem = problem(new FixedSamples(null), sample -> (Integer) sample.getCode().get(0));
        strategy.setProblem(problem);
        strategy.setCountCurrent(1);
        HillClimbingRestart.count = 1;
        HillClimbingRestart algorithm = new HillClimbingRestart();
        State previousReference = evaluatedState(2);
        algorithm.setInitialReference(previousReference);

        assertThrows(IllegalStateException.class, () -> algorithm.generate(1));
        problem.setOperator(new FixedSamples(List.of()));
        assertThrows(IllegalStateException.class, () -> algorithm.generate(1));
        problem.setOperator(new FixedSamples(Collections.singletonList(null)));
        assertThrows(IllegalStateException.class, () -> algorithm.generate(1));
        assertSame(previousReference, algorithm.getReference());

        problem.setOperator(new FixedSamples(List.of(state(11))));
        assertEquals(List.of(12), algorithm.generate(1).getCode());
        assertEquals(List.of(11.0), algorithm.getReference().getEvaluation());
        assertEquals(List.of(12), algorithm.generate(1).getCode());
    }

    @Test
    void strategyUpdatesTheIterationAndPreservesItsNumericSettings() throws Exception {
        strategy.setProblem(problem(new FixedSamples(List.of(state(4))), sample -> (Integer) sample.getCode().get(0)));
        strategy.setStopexecute(new StopExecute());
        strategy.setThreshold(0.625);
        strategy.setCountMax(3);
        assertEquals(0.625, strategy.getThreshold());
        assertEquals(3, strategy.getCountMax());

        strategy.executeStrategy(1, 10, 1, GeneratorType.RandomSearch);

        assertEquals(1, strategy.getCountCurrent());
        assertEquals(1, strategy.getCountMax());
        assertEquals(10, strategy.countPeriodChange);
        assertEquals(List.of(4.0), strategy.getBestState().getEvaluation());
    }

    @Test
    void distributionAndParetoRateKeepFractionalResults() throws Exception {
        var probabilities = new Univariate().distribution(List.of(state(1), state(1), state(2)));
        assertEquals(2f / 3f, probabilities.get(0).getProbability(), 0.000001f);
        double rate = new MetricasMultiobjetivo().TasaError(
                List.of(evaluatedState(1), evaluatedState(2), evaluatedState(3)),
                List.of(evaluatedState(1), evaluatedState(2)));
        assertEquals(1d / 3d, rate, 0.000000001);
    }

    private static State state(int code) {
        return new State(new ArrayList<>(List.of(code)));
    }

    private static State evaluatedState(int code) {
        State state = state(code);
        state.setEvaluation(new ArrayList<>(List.of((double) code)));
        return state;
    }

    private static Problem problem(Operator operator, ToDoubleFunction<State> objective) {
        Problem problem = new Problem();
        problem.setTypeProblem(Problem.ProblemType.Maximizar);
        problem.setOperator(operator);
        problem.setFunction(new ArrayList<>(List.of(new ObjetiveFunction() {
            @Override
            public Double Evaluation(State sample) {
                return objective.applyAsDouble(sample);
            }
        })));
        return problem;
    }

    private static final class FixedSamples extends Operator {
        private final List<State> samples;

        FixedSamples(List<State> samples) {
            this.samples = samples;
        }

        @Override
        public List<State> generateRandomState(Integer count) {
            return samples;
        }

        @Override
        public List<State> generatedNewState(State current, Integer count) {
            return List.of(state(12));
        }
    }
}
