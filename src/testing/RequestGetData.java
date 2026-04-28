package testing;

//import junit.framework.*;

import networking.Request;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

public class RequestGetData {
	
	@DisplayName(value = "Request Get Data")
	@Test
	public void RequestGetDataTest() {
		Request r = new Request("Hello World!", null, null, 0, 0, 0);
		assertEquals(r.getData(), "Hello World!");
	}
	
}
