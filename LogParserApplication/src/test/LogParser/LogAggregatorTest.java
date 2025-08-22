import Aggregators.LogAggregator;
import Models.LogEntry;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class LogAggregatorTest {

    @Test
    void testToJson() {
        // Create a dummy implementation of LogAggregator for testing
        LogAggregator aggregator = new LogAggregator() {
            @Override
            public String aggregate(java.util.List<LogEntry> logs) {
                return null; // Not used in this test
            }
        };

        // Input object to convert to JSON
        Map<String, Object> sampleData = Map.of(
                "key1", "value1",
                "key2", 42,
                "nested", Map.of("subKey", "subValue")
        );

        // Expected JSON string
        String expectedJson = "{\n" +
                "  \"key1\" : \"value1\",\n" +
                "  \"key2\" : 42,\n" +
                "  \"nested\" : {\n" +
                "    \"subKey\" : \"subValue\"\n" +
                "  }\n" +
                "}";

        // Call the toJson method
        String actualJson = aggregator.toJson(sampleData);

        // Use Jackson's ObjectMapper to compare JSON structures
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            assertEquals(
                    objectMapper.readTree(expectedJson),
                    objectMapper.readTree(actualJson)
            );
        } catch (Exception e) {
            fail("JSON comparison failed: " + e.getMessage());
        }
    }

    @Test
    void testToJsonWithEmptyObject() {
        // Create a dummy implementation of LogAggregator for testing
        LogAggregator aggregator = new LogAggregator() {
            @Override
            public String aggregate(java.util.List<LogEntry> logs) {
                return null; // Not used in this test
            }
        };

        // Input empty object
        Map<String, Object> emptyData = Map.of();

        // Expected JSON for an empty object
        String expectedJson = "{ }";

        // Call the toJson method
        String actualJson = aggregator.toJson(emptyData);

        // Assert equality
        assertEquals(expectedJson, actualJson);
    }

    @Test
    void testToJsonThrowsExceptionForInvalidInput() {
        // Create a dummy implementation of LogAggregator for testing
        LogAggregator aggregator = new LogAggregator() {
            @Override
            public String aggregate(java.util.List<LogEntry> logs) {
                return null; // Not used in this test
            }
        };

        // Input invalid object (Jackson can handle almost anything, so this test may be hard to trigger)
        Object invalidData = new Object() {
            @Override
            public String toString() {
                throw new RuntimeException("Invalid object");
            }
        };

        // Assert that an exception is thrown
        assertThrows(RuntimeException.class, () -> aggregator.toJson(invalidData));
    }
}
