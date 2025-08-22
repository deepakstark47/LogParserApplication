import Aggregators.ApplicationAggregator;
import Models.LogEntry;
import org.junit.jupiter.api.Test;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

class ApplicationAggregatorTest {
    @Test
    void testAggregateApplicationLogs() {
        List<LogEntry> logs = Arrays.asList(
                new LogEntry("APPLICATION", Map.of("level", "INFO")),
                new LogEntry("APPLICATION", Map.of("level", "ERROR")),
                new LogEntry("APPLICATION", Map.of("level", "INFO"))
        );

        ApplicationAggregator aggregator = new ApplicationAggregator();
        String result = aggregator.aggregate(logs);

        assertTrue(result.contains("\"INFO\" : 2"));
        assertTrue(result.contains("\"ERROR\" : 1"));
    }

    @Test
    void testAggregateEmptyApplicationLogs() {
        List<LogEntry> logs = new ArrayList<>();

        ApplicationAggregator aggregator = new ApplicationAggregator();
        String result = aggregator.aggregate(logs);

        assertEquals("{ }", result);
    }
}
