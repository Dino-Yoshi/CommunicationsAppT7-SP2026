package classtest;

import server.ContactManager;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ContactAddContact {

    @DisplayName("Add Contact: clarize adds darien")
    @Test
    public void AddContactTest() {
        ContactManager cm = new ContactManager();
        cm.addContact("clarize", "darien");
        assertEquals(true, cm.areContacts("clarize", "darien"));
    }
}