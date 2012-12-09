package engine;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;
import engine.Render;
import engine.Scene;
import engine.Screen;
import engine.TransformManager;
import engine.input.InputManager;
import engine.math.Point3D;
import engine.math.Vector3D;
import engine.objects.Light;
import engine.objects.BilliardBall;
import engine.objects.BilliardCamera;
import engine.physics.Movable;
import engine.physics.BillardPhysicsManager;
import engine.shapes.Triangle2D;
import engine.shapes.Triangle3D;


@SuppressWarnings("serial")
public abstract class BilliardGame extends JPanel {
	
	private int FPS = 24;
	private int screenWidth = 1200;
	private int screenHeight = 700;
	
	private InputManager inputManager; 
	private BillardPhysicsManager physicsManager; 
	private Screen screen;
	private Scene scene;
	private Render render;
	
	/**
	 * Constructor for Game.
	 */
	public BilliardGame(){
		
		screen = new Screen(screenWidth, screenHeight);
		
		render = new Render();
		
		inputManager = new InputManager();
		physicsManager = new BillardPhysicsManager(56); 

    	setBackground(Color.black);
		
		JFrame f = new JFrame ("3Dilliard");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);   
        f.getContentPane().add(this);
        f.pack();
        f.setVisible(true);
        f.setSize(screenWidth, screenHeight);
        setSize(screenWidth, screenHeight);
        
        f.addKeyListener(inputManager);
        f.addMouseListener(inputManager);
		
	}
	
	/**
	 * Game loop.
	 */
	public void run() {
		init();
		long lastUpdateTime = new Date().getTime();
		while(true){
			long delta = new Date().getTime() - lastUpdateTime;
			if (delta > 1000 / FPS){
				lastUpdateTime = new Date().getTime();
				
				update(delta);
				draw();
			} else {
				try {
					Thread.sleep(1000 / FPS - delta);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	protected abstract void init();
	
	/**
	 * Updates the game.
	 * @param delta
	 */
	protected abstract void update(long delta);
	
	/**
	 * Draws the game.
	 */
	protected void draw() {
		
		render.render(scene, screen);
		
		repaint();
		
	}
	
	public void paintComponent(Graphics g) {
		
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, getWidth(), getHeight());
		

		for(Triangle2D t : render.getRenderedTriangles()){
			int[]x = new int[3];
			int[]y = new int[3];
			x[0] = (int) t.getA().getX(); x[1] = (int) t.getB().getX(); x[2] = (int) t.getC().getX();			
			y[0] = (int) t.getA().getY(); y[1] = (int) t.getB().getY(); y[2] = (int) t.getC().getY();
			Polygon p = new Polygon(x, y, 3);
			
			g.setColor(t.getColor());
			//System.out.println("t2d's color = "+t.getColor());
			//g.fillPolygon(x, y, 3);
			
			g.setColor(Color.GREEN);
			g.drawLine( (int)t.getA().getX(), (int)t.getA().getY(), 
					 	(int)t.getB().getX(), (int)t.getB().getY());
 
			g.drawLine( (int)t.getB().getX(), (int)t.getB().getY(), 
					 	(int)t.getC().getX(), (int)t.getC().getY());
 
			g.drawLine( (int)t.getC().getX(), (int)t.getC().getY(), 
						(int)t.getA().getX(), (int)t.getA().getY());

		}
	}
	
	public Color calculateColor(Triangle3D t){
		int BnW = 0;
		
		for(Light l: scene.getLights()){		
			BnW += (int) t.getSurfaceNormal().getDotProduct(l.getPosition().toVector());
		}
		return new Color(BnW, BnW, BnW);
	}
	
}
