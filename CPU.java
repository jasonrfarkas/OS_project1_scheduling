

public class CPU {
	private boolean available;
	private int cycle;
	private PCB loadedPCB;

    public CPU (){
    	available = true;
    	setCycle(0);
    	loadedPCB = null;
    }
	public boolean isAvailable() {
		return available;
	}
	public PCB getLoadedPCB(){
		return loadedPCB;
	}
	public int setLoadedPCB(PCB loadedPCB) {
		if(isAvailable()){
			this.loadedPCB = loadedPCB;
			this.available = false;
			return 0;
		}
		else{
			return -1;
		}
	}
	
	public PCB popLoadedPCB() {
		// not sure what will happen if !isAvailable()
		System.out.println("\n POPPING PCB");
		PCB returnPCB= loadedPCB;
		loadedPCB = null;
		this.available = true;
		return returnPCB;
	}

	public PCB contextSwitch(PCB loadPCB) {
		PCB returnPCB = popLoadedPCB();
		setLoadedPCB(loadPCB);
		return returnPCB;
	}
	
	public int getCycle() {
		return cycle;
	}
	private void setCycle(int cycle) {
		this.cycle = cycle;
	}
	private void increaseCycle(){
		setCycle(getCycle()+1);
	}

	private void printStats(OS myOS){
		myOS.printStats();
	}

