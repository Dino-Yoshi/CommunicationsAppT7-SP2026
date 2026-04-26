package testing;

//import junit.framework.*;

import client.User;
import client.User.USERSTATUS;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

public class UserGetStatus {
	
	@DisplayName(value = "Get Status")
	@Test
	public void UserGetStatusTest() {
		User u = new User("DinoYoshi", "qwerty");
		assertEquals(USERSTATUS.ONLINE, u.getStatus()); 
	}
	
}
