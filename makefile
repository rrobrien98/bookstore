JFLAGS = -g
JC = javac
.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $*.java

CLASSES = \
	  Frontend.java \
	  Catalog.java \
	  Book.java \
	  Client.java \
	  Order.java 
         
        

default: classes

classes: $(CLASSES:.java=.class)

clean:
	$(RM) *.class
