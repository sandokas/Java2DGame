package com.sandokas.java2dgame.entities;

import com.sandokas.java2dgame.InputHandler;
import com.sandokas.java2dgame.gfx.Screen;
import com.sandokas.java2dgame.level.Level;

public class Player extends Mob{

	private InputHandler input;
	protected boolean isSwimming = false;
	private int tickCount =0;
	
	public Player(Level level, String name, int x, int y, InputHandler input) {
		super(level, "Player", x, y, 1);
		this.input = input;
	}

	@Override
	protected boolean hasCollided(int xa, int ya) {
		int xMin = 2;
		int xMax = 5;
		int yMin = 3;
		int yMax = 7;
		for (int x = xMin; x < xMax; x++) {
			if (isSolidTile(xa,ya, x, yMin) || isSolidTile(xa,ya, x, yMax)) {
				return true;
			}
		}
		for (int y = yMin; y < yMax; y++) {
			if (isSolidTile(xa,ya, xMin, y) || isSolidTile(xa,ya, xMax, y)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void tick() {
		int xa = 0;
		int ya = 0;
		if (input.up.isPressed()) {
			ya--;
		}
		if (input.down.isPressed()) {
			ya++;
		}
		if (input.left.isPressed()) {
			xa--;
		}
		if (input.right.isPressed()) {
			xa++;
		}
		if (xa != 0 || ya != 0) {
			move(xa , ya);
			isMoving = true;
		} else {
			isMoving = false;
		}
		if (level.getTile(this.x >>3, this.y>>3).getId() == 3) {
			isSwimming = true;
		}
		if (isSwimming && level.getTile(this.x >>3, this.y>>3).getId() != 3) {
			isSwimming = false;
		}
		tickCount++;
	} 

	@Override
	public void render(Screen screen) {
		int xTile = 0;
		int yTile = 3;
		int displayWalkingSpeed = 4;
		int flipTop = (numSteps>>displayWalkingSpeed) & 1;
		int flipBottom = flipTop;
		
		if (movingDir == 0) {
			xTile += 2;
		} else if (movingDir > 1) {
			xTile += 4 + flipTop * 2;
			flipTop = (movingDir - 1) % 2;
			//if (movingDir==3) 
				flipBottom = flipTop;
			
		}
		
		
		
		int modifier = 8 * scale;
		int xOffset = x - modifier/2;
		int yOffset = y - modifier/2 -4;
		
		if (isSwimming) {
			int waterColour = 0;
			yOffset += 4;
			if ((tickCount % 60) < 15) {
				waterColour = 3 + 32*6;
			} else if ((tickCount % 60) >= 15 && (tickCount % 60) < 30) {
				waterColour = 1 + 32*6;
			} else if ((tickCount % 60) >= 30 && (tickCount % 60) < 45) {
				waterColour = 0 + 32*6;
			} else {
				yOffset -= 1;
				waterColour = 1 + 32*6;			
			}
				
			screen.render(xOffset, yOffset + 3, waterColour, 0x00);
			screen.render(xOffset + 8, yOffset + 3, waterColour, 0x01);
		}
		
		screen.render(xOffset + (modifier *flipTop), yOffset, xTile + yTile * 32, flipTop);
		screen.render(xOffset + modifier - (modifier *flipTop), yOffset, xTile + 1 + yTile * 32, flipTop);
		
		if (!isSwimming) {
			screen.render(xOffset + (modifier *flipBottom), yOffset + modifier, xTile + (yTile + 1) * 32, flipBottom);
			screen.render(xOffset + modifier - (modifier *flipBottom), yOffset + modifier, xTile + 1 + (yTile + 1) * 32, flipBottom);
		}
	}

}
