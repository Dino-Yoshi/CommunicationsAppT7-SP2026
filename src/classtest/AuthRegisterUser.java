package classtest;

import server.UserAuthenticator;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AuthRegisterUser {

    @DisplayName("Register User")
    @Test
    public void RegisterUserTest() {
        UserAuthenticator ua = new UserAuthenticator();
        assertEquals(true, ua.registerUser("clarize", "1234"));
    }
}