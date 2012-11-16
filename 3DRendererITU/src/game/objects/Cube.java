package game.objects;

import engine.math.Point3D;
import engine.shapes.Shape3D;
import engine.shapes.Triangle3D;

public class Cube extends Shape3D {

	double height;
	double width;
	double depth;
	
	public Cube(Point3D anchor, double height, double width, double depth) {
		super(anchor);
		this.height = height;
		this.width = width;
		this.depth = depth;
		build();
	}

	@Override
	public void build() {
		
		Point3D a = new Point3D(width/2, height/2, depth/2);
		Point3D b = new Point3D(width/2, height/2, -depth/2);
		Point3D c = new Point3D(width/2, -height/2, depth/2);
		Point3D d = new Point3D(width/2, -height/2, -depth/2);
		
		Point3D e = new Point3D(-width/2, height/2, depth/2);
		Point3D f = new Point3D(-width/2, height/2, -depth/2);
		Point3D g = new Point3D(-width/2, -height/2, depth/2);
		Point3D h = new Point3D(-width/2, -height/2, -depth/2);
		
		// TOP
		triangles.add(new Triangle3D(a,b,e));
		triangles.add(new Triangle3D(b,f,e));
		
		// RIGHT
		triangles.add(new Triangle3D(b,c,d));
		triangles.add(new Triangle3D(b,a,c));
		
		// LEFT
		triangles.add(new Triangle3D(f,g,e));
		triangles.add(new Triangle3D(f,h,g));
		
		// BOTTOM
		triangles.add(new Triangle3D(g,c,d));
		triangles.add(new Triangle3D(g,h,d));
		
		// FRONT
		triangles.add(new Triangle3D(c,a,e));
		triangles.add(new Triangle3D(c,e,g));
		
		// BACK
		triangles.add(new Triangle3D(f,b,h));
		triangles.add(new Triangle3D(d,b,h));

	}
	
}
