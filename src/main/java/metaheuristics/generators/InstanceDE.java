package metaheuristics.generators;

/** Sustituye la instancia del generador DistributionEstimationAlgorithm en el portafolio activo. */
public class InstanceDE implements Runnable {
    private volatile boolean terminate;

    @Override
    public void run() {
        Generator[] generators = MultiGenerator.getListGenerators();
        for (int i = 0; i < generators.length; i++) {
            if (generators[i].getType() == GeneratorType.DistributionEstimationAlgorithm) {
                generators[i] = new DistributionEstimationAlgorithm();
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
