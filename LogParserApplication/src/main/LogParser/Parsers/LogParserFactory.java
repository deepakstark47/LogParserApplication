package Parsers;

public class LogParserFactory {
    public static LogParser getParser(String logLine) {
        if (logLine.contains("metric=")) {
            return new APMLogParser();
        } else if (logLine.contains("level=")) {
            return new ApplicationLogParser();
        } else if (logLine.contains("request_method=")) {
            return new RequestLogParser();
        }
        return null; // Ignore unsupported log lines
    }
}
