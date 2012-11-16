import java.util.ArrayList;


public abstract class Scene {

	protected ArrayList <Shape3D> shapes;
	protected Camera camera;
	
	public Scene(){
		shapes = new ArrayList<Shape3D>();
		buildShapes();
		setCamera();
	}
	
	public abstract void buildShapes();
	public abstract void setCamera();

	public ArrayList<Shape3D> getShapes() {
		return shapes;
	}

	public Camera getCamera() {
		return camera;
	}

	public void setCamera(Camera camera) {
		this.camera = camera;
	}

	public void setShapes(ArrayList<Shape3D> shapes) {
		this.shapes = shapes;
	}
	
}
