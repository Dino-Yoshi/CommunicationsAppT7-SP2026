package testing;

import client.GUI;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class GUIGetNetworkClient {

    @DisplayName("GUI Initializes Network Client")
    @Test
    public void GUIGetNetworkClientTest() {
        GUI gui = new GUI();
        
        // The network client should be automatically created in the GUI constructor, so should not be null
        assertNotNull(gui.getNetworkClient(), "Network client should not be null upon GUI creation.");
    }
}