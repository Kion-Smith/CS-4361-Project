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
	public static final int WIDTH = 600;
	public static final int HEIGHT = 400;
	public static final int FPS = 30;

	public boolean isRunning = false;
	
	Thread gameThread;
	BufferedImage image;
	Graphics2D g;
	
	gameStateManager gsm;
	
	//dont need
	Level w;
	
	int[] buffer;
	
	
	public gamePanel()
	{
		setPreferredSize(new Dimension (WIDTH,HEIGHT));
		setFocusable(true);
		requestFocus();
	}
	
	public void init()
	{
		
		//Going to need to move this
		String path = this.getClass().getClassLoader().getResource("./Resources/WorldMap.txt").toString().replaceAll("%20", " ");
		path = path.substring(path.indexOf(":")+1);
		File f = new File(path);
		w = new Level(f);
		
		isRunning = true;
		image = new BufferedImage(WIDTH,HEIGHT,BufferedImage.TYPE_INT_RGB);
		g = (Graphics2D) image.getGraphics();
		buffer = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
		
		gsm = new gameStateManager();
		
	}	
	
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

	public void run() 
	{
		init();
		
		double ns =1000000000.0 / FPS;
		double delta = 0;
		int frames = 0;
		int updates = 0;
		long lastTime = System.nanoTime();
		long timer = System.currentTimeMillis();
		
		while(isRunning)
		{
			long curTime = System.nanoTime();
			delta += (curTime - lastTime) / ns;
			lastTime = curTime;
			
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
	        	//TODO REMOVE/COMENT THIS OUT LATER
	        	System.out.println("Game Performaces" + "  |  " + updates + " ups, " + frames + " fps");
	        	timer += 1000;
	        	frames = 0;
	        	updates = 0;
	        }
	        
	        
		}
		
		System.out.println("Unexpected exit");
		System.exit(-1);

	}
	
	public void update()
	{
		gsm.update();
		keyHandler.update();
	}
	


	public void render()
	{
		Graphics g2 = getGraphics();
		gsm.draw(g, buffer);
		g2.drawImage(image, 0,0, WIDTH,HEIGHT,null);
		g2.dispose();
		
	}

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

