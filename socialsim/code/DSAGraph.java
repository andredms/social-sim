/*********************************************
* AUTHOR: Andre de Moeller                   *
* DATE: 10.09.19                             *
* PURPOSE: implements graph                  *
* LAST MODIFIED: 28.10.19                    *
*********************************************/
import java.util.*;
import java.io.*;
import java.text.SimpleDateFormat;

public class DSAGraph {
    private DSALinkedList vertices;

    //keeps track of total vertices
    private int verticeCount;

    //keeps track of total posts
    private int postCount;

    //keeps track of total likes
    private int likeCount;

    /****************************************
    * NAME: DSAGraphVertex                  *
    * PURPOSE: create graph vertex obj      *
    ****************************************/
    private class DSAGraphVertex {
        //name of person
        private String label;

        //posts stored in post class
        private DSALinkedList posts;

        //list of followers
        private DSALinkedList followers;
        private int followerCount;

        //list of following
        private DSALinkedList following;

        /****************************************
        * NAME: default constructor             *
        * IMPORT: inLabel, inValue              *
        * EXPORT: none                          *
        * PURPOSE: address of new vertex        *
        ****************************************/
        public DSAGraphVertex(String inLabel) {
            //edges
            following = new DSALinkedList();
            followers = new DSALinkedList();
            followerCount = 0;

            //posts
            posts = new DSALinkedList();

            //name
            label = inLabel;
        }

        //accessors
        public String getLabel() {
            return this.label;
        }

        public String toString() {
            String output = this.getLabel() + "";
            return output;
        }

        public DSALinkedList getFollowers() {
            return this.followers;
        }

        public int getFollowerCount() {
            return this.followerCount;
        }

        public DSALinkedList getPosts() {
            return this.posts;
        }

        public DSALinkedList getFollowing() {
            return this.following;
        }

        public DSALinkedList getLikedBy(String inText) {
            DSAPost post;
            DSALinkedList likedBy = this.getPosts();
            if (!posts.isEmpty()) {
                Iterator iter = posts.iterator();
                while (iter.hasNext()) {
                    post = (DSAPost) iter.next();
                    if (inText.equals(post.getText())) {
                        likedBy = post.getLikedBy();
                    }
                }
            }
            return likedBy;
        }

        //mutators
        public void removeFollowing(DSAGraphVertex vertex) {
            if (!following.isEmpty()) {
                following.removeMiddle(vertex);
            }
        }

        public void removeFollower(DSAGraphVertex vertex) {
            if (!followers.isEmpty()) {
                followers.removeMiddle(vertex);
            }
            followerCount--;
        }

        //mutators
        public void addFollowing(DSAGraphVertex vertex) {
            //is vertex already following
            if (!isFollowing(this.getLabel(), vertex.getLabel()) && (!this.getLabel().equals(vertex.getLabel()))) {
                following.insertLast(vertex);

            }
        }

        public void addFollower(DSAGraphVertex vertex) {
            //does vertex already have the follower
            if (!isFollower(this.getLabel(), vertex.getLabel()) && (!this.getLabel().equals(vertex.getLabel()))) {
                followers.insertLast(vertex);
                followerCount++;
            }
        }

        public void makePost(String inText, int multiplier) {
            DSAPost post = new DSAPost(inText, multiplier);
            posts.insertLast(post);
        }

        public void addLike(String inText, String liker) {
            DSAPost post;
            if (!posts.isEmpty()) {
                Iterator iter = posts.iterator();
                while (iter.hasNext()) {
                    post = (DSAPost) iter.next();
                    if (inText.equals(post.getText())) {
                        post.addLike(liker);
                    }
                }
            }
        }
    }

    /****************************************
    * NAME: default constructor             *
    * IMPORT: none                          *
    * EXPORT: none                          *
    * PURPOSE: address of new DSA graph     *
    ****************************************/
    public DSAGraph() {
        vertices = new DSALinkedList();
        verticeCount = 0;
        postCount = 0;
        likeCount = 0;
    }

    //accessor
    public DSALinkedList getVertices() {
        return vertices;
    }

    public int getCount() {
        return verticeCount;
    }

    public int getPostCount() {
        return postCount;
    }

    public int getLikeCount() {
        return likeCount;
    }

    /****************************************
    * NAME: addVertex                       *
    * IMPORT: inLabel, inValue              *
    * EXPORT: none                          *
    * PURPOSE: adds a vertex                *
    ****************************************/
    public void addVertex(String inLabel) {

        DSAGraphVertex vertex = new DSAGraphVertex(inLabel);
        if ((hasVertex(inLabel) == false) && (vertex != null)) {
            vertices.insertLast(vertex);
            verticeCount++;
        }
    }

