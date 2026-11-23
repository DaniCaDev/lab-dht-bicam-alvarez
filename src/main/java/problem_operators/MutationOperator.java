package problem_operators;

import java.util.ArrayList;
import java.util.List;

import metaheurictics.strategy.Strategy;

import problem.definition.Codification;
import problem.definition.Operator;
import problem.definition.State;


public class MutationOperator extends Operator {

	private static final int MAX_SAMPLE_ATTEMPTS = 100;

	public List<State> generatedNewState(State stateCurrent, Integer operatornumber){
		List<State> listNeigborhood = new ArrayList<State>();
		for (int i = 0; i < operatornumber; i++){
			int key = Strategy.getStrategy().getProblem().getCodification().getAleatoryKey();
			Object candidate = Strategy.getStrategy().getProblem().getCodification().getVariableAleatoryValue(key);
			State state = (State) stateCurrent.getCopy();
			state.getCode().set(key, candidate);
			listNeigborhood.add(state);
		}
		return listNeigborhood;
	}

	@Override
	public List<State> generateRandomState(Integer operatornumber) {
		if (operatornumber == null || operatornumber < 0) {
			throw new IllegalArgumentException("La cantidad de estados debe ser un entero no negativo");
		}
		List<State> states = new ArrayList<>(operatornumber);
		if (operatornumber == 0) {
			return states;
		}
		Codification codification = Strategy.getStrategy().getProblem().getCodification();
		int variableCount = codification.getVariableCount();
		for (int index = 0; index < operatornumber; index++) {
			states.add(sampleValidState(codification, variableCount));
		}
		return states;
	}

	private static State sampleValidState(Codification codification, int variableCount) {
		for (int attempt = 0; attempt < MAX_SAMPLE_ATTEMPTS; attempt++) {
			ArrayList<Object> code = new ArrayList<>(variableCount);
			for (int key = 0; key < variableCount; key++) {
				code.add(codification.getVariableAleatoryValue(key));
			}
			State sample = new State(code);
			if (codification.validState(sample)) {
				return sample;
			}
		}
		throw new IllegalStateException("No se encontró un estado válido después de " + MAX_SAMPLE_ATTEMPTS + " intentos");
	}

}
