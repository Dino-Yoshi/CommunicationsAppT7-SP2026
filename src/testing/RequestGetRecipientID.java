package testing;

//import junit.framework.*;

import networking.Request;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

public class RequestGetRecipientID {
	
	@DisplayName(value = "Request Get Recipient ID")
	@Test
	public void RequestGetRecipientIDTest() {
		Request r = new Request(null, "null", null, 0, 0, 0);
		assertEquals(r.getRecipientID(), 0);
	}
	
}
