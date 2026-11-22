package factory_method;

import static java.util.Map.entry;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.function.Supplier;
import evolutionary_algorithms.complement.AIOMutation;
import evolutionary_algorithms.complement.GenerationalReplace;
import evolutionary_algorithms.complement.OnePointCrossover;
import evolutionary_algorithms.complement.OnePointMutation;
import evolutionary_algorithms.complement.ProbabilisticSampling;
import evolutionary_algorithms.complement.RouletteSelection;
import evolutionary_algorithms.complement.SteadyStateReplace;
import evolutionary_algorithms.complement.TowPointsMutation;
import evolutionary_algorithms.complement.TruncationSelection;
import evolutionary_algorithms.complement.UniformCrossover;
import evolutionary_algorithms.complement.Univariate;
import local_search.acceptation_type.AcceptAnyone;
import local_search.acceptation_type.AcceptBest;
import local_search.acceptation_type.AcceptMulticase;
import local_search.acceptation_type.AcceptNotBad;
import local_search.acceptation_type.AcceptNotBadT;
import local_search.acceptation_type.AcceptNotBadU;
import local_search.acceptation_type.AcceptNotDominated;
import local_search.acceptation_type.AcceptNotDominatedTabu;
import local_search.candidate_type.GreaterCandidate;
import local_search.candidate_type.NotDominatedCandidate;
import local_search.candidate_type.RandomCandidate;
import local_search.candidate_type.SmallerCandidate;
import metaheuristics.generators.DistributionEstimationAlgorithm;
import metaheuristics.generators.EvolutionStrategies;
import metaheuristics.generators.GeneticAlgorithm;
import metaheuristics.generators.HillClimbing;
import metaheuristics.generators.HillClimbingRestart;
import metaheuristics.generators.LimitThreshold;
import metaheuristics.generators.MultiCaseSimulatedAnnealing;
import metaheuristics.generators.MultiGenerator;
import metaheuristics.generators.MultiobjectiveHillClimbingDistance;
import metaheuristics.generators.MultiobjectiveHillClimbingRestart;
import metaheuristics.generators.MultiobjectiveStochasticHillClimbing;
import metaheuristics.generators.MultiobjectiveTabuSearch;
import metaheuristics.generators.ParticleSwarmOptimization;
import metaheuristics.generators.RandomSearch;
import metaheuristics.generators.SimulatedAnnealing;
import metaheuristics.generators.TabuSearch;
import problem.extension.FactoresPonderados;
import problem.extension.MultiObjetivoPuro;

/** Instancia exclusivamente los componentes registrados de BiCIAM. */
public final class FactoryLoader {
    private static final Map<String, Supplier<?>> COMPONENTS = Map.ofEntries(
        entry("evolutionary_algorithms.complement.AIOMutation", AIOMutation::new),
        entry("evolutionary_algorithms.complement.GenerationalReplace", GenerationalReplace::new),
        entry("evolutionary_algorithms.complement.OnePointCrossover", OnePointCrossover::new),
        entry("evolutionary_algorithms.complement.OnePointMutation", OnePointMutation::new),
        entry("evolutionary_algorithms.complement.ProbabilisticSampling", ProbabilisticSampling::new),
        entry("evolutionary_algorithms.complement.RouletteSelection", RouletteSelection::new),
        entry("evolutionary_algorithms.complement.SteadyStateReplace", SteadyStateReplace::new),
        entry("evolutionary_algorithms.complement.TowPointsMutation", TowPointsMutation::new),
        entry("evolutionary_algorithms.complement.TruncationSelection", TruncationSelection::new),
        entry("evolutionary_algorithms.complement.UniformCrossover", UniformCrossover::new),
        entry("evolutionary_algorithms.complement.Univariate", Univariate::new),
        entry("local_search.acceptation_type.AcceptAnyone", AcceptAnyone::new),
        entry("local_search.acceptation_type.AcceptBest", AcceptBest::new),
        entry("local_search.acceptation_type.AcceptMulticase", AcceptMulticase::new),
        entry("local_search.acceptation_type.AcceptNotBad", AcceptNotBad::new),
        entry("local_search.acceptation_type.AcceptNotBadT", AcceptNotBadT::new),
        entry("local_search.acceptation_type.AcceptNotBadU", AcceptNotBadU::new),
        entry("local_search.acceptation_type.AcceptNotDominated", AcceptNotDominated::new),
        entry("local_search.acceptation_type.AcceptNotDominatedTabu", AcceptNotDominatedTabu::new),
        entry("local_search.candidate_type.GreaterCandidate", GreaterCandidate::new),
        entry("local_search.candidate_type.NotDominatedCandidate", NotDominatedCandidate::new),
        entry("local_search.candidate_type.RandomCandidate", RandomCandidate::new),
        entry("local_search.candidate_type.SmallerCandidate", SmallerCandidate::new),
        entry("metaheuristics.generators.DistributionEstimationAlgorithm", DistributionEstimationAlgorithm::new),
        entry("metaheuristics.generators.EvolutionStrategies", EvolutionStrategies::new),
        entry("metaheuristics.generators.GeneticAlgorithm", GeneticAlgorithm::new),
        entry("metaheuristics.generators.HillClimbing", HillClimbing::new),
        entry("metaheuristics.generators.HillClimbingRestart", HillClimbingRestart::new),
        entry("metaheuristics.generators.LimitThreshold", LimitThreshold::new),
        entry("metaheuristics.generators.MultiCaseSimulatedAnnealing", MultiCaseSimulatedAnnealing::new),
        entry("metaheuristics.generators.MultiGenerator", MultiGenerator::new),
        entry("metaheuristics.generators.MultiobjectiveHillClimbingDistance", MultiobjectiveHillClimbingDistance::new),
        entry("metaheuristics.generators.MultiobjectiveHillClimbingRestart", MultiobjectiveHillClimbingRestart::new),
        entry("metaheuristics.generators.MultiobjectiveStochasticHillClimbing", MultiobjectiveStochasticHillClimbing::new),
        entry("metaheuristics.generators.MultiobjectiveTabuSearch", MultiobjectiveTabuSearch::new),
        entry("metaheuristics.generators.ParticleSwarmOptimization", ParticleSwarmOptimization::new),
        entry("metaheuristics.generators.RandomSearch", RandomSearch::new),
        entry("metaheuristics.generators.SimulatedAnnealing", SimulatedAnnealing::new),
        entry("metaheuristics.generators.TabuSearch", TabuSearch::new),
        entry("problem.extension.FactoresPonderados", FactoresPonderados::new),
        entry("problem.extension.MultiObjetivoPuro", MultiObjetivoPuro::new)
    );

    private FactoryLoader() {
    }

    /**
     * Crea un componente a partir de su nombre Java completo.
     *
     * @param className nombre de un componente registrado
     * @return nueva instancia del componente
     * @throws ClassNotFoundException si el nombre no figura en el registro
     */
    public static Object getInstance(String className) throws ClassNotFoundException,
            InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        Supplier<?> constructor = className == null ? null : COMPONENTS.get(className);
        if (constructor == null) {
            throw new ClassNotFoundException("Componente de BiCIAM no registrado");
        }
        return constructor.get();
    }
}
