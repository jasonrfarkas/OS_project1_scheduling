/*Queue class has the features of an ordinary Queue. 
 * enqueue method insert an element at the tail of the queue 
 * dequeue method take out an element from the head of the queue 
 */


import java.util.NoSuchElementException;

public class Queue extends LinkedList {

    public Queue(){
        head = null;
        tail = null;
    }

    public Queue(LinkedList l){
        this.head = l.head;
        this.tail = l.tail;
    }

    public void enqueue(PCB myPCB) {
        Node newNode = new Node(myPCB, null);
        if (isEmpty()) {
            head = newNode;
        } 
        
        else {
            tail.setNext(newNode);
        }
       
        tail = newNode;
    }

    public PCB dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        PCB myPCB = head.getPcb();
        if (tail == head) {
            tail = null;
        }
        head = head.getNext();
        return myPCB;
    }

    
    //this method returns the head of the queue without dequeuing it from the queue 
    public PCB peek() {
        if (head == null) {
            throw new NoSuchElementException();
        }
        return head.getPcb();
    }

    
     public void addToQue(Queue other) {
        while(other.head != null){
            this.enqueue(other.dequeue());
        }
    }

    public void setAs(LinkedList other){
        this.head = other.head;
        this.tail = other.tail;
    }

}
