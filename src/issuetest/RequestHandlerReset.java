package issuetest;

import server.RequestHandler;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class RequestHandlerReset {

    @DisplayName("RequestHandler Reset Creates Fresh Instance")
    @Test
    public void RequestHandlerSingletonResetTest() {
        RequestHandler.resetInstanceForTests();
        RequestHandler handler1 = RequestHandler.getInstance();

        RequestHandler.resetInstanceForTests();
        RequestHandler handler2 = RequestHandler.getInstance();

        assertNotSame(handler1, handler2);
    }
}
