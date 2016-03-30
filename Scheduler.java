
public class Scheduler{
	
	public static LinkedList sortOneList(LinkedList unsorted){
		
		LinkedList sorted = new LinkedList();
		Node walker = unsorted.head;
		
		while(walker!=null){
			sorted.insert(walker.getPcb(), sorted);
			walker = walker.getNext();
		}
		return sorted;
	}
	
	
	public static LinkedList sortTwoList(LinkedList unsorted1, LinkedList unsorted2){
		LinkedList sorted = sortOneList(unsorted1);
		Node walker2 = unsorted2.head;
		
		while(walker2!=null){
			sorted.insert(walker2.getPcb(), sorted);
			walker2 = walker2.getNext();
		}
		return sorted;
	}
	
	
}
