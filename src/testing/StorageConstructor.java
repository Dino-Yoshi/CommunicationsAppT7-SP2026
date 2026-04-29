package testing;

import server.StorageManager;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class StorageConstructor {

    @DisplayName("Storage Constructor")
    @Test
    public void StorageConstructorTest() {
        StorageManager sm = new StorageManager(
                "testITUsers.txt",
                "testusers.txt",
                "testcontacts.txt",
                "testmessages"
        );

        assertNotNull(sm);
    }
}