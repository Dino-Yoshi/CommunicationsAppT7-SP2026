package classtest;

import server.GroupManager;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;

public class GroupRemoveMember {

    @DisplayName("Remove Member")
    @Test
    public void RemoveMemberTest() {
        GroupManager gm = new GroupManager();
        gm.createGroup("group1", new ArrayList<>());
        gm.addMember("group1", "clarize");
        gm.removeMember("group1", "clarize");
        assertEquals(false, gm.isMember("group1", "clarize"));
    }
}
