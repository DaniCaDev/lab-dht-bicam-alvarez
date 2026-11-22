package problem.definition;

import java.util.List;

/** Define cómo propone soluciones un problema de optimización. */
public abstract class Operator {
	
		/**
		 * Genera un vecindario alrededor de un estado existente.
		 *
		 * @param stateCurrent estado actual de la búsqueda
		 * @param operatornumber parámetro de tamaño del operador
		 * @return estados candidatos del vecindario
		 */
		public abstract List<State> generatedNewState(State stateCurrent, Integer operatornumber);
		/**
		 * Genera candidatos iniciales en el dominio del problema.
		 *
		 * @param operatornumber parámetro de tamaño del operador
		 * @return lista de estados candidatos
		 */
		public abstract List<State> generateRandomState (Integer operatornumber);

	}

