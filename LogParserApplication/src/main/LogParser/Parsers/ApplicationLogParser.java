package Parsers;

import Models.LogEntry;
import Utils.LogValidator;

import java.util.*;

public class ApplicationLogParser implements LogParser {
    @Override
    public LogEntry parse(String logLine) {
        Map<String, String> attributes = parseAttributes(logLine);

        if (!LogValidator.validate(attributes, Arrays.asList("level", "message"), logLine)) {
            return null; // Return null for invalid logs
        }

        return new LogEntry("APPLICATION", attributes);
    }

    private Map<String, String> parseAttributes(String logLine) {
        Map<String, String> attributes = new HashMap<>();
        String[] parts = logLine.split("\\s+");
        for (String part : parts) {
            String[] kv = part.split("=", 2);
            if (kv.length == 2) {
                attributes.put(kv[0], kv[1]);
            }
        }
        return attributes;
    }
}