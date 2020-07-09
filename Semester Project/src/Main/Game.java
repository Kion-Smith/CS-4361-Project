package Main;

import javax.swing.JFrame;

//START CLASS
public class Game 
{
	public static void main(String [] args)
	{
		//Creating a basic JFrame
		JFrame  f = new JFrame("Raycasting game");
		
		//adding out panel to the JFrame
		f.add(new gamePanel());
		f.setResizable(false);
		f.setVisible(true);
		
		f.pack();
		f.setLocationRelativeTo(null);
		
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
