package com.savx.world;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import com.savx.entity.Boss;
import com.savx.entity.Enemy1;
import com.savx.entity.Enemy2;
import com.savx.entity.Entity;
import com.savx.entity.Life;
import com.savx.entity.Player;
import com.savx.graphics.SpriteBoss;
import com.savx.graphics.Spritesheet;
import com.savx.graphics.UI;
import com.savx.main.Game;

public class World {

	public static Tile[] tiles;
	public static int width;
	public static int height;
	public static int tileSize = 32;

	public World(String path) {
		try {
			BufferedImage map = ImageIO.read(getClass().getResource(path));
			width = map.getWidth();
			height = map.getHeight();
			int[] pixels = new int[width * height];
			tiles = new Tile[width * height];
			map.getRGB(0, 0, width, height, pixels, 0, width);
			for (int xx = 0; xx < width; xx++) {
				for (int yy = 0; yy < height; yy++) {
					int curPixels = pixels[xx + (yy * width)];
					tiles[xx + (yy * width)] = new FloorTile(xx * 32, yy * 32, Tile.floorTile1);

					// Tiles tips.

					if (Game.currentLevel == 3) {
						tiles[xx + (yy * width)] = new FloorTile(xx * 32, yy * 32, Tile.floorTile2);
					}

					if (Game.currentLevel == 4) {
						tiles[xx + (yy * width)] = new FloorTile(xx * 32, yy * 32, Tile.floorTile2);
					}

					if (Game.currentLevel == 5) {
						tiles[xx + (yy * width)] = new FloorTile(xx * 32, yy * 32, Tile.floorTile3);
					}

					if (Game.currentLevel == 6) {
						tiles[xx + (yy * width)] = new FloorTile(xx * 32, yy * 32, Tile.floorTile3);
					}

					if (curPixels == 0xFFFFFFFF) {
						// Floor1.
						tiles[xx + (yy * width)] = new FloorTile(xx * 32, yy * 32, Tile.floorTile1);
					}

					else if (curPixels == 0xFF000000) {
						// Wall1.
						tiles[xx + (yy * width)] = new WallTile(xx * 32, yy * 32, Tile.wallTile1);
					}

					if (curPixels == 0xFFFF00DC) {
						// Floor2.
						tiles[xx + (yy * width)] = new FloorTile(xx * 32, yy * 32, Tile.floorTile2);
					}

					else if (curPixels == 0xFFB200FF) {
						// Wall2.
						tiles[xx + (yy * width)] = new WallTile(xx * 32, yy * 32, Tile.wallTile2);
					}

					if (curPixels == 0xFFFFA060) {
						// Floor3.
						tiles[xx + (yy * width)] = new FloorTile(xx * 32, yy * 32, Tile.floorTile3);
					}

					else if (curPixels == 0xFFFF6A00) {
						// Wall3.
						tiles[xx + (yy * width)] = new WallTile(xx * 32, yy * 32, Tile.wallTile3);
					}

					else if (curPixels == 0xFFFF0000) {
						// Enemy1.
						Enemy1 en1 = new Enemy1(xx * 32, yy * 32, 10, 10, Entity.enemy1);
						Game.enemy1.add(en1);
					}

					else if (curPixels == 0xFFFFE900) {
						// Enemy2.
						Enemy2 en2 = new Enemy2(xx * 32, yy * 32, 10, 10, Entity.enemy2);
						Game.enemy2.add(en2);
					}

					else if (curPixels == 0xFF4CFF00) {
						// Boss.
						Boss boss = new Boss(xx * 32, yy * 32, 16, 16, Entity.boss);
						Game.boss.add(boss);
					}

					else if (curPixels == 0xFFFF7FB6) {
						// Life.
						Life life = new Life(xx * 32, yy * 32, 10, 10, Entity.life);
						life.setMask(16, 16, 1, 1);
						Game.entities.add(life);
					}

					else if (curPixels == 0xFF0026FF) {
						// Player.
						Game.player.setMask(10, 10, 2, 2);
						Game.player.setX(xx * 32);
						Game.player.setY(yy * 32);
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static boolean isCollidingWithTile(int nextX, int nextY) {
		int x1 = nextX / tileSize;
		int y1 = nextY / tileSize;

		int x2 = (nextX + tileSize - 1) / tileSize;
		int y2 = nextY / tileSize;

		int x3 = nextX / tileSize;
		int y3 = (nextY + tileSize - 1) / tileSize;

		int x4 = (nextX + tileSize - 1) / tileSize;
		int y4 = (nextY + tileSize - 1) / tileSize;

		return !((tiles[x1 + (y1 * width)] instanceof WallTile) || (tiles[x2 + (y2 * width)] instanceof WallTile)
				|| (tiles[x3 + (y3 * width)] instanceof WallTile) || (tiles[x4 + (y4 * width)] instanceof WallTile));
	}

	public static void restartGame(String level) {
		Game.entities.clear();
		Game.enemy1.clear();
		Game.enemy2.clear();
		Game.boss.clear();
		Game.shoot.clear();
		Game.ui = new UI();
		Game.entities = new ArrayList<Entity>();
		Game.enemy1 = new ArrayList<Enemy1>();
		Game.enemy2 = new ArrayList<Enemy2>();
		Game.boss = new ArrayList<Boss>();
		Game.sheet = new Spritesheet("/spritesheet.png");
		Game.spriteboss = new SpriteBoss("/Boss.png");
		Game.player = new Player(0, 0, 16, 16, Game.sheet.getSprite(0, 0, 32, 32));
		Game.entities.add(Game.player);
		Game.world = new World("/" + level);
		return;
	}

	public void update() {

	}

	public void render(Graphics g) {

		int startX = Camera.x >> 32 + 5;
		int startY = Camera.y >> 32 + 5;

		int finalX = startX + (Game.width >> 4) + 5;
		int finalY = startY + (Game.height >> 4) + 5;

		// Rendering of Tile.

		for (int xx = startX; xx <= finalX; xx++) {
			for (int yy = startY; yy <= finalY; yy++) {
				if (xx < 0 || yy < 0 || xx >= width || yy >= height) {
					continue;
				}

				Tile tile = tiles[xx + (yy * width)];
				tile.render(g);
			}
		}
	}
}
