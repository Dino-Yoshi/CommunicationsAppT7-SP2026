package testing;

//import junit.framework.*;

import client.User;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

public class UserIsIT {
	
	@DisplayName(value = "Is IT")
	@Test
	public void UserIsITTest() {
		User u = new User("DinoYoshi", "qwerty");
		assertEquals(false, u.isIT()); 
	}
	
}
