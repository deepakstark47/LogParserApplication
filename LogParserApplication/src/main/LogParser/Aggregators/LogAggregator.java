package Aggregators;

import Models.LogEntry;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

public abstract class LogAggregator {
    public abstract String aggregate(List<LogEntry> logs);

    // Utility method to convert a Java object to a JSON string
    public String toJson(Object obj) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException("Error converting to JSON", e);
        }
    }
}
