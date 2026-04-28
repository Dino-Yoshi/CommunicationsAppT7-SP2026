package classtest;

import server.StorageManager;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class StorageLoadUsers {

    @DisplayName("Load Users")
    @Test
    public void LoadUsersTest() {
        StorageManager sm = new StorageManager("testusers.txt", "testmessages");
        sm.saveUser("clarize", "1234");
        assertEquals("1234", sm.loadUsers().get("clarize"));
    }
}