package metaheuristics.generators;

/** Sustituye la instancia del generador EvolutionStrategies en el portafolio activo. */
public class InstanceEE implements Runnable {
    private volatile boolean terminate;

    @Override
    public void run() {
        Generator[] generators = MultiGenerator.getListGenerators();
        for (int i = 0; i < generators.length; i++) {
            if (generators[i].getType() == GeneratorType.EvolutionStrategies) {
                generators[i] = new EvolutionStrategies();
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
