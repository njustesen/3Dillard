package game.managers;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class InputManager implements KeyListener {

	boolean leftDown;
	boolean rightDown;
	boolean upDown;
	boolean downDown;
	boolean spaceDown;
	boolean shiftDown;
	boolean wDown;
	boolean sDown;
	boolean aDown;
	boolean dDown;
	boolean controlDown;

	public int getHorizontalArrows(){
		int h = 0;
		if (leftDown) h--;
		if (rightDown) h++;
		return h;
	}
	
	public int getVerticalArrows(){
		int v = 0;
		if (upDown) v--;
		if (downDown) v++;
		return v;
	}
	
	public int getHorizontalWASD(){
		int h = 0;
		if (aDown) h--;
		if (dDown) h++;
		return h;
	}
	
	public int getVerticalWASD(){
		int v = 0;
		if (wDown) v--;
		if (sDown) v++;
		return v;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		
		switch (e.getKeyCode()) {
			case KeyEvent.VK_LEFT: leftDown = true; break;
			case KeyEvent.VK_RIGHT: rightDown = true; break;
			case KeyEvent.VK_UP: upDown = true; break;
			case KeyEvent.VK_DOWN: downDown = true; break;
			case KeyEvent.VK_W: wDown = true; break;
			case KeyEvent.VK_S: sDown = true; break;
			case KeyEvent.VK_A: aDown = true; break;
			case KeyEvent.VK_D: dDown = true; break;
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		
		switch (e.getKeyCode()) {
			case KeyEvent.VK_LEFT: leftDown = false; break;
			case KeyEvent.VK_RIGHT: rightDown = false; break;
			case KeyEvent.VK_UP: upDown = false; break;
			case KeyEvent.VK_DOWN: downDown = false; break;
			case KeyEvent.VK_W: wDown = false; break;
			case KeyEvent.VK_S: sDown = false; break;
			case KeyEvent.VK_A: aDown = false; break;
			case KeyEvent.VK_D: dDown = false; break;
		}
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}
