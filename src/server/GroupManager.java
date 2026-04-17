package server;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

// CLASS DESCRIPTION:
// manages chat groups and group membership

public class GroupManager {

    // ATTRIBUTES
    private Map<String, Set<String>> groups;	// maps each group name to group's unique member list

    // CONSTRUCTOR
    public GroupManager() {
        this.groups = new LinkedHashMap<>();	// creates group map
    }

    // METHODS

    // creates a new group with selected members
    public synchronized boolean createGroup(String groupName, List<String> members) {
        String cleanGroupName = normalize(groupName);	// cleans the group name
        
        if (cleanGroupName == null) {	// checks if the group name is missing or blank
            return false;				// rejects invalid group creation
        }	// end invalid group name check
        if (groups.containsKey(cleanGroupName)) {	// checks if the group already exists
            return false;							// rejects duplicate group names
        }	// end duplicate group check
        
        groups.put(cleanGroupName, new LinkedHashSet<>());	// creates the group member set
        
        if (members != null) {	// checks if an initial member list was provided
            for (String member : members) {			// start loop through each provided member
                addMember(cleanGroupName, member);	// adds each valid member to the group
            }	// end member loop
        } 		// end initial member check
        return true;	// reports that the group was created
    }

    // adds one member to an existing group
    public synchronized boolean addMember(String groupName, String username) {
        String cleanGroupName = normalize(groupName);	// cleans the group name
        String cleanUsername = normalize(username);		// cleans the username
        
        if (cleanGroupName == null || cleanUsername == null) {	// checks if either value is missing or blank
            return false;										// rejects invalid member additions
        }	// end missing value check
        if (!groups.containsKey(cleanGroupName)) {	// checks if the group exists
            return false;							// rejects additions to unknown groups
        }	// end missing group check
        return groups.get(cleanGroupName).add(cleanUsername); // adds the member and returns true only if it was new
    }

    // removes one member from an existing group
    public synchronized boolean removeMember(String groupName, String username) {
        String cleanGroupName = normalize(groupName);			// cleans the group name
        String cleanUsername = normalize(username);				// cleans the username
        
        if (cleanGroupName == null || cleanUsername == null) { 	// checks if either value is missing or blank
            return false;										// rejects invalid member removals
        }	// end missing value check
        if (!groups.containsKey(cleanGroupName)) {	// checks if the group exists
            return false;							// rejects removals from unknown groups
        }	// end missing group check
        return groups.get(cleanGroupName).remove(cleanUsername);	// removes the member and returns true only if it existed
    }

    // returns all members in a group
    public synchronized List<String> getMembers(String groupName) {
        String cleanGroupName = normalize(groupName);	// cleans the group name
        
        if (cleanGroupName == null) {	// checks if the group name is missing or blank
            return new ArrayList<>();	// returns an empty list for invalid group names
        }	// end invalid group name check
        if (!groups.containsKey(cleanGroupName)) {	// checks if the group exists
            return new ArrayList<>();				// returns an empty list for unknown groups
        }	// end missing group check
        return new ArrayList<>(groups.get(cleanGroupName));	// returns a copy of the group's members
    }

    // checks if a group exists
    public synchronized boolean groupExists(String groupName) {
        String cleanGroupName = normalize(groupName);	// cleans the group name
        return cleanGroupName != null && groups.containsKey(cleanGroupName);	// returns true only if the group exists
    }

    // searches group names by a partial query
    public synchronized List<String> searchGroups(String query) {
        String cleanQuery = normalize(query); 		// cleans the search query
        List<String> results = new ArrayList<>(); 	// creates the list that will store matching groups
        
        if (cleanQuery == null) {	// checks if the query is missing or blank
            return results;			// returns an empty list for invalid searches
        }	// end invalid query check
        
        String lowerQuery = cleanQuery.toLowerCase();	// converts query to lowercase for case-insensitive searching
        
        for (String groupName : groups.keySet()) {		// start loop through each group name
            if (groupName.toLowerCase().contains(lowerQuery)) {	// checks if the group name contains the query text
                results.add(groupName);	// adds the matching group name to the result list
            }	// end match check
        }		// end loop
        return results;	// returns all group names that matched the query
    }

    // checks if a user is a member of a group
    public synchronized boolean isMember(String groupName, String username) {
        String cleanGroupName = normalize(groupName); 			// cleans the group name
        String cleanUsername = normalize(username);				// cleans the username
        
        if (cleanGroupName == null || cleanUsername == null) { 	// checks if either value is missing or blank
            return false;										// invalid values cannot match group membership
        }	// end missing value check
        if (!groups.containsKey(cleanGroupName)) {	// checks if the group exists
            return false;							// returns false for unknown groups
        }	// end missing group check
        return groups.get(cleanGroupName).contains(cleanUsername);	// returns whether the user belongs to the group
    }

    // cleans group names, usernames, and query text
    private String normalize(String value) {
        if (value == null) {	// checks if the value is missing
            return null; 		// returns null for missing values
        }	// end null check
        
        String trimmed = value.trim();				// removes leading and trailing spaces
        return trimmed.isEmpty() ? null : trimmed;	// returns null for blank text otherwise returns the cleaned text
    }
}