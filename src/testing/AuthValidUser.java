package testing;

import server.UserAuthenticator;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AuthValidUser {

    @DisplayName("Authenticate Valid User")
    @Test
    public void AuthenticateUserTest() {
        UserAuthenticator ua = new UserAuthenticator();
        ua.registerUser("clarize", "1234");
        assertEquals(true, ua.authenticate("clarize", "1234"));
    }
}