package Project.Main;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class Game extends JFrame
{
	public static void main(String [] args)
	{
		JFrame  f = new JFrame("Test");
		
		f.add(new GamePanel());
		f.setResizable(false);
		f.setVisible(true);
		
		f.pack();
		f.setLocationRelativeTo(null);
		
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	
}