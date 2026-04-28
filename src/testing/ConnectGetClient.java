package testing;

import server.ConnectionManager;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ConnectGetClient {

    @DisplayName("Get Client By Username")
    @Test
    public void GetClientTest() {
        ConnectionManager cm = new ConnectionManager();
        Object client = new Object();
        cm.bindUsername("clarize", client);
        assertEquals(client, cm.getClient("clarize"));
    }
}