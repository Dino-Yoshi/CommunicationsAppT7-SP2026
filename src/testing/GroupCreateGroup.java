package testing;

import server.GroupManager;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;

public class GroupCreateGroup {

    @DisplayName("Create Group")
    @Test
    public void CreateGroupTest() {
        GroupManager gm = new GroupManager();
        assertEquals(true, gm.createGroup("group1", new ArrayList<>()));
    }
}
