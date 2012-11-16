
public class Point3 {

	private double x,y,z,w;
	
	public Point3(double x, double y, double z){
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = 1;
	}
	
	public double getX(){
		return x;
	}
	
	public double getY(){
		return y;
	}
	
	public double getZ(){
		return z;
	}
	public double getW(){
		return w;
	}
	
	public void setX(double newX){
		x = newX;
	}
	
	public void setY(double newY){
		y = newY;
	}
	
	public void setZ(double newZ){
		z = newZ;
	}
	public void setW(double newW){
		w = newW;
	}
	public Vector3 toVector(){
		return new Vector3(x,y,z);
	}
	
	public Matrix toMatrix(){
		Matrix newMatrix = new Matrix(4,1);
		newMatrix.matrixData[0][0]=this.x;
		newMatrix.matrixData[1][0]=this.y;
		newMatrix.matrixData[2][0]=this.z;
		newMatrix.matrixData[3][0]=1;
		return newMatrix;
	}
	
}
