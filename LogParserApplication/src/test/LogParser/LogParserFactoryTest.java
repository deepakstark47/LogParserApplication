import Parsers.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class LogParserFactoryTest {
    @Test
    void testGetParserForAPMLog() {
        LogParserFactory factory = new LogParserFactory();
        String logLine = "timestamp=2024-02-24T16:22:15Z metric=cpu_usage_percent host=webserver1 value=72";
        LogParser parser = factory.getParser(logLine);
        assertTrue(parser instanceof APMLogParser);
    }

    @Test
    void testGetParserForApplicationLog() {
        LogParserFactory factory = new LogParserFactory();
        String logLine = "timestamp=2024-02-24T16:22:20Z level=INFO message=\"Scheduled maintenance starting\" host=webserver1";
        LogParser parser = factory.getParser(logLine);
        assertTrue(parser instanceof ApplicationLogParser);
    }

    @Test
    void testGetParserForRequestLog() {
        LogParserFactory factory = new LogParserFactory();
        String logLine = "timestamp=2024-02-24T16:22:25Z request_method=POST request_url=\"/api/update\" response_status=202 response_time_ms=200 host=webserver1";
        LogParser parser = factory.getParser(logLine);
        assertTrue(parser instanceof RequestLogParser);
    }

    @Test
    void testGetParserForInvalidLog() {
        LogParserFactory factory = new LogParserFactory();
        String logLine = "invalid_log_format";
        LogParser parser = factory.getParser(logLine);
        assertNull(parser);
    }
}
