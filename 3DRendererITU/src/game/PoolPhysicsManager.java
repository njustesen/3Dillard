package game;

import java.util.ArrayList;
import engine.GameObject;
import engine.Scene;
import engine.math.Point3D;
import engine.math.Vector2D;
import engine.math.Vector3D;
import engine.physics.PhysicsManager;
import game.objects.Bumper;
import game.objects.PoolBall;
import game.objects.PoolTable;

public class PoolPhysicsManager extends PhysicsManager {

	private static final double velocityLimit = 0.065;
	private int multiplier;
	private ArrayList<PoolBall> balls;
	private PoolTable table;
	
	public PoolPhysicsManager(int multiplier){
		
		balls = new ArrayList<PoolBall>();
		
		this.multiplier = multiplier;
		
	}

	@Override
	public void move(long delta) {
		
		// Move balls
		for(PoolBall ball : balls){
			
			// Skip balls in pocket
			if (ball.inPocket()){
				continue;
			}
			
			// FRICTION - Slow down velocity
			Vector3D vel = new Vector3D(
					ball.getVelocity().getX() * table.getCloth().getFriction(), 
					ball.getVelocity().getY() * table.getCloth().getFriction(), 
					0);
			
			if (vel.getVectorLength() < velocityLimit){
				vel = new Vector3D(0, 0, 0);
			}
			
			ball.setVelocity(vel);
			
			// Move ball
			ball.move(ball.getVelocity().multiply( (delta / multiplier) ));
			
			// In pocket
			if (outOfBounds(ball)){
				
				ball.putInPocket();
				
			}
			
			// Test collision with bumpers
			checkBumperCollisions(ball);
						
			// Test collision with balls
			for(int i = balls.indexOf(ball); i < balls.size(); i++){
				
				// Skip balls in pocket
				if (balls.get(i).inPocket()){
					continue;
				}
				
				if (checkBallCollision(ball, balls.get(i))){
					
					ballCollision(ball, balls.get(i));
					
				}
				
			}
						
		}
		
	}

	private boolean outOfBounds(PoolBall ball) {

		if (ball.getPosition().getX() >= table.getCloth().getWidth() / 2){
			return true;
		}
				
		if (ball.getPosition().getX() <= -table.getCloth().getWidth() / 2){
			return true;
		}
		
		if (ball.getPosition().getY() >= table.getCloth().getHeight() / 2){
			return true;
		}
		
		if (ball.getPosition().getY() <= -table.getCloth().getHeight() / 2){
			return true;
		}
		
		return false;
		
	}

	private boolean checkBallCollision(PoolBall ball, PoolBall other) {
		
		if (ball == other) return false;
		
		Vector3D vectorBetween = ball.getPosition().subtract( other.getPosition() ).toVector();
		
		if (vectorBetween.getVectorLength() < ball.getRadius() + other.getRadius()) return true;
		
		return false;
	}
	
	private void ballCollision(PoolBall a, PoolBall b) {
		
		// Correct positions
		Vector3D vectorBetween3D = a.getPosition().subtract( b.getPosition() ).toVector();
		Vector3D unitBetween3D = vectorBetween3D.getUnitVector();
		a.setPosition( b.getPosition().add(unitBetween3D.multiply(a.getRadius() * 2.0) ) );
		
		Vector3D newVelA = new Vector3D(0,0,0);
		Vector3D newVelB = new Vector3D(0,0,0);
		
		Vector3D aToB = getTransferedForce(a, b);
		newVelB = newVelB.add(aToB);
		newVelA = newVelA.subtract(aToB);
		
		Vector3D bToA = getTransferedForce(b, a);
		newVelA = newVelA.add(bToA);
		newVelB = newVelB.subtract(bToA);
		
		newVelA = newVelA.add(a.getVelocity());
		newVelB = newVelB.add(b.getVelocity());
		a.setVelocity(newVelA);
		b.setVelocity(newVelB);
		
	}
		
	private Vector3D getTransferedForce(PoolBall a, PoolBall b) {
		
		Vector3D vectorBetween3D = a.getPosition().subtract( b.getPosition() ).toVector();
		Vector3D unitBetween3D = vectorBetween3D.getUnitVector();
		Vector2D unitBetween2D = new Vector2D(unitBetween3D);
		Vector2D velocityA2D = new Vector2D(a.getVelocity());
		
		if (!velocityA2D.equals(Vector2D.Zero)){
		
			// The angle between the two balls and the ball velocity
			double collisionAngle = Vector2D.angleBetween(unitBetween2D, velocityA2D);
			
			if(collisionAngle > 90){
				
				double impactOther = (collisionAngle - 90) / 90;
				double forceOther = velocityA2D.getVectorLength() * impactOther;

				Vector2D newVelB2D = unitBetween2D.multiply(forceOther).multiply( -1 );
				return new Vector3D(newVelB2D);
				
			}
			
		}
		
		return new Vector3D(0,0,0);
		
	}

	private void checkBumperCollisions(PoolBall ball) {
		
		if (checkBumperCollision(table.getBumperTopA(), ball)) 
			bumperCollision(table.getBumperTopA(), ball);
		if (checkBumperCollision(table.getBumperTopB(), ball)) 
			bumperCollision(table.getBumperTopB(), ball);
		if (checkBumperCollision(table.getBumperRight(), ball))
			bumperCollision(table.getBumperRight(), ball);
		if (checkBumperCollision(table.getBumperLeft(), ball))
			bumperCollision(table.getBumperLeft(), ball);
		if (checkBumperCollision(table.getBumperBottomA(), ball))
			bumperCollision(table.getBumperBottomA(), ball);
		if (checkBumperCollision(table.getBumperBottomB(), ball))
			bumperCollision(table.getBumperBottomB(), ball);
		
	}

	private boolean checkBumperCollision(Bumper bumper, PoolBall ball) {
		
		boolean collision = true;

		// Test
		double distanceX = Math.abs( ball.getPosition().getX() - bumper.getAnchor().getX() );
		double distanceY = Math.abs( ball.getPosition().getY() - bumper.getAnchor().getY() );
		
		if (distanceX > bumper.getWidth() / 2 + ball.getRadius() * Math.abs(bumper.getxDir())){
			collision = false;
		}
		if (distanceY > bumper.getHeight() / 2 + ball.getRadius() * Math.abs(bumper.getyDir())){
			collision = false;
		}
		
		return collision;
		
	}
	
	private void bumperCollision(Bumper bumper, PoolBall ball) {
		
		int x = 1;
		int y = 1;
		
		// If bumper is right or left
		if (bumper.getxDir() != 0){
			x = -1;
			if (bumper.getxDir() > 0)
				ball.getPosition().setX(bumper.getAnchor().getX() - (bumper.getWidth()/2 + ball.getRadius()));
			if (bumper.getxDir() < 0)
				ball.getPosition().setX(bumper.getAnchor().getX() + (bumper.getWidth()/2 + ball.getRadius()));
		}
		
		// If bumper is right or left
		if (bumper.getyDir() != 0){
			y = -1;
			if (bumper.getyDir() > 0)
				ball.getPosition().setY(bumper.getAnchor().getY() - (bumper.getHeight()/2 + ball.getRadius()));
			if (bumper.getyDir() < 0)
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
