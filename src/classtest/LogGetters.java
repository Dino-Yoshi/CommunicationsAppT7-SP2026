package classtest;

import server.Log;
import server.LogType;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class LogGetters {

    @DisplayName("Log Getters")
    @Test
    public void LogGettersTest() {
        Log log = new Log(LogType.SEARCH, "1", "SERVER", "query");

        assertEquals(LogType.SEARCH, log.getTypeLog());
        assertEquals("1", log.getSenderID());
        assertEquals("SERVER", log.getRecipientID());
        assertEquals("query", log.getContent());
        assertNotNull(log.getTimestamp());
    }
}