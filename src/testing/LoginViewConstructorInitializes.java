package testing;

import client.GUI;
import client.LoginView;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class LoginViewConstructorInitializes {

    @DisplayName("LoginView Constructor Instantiation")
    @Test
    public void LoginViewConstructorTest() {
        GUI gui = new GUI();
        LoginView view = new LoginView(gui);
        
        assertNotNull(view);
    }
}