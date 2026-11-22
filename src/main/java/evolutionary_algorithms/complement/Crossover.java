package evolutionary_algorithms.complement;


import problem.definition.State;

/** Operación que combina el código de dos soluciones progenitoras. */
public abstract class Crossover {
	
	/**
	 * @param father1 primer progenitor
	 * @param father2 segundo progenitor
	 * @param PC parámetro de probabilidad de cruce
	 * @return código descendiente representado como estado
	 */
	public abstract State crossover(State father1, State father2, double PC);
}
