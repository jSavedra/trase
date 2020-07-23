package com.savx.entity;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.savx.main.Game;
import com.savx.world.Camera;

public class Life extends Entity {

	private BufferedImage[] animation;

	private int frames = 0;
	private int maxFrames = 10;
	private int currentAnimation = 0;
	private int maxAnimation = 6;

	private int waitFrames = 0;
	private int waitMaxFrames = 10;
	private int waitAnimation = 0;
	private int waitMaxAnimation = 6;

	private boolean wait = false;

	public Life(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);

		animation = new BufferedImage[6];

		for (int i = 0; i < 6; i++) {
			animation[i] = Game.sheet.getSprite(0 + (i * 32), 5 * 32, 32, 32);
		}
	}

	public void update() {
		if (!wait) {
			frames++;
			if (frames > maxFrames) {
				frames = 0;
				currentAnimation++;
				if (currentAnimation >= maxAnimation) {
					currentAnimation = 0;
					wait = true;
				}
			}
		}

		// Wait Animation.

		if (wait) {
			waitFrames++;
			if (waitFrames >= waitMaxFrames) {
				waitFrames = 0;
				waitAnimation++;
				if (waitAnimation >= waitMaxAnimation) {
					waitAnimation = 0;
					wait = false;
				}
			}
		}
	}

	public void render(Graphics g) {
		g.drawImage(animation[currentAnimation], this.getX() - Camera.x, this.getY() - Camera.y, null);
	}
}
