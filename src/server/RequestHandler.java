package server;


import java.util.ArrayList;
import java.util.List;
import networking.Request;

// CLASS DESCRIPTION:
// receives requests and coordinates authentication, storage, logging, contacts, groups, and connections

public class RequestHandler {

    // ATTRIBUTES
    private List<Request> requestList;				// stores every request handled during this server run
    private int numRequests; 						// stores the number of requests handled during this server run
    private UserAuthenticator auth; 				// manages users, passwords, ids, and active sessions
    private GroupManager groupManager; 				// manages group creation and group membership
    private ContactManager contactManager; 			// manages contact lists
    private LoggingManager loggingManager; 			// manages server logging
    private StorageManager storageManager; 			// manages saved users, chat history, and offline messages
    private ConnectionManager connectionManager; 	// manages connected client mappings

    // CONSTRUCTOR
    public RequestHandler() {
        this.requestList = new ArrayList<>(); 	// creates the handled request list
        this.numRequests = 0; 					// starts handled request count at zero
        this.auth = new UserAuthenticator(); 	// creates the authentication manager
        this.groupManager = new GroupManager();	// creates the group manager
        this.contactManager = new ContactManager(); 			// creates the contact manager
        this.loggingManager = new LoggingManager("server.log"); // creates the logging manager using a simple log file
        this.storageManager = new StorageManager("users.txt", "messages"); 	// creates the storage manager using a user file and message folder
        this.connectionManager = new ConnectionManager(); 					// creates the connection manager
        this.auth.loadUsers(this.storageManager.loadUsers()); 				// loads saved users into the authentication manager
    }

    
    // METHODS

    // handles a request
    public synchronized Request handleRequest(Request request, Object client) {
        if (request == null) {									// checks if the request is missing
            loggingManager.logError("received null request"); 	// logs the null request problem
            loggingManager.saveLogs(); 							// saves the log immediately
            return createResponse("ERROR: request was null", Request.REQUESTTYPE.NULL, -1, -1);	// returns an error response
        }	// end null request check
        requestList.add(request);	// stores the request in the handled request list
        numRequests++; 				// increases the handled request count
        
        switch (request.getType()) {				// routes the request based on its request type
            
        case REGISTRATION:							// handles registration requests
                return doRegister(request); 		// sends request to registration handler
                
            case LOGIN: 							// handles login requests
                return doLogIn(request, client); 	// sends request to login handler
                
            case LOGOUT: 							// handles logout requests
                return doLogOut(request); 			// sends request to logout handler
                
            case SENDMESSAGE: 						// handles direct or group message requests
                return doSendMessage(request); 		// sends request to message handler
                
            case SEARCH: 							// handles user search requests
                return doSearch(request); 			// sends request to search handler
                
            case VIEWCONTACTS: 						// handles view contacts requests
                return doViewContacts(request); 	// sends request to contact list handler
                
            case ADDCONTACT: 						// handles add contact requests
                return doAddContact(request); 		// sends request to add contact handler
                
            case REMOVECONTACT: 					// handles remove contact requests
                return doRemoveContact(request); 	// sends request to remove contact handler
                
            case LOADCHATHISTORY: 					// handles chat history requests
                return doChatLog(request); 			// sends request to chat history handler
                
            case READLOG: 							// handles read log requests
                return doReadLog(request); 			// sends request to read log handler
                
            case CREATEGROUP:						// handles create group requests
                return doCreateGroup(request); 		// sends request to group creation handler
                
            default: // handles unsupported request types
                loggingManager.logWarning("unsupported request type " + request.getType());	// logs unsupported request type
                loggingManager.saveLogs();	// saves the warning log
                return createResponse("ERROR: unsupported request type", Request.REQUESTTYPE.NULL, -1, request.getSenderID());	// returns unsupported request response
        }	// end request type switch
    }

