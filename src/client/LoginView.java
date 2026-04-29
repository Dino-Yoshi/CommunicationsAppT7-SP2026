package client;

import javax.swing.JPanel;
import networking.Request;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;

import javax.swing.*;

public class LoginView extends JPanel{
//private attributes
	private String usernameField;
	private String passwordField;
	private GUI mainGUI;
	
	//attributes for panel interactables 
	private JTextField userFieldUI;
	private JPasswordField passFieldUI; //made it JPasswordField so it can make those privacy dots
	private JButton loginButton;
	
	public LoginView(GUI mainGUI) {
		this.mainGUI = mainGUI;
		
		//we will set up the overall layout with the GridBagLayout for a cleaner look
		setLayout(new GridBagLayout());
		GridBagConstraints grid = new GridBagConstraints();
		grid.insets = new Insets(10,10,10,10);
		
		userFieldUI = new JTextField(15);
		passFieldUI = new JPasswordField(15);
		loginButton = new JButton("Login");
		
		//set up the format of our title 
		grid.gridx =0;
		grid.gridy = 0;
		grid.gridwidth = 2;
		JLabel titleLabel = new JLabel("System Login");
		titleLabel.setFont(new Font("Arial", Font.BOLD, 18)); //could be change to diff font, but works for now
		add(titleLabel, grid);
		
		//set up the format of username 
		grid.gridx =0;
		grid.gridy = 1;
		grid.gridwidth = 1;
		add(new JLabel("Username:"), grid);
		grid.gridx = 1;
		add(userFieldUI, grid);
		
		//set up the format of password
		grid.gridx =0;
		grid.gridy = 2;
		add(new JLabel("Password:"),grid);
		grid.gridx = 1;
		add(passFieldUI, grid);
		
		//set up the format of Login Button
		grid.gridx = 1;
		grid.gridy = 3;
		add(loginButton, grid);
		
		//action Listeners
		loginButton.addActionListener(e -> attemptLogin());
	}
	
//methods
	private void attemptLogin() {
		String username = userFieldUI.getText();
		String password = new String(passFieldUI.getPassword());
		
		// If any field is empty, exit
		if(username.isEmpty() || password.isEmpty()) return;
				
		 String creds = username + "," + password;
		 
		 // create a login request
		 Request loginReq = new Request(creds, "USER", "SERVER", 7, -1, -1);
		 
		 User user = new User(username, password);
		 
		 Request res = mainGUI.getNetworkClient().connectToServer(loginReq, user);
		 
		// either login or fail
			if (res != null && res.getType() == Request.REQUESTTYPE.SUCCESS) {
						 
				//clears chat		
					mainGUI.getChatOverlayView().clearChatState();
						 
					//check if we tagged the user as IT
					if ("IT".equals(res.getRecipientType()) || (res.getData() != null && res.getData().contains("logged in as an IT"))) {
					    System.out.println("Successful IT login by: " + username);
					    ITUser adminUser = new ITUser(username, password);
					    adminUser.setUID(res.getRecipientID());
					    mainGUI.setCurrentUser(adminUser);
					    mainGUI.getChatOverlayView().setITButton(); 
					    mainGUI.switchView(VIEWSTATE.ITPANEL); 
					}
				else {//normal user login
					System.out.println("Successful login by: " + username);
					mainGUI.setCurrentUser(user);
					mainGUI.switchView(VIEWSTATE.MENU); 
				}	 
					clearFields();
						 
			} else {
						 
					JOptionPane.showMessageDialog(this, "Login Failed. Invalid credentials.", "Error", JOptionPane.ERROR_MESSAGE);
			}
		
	}
	
	//clears all fields
	public void clearFields() {
		userFieldUI.setText("");
		passFieldUI.setText("");
		usernameField = "";
		passwordField = "";
	}
	
	
}
