package issuetest;

import server.RequestHandler;
import networking.Request;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ContactPersistence {

    @DisplayName("Contacts Persist Through Server Boot")
    @Test
    public void ContactPersistenceTest() {
        RequestHandler.resetInstanceForTests(); 
        RequestHandler handler1 = RequestHandler.getInstance();

        handler1.doLogIn(new Request("root,sudo", "USER", "SERVER", 7, -1, -1), new Object());
        int rootId = handler1.getAuth().getIdByUsername("root");

        handler1.doRegister(new Request("clarize,1234", "USER", "SERVER", 1, rootId, -1));
        handler1.doRegister(new Request("bot,5678", "USER", "SERVER", 1, rootId, -1));

        handler1.doLogIn(new Request("clarize,1234", "USER", "SERVER", 7, -1, -1), new Object());
        int clarizeId = handler1.getAuth().getIdByUsername("clarize");

        handler1.doAddContact(new Request("bot", "USER", "SERVER", 9, clarizeId, -1));

        RequestHandler.resetInstanceForTests();
        RequestHandler handler2 = RequestHandler.getInstance();
        int clarizeIdAfterBoot = handler2.getAuth().getIdByUsername("clarize");

        Request contacts = handler2.doViewContacts(new Request("", "USER", "SERVER", 3, clarizeIdAfterBoot, -1));

        assertEquals(true, contacts.getData().contains("bot"));
    }
}