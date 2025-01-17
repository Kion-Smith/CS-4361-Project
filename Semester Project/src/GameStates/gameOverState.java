package GameStates;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import Controls.keyHandler;
import Main.gamePanel;

public class gameOverState extends gameStates {

	public gameOverState(gameStateManager g) {
		super(g);

	}

	//Nothing to init
	public void init() 
	{}

	//update the key input
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
	
	//Our death state inform the player of their loss
	public void draw(Graphics g, int[] buffer) 
	{
		g.setColor(new Color(0, 0, 0,4));
		g.fillRect(0,0,gamePanel.WIDTH,gamePanel.HEIGHT);
		
		g.setColor(Color.RED);
		g.setFont(new Font("Ariel",Font.PLAIN,48) );
		g.drawString("YOU DIED",gamePanel.WIDTH/2 - 100,gamePanel.HEIGHT/2);
		
		
		g.setColor(Color.WHITE);
		g.setFont(new Font("Ariel",Font.PLAIN,12) );
		g.drawString("Press a Enter or E to go back to the main menu",gamePanel.WIDTH/2 -100,gamePanel.HEIGHT/2 + 100);
	}

}
