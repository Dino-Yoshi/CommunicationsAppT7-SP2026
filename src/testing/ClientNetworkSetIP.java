package testing;

import networking.ClientNetwork;

//import junit.framework.*;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

public class ClientNetworkSetIP {
	
	@DisplayName(value = "ClientNetwork SetIP -> 127.0.0.1")
	@Test
	public void ClientNetworkSetIPTest() {
		ClientNetwork n = new ClientNetwork();
		n.setServerIP("127.0.0.1");
		assertEquals(n.getServerIP(), "127.0.0.1");
	}
	
}
