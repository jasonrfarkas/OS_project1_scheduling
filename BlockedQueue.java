
public class BlockedQueue extends LinkedList{
    
    public void blockedTimer(ReadyQueue myRQ){/*int currentCycleTime,*/
		
		System.out.println("inside Blockedtimer method of BlockedeQueue");
		Node walker = this.head;
    	while (walker!= null && walker.getPcb().recheckIfWaiting() == 0){
            myRQ.enqueue(this.dequeue());
            walker = walker.getNext();
			//System.out.println("iter.next().getBlockedEndTime() = "+iter.next().getBlockedEndTime());
			////System.out.println("currentCycleTime = "+currentCycleTime);
		//	PCB current1 = head.pcb;
        //    while(current1.recheckIfWaiting() == 0){ // If a pcb is done waiting it returns 0
       //         myRQ.enqueue(this.dequeue());
       //         walker = walker.next;
       //     }
		}
        System.out.println("ending Blockedtimer method of BlockedeQueue");
	}
    
}