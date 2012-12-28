package com.sandokas.java2dgame.level.tiles;

import com.sandokas.java2dgame.gfx.Screen;
import com.sandokas.java2dgame.level.Level;

public abstract class Tile {

	public static final Tile[] tiles = new Tile[256];
	public static final Tile VOID = new BasicSolidTile(0,0,0, 0xffff00ff);
	public static final Tile STONE = new BasicSolidTile(1,1,0, 0xff555555);
	public static final Tile GRASS = new BasicTile(2,2,0, 0xff00ff00);
	public static final Tile WATER = new AnimatedTile(3,new int[][] {{0,5},{1,5},{2,5},{1,5}}, 0xff0000ff, 1000);

	protected byte id;
	protected boolean solid;
	protected boolean emitter;
	private int colour;
	
	public Tile (int id, boolean isSolid, boolean isEmitter, int colour) {
		this.id = (byte) id;
		if (tiles[id] != null) throw new RuntimeException("Duplicate tile id on " + id);
		this.solid = isSolid;
		this.emitter = isEmitter;
		this.colour = colour;
		tiles[id] = this;
	}
	
	public byte getId() {
		return id;
	}
	
	public abstract void tick();
	
	public abstract void render(Screen screen, Level level, int x, int y);

	public boolean isSolid(Tile newTile) {
		return solid;
	}

	public int getColour() {
		return colour;
	} 

}
