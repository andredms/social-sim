/****************************************
* AUTHOR: Andre de Moeller              *
* DATE: 13.10.19                        *
* PURPOSE: kick starts social sim       *
* LAST MODIFIED: 28.10.19               *
****************************************/
import java.util.*;
import java.io.*;

public class SocialSim {
    public static void main(String[] args) {
        String netfile, eventfile;
        double probLike, probFollow;
        DSAGraph graph = new DSAGraph();

        if (args.length == 0) {
            System.out.println("\n");
            System.out.println("-------------------------------------------------");
            System.out.println("                USAGE INFORMATION                ");
            System.out.println("-------------------------------------------------");
            System.out.println("INTERACTIVE TESTING..............................");
            System.out.println("Run the program with -i for interactive testing");
            System.out.println("i.e. java SocialSim -i");
            System.out.println("\n");
            System.out.println("SIMULATION MODE..................................");
            System.out.println("Run the program with -s for simulation mode");
            System.out.println("i.e. java SocialSim -s nfile efile plike pfollow");
            System.out.println("-------------------------------------------------");
        } else if (args[0].equals("-i")) {
            userinterface.menu();
        } else if (args.length == 5) {
            try {
                netfile = args[1];
                eventfile = args[2];

                probLike = Double.parseDouble(args[3]);
                probFollow = Double.parseDouble(args[4]);
                if (probLike < 0.0 || probFollow < 0.0 || probLike > 1.0 || probFollow > 1.0) {
                    System.out.println("Error, probabilities must be between 0.0-1.0!");
                } else {
                    System.out.println("\n");
                    System.out.println("ERRORS...........................................");
                    fileio.processNetwork(netfile, graph);
                    fileio.processEvent(eventfile, graph);

                    simulation.menu(graph, probLike, probFollow);
                }
            } catch (NumberFormatException ex) {
                System.out.println("Error, invalid probabilities!");
            }
        } else {
            System.out.println("Error, invalid arguments!");
        }
    }
}