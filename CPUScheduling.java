import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;


public class CPUScheduling {
	public static void main(String [] args){
		JobQueue myJobQueue = new JobQueue();
		CPU myCPU = new CPU();
		ReadyQueue myReadyQueue = new ReadyQueue();
		BlockedQueue myBlockedQueue = new BlockedQueue();
		
		try {
			Scanner inFile = new Scanner(new FileReader(args[0]));
			
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
		myCPU.Run(myJobQueue, myReadyQueue, myBlockedQueue);	
		
		//System.out.println("The size of this ready Q is "+(myReadyQueue).size());
		System.out.println(myBlockedQueue);
	}
}
