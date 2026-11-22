package problem.definition;

import problem.definition.Problem.ProblemType;

/** Función objetivo que puntúa una solución y permite asignarle un peso. */
public abstract class ObjetiveFunction {
	
	private ProblemType typeProblem;
	private float weight;
	
	public float getWeight() {
		return weight;
	}

	public void setWeight(float weight) {
		this.weight = weight;
	}

	public ProblemType getTypeProblem() {
		return typeProblem;
	}

	public void setTypeProblem(ProblemType typeProblem) {
		this.typeProblem = typeProblem;
	}

	/**
	 * Evalúa el código de una solución.
	 *
	 * @param state estado que se desea puntuar
	 * @return valor de la función objetivo
	 */
	public abstract Double Evaluation(State state);
}
