package testing;

import networking.ClientNetwork;

//import junit.framework.*;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

public class ClientNetworkConstructor {
	
	@DisplayName(value = "ClientNetwork Constructor -> not null")
	@Test
	public void ClientNetworkConstructorTest() {
		ClientNetwork n = new ClientNetwork();
		assertNotNull(n);
	}
	
}
