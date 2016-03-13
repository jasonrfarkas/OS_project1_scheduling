

public class CPU {
	private boolean available;
	private int cycle;
    public CPU (){
    	setAvailable(true);
    	setCycle(0);
    }
	public boolean isAvailable() {
		return available;
	}
	public void setAvailable(boolean available) {
		this.available = available;
	}
	
	public int getCycle() {
		return cycle;
	}
	public void setCycle(int cycle) {
		this.cycle = cycle;
	}

	public void Run(JobQueue myJobQueue, ReadyQueue myReadyQueue, BlockedQueue myBlockedQueue){
		System.out.println("inside Run method of CPU class");
		System.out.println("After time cycle of "+cycle);
		System.out.println("printing myReadyQueue: "+myReadyQueue);
		System.out.println("printing myBlockedQueue: "+myBlockedQueue);
		
		
		while(true){
			
			if()

			// after edits

			//checking if ReadyQueue is empty or not
			if (!myReadyQueue.isEmpty()){
				
				System.out.println("(!myReadyQueue.isEmpty())");
				PCB currentPCB = myReadyQueue.dequeue();
				
				int currentCPUBurst = currentPCB.getCurrentCPUBurst();
				
				//checking if current CPUBurst is the last CPUBurst or not
				if (currentCPUBurst >= currentPCB.getTotalCPUBursts()){
					
					System.out.println("(currentCPUBurst >= currentPCB.getTotalCPUBursts()");
					//System.out.println("myReadyQueue"+myReadyQueue);
					//System.out.println("myBlockedQueue"+myBlockedQueue);
					//System.out.println(myJobQueue.isEmpty());
					if (myReadyQueue.isEmpty() && myJobQueue.isEmpty() && myBlockedQueue.isEmpty()){
						System.out.println("myReadyQueue.isEmpty() && myJobQueue.isEmpty() && myBlockedQueue.isEmpty())");
					}
					else if (myReadyQueue.isEmpty() && !myJobQueue.isEmpty()){
						System.out.println("(myReadyQueue.isEmpty() && !myJobQueue.isEmpty())");
						myReadyQueue.enqueue(myJobQueue.getNextJob());
						currentPCB = myReadyQueue.dequeue();
						currentCPUBurst = currentPCB.getCurrentCPUBurst();
					}
					else if (myReadyQueue.isEmpty() && myJobQueue.isEmpty() && !myBlockedQueue.isEmpty()){
						System.out.print("I am here    kjshkdhahd");
						
						int waitTime = myBlockedQueue.peek().getBlockedEndTime()-cycle;
						cycle += waitTime;
						myBlockedQueue.BlockedTimer(cycle,myReadyQueue);
						
					}
				}
				if (currentCPUBurst < currentPCB.getTotalCPUBursts()){
					System.out.println("(currentCPUBurst < currentPCB.getTotalCPUBursts())");
					cycle += currentPCB.getCPUBursts()[currentCPUBurst];
					currentPCB.setCurrentCPUBurst(currentCPUBurst+1);
				
					
					if (currentPCB.getCurrentCPUBurst() >= currentPCB.getTotalCPUBursts()){
						System.out.println("(currentCPUBurst >= currentPCB.getTotalCPUBursts()");
						
						
						System.out.println("myReadyQueue"+myReadyQueue);
						System.out.println("myBlockedQueue"+myBlockedQueue);
						System.out.println(myJobQueue.isEmpty());
						
						if (myReadyQueue.isEmpty() && myJobQueue.isEmpty() && myBlockedQueue.isEmpty()){
							System.out.println("myReadyQueue.isEmpty() && myJobQueue.isEmpty() && myBlockedQueue.isEmpty())");
							return;
						}
						else if (myReadyQueue.isEmpty() && !myJobQueue.isEmpty()){
							System.out.println("(myReadyQueue.isEmpty() && !myJobQueue.isEmpty())");
							myReadyQueue.enqueue(myJobQueue.getNextJob());
							currentPCB = myReadyQueue.dequeue();
							currentCPUBurst = currentPCB.getCurrentCPUBurst();
						}
						else if (myReadyQueue.isEmpty() && myJobQueue.isEmpty() && !myBlockedQueue.isEmpty()){
							System.out.print("I am here    kjshkdhahd");
							
							int waitTime = myBlockedQueue.peek().getBlockedEndTime()-cycle;
							cycle += waitTime;
							myBlockedQueue.BlockedTimer(cycle,myReadyQueue);
							
						}
					}
					
					currentPCB.setBlockedStartTime(cycle);
					currentPCB.setBlockedEndTime(cycle+10);
					myBlockedQueue.enqueue(currentPCB);
					myBlockedQueue.BlockedTimer(cycle,myReadyQueue);
				
					System.out.println("\nAfter time cycle of "+cycle);
					System.out.println("printing myReadyQueue: "+myReadyQueue);
					System.out.println("printing myBlockedQueue: "+myBlockedQueue);
				}
			}
			if (myReadyQueue.isEmpty() && !myBlockedQueue.isEmpty()){
				int waitTime = myBlockedQueue.peek().getBlockedEndTime()-cycle;
				cycle += waitTime;
				myBlockedQueue.BlockedTimer(cycle,myReadyQueue);
			}
		}
	}
}
