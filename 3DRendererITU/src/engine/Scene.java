package engine;

import java.util.ArrayList;

import engine.shapes.Shape3D;

public abstract class Scene {

	protected ArrayList <GameObject> objects;
	protected Camera camera;
	
	public Scene(){
		objects = new ArrayList<GameObject>();
		buildShapes();
		setCamera();
	}
	
	public abstract void buildShapes();
	public abstract void setCamera();

	public ArrayList<GameObject> getObjects() {
		return objects;
	}

	public Camera getCamera() {
		return camera;
	}

	public void setCamera(Camera camera) {
		this.camera = camera;
	}

	public void setObjects(ArrayList<GameObject> objects) {
		this.objects = objects;
	}
	
}