    // registers a new user if the requester is an it user
    public synchronized Request doRegister(Request request) {
        if (!isITRequest(request)) {	// checks if the requester is not an it user
            loggingManager.addStructuredLog(LogType.REGISTRATION_FAILED, String.valueOf(request.getSenderID()), "SERVER", "non-it registration attempt");	// logs blocked registration
            loggingManager.saveLogs();	// saves the log
            return createResponse("ERROR: only IT users can register new users", Request.REQUESTTYPE.NULL, -1, request.getSenderID()); // returns permission error
        } // end it permission check
        
        String[] credentials = parseTwoValues(request.getData());	// parses username and password from request data
        String username = credentials[0];	// stores the requested username
        String password = credentials[1]; 	// stores the requested password
        boolean registered = auth.registerUser(username, password); // attempts to register the user in memory
        
        if (registered) {	// checks if registration succeeded
            storageManager.saveUser(username.trim(), password);	// saves the new user to the user file
            loggingManager.addStructuredLog(LogType.REGISTRATION_SUCCESS, String.valueOf(request.getSenderID()), username, "registered user");	// logs successful registration
            loggingManager.saveLogs();	// saves the log
            Integer newUserId = auth.getIdByUsername(username);	// gets the new user's id
            return createResponse("SUCCESS: registered user " + username.trim() + " with id " + newUserId, Request.REQUESTTYPE.SUCCESS, -1, request.getSenderID()); // returns success response
        }	// end registration success check
        
        loggingManager.addStructuredLog(LogType.REGISTRATION_FAILED, String.valueOf(request.getSenderID()), username, "registration failed");	// logs failed registration
        loggingManager.saveLogs();	// saves the log
        return createResponse("ERROR: registration failed", Request.REQUESTTYPE.NULL, -1, request.getSenderID());	// returns failure response
    }

    // logs in a user and binds username to client handler
    public synchronized Request doLogIn(Request request, Object client) {
        String[] credentials = parseTwoValues(request.getData());	// parses username and password from request data
        String username = credentials[0];	// stores the username
        String password = credentials[1];	// stores the password
        boolean authenticated = auth.authenticate(username, password);	// attempts to authenticate the user
        
        if (authenticated) {	// checks if authentication succeeded
            Integer userId = auth.getIdByUsername(username);	// gets the logged in user's id
            if (client != null) {	// checks if servernetwork provided a client handler object
                connectionManager.bindUsername(username, client);	// maps the username to the connected client handler
            }	// end client binding check
            List<Request> offlineMessages = storageManager.getOfflineMessages(userId); // gets queued offline messages for this user
            loggingManager.addStructuredLog(LogType.LOGIN_SUCCESS, username, "SERVER", "login successful");	// logs successful login
            loggingManager.saveLogs();	// saves the log
            
            // String d, String sType, String rType, int t, int sID, int rID
            Request outbound = new Request("SUCCESS: logged in as " + username.trim() + " with id " + userId + " offline messages " + offlineMessages.size(), "SERVER", "USER", 11, -1, userId);
            return outbound;
            //return createResponse("SUCCESS: logged in as " + username.trim() + " with id " + userId + " offline messages " + offlineMessages.size(), Request.REQUESTTYPE.SUCCESS, -1, userId);	// returns success response
        }	// end authentication success check
        
        loggingManager.addStructuredLog(LogType.LOGIN_FAILED, username, "SERVER", "login failed");	// logs failed login
        loggingManager.saveLogs();	// saves the log
        return createResponse("ERROR: login failed", Request.REQUESTTYPE.NULL, -1, request.getSenderID());	// returns login failure response
    }

    // logs out a user
    public synchronized Request doLogOut(Request request) {
        String username = auth.getUsernameById(request.getSenderID());	// resolves the sender id to a username
    	
        
        if (username == null) {	// checks if the sender id is unknown
            return createResponse("ERROR: unknown user cannot log out", Request.REQUESTTYPE.NULL, -1, request.getSenderID());	// returns unknown user response
        }	// end unknown user check
        
        auth.logout(username);	// removes the username from active sessions
        connectionManager.removeClient(username);	// removes the username from active connections
        loggingManager.addStructuredLog(LogType.LOGOUT, username, "SERVER", "logout successful");	// logs logout
        loggingManager.saveLogs(); // saves the log
        return createResponse("SUCCESS: logged out " + username, Request.REQUESTTYPE.SUCCESS, -1, request.getSenderID());	// returns logout success response
    }

