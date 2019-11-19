package Entity;

import Controls.keyHandler;
import World.Sprites;

public class Player 
{
	public Vector pos;
	Vector dir;
	Vector plane;
	
	public final double MOVE_SPEED = .08;
	public final double ROTATION_SPEED = .045;
	
	Sprites brick = Sprites.tex1;
	Sprites block = Sprites.tex2;
	Sprites wood = Sprites.tex3;
	Sprites door = Sprites.tex5;
	
	Sprites enemy = Sprites.tex4;
	
	Sprites[] listOfSprites = {wood,block,brick,door,enemy};
	
	double[] zbuffer;
	
	public Player(double x, double y,double dirX, double dirY, double xPlane,double yPlane)
	{
		pos = new Vector(x,y);
		dir = new Vector(dirX,dirY);
		plane = new Vector(xPlane,yPlane);
	}
	
	public void update(int[][] map)
	{
		input(map);
	}
	
	public void input(int[][] map)
	{
		
		if(keyHandler.isPressed(keyHandler.UP))
		{
			if(map[(int) ( pos.getX() +dir.getX() *MOVE_SPEED)][(int) pos.getY()] ==0)
			{
				pos.setX(pos.getX()+dir.getX()*MOVE_SPEED);
			}
			
			if(map[(int) pos.getX()][(int) ( pos.getY() +dir.getY() *MOVE_SPEED)] ==0)
			{
				pos.setY(pos.getY()+dir.getY()*MOVE_SPEED);
			}

		}
		if(keyHandler.isPressed(keyHandler.DOWN))
		{
			if(map[(int) ( pos.getX() -dir.getX() *MOVE_SPEED)][(int) pos.getY()] ==0)
			{
				pos.setX(pos.getX()-dir.getX()*MOVE_SPEED);
			}
			
			if(map[(int) pos.getX()][(int) ( pos.getY() -dir.getY() *MOVE_SPEED)] ==0)
			{
				pos.setY(pos.getY()-dir.getY()*MOVE_SPEED);
			}

		}
		if(keyHandler.isPressed(keyHandler.RIGHT))
		{
			double oldxDir=dir.getX();
			dir.setX(dir.getX()*Math.cos(ROTATION_SPEED) - dir.getY()*Math.sin(ROTATION_SPEED));
			dir.setY(oldxDir*Math.sin(ROTATION_SPEED) + dir.getY()*Math.cos(ROTATION_SPEED));
			
			double oldxPlane = plane.getX();
			plane.setX(plane.getX()*Math.cos(ROTATION_SPEED) - plane.getY()*Math.sin(ROTATION_SPEED));
			plane.setY(oldxPlane*Math.sin(ROTATION_SPEED) + plane.getY()*Math.cos(ROTATION_SPEED));
		}
		if(keyHandler.isPressed(keyHandler.LEFT))
		{
			double oldxDir=dir.getX();
			dir.setX(dir.getX()*Math.cos(-ROTATION_SPEED) - dir.getY()*Math.sin(-ROTATION_SPEED));
			dir.setY(oldxDir*Math.sin(-ROTATION_SPEED) + dir.getY()*Math.cos(-ROTATION_SPEED));
			
			double oldxPlane = plane.getX();
			plane.setX(plane.getX()*Math.cos(-ROTATION_SPEED) - plane.getY()*Math.sin(-ROTATION_SPEED));
			plane.setY(oldxPlane*Math.sin(-ROTATION_SPEED) + plane.getY()*Math.cos(-ROTATION_SPEED));
		}
		

		
	}
	
