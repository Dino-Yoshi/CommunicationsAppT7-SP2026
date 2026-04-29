package testing;

import server.ContactManager;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ContactSearchContact {

    @DisplayName("Search Contact")
    @Test
    public void SearchContactTest() {
        ContactManager cm = new ContactManager();
        cm.addContact("clarize", "darien");
        assertEquals(1, cm.searchContacts("clarize", "dar").size());
    }
}
