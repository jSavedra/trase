package com.savx.entity;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import com.savx.main.Game;
import com.savx.world.Camera;
import com.savx.world.World;

public class Player extends Entity {

	private BufferedImage[] rightPlayer;
	private BufferedImage[] leftPlayer;
	private BufferedImage[] idleRightPlayer;
	private BufferedImage[] idleLeftPlayer;
	private BufferedImage[] damagedRightPlayer;
	private BufferedImage[] damagedLeftPlayer;
	private BufferedImage[] damagedIdleRightPlayer;
	private BufferedImage[] damagedIdleLeftPlayer;

	public boolean right, left, up, down;
	private boolean moved = false;
	public boolean damaged = false;
	public boolean shooting = false;

	private double spd = 1.6;

	public int mx;
	public int my;

	private int frames = 0;
	private int maxFrames = 3;
	private int currentAnimation = 0;
	private int maxAnimation = 10;

	private int idleFrames = 0;
	private int idleMaxFrames = 8;
	private int idleAnimation = 0;
	private int idleMaxAnimation = 6;

	private int damagedFrames = 0;
	private int maxDamagedFrames = 3;
	private int damagedAnimation = 0;
	private int maxDamagedAnimation = 10;

	private int damagedIdleFrames = 0;
	private int damagedIdleMaxFrames = 8;
	private int damagedIdleAnimation = 0;
	private int damagedIdleMaxAnimation = 6;

	private int rightDir = 0;
	private int leftDir = 1;
	private int dir = rightDir;

	public double life = 200;
	public int maxLife = 200;

	public boolean restart = false;

	public Player(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);

		rightPlayer = new BufferedImage[10];
		leftPlayer = new BufferedImage[10];
		idleRightPlayer = new BufferedImage[6];
		idleLeftPlayer = new BufferedImage[6];
		damagedRightPlayer = new BufferedImage[10];
		damagedLeftPlayer = new BufferedImage[10];
		damagedIdleRightPlayer = new BufferedImage[6];
		damagedIdleLeftPlayer = new BufferedImage[6];

		// Animation Walk

		for (int i = 0; i < 10; i++) {
			rightPlayer[i] = Game.sheet.getSprite(9 * 32, 0 + (i * 32), 32, 32);
		}

		for (int i = 0; i < 10; i++) {
			leftPlayer[i] = Game.sheet.getSprite(8 * 32, 0 + (i * 32), 32, 32);
		}

		// Idle.

		for (int i = 0; i < 6; i++) {
			idleRightPlayer[i] = Game.sheet.getSprite(0 + (i * 32), 8 * 32, 32, 32);
		}

		for (int i = 0; i < 6; i++) {
			idleLeftPlayer[i] = Game.sheet.getSprite(0 + (i * 32), 9 * 32, 32, 32);
		}

		// Damaged.

		for (int i = 0; i < 10; i++) {
			damagedRightPlayer[i] = Game.sheet.getSprite(7 * 32, 0 + (i * 32), 32, 32);
		}

		for (int i = 0; i < 10; i++) {
			damagedLeftPlayer[i] = Game.sheet.getSprite(6 * 32, 0 + (i * 32), 32, 32);
		}

		for (int i = 0; i < 6; i++) {
			damagedIdleRightPlayer[i] = Game.sheet.getSprite(0 + (i * 32), 6 * 32, 32, 32);
		}

