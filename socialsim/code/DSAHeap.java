/*********************************************
* AUTHOR: Andre de Moeller                   *
* DATE: 26.09.19                             *
* PURPOSE: creates a heap class              *
* LAST MODIFIED: 28.10.19
* REFERENCE: de Moeller, A. (2019). DSA P07  *
*********************************************/
import java.util.*;
import java.io.*;

public class DSAHeap
{ 
    private DSAHeapEntry[] heapArray;
	private int count;
     
	/****************************************
	* NAME: creates a heap entry            *
	* PURPOSE: address of new DSAHeapEntry  *
	****************************************/
	private class DSAHeapEntry
	{
		private int priority;
		private Object value;

		/****************************************
		* NAME: default constructor             *
		* IMPORT: inPriority, inValue           *
		* EXPORT: none                          *
		* PURPOSE: address of new DSAHeapEntry  *
		****************************************/
		public DSAHeapEntry(int inPriority, Object inValue)
		{
			priority = inPriority;
			value = inValue;
		}

		//accessors
		public Object getValue()
		{
			return value;
		}

		public int getPriority()
		{
			return priority;
		}

		public String toString()
		{
			String output;
			output = this.value + "\n" + this.priority;
			return output;
		}
	}

    /****************************************
    * NAME: default constructor             *
    * IMPORT: size                          *
    * EXPORT: none                          *
    * PURPOSE: address of new heap          *
    ****************************************/
	public DSAHeap(int size)
	{
	    count = 0;
		heapArray = new DSAHeapEntry[size];
	}

    /****************************************
    * NAME: add                             *
    * IMPORT: priority, value               *
    * EXPORT: none                          *
    * PURPOSE: adds entry to heap           *
    ****************************************/
	public void add(int priority, Object value)
	{
	    DSAHeapEntry entry = new DSAHeapEntry(priority, value); 
		heapArray[count] = entry;

		heapArray = trickleUp(heapArray, count);
		count++;
	}

    /****************************************
    * NAME: remove                          *
    * IMPORT: none                          *
    * EXPORT: temp                          *
    * PURPOSE: removes element from heap    *
    ****************************************/
    public Object remove()
	{
        DSAHeapEntry temp = heapArray[0];
	    if(count > 0)
		{ 
			heapArray[0] = heapArray[count - 1];

			heapArray[count - 1] = heapArray[0];
			trickleDown(heapArray, 0, count);
			count--;
		}
		return temp;
	}

    /****************************************
    * NAME: trickleUp                       *
    * IMPORT: index                         *
    * EXPORT: heapArray                     *
    * PURPOSE: trickles up highest priority *
    ****************************************/
	private DSAHeapEntry[] trickleUp(DSAHeapEntry[] heapArray, int index)
	{
	    int parentIndex = (index - 1)/2;
		DSAHeapEntry temp;
		if(index > 0)
		{
		    if(heapArray[index].getPriority() > heapArray[parentIndex].getPriority())
			{
			    temp = heapArray[parentIndex];
			    heapArray[parentIndex] = heapArray[index];
				heapArray[index] = temp;

				trickleUp(heapArray, parentIndex);
			}
		}
		return heapArray;
	}

    /****************************************
    * NAME: trickleDown                     *
    * IMPORT: heapArray, index, numItems    *
    * EXPORT: none                          *
    * PURPOSE: trickles down highest prior  *
    ****************************************/
    private void trickleDown(DSAHeapEntry[] heapArray, int index, int numItems)
    {
	    int oneChildIndex = index * 2 + 1;
		int rChildIndex = oneChildIndex + 1;

		if(oneChildIndex < numItems)
		{
		    int largeIndex = oneChildIndex;
			if(rChildIndex < numItems)
			{
			    if(heapArray[oneChildIndex].getPriority() < heapArray[rChildIndex].getPriority())
				{
				    largeIndex = rChildIndex;
				}
			}
		    if(heapArray[largeIndex].getPriority() > heapArray[index].getPriority())
			{
			    swap(heapArray, largeIndex, index);
				trickleDown(heapArray, largeIndex, numItems);
			}
		}
	}

    /****************************************
    * NAME: swap                            *
    * IMPORT: heapArray, curIdx, largeIdx   *
    * EXPORT: none                          *
    * PURPOSE: swaps array values           *
    ****************************************/
	public void swap(DSAHeapEntry[] heapArray, int curIdx, int largeIdx)
	{
        DSAHeapEntry temp = heapArray[curIdx];
		heapArray[curIdx] = heapArray[largeIdx];
		heapArray[largeIdx] = temp;
	}

    /****************************************
    * NAME: display                         *
    * IMPORT: none                          *
    * EXPORT: none                          *
    * PURPOSE: displays heapArray           *
    ****************************************/
    public void display(String prompt)
	{
	    for(int i = heapArray.length -1; i >= 0; i--)
		{
		    if(heapArray[i] != null)
			{
			    System.out.println(heapArray[i] + prompt);
			}
			if(i != 0)
			{
				System.out.println("-------------------------------------------------"); 
			}
		}
	}

    /****************************************
    * NAME: heapify                         *
    * IMPORT: heapArray                     *
    * EXPORT: heapArray                     *
    * PURPOSE: makes heapArray into a heap  *
    ****************************************/
    public DSAHeapEntry[] heapify()
	{
		for(int ii = ((heapArray.length) / 2) - 1; ii >= 0; ii--)
		{
			trickleDown(heapArray, ii, heapArray.length - 1);
		}
		return heapArray;
	}
 
    /****************************************
    * NAME: heapSort                        *
    * IMPORT: heapArray                     *
    * EXPORT: heapArray                     *
    * PURPOSE: sorts by priority            *
    ****************************************/
    public DSAHeapEntry[] heapSort()
    {
	    heapify();
		for(int ii = heapArray.length - 1; ii >= 1; ii--)
		{
			swap(heapArray, 0, ii);
			trickleDown(heapArray, 0, ii);
		}
		return heapArray;
	}
}
