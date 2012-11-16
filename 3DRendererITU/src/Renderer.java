import java.util.ArrayList;


public class Renderer {
	
	private ArrayList<Triangle2> lastRendering;
	
	public Renderer(){
		
		lastRendering = new ArrayList<Triangle2>();
		
	}

	/**
	 * Renders a 3D scene.
	 * The rendered triangles are stored locally.
	 * @param scene
	 * @param screen
	 */
	public void render(Scene scene, Screen screen){
		
		ArrayList<Triangle2> newRendering = new ArrayList<Triangle2>();
		
		//SETTING THE CAMERA LOCATION
		Matrix camTransMatrix = new Matrix(new double[][] {{1,0,0,-scene.getCamera().getX()},
				  							   {0,1,0,-scene.getCamera().getY()},
				  							   {0,0,1,-scene.getCamera().getZ()},
				  							   {0,0,0,1}});
		
		//POINTING AND ORIENTING THE CAMERA
		Vector3 povZ = new Vector3( scene.getCamera().getLookPointX()-scene.getCamera().getX(),
									scene.getCamera().getLookPointY()-scene.getCamera().getY(),
									scene.getCamera().getLookPointZ()-scene.getCamera().getZ()).getUnitVector();
		
		Vector3 newX = scene.getCamera().getUpVector().getCrossProduct(povZ).getUnitVector();
		Vector3 newY = povZ.getCrossProduct(newX);
		
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
		
		for(Shape3D shape: scene.getShapes()){
			for(Triangle3 t: shape.triangles){
					
				Point2 a = renderVertice(t.getPointA(), screen, projectionMatrix, camLookMatrix, camTransMatrix);
				Point2 b = renderVertice(t.getPointB(), screen, projectionMatrix, camLookMatrix, camTransMatrix);
				Point2 c = renderVertice(t.getPointC(), screen, projectionMatrix, camLookMatrix, camTransMatrix);
				
				// Remove outside screen
				if (!(screen.isOutsideScreen(a) && screen.isOutsideScreen(b) && screen.isOutsideScreen(c))){
					
					newRendering.add(new Triangle2(a,b,c)); 
					
				}
			}	
		}		
		
		lastRendering = newRendering;
	}
	
	/**
	 * Renders a vertice.
	 * @param p
	 * @param screen
	 * @param projectionMatrix
	 * @param cameraLookMatrix
	 * @param cameraTransMatrix
	 * @return
	 */
	private Point2 renderVertice(	Point3 p, 
									Screen screen, 
									Matrix projectionMatrix, 
									Matrix cameraLookMatrix, 
									Matrix cameraTransMatrix){
		
		Matrix viewspaceMatrix = projectionMatrix.multiplication(cameraLookMatrix.multiplication(cameraTransMatrix));

		Point3 viewspacePoint = viewspaceMatrix.multiplication(p);
		
		viewspacePoint.setX(viewspacePoint.getX()/viewspacePoint.getW());
		viewspacePoint.setY(viewspacePoint.getY()/viewspacePoint.getW());
		viewspacePoint.setZ(viewspacePoint.getZ()/viewspacePoint.getW());

		int x = (int) (viewspacePoint.getX()*(screen.getWidth()/2)+(screen.getWidth()/2));
		int y = (int) (viewspacePoint.getY()*(screen.getHeight()/2)+(screen.getHeight()/2));
		
		return new Point2(x,y);
		
	}

	public ArrayList<Triangle2> getRenderedTriangles() {
		return lastRendering;
	}
	
}
