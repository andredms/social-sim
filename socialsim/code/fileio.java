/****************************************
* AUTHOR: Andre de Moeller              *
* DATE: 19.08.19                        *
* PURPOSE: file io class for DSA        *
* LAST MODIFIED: 27.10.19
****************************************/
import java.util.*;
import java.io.*;

public class fileio
{
    /*********************************************
    * NAME: processNetwork                       *  
	* IMPORT: filename, graph                    *
	* EXPORT: graph                              *
    * REFERENCE: de Moeller, A. (2019). OOPD P04 *
	* PURPOSE: reads in a network                *
	*********************************************/
	public static DSAGraph processNetwork(String filename, DSAGraph graph)
	{
		FileInputStream fileStrm = null;
		InputStreamReader rdr;
		BufferedReader bufRdr;

		String line;

		try
		{
		    fileStrm = new FileInputStream(filename);
			rdr = new InputStreamReader(fileStrm);
			bufRdr = new BufferedReader(rdr);

			line = bufRdr.readLine();
			while(line != null)
			{
			    processNetworkLine(line, graph);
				line = bufRdr.readLine();
			}
			System.out.println(".................................................");
			System.out.println("Network loaded from: " + filename);
			fileStrm.close();
		}
		catch(IOException e)
		{
			System.out.println(".................................................");
			System.out.println("That file doesn't exist!" + "(" + filename + ")");
		    try
			{
			    if(fileStrm != null)
				{
				    fileStrm.close();
				}
			}
			catch(IOException ex)
			{
			}
		}
		return graph;
	}

    /*********************************************
    * NAME: processNetworkLine                   *
	* IMPORT: line, graph                        *
	* EXPORT: none                               *
    * REFERENCE: de Moeller, A. (2019). OOPD P04 *
	* PURPOSE: processes single line             *
	*********************************************/
    public static void processNetworkLine(String line, DSAGraph graph)
	{
	    String labelOne, labelTwo;  
	    String[] bits = line.split(":");
		String[] whiteSpace = line.split(": ");

        if(bits.length == 2)
		{
		    if(bits[1].charAt(0) == ' ')
			{
				labelOne = whiteSpace[0];
				labelTwo = whiteSpace[1];
				if(graph.isFollowing(labelTwo, labelOne))
				{
			        System.out.println(labelOne + " is already following " + labelTwo);
				}
				else
				{
					graph.addEdge(labelOne, labelTwo);
				}
			}
			else
			{
				labelOne = bits[0];
				labelTwo = bits[1];
                if(graph.isFollowing(labelTwo, labelOne))
				{
			        System.out.println(labelOne + " is already following " + labelTwo);
				}
				else
				{
				    graph.addEdge(labelOne, labelTwo);
				}
			}
		}
		else
		{
		    labelOne = bits[0];
			if(!graph.hasVertex(labelOne))
			{
		        graph.addVertex(labelOne);
			}
			else
			{
			    System.out.println(labelOne + " has already been added!");
			}
		}
	}

    /*********************************************
    * NAME: processEvent                         *
	* IMPORT: filename, graph                    *
	* EXPORT: graph                              *
    * REFERENCE: de Moeller, A. (2019). OOPD P04 *
	* PURPOSE: processes event file              *
	*********************************************/
	public static DSAGraph processEvent(String filename, DSAGraph graph)
	{
		FileInputStream fileStrm = null;
		InputStreamReader rdr;
		BufferedReader bufRdr;

		String line;

		try
		{
		    fileStrm = new FileInputStream(filename);
			rdr = new InputStreamReader(fileStrm);
			bufRdr = new BufferedReader(rdr);

			line = bufRdr.readLine();
			while(line != null)
			{
			    processEventLine(line, graph);
				line = bufRdr.readLine();
			}
			System.out.println(".................................................");
			System.out.println("Events loaded from: " + filename);
			fileStrm.close();
		}
		catch(IOException e)
		{
			System.out.println(".................................................");
			System.out.println("That file doesn't exist!" + " (" + filename + ")");
		    try
			{
			    if(fileStrm != null)
				{
				    fileStrm.close();
				}
			}
			catch(IOException ex)
			{
			}
		}
		return graph;
	}

