package engine;

import java.util.ArrayList;

import engine.math.Matrix;
import engine.math.Point2D;
import engine.math.Point3D;
import engine.math.Triangle2D;
import engine.math.Triangle3D;
import engine.math.Vector3D;
import engine.shapes.Shape3D;


public class Renderer {
	
	private ArrayList<Triangle2D> lastRendering;
	
	public Renderer(){
		
		lastRendering = new ArrayList<Triangle2D>();
		
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
		
		for(GameObject obj : scene.getObjects()){
			for(Triangle3D t: obj.getShape().getTriangles()){
				
				// Triangle to world view
				Point3D a3 = toWorldView(t.getPointA(), obj);
				Point3D b3 = toWorldView(t.getPointB(), obj);
				Point3D c3 = toWorldView(t.getPointC(), obj);
				
				// Render points
				Point2D a2 = renderVertice(a3, screen, projectionMatrix, camLookMatrix, camTransMatrix);
				Point2D b2 = renderVertice(b3, screen, projectionMatrix, camLookMatrix, camTransMatrix);
				Point2D c2 = renderVertice(c3, screen, projectionMatrix, camLookMatrix, camTransMatrix);
				
				// Remove if outside screen
				if (!(screen.isOutsideScreen(a2) && screen.isOutsideScreen(b2) && screen.isOutsideScreen(c2))){
					
					newRendering.add(new Triangle2D(a2,b2,c2)); 
					
				}
			}	
		}		
		
		lastRendering = newRendering;
	}
	
	private Point3D toWorldView(Point3D p, GameObject obj) {
		
		return new Point3D(	p.getX() + obj.getPosition().getX(),
							p.getY() + obj.getPosition().getY(),
							p.getZ() + obj.getPosition().getZ()	);
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
	private Point2D renderVertice(	Point3D p, 
									Screen screen, 
									Matrix projectionMatrix, 
									Matrix cameraLookMatrix, 
									Matrix cameraTransMatrix){
		
		Matrix viewspaceMatrix = projectionMatrix.multiplication(cameraLookMatrix.multiplication(cameraTransMatrix));

		Point3D viewspacePoint = viewspaceMatrix.multiplication(p);
		
		viewspacePoint.setX(viewspacePoint.getX()/viewspacePoint.getW());
		viewspacePoint.setY(viewspacePoint.getY()/viewspacePoint.getW());
		viewspacePoint.setZ(viewspacePoint.getZ()/viewspacePoint.getW());

		int x = (int) (viewspacePoint.getX()*(screen.getWidth()/2)+(screen.getWidth()/2));
		int y = (int) (viewspacePoint.getY()*(screen.getHeight()/2)+(screen.getHeight()/2));
		
		return new Point2D(x,y);
		
	}

	public ArrayList<Triangle2D> getRenderedTriangles() {
		return lastRendering;
	}
	
}
