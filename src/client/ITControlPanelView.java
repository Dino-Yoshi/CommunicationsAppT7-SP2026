package client;

import javax.swing.*;

import networking.Request;

import java.awt.*;

public class ITControlPanelView extends JPanel{
	private GUI mainGUI;
	
	//attributes for our pane interactables
	private JTextField newUserUI;
	private JPasswordField newPassUI;
	private JTextArea	logUI;
	private JTextField searchUI;
	private DefaultListModel<String> searchResultsModel;
	private JList<String> searchResultsList;
	private DefaultListModel<String> userChatsModel;
	private JList<String> userChatsList;
	
//constructor
	public ITControlPanelView(GUI mainGUI) {
		this.mainGUI = mainGUI;
		
		setLayout(new BorderLayout(10,10));
		setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		
		//set up for the Registration Panel
		JPanel regiPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		regiPanel.setBorder(BorderFactory.createTitledBorder("Register New User"));
		
		newUserUI = new JTextField(10);
		newPassUI = new JPasswordField(10);
		JButton regiButton = new JButton("Register");
		
		regiPanel.add(new JLabel("username:"));
		regiPanel.add(newUserUI);
		regiPanel.add(new JLabel("Password:"));
		regiPanel.add(newPassUI);
		regiPanel.add(regiButton);
		add(regiPanel, BorderLayout.NORTH);
		
		//setup for system log panel
		JPanel logPanel = new JPanel(new BorderLayout());
		logPanel.setBorder(BorderFactory.createTitledBorder("System & Chat Logs"));
		
		logUI = new JTextArea("Search for a user and select one of their chats to view logs...\n");
		logUI.setEditable(false);
		logPanel.add(new JScrollPane(logUI), BorderLayout.CENTER);
		
		JPanel logButton = new JPanel();
		JButton fetchAuthenticationButton = new JButton("Fetch Server Authentication Logs");
		logButton.add(fetchAuthenticationButton);
		logPanel.add(logButton, BorderLayout.SOUTH);
		
		//setup for search user panel
		JPanel searchTopPanel = new JPanel(new BorderLayout());
        searchTopPanel.setBorder(BorderFactory.createTitledBorder("1. User Database"));
        
        JPanel sTop = new JPanel(new BorderLayout());
        searchUI = new JTextField();
        JButton searchBtn = new JButton("Search");
        sTop.add(searchUI, BorderLayout.CENTER); 
        sTop.add(searchBtn, BorderLayout.EAST);
        
        searchResultsModel = new DefaultListModel<>();
        searchResultsList = new JList<>(searchResultsModel);
        searchResultsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        searchTopPanel.add(sTop, BorderLayout.NORTH);
        searchTopPanel.add(new JScrollPane(searchResultsList), BorderLayout.CENTER);
        
        //setup Users Chats Panel
        JPanel searchBottomPanel = new JPanel(new BorderLayout());
        searchBottomPanel.setBorder(BorderFactory.createTitledBorder("2. Selected User's Active Chats"));
        
        userChatsModel = new DefaultListModel<>();
        userChatsList = new JList<>(userChatsModel);
        userChatsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        searchBottomPanel.add(new JScrollPane(userChatsList), BorderLayout.CENTER);
        
        //setup for add the panes together as a split pane
        //makes search on top and chats on the bottom
        JSplitPane rightSplit = new JSplitPane(JSplitPane.VERTICAL_SPLIT, searchTopPanel, searchBottomPanel);
        rightSplit.setResizeWeight(0.5);
        
        //makes logs on the left side while search and chats are on the right
        JSplitPane mainSplit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, logPanel, rightSplit);
        mainSplit.setResizeWeight(0.60); 
        add(mainSplit, BorderLayout.CENTER);
        
        //setup for the navigation panel
        JPanel bottomNav = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton backButton = new JButton("Go to Chat Menu");
        JButton logoutButton = new JButton("Logout");
        
