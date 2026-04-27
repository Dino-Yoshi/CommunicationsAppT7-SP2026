package testing;

//import junit.framework.*;

import client.User;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

public class UserSetUID {
	
	@DisplayName(value = "Set Username: 0")
	@Test
	public void UserSetUIDTest() {
		User u = new User("DinoYoshi", "qwerty");
		u.setUID(0);
		assertEquals(0, u.getUID()); 
	}
	
}
