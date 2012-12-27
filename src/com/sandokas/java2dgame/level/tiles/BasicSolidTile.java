package com.sandokas.java2dgame.level.tiles;

public class BasicSolidTile extends BasicTile {

	public BasicSolidTile(int id, int x, int y, int colour) {
		super(id, x, y, colour);
		this.solid = true;
	}

}
