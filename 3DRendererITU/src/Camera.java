
public class Camera {

	private Point3 position;
	private Point3 lookPoint;
	private Vector3 up;
	private int angle;
	private int movementSpeed;
	private int rotationSpeed;
	
	public Camera(Point3 position, Point3 lookPoint, Vector3 up, int angle){
		this.position = position;
		this.lookPoint = lookPoint;
		this.up = up;
		this.angle = angle;
	}
	
	public double getX(){
		return position.getX();
	}
	
	public double getY(){
		return position.getY();
	}
	
	public double getZ(){
		return position.getZ();
	}
	
	public double getAngle(){
		return angle;
	}
	
	public void setX(double newX){
		this.position.setX(newX);
	}
	
	public void setY(double newY){
		this.position.setY(newY);
	}
	
	public void setZ(double newZ){
		this.position.setZ(newZ);
	}
	
	public void setLookpointX(double newX){
		this.lookPoint.setX(newX);
	}
	
	public void setLookpointY(double newY){
		this.lookPoint.setY(newY);
	}
	
	public void setLookpointZ(double newZ){
		this.lookPoint.setZ(newZ);
	}
	
	public double getLookPointX(){
		return lookPoint.getX();
	}
	
	public double getLookPointY(){
		return lookPoint.getY();
	}
	
	public double getLookPointZ(){
		return lookPoint.getZ();
	}
	
	public Point3 getLookPoint(){
		return lookPoint;
	}
	
	public Vector3 getUpVector(){
		return up;
	}
	
	public Point3 getPosition(){
		return position;
	}

	public int getMovementSpeed() {
		return movementSpeed;
	}

	public int getRotationSpeed() {
		return rotationSpeed;
	}

	public void setMovementSpeed(int movementSpeed) {
		this.movementSpeed = movementSpeed;
	}

	public void setRotationSpeed(int rotationSpeed) {
		this.rotationSpeed = rotationSpeed;
	}

	public void moveX(int i) {
		position.setX(position.getX() + i);
		
	}

	public void moveY(int i) {
		position.setY(position.getY() + i);
	}

	public void moveLookpointX(int i) {
		lookPoint.setX(lookPoint.getX() + i);
	}

	public void moveLookpointY(int i) {
		lookPoint.setY(lookPoint.getY() + i);
	}
}
