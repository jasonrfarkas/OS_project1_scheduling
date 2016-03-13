
public class PCB {
	private int jobId;
	private int arrivalTime;
	private int cycleCounter;
	private int currentCPUBurst;
	private int totalCPUBursts;
	private int[] CPUBursts;
	private int completionTime;
	private int IOCompletionTime;
	private String state;
	
	private int blockedStartTime;
	private int blockedEndTime;
	
	public PCB(String jobDescription ){
		
		String []tempArray = jobDescription.split("\\s");
		
		jobId = Integer.valueOf(tempArray[0]);
		arrivalTime = Integer.valueOf(tempArray[1]);
		state = null;
		
		cycleCounter = 0;
		blockedStartTime = 0;
		blockedEndTime = 0;
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

	public int getCycleCounter() {
		return cycleCounter;
	}

	public void setCycleCounter(int cycleCounter) {
		this.cycleCounter = cycleCounter;
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

	public int getIOCompletionTime() {
		return IOCompletionTime;
	}

	public void setIOCompletionTime(int iOCompletionTime) {
		IOCompletionTime = iOCompletionTime;
	}
	
	public int getCurrentCPUBurst() {
		return currentCPUBurst;
	}

	public void setCurrentCPUBurst(int currentCPUBurst) {
		this.currentCPUBurst = currentCPUBurst;
	}
	
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
	}

	
	public String toString(){
		String myString = "";
		myString+="\n\nCurrent information of this PCB\n\n";
		myString+="jobId = "+jobId+"\narrivalTime = "+arrivalTime+"\ncycleCounter = "+cycleCounter;
		
		myString+="\ncurrentCPUBurst = "+currentCPUBurst+" \ntotalCPUBursts = "+totalCPUBursts+"\nCPUBursts : ";
		for (int i = 0; i<totalCPUBursts; i++)
			myString+=" "+ CPUBursts[i];
		
		myString+="\ncompletionTime = "+completionTime+"\nIOCompletionTime = "+IOCompletionTime+"\nstate = "+state;
		
		return myString;
	}

	
}
