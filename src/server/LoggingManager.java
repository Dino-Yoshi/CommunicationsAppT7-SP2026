package server;

// CLASS DESCRIPTION:
// handles log buffering and log persistence, manages all logs

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LoggingManager {
	
	// ATTRIBUTES
    private String logFilePath;			// stores the file path where logs should be written
    private List<String> logBuffer; 	// stores readable log lines temporarily in memory
    private List<Log> structuredLogs; 	// stores structured log objects with metadata

    // CONSTRUCTOR
    public LoggingManager(String logFilePath) {
        this.logFilePath = logFilePath;				// stores the file path passed into the constructor
        this.logBuffer = new ArrayList<>(); 		// creates the list for readable buffered log lines
        this.structuredLogs = new ArrayList<>(); 	// creates the list for structured log objects
    }

    // METHODS
    
    // stores an informational log line
    public synchronized void logInfo(String message) {
        logBuffer.add("[INFO] " + message);	// adds info label and stores it in memory
    }

    // stores a warning log line
    public synchronized void logWarning(String message) {
        logBuffer.add("[WARNING] " + message);	// adds warning label and stores it in memory
    }

    // stores an error log line and a structured system error log object
    public synchronized void logError(String message) {
        logBuffer.add("[ERROR] " + message);	// adds error label and stores it in memory
        structuredLogs.add(new Log(LogType.SYSTEM_ERROR, "SYSTEM", "SYSTEM", message));	// stores the same event as a structured log object
    }

    // stores a structured log object and its string form in the readable log buffer
    public synchronized void addStructuredLog(LogType type, String senderID, String recipientID, String content) {
        Log log = new Log(type, senderID, recipientID, content);	// creates a structured log object
        structuredLogs.add(log);		// adds the structured log object to the structured log list
        logBuffer.add(log.toString());	// adds the string form of that structured log into the text buffer
    }

    // writes all buffered readable logs to the configured text file
    public synchronized void saveLogs() {
    	try (BufferedWriter writer = new BufferedWriter(new FileWriter(logFilePath, true))) { // opens the file in append mode and wraps it in a buffered writer
    		for (String log : logBuffer) {	// start loop through each buffered log line
                writer.write(log); 			// writes one log line into the file
                writer.newLine(); 			// inserts a newline so each log is on its own line
            }	// end loop
            logBuffer.clear(); 		// clears the in memory buffer after the logs have been saved to avoid duplicates
        } catch (IOException e) { 	// catches file writing errors
            System.out.println("Failed to save logs: " + e.getMessage()); // prints the error message to the console
        }	// end try-catch block
    }

    // returns the structured log objects
    public synchronized List<Log> getStructuredLogs() { // begins the structured log getter
        return Collections.unmodifiableList(new ArrayList<>(structuredLogs)); // returns a read only copy
    }

    // returns logs stored in memory but not yet saved
    public synchronized List<String> getBufferedLogs() { // begins the readable log getter
        return Collections.unmodifiableList(new ArrayList<>(logBuffer)); // returns a read only copy
    }
    
    // returns logs saved to log file
    public synchronized List<String> getSavedLogs() {
	    List<String> savedLogs = new ArrayList<>();	// creates a list to hold saved log lines
	    File file = new File(logFilePath); 			// creates a File object for the configured log file path
	    
	    if (!file.exists()) {	// checks whether the log file exists yet
	        return savedLogs;	// returns an empty list if no log file has been created
	    }	// end missing-file check
	    
	    try (BufferedReader reader = new BufferedReader(new FileReader(file))) {	// opens the saved log file for reading
	        String line;	// declares a variable to hold each line
	        while ((line = reader.readLine()) != null) {	// reads lines until the end of the file
	            savedLogs.add(line);	// adds the current saved log line to the result list
	        } // end the file-reading loop
	    } catch (IOException e) {	// catches file-reading errors
	        System.out.println("Failed to read saved logs: " + e.getMessage()); // prints the file-reading error
	    }	// end try-catch block
	    return savedLogs;	// returns the saved log lines
	}
}