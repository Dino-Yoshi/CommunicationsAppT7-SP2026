package server;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

// CLASS DESCRIPTION:
// manages each user's contact list

public class ContactManager {

    // ATTRIBUTES
    private Map<String, Set<String>> contactsByUser;	// maps each username to that user's unique contact list

    // CONSTRUCTOR
    public ContactManager() {
        this.contactsByUser = new LinkedHashMap<>();	// creates contact map
    }

    // METHODS

	// adds a contact to one user's contact list
    public synchronized boolean addContact(String ownerUsername, String contactUsername) {
        String cleanOwner = normalize(ownerUsername);			// cleans the owner username
        String cleanContact = normalize(contactUsername);		// cleans the contact username
        
        if (cleanOwner == null || cleanContact == null) { 		// checks if either username is missing or blank
            return false;	// rejects invalid contact additions
        }	// end missing username check
        if (cleanOwner.equals(cleanContact)) {	// checks if the user is trying to add themself
            return false;	// rejects self-contact additions
        }	// end self-contact check
        
        contactsByUser.putIfAbsent(cleanOwner, new LinkedHashSet<>());	// creates the owner's contact set if needed
        return contactsByUser.get(cleanOwner).add(cleanContact); 		// adds contact and returns true only if it was new
    }

    // removes a contact from one user's contact list
    public synchronized boolean removeContact(String ownerUsername, String contactUsername) {
        String cleanOwner = normalize(ownerUsername);			// cleans the owner username
        String cleanContact = normalize(contactUsername);		// cleans the contact username
        
        if (cleanOwner == null || cleanContact == null) { 		// checks if either username is missing or blank
            return false;	// rejects invalid contact removals
        }	// end missing username check
        if (!contactsByUser.containsKey(cleanOwner)) {	// checks if the owner has a contact list
            return false;	// nothing removed if the owner has no contacts
        }	// end missing owner check
        return contactsByUser.get(cleanOwner).remove(cleanContact);	// removes contact and returns true only if it existed
    }

    // returns all contacts for one user
    public synchronized List<String> getContacts(String ownerUsername) {
        String cleanOwner = normalize(ownerUsername);			// cleans the owner username
        
        if (cleanOwner == null) {		// checks if the owner username is invalid
            return new ArrayList<>();	// returns an empty list for invalid usernames
        }	// end invalid owner check
        if (!contactsByUser.containsKey(cleanOwner)) {	// checks if the owner has a contact list
            return new ArrayList<>();					// returns an empty list if the owner has no contacts
        } 	// end missing contacts check
        return new ArrayList<>(contactsByUser.get(cleanOwner));	// returns a copy of the owner's contacts
    }

    // searches one user's contact list
    public synchronized List<String> searchContacts(String ownerUsername, String query) {
        String cleanOwner = normalize(ownerUsername);	// cleans the owner username
        String cleanQuery = normalize(query); 			// cleans the search query
        List<String> results = new ArrayList<>();		// creates the list that will store matching contacts
        
        if (cleanOwner == null || cleanQuery == null) { // checks if owner or query is invalid
            return results;	// returns an empty list for invalid searches
        }	// end invalid search check
        if (!contactsByUser.containsKey(cleanOwner)) { 	// checks if the owner has contacts
            return results;								// returns an empty list if the owner has no contacts
        }	// end missing contacts check
        
        String lowerQuery = cleanQuery.toLowerCase();			// converts query to lowercase for case-insensitive searching
        
        for (String contact : contactsByUser.get(cleanOwner)) {	// start loop through each contact owned by the user
            if (contact.toLowerCase().contains(lowerQuery)) { 	// checks if the contact contains the query text
                results.add(contact);	// adds the matching contact to the result list
            } 	// end match check
        }		// end loop
        return results;	// returns all contacts that matched the query
    }

    // checks if one user has another user in their contacts
    public synchronized boolean areContacts(String ownerUsername, String contactUsername) {
        String cleanOwner = normalize(ownerUsername);		// cleans the owner username
        String cleanContact = normalize(contactUsername);	// cleans the contact username
        
        if (cleanOwner == null || cleanContact == null) { 	// checks if either username is invalid
            return false;	// invalid usernames cannot be contacts
        }	// ends invalid username check
        if (!contactsByUser.containsKey(cleanOwner)) {	// checks if the owner has a contact list
            return false;								// returns false when the owner has no contacts
        }	// ends missing contacts check
        return contactsByUser.get(cleanOwner).contains(cleanContact);	// returns if the contact is in the owner's contact list
    }
    
    // cleans usernames and query text
    private String normalize(String value) {
        if (value == null) {	// checks if the value is missing
            return null; 		// returns null for missing values
        }	// ends null check
        
        String trimmed = value.trim();				// removes leading and trailing spaces
        return trimmed.isEmpty() ? null : trimmed;	// returns null for blank text, otherwise returns the cleaned text
    }
}