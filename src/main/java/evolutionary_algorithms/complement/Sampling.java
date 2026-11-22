package evolutionary_algorithms.complement;

import java.util.List;

import problem.definition.State;


/** Genera nuevos candidatos a partir de una población de estados. */
public abstract class Sampling {
	/**
	 * @param fathers estados usados como muestra de referencia
	 * @param countInd número de individuos solicitados
	 * @return candidatos generados
	 */
	public abstract List<State> sampling (List<State> fathers, int countInd);
}
