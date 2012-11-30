package engine;

import java.awt.Color;
import java.util.ArrayList;
import java.util.PriorityQueue;


import engine.math.Matrix;
import engine.math.Point2D;
import engine.math.Point3D;
import engine.math.Vector3D;
import engine.shapes.Shape3D;
import engine.shapes.Triangle2D;
import engine.shapes.Triangle3D;
import game.objects.Light;


public class Renderer {
	
	private ArrayList<Triangle2D> lastRendering;
	private PriorityQueue<Triangle3D> pq;
	
	public Renderer(){
		
	//	lastRendering = new ArrayList<Triangle2D>();
		pq = new PriorityQueue<Triangle3D>();
		
	}

	/**
	 * Renders a 3D scene.
	 * The rendered triangles are stored locally.
	 * @param scene
	 * @param screen
	 */
	public void render(Scene scene, Screen screen){
		
		ArrayList<Triangle2D> newRendering = new ArrayList<Triangle2D>();
		
		//SETTING THE CAMERA LOCATION
		Matrix camTransMatrix = new Matrix(new double[][] {{1,0,0,-scene.getCamera().getX()},
				  							   {0,1,0,-scene.getCamera().getY()},
				  							   {0,0,1,-scene.getCamera().getZ()},
				  							   {0,0,0,1}});
		
		//POINTING AND ORIENTING THE CAMERA
		Vector3D povZ = new Vector3D( scene.getCamera().getLookPointX()-scene.getCamera().getX(),
									scene.getCamera().getLookPointY()-scene.getCamera().getY(),
									scene.getCamera().getLookPointZ()-scene.getCamera().getZ()).getUnitVector();
		
		Vector3D newX = scene.getCamera().getUpVector().getCrossProduct(povZ).getUnitVector();
		Vector3D newY = povZ.getCrossProduct(newX);
		
		Matrix camLookMatrix = new Matrix(new double[][]  {{newX.getX(),newX.getY(),newX.getZ(),0},
				   										   {newY.getX(),newY.getY(),newY.getZ(),0},
				   										   {povZ.getX(),povZ.getY(),povZ.getZ(),0},
				   										   {0,0,0,1}});
		
		//PERSPECTIVE/PROJECTION
		double near = 1;
		double far = 100000;
		
		double viewPlaneWidth = (Math.tan(scene.getCamera().getAngle()/2)*near)*2;
		double viewPlaneHeight = viewPlaneWidth/screen.getScreenRatio();

		
		Matrix projectionMatrix = new Matrix(new double[][]  {{(2*near)/viewPlaneWidth,0,0,0},
				   											  {0,(2*near)/viewPlaneHeight,0,0},
				   											  {0,0,-(far+near)/(far-near),(-2*far*near)/(far-near)},
				   											  {0,0,-1,0}});
		int newColor = 0;
		
		for(GameObject obj : scene.getObjects()){
			for(Shape3D shape: obj.getShapes()){
				for(Triangle3D t: shape.getTriangles()){
					
					
					// Triangle to object view
					Triangle3D objectTriangle = toObjectView(t, shape);
				
					// Triangle to world view
					Triangle3D worldTriangle = toWorldView(objectTriangle, obj);
					
					Triangle3D viewspaceTriangle = createViewspaceTriangle(worldTriangle, screen, projectionMatrix, camLookMatrix, camTransMatrix);
					
					
					for(Light light: scene.getLights()){
						Vector3D lightV3D = light.getPosition().toVector().subtract(t.getCenter().toVector());
						
					//	System.out.println("viewspaceNormal light dot = "+(int) (viewspaceTriangle.getSurfaceNormal().getDotProduct(lightV3D)*light.getIntensity()));
					//	System.out.println("l vector = "+light.getPosition());
						newColor = (int)(viewspaceTriangle.getSurfaceNormal().getDotProduct(lightV3D)*light.getIntensity());
					}
					viewspaceTriangle.setColor(newColor);
					pq.add(viewspaceTriangle);
				
					
				}
			}
		}
		
		while(!pq.isEmpty()){
			Triangle3D t = pq.poll();
			
			Point2D a2 = make2D(screen, t.getPointA());
			Point2D b2 = make2D(screen, t.getPointB());
			Point2D c2 = make2D(screen, t.getPointC());
			
			// Remove if outside screen
			if (!(screen.isOutsideScreen(a2) && screen.isOutsideScreen(b2) && screen.isOutsideScreen(c2))){
				Triangle2D t2 = new Triangle2D(a2,b2,c2);
				t2.setColor(t.getColor());
				newRendering.add(t2); 
				
			}
		}
		
		pq.clear();
		lastRendering = newRendering;
	}
	
