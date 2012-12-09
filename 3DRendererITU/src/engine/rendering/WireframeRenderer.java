package engine.rendering;

import java.util.ArrayList;
import java.util.PriorityQueue;

import engine.math.Matrix;
import engine.math.Point2D;
import engine.math.Vector3D;
import engine.objects.GameObject;
import engine.objects.Light;
import engine.shapes.Shape3D;
import engine.shapes.Triangle2D;
import engine.shapes.Triangle3D;


public class WireframeRenderer extends Renderer {
	
	private PriorityQueue<Triangle3D> pq;
	
	public WireframeRenderer(){
		
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
			
			if (!obj.isVisible()){
				continue;
			}
			
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
	
}
