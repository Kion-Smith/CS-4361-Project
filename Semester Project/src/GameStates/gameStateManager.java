package GameStates;

import java.awt.Graphics;

public class gameStateManager 
{
	public boolean paused;
	private pauseState pS;
	
	private gameStates[] gS;
	private int curState;
	private int prevState;
	
	public static final int STATECOUNT = 4;
	public static final int MENU = 0;
	public static final int PLAY = 1;
	public static final int HELP = 2;
	public static final int GAMEOVER = 3;
	
	public gameStateManager()
	{
		paused = false;
		pS = new pauseState(this);
		gS = new gameStates[STATECOUNT];
		
		setState(PLAY);
	}
	
	public void setState(int s)
	{
		prevState = curState;
		unloadState(prevState);
		curState = s;
		
		switch(s)
		{
			case MENU:
				gS[s] = new menuState(this);
				gS[s].init();	
				break;
			case PLAY:
				gS[s] = new playState(this);
				gS[s].init();				
				break;
			case HELP:
				gS[s] = new helpState(this);
				gS[s].init();
				break;
			case GAMEOVER:
				gS[s] = new gameOverState(this);
				gS[s].init();
				break;
		}
	}
	
	public void setPause(boolean b)
	{
		paused = b;
	}
	
	public void unloadState(int s)
	{
		gS[s] = null;
	}
	
	public void update()
	{
		if(paused)
		{
			pS.update();
		}
		else if(gS[curState] != null)
		{
			gS[curState].update();
		}
	}
	
	public void draw(Graphics g, int[] buffer)
	{
		if(paused)
		{
			pS.draw(g,buffer);
		}
		else if(gS[curState] != null)
		{
			gS[curState].draw(g,buffer);
		}
	}
	
}
