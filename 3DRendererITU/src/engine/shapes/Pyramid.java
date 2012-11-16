package engine.shapes;

import engine.math.Point3D;
import engine.math.Triangle3D;


public class Pyramid extends Shape3D{
	
	public Pyramid(Point3D anchor, int size){
		super(anchor, size);
		
	}

	@Override
	public void build() {
		
		Triangle3D triangle1 = new Triangle3D(new Point3D(-size/2+anchor.getX(),0+anchor.getY(),-size/2+anchor.getZ()),
				new Point3D(size/2+anchor.getX(),0+anchor.getY(),-size/2+anchor.getZ()),
				new Point3D(0+anchor.getX(),size+anchor.getY(),0+anchor.getZ()));
		
		triangles.add(triangle1);
		
		Triangle3D triangle2 = new Triangle3D(new Point3D(-size/2+anchor.getX(),0+anchor.getY(),-size/2+anchor.getZ()),
						new Point3D(-size/2+anchor.getX(),0+anchor.getY(),size/2+anchor.getZ()),
						new Point3D(0+anchor.getX(),size+anchor.getY(),0+anchor.getZ()));
		triangles.add(triangle2);
		
		Triangle3D triangle3 = new Triangle3D(new Point3D(-size/2+anchor.getX(),0+anchor.getY(),size/2+anchor.getZ()),
						new Point3D(size/2+anchor.getX(),0+anchor.getY(),size/2+anchor.getZ()),
						new Point3D(0+anchor.getX(),size+anchor.getY(),0+anchor.getZ()));
		triangles.add(triangle3);
		
		Triangle3D triangle4 = new Triangle3D(new Point3D(size/2+anchor.getX(),0+anchor.getY(),size/2+anchor.getZ()),
						new Point3D(size/2+anchor.getX(),0+anchor.getY(),-size/2+anchor.getZ()),
						new Point3D(0+anchor.getX(),size+anchor.getY(),0+anchor.getZ()));
		triangles.add(triangle4);
		
	}
	
}
