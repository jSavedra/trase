package com.savx.main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import com.savx.entity.Boss;
import com.savx.entity.Enemy1;
import com.savx.entity.Enemy2;
import com.savx.entity.Entity;
import com.savx.entity.Player;
import com.savx.entity.Shoot;
import com.savx.graphics.SpriteBoss;
import com.savx.graphics.Spritesheet;
import com.savx.graphics.UI;
import com.savx.world.World;

public class Game extends Canvas implements Runnable, KeyListener, MouseListener {

	private static final long serialVersionUID = 1L;
	private Thread thread;
	private JFrame frame;
	private BufferedImage img;
	private boolean isRunning;
	public static final int width = 320;
	public static final int height = 240;
	public static final int scale = 3;

	public static String gameState = "Tutorial";

	public static int currentLevel = 1;
	private int maxLevel = 6;

	public static Spritesheet sheet;
	public static SpriteBoss spriteboss;
	public static List<Entity> entities;
	public static List<Enemy1> enemy1;
	public static List<Enemy2> enemy2;
	public static List<Shoot> shoot;
	public static List<Boss> boss;
	public static Player player;

	public static World world;

	public static UI ui;

	public static void main(String[] args) {
		Game game = new Game();
		game.start();
	}

	public Game() {
		setPreferredSize(new Dimension(width * scale, height * scale));
		addMouseListener(this);
		addKeyListener(this);
		initFrame();

		img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		ui = new UI();
		entities = new ArrayList<Entity>();
		enemy1 = new ArrayList<Enemy1>();
		enemy2 = new ArrayList<Enemy2>();
		shoot = new ArrayList<Shoot>();
		boss = new ArrayList<Boss>();
		sheet = new Spritesheet("/spritesheet.png");
		spriteboss = new SpriteBoss("/Boss.png");
		player = new Player(0, 0, 16, 16, sheet.getSprite(0, 0, 32, 32));
		entities.add(player);
		world = new World("/level1.png");
	}

