import java.util.ArrayList;


public class Pyramid {

	Point3 center;
	ArrayList <Triangle> triangles = new ArrayList <Triangle>();
	
	public Pyramid(Point3 center){
		this.center = center;
		
		Triangle triangle1 = new Triangle(new Point3(-50+center.getX(),0+center.getY(),-50+center.getZ()),
											new Point3(50+center.getX(),0+center.getY(),-50+center.getZ()),
												new Point3(0+center.getX(),100+center.getY(),0+center.getZ()));
		triangles.add(triangle1);
		
		Triangle triangle2 = new Triangle(new Point3(-50+center.getX(),0+center.getY(),-50+center.getZ()),
											new Point3(-50+center.getX(),0+center.getY(),50+center.getZ()),
												new Point3(0+center.getX(),100+center.getY(),0+center.getZ()));
		triangles.add(triangle2);
		
		Triangle triangle3 = new Triangle(new Point3(-50+center.getX(),0+center.getY(),50+center.getZ()),
											new Point3(50+center.getX(),0+center.getY(),50+center.getZ()),
												new Point3(0+center.getX(),100+center.getY(),0+center.getZ()));
		triangles.add(triangle3);
		
		Triangle triangle4 = new Triangle(new Point3(50+center.getX(),0+center.getY(),50+center.getZ()),
											new Point3(50+center.getX(),0+center.getY(),-50+center.getZ()),
												new Point3(0+center.getX(),100+center.getY(),0+center.getZ()));
		triangles.add(triangle4);
	}
}
