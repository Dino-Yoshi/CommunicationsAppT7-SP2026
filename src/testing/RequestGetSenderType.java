package testing;

//import junit.framework.*;

import networking.Request;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

public class RequestGetSenderType {
	
	@DisplayName(value = "Request Get Sender Type")
	@Test
	public void RequestGetSenderTypeTest() {
		Request r = new Request(null, "SERVER", null, 0, 0, 0);
		assertEquals(r.getSenderType(), "SERVER");
	}
	
}
