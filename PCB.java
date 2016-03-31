/*
 * This class holds all the information that a pcb needs to have according to the job description, while creating a pcb object
 */


public class PCB {
	private int jobId;
	private String state;
	private int simulatedPc; 
	private int totalCPUBursts;
	private int[] CPUBursts; 
	private int currentCPUBurst;
	private int iOCompletionTime;
	private OS system; 

	private int arrivalTime;
	
	private int completionTime; 
	private int remainingInCurrentBurst;
	
	public int getArrivalTime(){
		return arrivalTime;
	}

	public void connectSystem(OS s){
		system = s;
	}

	public int getTotalProcessTime(){
		int time = CPUBursts[0];
		for(int i=1;i< totalCPUBursts;i++){
			time += CPUBursts[i] + 10;
		}
		return time;
	}
	
	public boolean systemConneted(){
		return system != null;
	}
	public int getSystemTime(){
		if(systemConneted()){
			return system.getCycle();
		}
		return -1;
	}

	public PCB(String jobDescription ){
		
		String []tempArray = jobDescription.split("\\s");
		
		jobId = Integer.valueOf(tempArray[0]);
		arrivalTime = Integer.valueOf(tempArray[1]);
		state = null;
		iOCompletionTime= 0;
		setCurrentCPUBurst(0);
		simulatedPc=0;
		currentCPUBurst = 0;
		
		totalCPUBursts = Integer.valueOf(tempArray[2]);
		
		CPUBursts = new int [totalCPUBursts];
		for (int i = 0; i<totalCPUBursts; i++){
			CPUBursts[i] = Integer.valueOf(tempArray[i+3]);
		
		}
		remainingInCurrentBurst = getCurrentCPUBurstLength();
	}

	public int getJobId() { return jobId; }
	public String getState() { return state; }
	public int getSimulated_pc(){ return simulatedPc; }
	public int getTotalCPUBursts() { return totalCPUBursts; }
	public int[] getCPUBursts() { return CPUBursts; }
	public int getCurrentCPUBurst() { return currentCPUBurst; }
	public int getRemainingInBurst(){ return remainingInCurrentBurst; }
	public int getCompletionTime() { return completionTime; }
	public int getiOCompletionTime() { return iOCompletionTime; }
	public int getCurrentCPUBurstLength(){
		if(getCurrentCPUBurst() < getTotalCPUBursts()){
			return getCPUBursts()[getCurrentCPUBurst()];
		}
		else{
			return getCPUBursts()[getTotalCPUBursts()-1];
		}
	}

	public void setJobId(int jobId) { this.jobId = jobId; }
	public boolean ready() { return this.getState() == "ready"; }
	public boolean blocked() { return this.getState() == "blocked" ; }
	public boolean running() { return this.getState() == "running"; }
	public boolean completed() {
		return this.getState() == "completed" || this.getCurrentCPUBurst() >= this.getTotalCPUBursts();
	}

	public void setState(String state) { this.state = state; }
	private void setTotalCPUBursts(int totalCPUBursts) { this.totalCPUBursts = totalCPUBursts; }
	public void setCPUBursts(int[] cPUBursts) { CPUBursts = cPUBursts; }
	public void setCurrentCPUBurst(int currentCPUBurst) { this.currentCPUBurst = currentCPUBurst; }
	private void setRemainingInCurrentBurst(int r){ this.remainingInCurrentBurst = r; }
	public void setCompletionTime(int completionTime) { this.completionTime = completionTime; }

	private void setSimulated_pc(int simulatedPc){
		if(simulatedPc > this.simulatedPc){
			this.simulatedPc = simulatedPc;
		}
	}

	private void setiOCompletionTime(int iOCompletionTime) {
		if(iOCompletionTime >= 0){
			this.iOCompletionTime = iOCompletionTime;
		}
	}

	public void setWaitTime(int waitTime){
		if(systemConneted()){
			setState("blocked");
			setiOCompletionTime(getSystemTime()+1 +waitTime ); //must account for the current cycle
		}
	}
	
	public int recheckIfWaiting(){
		/*
			Returns -1 if still waiting
			Returns 0 if done waiting and in ready state
			Returns -2 if the method should not have been called
		*/
		if(systemConneted() && blocked() ){
			if(getSystemTime() >= getiOCompletionTime()){
				setState("ready");
				setiOCompletionTime(0);
				return 0;
			}
			return -1;
		}
		return -2;
	}

	private void increasePC(){ 
		setSimulated_pc(getSimulated_pc()+1);
		setRemainingInCurrentBurst(getRemainingInBurst()-1);
		if( getRemainingInBurst() <= 0 ){
			this.increaseBurst();
		}
	}

	private void increaseBurst(){
		setCurrentCPUBurst(getCurrentCPUBurst()+1);
		if(this.getCurrentCPUBurst() >= this.getTotalCPUBursts()){
			setState("finished");
			setCompletionTime(getSystemTime()); 
		}
		else{
			setWaitTime(10);
			setRemainingInCurrentBurst(getCurrentCPUBurstLength());
		}
	}
	

	public int runLine(){
		/*
			This program simulates running a line of code in the program
			It returns -2 if the program is already completed
			It returns -1 if the program can't run because it is still waiting for io
			It returns 0 if the program finishes after running the line
			It returns 1 if the program ran one line successfully
			It returns 2 if the program ran one line and finished the burst
			After running the line of code the state will be changed accordingly
		*/
		if(this.completed()){ 
			return -2;
		}
		else if(this.blocked()){
			return -1;
		}
		else if(systemConneted()){
			/*
				Assuming this function is only called when pcb is on a cpu, 
				If we have more time we should add functions to ensure it can only run if it is on a cpu	
			*/
			setState("running");
		
			increasePC();
			//assumeNextState();
			if(this.completed()){
				return 0;
			}
			if(this.blocked()){
				return 2;
			}
			return 1;
		}
		else{
			return -3;
		}
	}
	
	public String toString(){
		String myString = "";
		myString+="\n\nCurrent information of this PCB\n\n";
		myString+="jobId = "+jobId+"\narrivalTime = "+arrivalTime+"\nsimulatedPc = "+simulatedPc;
		
		myString+="\ncurrentCPUBurst = "+currentCPUBurst+" \ntotalCPUBursts = "+totalCPUBursts+"\nCPUBursts : ";
		for (int i = 0; i<totalCPUBursts; i++)
			myString+=" "+ CPUBursts[i];
		
		myString+="\ncompletionTime = "+completionTime+"\niOCompletionTime = "+iOCompletionTime+"\nstate = "+state;
		
		return myString;
	}

	
}
