/*********************************************
* AUTHOR: Andre de Moeller                   *
* DATE: 14.08.19                             *
* PURPOSE:                                   *
* LAST MODIFIED: 28.10.19
* REFERENCE: de Moeller, A. (2019). DSA P03  *
*********************************************/
import java.util.*;
import java.io.*;

public class DSALinkedList implements Serializable, Iterable
{
    private DSAListNode head;
    private DSAListNode tail;
	private int size;

    /****************************************
    * NAME: DSAListNode                     *
    * PURPOSE: DSAListNode Class            *
    ****************************************/
    private class DSAListNode implements Serializable
    {
        private Object nodeValue;
	    private DSAListNode next;
        private DSAListNode prev;

        /****************************************
        * NAME: DSAListNode                     *
		* IMPORT: inNodeValue                   *
		* EXPORT: none                          *
		* PURPOSE: address of new DSAListNode   *
		****************************************/
		public DSAListNode(Object inNodeValue)
		{
		    nodeValue = inNodeValue;
		    next = null;
			prev = null;
	    }
 
        //Accessors
        public Object getNodeValue()
	    {
	        return nodeValue;
	    }

	    public DSAListNode getNext()
	    {
	        return next;
	    }

		public DSAListNode getPrev()
		{
		    return prev;
		}

        //mutators
        /****************************************
        * NAME: setValue                        *
		* IMPORT: inValue                       *
		* EXPORT: none                          *
		* PURPOSE: sets nodeValue               *
		****************************************/
	    public void setValue(Object inNodeValue)
 	    {
	        nodeValue = inNodeValue;
	    }
 
        /****************************************
        * NAME: setNext                         *
		* IMPORT: newNext                       *
		* EXPORT: none                          *
		* PURPOSE: sets next                    *
		****************************************/
	    public void setNext(DSAListNode newNext)
	    {
	        next = newNext;
	    }

        /****************************************
        * NAME: setPrev                         *
		* IMPORT: newPrev                       *
		* EXPORT: none                          *
		* PURPOSE: sets prev                    *
		****************************************/
	    public void setPrev(DSAListNode newPrev)
	    {
	        prev = newPrev;
	    } 
	}

    public Iterator iterator()
    {
	    return new DSALinkedListIterator(this);
	}

	private class DSALinkedListIterator implements Serializable, Iterator
	{
	    private DSAListNode iterNext;

		public DSALinkedListIterator(DSALinkedList theList)
		{
		    iterNext = theList.head;
		}

		public boolean hasNext()
	    {
		    return (iterNext != null);
		}
		
		public Object next()
		{
		    Object nodeValue;

            if(iterNext == null)
			{
			    nodeValue = null;
			}
			else
			{
			    nodeValue = iterNext.getNodeValue();
				iterNext = iterNext.getNext();
			}
			return nodeValue;
		}

		public void remove()
		{
		    throw new UnsupportedOperationException("Not supported");
		}
	}

	/****************************************
    * NAME: Alternate Constructor           *
    * IMPORT: none                          *
    * EXPORT: none                          *
    * PURPOSE: address of new list object   *
    ****************************************/
    public DSALinkedList()
	{
	    head = null;
		tail = null;
		size = 0;
	}

	//accessor
	public int getSize()
	{
	    return size;
	}
     
	/****************************************
    * NAME: insertFirst                     *
    * IMPORT: newValue                      *
    * EXPORT: none                          *
    * PURPOSE: adds val to first of list    *
    ****************************************/
	public void insertFirst(Object newValue)
	{
	    DSAListNode newNd = new DSAListNode(newValue);

		if(isEmpty())
		{
		    tail = newNd;
			head = newNd;
		}
		else
		{
		    newNd.setNext(head);
			head.setPrev(newNd);
			head = newNd;
		}
		size++;
	}
 
