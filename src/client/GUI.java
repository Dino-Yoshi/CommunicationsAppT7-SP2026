package client;

import java.awt.CardLayout;
import javax.swing.*;

public class GUI {
//private memember variables
	private VIEWSTATE currentState;
	private User currentUser;
	
	//private ClientNetwork networkClient; use when we integrate
	
	//private instances of our panels
	private LoginView loginView;
	private ChatOverlayView chatOverlayView;
	//TODO: set up the other classes from part of section 1
	
	//private GUI variables
	private JFrame mainFrame;
	private JPanel cardPanel;
	private CardLayout cardLayout;
	
//constructor
	public GUI() {
		//given we must always login first before use of application, set currentState to LOGIN as default value
		this.currentState = VIEWSTATE.LOGIN;
		
		//used to initialize the ClientNetwork class to create a thread for the user
		//this.networkClient = new ClientNetwork("localHost", 7777); will be used when integrated
		
		mainFrame = new JFrame("Group 7 Communication App");
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //ensure pressing X icon closes app
		mainFrame.setSize(800,600);
		mainFrame.setLocationRelativeTo(null); //area of launch
		

		cardLayout = new CardLayout();
		cardPanel = new JPanel(cardLayout);
		
		loginView = new LoginView(this);
		chatOverlayView = new ChatOverlayView(this);
		
		cardPanel.add(loginView, VIEWSTATE.LOGIN.name());
		cardPanel.add(chatOverlayView, VIEWSTATE.MENU.name());
		mainFrame.add(cardPanel);
	}
	
//methods
	
	public void setCurrentUser(User user) {
		this.currentUser = user;
	}
	
	public User getCurrentUser() {
		return currentUser;
	}
	
	//getter to get the connected instance of clientNetwork tied to GUI
	//used for when we integrate stuff
	/*
	 * 
	 * public ClientNetwork getNetworkClient() {
		return networkClient;
	
	}
	*/
	
	//method to automatically call for the LoginView Class
	public void startApplication() {
		switchView(VIEWSTATE.LOGIN);
		mainFrame.setVisible(true);
		
		
	}
	
	public void switchView(VIEWSTATE state) {
		currentState = state;
		cardLayout.show(cardPanel, state.name());
		
		if(state == VIEWSTATE.MENU) {
			chatOverlayView.loadContacts();
		}
	}
	
	//OUR MAIN METHOD 
	//creates the instance of our GUI class
	public static void main(String[] arg) {
		//helps ensure stability towards the current thread when a panel is displayed
		SwingUtilities.invokeLater(() -> {
			GUI app = new GUI();
			app.startApplication();	//will go straight to LoginView panel
		});
	}
	

//some test methods to see if GUI class interacts well
	public VIEWSTATE getCurrentSate() {
		return currentState;
	}
	
	public LoginView getLoginView() {
		return loginView;
	}
}
