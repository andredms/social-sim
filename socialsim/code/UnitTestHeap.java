/****************************************
* AUTHOR: Andre de Moeller              *
* DATE: 21.09.19                        *
* PURPOSE: tests DSAHeap functionality  *
* LAST MODIFIED: 28.10.19
****************************************/
import java.util.*;
import java.io.*;

public class UnitTestHeap
{
    public static void main(String[] args)
    { 
	    DSAHeap heap = null;
		Object temp = 0;
		int passed = 0;
		System.out.println("");
		System.out.println("-------------------------------------------------");
		System.out.println("                   TEST HARNESS                  ");
		System.out.println("-------------------------------------------------");
        System.out.print("HEAP CREATION: ");
        try
		{
		    heap = new DSAHeap(3);
			System.out.print("passed");
			passed++;
		}
		catch(Exception ex)
		{
		    System.out.print("failed");
		}

		System.out.println("");
		System.out.println(".................................................");
		System.out.print("ADD ELEMENTS: ");
        try
		{
		    heap.add(1, 1);
			heap.add(2, 2);
			heap.add(3, 3);
		    System.out.print("passed");
			passed++;
		}
		catch(Exception ex)
		{
		    System.out.print("failed");
		}

        System.out.println("");
		System.out.println(".................................................");
		System.out.print("REMOVE: ");
		try
		{
		    temp = heap.remove();
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
