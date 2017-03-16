import java.awt.*;
import java.awt.event.*;
import java.text.DecimalFormat;

import javax.swing.*;
 
public class PasswordPredictorGUI {
   private JFrame mainFrame;
   private JLabel headerLabel;
   private JLabel suggestionLabel;
   private JLabel scoreLabel;
   private JLabel strengthLabel;
   private JPanel controlPanel;
   private JPanel meterPanel;
   private JTextField passwordText;
   private static DecimalFormat dformat = new DecimalFormat("#.##");
   public PasswordPredictorGUI(){
      prepareGUI();
   }
   public static void main(String[] args){
	   PasswordPredictorGUI  application = new PasswordPredictorGUI();
	   PasswordPredictor.switchONTheService();
	   application.passwordAction();
   }
   private void prepareGUI(){
      mainFrame = new JFrame("Password Predicton Service");
      mainFrame.setSize(400,400);
      mainFrame.setLayout(new GridLayout(5, 1));
      
      mainFrame.addWindowListener(new WindowAdapter() {
         public void windowClosing(WindowEvent windowEvent){
            System.exit(0);
         }        
      });    
      headerLabel = new JLabel("Please wait..! Building service", JLabel.CENTER);        
      suggestionLabel = new JLabel("",JLabel.CENTER);    
      suggestionLabel.setSize(350,100);
      
      strengthLabel = new JLabel("",JLabel.CENTER);
      strengthLabel.setSize(130, 50);
      
      scoreLabel = new JLabel("",JLabel.CENTER);    
      scoreLabel.setSize(50,100);
      
      
      controlPanel = new JPanel();
      controlPanel.setLayout(new FlowLayout());

      mainFrame.add(headerLabel);
      mainFrame.add(controlPanel);
      mainFrame.add(strengthLabel);
      mainFrame.add(scoreLabel);
      mainFrame.add(suggestionLabel);
      
      mainFrame.setVisible(true);  
   }
   private void passwordAction(){
	   
      headerLabel.setText("Password Predictor Service"); 

     
      JLabel  passwordLabel = new JLabel("Password: ", JLabel.CENTER);
      passwordText = new JTextField(12); 
      passwordText.addKeyListener(new CustomKeyListener());

      JButton submitButton = new JButton("Create");
      submitButton.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {     
            String data = "Password Entered"; 
            suggestionLabel.setText(data);        
         }
      }); 
      
      controlPanel.add(passwordLabel);       
      controlPanel.add(passwordText);
      controlPanel.add(submitButton);
      
      
      mainFrame.setVisible(true);  
   }
   
   class CustomKeyListener implements KeyListener{
	      public void keyTyped(KeyEvent e) {
	    	
	      }
	      public void keyPressed(KeyEvent e) {
	    	  
	      }
	      public void keyReleased(KeyEvent e) {
	    	  if(e.getKeyCode() != KeyEvent.VK_ENTER){
	    		  	String passwd = passwordText.getText();
	    		  	String suggestionText = PasswordPredictor.suggestNextLetter(passwd);
	    		  	if( suggestionText != null)
	    		  		suggestionLabel.setText("Next Letter : " + suggestionText);
		            Password pass = PasswordPredictor.calculatePasswordStrength(passwd);
		            scoreLabel.setText(" Markov Score :: " + dformat.format(pass.getScore()) );
		            
		            if(pass.getStrength() == PasswordStrength.WEAK)
		            	strengthLabel.setText("Strength :: Weak");
		            else if (pass.getStrength() == PasswordStrength.GOOD)
		            	strengthLabel.setText("Strength :: Good");
		            else
		            	strengthLabel.setText("Strength :: Strong");
		            mainFrame.setVisible(true);
		         }
	      }
	   }
}