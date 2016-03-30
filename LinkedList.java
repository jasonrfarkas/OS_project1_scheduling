import java.util.NoSuchElementException;


public class LinkedList {
	protected Node head = null;
    protected Node tail = null;

    public void enqueue(PCB myPCB) {
        Node newNode = new Node(myPCB, null);
        if (isEmpty()) {
        	head = newNode;
        } 
        
        else {
        	tail.setNext(newNode);
        }
       
        tail = newNode;
    }

    public PCB dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        PCB myPCB = head.getPcb();
        if (tail == head) {
            tail = null;
        }
        head = head.getNext();
        return myPCB;
    }

    public PCB peek() {
        if (head == null) {
            throw new NoSuchElementException();
        }
        return head.getPcb();
    }

    public boolean isEmpty() {
        return head == null;
    }

    public int size() {
        int count = 0;
        for (Node node = head; node != null; node = node.getNext()) {
            count++;
        }
        return count;
    }
    
    public LinkedList insert(PCB myPCB, LinkedList sorted){
		
		Node myNode = new Node(myPCB, null);
		Node walker = sorted.head;
		
		if (sorted.isEmpty()) {
        	sorted.head = myNode;
        } 
		
		else while(walker.getNext() != null && myNode.getPcb().getJobId() > walker.getPcb().getJobId())
			walker = walker.getNext();
			  
		
		myNode.setNext(walker);
		walker = myNode;
	
		return sorted;
		
	}
    
    
    public String toString(){
    	
    	String myString ="";
    	Node walker = this.head;
    	while (walker!= null){
    		myString += walker.getPcb();
    		walker = walker.getNext();
    	}
    	return myString;
    }
}
