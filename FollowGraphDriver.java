/**
 * Menu driven java class to access and execute functions on the FollowGraph.
 */
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class FollowGraphDriver {
    public static void main(String[] args) throws IOException {   
        FollowGraph followGraph = new FollowGraph();     
        Scanner scanner = new Scanner(System.in);
        FileInputStream file = null;
        try {
            file = new FileInputStream("follow_graph.obj");
            ObjectInputStream inStream = new ObjectInputStream(file);
            followGraph = (FollowGraph) inStream.readObject();
            User.setUserCount(inStream.readInt());
            inStream.close();
            if (file != null) {
                file.close();
            }
        } catch (FileNotFoundException e) {
            System.out.println("follow_graph.obj is not found. New FollowGraph object will be created.");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        boolean isContinue = true;
        System.out.println("************ Menu ************");
        System.out.println("(U) Add User\n(C) Add Connection\n(AU) Load all Users\n(AC) Load all Connections\n(P) Print all Users\n(L) Print all Loops\n(RU) Remove User\n(RC) Remove Connection\n(SP) Find Shortest Path\n(AP) Find All Paths\n(Q) Quit");        

        while (isContinue){    
            System.out.print("Enter a selection: ");
            String a = scanner.nextLine().toUpperCase();

            switch (a) {
                case "Q":
                    FileOutputStream file_ = new FileOutputStream("follow_graph.obj");
                    ObjectOutputStream outStream = new ObjectOutputStream(file_);
                    outStream.writeObject(followGraph);
                    outStream.writeInt(followGraph.getUserCount());
                    outStream.close(); 
                    System.out.println("Program terminating...");
                    isContinue = false;
                    break;
                case "AU":
                    System.out.print("Enter the file name: ");
                    String au_name = scanner.nextLine();
                    followGraph.loadAllUsers(au_name);
                    break;
                case "AC":
                    System.out.print("Enter the file name: ");
                    String ac_name = scanner.nextLine();
                    followGraph.loadAllConnections(ac_name);
                    break;
                case "P":
                    System.out.println("(SA) Sort Users by Name\n(SB) Sort Users by Number of Followers\n(SC) Sort Users by Number of Following\n(Q) Quit");
                    boolean isContinue2 = true;
                    while (isContinue2){
                        System.out.print("Enter a selection: ");
                        String p_opt = scanner.nextLine().toUpperCase();
                        switch (p_opt) {
                            case "SA":
                                followGraph.printAllUsers(new NameComparator());
                                break;
                            case "SB":
                                followGraph.printAllUsers(new FollowersComparator(followGraph), new NameComparator());
                                break;
                            case "SC":
                                followGraph.printAllUsers(new FollowingComparator(followGraph), new NameComparator());
                                break;
                            case "Q":
                                isContinue2 = false;
                                break;
                            default:
                                System.out.println("Wrong option. Try again.");
                                break;
                        }
                    }
                    System.out.println("************ Menu ************");
                    System.out.println("(U) Add User\n(C) Add Connection\n(AU) Load all Users\n(AC) Load all Connections\n(P) Print all Users\n(L) Print all Loops\n(RU) Remove User\n(RC) Remove Connection\n(SP) Find Shortest Path\n(AP) Find All Paths\n(Q) Quit");        
                    break;
                case "L":
                    ArrayList<String> loops = followGraph.findAllLoops();
                    if (loops.isEmpty()){
                        System.out.println("There are no loops.");
                        break;
                    }
                    if (loops.size() == 1){
                        System.out.println("\nThere is 1 loop: ");
                    } else if (loops.size() > 1){
                        System.out.println("There are a total of " + loops.size() + " loops:");
                    }
                    
                    for (String s: loops){
                        System.out.println(s);
                    }
                    break;
                case "RC":
                    boolean __isContinue = true;
                    while (__isContinue){
                        System.out.print("\nPlease enter the source of the connection to remove: ");
                        String src_remove = scanner.nextLine();

                        if (src_remove.equals("")){
                            System.out.println("You cannot leave this field empty.");
                            System.out.println("There is no user with this name, Please choose a valid user!");
                            continue;
                        } 
                        if (!followGraph.checkIfUserExists(src_remove)){
                            System.out.println("There is no user with this name, Please choose a valid user!");
                            continue;
                        }

                        System.out.print("\nPlease enter the dest of the connection to remove: ");
                        String dest_remove = scanner.nextLine();

                        if (dest_remove.equals("")){
                            System.out.println("You cannot leave this field empty.");
                            System.out.println("There is no user with this name, Please choose a valid user!");
                            continue;
                        } 
                        if (!followGraph.checkIfUserExists(dest_remove)){
                            System.out.println("There is no user with this name, Please choose a valid user!");
                            continue;
                        }
                        followGraph.removeConnection(src_remove, dest_remove);
                        __isContinue = false;
                    }
                    System.out.println("\n************ Menu ************");
                    System.out.println("(U) Add User\n(C) Add Connection\n(AU) Load all Users\n(AC) Load all Connections\n(P) Print all Users\n(L) Print all Loops \n(RU) Remove User\n(RC) Remove Connection\n(SP) Find Shortest Path\n(AP) Find All Paths\n(Q) Quit");        
                    break;
                case "C":
                    System.out.print("\nPlease enter the source of the connection to add: ");
                    String src_add = scanner.nextLine();
                    System.out.print("\nPlease enter the dest of the connection to add: ");
                    String dest_add = scanner.nextLine();
                    followGraph.addConnection(src_add, dest_add);
                    break;
                case "U":
                    System.out.print("\nPlease enter the name of the user: ");
                    String src_name = scanner.nextLine();
                    followGraph.addUser(src_name);
                    break;
                case "RU":
                    System.out.print("\nPlease enter the user to remove: ");
                    String src_name_rem = scanner.nextLine();
                    followGraph.removeUser(src_name_rem);
                    break;
                case "SP":
                    boolean ___isContinue = true;
                    while (___isContinue){
                        System.out.print("\nPlease enter the desired source: ");
                        String src_add_ = scanner.nextLine();

                        if (src_add_.equals("")){
                            System.out.println("You cannot leave this field empty.");
                            System.out.println("There is no user with this name, Please choose a valid user!");
                            continue;
                        } 
                        if (!followGraph.checkIfUserExists(src_add_)){
                            System.out.println("There is no user with this name, Please choose a valid user!");
                            continue;
                        }

                        System.out.print("\nPlease enter the desired destination: ");
                        String dest_add_ = scanner.nextLine();

                        if (dest_add_.equals("")){
                            System.out.println("You cannot leave this field empty.");
                            System.out.println("There is no user with this name, Please choose a valid user!");
                            continue;
                        } 
                        if (!followGraph.checkIfUserExists(dest_add_)){
                            System.out.println("There is no user with this name, Please choose a valid user!");
                            continue;
                        }
                        String[] temmpp = followGraph.shortestPath(src_add_, dest_add_).split("###");
                        System.out.println("The shortest path is: " + temmpp[0]);
                        System.out.println("The number of users in this path is: " + temmpp[1]);
                        ___isContinue = false;
                    }
                    break;
                case "AP":    
                    System.out.print("\nPlease enter the desired source: ");
                    String src_add_a = scanner.nextLine();
                    System.out.print("\nPlease enter the desired destination: ");
                    String dest_add_a = scanner.nextLine();
                    ArrayList<String> allpaths = followGraph.allPaths(src_add_a, dest_add_a);
                    if (allpaths.isEmpty()){
                        System.out.println("There are no paths.");
                        break;
                    }
                    System.out.println("There are a total of " + allpaths.size() + " paths:");
                    for (String s: allpaths){
                        System.out.println(s);
                    }
                    break;
                default:
                    System.out.println("Wrong option. Try again.");
                    break;
            }
        
        }
        scanner.close();
    }
}
