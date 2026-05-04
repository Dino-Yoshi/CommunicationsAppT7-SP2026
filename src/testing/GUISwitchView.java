package testing;

import client.*;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class GUISwitchView {

	@DisplayName(value = "GUI View State Transitions")
	@Test
	public void GUISwitchView() {
		//makes an instance of GUI
		GUI gui = new GUI();
		
		//test default state, which should always be loginview panel
		assertEquals(VIEWSTATE.LOGIN, gui.getCurrentSate(), "Initial state should be LOGIN for the gui");
		
		//dummy user, since its required for client
		User fakeUser = new User("TestUser", "pass123");
		fakeUser.setUID(99);
		gui.setCurrentUser(fakeUser);
		
		//test chatOverlayView
		gui.switchView(VIEWSTATE.MENU);
		assertEquals(VIEWSTATE.MENU, gui.getCurrentSate(), "Failed to switch to ChatOverlayView");
		
		//test transition to Groupcreation view
		gui.switchView(VIEWSTATE.GROUPCREATION);
		assertEquals(VIEWSTATE.GROUPCREATION, gui.getCurrentSate(), "Failed to swithch to GroupCreationView");
		
		//test transition to itpanel 
		gui.switchView(VIEWSTATE.ITPANEL);
		assertEquals(VIEWSTATE.ITPANEL, gui.getCurrentSate(), "Failed to switch to ITPanel");
		
		//test transition back to login 
		
		gui.switchView(VIEWSTATE.LOGIN);
		assertEquals(VIEWSTATE.LOGIN, gui.getCurrentSate(), "Failed to switch back to Login");
	}
}
