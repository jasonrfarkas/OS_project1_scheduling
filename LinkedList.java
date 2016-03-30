public class LinkedList {

    public boolean isEmpty() {
        return head == null;
    }

    public int size() {
        int count = 0;
        for (Node node = head; node != null; node = node.getNext()) {
            count++;
        }
        return count;
    }
    
    public LinkedList insert(PCB myPCB, LinkedList sorted){
        
        Node myNode = new Node(myPCB, null);
        Node walker = sorted.head;
        
        if (sorted.isEmpty()) {
            sorted.head = myNode;
        } 
        
        else while(walker.getNext() != null && myNode.getPcb().getJobId() > walker.getPcb().getJobId())
            walker = walker.getNext();
              
        
        myNode.setNext(walker);
        walker = myNode;
    
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
