package factory_method;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import local_search.acceptation_type.AcceptBest;
import local_search.acceptation_type.AcceptType;
import local_search.candidate_type.CandidateType;
import local_search.candidate_type.RandomCandidate;
import org.junit.jupiter.api.Test;
import problem.extension.MultiObjetivoPuro;
import problem.extension.TypeSolutionMethod;

class FactoryLoaderTest {
    @Test
    void factoriesCreateNewInstancesOfRegisteredTypes() throws ReflectiveOperationException {
        FactoryAcceptCandidate factory = new FactoryAcceptCandidate();
        AcceptBest first = assertInstanceOf(AcceptBest.class, factory.createAcceptCandidate(AcceptType.AcceptBest));
        assertNotSame(first, factory.createAcceptCandidate(AcceptType.AcceptBest));
        assertInstanceOf(RandomCandidate.class, new FactoryCandidate().createSearchCandidate(CandidateType.RandomCandidate));
        assertInstanceOf(MultiObjetivoPuro.class,
                new FactorySolutionMethod().createdSolutionMethod(TypeSolutionMethod.MultiObjetivoPuro));
    }

    @Test
    void rejectsUnregisteredAndMissingNames() {
        assertThrows(ClassNotFoundException.class, () -> FactoryLoader.getInstance("biciam.NoExiste"));
        assertThrows(ClassNotFoundException.class, () -> FactoryLoader.getInstance("java.util.ArrayList"));
        assertThrows(ClassNotFoundException.class, () -> FactoryLoader.getInstance(null));
    }
}
