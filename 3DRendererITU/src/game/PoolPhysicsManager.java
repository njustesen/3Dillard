package game;

import java.awt.Shape;
import java.util.ArrayList;

import engine.GameObject;
import engine.Scene;
import engine.math.Vector3D;
import engine.physics.MovableBall;
import engine.physics.PhysicsManager;
import engine.shapes.Cube;
import engine.shapes.Shape3D;
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
			
		}
		
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
	
	private void bumperCollision(Bumper bumperTop, PoolBall ball) {
		
		int xDir = 1;
		int yDir = 1;
		
		if (bumperTop.getAnchor().getX() != 0){
			xDir = -1;
		}
		
		if (bumperTop.getAnchor().getY() != 0){
			yDir = -1;
		}
		
		Vector3D newVelocity = ball.getVelocity();
		newVelocity.setX( newVelocity.getX() * xDir );
		newVelocity.setY( newVelocity.getY() * yDir );
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
