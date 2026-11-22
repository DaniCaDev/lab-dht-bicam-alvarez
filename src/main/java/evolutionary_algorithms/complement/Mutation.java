package evolutionary_algorithms.complement;

import problem.definition.State;

/** Operación que varía el código de una solución candidata. */
public abstract class Mutation {
	
	/**
	 * @param state candidato de entrada
	 * @param PM parámetro de probabilidad de mutación
	 * @return estado resultante de la operación
	 */
	public abstract State mutation (State state, double PM);

}
