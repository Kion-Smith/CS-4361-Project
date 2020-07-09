package Entity;

public class megaZombie extends Zombie
{
	int lives = 30;
	private final double MOVESPEED = .09;
	
	public megaZombie(double x, double y) 
	{
		super(x, y);
	}
	
	
	public int getLives()
	{
		return lives;
	}
	
	public void removeLives()
	{
		if(lives > 0)
		{
			lives--;
		}
	
	}
}
