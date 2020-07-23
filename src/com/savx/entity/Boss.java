package com.savx.entity;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import com.savx.main.Game;
import com.savx.world.Camera;
import com.savx.world.World;

public class Boss extends Entity {

	private BufferedImage[] rightBoss;
	private BufferedImage[] leftBoss;
	private BufferedImage damagedRightBoss;
	private BufferedImage damagedLeftBoss;

	private int life = 100;

	private int rightDir = 0;
	private int leftDir = 1;
	private int dir = leftDir;

	private int frames = 0;
	private int maxFrames = 5;
	private int currentAnimation = 0;
	private int maxAnimation = 0;
	
	private int damagedFrames = 0;

	private int maskX = 4;
	private int maskY = 4;
	private int maskWidth = 50;
	private int maskHeight = 50;

	private double spd = 1.4;

	private boolean isDamaged = false;

	private String state = "First State";

	public Boss(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);

		rightBoss = new BufferedImage[6];
		leftBoss = new BufferedImage[6];

		damagedRightBoss = Game.spriteboss.getSprite(0, 2 * 64, 64, 64);
		damagedLeftBoss = Game.spriteboss.getSprite(64, 2 * 64, 64, 64);

		for (int i = 0; i < 6; i++) {
			rightBoss[i] = Game.spriteboss.getSprite(0 + (i * 64), 0, 64, 64);
		}

		for (int i = 0; i < 6; i++) {
			leftBoss[i] = Game.spriteboss.getSprite(0 + (i * 64), 64, 64, 64);
		}
	}

	public void update() {
		if (!isCollidingWithPlayer()) {
			if ((int) x < Game.player.getX() - 5 && World.isCollidingWithTile((int) (x + spd), this.getY())) {
				dir = leftDir;
				x += spd;
			}

			else if ((int) x > Game.player.getX() + 5 && World.isCollidingWithTile((int) (x - spd), this.getY())) {
				dir = rightDir;
				x -= spd;
			}

			if ((int) y < Game.player.getY() - 5 && World.isCollidingWithTile(this.getX(), (int) (y + spd))) {
				y += spd;
			}

			else if ((int) y > Game.player.getY() + 5 && World.isCollidingWithTile(this.getX(), (int) (y - spd))) {
				y -= spd;
			}
		} else {
			Game.player.life -= 5;
		}

		collisionBullet();

		if (life <= 0) {
			System.exit(1);
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

		// States.

		if (state == "First State") {
			spd = 1.4;
			if (life < 50) {
				state = "Second State";
			}
		}

		if (state == "Second State") {
			spd = 1.6;
			if (life <= 5) {
				life = 60;
				state = "Final State";
			}
		}

		if (state == "Final State") {
			spd = 1.8;
		}
	}

	public void render(Graphics g) {

		if (!isDamaged) {
			if (dir == rightDir) {
				g.drawImage(rightBoss[currentAnimation], this.getX() - Camera.x, this.getY() - Camera.y, null);
			}

			else if (dir == leftDir) {
				g.drawImage(leftBoss[currentAnimation], this.getX() - Camera.x, this.getY() - Camera.y, null);
			}
		}

		if (isDamaged) {
			if (dir == rightDir) {
				g.drawImage(damagedRightBoss, this.getX() - Camera.x, this.getY() - Camera.y, null);
			}

			else if (dir == leftDir) {
				g.drawImage(damagedLeftBoss, this.getX() - Camera.x, this.getY() - Camera.y, null);
			}
		}
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
}
