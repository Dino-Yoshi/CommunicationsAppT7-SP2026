package classtest;

import server.GroupManager;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;

public class GroupSearchGroup {

    @DisplayName("Search Group")
    @Test
    public void SearchGroupTest() {
        GroupManager gm = new GroupManager();
        gm.createGroup("CS401Group", new ArrayList<>());
        assertEquals(1, gm.searchGroups("CS4").size());
    }
}