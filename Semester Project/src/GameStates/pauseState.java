package GameStates;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import Controls.keyHandler;
import Main.gamePanel;

public class pauseState extends gameStates
{
	//basic pause menu
	private String[]  menu= {"Continue","Quit"};
	private int state = 0;
	
	public pauseState(gameStateManager gsm)
	{
		super(gsm);
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
		//selection for what to do on pause menu
		if(keyHandler.isPressed(keyHandler.UP))
		{
			if(state == 1)
			{
				state=0;
			}

		}
		else if (keyHandler.isPressed(keyHandler.DOWN))
		{
			if(state == 0)
			{
				state = 1;
			}

		}

		else if(keyHandler.isPressed(keyHandler.INTERACT))
		{
			if(state ==0)
			{
				gsm.setPause(false);
			}
			else if (state == 1)
			{
				System.exit(0);
			}
		}
		
	}
	
	public void draw(Graphics g, int[] buffer) 
	{
		//alpha in rgb allows for cool fade in effect
		g.setColor(new Color(0, 0, 0,2));
		g.fillRect(0,0,gamePanel.WIDTH,gamePanel.HEIGHT);
		
		g.setFont(new Font("Arial",Font.PLAIN,48) );
		g.setColor(Color.WHITE);
		g.drawString("PAUSED",gamePanel.WIDTH/2,gamePanel.HEIGHT/2);
		
		g.setFont(new Font("Arial",Font.PLAIN,24) );
		g.drawString(menu[0],gamePanel.WIDTH/2,gamePanel.HEIGHT/2 + 40);
		g.drawString(menu[1],gamePanel.WIDTH/2,gamePanel.HEIGHT/2 + 65);
		
		//change our arrow based on input
		if(state == 0)
		{
			g.drawString(">",gamePanel.WIDTH/2 - 50,gamePanel.HEIGHT/2 + 40);
		}
		else if(state == 1)
		{
			g.drawString(">",gamePanel.WIDTH/2 - 50,gamePanel.HEIGHT/2 + 65);
		}
	}
}
