import Models.LogEntry;
import Aggregators.RequestAggregator;
import org.junit.jupiter.api.Test;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

class RequestAggregatorTest {
    @Test
    void testAggregateRequestLogs() {
        List<LogEntry> logs = Arrays.asList(
                new LogEntry("REQUEST", Map.of(
                        "request_url", "/api/update",
                        "response_time_ms", "200",
                        "response_status", "202"
                )),
                new LogEntry("REQUEST", Map.of(
                        "request_url", "/api/update",
                        "response_time_ms", "250",
                        "response_status", "503"
                )),
                new LogEntry("REQUEST", Map.of(
                        "request_url", "/api/update",
                        "response_time_ms", "150",
                        "response_status", "404"
                ))
        );

        RequestAggregator aggregator = new RequestAggregator();
        String result = aggregator.aggregate(logs);

        assertTrue(result.contains("\"/api/update\""));
        assertTrue(result.contains("\"min\" : 150"));
        assertTrue(result.contains("\"max\" : 250"));
        assertTrue(result.contains("\"50_percentile\" : 200"));
        assertTrue(result.contains("\"2XX\" : 1"));
        assertTrue(result.contains("\"4XX\" : 1"));
        assertTrue(result.contains("\"5XX\" : 1"));
    }

    @Test
    void testAggregateEmptyRequestLogs() {
        List<LogEntry> logs = new ArrayList<>();

        RequestAggregator aggregator = new RequestAggregator();
        String result = aggregator.aggregate(logs);

        assertEquals("{ }", result);
    }
}
