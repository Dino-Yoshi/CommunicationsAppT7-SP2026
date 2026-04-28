package testing;

import server.StorageManager;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class StorageSaveUser {

    @DisplayName("Save User")
    @Test
    public void SaveUserTest() {
        StorageManager sm = new StorageManager(
                "testITUsers.txt",
                "testusers.txt",
                "testcontacts.txt",
                "testmessages"
        );

        sm.saveUser("clarize", "1234");

        assertEquals(true, sm.loadUsers().containsKey("clarize"));
    }
}