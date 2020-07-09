package Entity;

//Custom vector class so that we can easily store vectors instead of using an array or two vars
public class Vector 
{
	public double x;
	public double y;
	
	//basic constructor
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
	
	//basic setters and getters
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
