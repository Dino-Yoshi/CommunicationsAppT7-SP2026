package testing;

import networking.ClientNetwork;

//import junit.framework.*;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

public class ClientNetworkGetServerIP {
	
	@DisplayName(value = "ClientNetwork GetServerIP -> 192.168.1.70")
	@Test
	public void ClientNetworkGetServerIPTest() {
		ClientNetwork n = new ClientNetwork();
		assertEquals(n.getServerIP(), "192.168.1.70");
	}
	
}
