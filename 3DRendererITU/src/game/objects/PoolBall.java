package game.objects;

import engine.GameObject;
import engine.math.Point3D;
import engine.shapes.Sphere;

public class PoolBall extends GameObject {

	private int number;
	private double radius;
	
	public PoolBall(int number, double radius, Point3D position){
		super(position);
		this.number = number;
		this.radius = radius;
		build();
	}
	
	@Override
	public void build() {
		
		addShape(new Sphere(Point3D.Zero, radius));
		
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

}
