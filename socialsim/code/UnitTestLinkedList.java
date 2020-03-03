/****************************************
* AUTHOR: Andre de Moeller              *
* DATE: 27.10.19                        *
* PURPOSE: unit test for linked list    *
* LAST MODIFIED: 28.10.19               *
****************************************/
import java.util.*;
import java.io.*;

public class UnitTestLinkedList {
    public static void main(String[] args) {
        int passed = 0, temp;
        DSALinkedList list = null;

        System.out.println("-------------------------------------------------");
        System.out.println("                   TEST HARNESS                  ");
        System.out.println("-------------------------------------------------");
        System.out.print("LINKED LIST CREATION: ");
        try {
            list = new DSALinkedList();
            System.out.print("passed");
            passed++;
        } catch (Exception ex) {
            System.out.print("failed");
        }

        System.out.println("");
        System.out.println(".................................................");
        System.out.print("INSERT FIRST: ");
        try {
            list.insertFirst(1);
            list.insertFirst(2);
            list.insertFirst(3);
            list.insertFirst(4);

            System.out.print("passed");
            passed++;
        } catch (Exception ex) {
            System.out.print("failed");
        }

        System.out.println("");
        System.out.println(".................................................");
        System.out.print("INSERT LAST: ");
        try {
            list.insertLast(5);
            list.insertLast(6);
            list.insertLast(7);
            list.insertLast(8);

            System.out.print("passed");
            passed++;
        } catch (Exception ex) {
            System.out.print("failed");
        }

        System.out.println("");
        System.out.println(".................................................");
        System.out.print("REMOVE MIDDLE (5): ");
        try {
            list.removeMiddle(5);
            System.out.print("passed");
            passed++;
        } catch (Exception ex) {
            System.out.print("failed");
        }

        System.out.println("");
        System.out.println(".................................................");
        System.out.print("ITERATOR: ");
        try {
            System.out.print("passed");
            passed++;

            System.out.println("");
            Iterator iterator = list.iterator();
            while (iterator.hasNext()) {
                System.out.println(iterator.next());
            }
        } catch (Exception ex) {
            System.out.print("failed");
        }

        System.out.println("-------------------------------------------------");
        System.out.println("TESTS PASSED: " + passed + "/5");
        System.out.println("-------------------------------------------------");
    }
}