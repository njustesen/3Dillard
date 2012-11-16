import java.util.ArrayList;

public abstract class Shape3D {

	Point3 anchor;
	int size;
	ArrayList <Triangle3> triangles = new ArrayList <Triangle3>();
	
	public Shape3D(Point3 anchor, int size){
		this.anchor = anchor;
		this.size = size;
		build();
	}
	
	public abstract void build();

	public Point3 getCenter() {
		return anchor;
	}

	public void setCenter(Point3 anchor) {
		this.anchor = anchor;
	}

	public ArrayList<Triangle3> getTriangles() {
		return triangles;
	}

	public void setTriangles(ArrayList<Triangle3> triangles) {
		this.triangles = triangles;
	}
	
}
