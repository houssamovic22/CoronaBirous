package com.amrou.go;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageLoader {
	
	// java stores images in BufferedImage object !
	// c'est comme ça qu'on upload les images en Java !
	
	public static BufferedImage loadImage(String path) {
		try {
		return ImageIO.read(ImageLoader.class.getResource(path));
		}
		catch(IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		return null;
	}
	
}
