/*****************************************
* AUTHOR: Andre de Moeller               *
* DATE: 03.10.19                         *
* PURPOSE: user interface for socialsim  *
* LAST MODIFIED: 28.10.19                *
*****************************************/
import java.util.*;
import java.io.*;

public class userinterface {

    /****************************************
    * NAME: userinrerfacennnn               *
    * IMPORT: none                          *
    * EXPORT: none                          *
    * PURPOSE: interface for socialsim      *
    ****************************************/
    public static void menu() {
        //menu for user interaction
        DSAGraph graph = new DSAGraph();
        DSALinkedList list;

        Scanner sc = new Scanner(System.in);
        String networkName, saveName;
        int choice;
        double followProb = 0.0, likeProb = 0.0;

        System.out.println("\n");
        System.out.println("-------------------------------------------------");
        System.out.println("              WELCOME TO SOCIAL SIM              ");
        System.out.println("-------------------------------------------------");
        do {
            System.out.println("MENU.............................................");
            System.out.println("0) Load network\n1) Set probabilities\n2) Node operations\n3) Edge operations");
            System.out.println("4) New post\n5) Display network\n6) Display statistics\n7) Update (run a timestep)\n8) Save network\n9) Exit Simulator");
            choice = validateInteger(0, 9);

            switch (choice) {
                case 0:
                    //loads in network
                    System.out.println(".................................................");
                    networkName = getFileName("Enter the name of the network file: ");
                    graph = fileio.processNetwork(networkName, graph);

                    break;
                case 1:
                    //sets probabilities
                    System.out.println(".................................................");
                    System.out.println("Probability of liking a post 0.0-1.0:");
                    System.out.println(".................................................");
                    likeProb = validateDouble(0.0, 1.0);
                    System.out.println("-------------------------------------------------");
                    System.out.println("Like probablity set: " + likeProb * 100 + "%");

                    System.out.println(".................................................");
                    System.out.println("Probability of following the poster 0.0-1.0:");
                    System.out.println(".................................................");
                    followProb = validateDouble(0.0, 1.0);
                    System.out.println("-------------------------------------------------");
                    System.out.println("Follow probablity set: " + followProb * 100 + "%");

                    break;
                case 2:
                    //node operations
                    nodeOps(graph);

                    break;
                case 3:
                    //edge operations
                    list = graph.getVertices();
                    if (list.isEmpty()) {
                        System.out.println(".................................................");
                        System.out.println("No people have been added to the network!");
                    } else {
                        edgeOps(graph);
                    }

                    break;
                case 4:
                    //create posts
                    newPost(graph);

                    break;
                case 5:
                    //display network
                    list = graph.getVertices();
                    if (list.isEmpty()) {
                        System.out.println(".................................................");
                        System.out.println("No people have been added to the network!");
                    } else {
                        System.out.println("CURRENT STATE....................................");
                        graph.displayAsList();
                        System.out.println("-------------------------------------------------");
                    }
                    break;
                case 6:
                    //display statistics
                    list = graph.getVertices();
                    if (list.isEmpty()) {
                        System.out.println(".................................................");
                        System.out.println("No people have been added to the network!");
                    } else {
                        displayStatistics(graph);
                    }

                    break;
                case 7:
                    //timestep
                    list = graph.getVertices();
                    if (list.isEmpty()) {
                        System.out.println(".................................................");
                        System.out.println("No people have been added to the network!");
                    }
                    if (likeProb == 0.0) {
                        System.out.println(".................................................");
                        System.out.println("No probability has been set!");
                    } else {
                        update(graph, likeProb, followProb);
                    }

                    break;
                case 8:
                    //save network
                    System.out.println(".................................................");
                    saveName = getFileName("Enter the name of save file: ");
                    System.out.println(".................................................");
                    fileio.save(saveName, graph);

                    System.out.println("Network has been saved to: " + saveName);

                    break;
                case 9:
                    System.out.println(".................................................");
                    System.out.println("Goodbye!");
                    System.out.println(".................................................");

                    break;

                default:
                    System.out.println(".................................................");
                    System.out.println("Invalid selection!");
            }
        } while (choice != 9);
    }

