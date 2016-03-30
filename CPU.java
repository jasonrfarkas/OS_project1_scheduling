

public class CPU {
	private boolean available;
	private int cycle;
	private PCB loadedPCB;
	OS myOS;

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
		myOS.resetQuantom();
		//System.out.println("in pop: pcb: " + returnPCB.toString());
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
		this.myOS = myOS;
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
			//This point will only be reached is there is a pcb in the CPU
			myOS.premtiveCheck();
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
			
		}
		System.out.println("Program complete");
	}

}
