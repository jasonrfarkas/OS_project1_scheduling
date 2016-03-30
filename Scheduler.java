
public class Scheduler{
	
	private int schedulingAlgorithmNumber;
	private int quantomCounter; 
	private int quantomLimit;
	private boolean preemptive;

	public Scheduler(){
		schedulingAlgorithmNumber = 1;
		quantomCounter = 0;
		quantomLimit = 10;
		preemptive = false;
	}

	public Scheduler(int schedulingAlgorithmNumber){
		if(schedulingAlgorithmNumber > 0 && schedulingAlgorithmNumber < 4){
			this.schedulingAlgorithmNumber = schedulingAlgorithmNumber;
			preemptive = false;
			if(schedulingAlgorithmNumber == 3){
				preemptive = true;
			}
		}
		else{
			schedulingAlgorithmNumber = 1;
		}
		quantomCounter = 0;
		quantomLimit = 10;	
	}

	public Scheduler(int schedulingAlgorithmNumber, int quantom){
		// This should really be made into a swich statement 
		if(schedulingAlgorithmNumber > 0 && schedulingAlgorithmNumber < 4){
			this.schedulingAlgorithmNumber = schedulingAlgorithmNumber;
			preemptive = false;
			if(schedulingAlgorithmNumber == 3){
				preemptive = true;
				quantomLimit = quantom;
			}
		}
		else{
			schedulingAlgorithmNumber = 1;
		}
		if(preemptive){
			quantomCounter = 0;
		}
		
	}

	public void quantomCheck(OS myOS){
		if(preemptive){
			if(quantomCounter >= quantomLimit){
				boolean replaced = myOS.cpuReadyQQuantomHandOff();
				// That should call resetQuantom();
				if(replaced){
					upQuantomCounter();
				}
			}
			else{
				upQuantomCounter();
			}
		}
	}

	public void upQuantomCounter(){
		quantomCounter += 1;
	}
	public void resetQuantom(){
		quantomCounter = 0;
	}

	public int getSchedulingAlgorithmNumber(){
		return schedulingAlgorithmNumber;
	}

	public String getSchedulingAlgorithm(){
		switch(schedulingAlgorithmNumber){
			case (1):{
				return "First come First Serve";
			}
			case (2):{
				return "UNSET SchedulingAlgorithm";
			}
			case (3):{
				return "Round Robin";
			}
			default:{
				return "No Algorithm chosen";
			}
		}
	}

	public static LinkedList sortOneList(LinkedList unsorted){
		
		LinkedList sorted = new LinkedList();
		Node walker = unsorted.head;
		
		while(walker!=null){
			sorted.insert(walker.getPcb(), sorted);
			walker = walker.getNext();
		}
		return sorted;
	}
	
	
	public static LinkedList sortTwoList(LinkedList unsorted1, LinkedList unsorted2){
		LinkedList sorted = sortOneList(unsorted1);
		Node walker2 = unsorted2.head;
		
		while(walker2!=null){
			sorted.insert(walker2.getPcb(), sorted);
			walker2 = walker2.getNext();
		}
		return sorted;
	}
	
	
}
