import Aggregators.LogAggregator;
import Aggregators.LogAggregatorFactory;
import Models.LogEntry;
import IO.FileHandler;
import Parsers.LogParser;
import Parsers.LogParserFactory;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class LogParserApplication {
    public static void main(String[] args) {
        // Validate CLI arguments
        if (args.length < 2 || !"--file".equals(args[0])) {
            System.out.println("Usage: java LogParserApplication --file <filename.txt>");
            return;
        }

        String inputFileName = args[1];
        File inputFile = new File(inputFileName);

        // Validate the input file
        if (!inputFile.exists() || !inputFile.canRead()) {
            System.out.println("The specified file does not exist or cannot be read: " + inputFileName);
            return;
        }

        FileHandler fileHandler = FileHandler.getInstance();

        try {
            System.out.println("Reading log file: " + inputFileName);
            List<String> logLines = fileHandler.readFile(inputFileName);

            // Initialize categorized logs
            Map<String, List<LogEntry>> categorizedLogs = new HashMap<>();

            for (String logLine : logLines) {
                try {
                    // Get the appropriate parser for the log line
                    LogParser parser = LogParserFactory.getParser(logLine);

                    if (parser != null) {
                        LogEntry entry = parser.parse(logLine);
                        if (entry != null) {
                            categorizedLogs
                                    .computeIfAbsent(entry.getLogType(), k -> new ArrayList<>())
                                    .add(entry);
                        } else {
                            System.out.println("Skipping log line due to missing required fields: " + logLine);
                        }
                    } else {
                        System.out.println("No parser available for log line: " + logLine);
                    }
                } catch (Exception ex) {
                    System.out.println("Error parsing log line: " + logLine);
                    ex.printStackTrace();
                }
            }

            // Aggregate and write outputs
            System.out.println("Aggregating and writing categorized logs...");
            List<String> outputFiles = new ArrayList<>();

            for (Map.Entry<String, List<LogEntry>> category : categorizedLogs.entrySet()) {
                LogAggregator aggregator = LogAggregatorFactory.getAggregator(category.getKey());

                if (aggregator != null) {
                    String outputFileName = category.getKey().toLowerCase() + ".json";
                    String jsonOutput = aggregator.aggregate(category.getValue());
                    fileHandler.writeFile(outputFileName, jsonOutput);
                    outputFiles.add(outputFileName);
                } else {
                    System.out.println("No aggregator available for log type: " + category.getKey());
                }
            }

            // Ensure all required output files
            List<String> requiredFiles = Arrays.asList("apm.json", "application.json", "request.json");
            System.out.println("Ensuring all required output files...");
            fileHandler.ensureOutputFiles(requiredFiles);

            System.out.println("Log processing completed. Files created: " + outputFiles);
        } catch (IOException e) {
            System.out.println("Error during file I/O operations.");
            e.printStackTrace();
        }
    }
}
