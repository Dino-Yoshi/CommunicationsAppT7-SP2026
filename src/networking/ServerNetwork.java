// TODO: ServerNetwork and ClientNetwork must be built together.
package networking;



import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import server.RequestHandler;

public class ServerNetwork {
	
	// attributes
	private static int port = 7777; // port number
	private ServerSocket ss; // serversocket that listens on the defined port.
	private static SERVERSTATUS status; // status of server.
	private static ArrayList<Socket> clients; // array list of sockets which are all currently connected clients.
	private static int numConnections; // number of currently active connections.
	
	protected enum SERVERSTATUS {ONLINE, OFFLINE, ERROR, NULL}; // enumeration of server status
	
	// "constructor"
	public static void main(String[] args) throws IOException, ClassNotFoundException{
		
		// Create a Server Socket
        ServerSocket ss = new ServerSocket(port);
        System.out.println("ServerSocket awaiting connections...");
        clients = new ArrayList<Socket>();
        
        setStatus(0); // Server is ONLINE
        

        while (true) {

			// socket object to receive incoming client
			// requests
			Socket client = ss.accept();
			
			clients.add(client);
			++numConnections;

			// Displaying that new client is connected
			// to server
			System.out.println("New client connected: "
							+ client.getInetAddress()
									.getHostAddress());

			// create a new thread object
			ClientHandler clientSock
				= new ClientHandler(client);

			// This thread will handle the client
			// separately
			new Thread(clientSock).start();
		}
        
	}
	
	// ClientHandler class
	private static class ClientHandler implements Runnable  {
		
		// attribs
		private final Socket clientSocket;
		private String username;
		private boolean authenticated;
		private final RequestHandler requestHandler;
		
		
		// Constructor
		public ClientHandler(Socket socket)
		{
			this.clientSocket = socket;
			this.username = null;
			this.authenticated = false;
			this.requestHandler = new RequestHandler();
			
			// load the users...? feels like the requestHandler should be a singleton if its going to need to load users for ALL active users...
			//requestHandler.getAuth().loadUsers((requestHandler.getStorageManager().loadUsers()));
		}

		// methods
		public void run(){
			try {
						
				// Output stream socket.
		        OutputStream outputStream = clientSocket.getOutputStream();

			    // Create object output stream from the output stream to send an object through it
		        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
			        
		        objectOutputStream.flush();
			        
			    // get the input stream from the connected socket
		        InputStream inputStream = clientSocket.getInputStream();

		        // create a ObjectInputStream so we can read data from it. something here is freezing up... when client also has ObjectInputStream
		        ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
			        
		        Request InboundMsg = null;
		        Request OutboundMsg = null;
			        
		       
		        
		        do {
		        	do {
			        	try {
			        		InboundMsg = (Request) objectInputStream.readObject();
			        		if(InboundMsg.getType() != Request.REQUESTTYPE.LOGIN) {
			        			InboundMsg = null;
			        		}
			        	}catch(ClassNotFoundException e) {
			        		e.printStackTrace();
			        	}catch(EOFException e) { // connection is terminated abruptly, and the user must attempt another login.
			        		System.err.println("Closing " + clientSocket.getInetAddress()
							.getHostAddress() + " connection due to failed login attempt.");
			        		return;
			        	}
				        	
				        	
			        }while(InboundMsg == null);
		        	
		        	// process the user's login request
		        	OutboundMsg = requestHandler.handleRequest(InboundMsg, clientSocket);
		        	
		        	objectOutputStream.writeObject(OutboundMsg);
				    objectOutputStream.flush();
		        	
		        }while(OutboundMsg.getType() == Request.REQUESTTYPE.NULL);
			        
		        // now continuously read for text messages or a logout message from clients.
		        do {
		        	try {
		        		InboundMsg = (Request) objectInputStream.readObject();
		        		
		        		OutboundMsg = requestHandler.handleRequest(InboundMsg, clientSocket);
		        		
		        		objectOutputStream.writeObject(OutboundMsg);
	        			objectOutputStream.flush();
			        		
		        		// TODO: Interpret all incoming messages using the RequestHandler
		        		
		        		
		        		/*
		        		if(InboundMsg.getType() == Request.REQUESTTYPE.SENDMESSAGE) {
			        		
		        			
		        			OutboundMsg = new Request(2, 0, InboundMsg.getText().toUpperCase());
		        			objectOutputStream.writeObject(OutboundMsg);
		        			objectOutputStream.flush();
		        			
			        			
		        		// On logout, returns a new message with status "SUCCESS".
		        		}else if(InboundMsg.getType() == Request.REQUESTTYPE.LOGOUT) {
			        		
		        			
		        			OutboundMsg = new Request("You have been logged out.", "SERVER", "USER", 11, -1, 0);
		        			objectOutputStream.writeObject(OutboundMsg);
		        			objectOutputStream.flush();
		        			
			        			
		        		}else {
		        			System.out.println("Ignoring Message of type LOGIN");
		        		}
		        		*/
		        		
		        	}catch(ClassNotFoundException e) {
		        		e.printStackTrace();
		        	}

		        }while(InboundMsg.getType() != Request.REQUESTTYPE.LOGOUT);
			        
		        // once the loop breaks, close the thread. 
		        clientSocket.close();
		        --numConnections;
		        clients.remove(clientSocket);
					
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		// ClientHandler Methods
		
		public void handleRequest(Request req) {
			// TODO: Wrapper function for the RequestHandler's methods.
		}
		
		public void sendResponse(String msg) {
			// TODO: Wrapper function for sending a Request through the network
		}

		// getters
		public boolean isAuthenticated() {
			return authenticated;
		}
		
		public String getUsername() {
			return username;
		}

		
		// setters
		public void setAuthenticated(boolean authenticated) {
			this.authenticated = authenticated;
		}
		public void setUsername(String n) {
			
		}
		
	}

	
	
	
	
	// ServerNetwork methods
	
	public static int getPort() {
		return port;
	}

	public SERVERSTATUS getStatus() {
		return status;
	}

	public int getNumConnections() {
		return numConnections;
	}

	private static void setStatus(int i) {
		switch(i) {
		case(0):
			status = SERVERSTATUS.ONLINE;
		break;
			
		case(1):
			status = SERVERSTATUS.OFFLINE;
		break;
		
		case(2):
			status = SERVERSTATUS.ERROR;
		break;
		
		default:
			status = SERVERSTATUS.NULL;
		break;
		}
	}
	
	
	
	
	
	
	
	
		
}
