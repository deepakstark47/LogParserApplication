package Models;

import java.util.Map;

public class LogEntry {
    private final String logType;
    private final Map<String, String> attributes;

    public LogEntry(String logType, Map<String, String> attributes) {
        this.logType = logType;
        this.attributes = attributes;
    }

    public String getLogType() {
        return logType;
    }

    public Map<String, String> getAttributes() {
        return attributes;
    }
}
