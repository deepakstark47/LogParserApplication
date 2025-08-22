package Aggregators;

import Models.LogEntry;

import java.util.*;

public class APMAggregator extends LogAggregator {
    @Override
    public String aggregate(List<LogEntry> logs) {
        // Implement aggregation for metrics (e.g., min, max, median, average)
        Map<String, List<Double>> metrics = new HashMap<>();
        for (LogEntry log : logs) {
            String metric = log.getAttributes().get("metric");
            double value = Double.parseDouble(log.getAttributes().get("value"));
            metrics.computeIfAbsent(metric, k -> new ArrayList<>()).add(value);
        }

        TreeMap<String, Map<String, Double>> aggregatedResults = new TreeMap<>();
        for (Map.Entry<String, List<Double>> entry : metrics.entrySet()) {
            List<Double> values = entry.getValue();
            Collections.sort(values);
            double min = values.get(0);
            double max = values.get(values.size() - 1);
            double avg = values.stream().mapToDouble(Double::doubleValue).average().orElse(0.0);
            double median = calculateMedian(values);


            LinkedHashMap<String, Double> stats = new LinkedHashMap<>();
            stats.put("minimum", min);
            stats.put("median", median);
            stats.put("average", avg);
            stats.put("max", max);

            aggregatedResults.put(entry.getKey(), stats);
        }

        return toJson(aggregatedResults);
    }

    private double calculateMedian(List<Double> values) {
        if (values.isEmpty()) {
            return 0.0; // Return 0 for empty datasets
        }

        int size = values.size();

        if (size % 2 == 1) {
            return values.get(size / 2);
        } else {
            double lowerMiddle = values.get(size / 2 - 1);
            double upperMiddle = values.get(size / 2);
            return (lowerMiddle + upperMiddle) / 2.0;
        }
    }

}
