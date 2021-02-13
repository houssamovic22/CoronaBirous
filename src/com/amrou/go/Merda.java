package com.amrou.go;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.LinkedList;

public class Merda extends GameObject{
	

	private Animation WalkRight;
	private Animation WalkLeft;
	private Animation HitRight;
	private Animation HitLeft;
	
	private BufferedImage[] walkright_img;
	private BufferedImage[] walkleft_img;
	private BufferedImage[] hitright_img;
	private BufferedImage[] hitleft_img;
	private BufferedImage NoMove_img;
	
	private SpriteSheet spritessh;
	private boolean hitL = false;
	private boolean hitR = false;
	
	private float gravity = 0.5f;
	
	private Game game;
	
	private HashMap<String,AudioPlayer> sfx;
	
	public Merda(float x, float y, float w, float h,Game game, ObjectId id) {
		super(x, y, w, h, id);
		
		this.game = game;
		
		sfx = new HashMap<String,AudioPlayer>();
		sfx.put("ba9",new AudioPlayer("/ba9.wav"));
		sfx.put("darkom",new AudioPlayer("/darkom.wav"));
	
		
		velX=1;
		velY=0;
		
		spritessh = new SpriteSheet(ImageLoader.loadImage("/mmerda.png"));
		
		NoMove_img = spritessh.crop(0,275, 90, 135);
		
		
		walkright_img = new BufferedImage[5];
		walkright_img[0] = spritessh.crop(0,225, 90, 97);
		walkright_img[1] = spritessh.crop(100,225, 90, 97);
		walkright_img[2] = spritessh.crop(100*2,225, 90, 97);
		walkright_img[3] = spritessh.crop(280,225, 90, 97);
		walkright_img[4] = spritessh.crop(368,225, 90, 97);
		
		walkleft_img = new BufferedImage[5];
		walkleft_img[0] = spritessh.crop(0,333, 90, 97);
		walkleft_img[1] = spritessh.crop(100,333, 90, 97);
		walkleft_img[2] = spritessh.crop(186,333, 90, 97);
		walkleft_img[3] = spritessh.crop(275,333, 90, 97);
		walkleft_img[4] = spritessh.crop(370,333, 90, 97);
		
		hitright_img = new BufferedImage[5];
		hitright_img[0] = spritessh.crop(0,0, 84, 106);
		hitright_img[1] = spritessh.crop(80,0, 84, 106);
		hitright_img[2] = spritessh.crop(160,0, 84, 106);
		hitright_img[3] = spritessh.crop(240,0, 84, 106);
		hitright_img[4] = spritessh.crop(340,0, 84, 106);
		
		hitleft_img = new BufferedImage[5];
		hitleft_img[0] = spritessh.crop(0,112, 90, 97);
		hitleft_img[1] = spritessh.crop(100,112, 90, 97);
		hitleft_img[2] = spritessh.crop(200,112, 90, 97);
		hitleft_img[3] = spritessh.crop(270,112, 90, 97);
		hitleft_img[4] = spritessh.crop(359,112, 90, 97);
	
		WalkRight = new Animation(5, walkright_img[0],walkright_img[1],walkright_img[2],walkright_img[3],walkright_img[4]);
		WalkLeft = new Animation(5, walkleft_img[0],walkleft_img[1],walkleft_img[2],walkleft_img[3],walkleft_img[4]);
		
		HitRight = new Animation(10, hitright_img[0],hitright_img[1],hitright_img[2],hitright_img[3],hitright_img[4]);
		HitLeft = new Animation(10, hitleft_img[0],hitleft_img[1],hitleft_img[2],hitleft_img[3],hitleft_img[4]);
	}

	@Override
	public void tick(Handler handler) {
		
		x+=velX;
		y+=velY;
			
		velY+=gravity;
			
			Collision(handler);
			
			WalkRight.runAnimation();
			WalkLeft.runAnimation();
			
			HitRight.runAnimation();
			HitLeft.runAnimation();
		
	}