    /*********************************************
    * NAME: processEventLine                     *  
	* IMPORT: line, graph                        *
	* EXPORT: none                               *
    * REFERENCE: de Moeller, A. (2019). OOPD P04 *
	* PURPOSE: processes an event line           *
	*********************************************/
    public static void processEventLine(String line, DSAGraph graph)
	{
        String labelOne, labelTwo;
		String post;
		int multiplier;
	    String[] bits = line.split(":");
		if(bits[0].equals("F"))
		{
		    labelOne  = bits[1];
		    labelTwo = bits[2];

		    if(graph.isFollowing(labelTwo, labelOne))
			{
			    System.out.println(labelOne + " is already following " + labelTwo);
			}

			graph.addEdge(labelOne, labelTwo);
		}
		//post
		else if(bits[0].equals("P"))
		{
		    labelOne = bits[1];
            post = bits[2];
	    	if(graph.hasVertex(labelOne))
			{
				if(bits.length == 4)
				{
					multiplier = Integer.parseInt(bits[3]);
					graph.makePost(labelOne, post, multiplier);
				}
				else
				{
					graph.makePost(labelOne, post, 1);
				}
			}
			else
			{
			    System.out.println(labelOne + " couldn't post as they don't exist");
			}
		}
		else if(bits[0].equals("A"))
		{
		    labelOne = bits[1];
			if(!graph.hasVertex(labelOne))
			{
		        graph.addVertex(labelOne);
			}
			else
			{
			    System.out.println(labelOne + " has already been added!");
			}
		}
		else if(bits[0].equals("R"))
		{
		    labelOne = bits[1];
			if(graph.hasVertex(labelOne))
			{
			    graph.removeVertex(labelOne);
			}
			else
			{
			    System.out.println(labelOne + " couldn't be removed as they don't exist");
			}
		}
		else if(bits[0].equals("U"))
		{
		    labelOne = bits[1];
			labelTwo = bits[2];
			if(graph.isFollower(labelTwo, labelOne))
			{
			    graph.removeEdge(labelTwo, labelOne);
			}
			else
			{
			    System.out.println(labelTwo + " isn't following " + labelOne);
			}
		}
		else
		{
		    System.out.println("Invalid line format: " + bits[0]);
		}
		    
	}

    /****************************************
    * NAME: save                            *
	* IMPORT: filename, graph               *
	* EXPORT: none                          *
	* PURPOSE: saves graph to file          *
	****************************************/
	public static void save(String filename, DSAGraph graph)
	{
	    DSALinkedList names = graph.getVertices();
		DSALinkedList following;
		String output = "";
		String temp;

	    FileOutputStream fileStrm = null;
		PrintWriter pw;

		try
		{ 
		    fileStrm = new FileOutputStream(filename);
			pw = new PrintWriter(fileStrm);

		    //prints all vertices
		    Iterator i = names.iterator();
			while(i.hasNext())
			{
			    temp = String.valueOf(i.next());
				pw.println(temp);
			}
             
			//prints all follower relationships
			Iterator iterator = names.iterator();
			while(iterator.hasNext())
			{
				temp = String.valueOf(iterator.next());
				following = graph.getFollowing(temp);

				Iterator iter = following.iterator();
				while(iter.hasNext())
				{
					output = String.valueOf(iter.next());
					output = output + ":" + temp;

					pw.println(output);
				}
			}
			pw.close();
		}
		catch(IOException e)
		{
		    if(fileStrm == null)
			{
			    System.out.println("Invalid");
			}
		}
	}

    /****************************************
    * NAME: saveStep                        *
	* IMPORT: filename, graph               *
	* EXPORT: none                          *
	* PURPOSE: saves graph to file          *
	****************************************/
	public static void saveStep(String filename, int num, DSAGraph graph, long timeTaken, int newFollowers)
	{
		DSALinkedList following;
		String output = "";
		String temp;
		int followerCount;

	    FileOutputStream fileStrm = null;
		PrintWriter pw;
 
		try
		{ 
		    fileStrm = new FileOutputStream(filename, true);
			pw = new PrintWriter(fileStrm, true);

            if(num == 1)
			{
				pw.println("--------------------------------------------------------");
				pw.println("                      TIME-STEP LOGS                    ");
				pw.println("--------------------------------------------------------");
			}
			pw.println("");
			
	        DSALinkedList names = graph.getVertices();
			pw.println("TIMESTEP " + num + "..............................................");
		    Iterator i = names.iterator();
			while(i.hasNext())
			{
			    temp = String.valueOf(i.next());
				followerCount = graph.getFollowerCount(temp);
				pw.println(temp + ", " + followerCount + " followers");
			}
			pw.println("");
             
			pw.println("STATS..................................................."); 
            pw.println("Time taken: " + timeTaken + "ms");
			pw.println("Total posts: " + graph.getPostCount());
			pw.println("Follows performed: " + newFollowers);
			pw.println("Likes performed: " + graph.getLikeCount());
			pw.println("Most followed: " + simulation.mostFollowed(graph));
			pw.println("--------------------------------------------------------");

			pw.close();
		}
		catch(IOException e)
		{
		    if(fileStrm == null)
			{
			    System.out.println(e.getMessage());
			}
		}
	}
}
