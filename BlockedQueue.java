

public class BlockedQueue extends LinkedList{
    
    public void blockedTimer(ReadyQueue myRQ){/*int currentCycleTime,*/
		
         // System.out.println(" BlockedeQueue string:" +this.toString());
		 // System.out.println("inside Blockedtimer method of BlockedeQueue + size:" +size());
		Node walker = this.head;
    	while (walker!= null && walker.getPcb().recheckIfWaiting() == 0){
            myRQ.enqueue(this.dequeue());
            walker = walker.getNext();
		}
        // System.out.println("ending Blockedtimer method of BlockedeQueue");
	}
    
}