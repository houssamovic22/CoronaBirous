package com.amrou.go;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Fire extends GameObject{
	
	private Animation fire_anim;
	
	private BufferedImage[] fire_imgs;
	
	private SpriteSheet spritesh;

	public Fire(float x, float y, float w, float h, ObjectId id) {
		
		super(x, y, w, h, id);
		
		spritesh = new SpriteSheet(ImageLoader.loadImage("/fire.png"));
		
		fire_imgs = new BufferedImage[7];
		
		fire_imgs[0] = spritesh.crop(0,30, 61, 80);
		fire_imgs[1] = spritesh.crop(64,30, 61, 80);
		fire_imgs[2] = spritesh.crop(64*2,30, 61, 80);
		fire_imgs[3] = spritesh.crop(64*3,30, 61, 80);
		fire_imgs[4] = spritesh.crop(64*4,30, 61, 80);
		fire_imgs[5] = spritesh.crop(64*5,30, 61, 80);
		fire_imgs[6] = spritesh.crop(64*6,30, 61, 80);

	
		fire_anim = new Animation(10, fire_imgs[0],fire_imgs[1],fire_imgs[2],fire_imgs[3],fire_imgs[4],fire_imgs[5],fire_imgs[6]);
	}

	@Override
	public void tick(Handler handler) {
	
		fire_anim.runAnimation();
	}

	@Override
	public void render(Graphics g) {
		
		fire_anim.drawAnimation(g,x,y,61,80);
	}
	
	public Rectangle getBounds() {
		return new Rectangle((int)x-6,(int)y-4,(int)w,(int)h+16);
	}

	@Override
	public ObjectId getId() {
		
		return ObjectId.Fire;
	
	}

}
