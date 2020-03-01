/*********************************************
* AUTHOR: Andre de Moeller                   *
* DATE: 08.08.19                             *
* PURPOSE: queue implementation              *
* LAST MODIFIED: 27.10.19
* REFERENCE: de Moeller, A. (2019). DSA P03  *
*********************************************/
import java.util.*;
import java.io.*;

public class DSAQueue implements Iterable
{
    protected DSALinkedList list;

    /****************************************
    * NAME: Default Constructor             *
    * IMPORT: none                          *
    * EXPORT: Address of new stack          *
    * PURPOSE: count and array initialised  *
    ****************************************/
    public DSAQueue()
	{
	    list = new DSALinkedList();
	}
 
	//mutators
    /****************************************
    * NAME: enqueue                         *
    * IMPORT: value                         *
    * EXPORT: none                          *
    * PURPOSE: queues an element            *
    ****************************************/
    public void enqueue(Object value)
	{
		    list.insertLast(value);
    }

    /****************************************
    * NAME: dequeue                         *
    * IMPORT: topVal                        *
    * EXPORT: none                          *
    * PURPOSE: dequeues an element          *
    ****************************************/
    public Object dequeue()
    {
	    Object frontVal;
        frontVal = list.peekFirst();
		list.removeFirst();

		return frontVal;
    }

	//accessors
	public Iterator iterator()
	{
	    return list.iterator();
	}

	public Object peek()
	{
	    return list.peekFirst();
	}

	public boolean isEmpty()
	{
	    return list.isEmpty();
	}
}
