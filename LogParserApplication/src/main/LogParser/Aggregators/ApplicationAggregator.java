package Aggregators;

import Models.LogEntry;

import java.util.*;

public class ApplicationAggregator extends LogAggregator {
    @Override
    public String aggregate(List<LogEntry> logs) {
        LinkedHashMap<String, Integer> severityCounts = new LinkedHashMap<>();

        // Count logs by severity levels
        for (LogEntry log : logs) {
            String level = log.getAttributes().get("level");
            severityCounts.put(level, severityCounts.getOrDefault(level, 0) + 1);
        }


        // Convert the map to JSON
        return toJson(severityCounts);
    }
}
