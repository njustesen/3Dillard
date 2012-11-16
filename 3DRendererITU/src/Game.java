import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Game extends JPanel {
	
	private static final int FPS = 24;
	private static final int screenWidth = 1200;
	private static final int screenHeight = 700;
	
	private Universe universe;
	private Camera camera;
	private InputManager inputManager = new InputManager(); 
	
	private ArrayList<Triangle2> renderedTriangles;
	
	public Game(){
		
		universe = new Universe();
		
		camera = new Camera(new Point3(2,100,2),
							new Point3(550,3,550),
							new Vector3(0,1,0), 70);
		camera.setMovementSpeed(30);
		camera.setRotationSpeed(30);
		
		renderedTriangles = new ArrayList<Triangle2>();
		
		inputManager = new InputManager();

    	setBackground(Color.black);
		
		JFrame f = new JFrame ("3Dilliard");
        // Sets the behavior for when the window is closed
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        f.getContentPane().add(this);
        // arrange the components inside the window
        f.pack();
        
        //By default, the window is not visible. Make it visible.
        f.setVisible(true);
        f.setSize(screenWidth, screenHeight);

        setSize(screenWidth, screenHeight);
        f.addKeyListener(inputManager);
		
	}

	public void render(){
		
		//SETTING THE CAMERA LOCATION
		Matrix camTransMatrix = new Matrix(new double[][] {{1,0,0,-camera.getX()},
				  							   {0,1,0,-camera.getY()},
				  							   {0,0,1,-camera.getZ()},
				  							   {0,0,0,1}});
		
		//POINTING AND ORIENTING THE CAMERA
		Vector3 povZ = new Vector3(camera.getLookPointX()-camera.getX(),
									camera.getLookPointY()-camera.getY(),
										camera.getLookPointZ()-camera.getZ()).getUnitVector();
		
		Vector3 newX = camera.getUpVector().getCrossProduct(povZ).getUnitVector();
		Vector3 newY = povZ.getCrossProduct(newX);
		
		Matrix camLookMatrix = new Matrix(new double[][]  {{newX.getX(),newX.getY(),newX.getZ(),0},
				   										   {newY.getX(),newY.getY(),newY.getZ(),0},
				   										   {povZ.getX(),povZ.getY(),povZ.getZ(),0},
				   										   {0,0,0,1}});
		
		//PERSPECTIVE/PROJECTION
		double near = 1;
		double far = 100000;
		
		double viewPlaneWidth = (Math.tan(camera.getAngle()/2)*near)*2;
		double viewPlaneHeight = viewPlaneWidth/getScreenRatio();

		
		Matrix projectionMatrix = new Matrix(new double[][]  {{(2*near)/viewPlaneWidth,0,0,0},
				   											  {0,(2*near)/viewPlaneHeight,0,0},
				   											  {0,0,-(far+near)/(far-near),(-2*far*near)/(far-near)},
				   											  {0,0,-1,0}});
		
		ArrayList<Triangle2> triangles = new ArrayList<Triangle2>();
		
		for(Pyramid py: universe.shapesInUniverse){
			for(Triangle t: py.triangles){
					
				Point2 a = renderVertice(t.getPointA(), projectionMatrix, camLookMatrix, camTransMatrix);
				Point2 b = renderVertice(t.getPointB(), projectionMatrix, camLookMatrix, camTransMatrix);
				Point2 c = renderVertice(t.getPointC(), projectionMatrix, camLookMatrix, camTransMatrix);
				
				// Remove outside screen
				if (!(outsideScreen(a) && outsideScreen(b) && outsideScreen(c))){
					
					triangles.add(new Triangle2(a,b,c)); 
					
				}
			}	
		}		
		
		renderedTriangles = triangles;
		
	}
	
	private boolean outsideScreen(Point2 p) {
		if (p.getX() < 0 || p.getX() >= screenWidth){
			return true;
		}
		if (p.getY() < 0 || p.getY() >= screenHeight){
			return true;
		}
		return false;
	}

	private Point2 renderVertice(Point3 p, Matrix projectionMatrix, Matrix cameraLookMatrix, Matrix cameraTransMatrix){
		
		Matrix viewspaceMatrix = projectionMatrix.multiplication(cameraLookMatrix.multiplication(cameraTransMatrix));

		Point3 viewspacePoint = viewspaceMatrix.multiplication(p);
		
		viewspacePoint.setX(viewspacePoint.getX()/viewspacePoint.getW());
		viewspacePoint.setY(viewspacePoint.getY()/viewspacePoint.getW());
		viewspacePoint.setZ(viewspacePoint.getZ()/viewspacePoint.getW());

		int x = (int) (viewspacePoint.getX()*(getWidth()/2)+(getWidth()/2));
		int y = (int) (viewspacePoint.getY()*(getHeight()/2)+(getHeight()/2));
		
		return new Point2(x,y);
		
	}
	
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
	
	private void update(long delta) {
		
		// Move camera
		camera.moveX(-inputManager.getHorizontalArrows() * camera.getMovementSpeed());
		camera.moveLookpointX(-inputManager.getHorizontalWASD() * camera.getRotationSpeed());
		camera.moveY(-inputManager.getVerticalArrows() * camera.getMovementSpeed());
		camera.moveLookpointY(-inputManager.getVerticalWASD() * camera.getRotationSpeed());
			
		render();
		
	}

	private void draw() {
		
		//display.update(getGraphics());
		
		repaint();
		
	}
	
	public void paintComponent(Graphics g) {
		
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

	private double getScreenRatio(){
		
		return (double)getWidth()/(double)getHeight();
		
	}

	public ArrayList<Triangle2> getRenderedTriangles() {
		return renderedTriangles;
	}

	public void setRenderedTriangles(ArrayList<Triangle2> renderedTriangles) {
		this.renderedTriangles = renderedTriangles;
	}
	
}
