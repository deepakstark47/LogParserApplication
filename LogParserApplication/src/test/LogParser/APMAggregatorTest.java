import Aggregators.APMAggregator;
import Models.LogEntry;
import org.junit.jupiter.api.Test;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

class APMAggregatorTest {
    @Test
    void testAggregateAPMLogs() {
        List<LogEntry> logs = Arrays.asList(
                new LogEntry("APM", Map.of("metric", "cpu_usage_percent", "value", "72")),
                new LogEntry("APM", Map.of("metric", "cpu_usage_percent", "value", "90")),
                new LogEntry("APM", Map.of("metric", "cpu_usage_percent", "value", "78"))
        );

        APMAggregator aggregator = new APMAggregator();
        String result = aggregator.aggregate(logs);
        assertTrue(result.contains("\"minimum\" : 72.0"));
        assertTrue(result.contains("\"median\" : 78.0"));
        assertTrue(result.contains("\"average\" : 80.0"));
        assertTrue(result.contains("\"max\" : 90.0"));
    }

    @Test
    void testAggregateEmptyAPMLogs() {
        List<LogEntry> logs = new ArrayList<>();

        APMAggregator aggregator = new APMAggregator();
        String result = aggregator.aggregate(logs);

        assertEquals("{ }", result);
    }
}
