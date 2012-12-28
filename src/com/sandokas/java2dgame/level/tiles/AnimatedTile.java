package com.sandokas.java2dgame.level.tiles;

import com.sandokas.java2dgame.gfx.Screen;
import com.sandokas.java2dgame.level.Level;

public class AnimatedTile extends BasicTile {
	
	private int[][] animationTileCoords;
	private int currentAnimationIndex;
	private long lastIterationTime;
	private int animationSwitchDelay;

	public AnimatedTile(int id, int[][] animationCoords, int colour, int animationSwitchDelay) {
		super(id, animationCoords[0][0], animationCoords[0][1], colour);
		this.animationTileCoords = animationCoords;
		this.lastIterationTime = System.currentTimeMillis();
		this.animationSwitchDelay = animationSwitchDelay;
	}
	
	@Override
	public void render(Screen screen, Level level, int x, int y) {
		screen.render(x, y, tileId);
		
	}

	@Override
	public void tick() {
		if (System.currentTimeMillis() - lastIterationTime >= animationSwitchDelay) {
			lastIterationTime = System.currentTimeMillis();
			currentAnimationIndex = (currentAnimationIndex + 1) % animationTileCoords.length;
			tileId = (animationTileCoords[currentAnimationIndex][0] + (animationTileCoords[currentAnimationIndex][1]*32));
		}
	}

}
