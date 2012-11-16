/**Vector class representing a vector (a direction) in 3d space*/
public class Vector3 {

	private double x, y, z;
	
	public Vector3(double x, double y, double z){
		this.x = x;
		this.y = y;
		this.z = z;
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
	
	/**returns the length of the vector. |V| = sqrt(x^2 + y^2 + z^2).*/
	public double getVectorLength(){
		return Math.sqrt((x*x)+(y*y)+(z*z));
	}
	
	/**Adding this vector with the input vector.*/
	public Vector3 vectorAddition(Vector3 v){
		Vector3 newVector = new Vector3(x+v.x, y+v.y, z+v.z);
		return newVector;
	}
	
	/**Subtracting the input vector from this vector.*/
	public Vector3 vectorSubtraction(Vector3 v){
		Vector3 newVector = new Vector3(x-v.x, y-v.y, z-v.z);
		return newVector;	
	}
	
	/**Scaling a vector.*/
	public Vector3 vectorScaling(double scalar){
		Vector3 newVector = new Vector3(x*scalar, y*scalar, z*scalar);
		return newVector;	
	}
	
	/**Returns the dot-product of this and a given vector.*/
	public double getDotProduct(Vector3 v){
		return x*v.x + y*v.y + z*v.z;
	}
	
	/**Returns the cross-product of this and a given vector.*/
	public Vector3 getCrossProduct(Vector3 v) {
	    return new Vector3(y * v.z - z * v.y,
	                       z * v.x - x * v.z,
	                       x * v.y - y * v.x);
	  }

	
	/**returns a vector with a length of 1 unit.*/
	public Vector3 getUnitVector(){
		Vector3 newVector = new Vector3(x/getVectorLength(), y/getVectorLength(), z/getVectorLength());
		return newVector;
	}
	
	public Matrix toMatrix(){
		Matrix newMatrix = new Matrix(3,1);
		newMatrix.matrixData[0][0]=this.x;
		newMatrix.matrixData[0][1]=this.y;
		newMatrix.matrixData[0][2]=this.z;
		return newMatrix;
	}
	
	public Point3 toPoint(){
		Point3 p = new Point3(x,y,z);
		return p;	
	}
	
	
}
