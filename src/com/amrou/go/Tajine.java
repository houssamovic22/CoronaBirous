package com.amrou.go;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Tajine extends GameObject{

	private BufferedImage tajine_img;

	public Tajine(float x, float y, float w, float h, ObjectId id) {
		super(x, y, w, h, id);
		tajine_img = ImageLoader.loadImage("/tajine.png");
	}

	@Override
	public void tick(Handler handler) {
	
		
	}

	@Override
	public void render(Graphics g) {
		
		g.drawImage(tajine_img,(int)x,(int)y,null);
	}

	@Override
	public ObjectId getId() {
	
		return ObjectId.Tajine;
	}
	
	public Rectangle getBoundsBig() {
		return new Rectangle((int)x-6,(int)y-6,(int)w+25,(int)h+20);
	}
}
