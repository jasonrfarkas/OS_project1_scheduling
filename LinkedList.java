/*LinkedList class is used to implement Queues. This class has two Node variables as "head" and "tail"
 */

public class LinkedList {

    protected Node head = null;
    protected Node tail = null;

    
    //This method returns true if the LinkedList is empty and returns false otherwise
    public boolean isEmpty() {
        return head == null;
    }

    //This method returns the size of the LinkedList
    public int size() {
        int count = 0;
        for (Node node = head; node != null; node = node.getNext()) {
            count++;
        }
        return count;
    }
    
   
    //takes a pcb and inserts it in the linkedlist according to its remaining bursts
    public void SJFinsert(PCB myPCB){
        
        Node myNode = new Node(myPCB, null);
        Node walker = this.head;  
        if (isEmpty()) {
            this.head = myNode;  
        } 
        
        else{ 
        	while(walker.getNext() != null && Integer.valueOf(myNode.getPcb().getRemainingInBurst()) > Integer.valueOf(walker.getNext().getPcb().getRemainingInBurst())) {
        		walker = walker.getNext();
        	}
    
        	myNode.setNext(walker.getNext());
        	walker.setNext(myNode);
        }
       
    }
    
    
  //takes an unsorted linked list and returns a sorted linked list
  	public LinkedList SJFsortOneList(LinkedList unsorted){
  		
  		LinkedList sorted = new LinkedList();
  		Node walker = unsorted.head;
  		
  		while(walker!=null){
  			sorted.SJFinsert(walker.getPcb());
  			walker = walker.getNext();
  		}
  		return sorted;
  	}
    
    public String toString(){
        
        String myString ="";
        Node walker = this.head;
        while (walker!= null){
            myString += walker.getPcb();
            walker = walker.getNext();
        }
        return myString;
    }
}
