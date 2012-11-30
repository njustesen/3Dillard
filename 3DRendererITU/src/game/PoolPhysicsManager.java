package game;

import java.util.ArrayList;
import engine.GameObject;
import engine.Scene;
import engine.math.Vector2D;
import engine.math.Vector3D;
import engine.physics.PhysicsManager;
import game.objects.Bumper;
import game.objects.PoolBall;
import game.objects.PoolTable;

public class PoolPhysicsManager extends PhysicsManager {

	private ArrayList<PoolBall> balls;
	private PoolTable table;
	
	public PoolPhysicsManager(){
		
		balls = new ArrayList<PoolBall>();
		
	}

	@Override
	public void move() {
		
		// Move balls
		for(PoolBall ball : balls){
			
			// FRICTION - Slow down velocity
			Vector3D vel = new Vector3D(
					ball.getVelocity().getX() * table.getFriction(), 
					ball.getVelocity().getY() * table.getFriction(), 
					0);
			
			ball.setVelocity(vel);
			
			// Move ball
			ball.move(ball.getVelocity());
			
			// Test collision with bumpers
			checkBumperCollisions(ball);
			
			// Test collision with balls
			for(int i = balls.indexOf(ball); i < balls.size(); i++){
				
				if (checkBallCollision(ball, balls.get(i))){
					
					ballCollision(ball, balls.get(i));
					
				}
				
			}
			
		}
		
	}

	private boolean checkBallCollision(PoolBall ball, PoolBall other) {
	
		double distanceX = Math.abs(ball.getPosition().getX() - other.getPosition().getX());
		double distanceY = Math.abs(ball.getPosition().getY() - other.getPosition().getY());
		
		// X
		if (distanceX < ball.getRadius() + other.getRadius() && distanceY < ball.getRadius() + other.getRadius()){
			if (distanceX > ball.getRadius() - other.getRadius() && distanceY > ball.getRadius() - other.getRadius()){
				return true;
			}
		}
		
		return false;
	}
	
	private void ballCollision(PoolBall ball, PoolBall other) {
		
		Vector3D vectorBetween3D = ball.getPosition().subtract( other.getPosition() ).toVector();
		Vector3D unitBetween3D = vectorBetween3D.getUnitVector();
		
		Vector2D unitBetween2D = new Vector2D(unitBetween3D);
		Vector2D ballVelocity2D = new Vector2D(ball.getVelocity());
		Vector2D otherVelocity2D = new Vector2D(other.getVelocity());
		
		// Calculate for ball
		if (!ballVelocity2D.equals(Vector2D.Zero)){
		
			// The angle between the two balls and the ball velocity
			double collisionAngle = Vector2D.angleBetween(ballVelocity2D, unitBetween2D);
			
			if(collisionAngle > 90){
				
				double impactOther = (collisionAngle - 90) / 90;
				double impactBall = 1 - impactOther;
				double forceOther = ballVelocity2D.getVectorLength() * impactOther;
				double forceBall = ballVelocity2D.getVectorLength() * impactBall;

				Vector2D newVelOther2D = unitBetween2D.multiply(forceOther).multiply( -1 );
				Vector3D newVelOther3D = new Vector3D(newVelOther2D);
				
				other.setVelocity(newVelOther3D);
				
				Vector2D newVelBall2D = ballVelocity2D.subtract(newVelOther2D);
				Vector3D newVelBall3D = new Vector3D(newVelBall2D);
				
				ball.setVelocity(newVelBall3D);
				
				
			}
			
		}
		
		// Calculate for other
		/*
		if (!otherVelocity2D.equals(Vector2D.Zero)){
		
			double angleBetween = Vector2D.angleBetween(otherVelocity2D, unitBetween2D);
			
			if(angleBetween > 90){
				
				double impact = (angleBetween - 90) / 90;
				double force = otherVelocity2D.getVectorLength() * impact;
				
				Vector2D newVel2D = unitBetween2D.multiply( force );
				Vector3D newVel3D = new Vector3D(newVel2D).multiply(-1);
				
				ball.setVelocity(newVel3D);
				
			}
			
		}
		*/
		
	}

	private void checkBumperCollisions(PoolBall ball) {
		
		if (checkBumperCollision(table.getBumperTop(), ball)) 
			bumperCollision(table.getBumperTop(), ball);
		if (checkBumperCollision(table.getBumperRight(), ball))
			bumperCollision(table.getBumperRight(), ball);
		if (checkBumperCollision(table.getBumperLeft(), ball))
			bumperCollision(table.getBumperLeft(), ball);
		if (checkBumperCollision(table.getBumperBottom(), ball))
			bumperCollision(table.getBumperBottom(), ball);
		
	}

	private boolean checkBumperCollision(Bumper bumper, PoolBall ball) {
		
		boolean collision = true;

		// Test
		double distanceX = Math.abs( ball.getPosition().getX() - bumper.getAnchor().getX() );
		double distanceY = Math.abs( ball.getPosition().getY() - bumper.getAnchor().getY() );
		
		if (distanceX > bumper.getWidth() / 2 + ball.getRadius()){
			collision = false;
		}
		if (distanceY > bumper.getHeight() / 2 + ball.getRadius()){
			collision = false;
		}
		
		return collision;
		
	}
	
	private void bumperCollision(Bumper bumper, PoolBall ball) {
		
		int x = 1;
		int y = 1;
		
		// If bumper is right or left
		if (bumper.getAnchor().getX() != 0){
			x = -1;
			if (bumper.getAnchor().getX() > 0)
				ball.getPosition().setX(bumper.getAnchor().getX() - (bumper.getWidth()/2 + ball.getRadius()));
			if (bumper.getAnchor().getX() < 0)
				ball.getPosition().setX(bumper.getAnchor().getX() + (bumper.getWidth()/2 + ball.getRadius()));
		}
		
		// If bumper is right or left
		if (bumper.getAnchor().getY() != 0){
			y = -1;
			if (bumper.getAnchor().getY() > 0)
				ball.getPosition().setY(bumper.getAnchor().getY() - (bumper.getHeight()/2 + ball.getRadius()));
			if (bumper.getAnchor().getY() < 0)
				ball.getPosition().setY(bumper.getAnchor().getY() + (bumper.getHeight()/2 + ball.getRadius()));
		}
		
		// Change velocity
		Vector3D newVelocity = ball.getVelocity();
		newVelocity.setX( newVelocity.getX() * x );
		newVelocity.setY( newVelocity.getY() * y );
		ball.setVelocity(newVelocity);
		
	}

	public ArrayList<PoolBall> getBalls() {
		return balls;
	}

	public void setBalls(ArrayList<PoolBall> balls) {
		this.balls = balls;
	}
	
	public void addBall(PoolBall ball){
		this.balls.add(ball);
		
	}

	public PoolTable getTable() {
		return table;
	}

	public void setTable(PoolTable table) {
		this.table = table;
	}

	public void setupFromScene(Scene scene) {
		
		for(GameObject obj : scene.getObjects()){
			
			if (obj instanceof PoolBall){
				
				addBall((PoolBall)obj);
				
			} else if (obj instanceof PoolTable){
				
				setTable((PoolTable)obj);
				
			}
		}
		
	}
	
}
