package testing;

//import junit.framework.*;

import networking.Request;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

public class RequestGetRecipientType {
	
	@DisplayName(value = "Request Get Recipient Type")
	@Test
	public void RequestGetRecipientTypeTest() {
		Request r = new Request(null, null, "USER", 0, 0, 0);
		assertEquals(r.getRecipientType(), "USER");
	}
	
}
