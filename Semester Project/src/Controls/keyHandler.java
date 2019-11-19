package Controls;

import java.awt.event.KeyEvent;

public class keyHandler 
{
	public static final int UP = 0;
	public static final int DOWN = 1;
	public static final int LEFT = 2;
	public static final int RIGHT = 3;
	
	public static final int INTERACT = 4;
	public static final int PAUSE = 5;
	
	public static final int SHOOT =6;
	
	public static final int keyLength = 7;
	
	public static boolean keyState[] = new boolean[keyLength];
	public static boolean preKeyState[] = new boolean[keyLength];
	
	public static void keySet(int code, boolean b)
	{
		if(code == KeyEvent.VK_UP || code == KeyEvent.VK_W ) 
		{
			keyState[UP] = b;
		}
		else if(code == KeyEvent.VK_DOWN|| code == KeyEvent.VK_S ) 
		{
			keyState[DOWN] = b;
		}
		else if(code == KeyEvent.VK_A  ) 
		{
			keyState[LEFT] = b;
		}
		else if(code == KeyEvent.VK_D ) 
		{
			keyState[RIGHT] = b;
		}
		else if(code == KeyEvent.VK_E ) 
		{
			keyState[INTERACT] = b;
		}
		else if(code == KeyEvent.VK_SPACE)
		{
			keyState[SHOOT] = b;
		}
		else if(code == KeyEvent.VK_ESCAPE) 
		{
			keyState[PAUSE] = b;
		}
	}
	
	public static void update()
	{
		for(int i = 0;i<keyLength;i++)
		{
			preKeyState[i] = keyState[i];
		}
	}
	
	public static boolean isPressed(int i) 
	{
		if(keyState[i])
		{
			return true;
		}
		
		return false;
	}
}
