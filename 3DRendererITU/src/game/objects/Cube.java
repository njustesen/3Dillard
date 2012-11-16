package game.objects;

import engine.math.Point3D;
import engine.shapes.Shape3D;

public class Cube extends Shape3D {

	double height;
	double width;
	double depth;
	
	public Cube(Point3D anchor, double height, double width, double depth) {
		super(anchor);
		this.height = height;
		this.width = width;
		this.depth = depth;
		build();
	}

	@Override
	public void build() {
		// TODO Auto-generated method stub

	}
	
}