	/****************************************
    * NAME: insertLast                      *
    * IMPORT: newValue                      *
    * EXPORT: none                          *
    * PURPOSE: adds val to end of list      *
    ****************************************/
	public void insertLast(Object newValue)
	{
	    DSAListNode newNd = new DSAListNode(newValue);

	    if(isEmpty())
		{
			tail = newNd;
			head = newNd;
		}
		else
		{
		    newNd.setPrev(tail);
			tail.setNext(newNd);
			tail = newNd;
		}
		size++;
	}
    
	/****************************************
    * NAME: isEmpty                         *
    * IMPORT: none                          *
    * EXPORT: empty                         *
    * PURPOSE: checks if list is empty      *
    ****************************************/
	public boolean isEmpty()
	{
		return head == null;
	}
 
	/****************************************
    * NAME: peekFirst                       *
    * IMPORT: none                          *
    * EXPORT: nodeValue                     *
    * PURPOSE: peeks first value of list    *
    ****************************************/
	public Object peekFirst()
	{
        Object nodeValue;

	    if(isEmpty())
		{
		    throw new IllegalStateException("Error! Linked list is empty.");
		}
		else
		{
		    nodeValue = head.getNodeValue();
		}
		return nodeValue;
	}
    
	/****************************************
    * NAME: peekLast                        *
    * IMPORT: none                          *
    * EXPORT: nodeValue                     *
    * PURPOSE: peeks last value of list     *
    ****************************************/
	public Object peekLast()
	{
	    Object nodeValue;

	    if(isEmpty())
		{
		    throw new IllegalStateException("Error! Linked list is empty.");
		}
		else
		{
			nodeValue = tail.getNodeValue();
		}
		return nodeValue;
	}
    
	/****************************************
    * NAME: removeFirst                     *
    * IMPORT: none                          *
    * EXPORT: nodeValue                     *
    * PURPOSE: removes first value of list  *
    ****************************************/
	public Object removeFirst()
	{
	    Object nodeValue;

	    if(isEmpty())
		{
		    throw new IllegalStateException("Error! Linked list is empty.");
		}
		else if(head == tail)
		{
		    nodeValue = head.getNodeValue();
			head = null;
			tail = null;
		}
		else
		{
		    nodeValue = head.getNodeValue();
			head.getNext().setPrev(null);
			head = head.getNext();
		    size--;
		}
		return nodeValue;
	}
    
	/****************************************
    * NAME: removeLast                      *
    * IMPORT: none                          *
    * EXPORT: nodeValue                     *
    * PURPOSE: removes last value of list   *
    ****************************************/
	public Object removeLast()
	{
        Object nodeValue;

	    if(isEmpty())
		{
		    throw new IllegalStateException("Error! Linked list is empty.");
		}
		else
		{
		    nodeValue = tail.getNodeValue();
			tail = tail.getPrev();
		    size--;
		}
        return nodeValue;
	}

	/****************************************
    * NAME: removeMiddle                    *
    * IMPORT: name                          *
    * EXPORT: nodeValue                     *
    * PURPOSE: removes a middle value       *
    ****************************************/
	public Object removeMiddle(Object name)
	{
	    DSAListNode prev = null, nodeOne = head;
		Object removedVal = null;
		int i = 0;

		if(isEmpty())
		{
		    throw new IllegalStateException("Error! Linked list is empty.");
		}
		else
		{
		    while(nodeOne != null)
			{
			    if(nodeOne.getNodeValue().equals(name))
				{
				    nodeOne = nodeOne.getNext();
					if(prev == null)
					{
					    removedVal = removeFirst();
					}
					else if(i == size)
					{
					    removedVal = removeLast();
					}
					else
					{
					    prev.setNext(nodeOne);
						removedVal = nodeOne;
						size--;
					}
					//removed = true;
				}
				else
				{
				    prev = nodeOne;
					nodeOne = nodeOne.getNext();
				}
				i++;
		    }
		}
		return removedVal;
	}
}
