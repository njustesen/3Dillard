package game.objects;

import engine.GameObject;
import engine.math.Point3D;

public class PoolTable extends GameObject {
	
	double width, height, depth;

	public PoolTable(Point3D position, double height, double width, double depth) {
		super(position);
		this.width = width; 
		this.height = height;
		this.depth = depth;
		
		build();
	}

	@Override
	public void build() {
		
		addShape(new Cube(Point3D.Zero, height, width, depth));
		
		double thickness = 10;
		
		// TOP
		addShape(new Cube(new Point3D(0, height/2 + thickness/2, depth/2), thickness, width, depth*2));
		
		// BOTTOM
		addShape(new Cube(new Point3D(0, -height/2 - thickness/2, depth/2), thickness, width, depth*2));
		
		// LEFT
		addShape(new Cube(new Point3D(-width/2 - thickness/2, 0, depth/2), height + thickness *2, thickness, depth*2));
		
		// RIGHT
		addShape(new Cube(new Point3D(width/2 + thickness/2, 0, depth/2), height + thickness *2, thickness, depth*2));
		
		
	}

}
