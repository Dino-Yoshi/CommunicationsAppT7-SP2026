package testing;

//import junit.framework.*;

import client.User;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

public class UserSetPassword {
	
	@DisplayName(value = "Set Password: letmein")
	@Test
	public void UserSetPasswordTest() {
		User u = new User("DinoYoshi", "qwerty");
		u.setPassword("letmein");
		assertEquals("letmein", u.getPassword()); 
	}
	
}
