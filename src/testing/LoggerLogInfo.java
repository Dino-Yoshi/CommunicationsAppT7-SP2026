package testing;

import server.LoggingManager;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class LoggerLogInfo {

    @DisplayName("Log Info")
    @Test
    public void LogInfoTest() {
        LoggingManager lm = new LoggingManager("testlog.txt");
        lm.logInfo("hello");
        assertEquals(1, lm.getBufferedLogs().size());
    }
}