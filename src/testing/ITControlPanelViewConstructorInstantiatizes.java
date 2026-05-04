package testing;

import client.GUI;
import client.ITControlPanelView;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ITControlPanelViewConstructorInstantiatizes {

    @DisplayName("ITControlPanelView Constructor Instantiation")
    @Test
    public void ITControlPanelViewConstructorTest() {
        GUI gui = new GUI();
        ITControlPanelView view = new ITControlPanelView(gui);
        
        assertNotNull(view);
    }
}