package engine.physics;

import java.util.ArrayList;
import engine.GameObject;
import engine.math.Vector3D;

public class PhysicsManager {

	private static final double friction = 0.95;

	public PhysicsManager(){
		
		
		
	}
	
	public void moveBalls(ArrayList<MovableBall> balls){
		
		for(MovableBall ball : balls){
			
			// FRICTION - Slow down velocity
			Vector3D vel = new Vector3D(
					ball.getVelocity().getX() * friction, 
					ball.getVelocity().getY() * friction, 
					0);
			
			ball.setVelocity(vel);
			
			// Move ball
			ball.move(ball.getVelocity());
			
		}
		
	}
	
}
