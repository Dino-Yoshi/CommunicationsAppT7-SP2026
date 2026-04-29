package testing;

import networking.ClientNetwork;

//import junit.framework.*;

import static org.junit.jupiter.api.Assertions.*;

import java.net.Socket;

import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

public class ClientNetworkSetClientSock {
	
	@DisplayName(value = "ClientNetwork SetClientSock -> not null")
	@Test
	public void ClientNetworkSetClientSockTest() {
		
		Socket sock = new Socket();
		ClientNetwork n = new ClientNetwork();
		n.setClientSocket(sock);
		assertNotNull(n.getClientSocket());
		
	}
	
}
