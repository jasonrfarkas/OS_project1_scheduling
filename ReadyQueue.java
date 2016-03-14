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
    //private int memory_capacity = 10;
    //public int in_memory = 0;
    private int capacity= 10;
    private int size = 0;

    public void enqueue(PCB myPCB) {
        Node newNode = new Node(myPCB, null);
        if (isEmpty()) {
        	newNode.pcb.setState("ready");
        	head = newNode;
           // in_memory+=1;
        	
        } 
        else if (this.size() == capacity){
        	
        	System.out.print("ReadyQueue is full!\n");
        	return;
        }
        else {
        	newNode.pcb.setState("ready");
        	tail.next = newNode;
            //in_memory+=1;
        	
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
        //System.out.println("size is " + size);
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