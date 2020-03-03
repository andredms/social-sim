/****************************************
* AUTHOR: Andre de Moeller              *
* DATE: 18.10.19                        *
* PURPOSE: post class for socialsim     *
* LAST MODIFIED: 27.10.19               *
****************************************/
import java.util.*;
import java.io.*;
import java.text.SimpleDateFormat;

public class DSAPost {
    private String text;
    private int likes, multiplier;
    private DSALinkedList likedBy;

    public DSAPost(String inText, int inMultiplier) {
        text = inText;
        multiplier = inMultiplier;
        likes = 0;
        likedBy = new DSALinkedList();
    }

    public String toString() {
        String output = this.text + "\n" + this.likes + " likes";
        return output;
    }

    //accessors
    public String getText() {
        return text;
    }

    public int getLikes() {
        return likes;
    }

    public DSALinkedList getLikedBy() {
        return likedBy;
    }

    public int getMultiplier() {
        return multiplier;
    }

    public void addLike(String name) {
        likes++;
        likedBy.insertFirst(name);
    }
}