package Entity;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;


import javax.imageio.ImageIO;

import Controls.keyHandler;
import Main.gamePanel;
import World.Sprites;

public class Player 
{
	//we need out 3 diffrenet vectors to for out viewing cone, direction facing, current position and the distance from plane
	public Vector pos;
	private Vector dir;
	private Vector plane;
	
	//out var speeds
	public final double MOVESPEED = .08;
	public final double ROTSPEED = .045;
	
	//All our sprites to be render by the camera, which is the player; We store them in this file like this for quick access to save on look up costs
	private Sprites brick = Sprites.tex1;
	private Sprites block = Sprites.tex2;
	private Sprites wood = Sprites.tex3;
	private Sprites door = Sprites.tex5;
	//Our enemy types
	private Sprites enemy = Sprites.tex4;
	private Sprites megaenemy = Sprites.tex6;
	//The list of all our sprites
	private Sprites[] listOfSprites = {wood,block,brick,door,enemy,megaenemy};
	
	//getting the resources for drawing the gun; we use tick to determine when to update our animation
	private String temp = Sprites.class.getClassLoader().getResource("./Resources/Textures").toString().replaceAll("%20", " ");
	private String path = temp.substring(temp.indexOf(":")+1);
	private String[] gunFrames = {path+"/Entities/gun2.png",path+"/Entities/gun3.png"};
	private int tick =0;
	
	//basic booleans to determine when a player can shoot (when they see an enemy) if a player is shooting, and if an enemy has beenshot (isEnenmyDead)
	private boolean isShooting = false;
	private boolean canShoot = true;
	private boolean isEnemyDead = false;
	
	//Z buffer to determine what we can and cant see
	private double[] zbuffer;
	
	public Player(double x, double y,double dirX, double dirY, double xPlane,double yPlane)
	{
		
		pos = new Vector(x,y);
		dir = new Vector(dirX,dirY);
		plane = new Vector(xPlane,yPlane);
	}
	
	//update postion on the map
	public void update(int[][] map)
	{
		input(map);
	}
	
	//based on key input update what is being rendered and player postion
	public void input(int[][] map)
	{
		
		if(keyHandler.isPressed(keyHandler.UP))
		{
			//scalar addition to get the new location
			if(map[(int) ( pos.getX() +dir.getX() *MOVESPEED)][(int) pos.getY()] ==0)
			{
				pos.setX(pos.getX()+dir.getX()*MOVESPEED);
			}
			
			if(map[(int) pos.getX()][(int) ( pos.getY() +dir.getY() *MOVESPEED)] ==0)
			{
				pos.setY(pos.getY()+dir.getY()*MOVESPEED);
			}

		}
		if(keyHandler.isPressed(keyHandler.DOWN))
		{
			//scalar addition to get the new location
			if(map[(int) ( pos.getX() -dir.getX() *MOVESPEED)][(int) pos.getY()] ==0)
			{
				pos.setX(pos.getX()-dir.getX()*MOVESPEED);
			}
			
			if(map[(int) pos.getX()][(int) ( pos.getY() -dir.getY() *MOVESPEED)] ==0)
			{
				pos.setY(pos.getY()-dir.getY()*MOVESPEED);
			}

		}
		if(keyHandler.isPressed(keyHandler.RIGHT))
		{
			//matrix mult on our vector to get there new postions
			double oldxDir=dir.getX();
			dir.setX(dir.getX()*Math.cos(ROTSPEED) - dir.getY()*Math.sin(ROTSPEED));
			dir.setY(oldxDir*Math.sin(ROTSPEED) + dir.getY()*Math.cos(ROTSPEED));
			
			double oldxPlane = plane.getX();
			plane.setX(plane.getX()*Math.cos(ROTSPEED) - plane.getY()*Math.sin(ROTSPEED));
			plane.setY(oldxPlane*Math.sin(ROTSPEED) + plane.getY()*Math.cos(ROTSPEED));
		}
		if(keyHandler.isPressed(keyHandler.LEFT))
		{
			//matrix mult on our vector to get there new postions
			double oldxDir=dir.getX();
			dir.setX(dir.getX()*Math.cos(-ROTSPEED) - dir.getY()*Math.sin(-ROTSPEED));
			dir.setY(oldxDir*Math.sin(-ROTSPEED) + dir.getY()*Math.cos(-ROTSPEED));
			
			double oldxPlane = plane.getX();
			plane.setX(plane.getX()*Math.cos(-ROTSPEED) - plane.getY()*Math.sin(-ROTSPEED));
			plane.setY(oldxPlane*Math.sin(-ROTSPEED) + plane.getY()*Math.cos(-ROTSPEED));
		}
		
		//If we see an enemy we can shoot them
		if(keyHandler.isPressed(keyHandler.SHOOT) && canShoot) 
		{
			
			isShooting = true;
			isEnemyDead = true;
		}
		

		
	}
	
