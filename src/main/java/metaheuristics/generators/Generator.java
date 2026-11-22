package metaheuristics.generators;


import java.lang.reflect.InvocationTargetException;
import java.util.List;

import problem.definition.State;


/** Contrato de los generadores que proponen y seleccionan candidatos. */
public abstract class Generator {

	/**
	 * Genera el siguiente candidato.
	 *
	 * @param operatornumber número de candidatos que se solicita al operador
	 * @return estado propuesto por el generador
	 */
	public abstract State generate(Integer operatornumber) throws IllegalArgumentException, SecurityException, ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException;

	/**
	 * Actualiza la referencia del generador tras evaluar un candidato.
	 *
	 * @param stateCandidate candidato ya evaluado
	 * @param countIterationsCurrent índice de la iteración
	 */
	public abstract void updateReference(State stateCandidate, Integer countIterationsCurrent) throws IllegalArgumentException, SecurityException, ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException;

	/** @return estado que usa como referencia el generador. */
	public abstract State getReference();

	/** @param stateInitialRef estado de referencia al empezar la búsqueda. */
	public abstract void setInitialReference (State stateInitialRef);

	/** @return identificador del algoritmo de búsqueda. */
	public abstract GeneratorType getType ();

	/** @return referencias disponibles para el generador. */
	public abstract List<State> getReferenceList();

	/** @return descendientes disponibles en los algoritmos poblacionales. */
	public abstract List<State> getSonList ();

	/**
	 * Comprueba si procede actualizar la referencia premiada.
	 *
	 * @param stateCandidate candidato que se compara
	 * @return true si se produce la actualización
	 */
	public abstract boolean awardUpdateREF(State stateCandidate);

	/** @param weight nuevo peso del generador dentro del portafolio. */
	public abstract void setWeight(float weight);

	/** @return peso actual en el portafolio. */
	public abstract float getWeight();

	
	/** @return serie histórica de pesos, si está disponible. */
	public abstract float[] getTrace();
	public int countGender;
	public int countBetterGender;
	/** @return recuentos de mejoras por periodo. */
	public abstract int[] getListCountBetterGender();
	/** @return recuentos de usos del generador por periodo. */
	public abstract int[] getListCountGender();
	public int[] listCountBetterGender; // arreglo con las mejoras de cada generador en un periodo de 10, acumulativo
	

}
