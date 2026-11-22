package local_search.complement;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class StopExecuteTest {
    @ParameterizedTest
    @CsvSource({"0, 0, true", "4, 5, false", "5, 5, true", "6, 5, true"})
    void stopsWhenTheLimitIsReached(int current, int limit, boolean expected) {
        assertEquals(expected, new StopExecute().stopIterations(current, limit));
    }
}
