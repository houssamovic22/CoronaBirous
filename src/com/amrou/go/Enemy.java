package com.amrou.go;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Enemy extends GameObject {
	
	Random r = new Random();
	int choose = 0;
	int hp = 100;
	private BufferedImage corona_img; 
	
	public Enemy(float x, float y, float w, float h, ObjectId id) {
		super(x, y, w, h, id);
		corona_img = ImageLoader.loadImage("/corona1.png");
	}

	@Override
	public void tick(Handler handler) {
		x+=velX;
		y+=velY;
		
		choose = r.nextInt(10);
		
		for(int i=0;i<handler.object.size();i++) {
			GameObject tempObject = handler.object.get(i);
			if(tempObject.getId()==ObjectId.Block || tempObject.getId()==ObjectId.Brick || tempObject.getId()==ObjectId.Grass) {
				if(getBounds().intersects(tempObject.getBounds())) {
					x +=(velX*2)*-1;
					y +=(velY*2)*-1;
					velX=0;
					velY=0;
				}
				else if(choose == 0) {
					velX = (r.nextInt(4 - -4)+ -4);
					velY = (r.nextInt(4 - -4)+ -4);
				}
					
			}
		}
		
	}

	@Override
	public void render(Graphics g) {
		
		g.drawImage(corona_img,(int) x, (int) y, null);
		
		
	}

	@Override
	public ObjectId getId() {
		return ObjectId.Enemy;
	}
	
	public Rectangle getBounds() {
		return new Rectangle((int)x,(int)y,(int)w,(int)h);
	}
	
	public Rectangle getBoundsBig() {
		return new Rectangle((int)x-4,(int)y-4,(int)w+23,(int)h+10);
	}

}
