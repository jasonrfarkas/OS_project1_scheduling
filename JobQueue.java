
import java.util.NoSuchElementException;


public class JobQueue {
	
	
	private class Node {
        public String myString;
        public Node next;
        public Node(String s, Node next) {
            this.myString = s;
            this.next = next;
        }
    }
	
	 private Node head = null;
	 private Node tail = null;


	public boolean isEmpty() {
        return head == null;
    }

	
	public void enqueue(String s) {
        Node newNode = new Node(s, null);
        if (isEmpty()) { 	
        	head = newNode;
        	
        }
        else 
        	tail.next = newNode;
        tail = newNode;
	}
	
	public String dequeue() {
        if (isEmpty()) {
        	///System.out.println("All job has done.");
            throw new NoSuchElementException();
        }
        String myString = head.myString;
        if (tail == head) {
            tail = null;
        }
        head = head.next;
        return myString;
    }
	
	 public int size() {
	        int count = 0;
	        for (Node node = head; node != null; node = node.next) {
	            count++;
	        }
	        return count;
	 }

	private PCB peekNextJob(){
        return new PCB(this.head.myString);
    } 

	public PCB getNextJob(){
		return new PCB(this.dequeue());
	}

    public boolean hasGivableJobs(int cpuTime){
        //System.out.println("!isEmpty() is " + !isEmpty());
        /////System.out.println("(peekNextJob().getArrivalTime() <= cpuTime) " + (peekNextJob().getArrivalTime() <= cpuTime));
        return !isEmpty() && (peekNextJob().getArrivalTime() <= cpuTime);
    }
        
}
