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
