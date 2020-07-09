package Controls;

import java.awt.event.KeyEvent;

public class keyHandler 
{
	//All the possible keys we have mapped
	public static final int UP = 0;
	public static final int DOWN = 1;
	public static final int LEFT = 2;
	public static final int RIGHT = 3;
	public static final int INTERACT = 4;
	public static final int PAUSE = 5;
	public static final int SHOOT =6;
	
	//Length of our key mapping
	public static final int keyLength = 7;
	
	//keep track of our current state and previous ones; previous ones are legacy code
	public static boolean keyState[] = new boolean[keyLength];
	public static boolean preKeyState[] = new boolean[keyLength];
	
	//Depending on if they key is being pressed or not update accordingly
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
		else if(code == KeyEvent.VK_A  || code == KeyEvent.VK_A ) 
		{
			keyState[LEFT] = b;
		}
		else if(code == KeyEvent.VK_D|| code == KeyEvent.VK_D  ) 
		{
			keyState[RIGHT] = b;
		}
		else if(code == KeyEvent.VK_E || code == KeyEvent.VK_ENTER ) 
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
	
	//Update the old key list
	public static void update()
	{
		for(int i = 0;i<keyLength;i++)
		{
			preKeyState[i] = keyState[i];
		}
	}
	
	//To be use by our functions to see ig a key is currently being pressed
	public static boolean isPressed(int i) 
	{
		if(keyState[i])
		{
			return true;
		}
		
		return false;
	}
	
}
