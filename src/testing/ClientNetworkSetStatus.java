package testing;

import networking.ClientNetwork;
import networking.ClientNetwork.CLIENTSTATUS;

//import junit.framework.*;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

public class ClientNetworkSetStatus {
	
	@DisplayName(value = "ClientNetwork SetStatus -> CONNECTED")
	@Test
	public void ClientNetworkSetStatusTest() {
		ClientNetwork n = new ClientNetwork();
		
		n.setStatus(0);
		
		assertEquals(n.getStatus(), CLIENTSTATUS.CONNECTED);
	}
	
}
