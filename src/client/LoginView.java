package client;

import javax.swing.JPanel;

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
		
		//if any field is empty
		if(username.isEmpty() || password.isEmpty()) return;
		
		//logic for validation
		//this is just some hardCoded logic just to test out all buttons and field work
		if(username.equals("bob") && password.equals("pass123")) {
			System.out.println("Successful login by: "+ username);
			mainGUI.switchView(VIEWSTATE.MENU);
			clearFields();
		}
		else if(username.equals("admin") && password.equals("admin123")) {
			System.out.println("IT admin was successful");
			mainGUI.switchView(VIEWSTATE.ITPANEL);
			clearFields();
		}
		else {
			System.out.println("Failed LOGIN ATTEMPT");
			
			JOptionPane.showMessageDialog(this, "Invalid Credentials", "Error", JOptionPane.ERROR_MESSAGE);
		}
		
		//this bottom code is what will be primarily used when fully integrated
		
		/*
		 * String creds = usernameField + "," + passwordField;
		 * Request loginReq = new Request(credentials, "USER", "NULL",7,0,-1);
		 * 
		 * mainGUI.getNetworkClient().sendRequest(loginReq);
		 * 
		 * clearFields();
		 * 
		 * */
	}
	
	//clears all fields
	public void clearFields() {
		userFieldUI.setText("");
		passFieldUI.setText("");
		usernameField = "";
		passwordField = "";
	}
	
	
}
