package testing;

import networking.ServerNetwork;

//import junit.framework.*;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

public class ServerNetworkConstructor {
	
	@DisplayName(value = "ServerNetwork Constructor")
	@Test
	public void ServerNetworkConstructorTest() {
		ServerNetwork n = new ServerNetwork();
		assertNotNull(n);
	}
	
}
