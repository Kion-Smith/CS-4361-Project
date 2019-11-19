package Project.Screen;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class World
{
	public int wWidth,wHeight;
	
	public World()
	{
	
	}
	
	public World(File f)
	{
		Scanner kb;
		try 
		{
			kb = new Scanner(f);
			String temp = kb.nextLine();
			
			//wWidth = Integer.parseInt(temp.substring(0,temp.indexOf(".")));
			//wHeight = Integer.parseInt(temp.substring(temp.indexOf(".")+1));
			
			//System.out.println(wHeight+" "+wWidth);
			
			System.out.println("RAN");
			
		} 
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
			System.out.println("DEBUG:ERROR IN WORLD CREATION");
		}
		
		
	}
}
