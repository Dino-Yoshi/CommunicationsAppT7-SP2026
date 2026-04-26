package testing;

//import junit.framework.*;

import client.User;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

public class UserSetUsername {
	
	@DisplayName(value = "Set Username: Damien")
	@Test
	public void UserSetUsernameTest() {
		User u = new User("DinoYoshi", "qwerty");
		u.setUsername("Damien");
		assertEquals("Damien", u.getUsername()); 
	}
	
}
