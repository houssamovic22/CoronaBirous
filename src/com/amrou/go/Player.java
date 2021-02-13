package com.amrou.go;


import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.lang.Math;

public class Player extends GameObject{
	
	private float gravity = 0.5f;
	private final float MAX_SPEED = 5;
	protected boolean falling = true;
	protected boolean jumping = false;
	protected boolean ground = false;
	public static int change = 0;
	
	private KeyManager keymanager;
	
	private Animation playerWalkRight;
	private Animation playerWalkLeft;
	
	private Animation playerWalkRightMask;
	private Animation playerWalkLeftMask;
	
	private BufferedImage NoMove_img;
	private BufferedImage NoMoveMask_img;
	private BufferedImage dead_img;
	
	private BufferedImage[] walkright_img;
	private BufferedImage[] walkleft_img;
	
	private BufferedImage[] walkrightmask_img;
	private BufferedImage[] walkleftmask_img;
	
	private SpriteSheet spritesh;
	private SpriteSheet spritesh_mask;
	
	private Handler handler;
	private Game game;
	private Camera cam;
	
	private HashMap<String,AudioPlayer> sfx; // sfx genre sound effects
	
	public static int nb_dahmad = 0;
	public static boolean has_mask = false;
	

	
	public Player(float x, float y,float w,float h, ObjectId id,Camera cam,KeyManager keymanager,Handler handler,Game game) {
		
		super(x, y,w,h, id);
		this.handler = handler;
		this.keymanager=keymanager;
		this.game=game;
		this.cam=cam;
		
		sfx = new HashMap<String,AudioPlayer>();
		sfx.put("dead",new AudioPlayer("/player_dead.wav"));
		sfx.put("pain",new AudioPlayer("/pain.wav"));
		sfx.put("awdahmad",new AudioPlayer("/awdahmad.wav"));
		sfx.put("wili",new AudioPlayer("/wili.wav"));
		sfx.put("bsahtekmask",new AudioPlayer("/bsahtekmask.wav"));
		sfx.put("atikawa",new AudioPlayer("/atikawa.wav"));
		sfx.put("anablah",new AudioPlayer("/anablah.wav"));
		sfx.put("mli7",new AudioPlayer("/mli7.wav"));
		
		velX=0;
		velY=0;
		
		
		
		spritesh = new SpriteSheet(ImageLoader.loadImage("/player_sprite.png"));
		
		NoMove_img = spritesh.crop(0,0, 90, 92);
		dead_img = ImageLoader.loadImage("/dead_player.png");
		
		walkright_img = new BufferedImage[6];
		walkright_img[0] = spritesh.crop(0,190, 90, 92);
		walkright_img[1] = spritesh.crop(100,190, 90, 92);
		walkright_img[2] = spritesh.crop(100*2,190, 90, 92);
		walkright_img[3] = spritesh.crop(100*3,190, 90, 92);
		walkright_img[4] = spritesh.crop(100*4,190, 90, 92);
		walkright_img[5] = spritesh.crop(100*5,190, 90, 92);
		
		walkleft_img = new BufferedImage[6];
		walkleft_img[0] = spritesh.crop(0,280, 90, 92);
		walkleft_img[1] = spritesh.crop(100,280, 90, 92);
		walkleft_img[2] = spritesh.crop(100*2,280, 90, 92);
		walkleft_img[3] = spritesh.crop(100*3,280, 90, 92);
		walkleft_img[4] = spritesh.crop(100*4,280, 90, 92);
		walkleft_img[5] = spritesh.crop(100*5,280, 90, 92);
	
		playerWalkRight = new Animation(5, walkright_img[0],walkright_img[1],walkright_img[2],walkright_img[3],walkright_img[4],walkright_img[5]);
		playerWalkLeft = new Animation(5, walkleft_img[0],walkleft_img[1],walkleft_img[2],walkleft_img[3],walkleft_img[4],walkleft_img[5]);
	
		// With Mask
		
		spritesh_mask = new SpriteSheet(ImageLoader.loadImage("/player_masque_sprite.png"));
		
		NoMoveMask_img = spritesh_mask.crop(0,0, 90, 92);
		dead_img = ImageLoader.loadImage("/dead_player.png");
		
		walkrightmask_img = new BufferedImage[6];
		walkrightmask_img[0] = spritesh_mask.crop(0,190, 90, 92);
		walkrightmask_img[1] = spritesh_mask.crop(100,190, 90, 92);
		walkrightmask_img[2] = spritesh_mask.crop(100*2,190, 90, 92);
		walkrightmask_img[3] = spritesh_mask.crop(100*3,190, 90, 92);
		walkrightmask_img[4] = spritesh_mask.crop(100*4,190, 90, 92);
		walkrightmask_img[5] = spritesh_mask.crop(100*5,190, 90, 92);
		
		walkleftmask_img = new BufferedImage[6];
		walkleftmask_img[0] = spritesh_mask.crop(0,280, 90, 92);
		walkleftmask_img[1] = spritesh_mask.crop(100,280, 90, 92);
		walkleftmask_img[2] = spritesh_mask.crop(100*2,280, 90, 92);
		walkleftmask_img[3] = spritesh_mask.crop(100*3,280, 90, 92);
		walkleftmask_img[4] = spritesh_mask.crop(100*4,280, 90, 92);
		walkleftmask_img[5] = spritesh_mask.crop(100*5,280, 90, 92);
	
		playerWalkRightMask = new Animation(5, walkrightmask_img[0],walkrightmask_img[1],walkrightmask_img[2],walkrightmask_img[3],walkrightmask_img[4],walkrightmask_img[5]);
		playerWalkLeftMask = new Animation(5, walkleftmask_img[0],walkleftmask_img[1],walkleftmask_img[2],walkleftmask_img[3],walkleftmask_img[4],walkleftmask_img[5]);
		
	}

	
	public void tick(Handler handler) {
		
		
		x+=velX;
		y+=velY;
			
			if(has_mask)
				Game.mask_endurence-=0.1;
			if(Game.mask_endurence<1)
				has_mask=false;
		
			if(falling || jumping) {
				velY+=gravity;
				if(velY>MAX_SPEED || velY<-MAX_SPEED)
					velY=MAX_SPEED;
			}
		
			
			
			if(keymanager.right)
				setVelX(5);
			else if(keymanager.left)
				setVelX(-5);
			else if(!keymanager.right && !keymanager.left)
				setVelX(0);
			
		
			
			if(keymanager.up && !jumping) {
				ground = false;
				setJumping(true);
				setVelY(-5);
			}
			else
				setJumping(false);
			
			
		
		//setFalling(true);
		
	
		
		
		//collisions
		Collision(handler);
		

		//Animations
		playerWalkRight.runAnimation();
		playerWalkLeft.runAnimation();
		
		if(has_mask) {
			playerWalkRightMask.runAnimation();
			playerWalkLeftMask.runAnimation();
		}
		
		/*
		if(Game.health<0 || keymanager.escape)
			game.restartLevel();
			*/
		
	}
	
