package client;

public class ITUser extends User {
	
	// attribs
	private int ITUID;
	private static int count = 0;
	
	// constructor
	public ITUser(String u, String p) {
		super(u, p);
		IT = true;
	}
	
	// getters
	public int getITUID() {
		return ITUID;
	}
	
}
