package com.amrou.go;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Tree extends GameObject{
	
	private BufferedImage tree_img;

	public Tree(float x, float y, float w, float h, ObjectId id) {
		super(x, y, w, h, id);
		tree_img = ImageLoader.loadImage("/tree.png");
	}

	@Override
	public void tick(Handler handler) {
		
		
	}

	@Override
	public void render(Graphics g) {
		
		g.drawImage(tree_img,(int)x,(int)y,180,200,null);
	}

	@Override
	public ObjectId getId() {
		
		return ObjectId.Tree;
	}

}
