import java.util.NoSuchElementException;


public class BlockedQueue {
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
        	head = newNode;
        	
        } 
        
        else {
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
    
    
    public String toString(){
    	
    	String myString ="";
    	Node walker = this.head;
    	while (walker!= null){
    		myString += walker.pcb;
    		walker = walker.next;
    	}
    	return myString;
    }
    
    public void blockedTimer(ReadyQueue myRQ){/*int currentCycleTime,*/
		System.out.println(" BlockedeQueue string:" +this.toString());

		System.out.println("inside Blockedtimer method of BlockedeQueue + size:" +size());

        int counter= 0;
		Node walker = this.head;
        //System.out.println("walker!= null: " +(walker!= null));
        //System.out.println("walker.pcb.recheckIfWaiting(): " +(walker.pcb.recheckIfWaiting()));
    	while (walker!= null && walker.pcb.recheckIfWaiting() == 0){
            System.out.println("counter: " + counter);
            myRQ.enqueue(this.dequeue());
            // walker = walker.next;
            walker = this.head;
			//System.out.println("iter.next().getBlockedEndTime() = "+iter.next().getBlockedEndTime());
			//System.out.println("currentCycleTime = "+currentCycleTime);
		//	PCB current1 = head.pcb;
        //    while(current1.recheckIfWaiting() == 0){ // If a pcb is done waiting it returns 0
       //         myRQ.enqueue(this.dequeue());
       //         walker = walker.next;
        //    }
		}
        System.out.println("ending Blockedtimer method of BlockedeQueue");
	}
    
}