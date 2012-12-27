package com.sandokas.java2dgame;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

public class InputHandler implements KeyListener{

	public InputHandler (Game game) {
		game.addKeyListener(this);
	}
	public class Key {
		private boolean pressed = false;
		public boolean isPressed() {
			return pressed;
		}
		public void toggle(boolean isPressed) {
			pressed = isPressed;
		}
	}
	public List<Key> keys = new ArrayList<Key>();
	public Key up = new Key();
	public Key down = new Key();
	public Key left = new Key();
	public Key right = new Key();
	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		toggleKey(arg0.getKeyCode(), true);
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		toggleKey(arg0.getKeyCode(), false);
	}
	
	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	public void toggleKey (int keyCode, boolean isPressed) {
		if (keyCode == KeyEvent.VK_W || keyCode == KeyEvent.VK_UP){up.toggle(isPressed);}
		if (keyCode == KeyEvent.VK_S || keyCode == KeyEvent.VK_DOWN){down.toggle(isPressed);}
		if (keyCode == KeyEvent.VK_A || keyCode == KeyEvent.VK_LEFT){left.toggle(isPressed);}
		if (keyCode == KeyEvent.VK_D || keyCode == KeyEvent.VK_RIGHT){right.toggle(isPressed);}
	}
}