	//we draw the world, the player is the camera. MOST OF THE WORK
	public void draw(int[][] map, int[] buff, int width, int height)
	{
		//creating our z buffer depth
		zbuffer = new double[width];
		

		//we need to draw for each column we see, so we the screen width many cols
		for(int x =0;x<width;x++)
		{
			
			//Distance to the wall
			double perpWallDist;
		
			//what direction do we move in, used for calculating our distance from the wall
			int stepX, stepY;
			
			//determine if a ray hits a wall
			boolean hit = false;
			//special case where ray hits a wall
			
			
			//calculate how high our wall is
			int lineHeight;
			
			Vector sideDist = new Vector();
			
			
			//we calculate our cameras view
			double view = 2*x/(double) width-1;
			//we then need to calculate the ends of our screen
			Vector rayDir = new Vector(dir.getX()+plane.getX()*view, dir.getY()+plane.getY()*view);
			
			//get the absolute postion on the map we are so we can calculate the distance to  a wall
			int absMapX =  (int) pos.getX();
			int absMapY =  (int) pos.getY();
				
			Vector deltaDist = new Vector(Math.abs(1/rayDir.getX()),Math.abs(1/rayDir.getY()));
			
			
			
			//Calculate our distance from the wall
			if(rayDir.getX()  < 0)
			{
				stepX = -1;
				sideDist.setX( (pos.getX()-absMapX) *deltaDist.getX());
			}
			else
			{
				stepX = 1;
				sideDist.setX( (absMapX + 1 - pos.getX()) *deltaDist.getX());
			}
			
			if(rayDir.getY()  < 0)
			{
				stepY = -1;
				sideDist.setY( (pos.getY()-absMapY) *deltaDist.getY());
			}
			else
			{
				stepY = 1;
				sideDist.setY( (absMapY + 1 - pos.getY()) *deltaDist.getY());
			}
			
			
			int side =0;
			// if we didnt hit anything we need to calculate which col to render on
			while(!hit)
			{
				if(sideDist.getX() < sideDist.getY())
				{
					sideDist.setX(sideDist.getX()+deltaDist.getX());
					absMapX+=stepX;
					side =0;
				}
				else
				{
					sideDist.setY(sideDist.getY()+deltaDist.getY());
					absMapY+=stepY;
					side = 1;
				}
				
				//if we hit a wall 
				if(map[absMapX][absMapY] > 0)
				{
					hit = true;
				}
			}
			
			//calculating the distance to the wall based on the player postion and their direction
			if(side == 0)
			{
				perpWallDist = Math.abs( (absMapX - pos.getX() + (1-stepX)/2)/rayDir.getX() );
			}
			else
			{
				perpWallDist = Math.abs( (absMapY - pos.getY() + (1-stepY)/2)/rayDir.getY() );
			}
			
			
			//based on distance of the wall deternime the height of the wall
			if(perpWallDist>0)
			{
				lineHeight = Math.abs((int) (height/perpWallDist));
			}
			else
			{
				lineHeight = height;
			}
			
			//get the start postion to start drawing; Start of viewing  plane
			int drawStart = -lineHeight/2 + height/2;
			
			if(drawStart <0)
			{
				drawStart =0;
			}
			// draw the end of the viewing plane
			int drawEnd = lineHeight/2 + height/2;
			
			if(drawEnd >= height)
			{
				drawEnd = height - 1;
			}
			
			//selectiong what texture number we have base on the number in the map
			int textNum = map[absMapX][absMapY] -1;
			
			//based on the wall type we calculate how the wall texture will appear
			double wallX;
			if(side == 1)
			{
				wallX = (pos.getX() + ((absMapY - pos.getY() + (1-stepY)/2)/rayDir.getY()) *rayDir.getX());
			}
			else
			{
				wallX = (pos.getY() + ((absMapX - pos.getX() + (1-stepX)/2)/rayDir.getX()) *rayDir.getY());
			}
			
			wallX -= Math.floor(wallX);
			
			int texX = (int) (wallX*(64));
			
			if( (side == 0 && rayDir.getX() > 0) || (side == 1 && rayDir.getY() < 0))
			{
				texX = 64 - texX -1;
			}
			
			//draw from the bottom to the top
			for(int y = drawStart;y<drawEnd;y++)
			{
				//calculate the y offset for the texture
				int texY = (((y*2 - height +lineHeight) << 6)/lineHeight)/2;
				
				int color =0xffffff;//White incase of errors in file
				
				//choose between a dark side and a lighter side
				if(side == 0)
				{
					color = listOfSprites[textNum].pixelArray[texX+(texY*64)];
				}
				else
				{
					//bit shift here creates a psuedo shadow effect
					color = (listOfSprites[textNum].pixelArray[texX+(texY*64)] >> 1) & 8355711;
				}
				
				//add the pxiel to our screen
				buff[x + (y*width)] = color;
			}
			
			//Floor start
			zbuffer[x] = perpWallDist;
			
			//distance to the floor we have 4 possible options for rendering textures based on a wall hit
			 Vector floorToWall = new Vector();
			 
			 //north
			 if(side == 0 && rayDir.getX() >0)
			 {
				 floorToWall.setX(absMapX);
				 floorToWall.setY(absMapY+wallX); 
			 }
			 //east
			 else if(side ==0 && rayDir.getX() < 0)
			 {
				 floorToWall.setX(absMapX+1);
				 floorToWall.setY(absMapY+wallX); 
			 }
			 //south
			 else if(side == 1 && rayDir.getY() >0)
			 {
				 floorToWall.setX(absMapX+wallX);
				 floorToWall.setY(absMapY); 
			 }
			 //west
			 else
			 {
				 floorToWall.setX(absMapX+wallX);
				 floorToWall.setY(absMapY+1); 
			 }
			 
			 double distWall, distPlayer, curDist;
			 
			 distWall = perpWallDist;
			 distPlayer = 0.0;
			 
			 //draw the top and bottom of the screen based on the floors length
			 for(int y = drawEnd+1;y<height;y++)
			 {
				 curDist = height/(2.0*y-height); // calcuate the texture offset for floors/ceilings
				 
				 //distance of wall to the player so we can calculate the floor postions
				 double weight = (curDist -distPlayer)/(distWall - distPlayer);
				 Vector curFloor = new Vector(weight * floorToWall.getX() + (1.0 - weight) * pos.getX(),weight * floorToWall.getY() + (1.0 - weight) * pos.getY());
				 
				 //calculate the offesets
				int floorTextX = (int) ((curFloor.getX()*64) %64);
				int floorTextY = (int) ((curFloor.getY()*64) %64);
				
		        buff[x + y*width] = (listOfSprites[2].pixelArray[64 * floorTextY + floorTextX]); // floor
		        buff[x + (height-y)*(width)] = listOfSprites[0].pixelArray[64 * floorTextY + floorTextX]; // ceiling
			 }
			 
		}
			
		
	}
	