    /****************************************
    * NAME: getFileName                     *
    * IMPORT: prompt                        *
    * EXPORT: filename                      *
    * PURPOSE: gets network filename        *
    ****************************************/
    public static String getFileName(String prompt) {
        Scanner sc = new Scanner(System.in);
        String filename;
        System.out.println(prompt);
        System.out.println(".................................................");
        filename = sc.nextLine();

        return filename;
    }

    /****************************************
    * NAME: nodeOps                         *
    * IMPORT: none                          *
    * EXPORT: none                          *
    * PURPOSE: sub-menu for node ops        *
    ****************************************/
    public static void nodeOps(DSAGraph graph) {
        Scanner sc = new Scanner(System.in);
        DSALinkedList list;
        int nodeChoice;
        String name;
        boolean exists;

        System.out.println("NODE OPERATIONS..................................");
        System.out.println("1) Find node\n2) Add node\n3) Remove node");
        nodeChoice = validateInteger(1, 3);

        switch (nodeChoice) {
            case 1:
                //find
                list = graph.getVertices();
                if (!list.isEmpty()) {
                    System.out.println(".................................................");
                    name = fetchName(graph, "Enter the name:", list);
                    System.out.println(".................................................");
                    if (name != null) {
                        exists = graph.hasVertex(name);
                        if (exists) {
                            System.out.println(name + " exists in the network");
                        } else {
                            System.out.println(name + " doesn't exist in the network");
                        }
                    }
                } else {
                    System.out.println(".................................................");
                    System.out.println("No people have been added to the network!");
                }

                break;
            case 2:
                //insert

                System.out.println(".................................................");
                System.out.println("Enter the name:");
                System.out.println(".................................................");
                name = sc.next();
                if (graph.hasVertex(name) == true) {
                    System.out.println(".................................................");
                    System.out.println(name + " has already been added to the network!");
                } else {
                    graph.addVertex(name);
                    System.out.println(".................................................");
                    System.out.println("Success! " + name + " has been added to the network.");
                }
                break;
            case 3:
                //remove
                list = graph.getVertices();
                if (!list.isEmpty()) {
                    System.out.println(".................................................");
                    name = fetchName(graph, "Enter the name:", list);
                    System.out.println(".................................................");
                    graph.removeVertex(name);
                    if (name != null) {
                        System.out.println("Success! " + name + " has been removed from the network.");
                    }
                } else {
                    System.out.println(".................................................");
                    System.out.println("No people have been added to the network!");
                }
                break;
        }
    }

