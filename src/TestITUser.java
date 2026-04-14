
public class TestITUser {

	public static void main(String[] args) {
		
		// make a list of users.
		User[] users = new User[10];
		
		for(int i = 0; i < 10; i++) {
			users[i] = new User("DinoYoshi", "qwerty");
			
			System.out.println("User Information: " + users[i]);
		}
		
		// make a list of itusers.
		
		ITUser[] itusers = new ITUser[10];
		
		for(int i = 0; i < 10; i++) {
			itusers[i] = new ITUser("DinoYoshi", "qwerty");
			System.out.println("ITUser Information: " + itusers[i]);
		}
		
		// make a list of users and itusers. display all of them.
		
		User[] registeredUsers = new User[20];
		
		for(int i = 0; i < 10; i++) {
			registeredUsers[i] = users[i];
		}
		
		for(int i = 0; i < 10; i++) {
			registeredUsers[i+10] = itusers[i];
		}
		
		for(int i = 0; i < 20; i++) {
			System.out.println("All User Information: " + registeredUsers[i]);
		}

	}

}
