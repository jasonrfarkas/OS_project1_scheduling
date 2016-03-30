import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class OS {
	private int memory;
	private int memoryCapacity;
	private JobQueue myJobQueue;
	private CPU myCPU;
	private ReadyQueue myReadyQueue;
	private BlockedQueue myBlockedQueue;

/*	public OS(){
		myJobQueue = new JobQueue();
		myCPU = new CPU();
		myReadyQueue = new ReadyQueue();
		myBlockedQueue = new BlockedQueue();
		memory = 0;
		memoryCapacity = 10;
		loadJobs("");
		run();
	}*/
	public OS(String filename){
		
		
		
		myJobQueue = new JobQueue();
		myCPU = new CPU();
		myReadyQueue = new ReadyQueue();
		myBlockedQueue = new BlockedQueue();
		memory = 0;
		memoryCapacity = 10;
		loadJobs(filename);
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

	public boolean jobQReadyQHandOff(){

		LinkedList testList = new LinkedList();
		
		
		//System.out.println("myJobQueue.hasGivableJobs(myCPU.getCycle())= " + myJobQueue.hasGivableJobs(myCPU.getCycle()));
		//System.out.println("this.hasMemory()= " +this.hasMemory()); 
		if(myJobQueue.hasGivableJobs(myCPU.getCycle()) && this.hasMemory()){
			PCB p = myJobQueue.getNextJob();
			p.connectSystem(this);
			myReadyQueue.enqueue(p);
			
			testList.insert(p, testList);
			//System.out.println("here is my test list  >>>"+testList);
			
			increaseMemory();
			return true;
		}
		return false;
	}

	public boolean readyQCPUHandoff(){
		if(canPassPCBToCPU()){
			while(myJobQueue.hasGivableJobs(myCPU.getCycle()) && this.hasMemory()){
				System.out.println("handing off a job");
				jobQReadyQHandOff();
				//System.out.println("\nmy current readyQ"+myReadyQueue);
				
			}
			myCPU.setLoadedPCB(myReadyQueue.dequeue());
			return true;
		}
		return false;
	}

/*	public PCB getNextReadyJob(){
		//System.out.println("entering while loop to check if job q has jobs");
		//might change this to specifially call a cpu load function and hand it off the pcb
		while(myJobQueue.hasGivableJobs(myCPU.getCycle()) && this.hasMemory()){
			//System.out.println("handing off a job");
			jobQReadyQHandOff();
		}
		// System.out.println("dequeueing from readyQ");
		PCB rPCB = myReadyQueue.dequeue();
		// System.out.println("connecting cpu to pcb");
		//rPCB.connectSystem();
		//System.out.println("returning pcb");
		return rPCB;	
	}*/

	public boolean canPassPCBToCPU(){
		return !myReadyQueue.isEmpty() || (myJobQueue.hasGivableJobs(myCPU.getCycle()) && this.hasMemory());
	}

	public boolean cpuBlockedQHandOff(){
		if(myCPU.getLoadedPCB().blocked()){
			myBlockedQueue.enqueue(myCPU.popLoadedPCB());
			return true;
		}// Maybe this should then throw an error if not 
		return false;
	}
	public void completeProcess(PCB process){
		//print statistics
		decreaseMemory();
	}

	public void refreshBlocked(){
		myBlockedQueue.blockedTimer(myReadyQueue);
		// This works by having the blocked queue pass pcb's directly to the ready quequ, it should actually be that it passes them to the system first
		// we should also allow the passage of linked lists to the system.
	}
/*	public void blockedQReadyQHandOff(){
		
	}
*/
	public void loadJobs(String filename){
		//System.out.println("trying to load jobs from " + filename);
		this.myJobQueue = new JobQueue();
		this.myCPU = new CPU();
		this.myReadyQueue = new ReadyQueue();
		this.myBlockedQueue = new BlockedQueue();
		
		
		try {
			Scanner inFile = new Scanner(new FileReader(filename));
			while (inFile.hasNext()){
				//System.out.println("inFile.hasNext() ");
				String s = inFile.nextLine();
				myJobQueue.enqueue(s); 
				//System.out.println("loaded something into jobQ ");
			}
			inFile.close();
		}
		catch (FileNotFoundException e) {
			//System.out.println("caught exception");
			e.printStackTrace();
		}
		
		//System.out.println("handing off jobs to readyQ");	
		while (jobQReadyQHandOff()){
			//System.out.println("handing off a job");	
			//System.out.println("myReadyQueue.size()= " +myReadyQueue.size());
			//System.out.println("myJobQueue.hasGivableJobs(myCPU.getCycle())= " +myJobQueue.hasGivableJobs(myCPU.getCycle()) );
			
		}	
				
		//System.out.println("my Current ReadyQ"+ myReadyQueue);
		////System.out.println("The size of this ready Q is "+(myReadyQueue).size());
		
	}
	
	public int getCycle(){
		return myCPU.getCycle();
	}

	public void run(){
		// I want memory to be managed via the os so I need to change something here. 
		myCPU.Run(myJobQueue, myReadyQueue, myBlockedQueue, this);
		//System.out.println(myBlockedQueue);
	}

	public boolean running(){
		// System.out.println("!myJobQueue.isEmpty() = " + !myJobQueue.isEmpty() + " getMemory() = " + getMemory());
		// System.out.println("System is running");
		return !myJobQueue.isEmpty() || (getMemory() > 0);
	}
}


