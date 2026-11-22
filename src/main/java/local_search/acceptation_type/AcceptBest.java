package local_search.acceptation_type;

import metaheurictics.strategy.Strategy;
import problem.definition.Problem.ProblemType;
import problem.definition.State;

/** Acepta candidatos que igualan o mejoran la evaluación del estado actual. */
public class AcceptBest extends AcceptableCandidate {
    /**
     * Compara el primer objetivo de ambos estados según el sentido del problema.
     *
     * @param stateCurrent estado que sirve de referencia
     * @param stateCandidate estado que se pretende aceptar
     * @return true si el candidato mantiene o mejora la evaluación
     */
    @Override
    public Boolean acceptCandidate(State stateCurrent, State stateCandidate) {
        double current = stateCurrent.getEvaluation().get(0);
        double candidate = stateCandidate.getEvaluation().get(0);
        return Strategy.getStrategy().getProblem().getTypeProblem() == ProblemType.Maximizar
                ? candidate >= current : candidate <= current;
    }
}
