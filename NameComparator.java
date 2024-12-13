/**
 * Comparator to compare usernames.
 */
import java.util.Comparator;

public class NameComparator implements Comparator<User>{

    @Override
    public int compare(User o1, User o2) {
        return o1.getUserName().compareTo(o2.getUserName());
    }
    
}
