package com.sandokas.java2dgame;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javax.swing.JFrame;

import com.sandokas.java2dgame.entities.Player;
import com.sandokas.java2dgame.gfx.Colours;
import com.sandokas.java2dgame.gfx.Font;
import com.sandokas.java2dgame.gfx.Screen;
import com.sandokas.java2dgame.gfx.SpriteSheet;
import com.sandokas.java2dgame.level.Level;

public class Game extends Canvas implements Runnable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8664838135431037970L;
	public static final int WIDTH = 160;
	public static final int HEIGHT = WIDTH / 12 * 9;
	public static final int SCALE = 3;
	public static final String NAME = "Game";

	public boolean isRunning = false;
	private JFrame frame;
	final int TARGET_FPS = 64;
	final long OPTIMAL_TIME = 1000000000 / TARGET_FPS;
	public int tickCount = 0;
	private BufferedImage image = new BufferedImage(WIDTH,HEIGHT,BufferedImage.TYPE_INT_RGB);
	private int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();

	private Screen screen;
	public InputHandler input;
	public Level level;
	public Player player;

	public Game()  {
		// TODO Auto-generated method stub
		Dimension dimension = new Dimension(WIDTH*SCALE,HEIGHT*SCALE);
		setMinimumSize(dimension);
		setMaximumSize(dimension);
		setPreferredSize(dimension);
		frame = new JFrame(NAME);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		frame.setResizable(false);
		frame.setLocationRelativeTo(null); // This will put it on the center of the screen
		frame.setVisible(true);
		frame.add(this,BorderLayout.CENTER);
		frame.pack();
	}
	public void init() {
		SpriteSheet spriteSheet = new SpriteSheet("/sprite_sheet.png");
		screen = new Screen(WIDTH, HEIGHT, spriteSheet, pixels);
		input = new InputHandler(this);
		level = new Level(64,64);
		player = new Player(level,"Player1", screen.width / 2, screen.height/2,input);
		level.addEntity(player);
	}
	@Override
	public void run() {
		long lastTime = System.nanoTime();
		//		I've set this as static in the object
		int frames = 0;
		int ticks = 0;
		long lastTimer = System.currentTimeMillis();
		double delta = 0;
		boolean renderFrame = false;

		init();

		while(isRunning) {
			long now = System.nanoTime();
			long timeElapsed = now - lastTime;
			lastTime = now;
			delta += timeElapsed / (double) OPTIMAL_TIME;
			// If frame took too long to render try to catch up by doing several ticks before rendering next frame.
			while (delta >= 1) {
				ticks++;
				tick();
				delta--;
				renderFrame = true;
			}
			if (renderFrame) {
				frames++;
				render();
			}
			renderFrame = false;
			timeElapsed = System.currentTimeMillis() - lastTimer;
			if (timeElapsed >= 1000) {
				System.out.println("ticks:" + ticks + ",frames:" + frames + ",timeElapsed:" + timeElapsed);
				frames = 0;
				ticks = 0;
				lastTimer = System.currentTimeMillis();
			}
			// Try to sleep OPTIMAL_TIME between frames (we have to discount rendering time)
			try {
				long sleep = (lastTime - System.nanoTime() + OPTIMAL_TIME) / 1000000;
				if (sleep > 0) {
					Thread.sleep(sleep);
				}
			} catch (IllegalArgumentException e) {
				// do nothing - that means the frame took longer than 10 miliseconds
			} catch (InterruptedException e) {
				e.printStackTrace(System.out);
			}

		}
	}
	public synchronized void start() {
		isRunning = true;
		new Thread(this).start();
	}
	public synchronized void stop() {
		isRunning = false;
	}
	public static void main(String[] args) {
		new Game().start();
	}
	public void tick() {
		tickCount++;
		/*
		if (input.up.isPressed()) {if (yOffset > 0) yOffset--;}
		if (input.down.isPressed()) {
			if (yOffset < (level.height<<3) - screen.height) {
				yOffset++;
			}
		}
		if (input.left.isPressed()) {if (xOffset > 0) xOffset--;}
		if (input.right.isPressed()) {
			if (xOffset < (level.width<<3) - screen.width) {
				xOffset++;
			}
		}
		*/
		//screen.xOffset++;
		//screen.yOffset++;

		//for (int i = 0; i < pixels.length; i++) {
		//pixels[i] = (int) (255D * Math.random()); // só azuis;

		//int gray = (int) (255D * Math.random());
		//pixels[i] = (gray<<16) | (gray<<8) | (gray<<0);
		//	if (i < screen.sheet.pixels.length)
		//		pixels [i] = screen.sheet.pixels[i];
		//}
		level.tick();
	}
	public void render() {
		BufferStrategy bufferStrategy = getBufferStrategy();
		if (bufferStrategy == null) {
			createBufferStrategy(3);
			return;
		}
		
		//Render stuff
		int xOffset = player.x - (screen.width / 2);
		int yOffset = player.y - (screen.height / 2);
		//screen.render(0, 0, 0, 0, 10, 10);
		//screen.bgRender();
		level.renderTiles(screen, xOffset, yOffset);
		//screen.render(20, 20, 1, true, true);
		//Font.render("kcsit", screen, (screen.width/2)-("kcsit".length()*8/2), (screen.height/2)-4);
		//for (int x = 0; x<level.width; x++) {
		//	Font.render(((Integer) (x % 10)).toString(), screen, x*8, 0);
		//}
		level.renderEntities(screen);
		
		Graphics graphics = bufferStrategy.getDrawGraphics();
		graphics.setColor(Color.BLACK);
		graphics.fillRect(0, 0, getWidth(), getHeight());
		//for (int i = 0; i<500; i++) {
		graphics.drawImage(image, 0, 0, getWidth(), getHeight(), null);
		//}
		graphics.dispose();
		bufferStrategy.show();

	}
}
