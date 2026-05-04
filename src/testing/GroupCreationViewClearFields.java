package testing;

import client.GUI;
import client.GroupCreationView;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class GroupCreationViewClearFields {

    @DisplayName("GroupCreationView Clear Fields Executable")
    @Test
    public void GroupCreationViewClearFieldsTest() {
        GUI gui = new GUI();
        GroupCreationView view = new GroupCreationView(gui);
        
        // Verifies that clearing the group name and member lists works safely
        assertDoesNotThrow(() -> view.clearFields(), "Clearing Group Creation fields should not throw an exception.");
    }
}