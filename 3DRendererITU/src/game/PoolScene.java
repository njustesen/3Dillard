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
		
		PoolTable table = new PoolTable(Point3D.Zero, 100, 100, 100);
		objects.add(table);
		
		for(int i = 0; i < 8; i++){
			PoolBall ball = new PoolBall(8, new Point3D(i*35, 0, i*35));
			objects.add(ball);
		}
	}

	@Override
	public void setCamera() {
		
		camera = new Camera(new Point3D(400,0,400),
							new Point3D(0,0,0),
							new Vector3D(0,1,0), 70);
		
		camera.setMovementSpeed(10);
		camera.setRotationSpeed(10);
		
	}
	
}
