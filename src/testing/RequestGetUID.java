package testing;

//import junit.framework.*;

import networking.Request;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

public class RequestGetUID {
	
	@DisplayName(value = "Request Get UID")
	@Test
	public void RequestGetUIDTest() {
		Request r = new Request(null, null, null, 0, 0, 0);
		assertEquals(r.getUID(), "r2");
	}
	
}
