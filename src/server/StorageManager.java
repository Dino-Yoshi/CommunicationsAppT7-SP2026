package server;

import java.io.BufferedReader;
import networking.Request;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// CLASS DESCRIPTION:
// handles user persistence, chat history persistence, and offline message storage

public class StorageManager {
	
	// ATTRIBUTES
    private String userFilePath;							// stores the file path where username and password pairs are saved
    private String messageDirectory;						// stores the directory where conversation files are saved
    private Map<Integer, List<Request>> offlineMessages;	// maps recipient ids to queued Request objects for offline delivery

    // CONSTRUCTOR
    public StorageManager(String userFilePath, String messageDirectory) {
        this.userFilePath = userFilePath;					// stores the user file path
        this.messageDirectory = messageDirectory;			// stores the message directory path
        this.offlineMessages = new HashMap<>();				// creates the offline message map
        File messageFolder = new File(messageDirectory);	// creates a File object representing the message directory
        if (!messageFolder.exists()) { 						// checks whether the message directory exists
            messageFolder.mkdirs(); 						// creates the directory path if it does not exist
        }	// ends missing folder check
    }

    // METHODS
    
    // appends a username,password pair to the user file
    public synchronized void saveUser(String username, String password) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(userFilePath, true))) {	// opens the user file in append mode
            writer.write(username + "," + password);	// writes one username,password pair
            writer.newLine();		// moves to the next line for the next saved user
        } catch (IOException e) {	// catches file writing errors
            System.out.println("Failed to save user: " + e.getMessage());	// prints the error to the console
        }	// end try-catch block
    }

    // loads all saved username,password pairs from the user file
    public synchronized Map<String, String> loadUsers() {
        Map<String, String> users = new LinkedHashMap<>();	// creates the map that will hold loaded users
        File file = new File(userFilePath);				// creates a File object for the user file
        
        if (!file.exists()) {	// checks whether the user file exists yet
            return users;		// returns an empty map if no user file exists yet
        }	// end missing file check
        
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {	// opens the user file for reading
            String line;	// declares a variable for each line read from the file
            while ((line = reader.readLine()) != null) {		// loops through every line in file
                String[] parts = line.split(",", 2);			// splits each line into two parts at the first comma
                if (parts.length == 2 && !parts[0].isBlank()) {	// checks that the line has a username and password
                    users.put(parts[0].trim(), parts[1]); 		// stores the cleaned username and password in the map
                }
            }
        } catch (IOException e) {	// catches file reading errors
            System.out.println("Failed to load users: " + e.getMessage());	// prints the error to the console
        }	// end try-catch block
        return users;	// returns the loaded user map
    }

    // saves a direct message request into a conversation file
    public synchronized void saveMessage(Request message, UserAuthenticator auth) {
        if (message == null) {	// checks if message exists
        	return;
        }	// end missing message check
    	
        String filePath = buildConversationPath(message.getSenderID(), message.getRecipientID(), auth);			// computes the conversation file path
        
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) { 						// opens the conversation file in append mode
            writer.write(message.getSenderID() + "->" + message.getRecipientID() + ": " + message.getData());	// writes the message in a readable sender to recipient format
            writer.newLine();		// inserts a newline so each message stays on its own line
        } catch (IOException e) {	// catches file writing errors
            System.out.println("Failed to save message: " + e.getMessage());	// prints the error to the console
        }	// end try-catch block
    }

    // loads direct chat history between two numeric ids
    public synchronized List<String> loadChatHistory(int senderID, int recipientID, UserAuthenticator auth) {
        List<String> history = new ArrayList<>();	// creates the list that will hold the loaded conversation lines
        String filePath = buildConversationPath(senderID, recipientID, auth);	// computes the same path used during saving
        File file = new File(filePath);	// creates a File object for the conversation file
        
        if (!file.exists()) { 			// checks if the conversation file exists
            return history; 			// returns an empty list if no history file exists yet
        }	// end missing file check
        
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {	// opens the conversation file for reading
            String line;	// declares a variable for each line read from the file
            while ((line = reader.readLine()) != null) {	// start loop through every line in the conversation file
                history.add(line);	// adds the current line into the history list
            }	// end loop
        } catch (IOException e) {	// catches file reading errors
            System.out.println("Failed to load chat history: " + e.getMessage());	// prints the error to the console
        }	// end try-catch block
        return history;	// returns the loaded history lines
    }

    // stores a request for later delivery to an offline recipient
    public synchronized void storeOfflineMessage(int recipientID, Request message) {
        if (message == null) {	// checks if message exists
        	return;
        }	// end missing message check
    	
        offlineMessages.putIfAbsent(recipientID, new ArrayList<>());	// creates a queue list for the recipient if one does not already exist
        offlineMessages.get(recipientID).add(message); 					// appends the message request to the recipient's offline queue
    }

    // returns all queued offline messages for a recipient
    public synchronized List<Request> getOfflineMessages(int recipientID) {
    	List<Request> messages = offlineMessages.get(recipientID);	// gets offline message queue for recipient
        
    	if (messages == null) {	// checks if recipient has no queued messages
        	return new ArrayList<>();	// returns empty list if none
        }	// end missing queue check
        return new ArrayList<>(messages);	// returns copy
    }

    // clears all queued offline messages for a recipient after delivery
    public synchronized void clearOfflineMessages(int recipientID) {
        offlineMessages.remove(recipientID);	// removes the recipient's offline queue from the map
    }

    // builds a file path for a two-user conversation
    private String buildConversationPath(int senderID, int recipientID, UserAuthenticator auth) {
        String user1 = auth == null ? null : auth.getUsernameById(senderID); 	// resolves the sender id to a username
        String user2 = auth == null ? null : auth.getUsernameById(recipientID); // resolves the recipient id to a username
        
        if (user1 == null) { 			// checks if the sender id could not be resolved
            user1 = "user" + senderID; 	// uses a fallback sender name
        }	// end sender fallback check
        if (user2 == null) { 					// checks if the recipient id could not be resolved
            user2 = "user" + recipientID; 		// uses a fallback recipient name
        }	// end recipient fallback check
        
        List<String> users = new ArrayList<>(); // creates a temporary list to sort the two participant names
        users.add(user1);			// adds the first participant name
        users.add(user2);			// adds the second participant name
        Collections.sort(users); 	// sorts names so both use the same conversation file
        return messageDirectory + File.separator + users.get(0) + "_" + users.get(1) + ".txt"; 	// returns final conversation file path
    }
}