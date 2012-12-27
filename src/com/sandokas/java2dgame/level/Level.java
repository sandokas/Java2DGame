package com.sandokas.java2dgame.level;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import com.sandokas.java2dgame.entities.Entity;
import com.sandokas.java2dgame.entities.Player;
import com.sandokas.java2dgame.gfx.Screen;
import com.sandokas.java2dgame.level.tiles.Tile;

public class Level {
	private byte[] tiles;
	public int width;
	public int height;
	public List<Entity> entities = new ArrayList<Entity>();
	private String imagePath;
	private BufferedImage image;
	
	
	public Level(String imagePath) {
		if (imagePath != null) {
			this.imagePath = imagePath;
			this.loadLevelFromFile();
			
		} else {
			this.width = 64;
			this.height = 64;
			tiles= new byte[width*height];
			this.generateLevel();
		}
	}
	
	private void loadLevelFromFile() {
		try {
			this.image = ImageIO.read(Level.class.getResource(this.imagePath));
			this.width = image.getWidth();
			this.height = image.getHeight();
			tiles = new byte[width*height];
			loadTiles();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void loadTiles() {
		int[] tileColours = this.image.getRGB(0, 0, width, height, null, 0, width);
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				tileCheck: for (Tile t : Tile.tiles) {
					if (t != null) {
						if (t.getColour() == tileColours[x + y * width]) {
							this.tiles[x + y * width] = t.getId();
							break tileCheck;
						}
					}
				}
			}
		}
	}

	private void saveLevelToFile() {
		try {
			ImageIO.write(image,"png",new File(Level.class.getResource(this.imagePath).getFile()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void alterTile(int x, int y, Tile newTile) {
		this.tiles[x+y*width] = newTile.getId();
		image.setRGB(x, y, newTile.getColour());
	}
	
	public Level(int width, int height) {
		tiles= new byte[width*height];
		this.width = width;
		this.height = height;
		this.generateLevel();
	}
	public void tick () {
		for (Entity e : entities) {
			e.tick();
		}
	}
	public void generateLevel() {
		for (int y = 0; y<height; y++) {
			for (int x = 0; x<width; x++) {
				//if (y*x%10<7) {
				if ((Math.random()*10)<9) {
					tiles[x + y*width] = Tile.GRASS.getId();
				} else {
					tiles[x + y*width] = Tile.STONE.getId();
				}
			}
		}
	}
	public void renderTiles(Screen screen, int xOffset, int yOffset) {
		if (xOffset < 0) xOffset = 0;
		int xMaxOffset = ((width<<3) - screen.width);
		//System.out.println(xMaxOffset+":"+(width<<3)+":"+screen.width);
		if (xOffset > xMaxOffset) xOffset = xMaxOffset;
		if (yOffset < 0) yOffset = 0;
		int yMaxOffset = ((height<<3) - screen.height);
		if (yOffset > yMaxOffset) yOffset = yMaxOffset;
		
		screen.setOffset(xOffset, yOffset);
		
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < height; x++) {
				getTile(x,y).render(screen, this, x<<3, y<<3);
			}
			
		}
	}
	
	public void renderEntities (Screen screen) {
		for (Entity e : entities) {
			e.render(screen);
		}
	}

	public Tile getTile(int x, int y) {
		if (x<0 || x>= width || y<0 || y>= height) return Tile.VOID;
		return Tile.tiles[tiles[x+y * width]];
	}
	public void addEntity(Entity entity) {
		this.entities.add(entity);
	}
}
