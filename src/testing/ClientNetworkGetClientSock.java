package testing;

import networking.ClientNetwork;

//import junit.framework.*;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

public class ClientNetworkGetClientSock {
	
	@DisplayName(value = "ClientNetwork GetClientSock -> null")
	@Test
	public void ClientNetworkGetClientSockTest() {
		ClientNetwork n = new ClientNetwork();
		assertNull(n.getClientSocket());
	}
	
}
