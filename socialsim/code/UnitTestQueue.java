/****************************************
* AUTHOR: Andre de Moeller              *
* DATE: 28.10.19                        *
* PURPOSE: unit test for queue          *
* LAST MODIFIED: 28.10.19
****************************************/
import java.util.*;
import java.io.*;

public class UnitTestQueue
{
    public static void main(String[] args)
	{
	    DSAQueue queue = new DSAQueue();
		int passed = 0;
		System.out.println("-------------------------------------------------");
		System.out.println("                   TEST HARNESS                  ");
		System.out.println("-------------------------------------------------"); 
		System.out.print("QUEUE CREATION: ");
        try
		{
		    queue = new DSAQueue();
			System.out.print("passed");
			passed++;
		}
		catch(Exception ex)
		{
		    System.out.print("failed");
		}
		
		System.out.println("");
		System.out.println(".................................................");
		System.out.print("ENQUEUE: ");
        try
		{
		    queue.enqueue(1);
		    System.out.print("passed");
			passed++;
		}
		catch(Exception ex)
		{
		    System.out.print("failed");
		}

		System.out.println("");
		System.out.println(".................................................");
		System.out.print("DEQUEUE: ");
        try
		{
		    queue.dequeue();
		    System.out.print("passed");
			passed++;
		}
		catch(Exception ex)
		{
		    System.out.print("failed");
		}
		System.out.println("");
		System.out.println("-------------------------------------------------");
		System.out.println("TESTS PASSED: " + passed + "/3");
		System.out.println("-------------------------------------------------");
    }
}
