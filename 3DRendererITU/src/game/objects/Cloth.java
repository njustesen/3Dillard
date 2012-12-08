package game.objects;

import engine.math.Point3D;
import engine.shapes.Cube;

public class Cloth extends Cube  {
	
	double friction;
	
	public Cloth(Point3D anchor, double height, double width, double depth, double friction) {
		super(anchor, height, width, depth);
		
		this.friction = friction;
		
	}

	public double getFriction() {
		return friction;
	}
	

}
