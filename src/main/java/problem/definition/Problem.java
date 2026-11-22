package problem.definition;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import problem.extension.SolutionMethod;
import problem.extension.TypeSolutionMethod;


import factory_interface.IFFactorySolutionMethod;
import factory_method.FactorySolutionMethod;


/** Define los objetivos, el operador y la configuración de un problema de optimización. */
public class Problem {

	/** Determina si la búsqueda maximiza o minimiza la evaluación. */
	public enum ProblemType {Maximizar,Minimizar;}

	/** Funciones objetivo en el orden en que se evalúan. */
	private ArrayList<ObjetiveFunction> function;
	/** Estado inicial que sirve de referencia. */
	private State state;
	/** Sentido de optimización que usan los operadores. */
	private ProblemType typeProblem;
	/** Dominio y representación de las variables. */
	private Codification codification;
	/** Operador que genera nuevos estados. */
	private Operator operator;
	/** Cantidad de valores permitidos en el dominio discreto. */
	private int possibleValue;
	/** Método de combinación de los objetivos, si se configura. */
	private TypeSolutionMethod typeSolutionMethod;
	/** Fábrica que crea los métodos de evaluación. */
	private IFFactorySolutionMethod factorySolutionMethod;
	
	/** Crea un problema que puede configurarse antes de empezar la búsqueda. */
	public Problem() {
		super();
	}

	/** @return objetivos del problema en su orden de evaluación. */
	public ArrayList<ObjetiveFunction> getFunction() {
		return function;
	}

	/** @param function objetivos en el orden en que se evaluarán. */
	public void setFunction(ArrayList<ObjetiveFunction> function) {
		this.function = function;
	}

	/** @return estado inicial del problema. */
	public State getState() {
		return state;
	}

	/** @param state estado inicial que actuará como referencia. */
	public void setState(State state) {
		this.state = state;
	}

	/** @return sentido de optimización. */
	public ProblemType getTypeProblem() {
		return typeProblem;
	}
	/** @param typeProblem sentido de optimización. */
	public void setTypeProblem(ProblemType typeProblem) {
		this.typeProblem = typeProblem;
	}

	/** @return representación de las variables de una solución. */
	public Codification getCodification() {
		return codification;
	}
	/** @param codification representación de las variables. */
	public void setCodification(Codification codification) {
		this.codification = codification;
	}

	/** @return operador de generación y vecindad. */
	public Operator getOperator() {
		return operator;
	}

	/** @param operator operador de generación y vecindad. */
	public void setOperator(Operator operator) {
		this.operator = operator;
	}

	/** @return cantidad de valores del dominio discreto. */
	public int getPossibleValue() {
		return possibleValue;
	}

	/** @param possibleValue cantidad de valores del dominio discreto. */
	public void setPossibleValue(int possibleValue) {
		this.possibleValue = possibleValue;
	}

	/**
	 * Evalúa un estado y guarda el resultado.
	 *
	 * @param state estado que se desea evaluar
	 */
	public void Evaluate(State state) throws IllegalArgumentException, SecurityException, ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		double eval = 0;       
		ArrayList<Double> evaluation = new ArrayList<Double>(this.function.size());
		if (typeSolutionMethod == null) {
			eval= function.get(0).Evaluation(state);
			evaluation.add(evaluation.size(), eval);
			state.setEvaluation(evaluation);
		}
		else {
			SolutionMethod method = newSolutionMethod(typeSolutionMethod);
			method.evaluationState(state);
		}
	}
	
	/** @return método configurado para combinar objetivos. */
	public TypeSolutionMethod getTypeSolutionMethod() {
		return typeSolutionMethod;
	}
	/** @param typeSolutionMethod método para combinar objetivos. */
	public void setTypeSolutionMethod(TypeSolutionMethod typeSolutionMethod) {
		this.typeSolutionMethod = typeSolutionMethod;
	}
	/** @return última fábrica de métodos configurada. */
	public IFFactorySolutionMethod getFactorySolutionMethod() {
		return factorySolutionMethod;
	}
	/** @param factorySolutionMethod fábrica de métodos de evaluación. */
	public void setFactorySolutionMethod(
			IFFactorySolutionMethod factorySolutionMethod) {
		this.factorySolutionMethod = factorySolutionMethod;
	}
	
	/**
	 * Crea el método de evaluación seleccionado.
	 *
	 * @param typeSolutionMethod tipo de método
	 * @return instancia del método solicitado
	 */
	public SolutionMethod newSolutionMethod(TypeSolutionMethod typeSolutionMethod) throws IllegalArgumentException, SecurityException, ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		factorySolutionMethod = new FactorySolutionMethod();
		SolutionMethod solutionMethod = factorySolutionMethod.createdSolutionMethod(typeSolutionMethod);
		return solutionMethod;
	}
}

	
	

