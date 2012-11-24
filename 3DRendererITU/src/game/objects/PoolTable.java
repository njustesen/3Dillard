package game.objects;

import engine.GameObject;
import engine.math.Point3D;

public class PoolTable extends GameObject {
	
	double width, height, depth, bumperWidth;
	double friction;
	Bumper bumperTop;
	Bumper bumperLeft;
	Bumper bumperRight;
	Bumper bumperBottom;

	public PoolTable(Point3D position, double height, double width, double depth, double bumpersWidth, double friction) {
		super(position);
		this.width = width; 
		this.height = height;
		this.depth = depth;
		this.bumperWidth = bumpersWidth;
		this.friction = friction;
		build();
	}

	@Override
	public void build() {
		
		// SURFACE
		addShape(new Bumper(Point3D.Zero, height, width, depth));
		
		// TOP
		Bumper top = new Bumper(new Point3D(0, height/2 + bumperWidth/2, depth/2), bumperWidth, width, depth*2);
		addShape(top);
		setBumperTop(top);
		
		// BOTTOM
		Bumper bottom = new Bumper(new Point3D(0, -height/2 - bumperWidth/2, depth/2), bumperWidth, width, depth*2);
		addShape(bottom);
		setBumperBottom(bottom);
		
		// LEFT
		Bumper left = new Bumper(new Point3D(-width/2 - bumperWidth/2, 0, depth/2), height + bumperWidth *2, bumperWidth, depth*2);
		addShape(left);
		setBumperLeft(left);
		
		// RIGHT
		Bumper right = new Bumper(new Point3D(width/2 + bumperWidth/2, 0, depth/2), height + bumperWidth *2, bumperWidth, depth*2);
		addShape(right);
		setBumperRight(right);
		
	}

	public double getFriction() {
		return friction;
	}

	public double getWidth() {
		return width;
	}

	public void setWidth(double width) {
		this.width = width;
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	public double getDepth() {
		return depth;
	}

	public void setDepth(double depth) {
		this.depth = depth;
	}

	public double getBumperWidth() {
		return bumperWidth;
	}

	public void setBumperWidth(double bumperWidth) {
		this.bumperWidth = bumperWidth;
	}

	public void setFriction(double friction) {
		this.friction = friction;
	}

	public Bumper getBumperTop() {
		return bumperTop;
	}

	public void setBumperTop(Bumper bumperTop) {
		this.bumperTop = bumperTop;
	}

	public Bumper getBumperLeft() {
		return bumperLeft;
	}

	public void setBumperLeft(Bumper bumperLeft) {
		this.bumperLeft = bumperLeft;
	}

	public Bumper getBumperRight() {
		return bumperRight;
	}

	public void setBumperRight(Bumper bumperRight) {
		this.bumperRight = bumperRight;
	}

	public Bumper getBumperBottom() {
		return bumperBottom;
	}

	public void setBumperBottom(Bumper bumperBottom) {
		this.bumperBottom = bumperBottom;
	}
	
}
