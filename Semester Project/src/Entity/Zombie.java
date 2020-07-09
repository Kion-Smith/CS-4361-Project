package Entity;

public class Zombie 
{
	//Our enemy class and parent to the mega zombie, this is more generic
	
	public Vector pos;
	public boolean isAlive;
	
	private static final double MOVESPEED = .03;
	
	//constructor
	public Zombie(double x, double y)
	{
		pos = new Vector(x,y);
		isAlive = true;
	}
	
	//once we get a copy of the map and the plauer location follow them
	public void followPlayer(int playerX, int playerY, int map[][])
	{
		
		//If we dont have somthing blocking us from moving follow player until our values match
		if(map[(int)(pos.getX())][(int)(pos.getY())] <= 1 )
		{
			if(playerX>pos.getX() )
			{
				pos.setX(pos.getX()+MOVESPEED);
			}
			else if(playerX<pos.getX())
			{
				pos.setX(pos.getX()-MOVESPEED);
			}
			
			
			if(playerY>pos.getY() )
			{
				pos.setY(pos.getY()+MOVESPEED);
			}
			else if (playerY<pos.getY())
			{
				pos.setY(pos.getY()-MOVESPEED);
			}
			
		}

	}
	
}
