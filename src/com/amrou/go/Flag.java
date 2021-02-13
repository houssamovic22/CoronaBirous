package com.amrou.go;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Flag extends GameObject{
	
	private BufferedImage flag_img;

	public Flag(float x, float y, float w, float h, ObjectId id) {
		super(x, y, w, h, id);
		flag_img = ImageLoader.loadImage("/flag.png");
	}

	@Override
	public void tick(Handler handler) {
	
		
	}

	@Override
	public void render(Graphics g) {
		
		g.drawImage(flag_img,(int)x,(int)y,null);
	}

	@Override
	public ObjectId getId() {
	
		return ObjectId.Flag;
	}

}
