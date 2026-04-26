package testing;

//import junit.framework.*;

import client.User;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

public class UserGetUID {
	
	@DisplayName(value = "Get UID")
	@Test
	public void UserGetUIDTest() {
		User u = new User("DinoYoshi", "qwerty");
		assertEquals(2, u.getUID()); 
		// UID is 2, as this is the second User Created on Suite run.
		// UID is 1 when ran independently. 
	}
	
}