	public void render(Graphics g) {
		
		
		if(has_mask==false) {
		 if (velX>0)
			 playerWalkRight.drawAnimation(g,x,y,150,160);
		 else if(velX<0) {
			 playerWalkLeft.drawAnimation(g,x,y,150,160);
		 }
		 else if(!Game.alive) {
			 g.drawImage(dead_img, (int)x,(int)y+100,150,90, null);
			 setVelX(0);
			 setVelY(0);
			 game.restartLevel();
			 
		 }
		 else {
			 g.drawImage(NoMove_img , (int)x,(int)y,150,160,null);
		 }
		}
		else if(has_mask==true) {
			if (velX>0)
				 playerWalkRightMask.drawAnimation(g,x,y,150,160);
			 else if(velX<0) {
				 playerWalkLeftMask.drawAnimation(g,x,y,150,160);
			 }
			 else if(!Game.alive) {
				 g.drawImage(dead_img, (int)x,(int)y+100,150,90, null);
				 setVelX(0);
				 setVelY(0);
				 game.restartLevel();
			 }
			 else {
				 g.drawImage(NoMoveMask_img , (int)x,(int)y,150,160,null);
			 }
		}
		
		}
	
	private void Collision(Handler handler) {
		
		for(int i=0;i<handler.object.size();i++) {
			
			GameObject tempObject = handler.object.get(i);
			
			if(tempObject.getId()==ObjectId.Block) {
				
				if(getBoundsTop().intersects(tempObject.getBounds()) && !jumping) {
					setVelY(0);
					y=tempObject.getY()+102;
					
			}
				
				if(getBounds().intersects(tempObject.getBounds()) && !jumping) {
					setVelY(0);
					falling=false;
					jumping=false;
					ground = true;
					
			}
				else {
					falling=true;
					}
				if(getBoundsRight().intersects(tempObject.getBounds())) {
					setX(x-10);
					}
				if(getBoundsLeft().intersects(tempObject.getBounds())) {
					setX(x+10);
					}
			}
			
	if(tempObject.getId()==ObjectId.Brick) {
				
				if(getBoundsTop().intersects(tempObject.getBounds()) && !jumping) {
					setVelY(0);
					y=tempObject.getY()+102;
					
			}
				
				if(getBounds().intersects(tempObject.getBounds()) && !jumping) {
					setVelY(0);
					falling=false;
					jumping=false;
					ground = true;
					
			}
				else {
					falling=true;
					}
				if(getBoundsRight().intersects(tempObject.getBounds())) {
					setX(x-10);
					}
				if(getBoundsLeft().intersects(tempObject.getBounds())) {
					setX(x+10);
					}
			}
	
	if(tempObject.getId()==ObjectId.Grass) {
		
		if(getBoundsTop().intersects(tempObject.getBounds()) && !jumping) {
			setVelY(0);
			y=tempObject.getY()+102;
			
	}
		
		if(getBounds().intersects(tempObject.getBounds()) && !jumping) {
			setVelY(0);
			falling=false;
			jumping=false;
			ground = true;
			
	}
		else {
			falling=true;
			}
		if(getBoundsRight().intersects(tempObject.getBounds())) {
			setX(x-10);
			}
		if(getBoundsLeft().intersects(tempObject.getBounds())) {
			setX(x+10);
			}
	}
			
			if(tempObject.getId()==ObjectId.Fire) {
				if(getBoundsRight().intersects(tempObject.getBounds()) || getBoundsLeft().intersects(tempObject.getBounds())) {
					Game.health--;
					if(Game.playing)
						sfx.get("pain").play();
					if(Game.health<=0) {
						Game.alive=false;
						if(Game.playing)
							sfx.get("dead").play();
					}
				}
				
			}
			
			if(tempObject.getId()==ObjectId.Mask) {
				if(getBounds().intersects(tempObject.getBounds())) {
					has_mask=true;
					Game.mask_endurence=100;
					sfx.get("bsahtekmask").play();
					handler.removeObject(tempObject);
					
				}
				
			}
			
			if(tempObject.getId()==ObjectId.Tajine) {
				if(getBounds().intersects(tempObject.getBounds())) {
					Game.health=100;
					sfx.get("mli7").play();
					handler.removeObject(tempObject);
					
				}
				
			}
				
			if(tempObject.getId()==ObjectId.Enemy) {
				
				double distance = (x-tempObject.getX() )*(x-tempObject.getX() +
						(y-tempObject.getY() *(y-tempObject.getY())));
				
				if(Math.sqrt(distance) < 100) {
					if(nb_dahmad<=15) {
						if(nb_dahmad%14==0) {
							if(Game.playing)
								sfx.get("awdahmad").play();
						}
						else if(nb_dahmad%4==0) {
							sfx.get("anablah").play();
							}
						else {
							if(Game.playing)
								sfx.get("wili").play();
							
						}
					}
					if(Game.playing)
						nb_dahmad++;
				}
				if(getBounds().intersects(tempObject.getBounds()) && has_mask==false) {
					
					Game.health-=100;
					
					//sfx.get("awdahmad").play();
					if(Game.health<=0) {
						Game.alive=false;
						
						if(Game.playing)
							sfx.get("dead").play();
						
						
					}
				
					}	
				}
			
			if(tempObject.getId()==ObjectId.Flag) {
				if(getBounds().intersects(tempObject.getBounds())) {
					sfx.get("atikawa").play();
					change=1;
					Game.num_level++;
					has_mask=false;
					if(Game.num_level==4) {
						Game.num_level=1;
						Game.playing=false;
						Game.bgMusic.play();
						handler.clearLevel();
					}
						

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
		
		return id;
	}
	
	public boolean isFalling() {
		return falling;
	}

	public void setFalling(boolean falling) {
		this.falling = falling;
	}

	public boolean isJumping() {
		return jumping;
	}

	public void setJumping(boolean jumping) {
		this.jumping = jumping;
	}
	
}
