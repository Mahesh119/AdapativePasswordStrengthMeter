import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.Set;


public class Main {
	

	public static void main(String args[]){
		
		
		
			
		HashMap<String, Element> vocabularyMap = new HashMap<String, Element>();
		
		vocabularyMap = VocabularyBuilder.buildVocabulary(args[0], MarkovPredictor.GRAM_SIZE);
		System.out.println(args[0]);
		VocabularyBuilder.writeVocabulary(vocabularyMap, "vocabulary");
		
		
		
	}
	

	
}
