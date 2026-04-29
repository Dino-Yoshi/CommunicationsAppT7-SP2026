package testing;

import server.LoggingManager;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class LoggerLogError {

    @DisplayName("Log Error")
    @Test
    public void LogErrorTest() {
        LoggingManager lm = new LoggingManager("testlog.txt");
        lm.logError("boom");
        assertEquals(1, lm.getStructuredLogs().size());
    }
}