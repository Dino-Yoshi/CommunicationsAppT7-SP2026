package testing;

//import junit.framework.*;

import networking.Request;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

public class RequestGetSenderID {
	
	@DisplayName(value = "Request Get Sender ID")
	@Test
	public void RequestGetSenderIDTest() {
		Request r = new Request(null, "null", null, 0, 0, 0);
		assertEquals(r.getSenderID(), 0);
	}
	
}
