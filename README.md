# Social Sim

Date last modified: 28/10/19

Having more followers means having more influence, power and, in most cases, money. Through taking steps in time, one can observe the propagation of fake news, memes and posts within a network and observe how someone can gain popularity and stardom with a bit of luck and the right circumstances. The more followers someone has, the greater the chance of them being followed; however, this isn’t always the case. If someone posts something, and someone with a lot of followers likes it, they have the chance to potentially leapfrog the most popular person in the network. This will be expanded upon in the investigation of the results.

A time-step is essentially a 'step in time’. An event file is first loaded in, which allows nodes to be added/removed, people to post and people to follow/unfollow each other. After all these actions have been carried out, posts propagate themselves throughout the network, giving each person (who's following the poster), the chance to like it and spread it to all their followers. Running a simulation with a single-step approach ensures the spread of information is visualised better instead of recursing down and being carried out all at once.

The aspects of the code being investigated are the run-times of the time-step, the patterns revealed by the time-step, the correlation between likes/follows in a network, how differently sized networks scale, the rise and fall of most popular people in networks, and simple time comparisons for different methods in the simulation, such as followSort (similar algorithm to likeSort) and the removal of nodes in the graph. Insertion of people will not be investigated as the times reported were extremely trivial - O(1).

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
- Usage information: ``java SocialSim``
- Interactive mode: ``java SocialSim -i``
- Simulation mode: ``java SocialSim -s <network.txt> <events.txt> <likeProb> <followProb>``

Generate networks and events through java genfiles <numPeople> <numConnections>

## Known bugs:
N/A
    
## Additional functionality 
- Added smart auto-generating networks/events
