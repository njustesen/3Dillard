package engine.shapes;

import engine.math.Point3D;



public class Triangle3D {

	private Point3D a, b, c;
	private Point3D centerPoint;
	
	public Triangle3D(Point3D a, Point3D b, Point3D c) {
		this.centerPoint = new Point3D((a.getX()+b.getX()+c.getX())/3,
									  (a.getY()+b.getY()+c.getY())/3,
									  (a.getZ()+b.getZ()+c.getZ())/3);
		
		this.a = a;
		this.b = b;
		this.c = c;
	}

	public Point3D getPointA(){
		return a;
	}
	
	public Point3D getPointB(){
		return b;
	}
	
	public Point3D getPointC(){
		return c;
	}
	
	public void setPointA(Point3D a){
		this.a = a;
	}
	
	public void setPointB(Point3D b){
		this.b = b;
	}
	
	public void setPointC(Point3D c){
		this.c = c;
	}
	
	public void setPoints(Point3D a, Point3D b, Point3D c){
		this.a = a;
		this.b = b;
		this.c = c;
	}
	
	public Point3D getCenter(){
		return centerPoint;
	}
	/*
	public ArrayList<Point3D> getVertices(){
		//return vertices;
	}
*/
}
