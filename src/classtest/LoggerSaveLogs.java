package classtest;

import server.LoggingManager;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class LoggerSaveLogs {

    @DisplayName("Save Logs")
    @Test
    public void SaveLogsTest() {
        LoggingManager lm = new LoggingManager("testlog.txt");
        lm.logInfo("hello");
        lm.saveLogs();
        assertEquals(0, lm.getBufferedLogs().size());
    }
}