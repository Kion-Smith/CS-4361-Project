package World;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class Sprites 
{
	public static int size = 64;
	public int[] pixelArray;
	
	String loc ="";
	static String temp = Sprites.class.getClassLoader().getResource("./Resources/Textures").toString().replaceAll("%20", " ");
	static String path = temp.substring(temp.indexOf(":")+1);
	
	public static Sprites tex2 = new Sprites(path+"/special.png");
	public static Sprites tex1 = new Sprites(path+"/ground.png");
	public static Sprites tex3 = new Sprites(path+"/brick.png");
	public static Sprites tex5 = new Sprites(path+"/door.png");
	
	public static Sprites tex4 = new Sprites(path+"/zombie.png");
	//mega zombie add?
	
	public static Sprites tex6 = new Sprites(path+"/megaZombie.png");

	
	public Sprites(String s)
	{
		loc = s;
		pixelArray = new int[size*size];
		load();
	}
	
	public void load()
	{
		try
		{
			BufferedImage image = ImageIO.read(new File(loc));
			int w = image.getWidth();
			int h = image.getHeight();
			image.getRGB(0, 0,w,h,pixelArray,0,w);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	
}
