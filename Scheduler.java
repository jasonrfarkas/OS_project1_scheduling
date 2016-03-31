/*
 * Scheduler class determines which scheduling algorithm to use according to the user input.
 * sample input: in1.txt 1 2 3
 * argv[0]- name of the input file
 * argv[1]- an integer (1 = FCFS, 2 = SJF, 3 = RR)
 * argv[2]- an integer which determines the quantam number. If an user insert any input for argv[2], when algorithm is FCFS or SJF,
 * it will be ignored
 * if an user does not insert any input for argv[2], when algorithm is RR,
 * the default quantam(10) will be used. 
 * 
 */


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
				if(quantom > 0){
					quantomLimit = quantom;
				}
				else{
					quantomLimit=10;
				}
				
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
				return "Shortest Job First";
			}
			case (3):{
				return "Round Robin";
			}
			default:{
				return "No Algorithm chosen";
			}
		}
	}

	public void sort(Queue main, Queue other){
		switch(schedulingAlgorithmNumber){
			case (1):{
				main.addToQue(other);
				break;
			}
			case (2):{
				main.setAs(SJFgarunteedSortTwoList(main, other));
				break;
			}
			case (3):{
				main.addToQue(other);
				break;
			}
			default:{
				main.addToQue(other);
				break;
			}
		}
	}

	public void sortedAdd(Queue main, PCB other){
			switch(schedulingAlgorithmNumber){
				case (1):{
					main.enqueue(other);
					break;
				}
				case (2):{
					main.SJFinsert(other);
					break;
				}
				case (3):{
					main.enqueue(other);
					break;
				}
				default:{
					main.enqueue(other);
					break;
				}
			}
		}
	
	public LinkedList SJFgarunteedSortTwoList(LinkedList unsorted1, LinkedList unsorted){
		LinkedList sorted = SJFsortOneList(unsorted1);
		return SJFsortTwoList(sorted, unsorted); 

	}

	//takes one unsorted linked list and one sorted linked list; then returns a sorted linked list
	public LinkedList SJFsortTwoList(LinkedList sorted, LinkedList unsorted){
		
		Node walker = unsorted.head;
		while(walker!=null){
			sorted.SJFinsert(walker.getPcb());
			walker = walker.getNext();
		}
		return sorted;
	}
	
	 //takes an unsorted linked list and returns a sorted linked list
  	public LinkedList SJFsortOneList(LinkedList unsorted){
  		
  		LinkedList sorted = new LinkedList();
  		return SJFsortTwoList(sorted, unsorted);
  		
  	}
	
}
