package client;

import javax.swing.*;

import networking.Request;
import networking.Request.REQUESTTYPE;

import java.awt.*;

public class ChatOverlayView extends JPanel{
//private attributes
	private GUI mainGUI;
	private String currentTargetID ="";
	
	//attributes for pane interactables
	private DefaultListModel<String> contactsModel;
	private JList<String> contactsList;
	private JTextArea chatHistory;
	private JTextField messageInputUI;
	
//constructor
	public ChatOverlayView(GUI mainGUI) {
		this.mainGUI = mainGUI;
		setLayout(new BorderLayout());
		

		
		//set up layout for our contacts list
		JPanel contactsPanel = new JPanel(new BorderLayout());
		contactsPanel.setBorder(BorderFactory.createTitledBorder("Conversations"));
		contactsModel = new DefaultListModel<>();
		contactsList = new JList<>(contactsModel);
		contactsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		contactsPanel.add(new JScrollPane(contactsList), BorderLayout.CENTER);
		
		//set up our navigation buttons: create group and logout
		JPanel navButtons = new JPanel(new GridLayout(2,1,5,5));
		JButton createGroupButton = new JButton("Create Group");
		JButton logoutButton = new JButton("Logout");
		
		navButtons.add(createGroupButton);
		navButtons.add(logoutButton);
		contactsPanel.add(navButtons, BorderLayout.SOUTH); //adds to bottom of our contacts panel
		
		//set up our chat conversation and field for text input
		JPanel converPanel = new JPanel(new BorderLayout());
		converPanel.setBorder(BorderFactory.createTitledBorder("Active Conversations"));
		
		//set up the area for our text conversations
		chatHistory = new JTextArea("Select a to start Chatting with: \n");
		chatHistory.setEditable(false); // to prevent users typing in the convo area
		chatHistory.setLineWrap(true);
		converPanel.add(new JScrollPane(chatHistory), BorderLayout.CENTER);
		
		//setting up the input bar 
		JPanel inputPanel = new JPanel(new BorderLayout());
		messageInputUI = new JTextField();
		JButton sendButton = new JButton("Send");
		
		inputPanel.add(messageInputUI, BorderLayout.CENTER);
		inputPanel.add(sendButton, BorderLayout.EAST);
		converPanel.add(inputPanel, BorderLayout.SOUTH);
		
		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, contactsPanel, converPanel);
		splitPane.setDividerLocation(180);
		
		add(splitPane, BorderLayout.CENTER);
		
		//our action Listners
		sendButton.addActionListener(e-> sendMessage(messageInputUI.getText()));
		messageInputUI.addActionListener(e-> sendMessage(messageInputUI.getText()));
		contactsList.addListSelectionListener(e->{
			
			Request req = new Request("Fetching Contacts", "USER", "SERVER", 3, mainGUI.getCurrentUser().getUID(), -1);
			mainGUI.getNetworkClient().sendRequest(req);
			if(!e.getValueIsAdjusting() && contactsList.getSelectedValue() !=null) {
				openConversation(contactsList.getSelectedValue());
			}
		});
		logoutButton.addActionListener(e-> clickLogout());
		
		createGroupButton.addActionListener(e -> mainGUI.switchView(VIEWSTATE.GROUPCREATION));
	}
	


	//Methods
	//clears our previous state of contacts and updates it with our newer ones
	public void loadContacts() {
		contactsModel.clear();
		
		contactsModel.addElement("Darien");
		contactsModel.addElement("Clarize");
		contactsModel.addElement("Victor");
		
		contactsModel.addElement("Group 7");
	}
	
	public void openConversation(String targetChat) {
		
		Request req = new Request("Fetching Chatlog", "USER", "SERVER", 4, mainGUI.getCurrentUser().getUID(), -1);
		mainGUI.getNetworkClient().sendRequest(req);
		this.currentTargetID = targetChat;
		chatHistory.setText("Conversation with: " + currentTargetID+ "\n");
	}
	
	public void sendMessage(String content) {
		//if empty, exit out method
		if(content.trim().isEmpty()) {
			return;
		}
		
		chatHistory.append("You: " + content + "\n");
		
		//just dummy response to format of texting
		if(!currentTargetID.isEmpty()) {
			chatHistory.append(currentTargetID + ": I recieved your message!\n");
		}
		
		messageInputUI.setText("");//reset text input area
		
		// Darien Test (Sending Message)
		Request req = new Request(content, "USER", "SERVER", 0, mainGUI.getCurrentUser().getUID(), -1);
		mainGUI.getNetworkClient().sendRequest(req);
		//
		

		
	}
	
	//adds a newly created group to the UI list
	public void addNewGroup(String groupName) {
		contactsModel.addElement("[Group] " + groupName);
		
	}
	
	public void clickLogout() {
		
		// test request, but should dynamically make one based on the user's actual id.
		Request req = new Request(mainGUI.getCurrentUser().getUsername(), "USER", "NULL",8,mainGUI.getCurrentUser().getUID(),-1);
		Request decision = mainGUI.getNetworkClient().sendRequest(req);
		
		if(decision != null && decision.getType() == REQUESTTYPE.SUCCESS) {
			mainGUI.getNetworkClient().disconnect();
			mainGUI.switchView(VIEWSTATE.LOGIN);
		}else {
			JOptionPane.showMessageDialog(this, "An unknown exception has occurred when attempting to log out... Please contact your admin.", "Logout Error", JOptionPane.ERROR_MESSAGE);
		}
		
		
	}
	
}
