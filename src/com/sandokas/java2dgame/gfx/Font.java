package com.sandokas.java2dgame.gfx;

public class Font {
	
	private static String chars = "" + "ABCDEFGHIJKLMNOPQRSTUVWXYZ      "+ "0123456789.,:;'\"!?$%()-=+/      ";
	public static final int nTiles = 32;
	public static void render(String msg, Screen screen, int x, int y) {
		for (int i = 0; i < msg.length(); i++) {
			int charIndex = chars.indexOf(msg.toUpperCase().charAt(i));
			if (charIndex>=0) screen.render((x + i*8), y, charIndex + nTiles);
		}
	}

}
