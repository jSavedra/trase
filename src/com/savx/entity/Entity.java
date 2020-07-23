package com.savx.entity;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import com.savx.main.Game;
import com.savx.world.Camera;

public class Entity {

	public static BufferedImage enemy1 = Game.sheet.getSprite(0, 64, 32, 32);
	public static BufferedImage enemy2 = Game.sheet.getSprite(0, 96, 32, 32);
	public static BufferedImage boss = Game.spriteboss.getSprite(0, 0, 64, 64);
	public static BufferedImage life = Game.sheet.getSprite(0, 5 * 32, 32, 32);

	protected double x;
	protected double y;
	protected int width;
	protected int height;
	protected BufferedImage sprite;

	protected int maskX;
	protected int maskY;
	protected int maskWidth;
	protected int maskHeight;

	public Entity(int x, int y, int width, int height, BufferedImage sprite) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.sprite = sprite;

		this.maskX = 0;
		this.maskY = 0;
		this.maskWidth = width;
		this.maskHeight = height;
	}

	public void update() {

	}

	public void render(Graphics g) {
		g.drawImage(this.getSprite(), this.getX() - Camera.x, this.getY() - Camera.y, null);
	}

	public static boolean isCollidingWithEntities(Entity e1, Entity e2) {
		Rectangle e1Mask = new Rectangle(e1.getX() + e1.maskX, e1.getY() + e1.maskY, e1.getWidth() + e1.maskWidth,
				e1.getHeight() + e1.maskHeight);
		Rectangle e2Mask = new Rectangle(e2.getX() + e2.maskX, e2.getY() + e2.maskY, e2.getWidth() + e2.maskWidth,
				e2.getHeight() + e2.maskHeight);

		return e1Mask.intersects(e2Mask);
	}

	public void setMask(int maskX, int maskY, int maskW, int maskH) {
		this.maskX = maskX;
		this.maskY = maskY;
		this.maskWidth = maskW;
		this.maskHeight = maskH;
	}

	public void setX(int newX) {
		this.x = newX;
	}

	public void setY(int newY) {
		this.y = newY;
	}

	public int getX() {
		return (int) this.x;
	}

	public int getY() {
		return (int) this.y;
	}

	public int getWidth() {
		return this.width;
	}

	public int getHeight() {
		return this.height;
	}

	public BufferedImage getSprite() {
		return this.sprite;
	}
}
