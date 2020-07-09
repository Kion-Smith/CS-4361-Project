package GameStates;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import Controls.keyHandler;
import Main.gamePanel;

public class helpState extends gameStates {

	public helpState(gameStateManager g) 
	{
		super(g);
	}


	public void init() 
	{

		//no need to init
	}

	//basic update
	public void update() 
	{
		handleInput();
	}

	public void handleInput() 
	{
		if(keyHandler.isPressed(keyHandler.INTERACT))
		{
			gsm.setState(gsm.MENU);
		}
	}

	//basic draw
	public void draw(Graphics g, int[] buffer) 
	{
		g.setColor(new Color(9, 10, 66));
		g.fillRect(0, 0, gamePanel.WIDTH, gamePanel.HEIGHT);
		
		g.setFont(new Font("Arial",Font.PLAIN,24));
		g.setColor(Color.WHITE);
		
		g.drawString("HELP", gamePanel.WIDTH/2 -((7)*2), gamePanel.HEIGHT/2 - 100);
		
		g.setFont(new Font("Arial",Font.PLAIN,12));
		g.setColor(Color.WHITE);
		
		//INFO ABOUT GAME
		g.drawString("- This is a game which uses the raytracing method to create a 3D world space from a 2D map", gamePanel.WIDTH/2 -((40)*6), gamePanel.HEIGHT/2 -50);
		g.drawString("- A player has a pistol to kill zombies and get a key to go to the next level", gamePanel.WIDTH/2 -((40)*6), gamePanel.HEIGHT/2 -25);
		
		//The basic controls
		g.drawString("CONTROLS: Space to shoot", gamePanel.WIDTH/2 -((40)*6), gamePanel.HEIGHT/2 +25);
		g.drawString("- W or up to move foward", gamePanel.WIDTH/2 -((40)*6), gamePanel.HEIGHT/2 + 50);
		g.drawString("- S or down to move backward", gamePanel.WIDTH/2 -((40)*6), gamePanel.HEIGHT/2 + 75);
		g.drawString("- A or left to turn left", gamePanel.WIDTH/2 -((40)*6), gamePanel.HEIGHT/2 + 100);
		g.drawString("- D or right to turn right", gamePanel.WIDTH/2 -((40)*6), gamePanel.HEIGHT/2 + 125);
		
		g.drawString("PRESS ENTER OR E TO GO BACK TO THE MENU", gamePanel.WIDTH/2 -((40)*6), gamePanel.HEIGHT/2 +155);
	}
}
