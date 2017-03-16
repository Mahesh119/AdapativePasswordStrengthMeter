import java.util.ArrayList;
import java.util.List;


public class NGramsGenerator {

	
	public static List<String>  generateNGrams(int n, String sentence){
		
		List<String> grams = new ArrayList<String>();
		
		int start, end;
		int length = sentence.length();
		
		//	Prerequiste
		if(length < n){
			
			grams.add(sentence);
			return grams;
			
		}
		
		//	Grams Separation
		for (start = 0, end = n; end <= length; start = start + 1, end = end + 1){
			
			String gram = sentence.substring(start, end);
			grams.add(gram);
			
		}
		
		return grams;
	}
}
