package engine.objects;

import engine.GameObject;
import engine.math.Point3D;

public class BilliardTable extends GameObject {
	
	double width, height, depth, bumperWidth, pocketRadius;
	double friction;
	Bumper bumperTopA;
	Bumper bumperTopB;
	Bumper bumperLeft;
	Bumper bumperRight;
	Bumper bumperBottomA;
	Bumper bumperBottomB;
	Cloth cloth;

	public BilliardTable(Point3D position, double height, double width, double depth, double bumpersWidth, double pocketRadius, double friction) {
		super(position);
		this.width = width; 
		this.height = height;
		this.depth = depth;
		this.bumperWidth = bumpersWidth;
		this.friction = friction;
		this.pocketRadius = pocketRadius;
		build();
	}

	@Override
	public void build() {
		
		double pocketDiameter = pocketRadius * 2;
		
		// SURFACE
		cloth = new Cloth(Point3D.Zero, height, width, depth, friction);
		addShape(cloth);
		
		// TOP A
		bumperTopA = new Bumper(new Point3D(width / 4, height/2 + bumperWidth/2, depth/4), bumperWidth, width/2 - pocketDiameter, depth*1.5);
		bumperTopA.setxDir(0);
		bumperTopA.setyDir(1);
		addShape(bumperTopA);
		
		// TOP B
		bumperTopB = new Bumper(new Point3D(-width / 4, height/2 + bumperWidth/2, depth/4), bumperWidth, width/2 - pocketDiameter, depth*1.5);
		bumperTopB.setxDir(0);
		bumperTopB.setyDir(1);
		addShape(bumperTopB);
		
		// BOTTOM A
		bumperBottomA = new Bumper(new Point3D(width / 4, -height/2 - bumperWidth/2, depth/4), bumperWidth, width/2 - pocketDiameter, depth*1.5);
		bumperBottomA.setxDir(0);
		bumperBottomA.setyDir(-1);
		addShape(bumperBottomA);
		
		// BOTTOM B
		bumperBottomB = new Bumper(new Point3D(-width / 4, -height/2 - bumperWidth/2, depth/4), bumperWidth, width/2 - pocketDiameter, depth*1.5);
		bumperBottomB.setxDir(0);
		bumperBottomB.setyDir(-1);
		addShape(bumperBottomB);
		
		// LEFT
		bumperLeft = new Bumper(new Point3D(-width/2 - bumperWidth/2, 0, depth/4), height - pocketDiameter, bumperWidth, depth*1.5);
		bumperLeft.setxDir(-1);
		bumperLeft.setyDir(0);
		addShape(bumperLeft);
		
		// RIGHT
		bumperRight = new Bumper(new Point3D(width/2 + bumperWidth/2, 0, depth/4), height - pocketDiameter, bumperWidth, depth*1.5);
		bumperRight.setxDir(1);
		bumperRight.setyDir(0);
		addShape(bumperRight);
		
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

	public Bumper getBumperTopA() {
		return bumperTopA;
	}

	public void setBumperTopA(Bumper bumperTop) {
		this.bumperTopA = bumperTop;
	}
	
	public Bumper getBumperTopB() {
		return bumperTopB;
	}

	public void setBumperTopB(Bumper bumperTop) {
		this.bumperTopB = bumperTop;
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

	public Bumper getBumperBottomA() {
		return bumperBottomA;
	}

	public void setBumperBottomA(Bumper bumperBottom) {
		this.bumperBottomA = bumperBottom;
	}
	
	public Bumper getBumperBottomB() {
		return bumperBottomB;
	}

	public void setBumperBottomB(Bumper bumperBottom) {
		this.bumperBottomB = bumperBottom;
	}

	public Cloth getCloth() {
		return cloth;
	}
	
}
