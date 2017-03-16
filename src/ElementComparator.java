import java.util.Comparator;


public class ElementComparator implements Comparator<Element> {

	@Override
	public int compare(Element first, Element second){
		
		// Adjusted for MaxHeap implementation
		// if 'first' is large, then it should return -ve value
		return second.getCount() - first.getCount();
	}
	

}
