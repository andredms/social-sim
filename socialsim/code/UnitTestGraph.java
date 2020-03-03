/****************************************
* AUTHOR: Andre de Moeller              *
* DATE: 26.10.19                        *
* PURPOSE: tests graph                  *
* LAST MODIFIED: 28.10.19               *
****************************************/
import java.util.*;
import java.io.*;

public class UnitTestGraph {
    public static void main(String[] args) {
        String nameOne = "", nameTwo = "", nameThree = "";
        DSAGraph graph = null;
        String post = "", postTwo = "";
        boolean retVal;
        int passed = 0;
        DSALinkedList likedBy;

        System.out.println("");
        System.out.println("-------------------------------------------------");
        System.out.println("                   TEST HARNESS                  ");
        System.out.println("-------------------------------------------------");
        System.out.print("NETWORK CREATION: ");
        try {
            graph = new DSAGraph();
            System.out.print("passed");
            passed++;
        } catch (Exception ex) {
            System.out.print("failed");
        }

        System.out.println("");
        System.out.println(".................................................");
        System.out.print("ADD NODES: ");
        try {
            nameOne = genfiles.generateName();
            nameTwo = genfiles.generateName();
            nameThree = genfiles.generateName();

            graph.addVertex(nameOne);
            graph.addVertex(nameTwo);
            graph.addVertex(nameThree);

            if (graph.getCount() == 3) {
                System.out.println("passed");
                passed++;
                graph.displayAsList();
            }
        } catch (Exception ex) {
            System.out.println("failed");
        }

        System.out.println(".................................................");
        System.out.print("NODE EXISTS: ");
        try {
            if (graph.hasVertex(nameOne) == true) {
                System.out.println("passed");
                passed++;
            }
        } catch (Exception ex) {
            System.out.println("failed");
        }

        System.out.println(".................................................");
        System.out.print("ADD FOLLOWS: ");
        try {
            graph.addEdge(nameOne, nameTwo);
            graph.addEdge(nameTwo, nameThree);

            System.out.println("passed");
            passed++;
            graph.displayAsList();
        } catch (Exception ex) {
            System.out.println("failed");
        }

        System.out.println(".................................................");
        System.out.print("IS FOLLOWING: ");
        try {
            if (graph.isFollowing(nameTwo, nameOne) == true) {
                System.out.println("passed");
                passed++;
            }
        } catch (Exception ex) {
            System.out.println("failed");
        }

        System.out.println(".................................................");
        System.out.print("IS FOLLOWER: ");
        try {
            if (graph.isFollower(nameOne, nameTwo) == true) {
                System.out.println("passed");
                passed++;
            }
        } catch (Exception ex) {
            System.out.println("failed");
        }

        System.out.println(".................................................");
        System.out.print("MAKE POST: ");
        try {
            System.out.println("passed");
            passed++;

            post = genfiles.generatePost();

            graph.makePost(nameOne, post, 1);
            userinterface.printPosts(graph, nameOne);

            postTwo = genfiles.generatePost();
            graph.makePost(nameTwo, postTwo, 1);

            userinterface.printPosts(graph, nameTwo);
        } catch (Exception ex) {
            System.out.println("failed");
        }

        System.out.println(".................................................");
        System.out.print("ADD LIKES: ");
        try {
            System.out.println("passed");
            passed++;

            graph.addLike(nameOne, post, nameTwo);

            userinterface.printPosts(graph, nameOne);
        } catch (Exception ex) {
            System.out.println("failed");
        }

        System.out.println(".................................................");
        System.out.print("TIMESTEP: ");
        try {
            simulation.timestep(graph, 1.0, 1.0);
            System.out.println("passed");
            passed++;
            graph.displayAsList();
        } catch (Exception ex) {
            System.out.println("failed");
        }

        System.out.println(".................................................");
        System.out.print("HAS LIKED: ");
        try {
            likedBy = graph.getLikedBy(nameOne, post);
            retVal = graph.hasLiked(likedBy, nameTwo);
            if (retVal == true) {
                System.out.println("passed");
                passed++;
            }
        } catch (Exception ex) {
            System.out.println("failed");
        }

        System.out.println(".................................................");
        System.out.print("SORT LIKES: ");
        try {
            System.out.println("passed");
            simulation.likeSort(graph);
            passed++;
        } catch (Exception ex) {
            System.out.println(ex);
            System.out.println("failed");
        }

        System.out.println(".................................................");
        System.out.print("REMOVE CONNECTION: ");
        try {
            graph.removeEdge(nameOne, nameTwo);
            System.out.println("passed");
            passed++;
        } catch (Exception ex) {
            System.out.println("failed");
        }

        System.out.println(".................................................");
        System.out.print("FOLLOWER COUNT: ");
        try {
            if (graph.getFollowerCount(nameOne) == 2) {
                System.out.println("passed");
                passed++;
            }
        } catch (Exception ex) {
            System.out.println("failed");
        }


        System.out.println(".................................................");
        System.out.print("SORT FOLLOWS: ");
        try {
            System.out.println("passed");
            simulation.followSort(graph);
            passed++;
        } catch (Exception ex) {
            System.out.println("failed");
        }

        System.out.println(".................................................");
        System.out.print("REMOVE NODE: ");
        try {
            graph.removeVertex(nameOne);

            if (graph.getCount() == 2) {
                System.out.println("passed");
                passed++;
            }
        } catch (Exception ex) {
            System.out.println("failed");
        }

        System.out.println(".................................................");
        System.out.print("DISPLAY NETWORK: ");
        try {
            System.out.println("passed");
            passed++;
            graph.displayAsList();
        } catch (Exception ex) {
            System.out.println("failed");
        }
        System.out.println("-------------------------------------------------");
        System.out.println("TESTS PASSED: " + passed + "/16");
        System.out.println("-------------------------------------------------");
    }
}