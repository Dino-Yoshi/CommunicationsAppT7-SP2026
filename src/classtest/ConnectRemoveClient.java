package classtest;

import server.ConnectionManager;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ConnectRemoveClient {

    @DisplayName("Remove Client")
    @Test
    public void RemoveClientTest() {
        ConnectionManager cm = new ConnectionManager();
        Object client = new Object();
        cm.bindUsername("clarize", client);
        cm.removeClient("clarize");
        assertEquals(false, cm.isConnected("clarize"));
    }
}