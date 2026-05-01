package server;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

// CLASS DESCRIPTION:
// manages registration, credential validation, id mapping, and active sessions

public class UserAuthenticator {

	// ATTRIBUTES
    private Map<String, String> userCredentials;	// maps usernames to passwords
    private Map<String, String> ITUserCredentials;  // maps IT combinations
    private Map<Integer, String> idToUsername; 		// maps numeric ids to usernames
    private Map<String, Integer> usernameToId; 		// maps usernames to numeric ids
    private Set<String> activeSessions; 			// stores usernames that are currently logged in
    private int nextUserId; 						// stores the next id to assign to a new user

    // CONSTRUCTOR
    public UserAuthenticator() {
        this.userCredentials = new LinkedHashMap<>(); 	// creates the username to password map
        this.ITUserCredentials = new LinkedHashMap<>(); // creates the IT combination map
        this.idToUsername = new LinkedHashMap<>(); 		// creates the id to username map
        this.usernameToId = new LinkedHashMap<>(); 		// creates the username to id map
        this.activeSessions = new HashSet<>(); 			// creates the active session set
        this.nextUserId = 1; 							// starts user id assignment at one
    }
    
    // METHODS

    // validates username and password and logs user in if valid
    public synchronized boolean authenticate(String username, String password) {
        String cleanUsername = normalize(username);		// trims username, rejects blank input
        String cleanPassword = password;				// keeps password as is
    	
        if (cleanUsername == null || cleanPassword == null) { 	// checks for invalid null input
            return false; 										// rejects authentication
        }	// end missing-input check
        if (!userCredentials.containsKey(cleanUsername)) { 	// checks if username exists
            return false; 									// rejects authentication
        }	// end unknown-user check
        boolean valid = userCredentials.get(cleanUsername).equals(cleanPassword); // compares provided password to stored password
        if (valid) {							// checks if the credentials matched
            activeSessions.add(cleanUsername);	// adds the username to the active session set
        }	// end login success check
        return valid;	// returns authentication success or fail
    }
    
    public synchronized boolean ITAuthenticate(String username, String password) {
        String cleanUsername = normalize(username);		// trims username, rejects blank input
        String cleanPassword = password;				// keeps password as is
    	
        if (cleanUsername == null || cleanPassword == null) { 	// checks for invalid null input
            return false; 										// rejects authentication
        }	// end missing-input check
        if (!ITUserCredentials.containsKey(cleanUsername)) { 	// checks if username exists
            return false; 									// rejects authentication
        }	// end unknown-user check
        boolean valid = ITUserCredentials.get(cleanUsername).equals(cleanPassword); // compares provided password to stored password
        if (valid) {							// checks if the credentials matched
            activeSessions.add(cleanUsername);	// adds the username to the active session set
        }	// end login success check
        return valid;	// returns authentication success or fail
    }

    // registers a new user if the username is unused and valid
    public synchronized boolean registerUser(String username, String password) {
        String cleanUsername = normalize(username);	// cleans username
        String cleanPassword = password;			// keeps password as is
        
        if (cleanUsername == null || cleanPassword == null || cleanUsername.isBlank() || cleanPassword.isBlank()) {	// checks for invalid input
            return false;	// rejects invalid registration input
        }	// end invalid-input check
        if (userCredentials.containsKey(cleanUsername)) {	// checks if the username is already taken
            return false;	// rejects duplicate registration
        }	// end duplicate check
        userCredentials.put(cleanUsername, cleanPassword);	// stores the username and password
        usernameToId.put(cleanUsername, nextUserId);		// maps the username to the next available id
        idToUsername.put(nextUserId, cleanUsername);		// maps the new id back to the username
        nextUserId++;	// increments the id counter for the next new user
        return true;	// returns success
    }
    
    synchronized boolean registerGroup(String groupName) {
    	
    	if(getIdByUsername(groupName) != null) {
    		return false; // this group chat already exists.
    	}
    	
        usernameToId.put(groupName, nextUserId);		// maps the username to the next available id
        idToUsername.put(nextUserId, groupName);		// maps the new id back to the username
        nextUserId++;
    	
    	return true;
    }

    // removes a user from the active session set
    public synchronized void logout(String username) {
        String cleanUsername = normalize(username);	// cleans username
    	
        if (cleanUsername != null) {
    		activeSessions.remove(cleanUsername); // this removes the username from the active sessions
    	}	// end valid-username check
    }

    // checks if a username already exists
    public synchronized boolean userExists(String username) {
        String cleanUsername = normalize(username);	// cleans username
        return cleanUsername != null && (userCredentials.containsKey(cleanUsername) || ITUserCredentials.containsKey(cleanUsername));	// returns true if username exists 
    }