	private Point3D toObjectView(Point3D p, Shape3D shape) {
		return new Point3D(	p.getX() + shape.getAnchor().getX(),
				p.getY() + shape.getAnchor().getY(),
				p.getZ() + shape.getAnchor().getZ()	);
	}

	private Point3D toWorldView(Point3D p, GameObject obj) {
		
		return new Point3D(	p.getX() + obj.getPosition().getX(),
							p.getY() + obj.getPosition().getY(),
							p.getZ() + obj.getPosition().getZ()	);
	}

	private Triangle3D toObjectView(Triangle3D t, Shape3D shape) {
		return new Triangle3D(new Point3D(t.getPointA().getX() + shape.getAnchor().getX(),
											t.getPointA().getY() + shape.getAnchor().getY(),
												t.getPointA().getZ() + shape.getAnchor().getZ()),
							  new Point3D(t.getPointB().getX() + shape.getAnchor().getX(),
									  		t.getPointB().getY() + shape.getAnchor().getY(),
									  			t.getPointB().getZ() + shape.getAnchor().getZ()),
							  new Point3D(t.getPointC().getX() + shape.getAnchor().getX(),
									  		t.getPointC().getY() + shape.getAnchor().getY(),
									  			t.getPointC().getZ() + shape.getAnchor().getZ()));
	}

	private Triangle3D toWorldView(Triangle3D t, GameObject obj) {
		
		return new Triangle3D(new Point3D(t.getPointA().getX() + obj.getPosition().getX(),
											t.getPointA().getY() + obj.getPosition().getY(),
												t.getPointA().getZ() + obj.getPosition().getZ()),
							  new Point3D(t.getPointB().getX() + obj.getPosition().getX(),
											t.getPointB().getY() + obj.getPosition().getY(),
												t.getPointB().getZ() + obj.getPosition().getZ()),
							  new Point3D(t.getPointC().getX() + obj.getPosition().getX(),
											t.getPointC().getY() + obj.getPosition().getY(),
												t.getPointC().getZ() + obj.getPosition().getZ())
							);
	}

	/**
	 * Renders a vertex.
	 * @param p
	 * @param screen
	 * @param projectionMatrix
	 * @param cameraLookMatrix
	 * @param cameraTransMatrix
	 * @return
	 */
	private Triangle3D createViewspaceTriangle(	Triangle3D t, 
												Screen screen, 
												Matrix projectionMatrix, 
												Matrix cameraLookMatrix, 
												Matrix cameraTransMatrix){
		
		Matrix viewspaceMatrix = projectionMatrix.multiplication(cameraLookMatrix.multiplication(cameraTransMatrix));

		Point3D viewspacePointA = viewspaceMatrix.multiplication(t.getPointA());
		Point3D viewspacePointB = viewspaceMatrix.multiplication(t.getPointB());
		Point3D viewspacePointC = viewspaceMatrix.multiplication(t.getPointC());
			
		viewspacePointA.setX(viewspacePointA.getX()/viewspacePointA.getW());
		viewspacePointA.setY(viewspacePointA.getY()/viewspacePointA.getW());
		viewspacePointA.setZ(viewspacePointA.getZ()/viewspacePointA.getW());
		
		viewspacePointB.setX(viewspacePointB.getX()/viewspacePointB.getW());
		viewspacePointB.setY(viewspacePointB.getY()/viewspacePointB.getW());
		viewspacePointB.setZ(viewspacePointB.getZ()/viewspacePointB.getW());
		
		viewspacePointC.setX(viewspacePointC.getX()/viewspacePointC.getW());
		viewspacePointC.setY(viewspacePointC.getY()/viewspacePointC.getW());
		viewspacePointC.setZ(viewspacePointC.getZ()/viewspacePointC.getW());
		
		Triangle3D viewspaceTriangle = new Triangle3D(viewspacePointA, viewspacePointB, viewspacePointC);
		
		return viewspaceTriangle;
	}
	
	private Point2D make2D(Screen screen, Point3D viewspacePoint){
		
		int x = (int) (viewspacePoint.getX()*(screen.getWidth()/2)+(screen.getWidth()/2));
		int y = (int) (viewspacePoint.getY()*(screen.getHeight()/2)+(screen.getHeight()/2));
		
		return new Point2D(x,y);
		
	}

	public ArrayList<Triangle2D> getRenderedTriangles() {
		return lastRendering;
	}
	
}
