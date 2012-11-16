import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;



public class Display extends JFrame implements KeyListener{

	private int width, height;
	public Camera camera;
	public Main m;
	private ImagePanel panel;
	private String keyStringPressed;
	private String keyStringReleased;
	//ArrayList <Integer> al = new ArrayList<Integer>();


	
	public Display(int w, int h, Camera cam, Main m){
		width = w;
		height = h;
		camera = cam;
		panel = new ImagePanel();
		this.m = m;
		
		this.add(panel);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    this.setSize(width, height);
	    this.setVisible(true);
	    
	    addKeyListener(this);
	}
	
	public double getScreenRatio(){
		double ratio = (double)width/(double)height;
		return ratio;
	}
	
	public void draw(){
		repaint();
	}
	
	//private inner class
	 private class ImagePanel extends JPanel {
		    
		  
		   public void paintComponent(Graphics g) {
			
			   g.setColor(Color.BLACK);
			   g.fillRect(0, 0, width, height);
			   
			   g.setColor(Color.WHITE);
			   System.out.println("numberOfPoints = "+m.getNumberOfPoints());
			   
			   for(int i = 0; i < m.getNumberOfPoints(); i+=6){
		
				   g.drawLine((int)m.pointsToBeDrawn[i], (int)m.pointsToBeDrawn[i+1], 
						   (int)m.pointsToBeDrawn[i+2], (int)m.pointsToBeDrawn[i+3]);
				   g.drawLine((int)m.pointsToBeDrawn[i+2], (int)m.pointsToBeDrawn[i+3], 
						   (int)m.pointsToBeDrawn[i+4], (int)m.pointsToBeDrawn[i+5]);
				   g.drawLine((int)m.pointsToBeDrawn[i+4],(int)m.pointsToBeDrawn[i+5], 
						   (int)m.pointsToBeDrawn[i], (int)m.pointsToBeDrawn[i+1]);
		
		//ArrayList
		/*		   System.out.println("i = "+i+"   size = "+al.size());
				   System.out.println("from "+al.get(i)+","+ al.get(i+1)+" to "+al.get(i+2)+","+al.get(i+3));
				   System.out.println("from "+al.get(i+2)+","+al.get(i+3)+" to "+al.get(i+4)+","+al.get(i+5));
				   System.out.println("from "+al.get(i+4)+","+al.get(i+5)+" to "+al.get(i)+","+al.get(i+1));
			   
				   g.drawLine(al.get(i), al.get(i+1), 
					   		al.get(i+2), al.get(i+3));
				   g.drawLine(al.get(i+2), al.get(i+3), 
				   			al.get(i+4), al.get(i+5));
				   g.drawLine(al.get(i+4), al.get(i+5), 
				   			al.get(i),al.get(i+1));
		*/	   
			  
			   }
			}
	}

	 @Override
		public void keyPressed(KeyEvent e) {
			System.out.println("key pressed");
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
			m.keyIsPressed(keyStringPressed);
		}

		@Override
		public void keyReleased(KeyEvent e) {
			System.out.println("key released");
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
			m.keyIsReleased(keyStringReleased);
		}

		@Override
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}
}
