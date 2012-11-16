package engine;

import engine.math.Point3D;
import engine.shapes.Shape3D;

public abstract class GameObject {

	protected Shape3D shape;
	protected double size;
	protected Point3D position;
	
	public GameObject(Shape3D shape, double size, Point3D position){
		this.shape = shape;
		this.size = size;
		this.position = position;
	}

	public Shape3D getShape() {
		return shape;
	}

	public void setShape(Shape3D shape) {
		this.shape = shape;
	}

	public double getSize() {
		return size;
	}

	public void setSize(double size) {
		this.size = size;
	}

	public Point3D getPosition() {
		return position;
	}

	public void setPosition(Point3D position) {
		this.position = position;
	}
	
}
