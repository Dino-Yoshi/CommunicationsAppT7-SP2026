package testing;

import server.StorageManager;
import networking.Request;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class StorageOfflineMessages {

    @DisplayName("Store Offline Message")
    @Test
    public void StoreOfflineMessageTest() {
        StorageManager sm = new StorageManager(
                "testITUsers.txt",
                "testusers.txt",
                "testcontacts.txt",
                "testmessages"
        );

        Request r = new Request("hello,clarize", "USER", "USER", 0, 1, 2);
        sm.storeOfflineMessage(2, r);

        assertEquals(1, sm.getOfflineMessages(2).size());
    }
}