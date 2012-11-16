package engine.shapes;

import java.util.ArrayList;

import engine.math.Point3D;
import engine.math.Triangle3D;


public abstract class Shape3D {

	protected Point3D anchor;
	protected int size;
	protected ArrayList <Triangle3D> triangles = new ArrayList <Triangle3D>();
	
	public Shape3D(Point3D anchor, int size){
		this.anchor = anchor;
		this.size = size;
		build();
	}
	
	public abstract void build();

	public Point3D getAnchor() {
		return anchor;
	}

	public void setAnchor(Point3D anchor) {
		this.anchor = anchor;
	}

	public ArrayList<Triangle3D> getTriangles() {
		return triangles;
	}

	public void setTriangles(ArrayList<Triangle3D> triangles) {
		this.triangles = triangles;
	}
	
}