	public void draw(int[][] map, int[] buff, int width, int height)
	{
		
		zbuffer = new double[width];
		
		for(int x =0;x<width;x++)
		{
			double view = 2*x/(double) width-1;
			Vector rayDir = new Vector(dir.getX()+plane.getX()*view, dir.getY()+plane.getY()*view);
			
			int absMapX =  (int) pos.getX();
			int absMapY =  (int) pos.getY();
			
			Vector sideDist = new Vector();
			Vector deltaDist = new Vector(Math.abs(1/rayDir.getX()),Math.abs(1/rayDir.getY()));
			double perpWallDist;
			
			int stepX, stepY;
			boolean hit = false;
			
			int side =0;
			
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
				
				if(map[absMapX][absMapY] > 0)
				{
					hit = true;
				}
			}
			
			if(side == 0)
			{
				perpWallDist = Math.abs( (absMapX - pos.getX() + (1-stepX)/2)/rayDir.getX() );
			}
			else
			{
				perpWallDist = Math.abs( (absMapY - pos.getY() + (1-stepY)/2)/rayDir.getY() );
			}
			
			int lineHeight;
			
			if(perpWallDist>0)
			{
				lineHeight = Math.abs((int) (height/perpWallDist));
			}
			else
			{
				lineHeight = height;
			}
			
			int drawStart = -lineHeight/2 + height/2;
			
			if(drawStart <0)
			{
				drawStart =0;
			}
			
			int drawEnd = lineHeight/2 + height/2;
			
			if(drawEnd >= height)
			{
				drawEnd = height - 1;
			}
			
			int textNum = map[absMapX][absMapY] -1;
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
			
			for(int y = drawStart;y<drawEnd;y++)
			{
				int texY = (((y*2 - height +lineHeight) << 6)/lineHeight)/2;
				
				int color =0xffffff;//White incase of errors
				
				
				if(side == 0)
				{
					color = listOfSprites[textNum].pixelArray[texX+(texY*64)];
				}
				else
				{
					color = (listOfSprites[textNum].pixelArray[texX+(texY*64)] >> 1) & 8355711;
				}
				
				buff[x + (y*width)] = color;
			}
			
			//Floor start
			zbuffer[x] = perpWallDist;
			 Vector floorToWall = new Vector();
			 
			 if(side == 0 && rayDir.getX() >0)
			 {
				 floorToWall.setX(absMapX);
				 floorToWall.setY(absMapY+wallX); 
			 }
			 else if(side ==0 && rayDir.getX() < 0)
			 {
				 floorToWall.setX(absMapX+1);
				 floorToWall.setY(absMapY+wallX); 
			 }
			 else if(side == 1 && rayDir.getY() >0)
			 {
				 floorToWall.setX(absMapX+wallX);
				 floorToWall.setY(absMapY); 
			 }
			 else
			 {
				 floorToWall.setX(absMapX+wallX);
				 floorToWall.setY(absMapY+1); 
			 }
			 
			 double distWall, distPlayer, curDist;
			 
			 distWall = perpWallDist;
			 distPlayer = 0.0;
			 
			 for(int y = drawEnd+1;y<height;y++)
			 {
				 curDist = height/(2.0*y-height);
				 
				 double weight = (curDist -distPlayer)/(distWall - distPlayer);
				 
				 Vector curFloor = new Vector(weight * floorToWall.getX() + (1.0 - weight) * pos.getX(),weight * floorToWall.getY() + (1.0 - weight) * pos.getY());
				 
				int floorTextX = (int) ((curFloor.getX()*64) %64);
				int floorTextY = (int) ((curFloor.getY()*64) %64);
				
		        buff[x + y*width] = (listOfSprites[2].pixelArray[64 * floorTextY + floorTextX]);
		        buff[x + (height-y)*(width)] = listOfSprites[2].pixelArray[64 * floorTextY + floorTextX];
			 }
			 
		}
			
		
	}
	
	public void addEnity(Zombie z, int[] buffer,int width, int height)
	{
		
		Vector enemyLoc = new Vector(z.pos.getX()-pos.getX(),z.pos.getY()-pos.getY());
		double invDet = 1.0/ (plane.getX() * dir.getY() - dir.getX()*plane.getY());
		
		Vector transformation = new Vector (invDet *(dir.getY()*enemyLoc.getX() - dir.getX() *enemyLoc.getY() ),invDet *(-plane.getY()*enemyLoc.getX() + plane.getX() *enemyLoc.getY() )  );
		
		int spriteScreen = (int) ((width/2) * (1+transformation.getX()/transformation.getY()));
		
		int spriteHeight = Math.abs( (int)(height/transformation.getY()) );
		int drawStartY = -spriteHeight/2 + height/2;
		
		if(drawStartY<0)
		{
			drawStartY =0;
		}
		
		int drawEndY = spriteHeight/2+height/2;
		
		if(drawEndY >= height)
		{
			drawEndY = height-1;
		}
		
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
		
		for(int stripe = drawStartX;stripe<drawEndX;stripe++)
		{
			int texX = (int) (int) (256 * (stripe - (-spriteWidth / 2 + spriteScreen)) * 64 / spriteWidth) / 256;
			if(transformation.getY() > 0 && stripe > 0 && stripe < width && transformation.getY() < zbuffer[stripe])
			{
				for(int y = drawStartY; y < drawEndY; y++)
				{
					int d = y*256 - height * 128 + spriteHeight *128;
					int texY = ((d * 64) / spriteHeight) / 256;
					int color = listOfSprites[4].pixelArray[64 * texY + texX];
					
					if(color != 0xffffff) 
			        	  buffer[(y*width)+stripe] = color;
				}
			}
			

		}
		
		
	}
	

	
}

