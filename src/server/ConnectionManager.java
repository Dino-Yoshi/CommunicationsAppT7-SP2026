package server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// CLASS DESCRIPTION:
// tracks active client handler objects and maps usernames to connected clients

public class ConnectionManager {

    // ATTRIBUTES
    private List<Object> activeClients;		// stores active client handler objects
    private Map<String, Object> userMap;	// maps usernames to active client handler objects

    // CONSTRUCTOR
    public ConnectionManager() {
        this.activeClients = new ArrayList<>();	// creates the active client list
        this.userMap = new HashMap<>(); 		// creates the username to client handler map
    }

    // METHODS

    // adds a client handler object to the active client list
    public synchronized void addClient(Object client) {
        if (client == null) { 	// checks if the client object is missing
            return; 			// stops if there is no client to add
        }	// end missing client check
        if (!activeClients.contains(client)) { 	// checks if the client is not already tracked
            activeClients.add(client); 			// adds the client object to the active client list
        }	// end duplicate check
    }

    // binds a username to a client handler after successful login
    public synchronized void bindUsername(String username, Object client) {
        String cleanUsername = normalize(username); 	// cleans the username
        
        if (cleanUsername == null || client == null) { 	// checks if username or client is missing
            return;										// stops if the username or client cannot be used
        }	// end missing value check
        
        userMap.put(cleanUsername, client);	// stores the username to client handler mapping
        addClient(client);					// makes sure the client is also tracked as active
    }

    // removes a client by username
    public synchronized void removeClient(String username) {
        String cleanUsername = normalize(username);	// cleans the username
        
        if (cleanUsername == null) {				// checks if the username is missing or blank
            return; 								// stops if there is no valid username
        }	// end invalid username check
        
        Object client = userMap.remove(cleanUsername);	// removes the username mapping and stores the removed client
        
        if (client != null) { 							// checks if a client was mapped to the username
            activeClients.remove(client); 				// removes the client from the active client list
        }	// end mapped client check
    }

    // returns the client handler object for a username
    public synchronized Object getClient(String username) {
        String cleanUsername = normalize(username);	// cleans the username
        
        if (cleanUsername == null) { 				// checks if the username is missing or blank
            return null; 							// returns null if no valid username was provided
        }	// end invalid username check
        return userMap.get(cleanUsername);	// returns the mapped client object or null
    }

    // returns active client handler objects
    public synchronized List<Object> getActiveClients() {
        return new ArrayList<>(activeClients);	// returns a copy of the active client list
    }

    // returns the number of active clients
    public synchronized int getActiveClientCount() {
        return activeClients.size();	// returns the active client count
    }

    // checks whether a username has an active connected client
    public synchronized boolean isConnected(String username) {
        String cleanUsername = normalize(username);							// cleans the username
        return cleanUsername != null && userMap.containsKey(cleanUsername); // returns true only if the username is mapped to a client
    }

    // cleans usernames
    private String normalize(String value) {
        if (value == null) { 	// checks if the value is missing
            return null; 		// returns null for missing values
        }	// end null check
        
        String trimmed = value.trim();				// removes leading and trailing spaces
        return trimmed.isEmpty() ? null : trimmed; 	// returns null for blank text otherwise returns the cleaned text
    }
}
