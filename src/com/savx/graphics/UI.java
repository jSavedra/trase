package com.savx.graphics;

import java.awt.Color;
import java.awt.Graphics;
import com.savx.main.Game;

public class UI {

	public void render(Graphics g) {
		g.setColor(Color.red);
		g.fillRect(130, 4, 70, 8);
		g.setColor(Color.green);
		g.fillRect(130, 4, (int) ((Game.player.life / Game.player.maxLife) * 70), 8);
	}
}
