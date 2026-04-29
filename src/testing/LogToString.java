package testing;

import server.Log;
import server.LogType;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class LogToString {

    @DisplayName("Log To String")
    @Test
    public void LogToStringTest() {
        Log log = new Log(LogType.SEARCH, "1", "SERVER", "query");
        assertEquals(true, log.toString().contains("query"));
    }
}