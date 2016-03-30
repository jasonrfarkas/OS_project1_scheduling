

public class BlockedQueue extends Queue{
    
    public Queue filterBlockedQueue(Queue myQ){/*int currentCycleTime,*/
		
         // System.out.println(" BlockedeQueue string:" +this.toString());
		 // System.out.println("inside Blockedtimer method of BlockedeQueue + size:" +size());
		Node walker = this.head;
    	while (walker!= null && walker.getPcb().recheckIfWaiting() == 0){
            myQ.enqueue(this.dequeue());
            walker = walker.getNext();
		}
        // System.out.println("ending Blockedtimer method of BlockedeQueue");
        return myQ;
	}

    
}