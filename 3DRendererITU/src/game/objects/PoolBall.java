package game.objects;

import engine.GameObject;
import engine.math.Point3D;
import engine.shapes.Sphere;

public class PoolBall extends GameObject {

	private int number;
	
	public PoolBall(int number, Point3D position){
		super(number, position);
		build();
	}
	
	@Override
	public void build() {
		
		addShape(new Sphere(Point3D.Zero, 10));
		
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

}
