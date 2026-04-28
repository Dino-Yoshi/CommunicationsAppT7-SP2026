package classtest;

import server.GroupManager;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;

public class GroupAddMember {

    @DisplayName("Add Member")
    @Test
    public void AddMemberTest() {
        GroupManager gm = new GroupManager();
        gm.createGroup("group1", new ArrayList<>());
        gm.addMember("group1", "clarize");
        assertEquals(true, gm.isMember("group1", "clarize"));
    }
}