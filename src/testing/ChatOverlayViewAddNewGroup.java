package testing;

import client.GUI;
import client.ChatOverlayView;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ChatOverlayViewAddNewGroup {

    @DisplayName("ChatOverlayView Add New Group to UI")
    @Test
    public void ChatOverlayViewAddNewGroupTest() {
        GUI gui = new GUI();
        ChatOverlayView view = new ChatOverlayView(gui);
        
        // Verifies that adding a group name string to the UI list model doesn't crash
        assertDoesNotThrow(() -> view.addNewGroup("TestGroup123"), "Adding a new group to the UI should execute safely.");
    }
}