    /****************************************
    * NAME: edgeOps                         *
    * IMPORT: graph                         *
    * EXPORT: none                          *
    * PURPOSE: sub-menu for edge ops        *
    ****************************************/
    public static void edgeOps(DSAGraph graph) {
        Scanner sc = new Scanner(System.in);
        DSALinkedList list, following;
        boolean error = false;
        int edgeChoice, count;
        String nameOne, nameTwo, post, temp;

        System.out.println("EDGE OPERATIONS..................................");
        System.out.println("1) Follow\n2) Unfollow");
        edgeChoice = validateInteger(1, 2);

        switch (edgeChoice) {
            case 1:
                //follow
                list = graph.getVertices();
                System.out.println(".................................................");
                nameOne = fetchName(graph, "Who is following someone? ", list);
                System.out.println(".................................................");
                if (nameOne != null) {

                    nameTwo = fetchName(graph, "Follow:", list);

                    if (nameOne.equals(nameTwo)) {
                        System.out.println(".................................................");
                        System.out.println("A person can't follow themselves!");
                    } else if (graph.isFollowing(nameOne, nameTwo)) {
                        System.out.println(".................................................");
                        System.out.println(nameOne + " already follows " + nameTwo + "!");
                    } else {
                        graph.addEdge(nameTwo, nameOne);
                        System.out.println(".................................................");
                        System.out.println("Success! " + nameOne + " now follows " + nameTwo + "!");
                    }
                }

                break;
            case 2:
                //unfollow
                list = graph.getVertices();
                System.out.println(".................................................");
                nameOne = fetchName(graph, "Enter the name: ", list);
                System.out.println(".................................................");
                if (nameOne != null) {
                    list = graph.getFollowing(nameOne);
                    if (!list.isEmpty()) {
                        nameTwo = fetchName(graph, "Unfollow:", list);
                        if (nameOne.equals(nameTwo)) {
                            System.out.println(".................................................");
                            System.out.println("A person can't unfollow themselves!");
                        } else if (graph.isFollower(nameOne, nameTwo)) {
                            graph.removeEdge(nameOne, nameTwo);
                            System.out.println(".................................................");
                            System.out.println(nameOne + " has unfollowed " + nameTwo + "!");
                        } else {
                            System.out.println(".................................................");
                            System.out.println(nameOne + " doesn't follow " + nameTwo + "!");
                        }
                    } else {
                        System.out.println(nameOne + " doesn't follow anyone !");
                    }
                }
                break;
        }
    }

    /****************************************
    * NAME: newPost                         *
    * IMPORT: graph                         *
    * EXPORT: none                          *
    * PURPOSE: create a new post            *
    ****************************************/
    public static void newPost(DSAGraph graph) {
        DSALinkedList list;
        Scanner sc = new Scanner(System.in);
        String post, name;

        list = graph.getVertices();
        name = fetchName(graph, "Who's posting?", list);
        if (name != null) {

            System.out.println(".................................................");
            System.out.println("Post:");
            System.out.println(".................................................");
            post = sc.nextLine();

            graph.makePost(name, post, 1);

            System.out.println(".................................................");
            System.out.println(name + " has posted.");
        }
    }

    /****************************************
    * NAME: displayStatistics               *
    * IMPORT: none                          *
    * EXPORT: none                          *
    * PURPOSE: interface for stats          *
    ****************************************/
    public static void displayStatistics(DSAGraph graph) {
        Scanner sc = new Scanner(System.in);
        DSALinkedList list = new DSALinkedList();
        DSAQueue queue = new DSAQueue();
        int choice;
        String name;
        //long startTime, endTime, timeTaken;

        System.out.println("STATISTICS.......................................");
        System.out.println("1) Trending posts\n2) Most followed\n3) Person's record");
        choice = sc.nextInt();

        switch (choice) {
            case 1:
                simulation.likeSort(graph);

                break;
            case 2:

                //startTime = System.nanoTime();

                simulation.followSort(graph);
                //endTime = System.nanoTime();
                //timeTaken = (endTime - startTime);
                //System.out.println(timeTaken);

                break;
            case 3:
                list = graph.getVertices();
                name = fetchName(graph, "Enter the name:", list);

                System.out.println("-------------------------------------------------");
                System.out.println("Following:");
                System.out.println(".................................................");
                list = graph.getFollowing(name);
                printList(list);

                System.out.println("-------------------------------------------------");
                System.out.println("Followers:");
                System.out.println(".................................................");
                list = graph.getFollowers(name);
                printList(list);

                System.out.println("-------------------------------------------------");
                System.out.println("Posts:");
                System.out.println(".................................................");
                printPosts(graph, name);
        }
    }

    /****************************************
    * NAME: printPosts                      *
    * IMPORT: none                          *
    * EXPORT: none                          *
    * PURPOSE: interface for stats          *
    ****************************************/
    public static void printPosts(DSAGraph graph, String name) {
        DSALinkedList posts = graph.getPosts(name);
        int count = 1;
        DSAPost temp;

        Iterator iter = posts.iterator();
        if (iter.hasNext() == false) {
            System.out.println("N/A");
        } else {
            while (iter.hasNext()) {
                temp = (DSAPost) iter.next();

                System.out.println(temp);
                count++;
            }
        }
    }

