package engine;

import engine.math.Matrix;
import engine.math.Point3D;
import engine.shapes.Shape3D;
import engine.shapes.Triangle3D;

public class TransformManager {
	
	public static void rotateObject(GameObject obj, double degreesX, double degreesY, double degreesZ){
		
		for(Shape3D shape : obj.getShapes()){
			
			for(Triangle3D triangle: shape.getTriangles()){
				
				if (degreesX != 0){
					triangle.setPointA( rotatePointX(triangle.getPointA(), degreesX) );
					triangle.setPointB( rotatePointX(triangle.getPointB(), degreesX) );
					triangle.setPointC( rotatePointX(triangle.getPointC(), degreesX) );
				}
				
				if (degreesY != 0){
					triangle.setPointA( rotatePointY(triangle.getPointA(), degreesY) );
					triangle.setPointB( rotatePointY(triangle.getPointB(), degreesY) );
					triangle.setPointC( rotatePointY(triangle.getPointC(), degreesY) );
				}
				
				if (degreesZ != 0){
					triangle.setPointA( rotatePointZ(triangle.getPointA(), degreesZ) );
					triangle.setPointB( rotatePointZ(triangle.getPointB(), degreesZ) );
					triangle.setPointC( rotatePointZ(triangle.getPointC(), degreesZ) );
				}
				
			}
		}
		
	}
	
	public static Point3D rotatePointX(Point3D vector, double degrees){

		double cosD = Math.cos(degrees);
		double sinD = Math.sin(degrees);
		
		double[][] arrayA = 
			{
			{ 1.0,	0.0, 0.0, 0.0 },
			{ 0.0,	cosD, -sinD, 0.0 },
			{ 0.0,	sinD, cosD, 0.0 },
			{ 0.0,	0.0, 0.0, 1.0 },
			};
		
		Matrix matrixA = new Matrix(arrayA);

		double[][] arrayB = 
			{
			{ vector.getX() },
			{ vector.getY() },
			{ vector.getZ() },
			{ 1.0f },
			};
		
		Matrix matrixB = new Matrix(arrayB);
		
		Matrix result = matrixA.multiplication(matrixB);

		return result.toPoint();
		
	}
	
	public static Point3D rotatePointY(Point3D vector, double degrees){

		double cosD = Math.cos(degrees);
		double sinD = Math.sin(degrees);
		
		double[][] arrayA = 
			{
			{ cosD,	0.0f, sinD, 0.0f },
			{ 0.0f,	1.0f, 0.0f, 0.0f },
			{ -sinD,0.0f, cosD, 0.0f },
			{ 0.0f,	0.0f, 0.0f, 1.0f },
			};
		
		Matrix matrixA = new Matrix(arrayA);

		double[][] arrayB = 
			{
			{ vector.getX() },
			{ vector.getY() },
			{ vector.getZ() },
			{ 1.0f },
			};
		
		Matrix matrixB = new Matrix(arrayB);
		
		Matrix result = matrixA.multiplication(matrixB);

		return result.toPoint();
		
	}
	
	public static Point3D rotatePointZ(Point3D vector, double degrees){

		double cosD = Math.cos(degrees);
		double sinD = Math.sin(degrees);
		
		double[][] arrayA = 
			{
			{ cosD,	-sinD, 0.0f, 0.0f },
			{ sinD,	cosD, 0.0f, 0.0f },
			{ 0.0f, 0.0f, 1.0f, 0.0f },
			{ 0.0f,	0.0f, 0.0f, 1.0f },
			};
		
		Matrix matrixA = new Matrix(arrayA);

		double[][] arrayB = 
			{
			{ vector.getX() },
			{ vector.getY() },
			{ vector.getZ() },
			{ 1.0f },
			};
		
		Matrix matrixB = new Matrix(arrayB);
		
		Matrix result = matrixA.multiplication(matrixB);

		return result.toPoint();
		
	}
	
}
