import java.io.Serializable;
import java.util.Date;

public class Request implements Serializable{
	
	// attributes
	private String UID; // req id
	private static int count = 0; // attribute for id init
	private Date createdDate; // date this request was created
	private String data; // information to be parsed
	private String senderType; // the account type of the sender
	private String recipientType; // the account type of the recipient
	private REQUESTTYPE type; // request type
	private int senderID; // id of who is sending
	private int recipientID; // id to send to

	// ENUMERATION FOR REQUEST TYPE
	private enum REQUESTTYPE {SENDMESSAGE, REGISTRATION, SEARCH, VIEWCONTACTS, LOADCHATHISTORY, READLOG, CREATEGROUP, LOGIN, LOGOUT, ADDCONTACT, REMOVECONTACT, NULL};
	
	// constructor
	public Request(String d, String sType, String rType, int t, int sID, int rID) {
		UID = "r" + Integer.toString(++count); // ex: r1, r2, ...
		createdDate = new Date(); // creation date
		data = d; // data to parse
		senderType = sType; // sender account type
		recipientType = rType; // recipient account type
		setRequestType(t); // call the private setter to decide the type of the request.
		senderID = sID; // sender id
		recipientID = rID; // recipient id
	}
	
	// methods
	
	// provides a simplified way to retrieve data in one method, in one predetermined way.
	// the order is:
	// UID > Request Type > Data > SenderType > RecipientType > SenderID > RecipientID
	public String toString() {
		return getUID() + "," + getType() + "," + getData() + "," + getSenderType() + "," + getRecipientType() + "," + getSenderID() + "," + getRecipientID();
	}
	
	// getters
	public String getUID() {
		return UID;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public String getData() {
		return data;
	}

	public String getSenderType() {
		return senderType;
	}

	public String getRecipientType() {
		return recipientType;
	}

	public REQUESTTYPE getType() {
		return type;
	}

	public int getSenderID() {
		return senderID;
	}

	public int getRecipientID() {
		return recipientID;
	}
	
	// setter
	private void setRequestType(int n) {
		switch(n) {
		case(0):
			type = REQUESTTYPE.SENDMESSAGE;
		break;
		
		case(1):
			type = REQUESTTYPE.REGISTRATION;
		break;
		
		case(2):
			type = REQUESTTYPE.SEARCH;
		break;
		
		case(3):
			type = REQUESTTYPE.VIEWCONTACTS;
		break;
		
		case(4):
			type = REQUESTTYPE.LOADCHATHISTORY;
		break;
		
		case(5):
			type = REQUESTTYPE.READLOG;
		break;
			
		case(6):
			type = REQUESTTYPE.CREATEGROUP;
		break;
			
		case(7):
			type = REQUESTTYPE.LOGIN;
		break;
			
		case(8):
			type = REQUESTTYPE.LOGOUT;
		break;
			
		case(9):
			type = REQUESTTYPE.ADDCONTACT;
		break;
		
		case(10):
			type = REQUESTTYPE.REMOVECONTACT;
		break;
			
		default:
			type = REQUESTTYPE.NULL;
		break;
		
		}
	}
	
}
