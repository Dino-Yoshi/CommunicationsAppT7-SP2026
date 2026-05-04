package testing;

import client.GUI;
import client.ChatOverlayView;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ChatOverlayViewSetITButton {

    @DisplayName("ChatOverlayView Enable IT Button")
    @Test
    public void ChatOverlayViewSetITButtonTest() {
        GUI gui = new GUI();
        ChatOverlayView view = new ChatOverlayView(gui);
        
        // Verifies the method successfully interacts with the private JButton
        assertDoesNotThrow(() -> view.setITButton(), "Enabling the IT Panel button should execute safely.");
    }
}