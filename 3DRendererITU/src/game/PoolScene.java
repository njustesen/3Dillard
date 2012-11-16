package game;

import engine.Camera;
import engine.Scene;
import engine.math.Point3D;
import engine.math.Vector3D;
import game.objects.PoolBall;

public class PoolScene extends Scene {

	@Override
	public void buildShapes() {
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
		
		camera.setMovementSpeed(3);
		camera.setRotationSpeed(3);
		
	}
	
}
