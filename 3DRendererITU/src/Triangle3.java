
public class Triangle3 {

	private Point3 a, b, c;
	private Point3 centerPoint;
	
	public Triangle3(Point3 a, Point3 b, Point3 c) {
		this.centerPoint = new Point3((a.getX()+b.getX()+c.getX())/3,
									  (a.getY()+b.getY()+c.getY())/3,
									  (a.getZ()+b.getZ()+c.getZ())/3);
		
		this.a = a;
		this.b = b;
		this.c = c;
	}

	public Point3 getPointA(){
		return a;
	}
	
	public Point3 getPointB(){
		return b;
	}
	
	public Point3 getPointC(){
		return c;
	}
	
	public void setPointA(Point3 a){
		this.a = a;
	}
	
	public void setPointB(Point3 b){
		this.b = b;
	}
	
	public void setPointC(Point3 c){
		this.c = c;
	}
	
	public void setPoints(Point3 a, Point3 b, Point3 c){
		this.a = a;
		this.b = b;
		this.c = c;
	}
	
	public Point3 getCenter(){
		return centerPoint;
	}
	/*
	public ArrayList<Point3> getVertices(){
		//return vertices;
	}
*/
}
