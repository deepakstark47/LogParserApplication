package Aggregators;

public class LogAggregatorFactory {
    public static LogAggregator getAggregator(String logType) {
        switch (logType) {
            case "APM":
                return new APMAggregator();
            case "APPLICATION":
                return new ApplicationAggregator();
            case "REQUEST":
                return new RequestAggregator();
            default:
                throw new IllegalArgumentException("Unknown log type: " + logType);
        }
    }
}
