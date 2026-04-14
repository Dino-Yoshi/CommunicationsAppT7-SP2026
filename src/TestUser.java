
public class TestUser {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		User[] users = new User[10];
		
		for(int i = 0; i < 10; i++) {
			users[i] = new User("DinoYoshi", "qwerty");
			
			System.out.println("User Information: " + users[i]);
		}
	}

}
