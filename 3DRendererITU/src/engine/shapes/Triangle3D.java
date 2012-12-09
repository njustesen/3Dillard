package engine.shapes;
import java.awt.Color;

import engine.math.Point3D;
import engine.math.Vector3D;



public class Triangle3D implements Comparable<Triangle3D>{

	private Point3D a, b, c;
	private Point3D centerPoint;
	private Color color;
	
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
	
	public Color getColor(){
		return this.color;
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

	@Override
	public int compareTo(Triangle3D t) {
		
		double thisZMin = Math.min(this.getPointC().getZ(), 
				Math.min(this.getPointA().getZ(), this.getPointB().getZ()));
		
		//double thisZMax = Math.max(this.getPointC().getZ(), 
		//		Math.max(this.getPointA().getZ(), this.getPointB().getZ()));
		//double thisCenterZ = this.getCenter().getZ();
		double otherZMin = Math.min(this.getPointC().getZ(), 
				Math.min(t.getPointA().getZ(), t.getPointB().getZ()));
		//double otherZMax = Math.max(this.getPointC().getZ(), 
		//		Math.max(t.getPointA().getZ(), t.getPointB().getZ()));
		//double otherCenterZ = t.getCenter().getZ();
		
		if(thisZMin > otherZMin){
			return 1;
		}
		else if(thisZMin < otherZMin){
			return -1;
		}
		return 0;
	}
	
	public void setColor(Color newColor){
		this.color = newColor;
	}
	
	public void setColor(int r, int g, int b){
		this.color = new Color(r, g, b);
	}
	
	public void setColor(int c){
		//System.out.println("c = "+c);
		if(c <= 255 && c > 0)
			this.color = new Color(c, c, c);
		else if(c <= 0){
			this.color = new Color(0, 0, 0);
		}else this.color = new Color(255, 255, 255);
	}
	
	public Vector3D getSurfaceNormal(){
		Vector3D v1 = new Vector3D(this.getPointA());
		Vector3D v2 = new Vector3D(this.getPointB());
		Vector3D v3 = new Vector3D(this.getPointC());
		
		Vector3D p1 = v2.subtract(v1);
		Vector3D p2 = v3.subtract(v1);
		
		return p1.getCrossProduct(p2);
	}
}
