package com.amrou.go;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

public class Handler {



	public LinkedList<GameObject> object = new LinkedList<GameObject>();
	private GameObject tempObject;
	private KeyManager keymanager;
	private Game game;
	private Camera cam;
	private BufferedImage level1 = null, level2 = null, level3 = null;
	
	
	

	
	public Handler(KeyManager keymanager, Game game,Camera cam) {
		super();
		this.keymanager = keymanager;
		this.game = game;
		this.cam=cam;
		level1 = ImageLoader.loadImage("/level1.png");
		level2 = ImageLoader.loadImage("/level2.png");
		level3 = ImageLoader.loadImage("/level3.png");
	}


	public void tick() {
		
		for(int i=0;i<object.size();i++) {
			tempObject = object.get(i);
			tempObject.tick(this);
		}
		if(Player.change==1) {
			Player.change = 0;
			switchLevel();
		}
		
	}
	
	public void render(Graphics g) {
		
		for(int i=0;i<object.size();i++) {
			tempObject = object.get(i);
			tempObject.render(g);
		}
		
	}
	
	public void addObject(GameObject object) {
		this.object.add(object);
	}
	
	public void removeObject(GameObject object) {
		this.object.remove(object);
	}
	
	public void clearLevel() {
		object.clear();
	}
	
	public void switchLevel() {
		clearLevel();
		cam.setX(0);
		Game.health=100;
		Game.alive = true;
		Player.nb_dahmad=0;
		
		switch(Game.num_level) {
			case 1:
				LoadImageLevel(level1);
				Game.num_level=1;
				break;
			case 2:
				LoadImageLevel(level2);
				Game.num_level=2;
				break;
			case 3:
				LoadImageLevel(level3);
				Game.num_level=3;
				break;	
		}
		
	}
	
	public void LoadImageLevel(BufferedImage image) {
		
		int w=image.getWidth();
		int h=image.getHeight();
		
		for(int i=0;i<h;i++) {
			for(int j=0;j<w;j++) {
				int pixel = image.getRGB(j, i);
				int red = (pixel >> 16) & 0xff;
				int green = (pixel >> 8) & 0xff;
				int bleu = (pixel) & 0xff;
				
				if(red == 0 && green == 0 && bleu == 0) {
					addObject(new Block(j*64,i*64,64,64,ObjectId.Block));
				}
				if(red == 255 && green == 0 && bleu == 0) {
					addObject(new Player(j*64,i*64,100,100,ObjectId.Player,cam,keymanager,this,game));
				}
				if(red == 127 && green == 127 && bleu == 127) {
					addObject(new Merda(j*64+5,i*64+5,100,100,game,ObjectId.Merda));
				}
				if(red == 255 && green == 128 && bleu == 0) {
					addObject(new Flag(j*64,i*64,64,64,ObjectId.Flag));
				}
				if(red == 0 && green == 255 && bleu == 0) {
					addObject(new Tree(j*64,i*64-130,64,64,ObjectId.Tree));
				}
				if(red == 0 && green == 0 && bleu == 255) {
					addObject(new Fire(j*64,i*64-15,64,64,ObjectId.Fire));
				}
				if(red == 255 && green == 0 && bleu == 128) {
					addObject(new Enemy(j*64,i*64-15,64,64,ObjectId.Enemy));
				}
				if(red == 0 && green == 255 && bleu == 255) {
					addObject(new Masque(j*64+5,i*64+5,50,50,ObjectId.Mask));
				}
				if(red == 181 && green == 230 && bleu == 29) {
					addObject(new Grass(j*64+5,i*64+5,50,50,ObjectId.Grass));
				}
				if(red == 128 && green == 64 && bleu == 0) {
					addObject(new Brick(j*64+5,i*64+5,50,50,ObjectId.Brick));
				}
				if(red == 128 && green == 0 && bleu == 0) {
					addObject(new Tajine(j*64-14,i*64+5,50,50,ObjectId.Tajine));
				}

				
			}
		}
		
	}
	
	
}
