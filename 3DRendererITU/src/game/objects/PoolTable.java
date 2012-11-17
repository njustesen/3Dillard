package game.objects;

import engine.GameObject;
import engine.math.Point3D;

public class PoolTable extends GameObject {
	
	double width, height, depth, bumperWidth;

	public PoolTable(Point3D position, double height, double width, double depth, double bumpersWidth) {
		super(position);
		this.width = width; 
		this.height = height;
		this.depth = depth;
		this.bumperWidth = bumpersWidth;
		build();
	}

	@Override
	public void build() {
		
		addShape(new Cube(Point3D.Zero, height, width, depth));
		
		// TOP
		addShape(new Cube(new Point3D(0, height/2 + bumperWidth/2, depth/2), bumperWidth, width, depth*2));
		
		// BOTTOM
		addShape(new Cube(new Point3D(0, -height/2 - bumperWidth/2, depth/2), bumperWidth, width, depth*2));
		
		// LEFT
		addShape(new Cube(new Point3D(-width/2 - bumperWidth/2, 0, depth/2), height + bumperWidth *2, bumperWidth, depth*2));
		
		// RIGHT
		addShape(new Cube(new Point3D(width/2 + bumperWidth/2, 0, depth/2), height + bumperWidth *2, bumperWidth, depth*2));
		
		
	}

}
