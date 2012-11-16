import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;



public class Display extends JPanel implements KeyListener{

	private String keyStringPressed;
	private String keyStringReleased;
	private ArrayList<Triangle2> renderedTriangles;

	public Display(int w, int h){
		this.setSize(w, h);
	    this.setVisible(true);
	    addKeyListener(this);
	    renderedTriangles = new ArrayList<Triangle2>();
	}
	
	public double getScreenRatio(){
		
		return (double)getWidth()/(double)getHeight();
		
	}
	
	public void draw(){
		update(getGraphics());
	}
	
	public void paintComponent(Graphics g, ArrayList<Triangle2> renderedTriangles) {
		
		 g.setColor(Color.BLACK);
		 g.fillRect(0, 0, getWidth(), getHeight());
		 g.setColor(Color.GREEN);

		 for(Triangle2 t : renderedTriangles){

			 g.drawLine((int)t.getA().getX(), (int)t.getA().getY(), 
					 	(int)t.getB().getX(), (int)t.getB().getY());
  
			 g.drawLine((int)t.getB().getX(), (int)t.getB().getY(), 
					 	(int)t.getC().getX(), (int)t.getC().getY());
  
			 g.drawLine((int)t.getC().getX(), (int)t.getC().getY(), 
					 	(int)t.getA().getX(), (int)t.getA().getY());

		 }
	 }

	 @Override
		public void keyPressed(KeyEvent e) {
			int keyPressed = e.getKeyCode();
			
			switch (keyPressed) {
			case KeyEvent.VK_LEFT: keyStringPressed = "LEFT"; break;
			case KeyEvent.VK_RIGHT: keyStringPressed = "RIGHT"; break;
			case KeyEvent.VK_UP: keyStringPressed = "UP"; break;
			case KeyEvent.VK_DOWN: keyStringPressed = "DOWN"; break;
			case KeyEvent.VK_W: keyStringPressed = "W"; break;
			case KeyEvent.VK_S: keyStringPressed = "S"; break;
			case KeyEvent.VK_A: keyStringPressed = "A"; break;
			case KeyEvent.VK_D: keyStringPressed = "D"; break;
			default: keyStringPressed = "null"; break;
			}
			//m.keyIsPressed(keyStringPressed);
		}

		@Override
		public void keyReleased(KeyEvent e) {
			int keyReleased = e.getKeyCode();
			
			switch (keyReleased) {
			case KeyEvent.VK_LEFT: keyStringReleased = "LEFT"; break;
			case KeyEvent.VK_RIGHT: keyStringReleased = "RIGHT"; break;
			case KeyEvent.VK_UP: keyStringReleased = "UP"; break;
			case KeyEvent.VK_DOWN: keyStringReleased = "DOWN"; break;
			case KeyEvent.VK_W: keyStringReleased = "W"; break;
			case KeyEvent.VK_S: keyStringReleased = "S"; break;
			case KeyEvent.VK_A: keyStringReleased = "A"; break;
			case KeyEvent.VK_D: keyStringReleased = "D"; break;
			default: keyStringReleased = null; break;
			}
			//m.keyIsReleased(keyStringReleased);
		}

		@Override
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}

		public ArrayList<Triangle2> getRenderedTriangles() {
			return renderedTriangles;
		}

		public void setRenderedTriangles(ArrayList<Triangle2> renderedTriangles) {
			this.renderedTriangles = renderedTriangles;
		}
		
}
