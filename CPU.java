/*
	This CPU class represents a central processing unit
	Variables: 
	- available : keeps track if there is a PCB in the CPU
	- cycle: keep count of the current clock cycle
	- loadedPCB: points to a PCB that is currently loaded in the CPU
	- myOS: points to the OS that is controlling this CPU

	Methods:
	- Getters/Setters/constructor are self explanitory
	- popLoadedPCB: returns the PCB in the system and sets availible to true
	- Run: the main method of this class:
		This method checks if it should run based on the OS
		Prints out the current stats if the cycle is a multiple of 200
		Sees if the CPU has a loaded PCB, if not it tries to get one, 
		If the OS says it there are no ready ones then it just increments the cycle
		If it gets one/ already has one it checks to see if the quantom is up - and 
		the check method itself handles what should occur if that is the case
		It tells the PCB to run a line of code - which will automatically update the PCB"s State
		It then looks at the PCB's state and tells the OS to handle it occordingly
		If then increments the Cycle and repeats

*/

public class CPU {
	private boolean available;
	private int cycle;
	private PCB loadedPCB;
	private OS myOS;

    public CPU (){
    	available = true;
    	setCycle(0);
    	loadedPCB = null;
    }
	public boolean isAvailable() { return available; }
	public PCB getLoadedPCB(){ return loadedPCB; }
	public int getCycle() { return cycle; }
	private void setCycle(int cycle) { this.cycle = cycle; }
	private void increaseCycle(){ setCycle(getCycle()+1); }
	private void printStats(OS myOS){ myOS.printStats(); }
	public int setLoadedPCB(PCB loadedPCB) {
		if(isAvailable()){
			this.loadedPCB = loadedPCB;
			this.available = false;
			return 0;
		}
		else{ return -1; }
	}
	
	public PCB popLoadedPCB() {
		System.out.println("\n POPPING PCB");
		PCB returnPCB= loadedPCB;
		loadedPCB = null;
		this.available = true;
		myOS.resetQuantom();
		return returnPCB;
	}

	
	public void Run(JobQueue myJobQueue, ReadyQueue myReadyQueue, BlockedQueue myBlockedQueue, OS myOS){
		this.myOS = myOS;
		increaseCycle();// So that it prints what happened every cycle
		while(myOS.running()){
			if(getCycle() %200 == 0 ){
				printStats(myOS);
			}
			System.out.println("current CPU cycle is  "+getCycle());
			if(isAvailable()){
				// If ther cycle starts with noting loaded in the CPU
				// Make sure anything meant to be out of the blockedQ is out
				myOS.refreshBlocked();
				if(myOS.readyQCPUHandoff()){ 
					// If the OS loaded the CPU with a PCB
					// System.out.println("Got item from readyQ to CPU");
				}
				else{
					// System.out.println("No items to get from ready/jobQ");
					increaseCycle();
					continue;
				}
			}
			// This point will only be reached is there is a pcb in the CPU
			// Check if quantom is up, if not increase quantom counter in scheduler
			myOS.premtiveCheck();

			System.out.println("Running line of code in pcb");
			loadedPCB.runLine(); 
			// If after running the line of code... 
			if(loadedPCB.blocked()){
				System.out.println("\tPCB burst is finished");
				// Have the OS deal with placing it in the blockedQ
				myOS.cpuBlockedQHandOff(); 
			}
			else if(loadedPCB.completed()){
				System.out.println("\t\tPCB is finished");
				// Have the OS  deal with completing the process
				myOS.completeProcess(popLoadedPCB());
			}
			// Increment the CPU cycle counter
			increaseCycle();	
		}
		System.out.println("\nProgram complete");
	}

	/*
	This function isn't used but represents what would occur in a contextSwitch
	What happens in our project is contains more steps and classes so it doesn't
	use this function
	*/
	public PCB contextSwitch(PCB loadPCB) {
		PCB returnPCB = popLoadedPCB();
		setLoadedPCB(loadPCB);
		return returnPCB;
	}
}