    // handles private and group message requests
    public synchronized Request doSendMessage(Request request) {
        if (!senderIsLoggedIn(request)) {	// checks if the sender is not logged in
            return createResponse("ERROR: sender must be logged in to send messages", Request.REQUESTTYPE.NULL, request.getRecipientID(), request.getSenderID());	// returns login required response
        }	// end logged in check
        if ("GROUP".equalsIgnoreCase(request.getRecipientType())) {	// checks if the message is a group message
            return doGroupMessage(request);	// sends request to group message handler
        }	// end group message check
        
        String sender = auth.getUsernameById(request.getSenderID());		// resolves the sender username
        String recipient = auth.getUsernameById(request.getRecipientID()); 	// resolves the recipient username
        
        if (recipient == null) {	// checks if the recipient id is unknown
            loggingManager.addStructuredLog(LogType.SYSTEM_ERROR, String.valueOf(request.getSenderID()), String.valueOf(request.getRecipientID()), "unknown direct message recipient");	// logs unknown recipient
            loggingManager.saveLogs();	// saves the log
            return createResponse("ERROR: recipient does not exist", Request.REQUESTTYPE.NULL, request.getRecipientID(), request.getSenderID()); 	// returns unknown recipient response
        }	// end unknown recipient check
        
        storageManager.saveMessage(request, auth);	// saves the direct message to chat history
        
        if (!connectionManager.isConnected(recipient)) {	// checks if the recipient is not currently connected
            storageManager.storeOfflineMessage(request.getRecipientID(), request);	// stores the message for offline delivery
        }	// end offline recipient check
        
        loggingManager.addStructuredLog(LogType.PRIVATE_MESSAGE, sender, recipient, request.getData());	// logs the private message
        loggingManager.saveLogs(); // saves the log
        return createResponse("SUCCESS: message processed", Request.REQUESTTYPE.SUCCESS, request.getRecipientID(), request.getSenderID());	// returns message success response
    }

    // handles group message requests
    private synchronized Request doGroupMessage(Request request) {
        String[] groupParts = parseTwoValues(request.getData());	// parses group name and message content
        String groupName = groupParts[0];		// stores the group name
        String messageContent = groupParts[1];	// stores the message content
        String sender = auth.getUsernameById(request.getSenderID());	// resolves the sender username
        
        if (!groupManager.groupExists(groupName)) {	// checks if the group does not exist
            return createResponse("ERROR: group does not exist", Request.REQUESTTYPE.NULL, request.getRecipientID(), request.getSenderID());	// returns missing group response
        }	// end missing group check
        if (!groupManager.isMember(groupName, sender)) {	// checks if sender is not a group member
            return createResponse("ERROR: sender is not a member of this group", Request.REQUESTTYPE.NULL, request.getRecipientID(), request.getSenderID());	// returns membership error
        }	// end membership check
        
        loggingManager.addStructuredLog(LogType.GROUP_MESSAGE, sender, groupName, messageContent);	// logs the group message
        loggingManager.saveLogs();	// saves the log
        return createResponse("SUCCESS: group message processed for " + groupName, Request.REQUESTTYPE.SUCCESS, request.getRecipientID(), request.getSenderID());	// returns group message success response
    }

    // searches all registered users
    public synchronized Request doSearch(Request request) {
        if (!senderIsLoggedIn(request)) {	// checks if the sender is not logged in
            return createResponse("ERROR: sender must be logged in to search users", Request.REQUESTTYPE.NULL, -1, request.getSenderID());	// returns login required response
        }	// end logged in check
        
        List<String> results = auth.searchUsers(request.getData());	// searches registered users
        loggingManager.addStructuredLog(LogType.SEARCH, String.valueOf(request.getSenderID()), "SERVER", request.getData());	// logs the search request
        loggingManager.saveLogs();	// saves the log
        return createResponse(String.join(",", results), Request.REQUESTTYPE.SUCCESS, -1, request.getSenderID());	// returns comma separated results
    }

    // returns user's contacts
    public synchronized Request doViewContacts(Request request) {
        String username = auth.getUsernameById(request.getSenderID());	// resolves the sender username
        
        if (username == null) {	// checks if the sender id is unknown
            return createResponse("ERROR: unknown user", Request.REQUESTTYPE.NULL, -1, request.getSenderID());	// returns unknown user response
        }	// end unknown user check
        
        List<String> contacts = contactManager.getContacts(username);	// gets the user's contact list
        return createResponse(String.join(",", contacts), Request.REQUESTTYPE.SUCCESS, -1, request.getSenderID());	// returns comma separated contacts
    }

    // adds a contact for the user
    public synchronized Request doAddContact(Request request) {
        String owner = auth.getUsernameById(request.getSenderID());	// resolves the sender username
        String contact = request.getData();	// reads the contact username from request data
        
        if (owner == null) {	// checks if the sender id is unknown
            return createResponse("ERROR: unknown user", Request.REQUESTTYPE.NULL, -1, request.getSenderID());	// returns unknown user response
        }	// end unknown user check
        if (!auth.userExists(contact)) {	// checks if the requested contact is not registered
            return createResponse("ERROR: contact user does not exist", Request.REQUESTTYPE.NULL, -1, request.getSenderID());	// returns missing contact response
        }	// end contact existence check
        
        boolean added = contactManager.addContact(owner, contact);	// attempts to add the contact
        
        if (added) {	// checks if the contact was added
            return createResponse("SUCCESS: contact added", Request.REQUESTTYPE.SUCCESS, -1, request.getSenderID());	// returns success response
        }	// end added check
        return createResponse("ERROR: contact was not added", Request.REQUESTTYPE.NULL, -1, request.getSenderID());	// returns failure response
    }

