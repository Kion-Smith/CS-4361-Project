package GameStates;

import java.awt.Graphics;

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
