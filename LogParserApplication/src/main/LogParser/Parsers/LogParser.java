package Parsers;

import Models.LogEntry;

public interface LogParser {
    LogEntry parse(String logLine);
}