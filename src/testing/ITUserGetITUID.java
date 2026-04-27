package testing;

import client.ITUser;

//import junit.framework.*;

import client.User;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

public class ITUserGetITUID {
	
	// Fails on independent usage. ITUID will be 1, when expecting 2.
	@DisplayName(value = "Get ITUID")
	@Test
	public void ITUserGetITUIDTest() {
		ITUser u = new ITUser("DinoYoshi", "qwerty");
		assertEquals(2, u.getITUID()); 
	}
	
}
