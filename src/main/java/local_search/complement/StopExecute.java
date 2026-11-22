package local_search.complement;

/** Decide cuándo se ha alcanzado el límite de iteraciones. */
public class StopExecute {
    /**
     * @param countIterationsCurrent número de iteraciones realizadas
     * @param countmaxIterations máximo de iteraciones
     * @return true cuando se alcanza o supera el máximo
     */
    public Boolean stopIterations(int countIterationsCurrent, int countmaxIterations) {
        return countIterationsCurrent >= countmaxIterations;
    }
}
