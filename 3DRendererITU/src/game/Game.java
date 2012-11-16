package game;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Date;
import javax.swing.JFrame;
import javax.swing.JPanel;
import engine.Renderer;
import engine.Scene;
import engine.Screen;
import engine.shapes.Triangle2D;
import game.managers.InputManager;


@SuppressWarnings("serial")
public class Game extends JPanel {
	
	private static final int FPS = 24;
	private static final int screenWidth = 1200;
	private static final int screenHeight = 700;
	
	private InputManager inputManager; 
	private Screen screen;
	private Scene scene;
	private Renderer renderer;
	
	/**
	 * Constructor for Game.
	 */
	public Game(){
		
		scene = new PoolScene();
		
		screen = new Screen(screenWidth, screenHeight);
		
		renderer = new Renderer();
		
		inputManager = new InputManager();

    	setBackground(Color.black);
		
		JFrame f = new JFrame ("3Dilliard");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);   
        f.getContentPane().add(this);
        f.pack();
        f.setVisible(true);
        f.setSize(screenWidth, screenHeight);
        setSize(screenWidth, screenHeight);
        
        f.addKeyListener(inputManager);
		
	}
	
	/**
	 * Game loop.
	 */
	public void run() {
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
	
	/**
	 * Updates the game.
	 * @param delta
	 */
	private void update(long delta) {
		
		// Move camera
		scene.getCamera().moveX(-inputManager.getHorizontalArrows() * scene.getCamera().getMovementSpeed());
		scene.getCamera().moveLookpointX(-inputManager.getHorizontalWASD() * scene.getCamera().getRotationSpeed());
		scene.getCamera().moveY(-inputManager.getVerticalArrows() * scene.getCamera().getMovementSpeed());
		scene.getCamera().moveLookpointY(-inputManager.getVerticalWASD() * scene.getCamera().getRotationSpeed());
		
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
		g.setColor(Color.GREEN);

		for(Triangle2D t : renderer.getRenderedTriangles()){

			g.drawLine( (int)t.getA().getX(), (int)t.getA().getY(), 
					 	(int)t.getB().getX(), (int)t.getB().getY());
 
			g.drawLine( (int)t.getB().getX(), (int)t.getB().getY(), 
					 	(int)t.getC().getX(), (int)t.getC().getY());
 
			g.drawLine( (int)t.getC().getX(), (int)t.getC().getY(), 
						(int)t.getA().getX(), (int)t.getA().getY());

		}
	}
	
}
