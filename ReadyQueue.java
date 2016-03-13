import java.util.NoSuchElementException;


public class ReadyQueue {
    private class Node {
        public PCB pcb;
        public Node next;
        public Node(PCB myPCB, Node next) {
            this.pcb = myPCB;
            this.next = next;
        }
    }

    private Node head = null;
    private Node tail = null;

    public void enqueue(PCB myPCB) {
        Node newNode = new Node(myPCB, null);
        if (isEmpty()) {
        	newNode.pcb.setState("ready");
        	head = newNode;
        	
        } 
        else if (this.size() == 10){
        	
        	System.out.print("ReadyQueue is full!\n");
        	return;
        }
        else {
        	newNode.pcb.setState("ready");
        	tail.next = newNode;
        	
        }
       
        tail = newNode;
    }

    public PCB dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        PCB myPCB = head.pcb;
        if (tail == head) {
            tail = null;
        }
        head = head.next;
        return myPCB;
    }

    public PCB peek() {
        if (head == null) {
            throw new NoSuchElementException();
        }
        return head.pcb;
    }

    public boolean isEmpty() {
        return head == null;
    }

    public int size() {
        int count = 0;
        for (Node node = head; node != null; node = node.next) {
            count++;
        }
        return count;
    }
    
    public void insert(){
    	
    }
    
    public String toString(){
    	
    	String myString ="";
    	Node walker = this.head;
    	while (walker!= null){
    		myString += walker.pcb;
    		walker = walker.next;
    	}
    	return myString;
    }
    
}