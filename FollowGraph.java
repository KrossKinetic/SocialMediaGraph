/**
 * Represents a graph of User objects.
 */
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;
import java.util.stream.Collectors;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class FollowGraph implements Serializable{
    private ArrayList<User> users = new ArrayList<User>();
    public static final int MAX_USERS = 100;
    public boolean[][] connections = new boolean[MAX_USERS][MAX_USERS];
    private int userCount = 0;
    
    /**
     * Gets the total number of users in the follow graph.
     * @return The total number of users.
     */
    public int getUserCount(){
        return userCount;
    }

    /**
     * Returns the list of users in the graph.
     * @return The ArrayList containing all users in the graph.
     */
    public ArrayList<User> getUsers(){
        return users;
    }

    /**
     * Constructs a new FollowGraph object.
     *
     * @custom.postcondition A new FollowGraph object is created.
     */
    public FollowGraph(){}

    /**
     * Constructs a new FollowGraph object with the specified users, connections, and user count.
     * @param users The list of users in the graph.
     * @param connections The adjacency matrix representing the connections between users.
     * @param userCount The total number of users in the graph.
     * @custom.precondition  'users' is a valid ArrayList of User objects, 'connections' is a valid 2D boolean array, and userCount is a non-negative integer.
     * @custom.postcondition A new FollowGraph object is created with the provided users, connections, and user count.
     */
    public FollowGraph(ArrayList<User> users, boolean[][] connections, int userCount){
        this.users = users;
        this.connections = connections;
        this.userCount = userCount;
    }

    /**
     * Adds a new user to the follow graph.
     * @param userName The name of the new user to be added.
     * @custom.precondition  'userName' is a non-null, non-empty String and the graph has not reached maximum capacity (MAX_USERS).
     * @custom.postcondition If a user with the same name doesn't already exist, the new user is added to the graph. Otherwise an error message is displayed to the console.
     */
    public void addUser(String userName){
        if (!checkIfUserExists(userName)){
            users.add(new User(userName));
            userCount = User.getUserCount();
        } else {
            System.out.println("Username doesn't exists.");
        }
    }

    /**
     * Removes a user from the follow graph.
     * @param userName The name of the user to be removed.
     * @custom.precondition 'userName' is a non-null, non-empty string, representing a user that exists within the graph.
     * @custom.postcondition If a user with the given name exists, the user and all associated connections are removed from the graph.  The indices of subsequent users are adjusted accordingly. Otherwise, an error message is displayed to the console.
     */
    public void removeUser(String userName){
        if (checkIfUserExists(userName)){
            User a = findUser(userName);
            int a_index = a.getIndexPos();
            int numUsers = users.size();
            for (int i = a_index; i < numUsers - 1; i++) {
                System.arraycopy(connections[i + 1], 0, connections[i], 0, numUsers);
                for (int j = 0; j < numUsers; j++) {
                    connections[j][i] = connections[j][i + 1];
                }
            }
            Arrays.fill(connections[numUsers - 1], false);
            for (int i = 0; i < numUsers - 1; i++) {
                connections[i][numUsers - 1] = false; 
            }
    
            for (int i = a_index + 1; i < numUsers; i++){
                users.get(i).setIndexPos(i - 1);
                users.set(i - 1, users.get(i));
            }
            users.remove(users.size() - 1);
            User.setUserCount(User.getUserCount() - 1);
            userCount--;
        } else {
            System.out.println("Username doesn't exists.");
        }
    }

    /**
     * Checks if a user with the given username exists in the graph.
     * @param userName The name of the user to check for.
     * @return True if a user with the given username exists, false otherwise.
     * @custom.precondition 'userName' is a non-null string.
     * @custom.postcondition The method returns true if the user exists, false otherwise.
     */
    public Boolean checkIfUserExists(String userName){
        for (User a: users){
            if (a.getUserName().equalsIgnoreCase(userName)){
                return true;
            }
        }
        return false;
    }

    /**
     * Retrieves the User object corresponding to the provided userName.
     * @param userName The username to search for.
     * @return The User object associated with the provided username, or null if the user is not found.
     * @custom.precondition 'userName' is a non-null string.
     * @custom.postcondition The method returns a User object if a user with the given username exists.
     */
    private User findUser(String userName){
        for (User a: users){
            if (a.getUserName().equalsIgnoreCase(userName)){
                return a;
            }
        }
        return null;
    }

    /**
     * Adds a connection between two users, indicating that one user follows the other.
     * @param userFrom The name of the user initiating the follow.
     * @param userTo The name of the user being followed.
     * @custom.precondition  'userFrom' and 'userTo' are non-null, non-empty strings representing users existing within the graph. 
     * @custom.postcondition  If both usernames exist, a connection from 'userFrom' to 'userTo' is established in the graph, represented as a 'true' value in the connections matrix. Otherwise an error message is printed for the user that doesn't exist.
     */
    public void addConnection(String userFrom, String userTo){
        int a_index = 0;
        int b_index = 0;
        if (!checkIfUserExists(userFrom)){
            System.out.println("userFrom doesn't exist.");
        } else {
            a_index = findUser(userFrom).getIndexPos();
        }

        if (!checkIfUserExists(userTo)){
            System.out.println("userTo doesn't exist.");
        } else {
            b_index = findUser(userTo).getIndexPos();
        }

        if (checkIfUserExists(userFrom) && checkIfUserExists(userFrom)){
            connections[a_index][b_index] = true;
        }
    }

    /**
     * Removes a connection between two users.
     * @param userFrom The name of the user who is currently following the other user.
     * @param userTo The name of the user being followed.
     * @custom.precondition  `userFrom` and `userTo` are non-null, non-empty strings representing users that exist in the graph and have a connection established between them.
     * @custom.postcondition If both users exist and a connection exists between them, the connection is removed. Otherwise, error messages indicating the non-existence of users or the connection are printed to the console.
     */
    public void removeConnection(String userFrom, String userTo){
        int a_index = 0;
        int b_index = 0;
        if (!checkIfUserExists(userFrom)){
            System.out.println("userFrom doesn't exist.");
        } else {
            User a = findUser(userFrom);
            a_index = a.getIndexPos();
        }

        if (!checkIfUserExists(userTo)){
            System.out.println("userTo doesn't exist.");
        } else {
            User b = findUser(userTo);
            b.getIndexPos();
        }

        if (checkIfUserExists(userTo) && checkIfUserExists(userFrom)){
            connections[a_index][b_index] = false;
        }
        
    }

    /**
     * Finds the shortest path between two users in the follow graph using Breadth-First Search.
     * @param userFrom The starting user for the path.
     * @param userTo The ending user for the path.
     * @return A string representing the shortest path between the two users, including the number of users along the path. Returns the path as a string of usernames delimited by "->" and the number of users in the path separated by "###".
     * @custom.precondition `userFrom` and `userTo` are valid usernames existing in the graph.
     * @custom.postcondition The shortest path from userFrom to userTo is returned as a String. If no path exists, an IllegalArgumentException is thrown.
     */
    public String shortestPath(String userFrom, String userTo){
        User a;
        User b;
        if (!checkIfUserExists(userFrom)){
            System.out.println("userFrom doesn't exist.");
            return "";
        } else {
            a = findUser(userFrom);
        }
        if (!checkIfUserExists(userTo)){
            System.out.println("userTo doesn't exist.");
            return "";
        } else {
            b = findUser(userTo);
        }
        int a_index = a.getIndexPos();
        int b_index = b.getIndexPos();
        int n = connections.length;
        boolean[] visited = new boolean[n];
        Queue<List<Integer>> queue = new LinkedList<>();
        queue.add(Collections.singletonList(a_index));
        visited[a_index] = true;
        while (!queue.isEmpty()) {
            List<Integer> path = queue.poll();
            int currentNode = path.get(path.size() - 1);

            if (currentNode == b_index) {
                StringBuilder str_path = new StringBuilder();
                for (Integer ind: path){
                    str_path.append("->").append(users.get(ind).getUserName()); 
                }
                return str_path.substring(2) + "###" + path.size();
            }

            for (int neighbor = 0; neighbor < n; neighbor++) {
                if (connections[currentNode][neighbor] == true && !visited[neighbor]) {
                    visited[neighbor] = true;
                    List<Integer> newPath = new ArrayList<>(path);
                    newPath.add(neighbor);
                    queue.add(newPath);
                }
            }
        }
        System.out.println("Path doesn't exist.");
        return "";
    }

    /**
     * Finds all paths between two users in the follow graph using Depth-First Search.
     * @param userFrom The starting user for the paths.
     * @param userTo The ending user for the paths.
     * @return An ArrayList of strings, where each string represents a path from userFrom to userTo. Each path string contains usernames delimited by "->". The list is sorted alphabetically.
     * @custom.precondition `userFrom` and `userTo` are valid usernames that exist within the graph.
     * @custom.postcondition An ArrayList containing all paths between the given users is returned. The ArrayList is sorted alphabetically. If either user doesn't exist, an IllegalArgumentException is thrown.
     */
    public ArrayList<String> allPaths(String userFrom, String userTo){
        User a;
        User b;

        if (!checkIfUserExists(userFrom)) {
            System.out.println("userFrom doesn't exist.");
            return null;
        } else {
            a = findUser(userFrom);
        }

        if (!checkIfUserExists(userTo)) {
            System.out.println("userTo doesn't exist.");
            return null;
        } else {
            b = findUser(userTo);
        }
        
        int a_index = a.getIndexPos();
        int b_index = b.getIndexPos();

        if (a_index == b_index){
            return new ArrayList<>(Collections.singletonList(userFrom));
        }

        int n = connections.length;
        List<List<Integer>> allPaths = new ArrayList<>();
        Stack<List<Integer>> stack = new Stack<>();
        stack.push(new ArrayList<>(Collections.singletonList(a_index)));

        while (!stack.isEmpty()) {
            List<Integer> path = stack.pop();
            int currentNode = path.get(path.size() - 1);
            if (currentNode == b_index) {
                allPaths.add(path);
            } else {
                for (int neighbor = 0; neighbor < n; neighbor++) {
                    if (connections[currentNode][neighbor] && !path.contains(neighbor)) {
                        List<Integer> newPath = new ArrayList<>(path);
                        newPath.add(neighbor);
                        stack.push(newPath);
                    }
                }
            }
        }

        ArrayList<String> result = new ArrayList<>();
        for (List<Integer> path : allPaths) {
            String strPath = path.stream().map(index -> users.get(index).getUserName()).collect(Collectors.joining("->"));
            result.add(strPath);
        }
        Collections.sort(result);
        return result;
    }

    /**
     * Finds all loops within the follow graph.
     * @return An ArrayList of strings, where each string represents a unique loop in the graph.
     * @custom.postcondition An ArrayList of strings is returned, representing all unique loops in the follow graph. The ArrayList is sorted alphabetically.
     */
    public ArrayList<String> findAllLoops(){
        int n = connections.length;
        ArrayList<String> result = new ArrayList<>();
        
        for (int start = 0; start < n; start++) {
            Stack<List<Integer>> stack = new Stack<>();
            stack.push(new ArrayList<>(Collections.singletonList(start)));
            
            while (!stack.isEmpty()) {
                List<Integer> path = stack.pop();
                int currentNode = path.get(path.size() - 1);
                
                for (int neighbor = 0; neighbor < n; neighbor++) {
                    if (connections[currentNode][neighbor]) {
                        if (neighbor == start && path.size() > 1) {
                            String cycle = path.stream().map(index -> users.get(index).getUserName()).collect(Collectors.joining("->"));
                            result.add(cycle + "->"  + users.get(start).getUserName());
                        } else if (!path.contains(neighbor)) {
                            List<Integer> newPath = new ArrayList<>(path);
                            newPath.add(neighbor);
                            stack.push(newPath);
                        }
                    }
                }
            }
        }

        ArrayList<ArrayList<String>> temp = new ArrayList<>();
        for (String s: result){
            temp.add(new ArrayList<>(Arrays.asList(s.split("->"))));
        }

        ArrayList<String> new_result = new ArrayList<>(normalize(temp, result));
        new_result.removeIf(Objects::isNull);
        Collections.sort(new_result);
        return new_result;
    }

    /**
    * Normalize loops lexicographically from the given list.
    * @param a A list of loops where each loop is represented as a list of strings (usernames).
    * @param result The original list of loops as strings. This will be modified to remove duplicates.
    * @return A new list of strings with duplicates removed, preserving the original formatting.
    * @custom.precondition `a` contains a valid ArrayList of ArrayList<String> and `result` is an ArrayList of string.
    * @custom.postcondition Returns an ArrayList of strings where each valid string is a unique loop. The original parameter `result` is modified in the process.
    */
    private ArrayList<String> normalize(ArrayList<ArrayList<String>> a, ArrayList<String> result){
        for (ArrayList<String> b: a){
            ArrayList<String> sortedList = new ArrayList<>(b);
            Collections.sort(sortedList);
            String max_elim = sortedList.get(0);
            b.remove(b.size()-1);
            while (!b.get(0).equalsIgnoreCase(max_elim)){
                Collections.rotate(b, -1);
            }
        }

        Set<ArrayList<String>> set = new HashSet<>();

        for (int i = 0; i < result.size(); i++){
            if(!set.add(a.get(i))){
                result.set(i, null);
            }
        }
        return result;
    }

    /**
     * Prints all users in the follow graph, sorted according to the provided comparator.
     *
     * @param comp The Comparator used to sort the users. This allows for flexibility in sorting by different criteria (e.g., name, number of followers).
     * @custom.precondition `comp` is a valid Comparator object for User objects.
     * @custom.postcondition All users in the graph are printed to the console, sorted according to the specified comparator.
     */
    public void printAllUsers(Comparator<User> comp){
        System.out.println("Users: ");
        System.out.printf("%-30s %-20s %-20s%n", "User Name", "Number of Followers", "Number of Following");
        ArrayList<User> copy_ = new ArrayList<>(users);
        Collections.sort(copy_, comp);
        for (User a : copy_) {
            String userName = a.getUserName();
            int followersCount = this.getAllFollowers(userName).size();
            int followingCount = this.getAllFollowing(userName).size();
            System.out.printf("%-38s %-20d %-20d%n", userName, followersCount, followingCount);
        }
    }

    /**
     * Prints all users in the follow graph, sorted primarily by the first comparator and secondarily by the second comparator.
     *
     * @param comp The primary Comparator for sorting users.
     * @param comp2 The secondary Comparator used to break ties when the primary Comparator considers two users equal.
     * @custom.precondition `comp` and `comp2` are valid Comparator objects for User objects.
     * @custom.postcondition  All users are printed to the console, sorted according to the combined criteria of both comparators.
     */
    public void printAllUsers(Comparator<User> comp, Comparator<User> comp2){
        System.out.println("Users:");
        System.out.printf("%-30s %-20s %-20s%n", "User Name", "Number of Followers", "Number of Following");
        ArrayList<User> copy_ = new ArrayList<>(users);
        Collections.sort(copy_,comp.thenComparing(comp2));
        for (User a : copy_) {
            String userName = a.getUserName();
            int followersCount = this.getAllFollowers(userName).size();
            int followingCount = this.getAllFollowing(userName).size();
            System.out.printf("%-38s %-20d %-20d%n", userName, followersCount, followingCount);
        }
    }

    /**
     * Prints all users that the given user is following.
     * @param username The name of the user.
     * @custom.precondition The `username` exists in the `FollowGraph`.
     * @custom.postcondition Prints a list of users that the given user is following to the console. Throws `IllegalArgumentException` if the user does not exist.
     */
    public void printAllFollowing(String username){
        System.out.println(getAllFollowing(username));
    }

    /**
     * Prints all users that are following the given user (i.e., the followers of the given user).
     * @param username The name of the user whose followers are to be printed.
     * @custom.precondition The `username` exists in the `FollowGraph`.
     * @custom.postcondition Prints a list of users following the specified user to the console.  If the specified user does not exist, an IllegalArgumentException is thrown.
     */
    public void printAllFollowers(String username){
        System.out.println(getAllFollowers(username));
    }

    /**
     * Gets a list of all users followed by the given username.
     * @param username The name of the user for whom to retrieve the list of followed users.
     * @return An ArrayList of strings representing the usernames of all users followed by the specified user.
     * @throws IllegalArgumentException if the specified username does not exist in the graph.
     * @custom.precondition  The graph contains the user specified by `username`.
     * @custom.postcondition  Returns a list of usernames that the given user is following. Throws an IllegalArgumentException if the username does not exist.
     */
    public ArrayList<String> getAllFollowing(String username){
        ArrayList<String> all_followers = new ArrayList<>();
        int a;
        if (!checkIfUserExists(username)) {
            throw new IllegalArgumentException("Username doesn't exist.");
        } else {
            a = findUser(username).getIndexPos();
        }
        
        for (int i = 0; i < connections.length; i++){
            if ( connections[a][i] == true){
                all_followers.add(users.get(i).getUserName());
            }
        }

        return all_followers;
    }
    
    /**
     * Gets a list of all users (followers) following a specific user within the follow graph.
     * @param username The username of the user whose followers are to be retrieved.
     * @return  An `ArrayList` of strings, where each string represents the username of a follower of the specified user.
     * @throws IllegalArgumentException If the specified `username` does not exist within the follow graph.
     * @custom.precondition The `username` exists in the follow graph.
     * @custom.postcondition Returns a list of all followers of the specified user. Throws `IllegalArgumentException` if the user doesn't exist.
     */
    public ArrayList<String> getAllFollowers(String username){
        ArrayList<String> all_following = new ArrayList<>();
        int a;
        if (!checkIfUserExists(username)) {
            throw new IllegalArgumentException("Username doesn't exist.");
        } else {
            a = findUser(username).getIndexPos();
        }
        
        for (int i = 0; i < connections.length; i++){
            if ( connections[i][a] == true){
                all_following.add(users.get(i).getUserName());
            }
        }

        return all_following;
    }

    /**
     * Loads all users from a specified file into the FollowGraph.
     * @param filename The name of the file containing user data. Each line in the file should represent a single username.
     * @custom.precondition The file denoted by 'filename' exists and is readable. Each line in the file should contain a single valid username.
     * @custom.postcondition  Users listed in the input file are added to the FollowGraph if they don't already exist.  Any I/O errors are reported to the console.
     */
    public void loadAllUsers(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String name = line.strip();
                if (this.checkIfUserExists(name)){
                    continue;
                }
                this.addUser(name);
                System.out.println(name + " has been added");
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Error reading the file: " + e.getMessage());
        }
    }

    /**
     * Loads all connections from the specified file into the FollowGraph.
     * @param filename The name of the file from which to load the connections.
     * @custom.precondition  The file specified by `filename` must exist, be readable, and follow the specified CSV format.  The users involved in the connections should already exist in the FollowGraph.
     * @custom.postcondition Connections are added to the FollowGraph according to the file contents. Any errors related to file access, format, or non-existent users are printed to the console.
     */
    public void loadAllConnections(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] connection = Arrays.stream(line.split(",")).map(String::strip).toArray(String[]::new);
                
                if (connection.length != 2) continue;
                
                if (!checkIfUserExists(connection[0])) continue;
    
                if (!checkIfUserExists(connection[1])) continue;
    
                this.addConnection(connection[0], connection[1]);
                System.out.println(connection[0] + ", " + connection[1] + " added");
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
        } catch (IOException e) {
            System.out.println("Error reading the file.");
        }
    }
}
