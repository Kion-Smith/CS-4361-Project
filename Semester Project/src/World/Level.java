package World;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import Entity.Vector;

public class Level
{
	public int wWidth,wHeight;
	
	public int[][] board;
	
	public static final int DOORVAL = 4;
	public static Vector doorLoc;
	
	public Level()
	{
	
	}
	
	public Level(File f)
	{
		Scanner kb;
		try 
		{
			kb = new Scanner(f);
			String temp = kb.nextLine();
			
			wWidth = Integer.parseInt(temp.substring(0,temp.indexOf(".")));
			wHeight = Integer.parseInt(temp.substring(temp.indexOf(".")+1));
			
			board = new int[wHeight][wWidth];

			String curLine ="";
			int i =0;
			while(kb.hasNextLine())
			{
				curLine = kb.next();
				for(int j =0;j<curLine.length();j++)
				{
					board[i][j] = Integer.parseInt(curLine.charAt(j)+"");
					
					if(board[i][j] == DOORVAL)
					{
						doorLoc = new Vector(i,j-1);
					}
					
					if(j+1 == wWidth)
					{
						i++;
					}
				}
			}
				
			
		} 
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
			System.out.println("DEBUG:ERROR IN WORLD CREATION");
		}
		
		
	}
	
	public int getBoardWidth()
	{
		return wWidth;
	}
	
	public int getBoardHeight()
	{
		return wHeight;
	}
	
	public int[][] getBoard()
	{
		return board;
	}
}
