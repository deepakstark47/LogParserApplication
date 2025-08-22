**CMPE 202 Individual Project**

**Log Parser Application**

This project is a command-line tool to parse log files, classify logs into types (APM, Application, Request), and generate aggregated JSON outputs. It supports extensibility for new log types and file formats.

**Features**

APM Logs: Computes min, max, median, average.

Application Logs: Counts logs by severity (INFO, ERROR, etc.).

Request Logs: Response time percentiles and HTTP status categorization.

**Run Instructions**

Build: mvn clean install

Run: java -cp target/classes LogParserApplication --file input.txt

Outputs: apm.json, application.json, request.json.