	public void initFrame() {
		frame = new JFrame("Game Test");
		frame.add(this);
		frame.setResizable(false);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

	public void update() {

		if (gameState == "Tutorial" || gameState == "Playing") {
			for (int i = 0; i < entities.size(); i++) {
				entities.get(i).update();
			}

			for (int i = 0; i < enemy1.size(); i++) {
				enemy1.get(i).update();
			}

			for (int i = 0; i < enemy2.size(); i++) {
				enemy2.get(i).update();
			}

			for (int i = 0; i < boss.size(); i++) {
				boss.get(i).update();
			}

			for (int i = 0; i < shoot.size(); i++) {
				shoot.get(i).update();
			}

			// Levels.

			if (currentLevel == 1 && enemy1.size() == 0 && enemy2.size() == 0) {
				currentLevel++;
				gameState = "Playing";
				if (currentLevel > maxLevel) {
					currentLevel = 1;
				}
				String newLevel = "level" + currentLevel + ".png";
				World.restartGame(newLevel);
			}

			if (currentLevel == 2 && enemy1.size() == 0) {
				currentLevel++;
				if (currentLevel > maxLevel) {
					currentLevel = 1;
				}
				String newLevel = "level" + currentLevel + ".png";
				World.restartGame(newLevel);
			}

			if (currentLevel == 3 && enemy1.size() == 0 && enemy2.size() == 0) {
				currentLevel++;
				if (currentLevel > maxLevel) {
					currentLevel = 1;
				}
				String newLevel = "level" + currentLevel + ".png";
				World.restartGame(newLevel);
			}

			if (currentLevel == 4 && enemy1.size() == 0 && enemy2.size() == 0) {
				currentLevel++;
				if (currentLevel > maxLevel) {
					currentLevel = 1;
				}
				String newLevel = "level" + currentLevel + ".png";
				World.restartGame(newLevel);
			}

			if (currentLevel == 5 && enemy1.size() == 0 && enemy2.size() == 0) {
				currentLevel++;
				if (currentLevel > maxLevel) {
					currentLevel = 1;
				}
				String newLevel = "level" + currentLevel + ".png";
				World.restartGame(newLevel);
			}

			if (currentLevel == 6 && boss.size() == 0) {
				currentLevel++;
				if (currentLevel > maxLevel) {
					currentLevel = 1;
				}
				String newLevel = "level" + currentLevel + ".png";
				World.restartGame(newLevel);
			}
		}

		if (gameState == "Restart" && player.restart == true) {
			gameState = "Playing";
			currentLevel = 2;
			String newLevel = "level" + currentLevel + ".png";
			World.restartGame(newLevel);
		}
	}

	public void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		Graphics g = img.getGraphics();
		g.setColor(Color.green);
		g.fillRect(0, 0, width, height);

		world.render(g);

		for (int i = 0; i < entities.size(); i++) {
			entities.get(i).render(g);
		}

		for (int i = 0; i < enemy1.size(); i++) {
			enemy1.get(i).render(g);
		}

		for (int i = 0; i < enemy2.size(); i++) {
			enemy2.get(i).render(g);
		}

		for (int i = 0; i < boss.size(); i++) {
			boss.get(i).render(g);
		}

		for (int i = 0; i < shoot.size(); i++) {
			shoot.get(i).render(g);
		}

		ui.render(g);

		g.dispose();
		g = bs.getDrawGraphics();
		g.drawImage(img, 0, 0, width * scale, height * scale, null);
		if (gameState == "Tutorial") {
			g.setColor(new Color(250, 250, 250));
			g.setFont(new Font("Arial", Font.BOLD, 30));
			g.drawString("Kill Them", 100, 50);
			g.setColor(new Color(250, 250, 250));
			g.drawString("\r\n" + "Press the left button to shoot", 300, 700);
		}
		if (gameState == "Restart") {
			Graphics2D g2 = (Graphics2D) g; // Works With Opacity.
			g2.setColor(new Color(0, 0, 0, 220));
			g2.fillRect(0, 0, width * (int) scale, height * (int) scale);
			g.setColor(new Color(250, 250, 250));
			g.setFont(new Font("Arial", Font.BOLD, 30));
			g.drawString("Game Over", 450, 250);
			g.drawString("Press Enter To Restart", 350, 350);
		}
		
		bs.show();
	}

	public synchronized void start() {
		isRunning = true;
		thread = new Thread(this);
		thread.start();
	}

	public synchronized void stop() {
		isRunning = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void run() {
		long lastTime = System.nanoTime();
		double ticks = 60.0;
		double ns = 1000000000 / ticks;
		double delta = 0;
		int frames = 0;
		double timer = System.currentTimeMillis();
		while (isRunning) {
			requestFocus();
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			if (delta >= 1) {
				update();
				render();
				frames++;
				delta--;
			}
			if (System.currentTimeMillis() - timer >= 1000) {
				System.out.println("FPS: " + frames);
				frames = 0;
				timer += 1000;
			}
		}
		stop();
	}

	public void keyTyped(KeyEvent e) {

	}

	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) {
			player.right = true;
		}

		else if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) {
			player.left = true;
		}

		if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W) {
			player.up = true;
		}

		else if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S) {
			player.down = true;
		}

		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			player.restart = true;
		}
	}

	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) {
			player.right = false;
		}

		else if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) {
			player.left = false;
		}

		if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W) {
			player.up = false;
		}

		else if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S) {
			player.down = false;
		}
	}

	public void mouseClicked(MouseEvent e) {

	}

	public void mousePressed(MouseEvent e) {
		player.shooting = true;
		player.mx = (e.getX() / 3);
		player.my = (e.getY() / 3);
	}

	public void mouseReleased(MouseEvent e) {

	}

	public void mouseEntered(MouseEvent e) {

	}

	public void mouseExited(MouseEvent e) {

	}
}
