package Main;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.File;

import javax.swing.JPanel;

import Controls.keyHandler;
import GameStates.gameStateManager;
import World.Level;

@SuppressWarnings("serial")
public class gamePanel extends JPanel implements Runnable,KeyListener
{
	//Basic window size, must be low to render quickly
	public static final int WIDTH = 600;
	public static final int HEIGHT = 400;
	
	//Speed at which the game runs
	public static final int FPS = 30;

	public boolean isRunning = false;
	
	//Create a thread for the game to run on
	private Thread gameThread;
	
	//BufferedImage to draw indvidual pixels, and full iamges
	private BufferedImage image;
	
	//For drawing graphics to the screen
	private Graphics2D g;
	
	//user defined class for keeping track of the states of our game
	private gameStateManager gsm;
	
	//The screen buffer, all the pixels being drawn to the screen
	int[] buffer;
	
	//Setting up out JPanel
	public gamePanel()
	{
		setPreferredSize(new Dimension (WIDTH,HEIGHT));
		setFocusable(true);
		requestFocus();
	}
	
	//initializing our variables
	public void init()
	{
		
		
		isRunning = true;
		image = new BufferedImage(WIDTH,HEIGHT,BufferedImage.TYPE_INT_RGB);
		g = (Graphics2D) image.getGraphics();
		buffer = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
		
		gsm = new gameStateManager();
		
	}	
	
	//We override the addnotify method so that we can also create our thread and implement keylistner on said thread
	public void addNotify()
	{
		super.addNotify();
		
		if(gameThread == null)
		{
			gameThread = new Thread(this);
			addKeyListener(this);
			gameThread.start();
		}
		
	}

	//Main loop of the game
	public void run() 
	{
		//init vars
		init();
		
		//getting time in nano
		double ns =1000000000.0 / FPS;// fps in nano =seconds
		double delta = 0;//used to determine when to render or update, used to keep fps at 30
		int frames = 0; // SHOULD BE 30, frames we see per second
		int updates = 0; // Amount of updates done every few milliseconds
		long lastTime = System.nanoTime();
		long timer = System.currentTimeMillis();
		
		
		while(isRunning)
		{
			//get the current time
			long curTime = System.nanoTime();
			
			//subtract last iteration time from current divided by ns to deterine our delta, if delta is greater than 1 we can update
			delta += (curTime - lastTime) / ns;
			lastTime = curTime;
			
			//The reasoing behind this delta is we want to keep a fixed fps but want to keep game logic the same. So we redraw on frames, and do game logic on update
			while(delta >= 1)
			{
				update();
				updates++;
				
				delta--;
			}
			
			render();
			frames++;
			
	        if(System.currentTimeMillis() - timer >= 1000) //ever 1 second print this
	        {
	        	//This was used to see how our game ran not nessicary
	        	//System.out.println("Game Performaces" + "  |  " + updates + " ups, " + frames + " fps");
	        	
	        	//Reset our values to use again
	        	timer += 1000;
	        	frames = 0;
	        	updates = 0;
	        }
	        
	        
		}
		
		//incase we get out of our main loop we close our program to avoid errors
		System.out.println("Unexpected exit");
		System.exit(-1);

	}
	
	public void update()
	{
		//update based on the keys being pressed and the current state of the game
		gsm.update();
		keyHandler.update();
	}
	

	//drawing the screen
	public void render()
	{
		Graphics g2 = getGraphics();
		
		//Sending over our graphics compoent and our screen to our game state so that it can draw
		gsm.draw(g, buffer);
		
		//draw our current buffredimage (buffer array)
		g2.drawImage(image, 0,0, WIDTH,HEIGHT,null);
		g2.dispose();//get rid of old image
		
	}

	//Keeping track of keyinputs in another file, better practice and better for threads
	public void keyPressed(KeyEvent k) 
	{
		keyHandler.keySet(k.getKeyCode(), true);
	}

	public void keyReleased(KeyEvent k) 
	{
		keyHandler.keySet(k.getKeyCode(), false);
	}
	
	public void keyTyped(KeyEvent arg0) 
	{}
	


}

