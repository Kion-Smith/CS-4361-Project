package Entity;

public class Vector 
{
	public double x;
	public double y;
	
	public Vector( )
	{
		x = 0;
		y =0;
	}
	public Vector(double x, double y)
	{
		this.x = x;
		this.y =y;
	}
	
	public double getX()
	{
		return x;
	}
	
	public double getY()
	{
		return y;
	}
	
	public void setX(double x)
	{
		this.x =x;
	}
	
	public void setY(double y)
	{
		this.y =y;
	}
}
