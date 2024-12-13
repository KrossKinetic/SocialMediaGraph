/**
 * Represents a user in FollowGraph.
 */
import java.io.Serializable;

public class User implements Serializable{
    private String userName = "";
    private int indexPos = 0;
    private static int userCount = 0;

    /**
     * Constructs a new User object.
     * @param name The username for this user.
     * @custom.precondition `name` is a non-null, non-empty String.
     * @custom.postcondition A new User object is created with the given username, and the user count is incremented. The `indexPos` is set to the current `userCount` before the increment.
     */
    public User(String name){
        userName = name;
        this.indexPos = userCount;
        userCount++;
    }

    /**
     * Gets the total number of users created.
     * @return The total user count.
     * @custom.postcondition The current value of the static variable 'userCount' is returned. The object's state remains unchanged.
     */
    public static int getUserCount() {
        return userCount;
    }

    /**
     * Returns the username of this user.
     * @return The username.
     * @custom.postcondition The username is returned.  No changes to the object's state are made.
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Gets the index position of this user in the FollowGraph's adjacency matrix.
     * @return The index position.
     * @custom.postcondition The current index position of the user is returned. The state of the User object remains unchanged.
     */
    public int getIndexPos() {
        return indexPos;
    }

    /**
     * Sets the index position of the user.
     * @param indexPos The new index position.
     * @custom.precondition `indexPos` represents a valid index within the adjacency matrix of the associated FollowGraph.
     * @custom.postcondition This User's index position is updated to the supplied value.
     */
    public void setIndexPos(int indexPos) {
        this.indexPos = indexPos;
    }

    /**
     * Sets the static user count to provided value.
     * @param new_count The new user count.
     * @custom.precondition new_count should be non-negative
     * @custom.postcondition The static user count is updated to the new value provided.
     */
    public static void setUserCount(int new_count){
        userCount = new_count;
    }

    /**
     * Returns a string representation of the user.
     * @return A string containing the username, index position, and total user count.
     * @custom.postcondition A string representation of the User object is returned, containing information about the username, index position and user count. The object's state remains unchanged.
     */
    @Override
    public String toString() {
        return "Username: " + userName + ", index: " + indexPos + ", user count: " + userCount;
    }
}
