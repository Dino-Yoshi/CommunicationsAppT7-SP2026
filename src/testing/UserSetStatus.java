package testing;

//import junit.framework.*;

import client.User;
import client.User.USERSTATUS;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

public class UserSetStatus {
	
	@DisplayName(value = "Set Status: Offline")
	@Test
	public void UserSetStatusTest() {
		User u = new User("DinoYoshi", "qwerty");
		u.setStatus(3);
		assertEquals(USERSTATUS.OFFLINE, u.getStatus()); 
	}
	
}
