package com.amrou.go;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

public class Block extends GameObject{
	
	private BufferedImage block_img;
	
	public Block(float x, float y,float w,float h, ObjectId id) {
		super(x, y, w, h, id);
		block_img = ImageLoader.loadImage("/block.png");
	}

	@Override
	public void tick(Handler handler) {

		
	}

	@Override
	public void render(Graphics g) {
		
		
		g.drawImage(block_img,(int)x,(int)y,null);
	}

	@Override
	public ObjectId getId() {
		
		return ObjectId.Block;
	}
	
	public Rectangle getBoundsBig() {
		return new Rectangle((int)x-6,(int)y-6,(int)w+25,(int)h+20);
	}
	

}
