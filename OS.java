
public class OS {
	private int memory;
	private int memoryCapacity;
	private JobQueue myJobQueue;
	private CPU myCPU;
	private ReadyQueue myReadyQueue;
	private BlockedQueue myBlockedQueue;

	public OS(){
		myJobQueue = new JobQueue();
		myCPU = new CPU();
		myReadyQueue = new ReadyQueue();
		myBlockedQueue = new BlockedQueue();
		memory = 0;
		memoryCapacity = 10;
		loadJobs();
		run();
	}

	public int getMemory(){
		return memory;
	}

	private int setMemory(int memory){
		//returns -1 if memory was not reset
		if(memory <= memoryCapacity && memory >= 0){
			this.memory = memory;
			return 0;
		}
		return -1;
	}

	private int increaseMemory(){
		return setMemory(getMemory()+1);		
	}
	private int decreaseMemory(){
		return setMemory(getMemory()-1);		
	}
	public boolean hasMemory(){
		return (memoryCapacity-getMemory()) > 0;
	}

	public void jobQReadyQHandOff(){
		if(myJobQueue.hasGivableJobs(myCPU.getCycle()) && this.hasMemory()){
			myReadyQueue.enqueue(myJobQueue.getNextJob());
			increaseMemory();
		}
	}

	public PCB getNextReadyJob(){
		//might change this to specifially call a cpu load function and hand it off the pcb
		while(myJobQueue.hasGivableJobs(myCPU.getCycle()) && this.hasMemory()){
			jobQReadyQHandOff();
		}
		PCB rPCB = myReadyQueue.dequeue()
		rPCB.connectCPU(myCPU);
		return rPCB;	
	}

	public boolean canPassPCBToCPU(){
		return !myReadyQueue.isEmpty() || (myJobQueue.hasGivableJobs(myCPU.getCycle()) && this.hasMemory());
	}

	public void cpuBlockedQHandOff(PCB process){
		myBlockedQueue.enqueue(process);
	}
	public void completeProcess(PCB process){
		//print statistics
		decreaseMemory();
	}

	public void refreshBlocked(){
		myBlockedQueue.blockedTimer();
		// This works by having the blocked queue pass pcb's directly to the ready quequ, it should actually be that it passes them to the system first
		// we should also allow the passage of linked lists to the system.
	}
/*	public void blockedQReadyQHandOff(){
		
	}
*/
	public void loadJobs(String filename){
		JobQueue myJobQueue = new JobQueue();
		CPU myCPU = new CPU();
		ReadyQueue myReadyQueue = new ReadyQueue();
		BlockedQueue myBlockedQueue = new BlockedQueue();
		
		try {
			Scanner inFile = new Scanner(new FileReader(filename));
			
			while (inFile.hasNext()){
				String s = inFile.nextLine();
				myJobQueue.enqueue(s); 
			}
			inFile.close();
		}
		catch (FileNotFoundException e) {
			
			e.printStackTrace();
		}
				
		while (myReadyQueue.size()<2 && myJobQueue.hasGivableJobs(myCPU.getCycle()) && this.hasMemory()){
			jobQReadyQHandOff();
		}	
				
		System.out.println(myJobQueue.size());
		//System.out.println("The size of this ready Q is "+(myReadyQueue).size());
		
	}
	
	public void run(){
		// I want memory to be managed via the os so I need to change something here. 
		myCPU.Run(myJobQueue, myReadyQueue, myBlockedQueue);
		System.out.println(myBlockedQueue);
	}

}


