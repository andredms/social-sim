/*******************************************
* AUTHOR: Andre de Moeller                 *
* DATE: 24.10.19                           *
* PURPOSE: generates random network and    *
           event files based of cmd args   *
* LAST MODIFIED: 28.10.19
*******************************************/
import java.util.*;
import java.io.*;

public class genfiles
{
	/****************************************
    * NAME: main                            *
    * IMPORT: args                          *
    * EXPORT: none                          *
    * PURPOSE: sets cmd line args           *
    ****************************************/
    public static void main(String[] args)
	{
	    int names, connections, postNum;

	    if(args.length != 2)
		{
			System.out.println("\n");
			System.out.println("-------------------------------------------------");
			System.out.println("                USAGE INFORMATION                ");
			System.out.println("-------------------------------------------------");
		    System.out.println("RANDOM EVENT FILES...............................");
			System.out.println("1) java genfiles <numNames> <numFollows>         ");
			System.out.println("2) Files saved to: network.txt, events.txt       ");
            System.out.println("3) Events randomly generated based off input     ");
			System.out.println("-------------------------------------------------");
		}
		else
		{
			names = Integer.parseInt(args[0]);
			connections = Integer.parseInt(args[1]);
			if(names == 0 || connections == 0)
			{
			    System.out.println("Error, numbers must be greater than 0!");
			}
			else
			{
			    genNetwork(names, connections);
			    System.out.println("Generated: network.txt, events.txt");
			}
		}
    }

	/****************************************
    * NAME: genNetwork                      *
    * IMPORT: names, connections            *
    * EXPORT: none                          *
    * PURPOSE: generates random names       *
    ****************************************/
	public static void genNetwork(int names, int connections)
	{
	    Random rand = new Random();        

        String[] finalNames = new String[names];
		String[] follows = new String[connections];

		for(int i = 0; i < names; i++)
		{
		    finalNames[i] = generateName();
		}

		for(int j = 0; j < connections; j++)
		{
		    follows[j] = finalNames[rand.nextInt(names)] + ":" + finalNames[rand.nextInt(names)];
		}

		saveNetwork(finalNames, follows, "network.txt");
		genEvents(finalNames, names);
	}

	/****************************************
    * NAME: genEvents                       *
    * IMPORT: finalNames, names             *
    * EXPORT: none                          *
    * PURPOSE: generates random events      *
    ****************************************/
	public static void genEvents(String[] finalNames, int names)
	{
	    Random rand = new Random();
        int postCount = rand.nextInt(names);
		int followCount = rand.nextInt(names);
		String[] bits;
		String name;

		String[] post = new String[postCount];

		String[] postActions = new String[postCount];

		//divided by two/four for lesser chance of adding/removing person (skewing data), +1 in case 0
		String[] addActions = new String[(rand.nextInt(names) / 2) + 1];
		String[] removeActions = new String[(rand.nextInt(addActions.length) / 4) + 1];

		String[] addedNames = new String[addActions.length];

		//+1 in case followCount is 0
		String[] followActions = new String[followCount + 1];

		//divided by four for lesser chance of unfollowing (skewing data), +1 in case 0
		String[] unfollowActions = new String[(rand.nextInt(followActions.length) / 4) + 1];

		//array for storing final names array and the new added names array
		//this is used so that the added names have the chance to follow the original names
		String[] mergedArray;

        //post creation
		for(int i = 0; i < postCount; i++)
		{
		    post[i] = generatePost();
		}

        //post actions distributed randomly
		for(int j = 0; j < postCount; j++)
		{
		    //identifier:name:post:clickbait factor
		    postActions[j] = "P:" + finalNames[rand.nextInt(names)] + ":" + post[rand.nextInt(postCount)] + ":" + (rand.nextInt(3) + 1);
		}

		//add actions distributed randomly
		for(int i = 0; i < addActions.length; i++)
		{
			name = generateName();
			addActions[i] = "A:" + name;
			addedNames[i] = name;
		}

		mergedArray = mergeArrays(finalNames, addedNames);
 
        //follow actions distributed randomly
		for(int k = 0; k < followCount; k++)
		{
		    followActions[k] = "F:" + mergedArray[rand.nextInt(mergedArray.length)] + ":" + mergedArray[rand.nextInt(mergedArray.length)];
		}
		 
        //unfollow actions distributed randomly
        if(unfollowActions.length > 0 && followActions.length > 1)
		{
			for(int i = 0; i < unfollowActions.length; i++)
			{
				bits = followActions[i].split(":");
				unfollowActions[i] = "U:" + bits[1] + ":" + bits[2];
			}
		}    

		//remove actions distributed randomly
		for(int i = 0; i < removeActions.length; i++)
		{
		    removeActions[i] = "R:" + mergedArray[rand.nextInt(mergedArray.length)];
		}
		saveEvent(postActions, addActions, followActions, unfollowActions, removeActions, "events.txt");
	}

