package testing;

import client.ITUser;

//import junit.framework.*;

import client.User;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

public class ITUserConstructor {
	
	@DisplayName(value = "ITUser Constructor")
	@Test
	public void ITUserConstructorTest() {
		ITUser u = new ITUser("DinoYoshi", "qwerty");
		assertNotNull(u);
	}
	
}
