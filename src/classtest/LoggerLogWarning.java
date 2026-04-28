package classtest;

import server.LoggingManager;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class LoggerLogWarning {

    @DisplayName("Log Warning")
    @Test
    public void LogWarningTest() {
        LoggingManager lm = new LoggingManager("testlog.txt");
        lm.logWarning("careful");
        assertEquals(1, lm.getBufferedLogs().size());
    }
}