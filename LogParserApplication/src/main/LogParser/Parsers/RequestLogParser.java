package Parsers;

import Models.LogEntry;
import Utils.LogValidator;

import java.util.*;

public class RequestLogParser implements LogParser {
    @Override
    public LogEntry parse(String logLine) {
        Map<String, String> attributes = parseAttributes(logLine);


        if (!LogValidator.validate(attributes, Arrays.asList("request_method", "response_status","request_url","response_time_ms"), logLine)) {
            return null;
        }


        // Remove quotes around `request_url` if present
        String requestUrl = attributes.get("request_url");

        if (requestUrl != null && requestUrl.startsWith("\"") && requestUrl.endsWith("\"")) {
            attributes.put("request_url", requestUrl.substring(1, requestUrl.length() - 1));
        }

        return new LogEntry("REQUEST", attributes);
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