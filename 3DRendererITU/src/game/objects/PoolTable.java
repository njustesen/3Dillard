package game.objects;

import engine.GameObject;
import engine.math.Point3D;

public class PoolTable extends GameObject {

	public PoolTable(double size, Point3D position) {
		super(size, position);
		build();
	}

	@Override
	public void build() {
		
		addShape(new Cube(Point3D.Zero, 10, 10, 10));
		
	}

}
