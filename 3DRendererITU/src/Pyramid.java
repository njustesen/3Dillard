
public class Pyramid extends Shape3D{
	
	public Pyramid(Point3 anchor, int size){
		super(anchor, size);
		
	}

	@Override
	public void build() {
		
		Triangle3 triangle1 = new Triangle3(new Point3(-size/2+anchor.getX(),0+anchor.getY(),-size/2+anchor.getZ()),
				new Point3(size/2+anchor.getX(),0+anchor.getY(),-size/2+anchor.getZ()),
				new Point3(0+anchor.getX(),size+anchor.getY(),0+anchor.getZ()));
		
		triangles.add(triangle1);
		
		Triangle3 triangle2 = new Triangle3(new Point3(-size/2+anchor.getX(),0+anchor.getY(),-size/2+anchor.getZ()),
						new Point3(-size/2+anchor.getX(),0+anchor.getY(),size/2+anchor.getZ()),
						new Point3(0+anchor.getX(),size+anchor.getY(),0+anchor.getZ()));
		triangles.add(triangle2);
		
		Triangle3 triangle3 = new Triangle3(new Point3(-size/2+anchor.getX(),0+anchor.getY(),size/2+anchor.getZ()),
						new Point3(size/2+anchor.getX(),0+anchor.getY(),size/2+anchor.getZ()),
						new Point3(0+anchor.getX(),size+anchor.getY(),0+anchor.getZ()));
		triangles.add(triangle3);
		
		Triangle3 triangle4 = new Triangle3(new Point3(size/2+anchor.getX(),0+anchor.getY(),size/2+anchor.getZ()),
						new Point3(size/2+anchor.getX(),0+anchor.getY(),-size/2+anchor.getZ()),
						new Point3(0+anchor.getX(),size+anchor.getY(),0+anchor.getZ()));
		triangles.add(triangle4);
		
	}
	
}
