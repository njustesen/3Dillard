package game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;
import engine.Renderer;
import engine.Scene;
import engine.Screen;
import engine.input.InputManager;
import engine.math.Point3D;
import engine.math.Vector3D;
import engine.physics.MovableBall;
import engine.shapes.Triangle2D;
import engine.shapes.Triangle3D;
import game.objects.Light;
import game.objects.PoolBall;
import game.objects.PoolCamera;


@SuppressWarnings("serial")
public class Game extends JPanel {
	
	private static final int FPS = 24;
	private static final int screenWidth = 1200;
	private static final int screenHeight = 700;
	
	private InputManager inputManager; 
	private PoolPhysicsManager physicsManager; 
	private Screen screen;
	private PoolScene scene;
	private Renderer renderer;
	
	private boolean shooting;
	
	/**
	 * Constructor for Game.
	 */
	public Game(){
		
		scene = new PoolScene();
		
		screen = new Screen(screenWidth, screenHeight);
		
		renderer = new Renderer();
		
		inputManager = new InputManager();
		physicsManager = new PoolPhysicsManager(30);
		
		physicsManager.setupFromScene(scene);

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
	
	private void init(){
		
		// Move camera
		scene.getCamera().setLookpoint( scene.getCueBall().getPosition() );
		
		shooting = false;
		
	}
	
	/**
	 * Updates the game.
	 * @param delta
	 */
	private void update(long delta) {
		
		// Move camera
		scene.getCamera().moveX(inputManager.getHorizontalArrows() * scene.getCamera().getMovementSpeed());
		scene.getCamera().moveLookpointX(-inputManager.getHorizontalWASD() * scene.getCamera().getRotationSpeed());
		scene.getCamera().moveY(-inputManager.getVerticalArrows() * scene.getCamera().getMovementSpeed());
		scene.getCamera().moveLookpointY(-inputManager.getVerticalWASD() * scene.getCamera().getRotationSpeed());
		
		// Add force to ball
		shoot();

		// Simulate physics
		physicsManager.move(delta);
		
	}
	
	private void shoot(){
		
		if (inputManager.isMouseLeftDown() && scene.getCueBall().getVelocity().equals(Vector3D.Zero)){
			
			shooting = true;
			
		} else {
		
			if (shooting && inputManager.getMouseDownTime() > 0){
						
				Vector3D force = ((PoolCamera) scene.getCamera()).getShootingDirection();
				double power = Math.min(16, inputManager.getMouseDownTime() / 100);
				force = force.multiply(power);
						
				scene.getCueBall().addVelocity( force );
				
				shooting = false;
			}
	
			// Move camera
			scene.getCamera().setLookpoint( scene.getCueBall().getPosition() );
			
		}
		
	}

	/**
	 * Draws the game.
	 */
	private void draw() {
		
		renderer.render(scene, screen);
		
		repaint();
		
	}
	
	public void paintComponent(Graphics g) {
		
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, getWidth(), getHeight());
		

		for(Triangle2D t : renderer.getRenderedTriangles()){
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
