
public class Password {

	
	private  String passPhrase;
	private double score;
	private PasswordStrength strength;
	
	public Password(String text, double score, PasswordStrength strength){
		this.passPhrase = text;
		this.score = score;
		this.strength = strength;
		
	}
	
	public PasswordStrength getStrength(){
		
		return strength;
	}
	
	public String getpassPhrase(){
		
		return passPhrase;
	}
	public double getScore(){
		return score;
	}
}