		for (int i = 0; i < 6; i++) {
			damagedIdleLeftPlayer[i] = Game.sheet.getSprite(0 + (i * 32), 7 * 32, 32, 32);
		}
	}

	public void update() {
		moved = false;
		restart = false;
		if (right && World.isCollidingWithTile((int) (x + spd), this.getY())) {
			moved = true;
			dir = rightDir;
			x += spd;

		}
		if (left && World.isCollidingWithTile((int) (x - spd), this.getY())) {
			moved = true;
			dir = leftDir;
			x -= spd;
		}

		if (up && World.isCollidingWithTile(this.getX(), (int) (y - spd))) {
			moved = true;
			y -= spd;

		}
		if (down && World.isCollidingWithTile(this.getX(), (int) (y + spd))) {
			moved = true;
			y += spd;
		}

		// Bugs of Moved.

		if (right && left) {
			moved = false;
		}

		if (up && down) {
			moved = false;
		}

		if (right && down && left) {
			moved = true;
		}

		if (right && up && left) {
			moved = true;
		}

		/**/

		if (restart) {
			Game.gameState = "Restart";
		}

		if (life <= 0) {
			Game.gameState = "Restart";
		}

		if (shooting) {
			shooting = false;
			double angle = Math.atan2(my - (this.getY() + 8 - Camera.y), mx - (this.getX() + 8 - Camera.x));
			double dx = Math.cos(angle);
			double dy = Math.sin(angle);
			int px = 12;
			int py = 8;

			Shoot shoot = new Shoot(this.getX() + px, this.getY() + py, 3, 3, dx, dy, null);
			Game.shoot.add(shoot);
		}

		collisionLife();

		// Moved

		if (moved && !damaged) {
			frames++;
			if (frames > maxFrames) {
				frames = 0;
				currentAnimation++;
				if (currentAnimation >= maxAnimation) {
					currentAnimation = 0;
				}
			}
		}

		// Idle

		if (!moved && !damaged) {
			idleFrames++;
			if (idleFrames > idleMaxFrames) {
				idleFrames = 0;
				idleAnimation++;
				if (idleAnimation >= idleMaxAnimation) {
					idleAnimation = 0;
				}
			}
		}

		// Damaged.

		if (moved && damaged) {
			damagedFrames++;
			if (damagedFrames > maxDamagedFrames) {
				damagedFrames = 0;
				damagedAnimation++;
				damaged = false;
				if (damagedAnimation >= maxDamagedAnimation) {
					damagedAnimation = 0;
				}
			}
		}

		// Damaged Idle.

		if (!moved && damaged) {
			damagedIdleFrames++;
			if (damagedIdleFrames > damagedIdleMaxFrames) {
				damagedIdleFrames = 0;
				damagedIdleAnimation++;
				damaged = false;
				if (damagedIdleAnimation >= damagedIdleMaxAnimation) {
					damagedIdleAnimation = 0;
				}
			}
		}

		cameraAndClamp();

	}

	public void render(Graphics g) {

		// Moved Animation.

		if (!damaged) {
			if (dir == rightDir && moved) {
				g.drawImage(rightPlayer[currentAnimation], this.getX() - Camera.x, this.getY() - Camera.y, null);
			}

			else if (dir == leftDir && moved) {
				g.drawImage(leftPlayer[currentAnimation], this.getX() - Camera.x, this.getY() - Camera.y, null);
			}

			// Idle Animation.

			if (dir == rightDir && !moved) {
				g.drawImage(idleRightPlayer[idleAnimation], this.getX() - Camera.x, this.getY() - Camera.y, null);
			}

			else if (dir == leftDir && !moved) {
				g.drawImage(idleLeftPlayer[idleAnimation], this.getX() - Camera.x, this.getY() - Camera.y, null);
			}
		}

		// Damaged Render.

		if (damaged) {
			if (dir == rightDir && moved) {
				g.drawImage(damagedRightPlayer[damagedAnimation], this.getX() - Camera.x, this.getY() - Camera.y, null);
			}

			else if (dir == leftDir && moved) {
				g.drawImage(damagedLeftPlayer[damagedAnimation], this.getX() - Camera.x, this.getY() - Camera.y, null);
			}

			// Idle

			if (dir == rightDir && !moved) {
				g.drawImage(damagedIdleRightPlayer[damagedIdleAnimation], this.getX() - Camera.x,
						this.getY() - Camera.y, null);
			}

			else if (dir == leftDir && !moved) {
				g.drawImage(damagedIdleLeftPlayer[damagedIdleAnimation], this.getX() - Camera.x, this.getY() - Camera.y,
						null);
			}
		}
	}

	public void cameraAndClamp() {
		Camera.x = Camera.clamp(this.getX() - (Game.width / 2), 0, World.width * 32 - Game.width);
		Camera.y = Camera.clamp(this.getY() - (Game.height / 2), 0, World.height * 32 - Game.height);
	}

	public void collisionLife() {
		for (int i = 0; i < Game.entities.size(); i++) {
			Entity current = Game.entities.get(i);
			if (current instanceof Life) {
				if (Entity.isCollidingWithEntities(this, current) && life < 200) {
					life += 50;
					if (life >= 200) {
						life = 200;
					}
					Game.entities.remove(current);
				}
			}
		}
	}
}
