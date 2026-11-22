package local_search.acceptation_type;

import java.util.List;
import metaheurictics.strategy.Strategy;
import metaheuristics.generators.GeneratorType;
import metaheuristics.generators.MultiobjectiveHillClimbingDistance;
import problem.definition.Problem.ProblemType;
import problem.definition.State;

/** Compara soluciones y mantiene el conjunto de estados no dominados. */
public class Dominance {

    /**
     * Incorpora un candidato al frente de Pareto si aporta un código nuevo y no está dominado.
     *
     * @param solutionX estado candidato
     * @param list frente de Pareto que se actualiza
     * @return true si se incorpora el candidato
     */
    public boolean ListDominance(State solutionX, List<State> list) {
        for (State member : list) {
            if (dominance(member, solutionX) || solutionX.Comparator(member)) {
                return false;
            }
        }
        list.removeIf(member -> dominance(solutionX, member));
        list.add(solutionX.clone());

        Strategy strategy = Strategy.getStrategy();
        if (strategy.generator != null
                && strategy.generator.getType() == GeneratorType.MultiobjectiveHillClimbingDistance) {
            MultiobjectiveHillClimbingDistance.DistanceCalculateAdd(list);
        }
        return true;
    }

    /**
     * Comprueba si el primer estado mejora algún objetivo sin empeorar los demás.
     *
     * @param solutionX estado que se comprueba
     * @param solutionY estado de referencia
     * @return true si el primer estado domina al segundo
     */
    public boolean dominance(State solutionX, State solutionY) {
        boolean maximize = Strategy.getStrategy().getProblem().getTypeProblem() == ProblemType.Maximizar;
        List<Double> left = solutionX.getEvaluation();
        List<Double> right = solutionY.getEvaluation();
        if (left.size() != right.size()) {
            throw new IllegalArgumentException("Los estados deben tener la misma cantidad de objetivos");
        }

        boolean improved = false;
        for (int i = 0; i < left.size(); i++) {
            double first = left.get(i);
            double second = right.get(i);
            if (Double.isNaN(first) || Double.isNaN(second) || (maximize ? first < second : first > second)) {
                return false;
            }
            improved |= maximize ? first > second : first < second;
        }
        return improved;
    }
}
