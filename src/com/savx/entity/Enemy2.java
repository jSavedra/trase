package com.savx.entity;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import com.savx.main.Game;
import com.savx.world.Camera;
import com.savx.world.World;

public class Enemy2 extends Entity {

	private BufferedImage[] rightEnemy2;
	private BufferedImage[] leftEnemy2;
	private BufferedImage damagedRightEnemy2;
	private BufferedImage damagedLeftEnemy2;

	private boolean isDamaged = false;

	private int life = 5;

	private int maskX = 2;
	private int maskY = 2;
	private int maskWidth = 16;
	private int maskHeight = 16;

	private int frames = 0;
	private int maxFrames = 10;
	private int currentAnimation = 0;
	private int maxAnimation = 6;

	private int damagedFrames = 0;

	private int rightDir = 0;
	private int leftDir = 1;
	private int dir = rightDir;

	private double spd = 1.4;

	public Enemy2(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);

		rightEnemy2 = new BufferedImage[6];
		leftEnemy2 = new BufferedImage[6];
		damagedRightEnemy2 = Game.sheet.getSprite(32, 32, 32, 32);
		damagedLeftEnemy2 = Game.sheet.getSprite(64, 32, 32, 32);

		for (int i = 0; i < 6; i++) {
			rightEnemy2[i] = Game.sheet.getSprite(0 + (i * 32), 3 * 32, 32, 32);
		}

		for (int i = 0; i < 6; i++) {
			leftEnemy2[i] = Game.sheet.getSprite(0 + (i * 32), 4 * 32, 32, 32);
		}
	}

	public void update() {
		if (Game.gameState == "Playing") {
			if (!isCollidingWithPlayer()) {

				if ((int) x < Game.player.getX() - 5 && World.isCollidingWithTile((int) (x + spd), this.getY())
						&& !isCollidingWithEnemy((int) (x + spd), this.getY())) {
					dir = rightDir;
					x += spd;
				}

				else if ((int) x > Game.player.getX() + 5 && World.isCollidingWithTile((int) (x - spd), this.getY())
						&& !isCollidingWithEnemy((int) (x - spd), this.getY())) {
					dir = leftDir;
					x -= spd;
				}

				if ((int) y < Game.player.getY() + 5 && World.isCollidingWithTile(this.getX(), (int) (y + spd))
						&& !isCollidingWithEnemy(this.getX(), (int) (y + spd))) {
					y += spd;
				}

				else if ((int) y > Game.player.getY() - 5 && World.isCollidingWithTile(this.getX(), (int) (y - spd))
						&& !isCollidingWithEnemy(this.getX(), (int) (y - spd))) {
					y -= spd;
				}

				frames++;
				if (frames > maxFrames) {
					frames = 0;
					currentAnimation++;
					if (currentAnimation >= maxAnimation) {
						currentAnimation = 0;
					}
				}
			} else {
				Game.player.life -= 4;
				if (Game.player.life < 0) {
					// System.exit(1);
				}
			}
		}

		if (life <= 0) {
			destroySelf();
		}

		collisionBullet();

		frames++;
		if (frames > maxFrames) {
			frames = 0;
			currentAnimation++;
			if (currentAnimation >= maxAnimation) {
				currentAnimation = 0;
			}
		}

		if (isDamaged) {
			damagedFrames++;
			if (damagedFrames >= 5) {
				damagedFrames = 0;
				isDamaged = false;
			}
		}
	}

	public void render(Graphics g) {

		if (!isDamaged) {
			if (dir == rightDir) {
				g.drawImage(rightEnemy2[currentAnimation], this.getX() - Camera.x, this.getY() - Camera.y, null);
			}

			else if (dir == leftDir) {
				g.drawImage(leftEnemy2[currentAnimation], this.getX() - Camera.x, this.getY() - Camera.y, null);
			}
		}

		else if (isDamaged && dir == rightDir) {
			g.drawImage(damagedRightEnemy2, this.getX() - Camera.x, this.getY() - Camera.y, null);
		}

		else if (isDamaged && dir == leftDir) {
			g.drawImage(damagedLeftEnemy2, this.getX() - Camera.x, this.getY() - Camera.y, null);
		}
	}

	public boolean isCollidingWithEnemy(int nextX, int nextY) {
		Rectangle currentEnemy = new Rectangle(nextX + maskX, nextY + maskY, maskWidth, maskHeight);
		for (int i = 0; i < Game.enemy2.size(); i++) {
			Enemy2 e = Game.enemy2.get(i);
			if (e == this) {
				continue;
			}
			Rectangle targetEnemy = new Rectangle(e.getX() + maskX, e.getY() + maskY, maskWidth, maskHeight);
			if (currentEnemy.intersects(targetEnemy)) {
				return true;
			}
		}

		return false;
	}

	public void collisionBullet() {
		for (int i = 0; i < Game.shoot.size(); i++) {
			Entity e = Game.shoot.get(i);
			if (Entity.isCollidingWithEntities(this, e)) {
				isDamaged = true;
				life--;
				Game.shoot.remove(i);
				return;
			}
		}
	}

	public void destroySelf() {
		Game.enemy2.remove(this);
	}

	public boolean isCollidingWithPlayer() {
		Rectangle currentEnemy = new Rectangle(this.getX() + maskX, this.getY() + maskY, this.getWidth() + maskWidth,
				this.getHeight() + maskHeight);
		Rectangle currentPlayer = new Rectangle(Game.player.getX(), Game.player.getY(), 32, 32);

		return currentEnemy.intersects(currentPlayer);
	}
}
