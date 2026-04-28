package classtest;

import server.StorageManager;
import server.UserAuthenticator;
import server.Request;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class StorageLoadChatHistory {

    @DisplayName("Load Chat History")
    @Test
    public void LoadChatHistoryTest() {
        StorageManager sm = new StorageManager("testusers.txt", "testmessages");
        UserAuthenticator ua = new UserAuthenticator();
        ua.registerUser("clarize", "1234");
        ua.registerUser("victor", "5678");

        Request r = new Request("hello", "USER", "USER", 0, 1, 2);
        sm.saveMessage(r, ua);

        assertEquals(true, sm.loadChatHistory(1, 2, ua).get(0).contains("hello"));
    }
}