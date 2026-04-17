package networking;
import client.*;
import networking.Request.REQUESTTYPE;
import server.*;
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
	
	private ObjectOutputStream objectOutputStream;
	private ObjectInputStream objectInputStream;
	
	private RequestHandler requestHandler; 
	private User user; 
	
	// constructor
	public ClientNetwork(){
		serverIP = "192.168.1.70"; // hard connection to 'x' server under 'x' port.
		serverPort = 7777;
		status = CLIENTSTATUS.DISCONNECTED; // by default they are not connect until a login attempt is made. 
	}
		
	// methods
	public int connectToServer(Request req, User user) {
		if(status != CLIENTSTATUS.CONNECTED) { // the server cannot already be connected.
			try {
				
				// if the client is not connected, the next request HAS to be a login attempt. 
				if(req.getType() != REQUESTTYPE.LOGIN) {
					System.err.println("Not a login request type. Ignored. No connection attempt was made to the server.");
					return -1; // connection failed. 
				}
				
				// connect to our server
				clientSocket = new Socket(serverIP, serverPort);
				
				// who have we connected - DEBUG
				//System.out.println("Connected to " + getServerIP() + ":" + getServerPort()); // 
					
			    // initialize our output stream, this is solidifies our connection to the server.
			    objectOutputStream = new ObjectOutputStream(clientSocket.getOutputStream());
			    
			    // write the login request to be handled by the server.
		        objectOutputStream.writeObject(req);
			    objectOutputStream.flush();
			    
			    // create a ObjectInputStream so we can read data from it. Needs an InputStream
			    objectInputStream = new ObjectInputStream(clientSocket.getInputStream());
		        // Retrieve an expected Request of SUCCESS. 
			    
			    Request inbound = (Request) objectInputStream.readObject();
			    
			    if(inbound.getType() == REQUESTTYPE.SUCCESS) {
			    	status = CLIENTSTATUS.CONNECTED;
			    	user.setUID(inbound.getRecipientID());
			    	//System.out.println(getStatus()); debug
			    	return 0; // connection successfully established.
			    }else if(inbound.getType() == REQUESTTYPE.NULL) {
			    	disconnect();
			    	return -3; // credential failure.
			    }else {
			    	/*
			    	objectOutputStream = null; // streams are just set to null here as they may be used again.
			    	objectInputStream = null; // streams are just set to null here as they may be used again.
			    	clientSocket.close(); // socket is okay to close for now. 
			    	*/
			    	disconnect();
			    	return -1; // connection failed.
			    }
			    
			    
			    
			}catch(IOException e) {
				e.printStackTrace();
				return -4; // ioexception
			}catch(ClassNotFoundException e) {
		    	e.printStackTrace();
		    	return -5; // classnotfoundexception
		    }
		}else {
			return 1; // server is already connected. 
		}
	}
	
	// call this when client dies.
	// closes sockets and streams appropriately. 
	public void disconnect() {
		try {
			clientSocket.close();
			objectOutputStream.close();
			objectInputStream.close();
			return;
		}catch(IOException e) {
			e.printStackTrace();
		}
		return;
	}
	
	
	public int sendRequest(Request req) {
		if(status == CLIENTSTATUS.CONNECTED) {
			
			if(req.getType() != REQUESTTYPE.LOGIN) {
				try {
					
					switch(req.getType()) {
					
					case REQUESTTYPE.LOGOUT:
						
						objectOutputStream.writeObject(req);
						objectOutputStream.flush();
						
						Request inbound = (Request) objectInputStream.readObject();
						
						if(inbound.getType() == REQUESTTYPE.SUCCESS) {
							disconnect();
							setStatus(1);
							return 0; // log the user out.
						}else {
							return -1; // some sort of error occurred, can't log them out!
						}
					   
					
					}
					
					
				}catch(IOException e) {
					e.printStackTrace();
					return -4;
				}catch(ClassNotFoundException e) {
					e.printStackTrace();
					return -5;
				}
			}else {
				System.err.println("Ignoring Login Request. (500)");
			}
			
			
			
			
			return 0;
		}else {
			return -1; // No connection to the server. 
		}
	}
			    
		         
			    
			    // Interpret the SUCCESS Request, and enter the interactive loop. 
			    
			    /*
			    if(tmp.getType() == Request.REQUESTTYPE.SUCCESS) {
			       int msgChoice = 1; // TODO: Replace this using Graphical Choices
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
			        		
			       			Request newLogOutMsg = new Request("Logging out.", "USER", "NULL", 8, 0, -1);
			        		
			        		objectOutputStream.writeObject(newLogOutMsg);
					        objectOutputStream.flush();
	//					        
					        Request inboundLogOutMsg = (Request) objectInputStream.readObject();
	//					        
						    System.out.println("Server responded: " + inboundLogOutMsg.getData());
						    System.out.println("You are now going to be logged out for the sake of testing.");
						        
						break;
			        		
			        	default:
			        		System.err.println("Unexpected input.");
			        			
			        	break;
			        	}
			        		
			        }while(msgChoice != 1);
			        	
			       
			    }*/
			        
			        
			
		
	
	
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
