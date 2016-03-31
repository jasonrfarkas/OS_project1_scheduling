/*BlockedQueue class extends from Queue class which extends from LinkedList class.
 *filterBlockedQueue method iterate through the BlockedQueue and checks  to see which processes
 *have completed their I/O and returns a Queue of processes that have completed their I/O  
 */

public class BlockedQueue extends Queue{
    
    public Queue filterBlockedQueue(Queue myQ){
    	
		Node walker = this.head;
    	while (walker!= null && walker.getPcb().recheckIfWaiting() == 0){
            myQ.enqueue(this.dequeue());
            walker = walker.getNext();
		}
        
        return myQ;
	}

    
}