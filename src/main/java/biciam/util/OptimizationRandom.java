package biciam.util;

import java.security.SecureRandom;

/** Proporciona una fuente compartida de aleatoriedad para los operadores de búsqueda. */
public final class OptimizationRandom {
    private static final SecureRandom RANDOM = new SecureRandom();

    private OptimizationRandom() {
    }

    /** @return una muestra uniforme en el intervalo [0, 1). */
    public static double nextDouble() {
        return RANDOM.nextDouble();
    }

    /**
     * @param bound límite superior exclusivo y positivo
     * @return un entero uniforme desde cero hasta el límite exclusivo
     */
    public static int nextInt(int bound) {
        return RANDOM.nextInt(bound);
    }
}
