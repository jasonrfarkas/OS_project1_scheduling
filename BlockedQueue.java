

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