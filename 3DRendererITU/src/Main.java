import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.JFrame;

public class Main extends JFrame implements Runnable{
	
	private Universe universe;
	private Camera camera1;
	private Matrix xRotation;
	private Matrix yRotation;
	private Matrix zRotation;
	public double [] pointsToBeDrawn;
	private Display display;
	private int numberOfPoints;
	private int fps = 60;
	private int camreaSpeed = 20;
	private int screenWidth = 800;
	private int screenHeight = 400;
	private boolean moveCamLeft;
	private boolean moveCamRight;
	private boolean moveCamDown;
	private boolean moveCamUp;
	private boolean moveLookpointDown;
	private boolean moveLookpointUp;
	private boolean moveLookpointLeft;
	private boolean moveLookpointRight;
	
	//ArrayList <Integer> pointsToBeDrawn = new ArrayList<Integer>();
	Thread runThread = new Thread(this);
	
	public static void main(String [] args){
		Main m = new Main();	
		m.initialize();
	}

	public int getNumberOfPoints(){
		return numberOfPoints;
	}
	public void setNumberOfPoints(int number){
		numberOfPoints = number;
	}	
	
	public void initialize(){
		
		xRotation = new Matrix(new double[][] {{1,0,0,0},
				  			 				   {0,Math.cos(0),-Math.sin(0),0},
				  			 				   {0,Math.sin(0),Math.cos(0),0},
				  			 				   {0,0,0,1}});

		yRotation = new Matrix(new double[][] {{Math.cos(0),0,Math.sin(0),0},
						  					   {0,1,0,0},
						  					   {-Math.sin(0),0,Math.cos(0),0},
						  					   {0,0,0,1}});
		
		zRotation = new Matrix(new double[][] {{Math.cos(0),-Math.sin(0),0,0},
						  					   {Math.sin(0),Math.cos(0),0,0},
						  					   {0,0,1,0},
						  					   {0,0,0,1}});
		
		universe = new Universe();
	
		pointsToBeDrawn = new double[1000000];
		
		camera1 = new Camera(new Point3(2,100,2),new Point3(550,3,550),new Vector3(0,1,0), 70);
		display = new Display(screenWidth,screenHeight,camera1, this);
		
		runThread.run();

	}
	