    /****************************************
    * NAME: hasVertex                       *
    * IMPORT: label                         *
    * EXPORT: found                         *
    * PURPOSE: checks if vertex exists      *    
    ****************************************/
    public boolean hasVertex(String label) {
        boolean found = false;
        DSAGraphVertex temp;
        String labelTemp;

        Iterator iterator = vertices.iterator();
        while (iterator.hasNext()) {
            temp = (DSAGraphVertex) iterator.next();
            labelTemp = temp.getLabel();
            if (((String) labelTemp).equals((String) label)) {
                found = true;
            }
        }
        return found;
    }

    /****************************************
    * NAME: addEdge                         *
    * IMPORT: labelOne, labelTwo            *
    * EXPORT: none                          *
    * PURPOSE: adds edge                    *
    ****************************************/
    public void addEdge(String labelOne, String labelTwo) {
        DSAGraphVertex vertexOne;
        DSAGraphVertex vertexTwo;

        vertexOne = getVertex(labelOne);
        vertexTwo = getVertex(labelTwo);

        if (vertexOne != null && vertexTwo != null) {
            vertexOne.addFollower(vertexTwo);
            vertexTwo.addFollowing(vertexOne);
        }
    }

    /****************************************
    * NAME: getVertex                       *
    * IMPORT: label                         *
    * EXPORT: vertex                        *
    * PURPOSE: gets associated vertex       *
    ****************************************/
    public DSAGraphVertex getVertex(String label) {
        DSAGraphVertex temp, vertex = null;

        Iterator iterator = vertices.iterator();
        while (iterator.hasNext()) {
            temp = (DSAGraphVertex) iterator.next();
            if (((String) label).equals((String) temp.getLabel())) {
                vertex = temp;
            }
        }
        return vertex;
    }

    /****************************************
    * NAME: isAdjacent                      *
    * IMPORT: labelOne, labelTwo            *
    * EXPORT: found                         *
    * PURPOSE: check if two vertices adj    *
    ****************************************/
    public boolean isFollowing(String labelOne, String labelTwo) {
        DSAGraphVertex vertexOne, vertexTwo, temp;
        DSALinkedList listOne, listTwo;
        boolean found = false;

        vertexOne = getVertex(labelOne);
        vertexTwo = getVertex(labelTwo);
        if (vertexOne != null && vertexTwo != null) {
            listOne = vertexOne.getFollowing();

            Iterator iterator = listOne.iterator();
            while (iterator.hasNext()) {
                temp = (DSAGraphVertex) iterator.next();
                if (((String) vertexTwo.getLabel()).equals((String) temp.getLabel())) {
                    found = true;
                }
            }
        }
        return found;
    }

    /****************************************
    * NAME: isAdjacent                      *
    * IMPORT: labelOne, labelTwo            *
    * EXPORT: found                         *
    * PURPOSE: check if two vertices adj    *
    ****************************************/
    public boolean isFollower(String labelOne, String labelTwo) {
        DSAGraphVertex vertexOne, vertexTwo, temp;
        DSALinkedList listOne, listTwo;
        boolean found = false;

        vertexOne = getVertex(labelOne);
        vertexTwo = getVertex(labelTwo);

        listOne = vertexOne.getFollowers();

        Iterator iterator = listOne.iterator();
        while (iterator.hasNext()) {
            temp = (DSAGraphVertex) iterator.next();
            if (((String) vertexTwo.getLabel()).equals((String) temp.getLabel())) {
                found = true;
            }
        }
        return found;
    }

    /****************************************
    * NAME: getFollowing                    *
    * IMPORT: label                         *
    * EXPORT: list                          *
    * PURPOSE: gets list                    *
    ****************************************/
    public DSALinkedList getFollowing(String label) {
        DSAGraphVertex vertex;
        DSALinkedList list;

        vertex = getVertex(label);
        list = vertex.getFollowing();

        return list;
    }

    /****************************************
    * NAME: getFollowers                    *
    * IMPORT: label                         *
    * EXPORT: list                          *
    * PURPOSE: gets list                    *
    ****************************************/
    public DSALinkedList getFollowers(String label) {
        DSAGraphVertex vertex;
        DSALinkedList list;

        vertex = getVertex(label);
        list = vertex.getFollowers();

        return list;
    }

    /****************************************
    * NAME: displayAsList                   *
    * IMPORT: none                          *
    * EXPORT: none                          *
    * PURPOSE: displays graph as adj list   *
    ****************************************/
    public void displayAsList() {
        DSALinkedList list = new DSALinkedList();
        DSAGraphVertex temp;
        String output = "";

        Iterator iterator = vertices.iterator();
        while (iterator.hasNext()) {
            temp = (DSAGraphVertex) iterator.next();
            list = temp.getFollowing();

            Iterator iter = list.iterator();
            while (iter.hasNext()) {
                output += (DSAGraphVertex) iter.next();
                if (iter.hasNext()) {
                    output += ", ";
                }
            }
            System.out.println(temp + ": " + output);
            output = "";
        }
    }

