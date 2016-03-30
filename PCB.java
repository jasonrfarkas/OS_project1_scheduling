
public class PCB {
	private int jobId;
	private String state;
	private int simulatedPc; // may be the same as cycleCounter
	private int totalCPUBursts;
	private int[] CPUBursts; 
	private int currentCPUBurst;
	private int iOCompletionTime;
	//private CPU connectedCPU;
	private OS system; 

	private int arrivalTime;
	//private int cycleCounter; // I think this is the same as the simulatedPc
	private int completionTime; // Time when pcb job completed
	private int remainingInCurrentBurst;
	//private int blockedStartTime;
	//private int blockedEndTime;
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
	//public void disconnectSystem(){
	//	system = null;
	//}
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
		
		//cycleCounter = 0;
		currentCPUBurst = 0;
		// blockedStartTime = 0;
		// blockedEndTime = 0;
		
		
		totalCPUBursts = Integer.valueOf(tempArray[2]);
		//System.out.println("totalCPUBursts= " +totalCPUBursts);
		CPUBursts = new int [totalCPUBursts];
		for (int i = 0; i<totalCPUBursts; i++){
			CPUBursts[i] = Integer.valueOf(tempArray[i+3]);
		//	System.out.println("CPUBursts[i]= " + CPUBursts[i] + " i =" + i);
		}
		remainingInCurrentBurst = getCurrentCPUBurstLength();
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

	public boolean ready() {
		return this.getState() == "ready";
	}

	public boolean blocked() {
		return this.getState() == "blocked" ; //|| getiOCompletionTime > 0;
	}

	public boolean running() {
		return this.getState() == "running";
	}

	public boolean completed() {
		return this.getState() == "completed" || this.getCurrentCPUBurst() >= this.getTotalCPUBursts();
	}

	public void setState(String state) {
		this.state = state;
	}

/*	public String assumeNextState(){
		// This function tries to automatically reset the state and return it
		// This function should be refactored later
		state = this.getState();
		if(getiOCompletionTime > 0){
			this.setState("blocked");
		}
		else if(state== null){
			this.setState("ready");
		}
		else if(state== "ready" && systemConneted()){
			this.setState("running");
		}
		else if(state == completed || this.getCurrentCPUBurst() >= this.getTotalCPUBursts()){
			this.setState("completed");
		}
		else if(state=="blocked"){
			if(getiOCompletionTime <= 0){
				this.setState("ready");
			}
		}
		else if( getRemainingInBurst() <= 0 ){
			this.setState("blocked");
			this.increaseBurst();
		}
	}
*/
/*	
	I would want to automiate this function from the pcb class, but I can't figure out a way to determine if it is running except from outside the class
	
	public void setState(int cpuCycle) {
		//ready, running, blocked, completed
		if(this.getiOCompletionTime()>0){
			this.state = "blocked";
		}
		else if(){

		}
	}*/

/*	public int getCycleCounter() {
		return cycleCounter;
	}

	public void setCycleCounter(int cycleCounter) {
		this.cycleCounter = cycleCounter;
	}*/

	public int getSimulated_pc(){
		return simulatedPc;
	}

	private void setSimulated_pc(int simulatedPc){
		if(simulatedPc > this.simulatedPc){
			this.simulatedPc = simulatedPc;
		}
	}

	private void increasePC(){ 
		setSimulated_pc(getSimulated_pc()+1);
		//setCurrentCPUBurst(getCurrentCPUBurst()+1);
		setRemainingInCurrentBurst(getRemainingInBurst()-1);
		if( getRemainingInBurst() <= 0 ){
			this.increaseBurst();
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

/*	public int ioWait(){ 
		//return 0 if waited succesfully returns -1 if it finished io, -2 if it wasn't in waiting mode
		if(waiting()){
			setiOCompletionTime(getiOCompletionTime()-1); 
			if (getiOCompletionTime() <= 0){
				setState("ready");
				return -1;
			}
			return 0;
		}
		return -2;
	}*/

	public int getTotalCPUBursts() {
		return totalCPUBursts;
	}

	private void setTotalCPUBursts(int totalCPUBursts) {
		this.totalCPUBursts = totalCPUBursts;
	}

	public int[] getCPUBursts() {
		return CPUBursts;
	}

	public void setCPUBursts(int[] cPUBursts) {
		CPUBursts = cPUBursts;
	}

	public int getCurrentCPUBurst() {
		return currentCPUBurst;
	}

	public void setCurrentCPUBurst(int currentCPUBurst) {
		this.currentCPUBurst = currentCPUBurst;
	}

	public int getCurrentCPUBurstLength(){
		//System.out.println("getCurrentCPUBurst() =  " + getCurrentCPUBurst());
		//System.out.println("getTotalCPUBursts() =  " + getTotalCPUBursts());
		if(getCurrentCPUBurst() < getTotalCPUBursts()){
			return getCPUBursts()[getCurrentCPUBurst()];
		}
		else{
			return getCPUBursts()[getTotalCPUBursts()-1];
		}
		
	}

	public int getRemainingInBurst(){
		return remainingInCurrentBurst;
	}

	private void setRemainingInCurrentBurst(int r){
		this.remainingInCurrentBurst = r;
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

	public int getCompletionTime() {
		return completionTime;
	}

	public void setCompletionTime(int completionTime) {
		this.completionTime = completionTime;
	}

	public int getiOCompletionTime() {
		return iOCompletionTime;
	}

	private void setiOCompletionTime(int iOCompletionTime) {
		// System.out.println("setting io complete time to " +  iOCompletionTime);
		if(iOCompletionTime >= 0){
			this.iOCompletionTime = iOCompletionTime;
		}
		// System.out.println(" io complete time= " + this.iOCompletionTime );
	}

	public void setWaitTime(int waitTime){
	//	System.out.println(" \n\nmaking pcb wait" + getSystemTime() );
		if(systemConneted()){
			setState("blocked");
	//		System.out.println("state now blocked");
			setiOCompletionTime(getSystemTime()+1 +waitTime ); //must account for the current cycle
		}
	}
	
	public int recheckIfWaiting(){
		/*
			Returns -1 if still waiting
			Returns 0 if done waiting and in ready state
			Returns -2 if the method should not have been called
		*/

	//	System.out.println(" connected: " + systemConneted() +  "blocked() :"+ blocked() );
		if(systemConneted() && blocked() ){
		//	System.out.println(" checking pcb's time. getSystemTime:" + getSystemTime() + " >= iOCompletionTime:" + iOCompletionTime );
			if(getSystemTime() >= getiOCompletionTime()){
				setState("ready");
				setiOCompletionTime(0);
				// System.out.println("true");
		//		System.out.println("walker.pcb.recheckIfWaiting(): " +(0)  + "state: " + state);
				return 0;
			}
	//		System.out.println("walker.pcb.recheckIfWaiting(): " +(-1)  + "state: " + state);
			// System.out.println(" false");
			return -1;
		}
	//	System.out.println("walker.pcb.recheckIfWaiting(): " +(-2) + "state: " + state);
		return -2;
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
		myString+="jobId = "+jobId+"\narrivalTime = "+arrivalTime+"\nsimulatedPc = "+simulatedPc;
		
		myString+="\ncurrentCPUBurst = "+currentCPUBurst+" \ntotalCPUBursts = "+totalCPUBursts+"\nCPUBursts : ";
		for (int i = 0; i<totalCPUBursts; i++)
			myString+=" "+ CPUBursts[i];
		
		myString+="\ncompletionTime = "+completionTime+"\niOCompletionTime = "+iOCompletionTime+"\nstate = "+state;
		
		return myString;
	}

	
}
