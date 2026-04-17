package client;

import javax.swing.*;
import java.awt.*;
import java.util.*;

public class GroupCreationView extends JPanel{
	private GUI mainGUI;
	
	//attributes for interactables
	private JTextField groupNameUI;
	private JTextField searchUI;
    private DefaultListModel<String> searchResultsModel;
    private JList<String> searchResultsList;
    private DefaultListModel<String> selectedMembersModel;
    private JList<String> selectedMembersList;
    
    public GroupCreationView(GUI mainGUI) {
        this.mainGUI = mainGUI;

        setLayout(new BorderLayout(10,10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JPanel groupDetPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        groupDetPanel.setBorder(BorderFactory.createTitledBorder("Step 1: Name Your Group"));
    
        groupNameUI = new JTextField(20);
        groupDetPanel.add(new JLabel("Group Name:"));
        groupDetPanel.add(groupNameUI);
        add(groupDetPanel, BorderLayout.NORTH);
        
        //setup Search User Panel
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setBorder(BorderFactory.createTitledBorder("Step 2: Find Users"));
        
        //setup for the search bar
        JPanel searchBarPanel = new JPanel(new BorderLayout());
        searchUI = new JTextField();
        JButton searchButton = new JButton("Search");
        searchBarPanel.add(searchUI, BorderLayout.CENTER);
        searchBarPanel.add(searchButton, BorderLayout.EAST);
        leftPanel.add(searchBarPanel, BorderLayout.NORTH);
        
        //setup our search Result list
        searchResultsModel = new DefaultListModel<>();
        searchResultsList = new JList<>(searchResultsModel);
        searchResultsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        leftPanel.add(new JScrollPane(searchResultsList), BorderLayout.CENTER);
        
        //adds the add memeber button
        JButton addMemberButton = new JButton("Add to Group");
        leftPanel.add(addMemberButton, BorderLayout.SOUTH);
        
        //setup our selected users panel
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setBorder(BorderFactory.createTitledBorder("Step 3: Group Members"));
        
        //Selected users list
        selectedMembersModel = new DefaultListModel<>();
        selectedMembersList = new JList<>(selectedMembersModel);
        selectedMembersList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        rightPanel.add(new JScrollPane(selectedMembersList), BorderLayout.CENTER);
        
        
        //add our remove button for removing users
        JButton removeMemberButton = new JButton("<< Remove");
        rightPanel.add(removeMemberButton, BorderLayout.SOUTH);
        
        //setup bring our two panes together
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, rightPanel);
        splitPane.setResizeWeight(0.5); //make it even
        add(splitPane, BorderLayout.CENTER);
        
        //set up for the navigation panel, adds buttons
        JPanel bottomNav = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton cancelButton = new JButton("Cancel");
        JButton createGroupButtonn = new JButton("Create Group");

        bottomNav.add(cancelButton);
        bottomNav.add(createGroupButtonn);
        add(bottomNav, BorderLayout.SOUTH);
        
        //sets up our Active listeners
        searchButton.addActionListener(e -> {
            searchResultsModel.clear();
            searchResultsModel.addElement("Victor");
            searchResultsModel.addElement("Clarize");
            searchResultsModel.addElement("Darien");
        });
        
        //moves selected user from contacting list to wanted for group
        addMemberButton.addActionListener(e -> {
            String selectedUser = searchResultsList.getSelectedValue();
            if (selectedUser != null) {
                // Prevent adding the same person twice
                if (!selectedMembersModel.contains(selectedUser)) {
                    selectedMembersModel.addElement(selectedUser);
                }
            }
        });
        
        //removes a user from group 
        removeMemberButton.addActionListener(e -> {
            String selectedMember = selectedMembersList.getSelectedValue();
            if (selectedMember != null) {
                selectedMembersModel.removeElement(selectedMember);
            }
        });
        
        //when pressed it sends us back to chat menu, clearing fields
        cancelButton.addActionListener(e -> {
            clearFields();
            mainGUI.switchView(VIEWSTATE.MENU);
        });
        
        //simiply confirms choice and saves the choice
        createGroupButtonn.addActionListener(e-> attemptCreateGroup());
        
    }
    
    private void attemptCreateGroup() {
    	String groupName = groupNameUI.getText().trim();
    	
    	//checks group was named
    	if(groupName.isEmpty()) {
    		JOptionPane.showMessageDialog(this, "Please Enter a Group Name", "Error",JOptionPane.ERROR_MESSAGE);
    		return;
    	}
    	
    	//check if anyone was added
    	if(selectedMembersModel.isEmpty()) {
    		JOptionPane.showMessageDialog(this, "Please add at least one user to the group", "Error", JOptionPane.ERROR_MESSAGE);
    		return;
    	}
    	
    	String membersList = "";
    	for(int i=0; i <selectedMembersModel.size(); i++) {
    		membersList += selectedMembersModel.getElementAt(i);
    		
    		if( i< selectedMembersModel.size()-1) {
    			membersList+= ",";
    		}
    	}
    	
    	JOptionPane.showMessageDialog(this, "Group: "+groupName + " created successfully with members: "  + membersList);
    	
    	mainGUI.getChatOverlayView().addNewGroup(groupName);
    	
    	clearFields();
    	mainGUI.switchView(VIEWSTATE.MENU);
    	
    }
    
    public void clearFields() {
        groupNameUI.setText("");
        searchUI.setText("");
        searchResultsModel.clear();
        selectedMembersModel.clear();
    }
}
