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
		
	}

}
