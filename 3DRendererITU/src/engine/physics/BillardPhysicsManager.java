package engine.physics;

import java.util.ArrayList;
import engine.math.Vector2D;
import engine.math.Vector3D;
import engine.objects.Rail;
import engine.objects.BilliardBall;
import engine.objects.BilliardTable;
import engine.objects.GameObject;
import engine.rendering.Scene;

public class BillardPhysicsManager {

	private static final double velocityLimit = 0.01;	// 1 cm/sec
	private ArrayList<BilliardBall> balls;
	private BilliardTable table;
	private int iterationsPerSecond;
	
	public BillardPhysicsManager(int iterationsPerSecond){
		
		balls = new ArrayList<BilliardBall>();
		this.iterationsPerSecond = iterationsPerSecond;
		
	}

	public void move(long delta) {
		
		int iterations = Math.max(1, (int) (delta/1000.0 * iterationsPerSecond) );
		double iterationTime = delta/iterations;
		
		for(int i = 0; i < iterations; i++){
			
			//long nano = System.nanoTime();
			moveBalls(iterationTime);
			//nano = System.nanoTime() - nano;
			//double ms = nano / 1000000.0;
			//System.out.println(ms);
			
		}
		
	}

	public void moveBalls(double ms){
		

		// Move balls
		for(BilliardBall ball : balls){
			
			// Skip balls in pocket
			if (ball.inPocket()){
				continue;
			}
			
			// Friction
			double speed = ball.getVelocity().getVectorLength();
			double deacceleration = table.getCloth().getDeaccelerationCoefficient() * speed;
			if (deacceleration >= speed){
				speed = 0;
			} else {
				speed -= deacceleration;
			}
			
			if (speed < velocityLimit && speed != 0){
				speed = 0;
			}
			
			Vector3D unit = ball.getVelocity().getUnitVector();
			ball.setVelocity( unit.multiply(speed) );

			
			// Move ball - in cm/s
			ball.move(ball.getVelocity().multiply( ms/1000.0 * 100 ));
			
			// In pocket
			if (outOfBounds(ball)){
				
				ball.putInPocket();
				
			}
			
			// Test collision with bumpers
			checkRailCollisions(ball);
						
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
	
	private boolean outOfBounds(BilliardBall ball) {

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

	private boolean checkBallCollision(BilliardBall ball, BilliardBall other) {
		
		if (ball == other) return false;
		
		Vector3D vectorBetween = ball.getPosition().subtract( other.getPosition() ).toVector();
		
		if (vectorBetween.getVectorLength() < ball.getRadius() + other.getRadius()) return true;
		
		return false;
	}
	
	private void ballCollision(BilliardBall a, BilliardBall b) {
		
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
		
	private Vector3D getTransferedForce(BilliardBall a, BilliardBall b) {
		
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

	private void checkRailCollisions(BilliardBall ball) {
		
		if (checkRailCollision(table.getRailTopA(), ball)) 
			bumperCollision(table.getRailTopA(), ball);
		if (checkRailCollision(table.getRailTopB(), ball)) 
			bumperCollision(table.getRailTopB(), ball);
		if (checkRailCollision(table.getRailRight(), ball))
			bumperCollision(table.getRailRight(), ball);
		if (checkRailCollision(table.getRailLeft(), ball))
			bumperCollision(table.getRailLeft(), ball);
		if (checkRailCollision(table.getRailBottomA(), ball))
			bumperCollision(table.getRailBottomA(), ball);
		if (checkRailCollision(table.getRailBottomB(), ball))
			bumperCollision(table.getRailBottomB(), ball);
		
	}

	private boolean checkRailCollision(Rail bumper, BilliardBall ball) {
		
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
	
	private void bumperCollision(Rail bumper, BilliardBall ball) {
		
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

	public ArrayList<BilliardBall> getBalls() {
		return balls;
	}

	public void setBalls(ArrayList<BilliardBall> balls) {
		this.balls = balls;
	}
	
	public void addBall(BilliardBall ball){
		this.balls.add(ball);
		
	}

	public BilliardTable getTable() {
		return table;
	}

	public void setTable(BilliardTable table) {
		this.table = table;
	}

	public void setupFromScene(Scene scene) {
		
		for(GameObject obj : scene.getObjects()){
			
			if (obj instanceof BilliardBall){
				
				addBall((BilliardBall)obj);
				
			} else if (obj instanceof BilliardTable){
				
				setTable((BilliardTable)obj);
				
			}
		}
		
	}

}
