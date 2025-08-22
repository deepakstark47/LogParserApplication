package Aggregators;

import Models.LogEntry;

import java.util.*;

public class RequestAggregator extends LogAggregator {
    @Override
    public String aggregate(List<LogEntry> logs) {
        Map<String, RouteStats> routeStatsMap = new HashMap<>();

        // Process each log entry
        for (LogEntry log : logs) {
            String route = log.getAttributes().get("request_url");
            int responseTime = Integer.parseInt(log.getAttributes().get("response_time_ms"));
            String statusCode = log.getAttributes().get("response_status");

            // Ensure a RouteStats object exists for the route
            RouteStats routeStats = routeStatsMap.computeIfAbsent(route, k -> new RouteStats());

            // Add response time and categorize status code
            routeStats.addResponseTime(responseTime);
            routeStats.incrementStatusCode(statusCode);
        }

        // Convert route stats to JSON
        TreeMap<String, Object> output = new TreeMap<>();
        for (Map.Entry<String, RouteStats> entry : routeStatsMap.entrySet()) {
            output.put(entry.getKey(), entry.getValue().toJson());
        }

        return toJson(output);
    }

    // Inner class to manage stats for a specific route
    private static class RouteStats {
        private final List<Integer> responseTimes = new ArrayList<>();
        private final Map<String, Integer> statusCodeCounts = new HashMap<>();

        public void addResponseTime(int time) {
            responseTimes.add(time);
        }

        public void incrementStatusCode(String statusCode) {
            String category = categorizeStatusCode(statusCode);

            statusCodeCounts.put(category, statusCodeCounts.getOrDefault(category, 0) + 1);
        }

        public Map<String, Object> toJson() {
            LinkedHashMap<String, Object> stats = new LinkedHashMap<>();
            Map<String, Double> completeResponseTimes = new LinkedHashMap<>();

            if (!responseTimes.isEmpty()) {
                Collections.sort(responseTimes);
                // Calculate min, max, and percentiles

                completeResponseTimes.put("min", responseTimes.get(0).doubleValue());
                completeResponseTimes.put("50_percentile", calculatePercentile(50));
                completeResponseTimes.put("90_percentile", calculatePercentile(90));
                completeResponseTimes.put("95_percentile", calculatePercentile(95));
                completeResponseTimes.put("99_percentile", calculatePercentile(99));
                completeResponseTimes.put("max", responseTimes.get(responseTimes.size() - 1).doubleValue());

                stats.put("response_times", completeResponseTimes);
            }
            Map<String, Integer> completeStatusCodes = new LinkedHashMap<>();
            completeStatusCodes.put("2XX", statusCodeCounts.getOrDefault("2XX", 0));
            completeStatusCodes.put("4XX", statusCodeCounts.getOrDefault("4XX", 0));
            completeStatusCodes.put("5XX", statusCodeCounts.getOrDefault("5XX", 0));
            stats.put("status_codes", completeStatusCodes);
            return stats;
        }

        private double calculatePercentile(int percentile) {
            if (responseTimes.isEmpty()) {
                return 0.0; // Return 0 for empty datasets
            }

            if (responseTimes.size() == 1) {
                return responseTimes.get(0); // Return the only value for single-element datasets
            }

            // Calculate rank as a floating-point index
            double rank = (percentile / 100.0) * (responseTimes.size() - 1);
            int lowerIndex = (int) Math.floor(rank);
            int upperIndex = (int) Math.ceil(rank);

            // If rank is an integer, return the exact value
            if (lowerIndex == upperIndex) {
                return responseTimes.get(lowerIndex);
            }

            // Interpolate between the lower and upper values
            double lowerValue = responseTimes.get(lowerIndex);
            double upperValue = responseTimes.get(upperIndex);
            double fractionalPart = rank - lowerIndex;

            double interpolatedValue = lowerValue + fractionalPart * (upperValue - lowerValue);

            return Double.parseDouble(String.format("%.2f", interpolatedValue));
        }


        private String categorizeStatusCode(String statusCode) {
            if (statusCode.startsWith("2")) return "2XX";
            if (statusCode.startsWith("4")) return "4XX";
            if (statusCode.startsWith("5")) return "5XX";
            return "UNKNOWN";
        }
    }
}
