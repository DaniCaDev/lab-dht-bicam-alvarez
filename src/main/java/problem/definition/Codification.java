package problem.definition;

/** Define la representación y el dominio de las variables de una solución. */
public abstract class Codification {

	/**
	 * Comprueba las restricciones de la representación.
	 *
	 * @param state estado candidato
	 * @return true si se cumplen las restricciones
	 */
	public abstract boolean validState(State state);
//	public abstract int getVariableDomain(int variable);
	/**
	 * Muestrea el dominio de una variable.
	 *
	 * @param key índice de la variable
	 * @return valor permitido para la variable
	 */
	public abstract Object getVariableAleatoryValue(int key);
	/** @return índice de una variable elegida al azar. */
	public abstract int getAleatoryKey ();
	/** @return cantidad de variables que forman una solución. */
	public abstract int getVariableCount();

}