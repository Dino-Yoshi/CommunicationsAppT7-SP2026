package testing;

import client.GUI;
import client.GroupCreationView;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class GroupCreationViewConstructorInstantializes {

    @DisplayName("GroupCreationView Constructor Instantiation")
    @Test
    public void GroupCreationViewConstructorTest() {
        GUI gui = new GUI();
        GroupCreationView view = new GroupCreationView(gui);
        
        assertNotNull(view);
    }
}