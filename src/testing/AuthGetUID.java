package testing;

import server.UserAuthenticator;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AuthGetUID {

    @DisplayName("Get User ID")
    @Test
    public void GetUserIdTest() {
        UserAuthenticator ua = new UserAuthenticator();
        ua.registerUser("clarize", "1234");
        assertEquals(1, ua.getIdByUsername("clarize"));
    }
}