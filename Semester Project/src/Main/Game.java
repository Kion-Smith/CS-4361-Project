package Main;

import javax.swing.JFrame;

public class Game 
{
	public static void main(String [] args)
	{
		JFrame  f = new JFrame("Test");
		
		f.add(new gamePanel());
		f.setResizable(false);
		f.setVisible(true);
		
		f.pack();
		f.setLocationRelativeTo(null);
		
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
