package game.objects;

import engine.GameObject;
import engine.math.Point3D;
import engine.math.Vector3D;
import engine.physics.MovableBall;
import engine.shapes.Sphere;

public class PoolBall extends GameObject implements MovableBall {

	private int number;
	private double radius;
	private Vector3D velocity;
	
	public PoolBall(int number, double radius, Point3D position){
		super(position);
		this.number = number;
		this.radius = radius;
		this.velocity = Vector3D.Zero;
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

	public double getRadius() {
		return radius;
	}

	public void setRadius(double radius) {
		this.radius = radius;
	}

	@Override
	public Vector3D getVelocity() {
		return velocity;
	}

	@Override
	public void setVelocity(Vector3D velocity) {
		this.velocity = velocity;
	}

	@Override
	public void move(Vector3D move) {
		position.setX(position.getX() + move.getX());
		position.setY(position.getY() + move.getY());
		position.setZ(position.getZ() + move.getZ());
	}

	@Override
	public void addVelocity(Vector3D amount) {
		velocity.setX(velocity.getX() + amount.getX());
		velocity.setY(velocity.getY() + amount.getY());
		velocity.setZ(velocity.getZ() + amount.getZ());
	}
	
}
