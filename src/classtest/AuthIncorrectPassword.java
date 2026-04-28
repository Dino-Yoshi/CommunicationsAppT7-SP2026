package classtest;

import server.UserAuthenticator;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AuthIncorrectPassword {

    @DisplayName("Reject Wrong Password")
    @Test
    public void AuthenticateWrongPasswordTest() {
        UserAuthenticator ua = new UserAuthenticator();
        ua.registerUser("clarize", "1234");
        assertEquals(false, ua.authenticate("clarize", "9999"));
    }
}