package com.amrou.go;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Masque extends GameObject{

	private BufferedImage masque_img;

	public Masque(float x, float y, float w, float h, ObjectId id) {
		super(x, y, w, h, id);
		masque_img = ImageLoader.loadImage("/masque.png");
	}

	@Override
	public void tick(Handler handler) {
	
		
	}

	@Override
	public void render(Graphics g) {
		/*
		Graphics2D g2d = (Graphics2D) g;
		g.setColor(Color.green);
		g2d.draw(getBounds());
		*/
		g.drawImage(masque_img,(int)x,(int)y,(int)w,(int)h,null);
	}

	@Override
	public ObjectId getId() {
	
		return ObjectId.Mask;
	}

}
