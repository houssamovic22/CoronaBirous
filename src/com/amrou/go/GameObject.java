package com.amrou.go;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.LinkedList;

public abstract class GameObject {
	
	protected float x,y,w,h;
	protected float velX,velY;

	protected ObjectId id;


	public GameObject(float x,float y,float w,float h, ObjectId id) {
		this.x=x;
		this.y=y;
		this.w=w;
		this.h=h;
		this.id=id;
	}
	
	public abstract void tick(Handler handler);
	public abstract void render(Graphics g);

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public float getVelX() {
		return velX;
	}

	public void setVelX(float velX) {
		this.velX = velX;
	}

	public float getVelY() {
		return velY;
	}

	public void setVelY(float velY) {
		this.velY = velY;
	}
	
	public Rectangle getBounds() {
		return new Rectangle((int)x,(int)y,(int)w,(int)h);
	}
	
	public float getW() {
		return w;
	}

	public void setW(float w) {
		this.w = w;
	}

	public float getH() {
		return h;
	}

	public void setH(float h) {
		this.h = h;
	}
	
	public abstract ObjectId getId();
	
	
}
