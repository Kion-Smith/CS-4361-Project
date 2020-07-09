package GameStates;

import java.awt.Graphics;
import java.io.File;

import Controls.keyHandler;
import Entity.Player;
import Entity.Zombie;
import Entity.megaZombie;
import Main.gamePanel;
import World.Level;

public class playState extends gameStates 
{
	int curLevel =1;
	
	private Level level;
	private Player p;
	private Zombie z;
	private megaZombie mz;
	
	public playState(gameStateManager gsm) 
	{
		super(gsm);
	}

	public void init() 
	{
		//init used to get the file path and load in our current first level
		String path = this.getClass().getClassLoader().getResource("./Resources/level1.txt").toString().replaceAll("%20", " ");
		path = path.substring(path.indexOf(":")+1);
		File f = new File(path);
		level = new Level(f);
		
		//adding our players on to the map, we use doubles because our input would be rigid if it was based ona grid
		p = new Player(4,4,1,0,0,.90);
		z = new Zombie(7.5,4.5);
	}
	
	public void changeLevel()
	{
		//Same as init except we add the boss instead of a standard enemy
		String path = this.getClass().getClassLoader().getResource("./Resources/level2.txt").toString().replaceAll("%20", " ");
		path = path.substring(path.indexOf(":")+1);
		File f = new File(path);
		level = new Level(f);
		
		p = new Player(4,4,1,0,0,.90);
		mz = new megaZombie(7.5,4.5);
	}

	public void update() 
	{
		//Both functions deal with our user input, one on the map and then ther other for pausing in the play state
		inputHandler();
		p.update(level.getBoard());
		
		//if an enemy has been hit either kill them or remove a life in the case of the boss
		if(p.getIsEnemyDead())
		{
			if(curLevel == 1)
			{
				z.isAlive = false;
			}
			else if(curLevel == 2)
			{
				if(mz.getLives()>0)
				{
					mz.removeLives();
				}
				else
				{
					mz.isAlive = false;
				}
			}

		}
			
		//If the current level is one, then we check to see if the zombie is alive
		if( curLevel == 1 && z.isAlive)
		{
			//If alive then we need to follow the player
			z.followPlayer( (int) Math.ceil(p.pos.getX()), (int) Math.ceil(p.pos.getY()),level.getBoard());
			
			//If we catch the player then kill them
			if( (int) (z.pos.getX()) == (int) (p.pos.getX())  && (int) (z.pos.getY())  == (int) (p.pos.getY()) )
			{
				gsm.setState(gsm.GAMEOVER);
			}
		}
		else if(curLevel == 2 && mz.isAlive) // Same as before but instead for our mega zombie (the boss)
		{
			mz.followPlayer( (int) Math.ceil(p.pos.getX()), (int) Math.ceil(p.pos.getY()),level.getBoard());
			if( (int) (mz.pos.getX()) == (int) (p.pos.getX())  && (int) (mz.pos.getY())  == (int) (p.pos.getY()) )
			{
				gsm.setState(gsm.GAMEOVER);
			}
		}
		
		//If we are in front of a door and all enemies our dead then we can go to the next level
		if((int)p.pos.getX() == Level.doorLoc.getX() && (int)p.pos.getY() == Level.doorLoc.getY() && z.isAlive == false)
		{
			curLevel = 2;
			changeLevel();
		}
		
		// if the mega zombie is dead then instantly win
		if(curLevel == 2 && mz.isAlive == false)
		{
			gsm.setState(gsm.WIN);
		}

		
	}
	
	//Pause our game
	public void inputHandler()
	{
		if(keyHandler.isPressed(keyHandler.PAUSE))
		{
			gsm.setPause(true);
		}
	}

	public void draw(Graphics g, int[] buffer) 
	{
		//first draw 3d world
		p.draw(level.getBoard(), buffer, gamePanel.WIDTH, gamePanel.HEIGHT);
		
		//then draw the objects in the world
		if(curLevel ==1 && z.isAlive)
		{
			p.drawEnemy(z,buffer,gamePanel.WIDTH,gamePanel.HEIGHT,1);
		}
		else if(curLevel ==2 && mz.isAlive)
		{
			p.drawEnemy(mz,buffer,gamePanel.WIDTH,gamePanel.HEIGHT,2);
		}
		
		//then draw the gun
		p.drawWeapon(g);
	}

}