	/****************************************
    * NAME: saveFile                        *
    * IMPORT: arrOne, arrTwo, filename      *
    * EXPORT: none                          *
    * PURPOSE: saves network                *
    ****************************************/
	public static void saveNetwork(String[] arrOne, String[] arrTwo, String filename)
	{
	    FileOutputStream fileStrm = null;
		PrintWriter pw;

		try
		{ 
		    fileStrm = new FileOutputStream(filename);
			pw = new PrintWriter(fileStrm);

            for(int i = 0; i < arrOne.length; i++)
			{
					pw.println(arrOne[i]);
			}

            for(int i = 0; i < arrTwo.length; i++)
			{
					pw.println(arrTwo[i]);
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
    * NAME: save                            *
    * IMPORT: arrOne, arrTwo, filename,     *
	*         arrThree, arrFour             *
    * EXPORT: none                          *
    * PURPOSE: saves events                 *
    ****************************************/
	public static void saveEvent(String[] arrOne, String[] arrTwo, String[] arrThree, String[] arrFour, String[] arrFive, String filename)
	{
	    FileOutputStream fileStrm = null;
		PrintWriter pw;

		try
		{ 
		    fileStrm = new FileOutputStream(filename);
			pw = new PrintWriter(fileStrm);

            for(int i = 0; i < arrOne.length; i++)
			{
			    if(arrOne[i] != null)
				{
					pw.println(arrOne[i]);
				}
			}

            for(int i = 0; i < arrTwo.length; i++)
			{
			    if(arrTwo[i] != null)
				{
					pw.println(arrTwo[i]);
				}
			}

            for(int i = 0; i < arrThree.length; i++)
			{
			    if(arrThree[i] != null)
				{
					pw.println(arrThree[i]);
				}
			}
		
            for(int i = 0; i < arrFour.length; i++)
			{
			    if(arrFour[i] != null)
				{
					pw.println(arrFour[i]);
				}
			}

            for(int i = 0; i < arrFive.length; i++)
			{
				if(arrFive[i] != null)
				{
					pw.println(arrFive[i]);
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
    * NAME: generateName                    *
    * IMPORT: none                          *
    * EXPORT: output (random name)          *
    * PURPOSE: generates random name        *
    ****************************************/
    public static String generateName()
	{
	    Random rand = new Random();

		//65,536 unique names
	    String[] firstNameOne = {"Co", "Le", "No", "Te", "Se", "Fu", "Po", "Jo", "Ga", "Me", "Fe", "De", "Be", "Ha", "He", "De"};
		String[] firstNameTwo = {"nnor", "ntil", "nrad", "dy", "mbo", "nny", "t", "h", "hn", "rry", "ck", "ep", "pis", "iry", "ary", "nnis"};
		String[] lastNameOne = {"Te", "Mu", "Simp", "Wea", "Mo", "Di", "Dan", "Na", "Whim", "Ma", "Jo", "Ga", "Waz", "Pot", "Gra", "Flam"};
		String[] lastNameTwo = {"mpo", "ntz", "son", "sley", "sby", "gs", "iels", "sty", "sical", "ster", "bs", "tes", "owski", "ter", "nger", "ingo"};

        //generates random name based off random indexes
	    String output = firstNameOne[rand.nextInt(16)] + firstNameTwo[rand.nextInt(16)] + " " + lastNameOne[rand.nextInt(16)] + lastNameTwo[rand.nextInt(16)];
		return output;
	}

	/****************************************
    * NAME: generatePost                    *
    * IMPORT: none                          *
    * EXPORT: output (random name)          *
    * PURPOSE: generates random name        *
    ****************************************/
    public static String generatePost()
	{
	    Random rand = new Random();

	    //840 max post combinations
	    String[] greetings = {"Hello", "Howdy", "Hey", "Hi", "Sup", "Greetings", "Welcome"};
		String[] noun = {" kids", " friends", " enemies", " gamers", " dudes", " buddy", " pal", " comrade"};
		String[] verb = {", let's go to", ", come with me to", ". Onwards to"};
		String[] action = {" the movies!", " the park!", " the beach!", " the footy!", " find true meaning."};

        //generates random name based off random indexes
		String post = greetings[rand.nextInt(7)] + noun[rand.nextInt(8)] + verb[rand.nextInt(2)] + action[rand.nextInt(5)];
		return post;
	}

	/****************************************
    * NAME: mergeArrays                     *
    * IMPORT: none                          *
    * EXPORT: output (random name)          *
    * PURPOSE: generates random name        *
    ****************************************/
	public static String[] mergeArrays(String[] finalNames, String[] addedNames)
	{
	    String[] mergedArray = new String[finalNames.length + addedNames.length];
		int count = 0;

		for(int i = 0; i < finalNames.length; i++)
		{
		    mergedArray[i] = finalNames[i];
			count++;
		}

		for(int j = 0; j < addedNames.length; j++)
		{
		    mergedArray[j+count] = addedNames[j];
		}
		return mergedArray;
	}
}
