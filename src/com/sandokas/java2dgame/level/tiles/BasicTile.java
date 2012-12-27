package com.sandokas.java2dgame.level.tiles;

import com.sandokas.java2dgame.gfx.Screen;
import com.sandokas.java2dgame.level.Level;

public class BasicTile extends Tile {
	
	protected int tileId;

	public BasicTile(int id, int x, int y) {
		super(id, false, false);
		this.tileId = x+y;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void render(Screen screen, Level level, int x, int y) {
		screen.render(x, y, tileId);
		
	}

}