        bottomNav.add(backButton); 
        bottomNav.add(logoutButton);
        add(bottomNav, BorderLayout.SOUTH);
        
        //setup for our Action Listners
        backButton.addActionListener(e -> mainGUI.switchView(VIEWSTATE.MENU));
        
        logoutButton.addActionListener(e -> {
    		Request req = new Request(mainGUI.getCurrentUser().getUsername(), "USER", "SERVER", 8, mainGUI.getCurrentUser().getUID(), -1);
    		mainGUI.getNetworkClient().sendRequest(req);
            mainGUI.switchView(VIEWSTATE.LOGIN);
        });
        
        regiButton.addActionListener(e -> registerNewUser());
        
        //area to see authentication logs, such as
        //any type of logins, new added user, etc...
        fetchAuthenticationButton.addActionListener(e ->{
    		Request req = new Request("", "USER", "SERVER", 5, mainGUI.getCurrentUser().getUID(), -1);
    		Request res = mainGUI.getNetworkClient().sendRequest(req);
        	logUI.setText("Authentication Logs \n");
        	
        	String[] messages = res.getData().split("\n");
    		
    		for(int i = 0; i < messages.length; i++) {
    			logUI.append(messages[i]);
    			logUI.append("\n");
    		}
        	
        });
        
        //shows a list of existing users
        searchBtn.addActionListener(e -> {
        	
        	Request req = new Request(searchUI.getText(), "USER", "SERVER", 2, mainGUI.getCurrentUser().getUID(), -1);
    		Request res = mainGUI.getNetworkClient().sendRequest(req);
    		
    		String[] returnedQuery = res.getData().split(",");
    		
    		searchResultsModel.clear();
    		
    		for(int i = 0; i < returnedQuery.length; i++) {
    			searchResultsModel.addElement(returnedQuery[i]);
    		}
        });
        
        //shows active contacts and group conversations from the selected user
        searchResultsList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && searchResultsList.getSelectedValue() != null) {

                userChatsModel.clear();
                
                
                
                Request req = new Request("Fetching Contacts", "USER", "SERVER", 3, mainGUI.getCurrentUser().getUID(), -1);
        		Request res = mainGUI.getNetworkClient().sendRequest(req);
        		
        		String[] listOfContacts = res.getData().split(",");
        		
        		for(int i = 0; i < listOfContacts.length; i++) {
        			userChatsModel.addElement(listOfContacts[i]);
        		}
                
               
            }
        });
        
        //
        userChatsList.addListSelectionListener(e -> {
        	
    		Request req = new Request("", "USER", "SERVER", 3, mainGUI.getCurrentUser().getUID(), -1);
    		mainGUI.getNetworkClient().sendRequest(req);
        	
            if (!e.getValueIsAdjusting() && userChatsList.getSelectedValue() != null) {
                String selectedUser = searchResultsList.getSelectedValue();
                String targetContact = userChatsList.getSelectedValue();
                
                logUI.setText("--- CHAT LOGS: " + selectedUser + " in " + targetContact + " ---\n\n");
                logUI.append("[" + selectedUser + "] Hello everyone!\n");
                
            }
        });	
    }
	
	public void registerNewUser() {
		String newUserName = newUserUI.getText();
		String newPassword = new String(newPassUI.getPassword());
		
		if(newUserName.isEmpty() || newPassword.isEmpty()) return;
		
		//display that new registration was successful
		JOptionPane.showMessageDialog(this, "User '" + newUserName + "' registered successfully.");
        newUserUI.setText(""); 
        newPassUI.setText("");
        
        String msg = newUserName + "," + newPassword;
		Request req = new Request(msg, "USER", "SERVER", 1, mainGUI.getCurrentUser().getUID(), -1);
		mainGUI.getNetworkClient().sendRequest(req);
        
		/* layout of how it will generally go
		 * String regData = newUsername + "," + newPassword; Request regReq = new
		 * Request(regData, "IT_USER", "SERVER", 1, 0, 0);
		 */
	}
	
}
