package GameStates;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import Controls.keyHandler;
import Main.gamePanel;

public class menuState extends gameStates 
{
	//Menu for the main menu
	private String[]  menu= {"Start","Help","Quit"};
	private int state = 0;
	
	public menuState(gameStateManager g) 
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

	//handle our input
	public void handleInput() 
	{
		//if the user is pressing up do not allow them to go past the top most element
		if(keyHandler.isPressed(keyHandler.UP))
		{
			if(state >= menu.length-1)
			{
				state--;
			}
			else
			{
				state= 0;
			}
		}
		else if (keyHandler.isPressed(keyHandler.DOWN)) // same as before but for bottom most elements
		{
			if(state <= 0)
			{
				state++;
			}
			else
			{
				state= menu.length-1;
			}
		}
		else if (keyHandler.isPressed(keyHandler.INTERACT)) // When a user selects an element we must select the state to go to, or to exit the game
		{
			switch(state)
			{
				case 0:
					gsm.setState(gsm.PLAY);
					break;
				case 1:
					gsm.setState(gsm.HELP);
					break;
				case 2:
					System.exit(0);
					break;
				default:
					break;
			}
		}
	}

	//basic draw
	public void draw(Graphics g, int[] buffer) 
	{
		g.setColor(new Color(31, 77, 36));
		g.fillRect(0, 0, gamePanel.WIDTH, gamePanel.HEIGHT);
		
		g.setFont(new Font("Arial",Font.PLAIN,48));
		g.setColor(Color.BLACK);
		
		g.drawString("Raytracer", gamePanel.WIDTH/2 -((7)*12), gamePanel.HEIGHT/2 - 100);
		
		g.setFont(new Font("Arial",Font.PLAIN,24));
		g.setColor(Color.BLACK);
		
		g.drawString(menu[0], gamePanel.WIDTH/2 -((menu[0].length()-2)*6), gamePanel.HEIGHT/2);
		g.drawString(menu[1], gamePanel.WIDTH/2 -((menu[1].length()-1)*6), (gamePanel.HEIGHT/2) + 50);
		g.drawString(menu[2], gamePanel.WIDTH/2 -((menu[2].length()-1)*6), (gamePanel.HEIGHT/2) + 100);
		
		
		g.setColor(Color.RED);
		switch(state)
		{
			
			case 0:
				g.drawString(">", gamePanel.WIDTH/2 - 80, gamePanel.HEIGHT/2);
				g.drawString(menu[0], gamePanel.WIDTH/2 -((menu[0].length()-2)*6), gamePanel.HEIGHT/2);
				break;
			case 1:
				g.drawString(">", gamePanel.WIDTH/2 - 80, (gamePanel.HEIGHT/2) + 50);
				g.drawString(menu[1], gamePanel.WIDTH/2 -((menu[1].length()-1)*6), (gamePanel.HEIGHT/2) + 50);
				break;
			case 2:
				g.drawString(">", gamePanel.WIDTH/2 - 80, (gamePanel.HEIGHT/2) + 100);
				g.drawString(menu[2], gamePanel.WIDTH/2 -((menu[2].length()-1)*6), (gamePanel.HEIGHT/2) + 100);
				break;
			default:
				break;
		}
	}

}
