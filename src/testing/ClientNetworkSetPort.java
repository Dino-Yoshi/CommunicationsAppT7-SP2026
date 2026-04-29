package testing;

import networking.ClientNetwork;

//import junit.framework.*;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

public class ClientNetworkSetPort {
	
	@DisplayName(value = "ClientNetwork SetServerPort -> 8080")
	@Test
	public void ClientNetworkSetServerPortTest() {
		ClientNetwork n = new ClientNetwork();
		n.setServerPort(8080);
		assertEquals(n.getServerPort(), 8080);
	}
	
}