	@Override
	public void render(Graphics g) {
		
		
		 if (velX>0)
			 if(hitR)
				 HitRight.drawAnimation(g, x, y, 110, 160);
			 else
			 WalkRight.drawAnimation(g,x,y,110, 160);
		 else if(velX<0) {
			 if(hitL)
				 HitLeft.drawAnimation(g, x, y,110, 160);
			 else
			 WalkLeft.drawAnimation(g,x,y,110,160);
		 }
	}
	
private void Collision(Handler handler) {
		
		for(int i=0;i<handler.object.size();i++) {
			
			GameObject tempObject = handler.object.get(i);
			
			if(tempObject.getId()==ObjectId.Player) {
				if(tempObject.getX() < x && tempObject.getX()>x-100 && tempObject.getY()>y-100) {
					hitL = true;
					sfx.get("ba9").play();
					//sfx.get("darkom").play();
					Game.health-=0.1;
					if(Game.health<=0) {
						Game.alive=false;
						game.restartLevel();
					}
					System.out.println(Game.health);
				}
				else if(tempObject.getX() > x+100 && tempObject.getX()<x+180 && tempObject.getY()>y-100) {
					hitR=true;
					sfx.get("ba9").play();
					sfx.get("darkom").play();
					Game.health-=0.1;
					System.out.println(Game.health);
					if(Game.health<=0) {
						Game.alive=false;
						game.restartLevel();
					}
				}
				else { 
					hitL=false;
					hitR=false;
				}
			}
			
			if(tempObject.getId()==ObjectId.Block) {
				
				if(getBoundsTop().intersects(tempObject.getBounds())) {
					setVelY(0);
					y=tempObject.getY()+102;
					
			}
				
				if(getBounds().intersects(tempObject.getBounds())) {
					setVelY(0);
				}
				if(getBoundsRight().intersects(tempObject.getBounds())) {
					setVelX(-velX);
					}
				if(getBoundsLeft().intersects(tempObject.getBounds())) {
					setVelX(-velX);
					}
			}
			
	if(tempObject.getId()==ObjectId.Brick) {
				
				if(getBoundsTop().intersects(tempObject.getBounds())) {
					setVelY(0);
					y=tempObject.getY()+102;
					
			}
				
				if(getBounds().intersects(tempObject.getBounds())) {
					setVelY(0);
					
			}
		
				if(getBoundsRight().intersects(tempObject.getBounds())) {
					setVelX(-velX);
					}
				if(getBoundsLeft().intersects(tempObject.getBounds())) {
					setVelX(-velX);
					}
			}
	
	if(tempObject.getId()==ObjectId.Grass) {
		
		if(getBoundsTop().intersects(tempObject.getBounds())) {
			setVelY(0);
			y=tempObject.getY()+102;
			
	}
		
		if(getBounds().intersects(tempObject.getBounds())) {
			setVelY(0);
			
	}

		if(getBoundsRight().intersects(tempObject.getBounds())) {
			setVelX(-velX);
			}
		if(getBoundsLeft().intersects(tempObject.getBounds())) {
			setVelX(-velX);
			}
		}
		
		}
				
		}
			
	public Rectangle getBounds() {
		return new Rectangle((int)x+(int)w/2 -14,(int)y,(int)w-25,(int)h*2-40);
}
	public Rectangle getBoundsRight() {
		return new Rectangle((int)x+(int)w/2 + (int)w-32,(int)y+14,(int)5,(int)h*2-60);
}
	public Rectangle getBoundsLeft() {
		return new Rectangle((int)x+(int)w/2-18,(int)y+14,(int)5,(int)h*2-60);
}
	public Rectangle getBoundsTop() {
		return new Rectangle((int)x+(int)w/2+11,(int)y,(int)52,(int)10);
}
	
	@Override
	public ObjectId getId() {
		
		return ObjectId.Merda;
	}
	
	public Rectangle getBoundsBig() {
		return new Rectangle((int)x-6,(int)y-6,(int)w+25,(int)h+20);
	}
	

}