    // checks if a username is currently logged in
    public synchronized boolean isLoggedIn(String username) {
        String cleanUsername = normalize(username);	// cleans username
        return cleanUsername != null && activeSessions.contains(cleanUsername);	// returns true if username is logged in
    }
    // check if username is inside the IT map.
    public synchronized boolean isIT(String username) {
        String cleanUsername = normalize(username);	// cleans username
        return cleanUsername != null && ITUserCredentials.containsKey(cleanUsername);	// returns true if username is logged in
    }

    // returns the username associated with a numeric id
    public synchronized String getUsernameById(int id) {
        return idToUsername.get(id);	// returns the mapped username for the id
    }

    // returns the numeric id associated with a username
    public synchronized Integer getIdByUsername(String username) {
        String cleanUsername = normalize(username);	// cleans username
        return cleanUsername == null ? null : usernameToId.get(cleanUsername);	// returns id or null if unknown
    }

    
    // loads persisted username and password data
    public synchronized void loadUsers(Map<String, String> users) {
        userCredentials.clear();	// clears any existing credential data
        idToUsername.clear(); 		// clears any existing id to username mappings
        usernameToId.clear(); 		// clears any existing username to id mappings
        activeSessions.clear(); 	// clears active sessions
        nextUserId = 1; 			// resets the next id counter
        
        if (users == null) { 		// checks if there are users to load
        	return;					// stops loading if none
        }	// end null check
        for (Map.Entry<String, String> entry : users.entrySet()) { // start loop through each user entry
            String username = normalize(entry.getKey()); 	// trims and gets the username from the current entry
            String password = entry.getValue();				// gets the password from the current entry
            if (username != null && password != null && !userCredentials.containsKey(username)) {	// checks that the saved user is valid and not duplicated
                userCredentials.put(username, password);	// stores the credential pair
                usernameToId.put(username, nextUserId); 	// assigns an id to the username
                idToUsername.put(nextUserId, username); 	// maps that numeric id back to the username
                nextUserId++;	// increments the id counter for the next user
            }	// end valid user check
        }	// end loop
    }
    
    public synchronized void loadITUsers(Map<String, String> users) {
        ITUserCredentials.clear();	// clears any existing credential data for ITUsers
        
        if (users == null) { 		// checks if there are users to load
        	return;					// stops loading if none
        }	// end null check
        for (Map.Entry<String, String> entry : users.entrySet()) { // start loop through each user entry
            String username = normalize(entry.getKey()); 	// trims and gets the username from the current entry
            String password = entry.getValue();				// gets the password from the current entry
            if (username != null && password != null && !ITUserCredentials.containsKey(username)) {	// checks that the saved user is valid and not duplicated
            	ITUserCredentials.put(username, password);	// stores the credential pair
                usernameToId.put(username, nextUserId); 	// assigns an id to the username
                idToUsername.put(nextUserId, username); 	// maps that numeric id back to the username
                nextUserId++;	// increments the id counter for the next user
            }	// end valid user check
        }	// end loop
    }

    // returns a copy of the credential map
    public synchronized Map<String, String> getUserCredentials() {
        return new LinkedHashMap<>(userCredentials);	// returns a copy of the credential map
    }
    
    // note: searchUsers in UserAuthenticator since it owns the registered user map
    // searches registered usernames by a partial text query
    public synchronized List<String> searchUsers(String query) {
        String normalizedQuery = normalize(query);	// cleans search query
        List<String> results = new ArrayList<>();	// creates the list that will store matching usernames
        
        if (normalizedQuery == null) { 				// checks if the search query is blank or missing
            return results;	// returns an empty result list for invalid searches
        }	// end invalid-query check
        
        String lowerQuery = normalizedQuery.toLowerCase();		// converts the query to lowercase for case-insensitive searching
        
        for (String username : userCredentials.keySet()) {		// loops through every registered username
            if (username.toLowerCase().contains(lowerQuery)) {	// checks whether the username contains the query text
                results.add(username);	// adds the matching username to the result list
            }	// end match check
        }	// end loop
        
        for (String username : ITUserCredentials.keySet()) { // queries the ITUser set as well. 
        	if (username.toLowerCase().contains(lowerQuery)) {	// checks whether the username contains the query text
                results.add(username);	// adds the matching username to the result list
            }	// end match check
        }
        
        return results;	// returns all usernames that matched the search query
    }

    // cleans username and query text
    private String normalize(String value) {
        if (value == null) {	// checks if value is missing
            return null; 		// returns null for missing values
        }	// end null check
        
        String trimmed = value.trim();				// removes leading and trailing spaces
        return trimmed.isEmpty() ? null : trimmed;	// returns null for blank text, otherwise returns the cleaned text
    }
}