	public void Run(JobQueue myJobQueue, ReadyQueue myReadyQueue, BlockedQueue myBlockedQueue, OS myOS){
		//System.out.println("inside Run method of CPU class");
		//System.out.println("After time cycle of "+cycle);
		//System.out.println("printing myReadyQueue: "+myReadyQueue);
		//System.out.println("printing myBlockedQueue: "+myBlockedQueue);
		increaseCycle();
		while(myOS.running()){
			if(getCycle() %200 == 0 ){
				printStats(myOS);
			}
			//System.out.println("my os is running");
			System.out.println("current CPU cycle is  "+getCycle());
			if(isAvailable()){
				System.out.println("space is availible");
				myOS.refreshBlocked();
			//	System.out.println("blockedQ is refreshed");
				if(myOS.readyQCPUHandoff()){
					// the check has the side effect of working if it succeeded
			//		System.out.println("Getting item fom readyQ");
					//setLoadedPCB(myOS.getNextReadyJob());
					System.out.println("Got item from readyQ to CPU");
					//increaseCycle();
					
				}
				else{
					System.out.println("no items to get from ready/jobQ");
					increaseCycle();
					// System.out.println("inside else increase cycle: current CPU cycle is  "+getCycle());
					continue;
				}
			}
			//else if() Quantom is reached
			System.out.println("running line of code in pcb");
			int statusCode=loadedPCB.runLine(); //runs a line of code and returns state of PCB, We could change the code to use this
			if (statusCode > 0){
			
			}
			//System.out.println("checking if loadedPCB is completed");
			if(loadedPCB.blocked()){
				System.out.println("pcb burst is finished");
				myOS.cpuBlockedQHandOff();
				// System.out.println("my current blockedQ "+myBlockedQueue);
			}
			else if(loadedPCB.completed()){
				System.out.println("pcb is finished");
				myOS.completeProcess(popLoadedPCB());
			}
			
			increaseCycle();
			
/*
System.out.println("my os is running");
			if(isAvailable()){
				System.out.println("space is availible");
				myOS.refreshBlocked();
				System.out.println("blockedQ is refreshed");
				if(myOS.readyQCPUHandoff()){
					// the check has the side effect of working if it succeeded
			//		System.out.println("Getting item fom readyQ");
					//setLoadedPCB(myOS.getNextReadyJob());
					System.out.println("Got item fom readyQ to CPU");
					//increaseCycle();
					
				}
				else{
					System.out.println("no items to get from ready/jobQ");
					increaseCycle();
					System.out.println("current CPU cycle is  "+getCycle());
					//System.out.println("inside else increase cycle: current CPU cycle is  "+getCycle());
					continue;
				}
			}
			else{
				System.out.println("Working with loaded PCB");
			}
			increaseCycle();
			System.out.println("current CPU cycle is  "+getCycle());
			//else if() Quantom is reached
			int statusCode=loadedPCB.runLine(); //runs a line of code and returns state of PCB, We could change the code to use this
			System.out.println("ran a line of code");
			if (statusCode < 0){
				System.out.println("ERROR running PCB line of code");
				//increaseCycle();
			}
			System.out.println("checking if loadedPCB is completed");
			if(loadedPCB.completed()){ // same as statusCode==0
				System.out.println("pcb is finished");
				myOS.completeProcess(popLoadedPCB());
			}
			else if(loadedPCB.blocked()){
				System.out.println("pcb BURST is finished");
				//increaseCycle(); // must be before handoff so that ready time is correct
				myOS.cpuBlockedQHandOff();
				// System.out.println("my current blockedQ "+myBlockedQueue);
			}
			else{
				System.out.println("Line ran normally");
			}
			System.out.println("next cycle");
*/
			/*//checking if ReadyQueue is empty or not
			if (!myReadyQueue.isEmpty()){
				// //System.out.println("(!myReadyQueue.isEmpty())");
				PCB currentPCB = myReadyQueue.dequeue();
				int currentCPUBurst = currentPCB.getCurrentCPUBurst();
				//checking if current CPUBurst is the last CPUBurst or not
				if (currentCPUBurst >= currentPCB.getTotalCPUBursts()){
					
					//System.out.println("(currentCPUBurst >= currentPCB.getTotalCPUBursts()");
					////System.out.println("myReadyQueue"+myReadyQueue);
					////System.out.println("myBlockedQueue"+myBlockedQueue);
					////System.out.println(myJobQueue.isEmpty());
					if (myReadyQueue.isEmpty() && myJobQueue.isEmpty() && myBlockedQueue.isEmpty()){
						//System.out.println("myReadyQueue.isEmpty() && myJobQueue.isEmpty() && myBlockedQueue.isEmpty())");
					}
					else if (myReadyQueue.isEmpty() && !myJobQueue.isEmpty()){
						//System.out.println("(myReadyQueue.isEmpty() && !myJobQueue.isEmpty())");
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
					//System.out.println("(currentCPUBurst < currentPCB.getTotalCPUBursts())");
					cycle += currentPCB.getCPUBursts()[currentCPUBurst];
					currentPCB.setCurrentCPUBurst(currentCPUBurst+1);
				
					
					if (currentPCB.getCurrentCPUBurst() >= currentPCB.getTotalCPUBursts()){
						//System.out.println("(currentCPUBurst >= currentPCB.getTotalCPUBursts()");
						
						
						//System.out.println("myReadyQueue"+myReadyQueue);
						//System.out.println("myBlockedQueue"+myBlockedQueue);
						//System.out.println(myJobQueue.isEmpty());
						
						if (myReadyQueue.isEmpty() && myJobQueue.isEmpty() && myBlockedQueue.isEmpty()){
							//System.out.println("myReadyQueue.isEmpty() && myJobQueue.isEmpty() && myBlockedQueue.isEmpty())");
							return;
						}
						else if (myReadyQueue.isEmpty() && !myJobQueue.isEmpty()){
							//System.out.println("(myReadyQueue.isEmpty() && !myJobQueue.isEmpty())");
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
				
					//System.out.println("\nAfter time cycle of "+cycle);
					//System.out.println("printing myReadyQueue: "+myReadyQueue);
					//System.out.println("printing myBlockedQueue: "+myBlockedQueue);
				}
			}
			if (myReadyQueue.isEmpty() && !myBlockedQueue.isEmpty()){
				int waitTime = myBlockedQueue.peek().getBlockedEndTime()-cycle;
				cycle += waitTime;
				myBlockedQueue.BlockedTimer(cycle,myReadyQueue);
			}*/
		}
		System.out.println("Program complete");
	}


}
