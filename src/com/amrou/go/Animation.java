package com.amrou.go;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Animation {
	
	private int frames;
	private int speed;
	
	private int index = 0;
	private int count = 0;
	
	
	private BufferedImage[] images;
	private BufferedImage currentImg;
	
	public Animation(int speed, BufferedImage... args) {
		super();
		this.speed = speed;
		images = new BufferedImage[args.length];
		for(int i=0;i<args.length;i++) {
			images[i]=args[i];
		}
		frames = args.length;
	}
	
	
	public void runAnimation() {
		
		index++;
		if(index>speed) {
			index=0;
			nextframe();
		}
		
	}
	
	private void nextframe() {

		for(int i=0;i<frames;i++) {
			if(count == i)
				currentImg = images[i];
		}
		count++;
		if(count>frames)
			count=0;
		
	}
	
	public void drawAnimation(Graphics g,float x,float y) {
		g.drawImage(currentImg,(int)x,(int)y,null);
	}
	
	public void drawAnimation(Graphics g,float x,float y,int scaleX,int scaleY) {
		g.drawImage(currentImg,(int)x,(int)y,scaleX,scaleY,null);
	}


	public int getSpeed() {
		return speed;
	}
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	
}
