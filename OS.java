
public class OS {
	private int memory;
	private JobQueue myJobQueue;
	private CPU myCPU;
	private ReadyQueue myReadyQueue;
	private BlockedQueue myBlockedQueue;

	public OS(){
		myJobQueue = new JobQueue();
		myCPU = new CPU();
		myReadyQueue = new ReadyQueue();
		myBlockedQueue = new BlockedQueue();
		loadJobs();
		run();
	}

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
				
		while (myReadyQueue.size()<2 && !myJobQueue.isEmpty()){
			PCB myPCB = myJobQueue.getNextJob();
			myReadyQueue.enqueue(myPCB);
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


