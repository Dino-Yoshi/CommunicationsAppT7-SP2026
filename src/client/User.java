package client;

public class User {
	
	// attribs
	private int UID;
	private static int count = 0;
	private String username;
	private String password;
	protected boolean IT;
	private USERSTATUS status;
	
	public enum USERSTATUS {ONLINE, AWAY, DND, OFFLINE, NULL};
	
	// constructor
	
	public User(String u, String p){
		username = u;
		password = p;
		IT = false;
		status = USERSTATUS.ONLINE;
		UID = ++count;
	}
	
	// methods
	
	public String toString() {
		return getUID() + "," + getUsername() + "," + getPassword() + "," + getStatus() + "," + isIT();
	}
	
	// getters

	public int getUID() {
		return UID;
	}
	
	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}
	
	public USERSTATUS getStatus() {
		return status;
	}
	
	public boolean isIT() {
		return IT;
	}
	
	// setters

	public void setUID(int uID) {
		UID = uID;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setStatus(int i) {
		switch(i) {
		case(0):
			status = USERSTATUS.ONLINE;
		break;
		
		case(1):
			status = USERSTATUS.AWAY;
		break;
		
		case(2):
			status = USERSTATUS.DND;
		break;
			
		case(3):
			status = USERSTATUS.OFFLINE;
		break;
			
		default:
			status = USERSTATUS.NULL;
		break;
		}
	}

	
	
	
	
	
	
}
