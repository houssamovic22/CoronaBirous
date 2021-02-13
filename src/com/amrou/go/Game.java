package com.amrou.go;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.Random;

import javax.swing.JFrame;

public class Game extends Canvas implements Runnable {

	private static final long serialVersionUID = 1L;
	private String title;
	private int width , height;
	private Thread thread;
	private boolean running = false;
	private KeyManager keymanager;
	private Handler handler;
	public static int WIDTH;
	public static int HEIGHT;
	private Camera cam;
	public static int health = 100;
	public static float mask_endurence = 100;
	public static boolean alive = true;
	public static AudioPlayer bgMusic;
	public static int num_level = 1;
	private BufferedImage level1 = null,level2 = null,level3=null, menu = null, healthicon=null,maskicon=null,wallpaper=null;
	public static boolean playing = false;

	public Game(String title,int width,int height) {
		this.title=title;
		this.width=width;
		this.height=height;
		WIDTH=width;
		HEIGHT=height;
		
		keymanager = new KeyManager();
		wallpaper = ImageLoader.loadImage("/wallpaper.png");
		level1 = ImageLoader.loadImage("/level1.png");
		level2 = ImageLoader.loadImage("/level2.png");
		level3 = ImageLoader.loadImage("/level3.png");
		 menu = ImageLoader.loadImage("/menu.png");
		 healthicon = ImageLoader.loadImage("/health.png");
		 maskicon = ImageLoader.loadImage("/maskicon.png");
		bgMusic = new AudioPlayer("/damdamdirouriraa.wav");
		
			 
	
	}
	
	private void init() {
		
		

		cam = new Camera(0,0);
		handler = new Handler(keymanager,this,cam);

		bgMusic.play();
		
		Player.has_mask = false;
		handler.LoadImageLevel(level1);
		
		this.addKeyListener(keymanager);
		
	    }
		
		
		

	@Override
	public void run() {
		
		//Game loop
		
			init();
			
			int fps = 60;
			double timePerTick = 1000000000 / fps;
			double delta = 0;
			long now;
			long lastTime = System.nanoTime(); // current time en nanosec
			int ticks = 0;
			long timer = 0;
			long timer_mask = 0;
				
				while(running) {
					
					now = System.nanoTime();
					delta += (now-lastTime)/timePerTick;
					timer += now - lastTime;
					lastTime=now;
					
					if(delta>=1) {
						
						tick();
						render();
						ticks++;
						delta--;
					}
					
					if(timer >= 1000000000) {
						
						//System.out.println("Fps : "+ ticks);
						ticks=0;
						timer=0;
						
						}
					
					}
				
			 stop();
		
	}
	
	private synchronized void start() {
		if(running)
			return;
		running = true;
		thread = new Thread(this);
		thread.start();
		
	}
	
	private synchronized void stop() {
		if(!running)
			return;
		
		running = false;
		
		try {
			thread.join();
		}
		catch(InterruptedException e) {
			e.printStackTrace();
		}
		
		System.exit(1);
	}
	
	public void window_Launch() {
		this.setPreferredSize(new Dimension(width,height));
		this.setMaximumSize(new Dimension(width,height));
		this.setMinimumSize(new Dimension(width,height));
		
		JFrame frame = new JFrame(title);
		frame.add(this);
		frame.pack();
		this.setFocusable(true);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);


		this.start();
	}
	
	public void tick() {
		// Everything in the game that updates
		keymanager.tick();
		handler.tick();
		for(int i=0;i<handler.object.size();i++) {
			if(handler.object.get(i).getId()==ObjectId.Player)
				cam.tick(handler.object.get(i));
		}
		if(keymanager.enter) {
			playing = true;
			bgMusic.stop();
		}
		if(keymanager.escape)
			Game.playing = false;
		

	}
	
	public void render() {
		// Everything in the game that renders
				BufferStrategy bs = this.getBufferStrategy();
				if(bs==null) {
					createBufferStrategy(3);
					return;
				}
				
				Graphics g = bs.getDrawGraphics();
				
				Graphics2D g2d = (Graphics2D) g;
				
				////////////////////////
				/*
				  here we can draw stuff 
				 */

				if(!playing) {
					g.drawImage(menu, 0	, 0,width,height, null);
				}
					
				if(playing) {
				/*Color bg = new Color(34, 40, 67);
				if(num_level==2)
					bg = new Color(209,238,245);
				g.setColor(bg);
				g.fillRect(0, 0, width, height);
				*/
					g.drawImage(wallpaper, 0, 0, null);
				}
				// begin of cam
				g2d.translate(cam.getX(), cam.getY());
				
				if(playing)
					handler.render(g);
				

				
				g2d.translate(-cam.getX(), -cam.getY());
				// end of cam
				
				//healthbar
				if(playing) {
				g.drawImage(healthicon, 5, 5,34,34, null);
				g.setColor(Color.gray);
				g.fillRect(40, 5, 200, 32);
				g.setColor(Color.green);
				g.fillRect(40, 5, health*2, 32);
				g.setColor(Color.red);
				g.drawRect(40, 5, 200, 32);
				g.drawImage(maskicon, 5, 40,34,34, null);
				Color mybleu = new Color(153, 217, 234);
				g.setColor(Color.gray);
				g.fillRect(40, 40, 200, 32);
				g.setColor(mybleu);
				g.fillRect(40, 40,(int) mask_endurence*2, 32);
				g.setColor(Color.red);
				g.drawRect(40, 40, 200, 32);
				}
				////////////////////////
				g.dispose();
				bs.show();
	}
	
	

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public KeyManager getKeymanager() {
		return keymanager;
	}

	public void setKeymanager(KeyManager keymanager) {
		this.keymanager = keymanager;
	}
	
	
	public void restartLevel() {
		handler.clearLevel();
		Game.health=100;
		mask_endurence=0;
		if(num_level==1)
			handler.LoadImageLevel(level1);
		if(num_level==2)
			handler.LoadImageLevel(level2);
		if(num_level==3)
			handler.LoadImageLevel(level3);
		alive = true;
		Player.nb_dahmad=0;
	}
	

	

}
