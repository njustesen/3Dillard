package engine;

import java.util.ArrayList;

import engine.math.Point3D;
import engine.shapes.Shape3D;

public abstract class GameObject {

	protected ArrayList<Shape3D> shapes;
	protected double size;
	protected Point3D position;
	
	public GameObject(double size, Point3D position){
		this.shapes = new ArrayList<Shape3D>();
		this.size = size;
		this.position = position;
	}
	
	public abstract void build();

	public ArrayList<Shape3D> getShapes() {
		return shapes;
	}

	public void setShape(ArrayList<Shape3D> shapes) {
		this.shapes = shapes;
	}
	
	public void addShape(Shape3D shape){
		shapes.add(shape);
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
