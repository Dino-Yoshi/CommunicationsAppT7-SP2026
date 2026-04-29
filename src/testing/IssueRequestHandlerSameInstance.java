package testing;

import server.RequestHandler;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class IssueRequestHandlerSameInstance {

    @DisplayName("RequestHandler Singleton Returns Same Instance")
    @Test
    public void RequestHandlerSingletonSameInstanceTest() {
        RequestHandler.resetInstanceForTests();

        RequestHandler handler1 = RequestHandler.getInstance();
        RequestHandler handler2 = RequestHandler.getInstance();

        assertSame(handler1, handler2);
    }
}