    /****************************************
    * NAME: printList                       *
    * IMPORT: list                          *
    * EXPORT: none                          *
    * PURPOSE: prints a linked list         *
    ****************************************/
    public static void printList(DSALinkedList list) {
        Iterator iter = list.iterator();
        if (!iter.hasNext()) {
            System.out.println("N/A");
        }
        while (iter.hasNext()) {
            System.out.println(iter.next());
        }
    }

     /*****************************************************
    * NAME: validateDouble                               *
    * IMPORT: none                                       *  
    * EXPORT: none                                       *
    * PURPOSE: validates double input                    *
    * REFERENCE: de Moeller, A. (2019). OOPD Assignment  *
    *****************************************************/
    public static double validateDouble(double min, double max) {
        Scanner sc = new Scanner(System.in);
        double num = 0.0;
        boolean error;

        do {
            try {
                num = sc.nextDouble();
                error = false;
                if ((num < min) || (num > max)) {
                    System.out.println(".................................................");
                    System.out.println("Invalid input, please re-enter: ");
                    System.out.println(".................................................");

                    error = true;
                    sc.nextLine();
                }
            } catch (InputMismatchException ex) {

                System.out.println(".................................................");
                System.out.println("Invalid input, please re-enter: ");
                System.out.println(".................................................");

                error = true;
                sc.nextLine();
            }
        } while (error == true);
        return num;
    }

     /***************************************************** 
     * NAME: validateInteger                              *
     * IMPORT: none                                       *
     * EXPORT: none                                       *
     * PURPOSE: validates integer input                   *
     *****************************************************/
     public static int validateInteger(int min, int max) {
        Scanner sc = new Scanner(System.in);
        int num = 0;
        boolean error;

        do {
            try {
                num = sc.nextInt();
                error = false;
                if ((num < min) || (num > max)) {
                    System.out.println(".................................................");
                    System.out.println("Invalid input, please re-enter: ");
                    System.out.println(".................................................");
                    error = true;
                    sc.nextLine();
                }
            } catch (InputMismatchException ex) {
                System.out.println(".................................................");
                System.out.println("Invalid input, please re-enter: ");
                System.out.println(".................................................");
                error = true;
                sc.nextLine();
            }
        } while (error == true);
        return num;
    }

    /****************************************
    * NAME: fetchName                       *
    * IMPORT: graph, prompt                 *
    * EXPORT: name                          *
    * PURPOSE: gets a name from user        *
    ****************************************/
    public static String fetchName(DSAGraph graph, String prompt, DSALinkedList list) {
        Scanner sc = new Scanner(System.in);
        String name = null;
        boolean error = true;
        DSALinkedList temp;

        //checks that list isn't empty
        temp = graph.getVertices();
        if (temp.isEmpty()) {

            System.out.println(".................................................");
            System.out.println("No people have been added to the network!");
        } else {
            do {
                System.out.println(prompt);
                System.out.println("-------------------------------------------------");
                //displays all the vertices in the list
                printList(list);
                System.out.println("-------------------------------------------------");
                name = sc.nextLine();

                //checks if person already exists
                if (graph.hasVertex(name)) {
                    error = false;
                } else {

                    System.out.println(".................................................");
                    System.out.println("That person doesn't exist!");
                    System.out.println(".................................................");
                    error = true;
                }
            } while (error == true);
        }
        return name;
    }

    /****************************************
    * NAME: update                          *
    * IMPORT: graph, likeProb, followProb   *
    * EXPORT: none                          *
    * PURPOSE: calls the timestep           *
    ****************************************/
    public static void update(DSAGraph graph, double likeProb, double followProb) {
        simulation.timestep(graph, likeProb, followProb);
    }
}