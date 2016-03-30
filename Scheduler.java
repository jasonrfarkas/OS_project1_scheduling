
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

	
	public static LinkedList SJFgarunteedSortTwoList(LinkedList unsorted1, LinkedList unsorted){
		LinkedList sorted = new SJFsortOneList(unsorted1);
		return SJFsortTwoList(sorted, unsorted); 

/*		Node walker = unsorted.head;
		while(walker!=null){
			sorted.SJFsortOneList(unsorted);
			walker = walker.getNext();
		}
		return sorted;*/
	}

	//takes one unsorted linked list and one sorted linked list; then returns a sorted linked list
	public static LinkedList SJFsortTwoList(LinkedList sorted, LinkedList unsorted){
		
		Node walker = unsorted.head;
		while(walker!=null){
			sorted.SJFinsert(walker.getPcb());
			//sorted.SJFsortOneList(unsorted);
			walker = walker.getNext();
		}
		return sorted;
	}
	
	 //takes an unsorted linked list and returns a sorted linked list
  	public LinkedList SJFsortOneList(LinkedList unsorted){
  		
  		LinkedList sorted = new LinkedList();
  		return SJFsortTwoList(sorted, unsorted);
/*
  		Node walker = unsorted.head;
  		
  		while(walker!=null){
  			sorted.SJFinsert(walker.getPcb());
  			walker = walker.getNext();
  		}
  		return sorted;*/
  	}
	
}
