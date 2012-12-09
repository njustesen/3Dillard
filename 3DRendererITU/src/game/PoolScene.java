package game;

import java.awt.Color;
import engine.Camera;
import engine.Scene;
import engine.math.Point3D;
import engine.math.Vector3D;
import game.objects.Light;
import game.objects.PoolBall;
import game.objects.PoolCamera;
import game.objects.PoolTable;

public class PoolScene extends Scene {
	
	PoolBall cueBall;

	@Override
	public void buildShapes() {
		
		double triangleX = -80;
		double diameter = 5.4;
		double offset = 0.95;
		double depth = 0;
		
		cueBall = new PoolBall(0, diameter/2, new Point3D(100, 0, depth));
		
		objects.add( cueBall );
				
		objects.add( new PoolBall(1, diameter/2, new Point3D(triangleX-diameter*offset, 0, depth)) );
		
		objects.add( new PoolBall(2, diameter/2, new Point3D(triangleX-diameter*2*offset, diameter/2, depth)) );
		objects.add( new PoolBall(3, diameter/2, new Point3D(triangleX-diameter*2*offset, -diameter/2, depth)) );
		
		objects.add( new PoolBall(2, diameter/2, new Point3D(triangleX-diameter*3*offset, diameter, depth)) );
		objects.add( new PoolBall(3, diameter/2, new Point3D(triangleX-diameter*3*offset, 0, depth)) );
		objects.add( new PoolBall(2, diameter/2, new Point3D(triangleX-diameter*3*offset, -diameter, depth)) );
		
		objects.add( new PoolBall(2, diameter/2, new Point3D(triangleX-diameter*4*offset, diameter + diameter/2, depth)) );
		objects.add( new PoolBall(3, diameter/2, new Point3D(triangleX-diameter*4*offset, diameter/2, depth)) );
		objects.add( new PoolBall(2, diameter/2, new Point3D(triangleX-diameter*4*offset, -diameter/2, depth)) );
		objects.add( new PoolBall(2, diameter/2, new Point3D(triangleX-diameter*4*offset, -(diameter + diameter/2), depth)) );
		
		objects.add( new PoolBall(2, diameter/2, new Point3D(triangleX-diameter*5*offset, diameter*2, depth)) );
		objects.add( new PoolBall(3, diameter/2, new Point3D(triangleX-diameter*5*offset, diameter, depth)) );
		objects.add( new PoolBall(2, diameter/2, new Point3D(triangleX-diameter*5*offset, 0, depth)) );
		objects.add( new PoolBall(2, diameter/2, new Point3D(triangleX-diameter*5*offset, -diameter, depth)) );
		objects.add( new PoolBall(2, diameter/2, new Point3D(triangleX-diameter*5*offset, -diameter*2, depth)) );
		
		PoolTable table = new PoolTable(new Point3D(0,0,-diameter*1.5), 140, 270, 10, 20, diameter*1.8, 0.01);
		objects.add(table);
		
		Light light = new Light(new Point3D(0,10000,0), 100, Color.white);
		lights.add(light);
	}

	@Override
	public void setCamera() {
		
		camera = new PoolCamera(new Point3D(400,0,200),
							new Point3D(0,0,0),
							new Vector3D(0,0,1), 70);
		
		camera.setMovementSpeed(30);
		camera.setRotationSpeed(30);
		
	}

	public PoolBall getCueBall() {
		return cueBall;
	}

}
