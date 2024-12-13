/**
 * Comparator to compare each user's following count.
 */
import java.util.Comparator;

public class FollowingComparator implements Comparator<User>{
    private FollowGraph followGraph;

    public FollowingComparator(FollowGraph followGraph) {
        this.followGraph = followGraph;
    
    }
    @Override
    public int compare(User o1, User o2) {
        int o1_following = followGraph.getAllFollowing(o1.getUserName()).size();
        int o2_following = followGraph.getAllFollowing(o2.getUserName()).size();
        
        if (o1_following == o2_following) return 0;
        else if (o1_following > o2_following) return -1;
        else return 1;
    }
}
