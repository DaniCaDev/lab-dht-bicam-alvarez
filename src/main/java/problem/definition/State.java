package problem.definition;

import java.util.ArrayList;
import java.util.Objects;
import metaheuristics.generators.GeneratorType;

/** Representa una solución candidata, su evaluación y el generador que la produjo. */
public class State implements Cloneable {

    /** Tipo de generador que produjo el estado. */
    protected GeneratorType typeGenerator;
    /** Valores de las funciones objetivo, en su orden de evaluación. */
    protected ArrayList<Double> evaluation;
    /** Número asignado durante la búsqueda. */
    protected int number;
    /** Código mutable de la solución candidata. */
    protected ArrayList<Object> code;

    /**
     * Copia las listas del estado y conserva sus metadatos.
     *
     * @param source estado que se copia
     */
    public State(State source) {
        this(source.getCode());
        typeGenerator = source.getTypeGenerator();
        setEvaluation(source.getEvaluation());
        number = source.getNumber();
    }

    /** @param code valores iniciales del código de la solución. */
    public State(ArrayList<Object> code) {
        setCode(code);
    }

    /** Crea un estado con el código vacío, pendiente de evaluación. */
    public State() {
        code = new ArrayList<>();
    }

    /** @return código mutable sobre el que trabajan los operadores. */
    public ArrayList<Object> getCode() {
        return code;
    }

    /** @param code código de la solución que se copiará. */
    public void setCode(ArrayList<Object> code) {
        this.code = new ArrayList<>(code);
    }

    /** @return tipo del generador asociado al estado. */
    public GeneratorType getTypeGenerator() {
        return typeGenerator;
    }

    /** @param typeGenerator tipo del generador asociado al estado. */
    public void setTypeGenerator(GeneratorType typeGenerator) {
        this.typeGenerator = typeGenerator;
    }

    /** @return evaluación mutable o null si está pendiente. */
    public ArrayList<Double> getEvaluation() {
        return evaluation;
    }

    /** @param evaluation valores de evaluación que se copiarán, o null. */
    public void setEvaluation(ArrayList<Double> evaluation) {
        this.evaluation = evaluation == null ? null : new ArrayList<>(evaluation);
    }

    /** @return número asignado al estado. */
    public int getNumber() {
        return number;
    }

    /** @param number número asignado al estado. */
    public void setNumber(int number) {
        this.number = number;
    }

    /** @return copia del estado con listas independientes y los mismos metadatos. */
    @Override
    public State clone() {
        try {
            State copy = (State) super.clone();
            copy.setCode(code);
            copy.setEvaluation(evaluation);
            return copy;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }

    /** @return estado nuevo con el mismo código, pendiente de evaluación. */
    public Object getCopy() {
        return new State(code);
    }

    /**
     * Compara los códigos de dos estados.
     *
     * @param state estado con el que se compara
     * @return true si ambos códigos contienen los mismos valores en el mismo orden
     */
    public boolean Comparator(State state) {
        return code.equals(state.getCode());
    }

    /**
     * Calcula la distancia de Hamming entre dos códigos de igual longitud.
     *
     * @param state estado con el que se compara
     * @return número de posiciones con valores distintos
     * @throws IllegalArgumentException si los códigos tienen distinta longitud
     */
    public double Distance(State state) {
        ArrayList<Object> other = state.getCode();
        if (code.size() != other.size()) {
            throw new IllegalArgumentException("Los códigos deben tener la misma longitud");
        }
        int distance = 0;
        for (int i = 0; i < code.size(); i++) {
            if (!Objects.equals(code.get(i), other.get(i))) {
                distance++;
            }
        }
        return distance;
    }
}
