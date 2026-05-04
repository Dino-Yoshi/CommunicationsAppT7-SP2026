package testing;

import client.GUI;
import client.User;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class GUISetCurrentUser {

    @DisplayName("GUI Set and Get Current User")
    @Test
    public void GUISetCurrentUserTest() {
        GUI gui = new GUI();
        User testUser = new User("TestUserForJunitTest", "password123");
        testUser.setUID(10);
        
        gui.setCurrentUser(testUser);
        
        assertNotNull(gui.getCurrentUser());
        assertEquals("TestUserForJunitTest", gui.getCurrentUser().getUsername());
        assertEquals(10, gui.getCurrentUser().getUID());
    }
}