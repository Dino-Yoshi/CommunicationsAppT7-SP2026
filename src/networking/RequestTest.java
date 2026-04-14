// basic test drive for the Request Class and its methods + getters.
package networking;


public class RequestTest {

	public static void main(String[] args) {
		
		Request[] requests = new Request[10];
		
		for(int i = 0; i < 10; i++) {
			
			// String d, String sType, String rType, int t, int sID, int rID
			requests[i] = new Request("hello world", "USER", "ITUSER", 0, 0, 0);
			
			// UID > Request Type > Data > SenderType > RecipientType > SenderID > RecipientID
			System.out.println("Attributes of this Request: " + requests[i]);
			
		}
		
		return;
	}

}
