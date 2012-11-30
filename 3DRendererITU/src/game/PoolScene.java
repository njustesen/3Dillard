package game;

import engine.Camera;
import engine.Scene;
import engine.math.Point3D;
import engine.math.Vector3D;
import game.objects.PoolBall;
import game.objects.PoolTable;

public class PoolScene extends Scene {

	@Override
	public void buildShapes() {

		for(int i = 0; i < 16; i++){
			PoolBall ball = new PoolBall(8, 5, new Point3D(i*12-120, 0, 0));
			objects.add(ball);
		}
		
		PoolTable table = new PoolTable(new Point3D(0,0,-10), 142, 284, 10, 20, 0.97);
		objects.add(table);
	}

	@Override
	public void setCamera() {
		
		camera = new Camera(new Point3D(0,0,400),
							new Point3D(0,0,0),
							new Vector3D(0,1,0), 70);
		
		camera.setMovementSpeed(30);
		camera.setRotationSpeed(30);
		
	}
	
}
