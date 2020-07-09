package GameStates;

import java.awt.Graphics;

//Manages our game states
public class gameStateManager 
{
	public boolean paused;
	
	//we keep track of our game states in a array except for pause because it is a special function that stops the thread
	private pauseState pS;
	private gameStates[] gS;
	private int curState;
	private int prevState;
	
	//Static vars for use to call outside of gsm
	public static final int STATECOUNT = 5;
	public static final int MENU = 0;
	public static final int PLAY = 1;
	public static final int HELP = 2;
	public static final int GAMEOVER = 3;
	public static final int WIN = 4;
	
	public gameStateManager()
	{
		//init se the game not to pause and set the game to the menu
		paused = false;
		pS = new pauseState(this);
		gS = new gameStates[STATECOUNT];
		
		setState(MENU);
	}
	
	//used to select the current state, uses switch statement to keep track of this
	public void setState(int s)
	{
		//Keep track of our previous state in case of errors
		prevState = curState;
		unloadState(prevState);
		
		//load the next state
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
			case WIN:
				gS[s] = new winState(this);
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
	
	//update based on the game state
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
	
	//render based on game state
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
