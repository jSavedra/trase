package com.savx.entity;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import com.savx.main.Game;
import com.savx.world.Camera;
import com.savx.world.World;

public class Enemy1 extends Entity {

	private BufferedImage[] animationEnemy1;
	private BufferedImage damagedEnemy1;

	private double spd = 1.2;

	private int life = 2;

	private int frames = 0;
	private int maxFrames = 10;
	private int currentAnimation = 0;
	private int maxAnimation = 3;

	private int damagedFrames = 0;

	private int maskX = 4;
	private int maskY = 4;
	private int maskWidth = 24;
	private int maskHeight = 24;

	private boolean isDamaged = false;

	public Enemy1(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);

		animationEnemy1 = new BufferedImage[3];
		damagedEnemy1 = Game.sheet.getSprite(0, 32, 32, 32);

		for (int i = 0; i < 3; i++) {
			animationEnemy1[i] = Game.sheet.getSprite(0 + (i * 32), 2 * 32, 32, 32);
		}
	}

	public void update() {

		if (Game.gameState == "Playing") {
			if (isCollidingWithPlayer() == false) {
				if ((int) x < Game.player.getX() - 5 && World.isCollidingWithTile((int) (x + spd), this.getY())
						&& !isCollidingWithEnemies((int) (x + spd), this.getY())) {
					x += spd;
				}

				else if ((int) x > Game.player.getX() + 5 && World.isCollidingWithTile((int) (x - spd), this.getY())
						&& !isCollidingWithEnemies((int) (x - spd), this.getY())) {
					x -= spd;
				}

				if ((int) y < Game.player.getY() + 5 && World.isCollidingWithTile(this.getX(), (int) (y + spd))
						&& !isCollidingWithEnemies(this.getX(), (int) (y + spd))) {
					y += spd;
				}

				else if ((int) y > Game.player.getY() - 5 && World.isCollidingWithTile(this.getX(), (int) (y - spd))
						&& !isCollidingWithEnemies(this.getX(), (int) (y - spd))) {
					y -= spd;
				}
			} else {
				Game.player.life--;
				Game.player.damaged = true;
				if (Game.player.life <= 0) {
					// System.exit(1);
				}
			}
		}

		collisionBullet();

		if (life <= 0) {
			destroySelf();
		}

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
			g.drawImage(animationEnemy1[currentAnimation], this.getX() - Camera.x, this.getY() - Camera.y, null);
		} else {
			g.drawImage(damagedEnemy1, this.getX() - Camera.x, this.getY() - Camera.y, null);
		}

		// g.setColor(Color.blue);
		// g.fillRect(this.getX() + maskX - Camera.x, this.getY() + maskY - Camera.y,
		// maskWidth, maskHeight);
	}

	public boolean isCollidingWithEnemies(int nextX, int nextY) {
		Rectangle currentEnemy = new Rectangle(nextX + maskX, nextY + maskY, maskWidth, maskHeight);
		for (int i = 0; i < Game.enemy1.size(); i++) {
			Entity e = Game.enemy1.get(i);
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

	public boolean isCollidingWithPlayer() {
		Rectangle currentEnemy = new Rectangle(this.getX() + maskX, this.getY() + maskY, this.getWidth() + maskWidth,
				this.getHeight() + maskHeight);
		Rectangle currentPlayer = new Rectangle(Game.player.getX(), Game.player.getY(), 32, 32);

		return currentEnemy.intersects(currentPlayer);
	}

	public void destroySelf() {
		Game.enemy1.remove(this);
	}
}
