package testing;

import networking.ServerNetwork;

//import junit.framework.*;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

public class ServerNetworkGetStatus {
	
	@DisplayName(value = "ServerNetwork GetStatus -> NULL")
	@Test
	public void ServerNetworkGetStatusTest() {
		ServerNetwork n = new ServerNetwork();
		assertNull(n.getStatus());
	}
	
}
