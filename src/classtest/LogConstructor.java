package classtest;

import server.Log;
import server.LogType;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class LogConstructor {

    @DisplayName("Log Constructor")
    @Test
    public void LogConstructorTest() {
        Log log = new Log(LogType.SEARCH, "1", "SERVER", "query");
        assertNotNull(log);
    }
}