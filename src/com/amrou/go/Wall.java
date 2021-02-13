package com.amrou.go;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Wall extends GameObject{
	
	private BufferedImage wall_img;

	public Wall(float x, float y, float w, float h, ObjectId id) {
		super(x, y, w, h, id);
		wall_img = ImageLoader.loadImage("/wall.png");
	}

	@Override
	public void tick(Handler handler) {
		
		
	}

	@Override
	public void render(Graphics g) {
		
		g.drawImage(wall_img,(int)x,(int)y,null);
	}

	@Override
	public ObjectId getId() {
		
		return ObjectId.Wall;
	}

}