    // removes a contact for the sender
    public synchronized Request doRemoveContact(Request request) {
        String owner = auth.getUsernameById(request.getSenderID());	// resolves the sender username
        String contact = request.getData();	// reads the contact username from request data
        
        if (owner == null) {	// checks if the sender id is unknown
            return createResponse("ERROR: unknown user", Request.REQUESTTYPE.NULL, -1, request.getSenderID());	// returns unknown user response
        }	// end unknown user check
        
        boolean removed = contactManager.removeContact(owner, contact);	// attempts to remove the contact
        
        if (removed) {	// checks if the contact was removed
            return createResponse("SUCCESS: contact removed", Request.REQUESTTYPE.SUCCESS, -1, request.getSenderID());	// returns success response
        }	// end removed check
        return createResponse("ERROR: contact was not removed", Request.REQUESTTYPE.NULL, -1, request.getSenderID());	// returns failure response
    }

    // loads direct chat history between sender and recipient
    public synchronized Request doChatLog(Request request) {
        if (!senderIsLoggedIn(request)) {	// checks if the sender is not logged in
            return createResponse("ERROR: sender must be logged in to load chat history", Request.REQUESTTYPE.NULL, request.getRecipientID(), request.getSenderID());	// returns login required response
        }	// end logged in check
        List<String> history = storageManager.loadChatHistory(request.getSenderID(), request.getRecipientID(), auth);	// loads chat history from storage
        return createResponse(String.join("\n", history), Request.REQUESTTYPE.SUCCESS, request.getRecipientID(), request.getSenderID());	// returns chat history response
    }

    // reads saved and buffered logs for it users
    public synchronized Request doReadLog(Request request) {
        if (!isITRequest(request)) {	// checks if the requester is not an it user
            return createResponse("ERROR: only IT users can read logs", Request.REQUESTTYPE.NULL, -1, request.getSenderID());	// returns permission error
        }	// end it permission check
        
        List<String> logs = new ArrayList<>(); 			// creates a combined log result list
        logs.addAll(loggingManager.getSavedLogs()); 	// adds logs already saved to the file
        logs.addAll(loggingManager.getBufferedLogs()); 	// adds logs still waiting in memory
        loggingManager.addStructuredLog(LogType.READ_LOG, String.valueOf(request.getSenderID()), "SERVER", "read logs");	// logs the read log request
        loggingManager.saveLogs();	// saves the read log event
        return createResponse(String.join("\n", logs), Request.REQUESTTYPE.SUCCESS, -1, request.getSenderID());	// returns readable log lines
    }

    // creates a new group
    public synchronized Request doCreateGroup(Request request) {
        if (!senderIsLoggedIn(request)) {	// checks if the sender is not logged in
            return createResponse("ERROR: sender must be logged in to create groups", Request.REQUESTTYPE.NULL, -1, request.getSenderID());	// returns login required response
        } // end logged in check
       
        String[] groupData = parseTwoValues(request.getData());	// parses group name and member list from request data
        String groupName = groupData[0];				// stores the group name
        List<String> members = parseList(groupData[1]); // parses selected members
        String creator = auth.getUsernameById(request.getSenderID());	// resolves the creator username
        
        if (creator != null && !members.contains(creator)) {	// checks if the creator is not already included
            members.add(creator);	// adds the creator to the group
        }	// end creator check
        for (String member : members) {	// starts loop through each requested member
            if (!auth.userExists(member)) {	// checks if the requested member is not registered
                return createResponse("ERROR: group member does not exist " + member, Request.REQUESTTYPE.NULL, -1, request.getSenderID());	// returns missing member response
            }	// end member exists check
        }	// end member validation loop
        
        boolean created = groupManager.createGroup(groupName, members);	// attempts to create the group
        
        if (created) { // checks if the group was created
            loggingManager.addStructuredLog(LogType.GROUP_MESSAGE, creator, groupName, "created group");	// logs successful group creation
            loggingManager.saveLogs(); // saves the log
            return createResponse("SUCCESS: group created " + groupName, Request.REQUESTTYPE.SUCCESS, -1, request.getSenderID());	// returns success response
        }	// end group creation success check
        return createResponse("ERROR: group was not created", Request.REQUESTTYPE.NULL, -1, request.getSenderID());	// returns failure response
    }

    // GETTERS
    
    // returns handled requests
    public synchronized List<Request> getRequestList() {
        return new ArrayList<>(requestList);	// returns a copy of handled requests
    }

