package game.objects;

import engine.math.Point3D;
import engine.shapes.Cube;

public class Cloth extends Cube  {
	
	double friction;
	double deacceleration;
	
	public Cloth(Point3D anchor, double height, double width, double depth, double deacceleration) {
		super(anchor, height, width, depth);
		
		this.deacceleration = deacceleration;
		
	}

	public double getFriction() {
		return friction;
	}

	public double getDeaccelerationCoefficient() {
		return deacceleration;
	}
	

}
