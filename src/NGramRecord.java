import java.util.Iterator;
import java.util.PriorityQueue;


public class NGramRecord {
	
	private String name;
	private int count;
	public PriorityQueue<Element> maxHeap;
	
	public int getCount(){
		return count;
	}
	
	public String getName(){
		return name;
	}
	
	public int setCount(int count){
		this.count = count;
		return count;
	}
	
	public NGramRecord promoteRecord(){
		
		count = count + 1;
		return this;
	}
	
	public NGramRecord promoteRecord(int increment){

		this.count = count + increment;
		return this;
		
	}
	public NGramRecord(String name){
		this.name = name;
		this.count = 0;
		this.maxHeap = new PriorityQueue(5, new ElementComparator());
	}
	
	public NGramRecord(String name, int count){
		this.name = name;
		this.count = count;
		this.maxHeap = new PriorityQueue(5, new ElementComparator());
	}
	
	public Element getElement(String letter){
		
		Element element = new Element(letter);
		if (maxHeap.isEmpty())
			return element;
		
		Iterator<Element> it = maxHeap.iterator();
		
		while (it.hasNext()){
			Element tmp = it.next();
			if (tmp.getName().equals(letter))
				return tmp;
		}
		return element;
	}

}
