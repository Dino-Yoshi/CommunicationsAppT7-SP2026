package testing;

import server.RequestHandler;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class IssueRequestHandlerSharedState {

    @DisplayName("RequestHandler Singleton Shares State")
    @Test
    public void RequestHandlerSingletonSharedStateTest() {
        RequestHandler.resetInstanceForTests();

        RequestHandler handler1 = RequestHandler.getInstance();
        RequestHandler handler2 = RequestHandler.getInstance();

        assertEquals(0, handler1.getNumRequests());
        assertEquals(0, handler2.getNumRequests());
        assertSame(handler1, handler2);
    }
}
