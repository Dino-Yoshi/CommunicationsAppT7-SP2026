package server;

// CLASS DESCRIPTION:
// represents one log entry in system

import java.util.Date;

public class Log {
	
	// ATTRIBUTES
	private Date timestamp;		// stores when log was created
	private LogType typeLog;	// stores category of log event
	private String senderID; 	// stores the sender id as a string
	private String recipientID; // stores the recipient id as a string
	private String content; 	// stores the message associated with the event
	
	// CONSTRUCTOR
	public Log(LogType typeLog, String senderID, String recipientID, String content) {
		this.timestamp = new Date();		// captures timestamp
		this.typeLog = typeLog;				// stores log category
		this.senderID = senderID;			// stores sender id
		this.recipientID = recipientID;		// stores recipient id
		this.content = content;				// stores content
	}
		
	// METHODS 
	
	// GETTERS
	public Date getTimestamp() {
		return new Date(timestamp.getTime());
	}
	
	public LogType getTypeLog() {
		return typeLog;
	}
	
	public String getSenderID() {
		return senderID;
	}
	
	public String getRecipientID() {
		return recipientID;
	}
	
	public String getContent() {
		return content;
	}
	
	// toString method, used to convert log into readable text
	// time stamp -> log type -> senderID -> recipientID -> content
	public String toString() {
		return timestamp + " | " + typeLog + " | sender=" + senderID + 
				" | recipient=" + recipientID + " | content=" + content;	   
	}
}