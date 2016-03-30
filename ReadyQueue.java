
public class ReadyQueue extends LinkedList{
     
    public String toString(){
    	
    	String myString ="";
    	Node walker = this.head;
    	while (walker!= null){
    		myString += walker.getPcb();
    		walker = walker.getNext();
    	}
    	return myString;
    }
    
    
    public ReadyQueue insert(PCB myPCB, ReadyQueue sorted){
		
		Node myNode = new Node(myPCB, null);
		Node walker = sorted.head;
		
		if (sorted.isEmpty()) {
			System.out.println("                    readyq is empty now");
        	sorted.head = myNode;
        } 
		
		else while(walker.getNext() != null && Integer.valueOf(myNode.getPcb().getJobId()) > Integer.valueOf(walker.getPcb().getJobId())){
			
		
			System.out.println("                    readyq is not empty now");
			walker = walker.getNext();
			  
		}
		myNode.setNext(walker);
		walker = myNode;
	
		return sorted;
		
	}
   
public static ReadyQueue sortOneList(ReadyQueue unsorted){
		
		ReadyQueue sorted = new ReadyQueue();
		Node walker = unsorted.head;
		
		while(walker!=null){
			sorted.insert(walker.getPcb(), sorted);
			walker = walker.getNext();
		}
		
		return sorted;
	}
}