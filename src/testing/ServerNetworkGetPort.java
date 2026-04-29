package testing;

import networking.ServerNetwork;

//import junit.framework.*;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

public class ServerNetworkGetPort {
	
	@DisplayName(value = "ServerNetwork GetPort -> 7777")
	@Test
	public void ServerNetworkGetPortTest() {
		ServerNetwork n = new ServerNetwork();
		assertEquals(n.getPort(), 7777);
	}
	
}
