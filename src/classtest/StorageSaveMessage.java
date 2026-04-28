package classtest;

import server.StorageManager;
import server.UserAuthenticator;
import server.Request;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class StorageSaveMessage {

    @DisplayName("Save Message")
    @Test
    public void SaveMessageTest() {
        StorageManager sm = new StorageManager("testusers.txt", "testmessages");
        UserAuthenticator ua = new UserAuthenticator();
        ua.registerUser("clarize", "1234");
        ua.registerUser("darien", "5678");

        Request r = new Request("hello", "USER", "USER", 0, 1, 2);
        sm.saveMessage(r, ua);

        assertEquals(1, sm.loadChatHistory(1, 2, ua).size());
    }
}