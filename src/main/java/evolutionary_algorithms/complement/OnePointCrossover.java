package evolutionary_algorithms.complement;

import biciam.util.OptimizationRandom;
import java.util.ArrayList;
import problem.definition.State;

/** Combina el prefijo de un progenitor con el sufijo del otro. */
public class OnePointCrossover extends Crossover {

    /**
     * @param father1 primer progenitor
     * @param father2 segundo progenitor
     * @param probability probabilidad de cruzar, entre cero y uno
     * @return descendiente con un código independiente
     */
    @Override
    public State crossover(State father1, State father2, double probability) {
        ArrayList<Object> first = father1.getCode();
        ArrayList<Object> second = father2.getCode();
        if (first.size() != second.size()) {
            throw new IllegalArgumentException("Los progenitores deben tener códigos de igual longitud");
        }

        State offspring = (State) father1.getCopy();
        if (first.size() < 2 || OptimizationRandom.nextDouble() >= probability) {
            return offspring;
        }

        int cut = 1 + OptimizationRandom.nextInt(first.size() - 1);
        boolean firstAsPrefix = OptimizationRandom.nextInt(2) == 0;
        ArrayList<Object> prefix = firstAsPrefix ? first : second;
        ArrayList<Object> suffix = firstAsPrefix ? second : first;
        for (int i = 0; i < first.size(); i++) {
            offspring.getCode().set(i, i < cut ? prefix.get(i) : suffix.get(i));
        }
        return offspring;
    }
}
