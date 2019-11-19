package GameStates;

import java.awt.Graphics;
import java.io.File;

import Entity.Player;
import Entity.Zombie;
import Main.gamePanel;
import World.Level;

public class playState extends gameStates 
{
	int curLevel =1;
	
	Level level;
	Player p;
	Zombie z;
	
	public playState(gameStateManager gsm) 
	{
		super(gsm);
	}

	public void init() 
	{
		String path = this.getClass().getClassLoader().getResource("./Resources/WorldMap.txt").toString().replaceAll("%20", " ");
		path = path.substring(path.indexOf(":")+1);
		File f = new File(path);
		level = new Level(f);
		
		p = new Player(4,4,1,0,0,.90);
		z = new Zombie(7.5,4.5);
	}

	public void update() 
	{
		if(curLevel == 2)
		{
		
			String path = this.getClass().getClassLoader().getResource("./Resources/level2.txt").toString().replaceAll("%20", " ");
			path = path.substring(path.indexOf(":")+1);
			File f = new File(path);
			level = new Level(f);
			
			p = new Player(4,4,1,0,0,.90);
			
			
		}
		else
		{
			p.update(level.getBoard());
			
			if((int)p.pos.getX() == Level.doorLoc.getX() && (int)p.pos.getY() == Level.doorLoc.getY())
			{
				curLevel = 2;
			}
			
			//TODO laters stuff
			if(z.isAlive)
			{
				
			
			}
			

		}
	}

	public void draw(Graphics g, int[] buffer) 
	{
		p.draw(level.getBoard(), buffer, gamePanel.WIDTH, gamePanel.HEIGHT);
	
		if(z.isAlive)
		{
			p.addEnity(z,buffer,gamePanel.WIDTH,gamePanel.HEIGHT);
		}
	}

}
