/**
 * Comparator to compare each user's follower count.
 */
import java.util.Comparator;

public class FollowersComparator implements Comparator<User>{
    private FollowGraph followGraph;

    public FollowersComparator(FollowGraph followGraph) {
        this.followGraph = followGraph;
    
    }
    @Override
    public int compare(User o1, User o2) {
        int o1_followers = followGraph.getAllFollowers(o1.getUserName()).size();
        int o2_followers = followGraph.getAllFollowers(o2.getUserName()).size();
        
        if (o1_followers == o2_followers) return 0;
        else if (o1_followers > o2_followers) return -1;
        else return 1;
    }
}
