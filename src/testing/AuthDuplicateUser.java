package testing;

import server.UserAuthenticator;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AuthDuplicateUser {

    @DisplayName("Duplicate User Registration")
    @Test
    public void DuplicateUserTest() {
        UserAuthenticator ua = new UserAuthenticator();
        ua.registerUser("clarize", "1234");
        assertEquals(false, ua.registerUser("clarize", "5678"));
    }
}