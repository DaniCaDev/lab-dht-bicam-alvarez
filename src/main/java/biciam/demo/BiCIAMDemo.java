package biciam.demo;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import metaheurictics.strategy.Strategy;
import metaheuristics.generators.GeneratorType;
import metaheuristics.generators.RandomSearch;
import problem.definition.ObjetiveFunction;
import problem.definition.Operator;
import problem.definition.Problem;
import problem.definition.State;

/** Ejecuta una búsqueda de ejemplo para comprobar la instalación de BiCIAM. */
public final class BiCIAMDemo {
    private BiCIAMDemo() {
    }

    /**
     * Minimiza (x - 3)² con RandomSearch para enteros de -5 a 5.
     *
     * @param args argumentos de consola, no utilizados
     */
    public static void main(String[] args) {
        try {
            State best = search();
            System.out.printf(Locale.ROOT, "BiCIAM | RandomSearch | x=%s | f(x)=%.1f%n",
                    best.getCode().get(0), best.getEvaluation().get(0));
        } catch (ReflectiveOperationException | RuntimeException e) {
            System.err.println("BiCIAM: no se pudo ejecutar la búsqueda de ejemplo.");
            System.exit(1);
        }
    }

    private static State search() throws ReflectiveOperationException {
        Strategy strategy = Strategy.getStrategy();
        Problem problem = new Problem();
        problem.setTypeProblem(Problem.ProblemType.Minimizar);
        problem.setFunction(new ArrayList<>(List.of(new SquaredDistance())));
        problem.setOperator(new IntegerSamples());
        strategy.setProblem(problem);

        RandomSearch search = new RandomSearch();
        State first = search.generate(1);
        problem.Evaluate(first);
        search.setInitialReference(first);

        for (int iteration = 1; iteration <= 10; iteration++) {
            State candidate = search.generate(1);
            candidate.setTypeGenerator(GeneratorType.RandomSearch);
            problem.Evaluate(candidate);
            search.updateReference(candidate, iteration);
        }
        return search.getReference();
    }

    private static final class SquaredDistance extends ObjetiveFunction {
        @Override
        public Double Evaluation(State state) {
            int difference = (Integer) state.getCode().get(0) - 3;
            return (double) difference * difference;
        }
    }

    /** Entrega una muestra por iteración para que el resultado de la demo sea comprobable. */
    private static final class IntegerSamples extends Operator {
        private int next = -5;

        @Override
        public List<State> generatedNewState(State current, Integer count) {
            return generateRandomState(count);
        }

        @Override
        public List<State> generateRandomState(Integer count) {
            return List.of(new State(new ArrayList<>(List.of(next++))));
        }
    }
}
