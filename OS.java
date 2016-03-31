/*
	This OS class is the central core of the project. 
	It works to replicate the role of an operating system
	Every movement of the PCB from one part of the system to 
	another part of the system will move through the os. 

	This class has attributes for functionallity and attributes 
	for keeping track of running statistics. 

	Funcional attributes: 
	 - memory: keeps track of the number of PCBs in system
	 - memoryCapacity: the maximum number of PCBs that can be in the system
	 - myJobQueue: holds all PCBs that have yet to enter the system
	 - myCPU: runs the functionality of the computer by bringing in PCB objects
	 		  and calling thier runline method and then calling the appropriate 
			  system methods. 
	- myReadyQueue: holds all PCB's that are in the ready state, in the appropriate 
					order as scheduled by the myScheduler
	- myBlockedQueue: holds all PCB's in the blocked state in the the order which
					  they left the CPU
	- myScheduler: Contains functions to organize the order of a Queue so that the 
					ready Queue states in the right order and also keeps track of 
					preemptive algorithms and calling the os functions to remove and 
					replace PCB units in that senario. 

	Statistics attributes:
	- completedJobsNumber: Holds the number of jobs that have enter the system and
						   been completed
	- sumJobTurnaroundTime: Holds the sum of the turn around time for every complete job
	- sumJobWaitingTime: Holds the sum of the waiting time for every complete job
	- sumJobProcessTime:Holds the sum of the proccessing time for every complete job

	
	Methods: 
	- Constructors: There are three constructors that accept parameters such that the first
					parameter is required and the second and third are optional parameters 
					that are used to set up the schuedling algorithm. The second paramter 
					is a number to specify which alogithm to use where 1 maps to First come 
					First serve. 2 maps to Shortest Job First. And 3 maps to Round Robin. 
					If Round Robin is used you can add an additional parameter to select 
					the quantom used, where the default is 10. 
	- Getters/Setters/Boolean checks: These are self explanitory. 
	- Handoffs: Functions that are mostly called from outside the system to move PCB between system elements
	- releaseCPU: pops the cpu's PCB
	- refeshBlocked: Tells the blocked queque to move everything it has in the ready state to the ready queque, 
					 In the order sorted by the scheduler
	- loadJobs: Reads a text file of PCB objects into the JobQ
	- completeProcess: dictates what happens when a process is finished
	- Printing functions: these print out statics as to how well the OS and the algorithm work
*/


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
	private Scheduler myScheduler;

	private int completedJobsNumber;
	private int sumJobTurnaroundTime;
	private int sumJobWaitingTime;
	private int sumJobProcessTime;

	public OS(String filename){ this(filename, 1); }
	public OS(String filename, int schedulingAlgorithmNumber){
		this(filename,schedulingAlgorithmNumber,-1);
	}
	public OS(String filename, int schedulingAlgorithmNumber, int quantomNumber){
		completedJobsNumber = 0;
		myJobQueue = new JobQueue();
		myCPU = new CPU();
		myReadyQueue = new ReadyQueue();
		myBlockedQueue = new BlockedQueue();
		myScheduler = new Scheduler(schedulingAlgorithmNumber, quantomNumber);
		memory = 0;
		memoryCapacity = 10;
		loadJobs(filename);
		run();
	}

	public boolean hasMemory(){ return (memoryCapacity-getMemory()) > 0; }
	public boolean canReceiveJobsFromJobQ(){ return myJobQueue.hasGivableJobs(myCPU.getCycle()) && this.hasMemory(); }
	public boolean canPassPCBToCPU(){ return (!myReadyQueue.isEmpty() || canReceiveJobsFromJobQ() ); }
	public int getCycle(){ return myCPU.getCycle(); }
	public boolean running(){  return !myJobQueue.isEmpty() || (getMemory() > 0); }
	public int getMemory(){ return memory; }
	public int getCompletedJobsNumber(){ return completedJobsNumber; }
	public int getSumJobTurnaroundTime(){ return sumJobTurnaroundTime; }
	public int getSumJobProcessTime(){ return sumJobProcessTime; }
	public int getSumJobWaitingTime(){
		// returns the sum of the amount of time a process wasn't in i/o or on cpu and was in system
		return sumJobTurnaroundTime - sumJobProcessTime;
	}

	private int setMemory(int memory){
		// Returns -1 if memory was not reset
		if(memory <= memoryCapacity && memory >= 0){
			this.memory = memory;
			return 0;
		}
		return -1;
	}
	private int increaseMemory(){ return setMemory(getMemory()+1); }
	private int decreaseMemory(){ return setMemory(getMemory()-1); }
	public void premtiveCheck(){ myScheduler.quantomCheck(this); }
	public void resetQuantom(){ myScheduler.resetQuantom(); }
	private PCB releaseCPU(){
		refreshBlocked();
		return myCPU.popLoadedPCB();
	}

	public void refreshBlocked(){
		// PCB's are passed to the 
		myScheduler.sort(myReadyQueue, myBlockedQueue.filterBlockedQueue(new Queue()));
	}

	public void run(){
		myCPU.Run(myJobQueue, myReadyQueue, myBlockedQueue, this);
		printFinalInfo();
	}

	public void loadJobs(String filename){
		this.myJobQueue = new JobQueue();
		this.myCPU = new CPU();
		this.myReadyQueue = new ReadyQueue();
		this.myBlockedQueue = new BlockedQueue();
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
		while (jobQReadyQHandOff()){ }	
	}
	
	public boolean jobQReadyQHandOff(){
		if(canReceiveJobsFromJobQ()){
			PCB p = myJobQueue.getNextJob();
			p.connectSystem(this);
			myScheduler.sortedAdd(myReadyQueue,p);
			increaseMemory();
			return true;
		}
		return false;
	}

	public boolean readyQCPUHandoff(){
		if(canPassPCBToCPU()){
			while(myJobQueue.hasGivableJobs(myCPU.getCycle()) && this.hasMemory()){
				jobQReadyQHandOff();
			}
			myCPU.setLoadedPCB(myReadyQueue.dequeue());
			return true;
		}
		return false;
	}

	public boolean cpuBlockedQHandOff(){
		if(myCPU.getLoadedPCB().blocked()){
			myBlockedQueue.enqueue(releaseCPU());
			return true;
		}
		return false;
	}

	public boolean cpuReadyQQuantomHandOff(){
		if(myCPU.getLoadedPCB().running()){
			System.out.println("Quantom Reached ");
		//	System.out.println("unloading pcb job: " + myCPU.getLoadedPCB().toString() );
			//myReadyQueue.enqueue(releaseCPU());
			myScheduler.sortedAdd(myReadyQueue,releaseCPU());
			return readyQCPUHandoff();
		}
		return false;
	}


	public void completeProcess(PCB process){
		decreaseMemory();
		completedJobsNumber +=1;
		sumJobTurnaroundTime+= (process.getCompletionTime()-process.getArrivalTime());
		sumJobProcessTime+= process.getTotalProcessTime();
		printJobCompleteInfo(process);
	}

	private void printJobCompleteInfo(PCB job){
		System.out.println("\nPrint completed Job Stats: ");
		System.out.println("JobID: " + job.getJobId() );
		System.out.println("ArrivalTime: " + job.getArrivalTime() );
		System.out.println("Completion Time: " + job.getCompletionTime());
		System.out.println("Processing Time: " + job.getTotalProcessTime() );
		System.out.println("Turnaround Time: " + (job.getCompletionTime()-job.getArrivalTime()) );
	}

	public void printFinalInfo(){
		System.out.println("\n\n\nPrint OS Stats: ");
		System.out.println("Scheduling Algorithm Used: "+ myScheduler.getSchedulingAlgorithm());
		System.out.println("Final CPU Clock: " + myCPU.getCycle() );
		System.out.println("AVG processing time: " + (getSumJobProcessTime()/getCompletedJobsNumber()) );
		System.out.println("AVG waiting time: "+ getSumJobWaitingTime()/getCompletedJobsNumber());
		System.out.println("AVG turnaround time: "+ getSumJobTurnaroundTime()/getCompletedJobsNumber());

	}

	public void printStats(){
		System.out.println("\n Printing statics: ");
		System.out.println("Number of jobs in the readyQ: " + myReadyQueue.size());
		System.out.println("Number of jobs in the blockedq: " + myBlockedQueue.size());
		System.out.println("Number of completed jobs: " + getCompletedJobsNumber());
	}
	
}


