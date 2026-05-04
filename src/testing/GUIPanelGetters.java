package testing;

import client.GUI;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class GUIPanelGetters {

    @DisplayName("GUI Retrieves Specific View Panels")
    @Test
    public void GUIPanelGettersTest() {
        GUI gui = new GUI();
        
        // Verifies the GUI class correctly instantiates and returns its panels
        assertNotNull(gui.getLoginView(), "GUI should return a valid LoginView instance.");
        assertNotNull(gui.getChatOverlayView(), "GUI should return a valid ChatOverlayView instance.");
    }
}