	public void render(){
		
		
		
		//SETTING THE CAMERA LOCATION
		Matrix camTransMatrix = new Matrix(new double[][] {{1,0,0,-camera1.getX()},
				  							   {0,1,0,-camera1.getY()},
				  							   {0,0,1,-camera1.getZ()},
				  							   {0,0,0,1}});
		
		//POINTING AND ORIENTING THE CAMERA
		Vector3 povZ = new Vector3(camera1.getLookPointX()-camera1.getX(),
									camera1.getLookPointY()-camera1.getY(),
										camera1.getLookPointZ()-camera1.getZ()).getUnitVector();
		
		Vector3 newX = camera1.getUpVector().getCrossProduct(povZ).getUnitVector();
		Vector3 newY = povZ.getCrossProduct(newX);
		
		Matrix camLookMatrix = new Matrix(new double[][]  {{newX.getX(),newX.getY(),newX.getZ(),0},
				   										   {newY.getX(),newY.getY(),newY.getZ(),0},
				   										   {povZ.getX(),povZ.getY(),povZ.getZ(),0},
				   										   {0,0,0,1}});
		
		//PERSPECTIVE/PROJECTION
		double near = 1;
		double far = 100000;
		
		double viewPlaneWidth = (Math.tan(camera1.getAngle()/2)*near)*2;
		double viewPlaneHeight = viewPlaneWidth/display.getScreenRatio();

		
		Matrix projectionMatrix = new Matrix(new double[][]  {{(2*near)/viewPlaneWidth,0,0,0},
				   											  {0,(2*near)/viewPlaneHeight,0,0},
				   											  {0,0,-(far+near)/(far-near),(-2*far*near)/(far-near)},
				   											  {0,0,-1,0}});
		
		int discartedpoints = 0;
		numberOfPoints = 0;
		int total = 0;
		for(Pyramid py: universe.shapesInUniverse){
			for(Triangle t: py.triangles){
				int p1x = 0;
				int p1y = 0;
				int p2x = 0;
				int p2y = 0;
				int p3x = 0;
				int p3y = 0;
				int c = 1;
				for(Point3 p: t.getVertices()){
				
				Matrix viewspaceMatrix = projectionMatrix.multiplication(camLookMatrix.multiplication(camTransMatrix));

				Point3 viewspacePoint = viewspaceMatrix.multiplication(p);
				
				viewspacePoint.setX(viewspacePoint.getX()/viewspacePoint.getW());
				viewspacePoint.setY(viewspacePoint.getY()/viewspacePoint.getW());
				viewspacePoint.setZ(viewspacePoint.getZ()/viewspacePoint.getW());
				
				if(c == 1){
					p1x = (int) (viewspacePoint.getX()*(display.getWidth()/2)+(display.getWidth()/2));
					p1y = (int) (viewspacePoint.getY()*(display.getHeight()/2)+(display.getHeight()/2));
				}else if(c == 2){
					p2x = (int) (viewspacePoint.getX()*(display.getWidth()/2)+(display.getWidth()/2));
					p2y = (int) (viewspacePoint.getY()*(display.getHeight()/2)+(display.getHeight()/2));
				}else if(c == 3){
					p3x = (int) (viewspacePoint.getX()*(display.getWidth()/2)+(display.getWidth()/2));
					p3y = (int) (viewspacePoint.getY()*(display.getHeight()/2)+(display.getHeight()/2));
				}
	total++;
				c++;
				}
				if(p1x > 0 && p1x < screenWidth && p1y > 0 && p1y < screenHeight ||
						p2x > 0 && p2x < screenWidth && p2y > 0 && p2y < screenHeight ||
						p3x > 0 && p3x < screenWidth && p3y > 0 && p3y < screenHeight){
					pointsToBeDrawn[numberOfPoints] = p1x;
					pointsToBeDrawn[numberOfPoints+1] = p1y;
					pointsToBeDrawn[numberOfPoints+2] = p2x;
					pointsToBeDrawn[numberOfPoints+3] = p2y;
					pointsToBeDrawn[numberOfPoints+4] = p3x;
					pointsToBeDrawn[numberOfPoints+5] = p3y;
					numberOfPoints += 6;
				}else{
					discartedpoints += 6;
				}
			}	
		}
		System.out.println("discartedpoints = "+discartedpoints);
		System.out.println("MAIN numberOfPoints = "+numberOfPoints);
		System.out.println("total = "+total);
		display.draw();
		
		try {
			Thread.sleep(1000/fps);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void run() {
		while(true){
		
			if(moveCamLeft){
				camera1.setX(camera1.getX()+camreaSpeed);
				camera1.setLookpointX(camera1.getLookPointX()+camreaSpeed);
				}
			if(moveCamRight){
				camera1.setX(camera1.getX()-camreaSpeed);
				camera1.setLookpointX(camera1.getLookPointX()-camreaSpeed);
				}
			if(moveCamUp){
				camera1.setY(camera1.getY()+camreaSpeed);
				camera1.setLookpointY(camera1.getLookPointY()+camreaSpeed);
				}
			if(moveCamDown){
				camera1.setY(camera1.getY()-camreaSpeed);
				camera1.setLookpointY(camera1.getLookPointY()-camreaSpeed);
				}
			if(moveLookpointUp){
				camera1.setLookpointY(camera1.getLookPointY()+camreaSpeed);
				}
			if(moveLookpointDown){
				camera1.setLookpointY(camera1.getLookPointY()-camreaSpeed);
				}
			if(moveLookpointLeft){
				camera1.setLookpointX(camera1.getLookPointX()+camreaSpeed);
				}
			if(moveLookpointRight){
				camera1.setLookpointX(camera1.getLookPointX()-camreaSpeed);
				}
			render();
		}
		
	}

	
	public void keyIsPressed(String key) {
		System.out.println("key pressed = "+key);
		if(key == "RIGHT")
			moveCamRight = true;
		if(key == "LEFT")
			moveCamLeft = true;
		if(key == "UP")
			moveCamUp = true;
		if(key == "DOWN")
			moveCamDown = true;
		if(key == "W")
			moveLookpointUp = true;
		if(key == "S")
			moveLookpointDown = true;
		if(key == "A")
			moveLookpointLeft = true;
		if(key == "D")
			moveLookpointRight = true;
	}

	public void keyIsReleased(String key) {
		System.out.println("key released = "+key);
		if(key == "RIGHT")
			moveCamRight = false;
		if(key == "LEFT")
			moveCamLeft = false;
		if(key == "UP")
			moveCamUp = false;
		if(key == "DOWN")
			moveCamDown = false;
		if(key == "W")
			moveLookpointUp = false;
		if(key == "S")
			moveLookpointDown = false;
		if(key == "A")
			moveLookpointLeft = false;
		if(key == "D")
			moveLookpointRight = false;
		
	}
}
