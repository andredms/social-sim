/****************************************
* AUTHOR: Andre de Moeller              *
* DATE: 08.10.19                        *
* PURPOSE: social sim automation        * 
* LAST MODIFIED: 28.10.19
****************************************/
import java.util.*;
import java.io.*;
import java.text.SimpleDateFormat;

public class simulation
{
    /****************************************
    * NAME: menu                            *
	* IMPORT: graph, likeProb, followProb   *
	* EXPORT: none                          *
	* PURPOSE: menu for simulation          *
	****************************************/
    public static void menu(DSAGraph graph, double likeProb, double followProb)
	{
	    Scanner sc = new Scanner(System.in);
	    int choice, stepNum = 0, newFollowers = 0, prevFollowers = 0;
		long startTime, endTime, timeTaken = 0;
		boolean exit = false;
		String filename = generateFileName();

		System.out.print("\n");
		System.out.println("-------------------------------------------------");
		System.out.println("                 SIMULATION MODE                 ");
		System.out.println("-------------------------------------------------"); 
		System.out.println("CURRENT STATE....................................");
		graph.displayAsList();
		System.out.println("-------------------------------------------------"); 

        do
		{
		    System.out.println("MENU.............................................");
	        System.out.println("1) Update\n2) Exit");
			choice = userinterface.validateInteger(1, 2);

			switch(choice)
			{
				case 1:
                    newFollowers = getTotalFollowers(graph);
					if(newFollowers - prevFollowers <= 0)
					{
					     exit = true;
						 System.out.println(".................................................");
						 System.out.println("Max steps! Logs saved to: " + filename);
						 System.out.println(".................................................");
					}
					else
					{
						startTime = System.nanoTime();
						timestep(graph, likeProb, followProb);
						endTime = System.nanoTime();
						timeTaken = (endTime - startTime) / 1000000;
						stepNum++;

						System.out.println("-------------------------------------------------");
						graph.displayAsList();
						System.out.println("-------------------------------------------------");
						
						fileio.saveStep(filename, stepNum, graph, timeTaken, newFollowers);
						prevFollowers = newFollowers;
					}

				break;
				case 2:
					System.out.println(".................................................");
					System.out.println("Goodbye! Logs saved to: " + filename);
					System.out.println(".................................................");
				break;
				default:
					System.out.println(".................................................");
					System.out.println("Invalid selection!");
			}
		}while(choice != 2 && exit == false);

	}

    /****************************************
    * NAME: generateProb                    *
	* IMPORT: prob                          *
	* EXPORT: chance                        *
	* PURPOSE: generates probability        *
	****************************************/
	public static boolean generateProb(double prob)
	{
	    Random rand = new Random();
		boolean chance = false;
		if(prob > 1.0)
		{
		    prob = 1.0;
		}
		if(rand.nextDouble() < prob)
		{
		    chance = true;
		}
		else
		{
		    chance = false;
		}	
		return chance;
	}

    /****************************************
    * NAME: timeStep                        *
	* IMPORT: graph, likeProb, followProb   *
	* EXPORT: chance                        *
	* PURPOSE: automated post liking        *
	****************************************/
    public static void timestep(DSAGraph graph, double likeProb, double followProb)
	{
	    DSALinkedList people, followers, following, posts, likedBy;
		boolean chance, likeChance, followChance;
		String nameOne, nameTwo, temp, post, follower;

		DSAQueue followQueue = new DSAQueue();

		int multiplier;
		DSAPost postTemp;
		String bits[];

        //grab all vertices in the graph
        people = graph.getVertices();

        //iterate through all vertices
		Iterator iter = people.iterator();
		while(iter.hasNext())
		{
		    //get nameOne from list of all people
		    nameOne = String.valueOf(iter.next());

			//get all the people nameOne is following
			following = graph.getFollowing(nameOne);

			//get all the followers of nameOne
			followers = graph.getFollowers(nameOne);

            //iterate over the people nameOne is following
		    Iterator iterator = following.iterator();
			while(iterator.hasNext())
			{ 
			    //get the name of person nameOne is following
			    nameTwo = String.valueOf(iterator.next());

				//get all the posts of nameTwo
				posts = graph.getPosts(nameTwo);

                //iterate over nameTwo's posts
                Iterator i = posts.iterator();
				while(i.hasNext())
				{
				    //gets post
				    postTemp = (DSAPost)(i.next());

					//gets clickbait factor
					multiplier = postTemp.getMultiplier();
					likeProb = likeProb * multiplier;

					temp = String.valueOf(postTemp);

					//split on new line, as the number of likes appear below the post as defined in the posts toString
					bits = temp.split("\n");
            
			        post = bits[0];

					//generate probability of liking the post
		            chance = generateProb(likeProb);

                    //if the random probability function returns true
					if(chance == true)
					{
						graph.addLike(nameTwo, post, nameOne);

						//all the followers of nameOne can see they've liked nameTwo's post
						//giving nameOne's followers the ability to also like the post/follow the original poster
						Iterator it = followers.iterator();
						while(it.hasNext())
						{
							//get the follower's name
							follower = String.valueOf(it.next());

							//get probability of liking the post
							likeChance = generateProb(likeProb);

							//if the random probability function returns true
							if(likeChance == true)
							{
								//increase the like count of that certain post by 1
								graph.addLike(nameTwo, post, follower);
								followChance = generateProb(followProb);

								//if chance returns true, and the names don't match
								if((followChance == true) && (!follower.equals(nameTwo)))
								{
									/*enqueue nameTwo and follower, an edge will be added later*/
									followQueue.enqueue(nameTwo);
									followQueue.enqueue(follower);
								}
							}
						}
					}
				}
			}
		}
        
		/*makes people only follow each other at the very end - shows propagation of information better*/
	    while(!followQueue.isEmpty())
		{
		    nameTwo = String.valueOf(followQueue.dequeue());
			nameOne = String.valueOf(followQueue.dequeue());
		    graph.addEdge(nameTwo, nameOne);
		}
	}

