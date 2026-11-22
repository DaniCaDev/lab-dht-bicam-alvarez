package metaheuristics.generators;

/** Sustituye la instancia del generador GeneticAlgorithm en el portafolio activo. */
public class InstanceGA implements Runnable {
    private volatile boolean terminate;

    @Override
    public void run() {
        Generator[] generators = MultiGenerator.getListGenerators();
        for (int i = 0; i < generators.length; i++) {
            if (generators[i].getType() == GeneratorType.GeneticAlgorithm) {
                generators[i] = new GeneticAlgorithm();
                terminate = true;
                return;
            }
        }
    }

    public boolean isTerminate() {
        return terminate;
    }

    public void setTerminate(boolean terminate) {
        this.terminate = terminate;
    }
}
