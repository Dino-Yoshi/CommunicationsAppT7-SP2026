package testing;

import server.ConnectionManager;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ConnectAddClient {

    @DisplayName("Add Client")
    @Test
    public void AddClientTest() {
        ConnectionManager cm = new ConnectionManager();
        Object client = new Object();
        cm.addClient(client);
        assertEquals(1, cm.getActiveClientCount());
    }
}