    // returns the number of handled requests
    public synchronized int getNumRequests() {
        return numRequests;
    }

    // returns the authentication manager
    public UserAuthenticator getAuth() {
        return auth;
    }

    // returns the group manager
    public GroupManager getGroupManager() {
        return groupManager;
    }

    // returns the contact manager
    public ContactManager getContactManager() {
        return contactManager;
    }

    // returns the logging manager
    public LoggingManager getLoggingManager() {
        return loggingManager;
    }

    // returns the storage manager
    public StorageManager getStorageManager() {
        return storageManager;
    }

    // returns the connection manager
    public ConnectionManager getConnectionManager() {
        return connectionManager;
    }

    // checks if the request came from an it user
    private boolean isITRequest(Request request) {
        return request != null && "IT".equalsIgnoreCase(request.getSenderType());	// returns true only for sender type it
    }

    // checks if the request sender is logged in
    private boolean senderIsLoggedIn(Request request) {
        String username = auth.getUsernameById(request.getSenderID());	// resolves sender id to username
        return username != null && auth.isLoggedIn(username); 			// returns true only if the sender is known and logged in
    }

    // creates a response request
    private Request createResponse(String data, Request.REQUESTTYPE type, int recipientID, int senderID) {
        return new Request(data, "SERVER", "USER", requestTypeToNumber(type), senderID, recipientID);	// creates a response using the existing request constructor
    }

    // converts request type enum values into request constructor numbers
    private int requestTypeToNumber(Request.REQUESTTYPE type) {
        if (type == Request.REQUESTTYPE.SENDMESSAGE) {		// checks for send message type
            return 0; 										// returns send message number
        }	// end send message check
        
        if (type == Request.REQUESTTYPE.REGISTRATION) { 	// checks for registration type
            return 1; 										// returns registration number
        }	// end registration check
        
        if (type == Request.REQUESTTYPE.SEARCH) {			// checks for search type
            return 2; 										// returns search number
        }	// end search check
        
        if (type == Request.REQUESTTYPE.VIEWCONTACTS) { 	// checks for view contacts type
            return 3; 										// returns view contacts number
        }	// end view contacts check
        
        if (type == Request.REQUESTTYPE.LOADCHATHISTORY) { 	// checks for load chat history type
            return 4; 										// returns load chat history number
        }	// end load chat history check
        
        if (type == Request.REQUESTTYPE.READLOG) { 			// checks for read log type
            return 5; 										// returns read log number
        }	// end read log check
        
        if (type == Request.REQUESTTYPE.CREATEGROUP) { 		// checks for create group type
            return 6; 										// returns create group number
        }	// end create group check
        
        if (type == Request.REQUESTTYPE.LOGIN) { 			// checks for login type
            return 7; 										// returns login number
        }	// end login check
        
        if (type == Request.REQUESTTYPE.LOGOUT) { 			// checks for logout type
            return 8; 										// returns logout number
        }	// end logout check
        
        if (type == Request.REQUESTTYPE.ADDCONTACT) { 		// checks for add contact type
            return 9; 										// returns add contact number
        }	// end add contact check
        
        if (type == Request.REQUESTTYPE.REMOVECONTACT) {	// checks for remove contact type
            return 10; 										// returns remove contact number
        }	// end remove contact check
        
        if (type == Request.REQUESTTYPE.SUCCESS) { 			// checks for success type
            return 11; 										// returns success number
        }	// end success check
        
        return 12;	// returns null request number
    }

    // parses two comma separated values
    private String[] parseTwoValues(String data) {
        String safeData = data == null ? "" : data;	// converts null data to empty text
        String[] parts = safeData.split(",", 2); 	// splits data into two parts at the first comma
        String first = parts.length > 0 ? parts[0].trim() : "";		// reads the first parsed value
        String second = parts.length > 1 ? parts[1].trim() : ""; 	// reads the second parsed value
        return new String[] { first, second };						// returns the two parsed values
    }

    // parses a comma or semicolon separated list
    private List<String> parseList(String data) {
        List<String> values = new ArrayList<>();	// creates parsed values list
        
        if (data == null || data.isBlank()) {	// checks if the data is missing or blank
            return values;	// returns an empty list for invalid data
        }	// end invalid data check
        
        String[] parts = data.split("[,;]");	// splits the data by commas or semicolons
        
        for (String part : parts) {			// starts loop through each parsed part
            String cleanPart = part.trim(); // trims the parsed part
            if (!cleanPart.isEmpty()) { 	// checks if the parsed part has text
                values.add(cleanPart); 	// adds the parsed part to the values list
            }	// end text check
        }	// end parsed part loop
        return values;	// returns parsed values
    }
}