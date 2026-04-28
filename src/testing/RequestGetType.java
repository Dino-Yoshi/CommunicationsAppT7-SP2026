package testing;

//import junit.framework.*;

import networking.Request;
import networking.Request.REQUESTTYPE;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

public class RequestGetType {
	
	@DisplayName(value = "Request Get Type")
	@Test
	public void RequestGetTypeTest() {
		Request r = new Request(null, null, null, 0, 0, 0);
		assertEquals(r.getType(), REQUESTTYPE.SENDMESSAGE);
	}
	
}
