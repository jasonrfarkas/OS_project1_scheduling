import java.util.NoSuchElementException;


public class Queue extends LinkedList {
    protected Node head = null;
    protected Node tail = null;

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

}
