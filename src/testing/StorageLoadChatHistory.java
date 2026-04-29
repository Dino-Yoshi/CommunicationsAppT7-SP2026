package testing;

import server.StorageManager;
import server.UserAuthenticator;
import networking.Request;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class StorageLoadChatHistory {

    @DisplayName("Load Chat History")
    @Test
    public void LoadChatHistoryTest() {
        StorageManager sm = new StorageManager(
                "testITUsers.txt",
                "testusers.txt",
                "testcontacts.txt",
                "testmessages"
        );

        UserAuthenticator ua = new UserAuthenticator();
        ua.registerUser("clarize", "1234");
        ua.registerUser("victor", "5678");

        Request r = new Request("hello,victor", "USER", "USER", 0, 1, 2);
        sm.saveMessage(r, ua);

        assertEquals(true, sm.loadChatHistory(1, 2, ua).get(0).contains("hello"));
    }
}