    /****************************************
    * NAME: makePost                        *
    * IMPORT: label, post                   *
    * EXPORT: none                          *
    * PURPOSE: makes a post                 *
    ****************************************/
    public void makePost(String label, String text, int multiplier) {
        DSAGraphVertex vertex;
        if (hasVertex(label) == true) {
            vertex = getVertex(label);
            vertex.makePost(text, multiplier);
            postCount++;
        }
    }

    /****************************************
    * NAME: addLike                         *
    * IMPORT: label, key                    *
    * EXPORT: none                          *
    * PURPOSE: increases likes on post      *
    ****************************************/
    public void addLike(String label, String text, String liker) {
        DSALinkedList likedBy;
        DSAGraphVertex vertex;
        boolean hasLiked;
        vertex = getVertex(label);
        likedBy = getLikedBy(label, text);

        //check if follower has already liked the post
        hasLiked = hasLiked(likedBy, liker);
        if (hasLiked == false) {
            vertex.addLike(text, liker);
            likeCount++;
        }
    }

    /****************************************
    * NAME: getPosts                        *
    * IMPORT: label                         *
    * EXPORT: temp.getPosts                 *
    * PURPOSE: returns posts                *
    ****************************************/
    public DSALinkedList getPosts(String label) {
        DSAGraphVertex vertex;
        vertex = getVertex(label);

        return vertex.getPosts();
    }

    /****************************************
    * NAME: getLikedBy                      *
    * IMPORT: label, inKey                  *
    * EXPORT: temp.getLikedBy               *
    * PURPOSE: gets post likers             *
    ****************************************/
    public DSALinkedList getLikedBy(String label, String inText) {
        DSAGraphVertex vertex;
        vertex = getVertex(label);

        return vertex.getLikedBy(inText);
    }

    /****************************************
    * NAME: removeVertex                    *
    * IMPORT: label                         *
    * EXPORT: none                          *
    * PURPOSE: removes a vertex             *
    ****************************************/
    public Object removeVertex(String label) {
        //long startTime, endTime, timeTaken;

        DSAGraphVertex vertex, temp, removeFollows;
        DSALinkedList follows;
        Object removedVal;

        vertex = getVertex(label);

        //startTime = System.nanoTime();
        removedVal = vertices.removeMiddle(vertex);

        //endTime = System.nanoTime();
        //timeTaken = (endTime - startTime) / 1000000;

        Iterator iter = vertices.iterator();
        while (iter.hasNext()) {
            temp = (DSAGraphVertex) iter.next();
            temp.removeFollowing(vertex);
        }
        verticeCount--;
        //System.out.println(timeTaken);
        return removedVal;
    }

    /****************************************
    * NAME: removeEdge                      *
    * IMPORT: label                         *
    * EXPORT: none                          *
    * PURPOSE: removes an edge              *
    ****************************************/
    public void removeEdge(String labelOne, String labelTwo) {
        DSAGraphVertex vertexOne, vertexTwo;

        vertexOne = getVertex(labelOne);
        vertexTwo = getVertex(labelTwo);

        if ((vertexOne != null && vertexTwo != null) && (isFollowing(labelOne, labelTwo) == true)) {
            vertexOne.removeFollowing(vertexTwo);
            vertexTwo.removeFollower(vertexOne);
        }
    }

    /****************************************
    * NAME: getFollowerCount                *
    * IMPORT: label                         *
    * EXPORT: none                          *
    * PURPOSE: gets follower count of label *
    ****************************************/
    public int getFollowerCount(String label) {
        DSAGraphVertex vertexOne;
        int count = 0;

        vertexOne = getVertex(label);

        if (vertexOne != null) {
            count = vertexOne.getFollowerCount();
        }
        return count;
    }

    /****************************************
    * NAME: hasLiked                        *
    * IMPORT: list, name, post              *
    * EXPORT: none                          *
    * PURPOSE: gets those who haved liked   *
    *          a post                       *
    ****************************************/
    public static boolean hasLiked(DSALinkedList likedBy, String name) {
        boolean liked = false;
        String temp;
        if (likedBy != null) {
            Iterator iter = likedBy.iterator();
            while (iter.hasNext()) {
                temp = String.valueOf(iter.next());
                if (temp.equals(name)) {
                    liked = true;
                }
            }
        }
        return liked;
    }
}