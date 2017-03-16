import java.io.Serializable;


public class Element implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int count;
	private String name;
	
	public int getCount(){
		return count;
	}
	
	public String getName(){
		return name;
	}
	
	public Element(int count, String name){
		
		this.count = count;
		this.name = name;
	}
	
	public Element(String name){
		this.count = 0;
		this.name = name;
	}
	
	public Element promoteElement(){
		this.count = this.count + 1;
		return this;
	}
	
	public String toString(){
		return "name : " + name +"count : "+ count ;
		
	}
}
