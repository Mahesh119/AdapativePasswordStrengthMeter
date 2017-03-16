import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;


public class VocabularyBuilder {
	
	public static HashMap<String, Element> buildVocabulary(String fileName, int gramSize){
		
		HashMap<String, Element> vocabularyMap = new HashMap<String, Element>();
		String passwd;
		
		try{
			FileReader file = new FileReader(fileName);
			BufferedReader br = new BufferedReader(file);
			while ((passwd = br.readLine()) != null){
		
				vocabularyMap = buildVocabulary(passwd, gramSize, vocabularyMap);
				
			}

		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

		
		return vocabularyMap;
	}
	
	public  static HashMap<String, Element> buildVocabulary(String passwd, int n, HashMap<String, Element> lookUp){
		
		if(passwd.length() == 0 || passwd.length() < n){
			return lookUp;
		}
		
		// Generate NGrams
		List<String> ngrams = NGramsGenerator.generateNGrams(n, passwd);
		
		// Add or Update the ngrams
		for (String gram : ngrams){
			Element tmp = lookUp.getOrDefault(gram, new Element(gram)).promoteElement();
			lookUp.put(gram, tmp);
		}
			
		return lookUp;
	}
	
	public static void writeVocabulary(HashMap<String, Element> vocabularyMap, String fileName){
		
		// OPen file in write mode
		FileOutputStream fout = null;
		ObjectOutputStream oos = null;
		
		try {
			fout = new FileOutputStream(fileName); 
			oos = new ObjectOutputStream(fout);
		
			// Write Elements to the file 
			for (Element vocabulary : vocabularyMap.values()){
				oos.writeObject(vocabulary);
			}
		}	
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static HashMap<String,NGramRecord> loadVocabulary(String fileName){
		
		HashMap<String,NGramRecord> vocabularyMap = new HashMap<String,NGramRecord>();
		
		try{
			FileInputStream fstream = new FileInputStream(fileName);
			ObjectInputStream ostream = new ObjectInputStream(fstream);
			
			while( Boolean.TRUE){
				Element e = (Element) ostream.readObject();
				
				// Construct NGramRecord
				NGramRecord ngramrecord = new NGramRecord(e.getName(), e.getCount());
				vocabularyMap.put(e.getName(), ngramrecord);
			
			}
		} 
		catch(EOFException e){
			System.out.println(" End of file reached");
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return vocabularyMap;
	}
		 
		
}


