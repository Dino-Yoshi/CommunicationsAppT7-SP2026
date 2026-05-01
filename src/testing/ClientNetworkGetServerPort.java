package testing;

import networking.ClientNetwork;

//import junit.framework.*;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

public class ClientNetworkGetServerPort {
	
	@DisplayName(value = "ClientNetwork GetServerPort -> 7777")
	@Test
	public void ClientNetworkGetServerPortTest() {
		ClientNetwork n = new ClientNetwork();
		assertEquals(n.getServerPort(), 7777);
	}
	
}
