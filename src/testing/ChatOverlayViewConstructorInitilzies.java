package testing;

import client.GUI;
import client.ChatOverlayView;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ChatOverlayViewConstructorInitilzies {

    @DisplayName("ChatOverlayView Constructor Instantiation")
    @Test
    public void ChatOverlayViewConstructorTest() {
        GUI gui = new GUI();
        ChatOverlayView view = new ChatOverlayView(gui);
        
        assertNotNull(view);
    }
}