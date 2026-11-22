/**
 * @(#) AleatoryCandidate.java
 */

package local_search.candidate_type;

import biciam.util.OptimizationRandom;

import java.util.List;

import problem.definition.State;

public class RandomCandidate extends SearchCandidate {

	@Override
	public State stateSearch(List<State> listNeighborhood) {
		int pos = OptimizationRandom.nextInt(listNeighborhood.size());
		State stateAleatory = listNeighborhood.get(pos);
		return stateAleatory;
	}
}
