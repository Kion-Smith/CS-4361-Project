package GameStates;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import Controls.keyHandler;
import Main.gamePanel;

public class winState extends gameStates
{

	public winState(gameStateManager g) 
	{
		super(g);
	}


	public void init() 
	{
		
	}
	
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

	//all most identical to the game over but instead say the player has won
	public void draw(Graphics g, int[] buffer) 
	{
		g.setColor(new Color(255, 255, 255,4));
		g.fillRect(0,0,gamePanel.WIDTH,gamePanel.HEIGHT);
		
		g.setColor(new Color(204, 153, 0));
		g.setFont(new Font("Ariel",Font.PLAIN,48) );
		g.drawString("YOU WON",gamePanel.WIDTH/2 - 100,gamePanel.HEIGHT/2);
		
		
		g.setColor(Color.BLACK);
		g.setFont(new Font("Ariel",Font.PLAIN,12) );
		g.drawString("Press a Enter or E to go back to the main menu",gamePanel.WIDTH/2 -100,gamePanel.HEIGHT/2 + 100);
	}



}
