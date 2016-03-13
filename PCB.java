
public class PCB {
	private int jobId;
	private String state;
	private int simulated_pc;
	//program counter
	private int totalCPUBursts;
	private int[] CPUBursts;
	private int currentCPUBurst;
	private int iOCompletionTime;

	private int arrivalTime;
	private int cycleCounter; // I think this is the same as the simulated_pc

	
	
	
	//private int completionTime;
	
	//private int blockedStartTime;
	//private int blockedEndTime;
	
	public PCB(String jobDescription ){
		
		String []tempArray = jobDescription.split("\\s");
		
		jobId = Integer.valueOf(tempArray[0]);
		arrivalTime = Integer.valueOf(tempArray[1]);
		state = null;
		
		cycleCounter = 0;
		// blockedStartTime = 0;
		// blockedEndTime = 0;
		iOCompletionTime= 0;
		setCurrentCPUBurst(0);
		
		totalCPUBursts = Integer.valueOf(tempArray[2]);
		
		
		CPUBursts = new int [totalCPUBursts];
		for (int i = 0; i<totalCPUBursts; i++){
			CPUBursts[i] = Integer.valueOf(tempArray[i+3]);
		}
	}

	public int getJobId() {
		return jobId;
	}

	public void setJobId(int jobId) {
		this.jobId = jobId;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public void setState(int cpuCycle) {
		//ready, running, blocked
	}

	public int getCycleCounter() {
		return cycleCounter;
	}

	public void setCycleCounter(int cycleCounter) {
		this.cycleCounter = cycleCounter;
	}

	public int getSimulated_pc(){
		return simulated_pc;
	}

	public void setSimulated_pc(int simulated_pc){
		if(simulated_pc > this.simulated_pc){
			this.simulated_pc = simulated_pc;
		}
	}

	public int getTotalCPUBursts() {
		return totalCPUBursts;
	}

	public void setTotalCPUBursts(int totalCPUBursts) {
		this.totalCPUBursts = totalCPUBursts;
	}

	public int[] getCPUBursts() {
		return CPUBursts;
	}

	public void setCPUBursts(int[] cPUBursts) {
		CPUBursts = cPUBursts;
	}

	public int getCompletionTime() {
		return completionTime;
	}

	public void setCompletionTime(int completionTime) {
		this.completionTime = completionTime;
	}

	public int getiOCompletionTime() {
		return iOCompletionTime;
	}

	public void setiOCompletionTime(int iOCompletionTime) {
		iOCompletionTime = iOCompletionTime;
	}
	
	public int getCurrentCPUBurst() {
		return currentCPUBurst;
	}

	public void setCurrentCPUBurst(int currentCPUBurst) {
		this.currentCPUBurst = currentCPUBurst;
	}
	
/*	
	We should only be using iocompletion

	public int getBlockedStartTime() {
		return blockedStartTime;
	}

	public void setBlockedStartTime(int blockedStartTime) {
		this.blockedStartTime = blockedStartTime;
	}

	public int getBlockedEndTime() {
		return blockedEndTime;
	}

	public void setBlockedEndTime(int blockedEndTime) {
		this.blockedEndTime = blockedEndTime;
	}*/

	
	public String toString(){
		String myString = "";
		myString+="\n\nCurrent information of this PCB\n\n";
		myString+="jobId = "+jobId+"\narrivalTime = "+arrivalTime+"\ncycleCounter = "+cycleCounter;
		
		myString+="\ncurrentCPUBurst = "+currentCPUBurst+" \ntotalCPUBursts = "+totalCPUBursts+"\nCPUBursts : ";
		for (int i = 0; i<totalCPUBursts; i++)
			myString+=" "+ CPUBursts[i];
		
		myString+="\ncompletionTime = "+completionTime+"\niOCompletionTime = "+iOCompletionTime+"\nstate = "+state;
		
		return myString;
	}

	
}
