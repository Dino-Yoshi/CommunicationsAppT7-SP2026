package classtest;

import server.ContactManager;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ContactDuplicateContact {

    @DisplayName("Duplicate Contact Add")
    @Test
    public void DuplicateContactTest() {
        ContactManager cm = new ContactManager();
        cm.addContact("darien", "victor");
        assertEquals(false, cm.addContact("darien", "victor"));
    }
}