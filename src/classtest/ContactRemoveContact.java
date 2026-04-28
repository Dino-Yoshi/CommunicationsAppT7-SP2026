package classtest;

import server.ContactManager;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ContactRemoveContact {

    @DisplayName("Remove Contact: clarize removes victor")
    @Test
    public void RemoveContactTest() {
        ContactManager cm = new ContactManager();
        cm.addContact("clarize", "victor");
        cm.removeContact("clarize", "victor");
        assertEquals(false, cm.areContacts("clarize", "victor"));
    }
}