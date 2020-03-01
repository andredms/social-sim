# Social Simulator
A simulation which shows the propagation of information throughout a network for 'Data Structures and Algorithms' - achieved 98%. Investigation included. 

Date created: 28/10/19
Date last modified: 28/10/19

## Purpose
Simulate the spread of information in a social network

## File Explanations
- DSAGraph.java, implementation of a graph 
- DSAHeap.java, implementation of a heap
- DSALinkedList.java, implementation of a linked list 
- DSAPost.java, post class used to host posts
- DSAQueue.java, implementation of a queue
- SocialSim.java, kickstarts the program
- fileio.java, handles all file input and output
- genfiles.java, creates random network/event files
- userinterface.java, interface for interactive mode             
- simulation.java, hosts time step and various algorithms to support sorts 
- Makefile, used for compiling

## Test Files
genfiles.java, UnitTestGraph.java, UnitTestLinkedList.java, UnitTestQueue.java, UnitTestHeap.java

## Functionality
- Usage information: java SocialSim
- Interactive mode: java SocialSim -i
- Simulation mode: java SocialSim -s <network.txt> <events.txt> <likeProb> <followProb>

Generate networks and events through java genfiles <numPeople> <numConnections>

## Known bugs:
N/A
    
## Additional functionality 
- Added smart auto-generating networks/events
