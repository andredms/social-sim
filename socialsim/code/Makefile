JFLAGS = -g
JC = javac
.SUFFIXES: .java .class
.java.class:
		$(JC) $(JFLAGS) $*.java

CLASSES = \
	fileio.java \
    userinterface.java \
	DSAHeap.java \
	DSAGraph.java \
	DSAPost.java \
	DSALinkedList.java \
	SocialSim.java \
	simulation.java \
	genfiles.java \
	UnitTestGraph.java \
	UnitTestHeap.java \
	UnitTestLinkedList.java \
	UnitTestQueue.java \

default: classes

classes: $(CLASSES:.java=.class)

clean:
		$(RM) *.class
