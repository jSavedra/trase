package com.savx.world;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.savx.main.Game;

public class Tile {

	public static BufferedImage floorTile1 = Game.sheet.getSprite(32, 0, 32, 32);
	public static BufferedImage wallTile1 = Game.sheet.getSprite(0, 0, 32, 32);
	public static BufferedImage floorTile2 = Game.sheet.getSprite(3 * 32, 0, 32, 32);
	public static BufferedImage wallTile2 = Game.sheet.getSprite(2 * 32, 0, 32, 32);
	public static BufferedImage floorTile3 = Game.sheet.getSprite(5 * 32, 0, 32, 32);
	public static BufferedImage wallTile3 = Game.sheet.getSprite(4 * 32, 0, 32, 32);

	private int x;
	private int y;
	private BufferedImage sprite;

	public Tile(int x, int y, BufferedImage sprite) {
		this.x = x;
		this.y = y;
		this.sprite = sprite;
	}

	public void update() {

	}

	public void render(Graphics g) {
		g.drawImage(sprite, x - Camera.x, y - Camera.y, null);
	}
}
