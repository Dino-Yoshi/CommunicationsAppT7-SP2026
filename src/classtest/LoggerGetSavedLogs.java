package classtest;

import server.LoggingManager;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class LoggerGetSavedLogs {

    @DisplayName("Get Saved Logs")
    @Test
    public void GetSavedLogsTest() {
        LoggingManager lm = new LoggingManager("testlog.txt");
        lm.logInfo("hello");
        lm.saveLogs();
        assertEquals(true, lm.getSavedLogs().size() >= 1);
    }
}