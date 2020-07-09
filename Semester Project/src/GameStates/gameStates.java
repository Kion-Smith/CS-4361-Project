package GameStates;

import java.awt.Graphics;

//Abstract class for all the possible states
public abstract class gameStates 
{
	protected gameStateManager gsm;
	
	public gameStates(gameStateManager g)
	{
		gsm =g;
	}
	
	public abstract void init();
	public abstract void update();
	public abstract void draw(Graphics g, int[] buffer);
}
