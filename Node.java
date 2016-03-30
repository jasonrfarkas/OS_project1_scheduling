

public class Node {
        private  PCB pcb;
        private Node next;
        public Node(PCB myPCB, Node next) {
            this.setPcb(myPCB);
            this.setNext(next);
        }
		public Node() {
			
		}
		public PCB getPcb() {
			return pcb;
		}
		public void setPcb(PCB pcb) {
			this.pcb = pcb;
		}
		public Node getNext() {
			return next;
		}
		public void setNext(Node next) {
			this.next = next;
		}
    }