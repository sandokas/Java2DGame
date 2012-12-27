package com.sandokas.java2dgame.gfx;

public class Screen {

	
	public static final byte BIT_MIRROR_X = 0x01;
	public static final byte BIT_MIRROR_Y = 0x02;
	
	public static final int tileSize = 8;
	public static final int[] invisible = new int[]{(255<<24) | (255<<16) | (0<<8) | (255<<0),(255<<24) | (127<<16) | (0<<8) | (127<<0)};
	
	private int xOffset = 0;
	private int yOffset = 0;
	
	public int width;
	public int height;
	public SpriteSheet sheet;
	private int[] pixels;
	
	public Screen(int width, int height, SpriteSheet sheet, int[] pixels) {
		this.width = width;
		this.height = height;
		this.sheet = sheet;
		this.pixels = pixels;
	}
	public void bgRender() {
		for (int yTile = yOffset>>3; yTile <= (yOffset + height)>>3; yTile++) {
			int yMin = yTile * 8 - yOffset;
			int yMax = yMin + 8;
			if (yMin<0) yMin = 0;
			if (yMax>height) yMax = height;
			for (int xTile = xOffset>>3; xTile <= (xOffset + width)>>3; xTile++) {
				int xMin = xTile * 8 - xOffset;
				int xMax = xMin + 8;
				if (xMin<0) xMin = 0;
				if (xMax>width) xMax = width;
				
				for (int y = yMin; y < yMax; y++) {
					for (int x = xMin; x < xMax; x++) {
						int sheetPixel = ((y + yOffset) & 7) * sheet.width + ((x + xOffset) & 7);
						int screenPixel = y*width + x;
						pixels[screenPixel] = sheet.pixels[sheetPixel];
						//pixels[screenPixel] = 125<<16 | 125<<8 | 125;
					}
				}
			}			
		}
	}
	public void render (int xPos, int yPos, int tile) {
		render (xPos, yPos, tile, 0);
	}
	public void render (int xPos, int yPos, int tile, int mirrorDir) {
		boolean mirrorX = (mirrorDir & BIT_MIRROR_X) > 0;
		boolean mirrorY = (mirrorDir & BIT_MIRROR_Y) > 0;
		render(xPos, yPos, tile, mirrorX, mirrorY, 1);
	}
	public void render (int xPos, int yPos, int tile, boolean mirrorX, boolean mirrorY, int scale) {
		xPos -= xOffset;
		yPos -= yOffset;
		//tamanho da screensheet = 256 / 8 bit per tile = 32!!
		int scaleMap = scale - 1;
		int xTile = tile % (sheet.width / tileSize);
		int yTile = tile / (sheet.height / tileSize);
		int tileOffset = (xTile<<3) + (yTile<<3)*sheet.width;
		for (int y = 0; y <8; y++) {
			int ySheet = y;
			if (mirrorY) ySheet = 7-y;
			int yPixel = y + yPos + (y * scaleMap) - ((scaleMap<<3)/2);
			
			for (int x = 0; x <8; x++) {
				int xSheet = x;
				if (mirrorX) 
					xSheet = 7-x;
				int xPixel = x + xPos + (x * scaleMap) - ((scaleMap<<3)/2);
				int col = sheet.pixels[xSheet + ySheet *sheet.width + tileOffset];
				//System.out.println(col + ":" + invisible);
				if (col != invisible[0] && col != invisible[1]) {
					for (int yScale = 0; yScale < scale; yScale++) {
						if (yPixel + yScale < 0 || yPixel + yScale >= height) 
							continue;
						for (int xScale = 0; xScale < scale; xScale++) {
							if (xPixel + xScale < 0 || xPixel + xScale >= width) 
								continue;
							pixels[(xPixel + xScale) + (yPixel + yScale) * width] = col;
						}
					}
				}
				//System.out.println("col:"+col);
			}			
		}
	}
	public void setOffset(int xOffset2, int yOffset2) {
		xOffset = xOffset2;
		yOffset = yOffset2;
	}
}
