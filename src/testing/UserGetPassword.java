package testing;

//import junit.framework.*;

import client.User;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

public class UserGetPassword {
	
	@DisplayName(value = "Get Password")
	@Test
	public void UserGetUIDTest() {
		User u = new User("DinoYoshi", "qwerty");
		assertEquals("qwerty", u.getPassword()); 
	}
	
}
