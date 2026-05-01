package testing;

import networking.ClientNetwork;
import networking.ClientNetwork.CLIENTSTATUS;

//import junit.framework.*;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

public class ClientNetworkGetStatus {
	
	@DisplayName(value = "ClientNetwork GetStatus -> DISCONNECTED")
	@Test
	public void ClientNetworkGetStatusTest() {
		ClientNetwork n = new ClientNetwork();
		assertEquals(n.getStatus(), CLIENTSTATUS.DISCONNECTED);
	}
	
}
