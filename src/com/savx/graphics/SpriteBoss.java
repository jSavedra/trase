package com.savx.graphics;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class SpriteBoss {

	private BufferedImage spriteboss;

	public SpriteBoss(String path) {
		try {
			spriteboss = ImageIO.read(getClass().getResource(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public BufferedImage getSprite(int x, int y, int w, int h) {
		return spriteboss.getSubimage(x, y, w, h);
	}
}
