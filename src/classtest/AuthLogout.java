package classtest;

import server.UserAuthenticator;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AuthLogout {

    @DisplayName("Logout User")
    @Test
    public void LogoutUserTest() {
        UserAuthenticator ua = new UserAuthenticator();
        ua.registerUser("clarize", "1234");
        ua.authenticate("clarize", "1234");
        ua.logout("clarize");
        assertEquals(false, ua.isLoggedIn("clarize"));
    }
}