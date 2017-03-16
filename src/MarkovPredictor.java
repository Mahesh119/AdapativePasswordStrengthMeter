import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;



public class MarkovPredictor {
	
	public static final int GRAM_SIZE = 4;
	public static final int NUMBER_OF_SUGGESTIONS = 3;
	
	private List<NGramStructure> ngramForest;
	// Generate Lookup for next letter given n-1 letters
	
	private NGramStructure ConstructNGramProbabilityTable(HashMap<String, NGramRecord> vocabularyMap){
	
		HashMap<String, NGramRecord> ngramProbTable = new HashMap<String, NGramRecord>();
		int recordPopulation = 0;
		
		for (NGramRecord vocabularyRecord : vocabularyMap.values()){
			
			String word = vocabularyRecord.getName();
			int wordCount = vocabularyRecord.getCount();
			
			// A Record in ngramProbTable is created with the help of Vocabulary
			String prefix = word.substring(0, word.length() - 1);
			String nextLetter = word.substring(word.length()-1, word.length());
			NGramRecord ngramRecord = ngramProbTable.getOrDefault(prefix, new NGramRecord(prefix));
			
			// Update the prefix count 
			ngramRecord.promoteRecord(wordCount);
			
			// Create Element Record for the last letter and add to PriorityQueue 
			
			ngramRecord.maxHeap.add(new Element(wordCount, nextLetter));
			
			// add to ngramProbTable
			ngramProbTable.put(prefix, ngramRecord);
			
			// Update ngramPopulation (words so far processed)
			recordPopulation = recordPopulation + wordCount;
		}
		
		return new NGramStructure(ngramProbTable, recordPopulation);
    }
	
	public List<Element> giveSuggestions(String query){
		
		if (query.length() >= GRAM_SIZE || query.length() == 0){
			return new ArrayList<Element>();
		}
		
		return giveSuggestions(query, ngramForest.get(GRAM_SIZE - query.length() - 1).ngramProbTable);
	}
	public List<Element> giveSuggestions(String query, HashMap<String, NGramRecord> ngramProbTable){
		
		List<Element> suggestions = new ArrayList(NUMBER_OF_SUGGESTIONS);
		// Get the required N-Gram
		NGramRecord ngramRecord  = ngramProbTable.get(query);
		
		// if no mapping for the key
		if (ngramRecord == null){
			return suggestions;
		}
		
		// Get the suggestions
		PriorityQueue<Element> queue = ngramRecord.maxHeap;
		for (int i = 0; i < NUMBER_OF_SUGGESTIONS && !queue.isEmpty(); i++){
			
			suggestions.add(queue.poll());
		}
		
		// Add them back for future reference
		queue.addAll(suggestions);
		
		return suggestions;
	}
	
	
	private int buildModel(){
		
		HashMap<String, NGramRecord> vocabularyMap = new HashMap<String, NGramRecord>();
		
		//Load the file and initiate vocabulary construction
		vocabularyMap = VocabularyBuilder.loadVocabulary("vocabulary");
		
		//Construct ngram Forest
		
		for( int i = 0; i < GRAM_SIZE - 1; i++){
			// Construct ngramStructure
			NGramStructure ngramStructure = ConstructNGramProbabilityTable(vocabularyMap);
			vocabularyMap = ngramStructure.ngramProbTable;
			
			// Add ngramStructure to ngramForest
			ngramForest.add(ngramStructure);
		}
		
		
		return 1;
	}
	
	public MarkovPredictor(){
		
		ngramForest = new ArrayList<NGramStructure>(GRAM_SIZE);
		buildModel();
	}
	
	public double computeCondtionalProb(String nextLetter, String givenLetters){
		double prob = 0.0;
		
		// Decide probTable
		HashMap<String, NGramRecord> ngramProbTable = ngramForest.get(GRAM_SIZE - givenLetters.length() - 1).ngramProbTable;
		
		NGramRecord ngramRecord = ngramProbTable.get(givenLetters);
		int givenLettersCount;
		if( ngramRecord == null){
			
			return Double.MIN_VALUE;
		}
		
		givenLettersCount = ngramRecord.getCount();
		
		// Get the nextLetterCount
		Element nextElement = ngramRecord.getElement(nextLetter);
		int nextLetterCount = nextElement.getCount();
		
		// Recalculate Prob
		prob = nextLetterCount/(double)(givenLettersCount);
		System.out.println( " nextLetter "+ nextLetter+ "  givenLetters  " + givenLetters + " prob: "+prob);
		
		return Math.max(prob, Double.MIN_VALUE);
	}
	
	public double computeProb(String letter){
		double prob = 0.0;
		
		// Decide probTable
		NGramStructure ngramStructure = ngramForest.get(GRAM_SIZE - letter.length() - 1);
		HashMap<String, NGramRecord> ngramProbTable = ngramStructure.ngramProbTable;
		int ngramPopulation = ngramStructure.ngramPopulation;
				
		// prob recalculation
		NGramRecord ngramRecord = ngramProbTable.get(letter);
		if (ngramRecord != null){
			prob = ngramRecord.getCount() / (double)(ngramPopulation);
			System.out.println(letter + "  "+ngramRecord.getCount() + "   " + ngramPopulation + " prob: " + prob);
		}
		return Math.max(prob, Double.MIN_VALUE);
	}
	
}
