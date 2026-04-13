import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientNetwork {
	// attributes
	private String serverIP; // Internet Protocol Address for the Server to connect to.
	private int serverPort; // Port Number of Server to access.
	private Socket clientSocket; // Socket which Client uses to connect to a ServerSocket
	private CLIENTSTATUS status; // Client Current Status
	private enum CLIENTSTATUS {CONNECTED, DISCONNECTED, NULL}; // Status ENUM
	
	// private RequestHandler requestHandler; TODO: pull from Clarize's Branch for RequestHandler
	// private USER user; 
	
	// constructor
	ClientNetwork(String ip, int port){
		serverIP = ip;
		serverPort = port;
		
		try {
			clientSocket = new Socket(serverIP, serverPort);
			
			System.out.println("Connected to " + ip + ":" + port); // TODO: Change to a GUI window.
				
		    // Create object output stream from the output stream to send an object through it. Needs an OutputStream
		    ObjectOutputStream objectOutputStream = new ObjectOutputStream(clientSocket.getOutputStream());
		        
		    // create a ObjectInputStream so we can read data from it. Needs an InputStream
		    ObjectInputStream objectInputStream = new ObjectInputStream(clientSocket.getInputStream());
		
		    // Create a new Request of LOGIN. Send this to the Server.
		    
		    // String d, String sType, String rType, int t, int sID, int rID
	        Request loginReq = new Request("Logging in.", "USER", "NULL", 0, 0, -1);
	        objectOutputStream.writeObject(loginReq);
	        objectOutputStream.flush();
	        
	        // Retrieve an expected Request of SUCCESS. 
	        Request tmp = (Request) objectInputStream.readObject();
		    
		    // Interpret the SUCCESS Request, and enter the interactive loop. 
		    if(tmp.getType() == Request.REQUESTTYPE.SUCCESS) {
		       int msgChoice = 1; // TODO: Replace this
		       do {
		    	   try {
		    		   // TODO: Make a Graphical Window with options that require sending and receiving requests to the server.
		        	}catch(NumberFormatException e) {
		        		e.printStackTrace();
		        		System.out.println("Malformed Entry. Default to Logging Out");
		        		msgChoice = 1;
		        	}
		        		
		        	switch(msgChoice) {
		        		
		        	// TODO: Implement the different requests that can be made by the client. Use the RequestHandler
		        	// TODO: Implement the different requests able to be received and interpret them correctly. Use the RequestHandler
		        	case(0):
		        			
		        		
		       			//Request newTxtMsg = new Request(2, 0, );
		       			
		       			//objectOutputStream.writeObject(newTxtMsg);
				        //objectOutputStream.flush();

					    //Request inboundTxtMsg = (Request) objectInputStream.readObject();
					       
					    //System.out.println("Server responded: " + inboundTxtMsg.getText());
					        
					break;
		        		
		        	// Logout Message logic.
		        	// Makes a message of type "logout" and status "success", before writing it to the server with objectOutputStream
		       		// The server will respond with the logout message.
		       		// This will lead to the loop breaking and the client closing.
		       		case(1):
		        		
//		       			Request newLogOutMsg = new Request(1, 0, "luser logging out");
//		        		
//		        		objectOutputStream.writeObject(newLogOutMsg);
//				        objectOutputStream.flush();
//					        
//				        Request inboundLogOutMsg = (Request) objectInputStream.readObject();
//					        
//					    System.out.println("Server responded: " + inboundLogOutMsg.getText());
					        
					break;
		        		
		        	default:
		        		System.err.println("Unexpected input.");
		        			
		        	break;
		        	}
		        		
		        }while(msgChoice != 1);
		        	
		       clientSocket.close(); // TODO: Potential Socket Exception
		    }
		        
		        
		}catch(IOException e) {
			e.printStackTrace();
		}catch(ClassNotFoundException e) {
	    	e.printStackTrace();
	    }
		
	}
	
		
	// methods
	
	
	// getters
	public String getServerIP() {
		return serverIP;
	}
	
	public int getServerPort() {
		return serverPort;
	}
	
	public Socket getClientSocket() {
		return clientSocket;
	}
	
	public CLIENTSTATUS getStatus() {
		return status;
	}
	
	// setters
	public void setServerIP(String serverIP) {
		this.serverIP = serverIP;
	}

	public void setServerPort(int serverPort) {
		this.serverPort = serverPort;
	}

	public void setClientSocket(Socket clientSocket) {
		this.clientSocket = clientSocket;
	}

	public void setStatus(int status) {
		switch(status) {
		case(0):
			this.status = CLIENTSTATUS.CONNECTED;
		break;
		case(1):
			this.status = CLIENTSTATUS.DISCONNECTED;
		break;
		default:
			this.status = CLIENTSTATUS.NULL;
		}
	}
		
}
