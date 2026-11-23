/**
 * @(#) CandidateValue.java
 */

package local_search.candidate_type;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import problem.definition.State;

import local_search.complement.StrategyType;
import local_search.complement.TabuSolutions;
import metaheurictics.strategy.Strategy;

//import ceis.grial.problem.Problem;
import factory_interface.IFFactoryCandidate;
import factory_method.FactoryCandidate;

public class CandidateValue {

	private IFFactoryCandidate ifFactory;

	private TabuSolutions tabusolution;

	private SearchCandidate searchcandidate;

	public CandidateValue(){}

	public CandidateValue(StrategyType strategy, IFFactoryCandidate ifFactory, CandidateType typecand, 
			TabuSolutions tabusolution, SearchCandidate searchcandidate) { //, Strategy executegenerator
		super();
		this.ifFactory = ifFactory;
		this.tabusolution = tabusolution;
		this.searchcandidate = searchcandidate;
	}

	public SearchCandidate newSearchCandidate(CandidateType typecandidate) throws IllegalArgumentException, SecurityException, ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		ifFactory = new FactoryCandidate();
		searchcandidate = ifFactory.createSearchCandidate(typecandidate);
		return searchcandidate;
	}

	public State stateCandidate(State stateCurrent, CandidateType typeCandidate, StrategyType strategy, Integer operatornumber, List<State> neighborhood) throws IllegalArgumentException, SecurityException, ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException{
		if (neighborhood == null || neighborhood.isEmpty()) {
			throw new IllegalArgumentException("El operador debe proporcionar al menos un candidato");
		}
		State stateCandidate;
		List<State> auxList = new ArrayList<>(neighborhood);
		this.tabusolution = new TabuSolutions();
		if (strategy.equals(StrategyType.TABU)) {
			try {
				auxList = this.tabusolution.filterNeighborhood(auxList);
			}
			catch (Exception e) {
				Strategy strategys = Strategy.getStrategy();
				if(strategys.getProblem()!=null){
					neighborhood = strategys.getProblem().getOperator().generatedNewState(neighborhood.get(0), operatornumber);
				}
				return stateCandidate(stateCurrent, typeCandidate, strategy, operatornumber, neighborhood);
			}
		}
		SearchCandidate searchCand = newSearchCandidate(typeCandidate);
		stateCandidate = searchCand.stateSearch(auxList);
		return stateCandidate;
	}

	public TabuSolutions getTabusolution() {
		return tabusolution;
	}

	public void setTabusolution(TabuSolutions tabusolution) {
		this.tabusolution = tabusolution;
	}
}
