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
	private JButton ITPanelButton;
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
		ITPanelButton = new JButton("IT Panel");
		JButton refreshButton = new JButton("Refresh");
		JButton logoutButton = new JButton("Logout");
		
		ITPanelButton.setEnabled(false); // disabled by default.
		
		navButtons.add(refreshButton);
		navButtons.add(ITPanelButton);
		navButtons.add(createGroupButton);
		navButtons.add(logoutButton);
		contactsPanel.add(navButtons, BorderLayout.SOUTH); //adds to bottom of our contacts panel
		
		//set up our chat conversation and field for text input
		JPanel converPanel = new JPanel(new BorderLayout());
		converPanel.setBorder(BorderFactory.createTitledBorder("Active Conversations"));
		
		//set up the area for our text conversations
		chatHistory = new JTextArea("Click or create a chat to start talking with people! \n");
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
		refreshButton.addActionListener(e ->{ loadContacts(); openConversation(currentTargetID); });
		sendButton.addActionListener(e-> sendMessage(messageInputUI.getText()));
		
		
		
		ITPanelButton.addActionListener(e -> {mainGUI.switchView(VIEWSTATE.ITPANEL);});
		messageInputUI.addActionListener(e-> sendMessage(messageInputUI.getText()));
		contactsList.addListSelectionListener(e->{
			
			if(!e.getValueIsAdjusting() && contactsList.getSelectedValue() !=null) {
				openConversation(contactsList.getSelectedValue());
			}
		});
		logoutButton.addActionListener(e-> clickLogout());
		
		createGroupButton.addActionListener(e -> {
			mainGUI.switchView(VIEWSTATE.GROUPCREATION);
		});
		
		//possible solution loading contacts automatically
		Timer autoRefreshTimer = new Timer(10000, e -> {
			//check if user is logged in
			if(mainGUI.getCurrentUser() !=null) {
				loadContacts();
			}
		});
		autoRefreshTimer.start();
	}
	


	//Methods
	//clears our previous state of contacts and updates it with our newer ones
	public void loadContacts() {
		//new
		String currentlySelected = contactsList.getSelectedValue();
		
		contactsModel.clear();
		
		Request req = new Request("Fetching Contacts", "USER", "SERVER", 3, mainGUI.getCurrentUser().getUID(), -1);
		Request res = mainGUI.getNetworkClient().sendRequest(req);
		
		//check if response was sent
		if(res != null && res.getData() != null) {
			String[] listOfContacts = res.getData().split(",");
			
			for(int i =0; i < listOfContacts.length;i++) {
				//avoid empty strings from showing up as blank contacts
				if(!listOfContacts[i].trim().isEmpty()) {
					contactsModel.addElement(listOfContacts[i]);
				}
			}
		}
		
		//prevents werid UI issue
		if(currentlySelected != null && contactsModel.contains(currentlySelected)) {
			contactsList.setSelectedValue(currentlySelected, true);
		}
		
		/*
		 * String[] listOfContacts = res.getData().split(",");
		 * 
		 * for(int i = 0; i < listOfContacts.length; i++) {
		 * contactsModel.addElement(listOfContacts[i]); }
		 */
		
	}
	
	public void openConversation(String targetChat) {
		
		
		Request req = new Request(targetChat, "USER", "USER", 4, mainGUI.getCurrentUser().getUID(), -1);
		Request res = mainGUI.getNetworkClient().sendRequest(req);
		this.currentTargetID = targetChat;
		chatHistory.setText("Conversation with: " + currentTargetID + "\n");
		
		String[] messages = res.getData().split("\n");
		
		for(int i = 0; i < messages.length; i++) {
			chatHistory.append(messages[i]);
			chatHistory.append("\n");
		}
		
	}
	
	public void sendMessage(String content) {
		//if empty, exit out method
		if(content.trim().isEmpty()) {
			return;
		}
		
		chatHistory.append(mainGUI.getCurrentUser() + ": " + content + "\n");
		
		
		/*
		
		//just dummy response to format of texting
		if(!currentTargetID.isEmpty()) {
			chatHistory.append(currentTargetID + ": I recieved your message!\n");
		}
		
		*/
		
		messageInputUI.setText("");//reset text input area
		
		// Darien Test (Sending Message)
		Request req = new Request(content + "," + currentTargetID, "USER", "USER", 0, mainGUI.getCurrentUser().getUID(), -1);
		Request res = mainGUI.getNetworkClient().sendRequest(req);
		
	}
	
	//adds a newly created group to the UI list
	public void addNewGroup(String groupName) {
		contactsModel.addElement("[Group] " + groupName);
		
	}
	
	
	//method to wipe chat history and contacts
	//helpful for when a current user logs out and a different user logs in
	public void clearChatState() {
		chatHistory.setText("Select a user to start chatting with: \n");
		currentTargetID = "";
		contactsModel.clear();
	public void setITButton() {
		ITPanelButton.setEnabled(true);
	}
	
	public void clickLogout() {
		
		// test request, but should dynamically make one based on the user's actual id.
		Request req = new Request(mainGUI.getCurrentUser().getUsername(), "USER", "NULL",8,mainGUI.getCurrentUser().getUID(),-1);
		Request decision = mainGUI.getNetworkClient().sendRequest(req);
		
		if(decision != null && decision.getType() == REQUESTTYPE.SUCCESS) {
			mainGUI.getNetworkClient().disconnect();
			
			clearChatState(); //reset our chatoverlaypanel for the next user
			mainGUI.switchView(VIEWSTATE.LOGIN);
		}else {
			JOptionPane.showMessageDialog(this, "An unknown exception has occurred when attempting to log out... Please contact your admin.", "Logout Error", JOptionPane.ERROR_MESSAGE);
		}
	}
}
