package game;

import java.awt.Color;
import engine.Camera;
import engine.Scene;
import engine.math.Point3D;
import engine.math.Vector3D;
import game.objects.Light;
import game.objects.PoolBall;
import game.objects.PoolTable;

public class PoolScene extends Scene {

	@Override
	public void buildShapes() {
		
		objects.add( new PoolBall(0, 5, new Point3D(100, 0, 0)) );
		
		double triangleX = 0;
		
		objects.add( new PoolBall(1, 5, new Point3D(triangleX-72, 0, 0)) );
		
		objects.add( new PoolBall(2, 5, new Point3D(triangleX-80, 6, 0)) );
		objects.add( new PoolBall(3, 5, new Point3D(triangleX-80, -6, 0)) );
		
		objects.add( new PoolBall(2, 5, new Point3D(triangleX-88, 10, 0)) );
		objects.add( new PoolBall(3, 5, new Point3D(triangleX-88, 0, 0)) );
		objects.add( new PoolBall(2, 5, new Point3D(triangleX-88, -10, 0)) );
		
		objects.add( new PoolBall(2, 5, new Point3D(triangleX-96, 16, 0)) );
		objects.add( new PoolBall(3, 5, new Point3D(triangleX-96, 6, 0)) );
		objects.add( new PoolBall(2, 5, new Point3D(triangleX-96, -6, 0)) );
		objects.add( new PoolBall(2, 5, new Point3D(triangleX-96, -16, 0)) );
		
		objects.add( new PoolBall(2, 5, new Point3D(triangleX-104, 20, 0)) );
		objects.add( new PoolBall(3, 5, new Point3D(triangleX-104, 10, 0)) );
		objects.add( new PoolBall(2, 5, new Point3D(triangleX-104, 0, 0)) );
		objects.add( new PoolBall(2, 5, new Point3D(triangleX-104, -10, 0)) );
		objects.add( new PoolBall(2, 5, new Point3D(triangleX-104, -20, 0)) );
		
		/*
		for(int i = 0; i < 7; i++){
			PoolBall ball = new PoolBall(8, 5, new Point3D(i*20-120, 0, 0));
			objects.add(ball);
		}
		*/
		
		PoolTable table = new PoolTable(new Point3D(0,0,-10), 142, 284, 10, 20, 0.975);
		objects.add(table);
		
		Light light = new Light(new Point3D(0,10000,0), 100, Color.white);
		lights.add(light);
	}

	@Override
	public void setCamera() {
		
		camera = new Camera(new Point3D(0,0,350),
							new Point3D(0,0,0),
							new Vector3D(0,1,0), 70);
		
		camera.setMovementSpeed(30);
		camera.setRotationSpeed(30);
		
	}
	
}