    /****************************************
    * NAME: mostLiked                       *
	* IMPORT: graph                         *
	* EXPORT: none                          *
	* PURPOSE: sorts most liked posts       *
	****************************************/
    public static void likeSort(DSAGraph graph)
	{
	    //size of ADTs judged by how many posts are in the graph
	    int count = graph.getPostCount(), likes;
		String temp, text;
		String[] bits;
		DSAPost post;

        //checks if post count is greater than 0
        if(count <= 0)
		{
			System.out.println(".................................................");
		    System.out.println("No posts have been made yet!");
		}
		else
		{
			DSALinkedList names, posts;
			DSAHeap heap = new DSAHeap(count);

			//get all vertices
			names = graph.getVertices();

			//iterate through all vertices
			Iterator iter = names.iterator();
			while(iter.hasNext())
			{
			    /*get a vertice*/
				temp = String.valueOf(iter.next());

				/*get all posts*/
				posts = graph.getPosts(temp);

                /*iterate through all posts*/
				Iterator iterator = posts.iterator();
				while(iterator.hasNext())
				{
					post = (DSAPost)iterator.next();

					text = post.getText();
					text += "\nPosted by: " + temp;
					likes = post.getLikes(); 
					//store in a heap array, which will later be sorted based on the likes (value)
					heap.add(likes, text);
				}
			}
		    heap.heapSort();
			System.out.println("TRENDING POSTS...................................");
			heap.display(" likes");
		} 
	}

    /****************************************
    * NAME: mostFollowers                   *
	* IMPORT: graph                         *
	* EXPORT: none                          *
	* PURPOSE: finds vertex  most followers *
	****************************************/
    public static void followSort(DSAGraph graph)
	{
		int famous = 0, i = 0, followerCount = 0, verticeCount = graph.getCount();
		String temp, output = "";

		DSAHeap heap = new DSAHeap(verticeCount);

	    DSALinkedList names, followers; 
		//gets all people in the graph
		names = graph.getVertices();

        //iterate through all vertices
		Iterator iter = names.iterator();
		while(iter.hasNext())
		{
			//get single person
            temp = String.valueOf(iter.next());

			//get the followers of the vertex
			followerCount = graph.getFollowerCount(temp);

			heap.add(followerCount, temp);
	    }
		heap.heapSort();
		System.out.println("MOST FOLLOWED....................................");
		heap.display(" followers");
	}

    /****************************************
    * NAME: generateFileName                *
	* IMPORT: none                          *
	* EXPORT: filename                      *
	* PURPOSE: gets unique filename         *
	****************************************/
    public static String generateFileName()
	{
        String filename = "", dateString = "";
        Date date = new Date();
	    SimpleDateFormat formatter = new SimpleDateFormat("HH-mm-ss");
		dateString = formatter.format(date);
		filename = dateString + ".log";
		return filename;
	}

    /****************************************
    * NAME: mostFollowers                   *
	* IMPORT: graph                         *
	* EXPORT: none                          *
	* PURPOSE: finds vertex  most followers *
	****************************************/
    public static String mostFollowed(DSAGraph graph)
	{
	    DSALinkedList names = graph.getVertices();
		Iterator iter = names.iterator();
		String temp, name = "", output;
		int topFollowCount = 1;

		while(iter.hasNext())
		{
		     temp = String.valueOf(iter.next());
			 if(graph.getFollowerCount(temp) > topFollowCount)
			 {
			      name = temp;
			      topFollowCount = graph.getFollowerCount(temp);
			 }
	    }
		output = name  + " (" + topFollowCount + ")";
		return output;
	}

    /****************************************
    * NAME: getPostCount                    *
	* IMPORT: graph                         *
	* EXPORT: count                         *
	* PURPOSE: finds total number of posts  *
	****************************************/
	public static int getTotalFollowers(DSAGraph graph)
	{
		int count = 0;
		String temp;
	    DSALinkedList people;
        
		//get all vertices in the graph
		people = graph.getVertices();

        //iterate through all vertices
		Iterator iter = people.iterator();
		while(iter.hasNext())
		{
		    temp = String.valueOf(iter.next());
		    count += graph.getFollowerCount(temp);
		}
		return count;
	}
}
