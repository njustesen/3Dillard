package engine;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JPanel;
import engine.input.InputManager;
import engine.objects.Light;
import engine.physics.BillardPhysicsManager;
import engine.rendering.Renderer;
import engine.rendering.WireframeRenderer;
import engine.rendering.Scene;
import engine.rendering.Screen;
import engine.shapes.Triangle2D;
import engine.shapes.Triangle3D;

@SuppressWarnings("serial")
public abstract class BilliardGame extends JPanel {
	
	protected int FPS = 24;
	protected int screenWidth = 1200;
	protected int screenHeight = 700;
	
	protected InputManager inputManager; 
	protected BillardPhysicsManager physicsManager; 
	protected Screen screen;
	protected Scene scene;
	protected Renderer renderer;
	
	/**
	 * Constructor for Game.
	 */
	public BilliardGame(){
		
		screen = new Screen(screenWidth, screenHeight);
		
		renderer = new WireframeRenderer();
		
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
	private void draw() {
		
		renderer.render(scene, screen, this);
		
		//repaint();
		
	}
	
	public void paintComponent(Graphics g) {
		
		g.drawImage(renderer.getLastRendering(), 0, 0, null);
		
	}
	
	public Color calculateColor(Triangle3D t){
		int BnW = 0;
		
		for(Light l: scene.getLights()){		
			BnW += (int) t.getSurfaceNormal().getDotProduct(l.getPosition().toVector());
		}
		return new Color(BnW, BnW, BnW);
	}

	public int getFPS() {
		return FPS;
	}

	public void setFPS(int fPS) {
		FPS = fPS;
	}

	public int getScreenWidth() {
		return screenWidth;
	}

	public void setScreenWidth(int screenWidth) {
		this.screenWidth = screenWidth;
	}

	public int getScreenHeight() {
		return screenHeight;
	}

	public void setScreenHeight(int screenHeight) {
		this.screenHeight = screenHeight;
	}

	public BillardPhysicsManager getPhysicsManager() {
		return physicsManager;
	}

	public void setPhysicsManager(BillardPhysicsManager physicsManager) {
		this.physicsManager = physicsManager;
	}

	public Screen getScreen() {
		return screen;
	}

	public void setScreen(Screen screen) {
		this.screen = screen;
	}

	public Scene getScene() {
		return scene;
	}

	public void setScene(Scene scene) {
		this.scene = scene;
	}

	public Renderer getRenderer() {
		return renderer;
	}

	public void setRender(Renderer renderer) {
		this.renderer = renderer;
	}
	
}
