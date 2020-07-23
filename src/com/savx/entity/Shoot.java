package com.savx.entity;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.savx.main.Game;
import com.savx.world.Camera;
import com.savx.world.World;

public class Shoot extends Entity {

	private double dx;
	private double dy;
	private double spd = 3;
	
	private int timer = 0;
	private int maxTimer = 120;

	public Shoot(int x, int y, int width, int height, double dx, double dy, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		
		this.dx = dx;
		this.dy = dy;
		
	}

	public void update() {
		x += dx * spd;
		y += dy * spd;
		
		timer++;
		if (timer > maxTimer)
		{
			Game.shoot.remove(this);
			return;
		}
	}

	public void render(Graphics g) {
		g.setColor(Color.white);
		g.fillOval(this.getX() - Camera.x, this.getY() - Camera.y, 4, 4);
	}
}
