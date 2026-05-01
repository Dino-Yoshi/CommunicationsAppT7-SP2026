package testing;

import networking.ServerNetwork;

//import junit.framework.*;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

public class ServerNetworkGetNumConnections {
	
	@DisplayName(value = "ServerNetwork NumConnections -> 0")
	@Test
	public void ServerNetworkGetNumConnectionsTest() {
		ServerNetwork n = new ServerNetwork();
		assertEquals(n.getNumConnections(), 0);
	}
	
}