	//adding our enemy to the players view
	public void drawEnemy(Zombie z, int[] buffer,int width, int height, int type)
	{
		//determine what enemy to draw, if type 1 we draw a normal zomibe, else we draw mega zombie
		int index = 0;
		if(type == 1)
		{
			index = 4;
		}
		else if(type == 2)
		{
			index = 5;
		}
		
		//getting the enemies postion
		Vector enemyLoc = new Vector(z.pos.getX()-pos.getX(),z.pos.getY()-pos.getY());
		
		//getting the inverse of the matrix, to project sprite
		double invDet = 1.0/ (plane.getX() * dir.getY() - dir.getX()*plane.getY());
		Vector transformation = new Vector (invDet *(dir.getY()*enemyLoc.getX() - dir.getX() *enemyLoc.getY() ),invDet *(-plane.getY()*enemyLoc.getX() + plane.getX() *enemyLoc.getY() )  );
		
		int spriteScreen = (int) ((width/2) * (1+transformation.getX()/transformation.getY()));
		
		//sprite dimension
		int spriteHeight = Math.abs( (int)(height/transformation.getY()) );
		
		//getting bottom "col" of the sprite
		int drawStartY = -spriteHeight/2 + height/2;
		
		if(drawStartY<0)
		{
			drawStartY =0;
		}
		
		//getting top "col" of the sprite
		int drawEndY = spriteHeight/2+height/2;
		
		if(drawEndY >= height)
		{
			drawEndY = height-1;
		}
		
		//same as above with height, just now with width
		int spriteWidth = Math.abs( (int)(height/transformation.getY()) );
		int drawStartX = -spriteWidth/2+spriteScreen;
		
		if(drawStartX<0)
		{
			drawStartX =0;
		}
		
		int drawEndX = spriteWidth/2+spriteScreen;
		
		if(drawEndX>=width)
		{
			drawEndX = width -1;
		}
		
		//start draw like we did with colmuns, but do so based on the pixel heights we calculated 
		for(int stripe = drawStartX;stripe<drawEndX;stripe++)
		{
			
			int texX = (int) (int) (256 * (stripe - (-spriteWidth / 2 + spriteScreen)) * 64 / spriteWidth) / 256;
			if(transformation.getY() > 0 && stripe > 0 && stripe < width && transformation.getY() < zbuffer[stripe]) //Z Buffer, we clip all things that we cant see
			{
				//if the statement is true we then can render the enemy and by proxy we can attack them
				canShoot = true;
				for(int y = drawStartY; y < drawEndY; y++)// pixel/stripe
				{
					//calc the texture
					int d = y*256 - height * 128 + spriteHeight *128;
					int texY = ((d * 64) / spriteHeight) / 256;
					int color = listOfSprites[index].pixelArray[64 * texY + texX];
					
					//removing transperancy, if needed to
					if(color != 0xffffff) 
			        	  buffer[(y*width)+stripe] = color;
				}
			}
			else
			{
				canShoot = false;
			}
			

		}
		
		
	}
	
	//getter for seeing if an enemy is dead
	public boolean getIsEnemyDead()
	{
		return isEnemyDead;
	}
	
	//basic function for drawin the gun
	public void drawWeapon(Graphics g)
	{
		try 
		{
			//init the gun
			BufferedImage gun = ImageIO.read(new File(gunFrames[0]) );
			
			//if we our currently shooting use the shooting image
			if(isShooting) 
			{
				//load in our gun picture
				 gun = ImageIO.read(new File(gunFrames[1]) );
				 g.drawImage(gun,gamePanel.WIDTH/2 - 64 ,gamePanel.HEIGHT -128,128,128,null);
				
				 //if 50 game updates have happened then stop drawing shooting animation
				if(tick > 50)
				{
					tick =0;
					isShooting = false;
				}
				
						
			}
			else // if not draw default gun
			{
				g.drawImage(gun,gamePanel.WIDTH/2 - 64 ,gamePanel.HEIGHT -128,128,128,null);
			}
				
			
		} 
		catch (Exception  e) 
		{
			e.printStackTrace();
			System.out.println("FIle not found");
		}
		//update on update tick
		tick++;
	}

	
}

