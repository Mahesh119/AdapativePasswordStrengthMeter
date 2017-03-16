import java.util.ArrayList;
import java.util.List;


public class PasswordPredictor {

	private static MarkovPredictor predictor;
	public static final double NEGATIVE_ONE = -1; 
	public static final double LOG2_VALUE = Math.log10(2);
	
	public static boolean switchONTheService()
	{
		predictor = new MarkovPredictor();
		return Boolean.TRUE;
	}
	
	public static String suggestNextLetter(String passwd){
		
		String suggestionText = " ";
		int length = passwd.length();
		List<Element> suggestions = new ArrayList<Element>();
		if (passwd.length() < MarkovPredictor.GRAM_SIZE){
			
			suggestions = predictor.giveSuggestions(passwd);
		}
		else{
			
			suggestions = predictor.giveSuggestions(passwd.substring(length - MarkovPredictor.GRAM_SIZE + 1, length));
		}
		
		for (Element suggestion : suggestions){
			suggestionText = suggestionText + "'" + suggestion.getName() + "'" + "  :  " + suggestion.getCount() + "  ";
		}
		return suggestionText;
	}
	
	public static Password calculatePasswordStrength(String passwd){
		Password pass = null;
		
		double score = NEGATIVE_ONE * (Math.log10(calculatePasswordProb(passwd)) / LOG2_VALUE); 
		PasswordStrength strength;
		if( score < 25){
			strength = PasswordStrength.WEAK;
		}
		else if (score <= 100){
			strength = PasswordStrength.GOOD;
		}
		else{
			strength = PasswordStrength.STRONG;
		}
		
		return new Password(passwd, score, strength);
		
	}
	
	private static double calculatePasswordProb(String passwd){
		
		double prob = 1.0;
		
		int length = passwd.length();
		
		if (length == 0){
			return prob;
		}
		
		// Calculate Initial Probability
		prob = predictor.computeProb(passwd.substring(0,1));
		int start = 0;
		String givenLetters=" ", letter = " ";
		int end = 2, numberOfSamples = 0;
		
		while (numberOfSamples < length - 1){
			
			// Token Separation
			String token = passwd.substring(start, end);
			givenLetters = token.substring(0, token.length() - 1);
			letter = token.substring(token.length() - 1, token.length());
			
			// compute Probability
			prob = predictor.computeCondtionalProb(letter, givenLetters);
			
			// Update start and end parameters
			if(end < MarkovPredictor.GRAM_SIZE){
				end = end + 1;
			}
			else{
				start = start + 1;
				end = start + MarkovPredictor.GRAM_SIZE;	
			}
			
			numberOfSamples = numberOfSamples + 1; 
		}	
		
		return prob;
		
	}
}
