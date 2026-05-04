package testing;

import client.GUI;
import client.LoginView;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class LoginViewClearFields {

    @DisplayName("LoginView Clear Fields Executable")
    @Test
    public void LoginViewClearFieldsTest() {
        GUI gui = new GUI();
        LoginView view = new LoginView(gui);
        
        // Verifies that the clearFields method executes safely
        assertDoesNotThrow(() -> view.clearFields(), "Clearing Login fields should not throw an exception.");
    }
}