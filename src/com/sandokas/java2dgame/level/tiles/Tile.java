package com.sandokas.java2dgame.level.tiles;

import com.sandokas.java2dgame.gfx.Screen;
import com.sandokas.java2dgame.level.Level;

public abstract class Tile {

	public static final Tile[] tiles = new Tile[256];
	public static final Tile VOID = new BasicSolidTile(0,0,0);
	public static final Tile STONE = new BasicSolidTile(1,1,0);
	public static final Tile GRASS = new BasicTile(2,2,0);

	protected byte id;
	protected boolean solid;
	protected boolean emitter;
	
	public Tile (int id, boolean isSolid, boolean isEmitter) {
		this.id = (byte) id;
		if (tiles[id] != null) throw new RuntimeException("Duplicate tile id on " + id);
		this.solid = isSolid;
		this.emitter = isEmitter;
		tiles[id] = this;
	}
	
	public byte getId() {
		return id;
	}
	
	public abstract void render(Screen screen, Level level, int x, int y);

	public boolean isSolid(Tile newTile) {
		return solid;
	} 

}
