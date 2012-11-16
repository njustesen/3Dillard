
public class Matrix {

	int rows, colums;
	double [][] matrixData;
	
	public Matrix(double[][] matrixData){
		this.matrixData = matrixData;
		this.rows = matrixData.length;
		this.colums = matrixData[0].length;		
	}
	
	public Matrix(int rows, int colums){

		this.rows = rows;
		this.colums = colums;
		matrixData = new double[rows][colums];
		
	}
	
	public Matrix(Point3 p){	
		this.colums = 1;
		this.rows = 3;
		matrixData = new double[rows][colums];
		matrixData[0][0] = p.getX();
		matrixData[1][0] = p.getY();
		matrixData[2][0] = p.getZ();
		
	}
	
	public Matrix(Point3 p, double w){	
		this.colums = 1;
		this.rows = 3;
		matrixData = new double[rows][colums];
		matrixData[0][0] = p.getX();
		matrixData[1][0] = p.getY();
		matrixData[2][0] = p.getZ();
		matrixData[3][0] = w;
		
		
	}
	// return multiplication of tis and other matrix
    public Matrix multiplication(Matrix otherMatrix) {
       if(this.colums != otherMatrix.rows)
    	   throw new RuntimeException("Illegal matrix dimensions.");
       Matrix newMatrix = new Matrix(otherMatrix.rows, otherMatrix.colums);
       int counter = 0;
       for (int i = 0; i < otherMatrix.rows; i++){
    	   for(int j = 0; j < otherMatrix.colums; j++){
    //		   counter++;
    		   for(int k = 0; k < this.colums; k++){
    			   newMatrix.matrixData[i][j] += (this.matrixData[i][k]*otherMatrix.matrixData[k][j]);
    //			   System.out.println("this = "+this.matrixData[i][k]+" * "+otherMatrix.matrixData[k][j]);
    //			   System.out.println("new matrix being created.. cell "+i+","+j+"   from this matrix cell "+i+","+k+
    //					   " and other matrix cell "+k+","+j);
    		   }
    //		   counter = 0;
    	   }
   // 	   System.out.println("Value of ["+i+"]["+counter+"] = "+newMatrix.matrixData[i][counter]);
       }
       return newMatrix;
    }
    
    public Point3 multiplication(Point3 otherPoint) {
       
        Point3 newPoint = new Point3(0,0,0);
 
     			  double XX = 0;
     			  XX += (this.matrixData[0][0]*otherPoint.getX());
     			  XX += (this.matrixData[0][1]*otherPoint.getY());
     			  XX += (this.matrixData[0][2]*otherPoint.getZ());
    			  XX += (this.matrixData[0][3]*otherPoint.getW());
    			  
    			  double YY = 0;
    			  YY += (this.matrixData[1][0]*otherPoint.getX());
    			  YY += (this.matrixData[1][1]*otherPoint.getY());
    			  YY += (this.matrixData[1][2]*otherPoint.getZ());
    			  YY += (this.matrixData[1][3]*otherPoint.getW());
    			  
    			  double ZZ = 0;
    			  ZZ += (this.matrixData[2][0]*otherPoint.getX());
    			  ZZ += (this.matrixData[2][1]*otherPoint.getY());
    			  ZZ += (this.matrixData[2][2]*otherPoint.getZ());
    			  ZZ += (this.matrixData[2][3]*otherPoint.getW());
    			  
    			  double WW = 0;
     			  WW += (this.matrixData[3][0]*otherPoint.getX());
     			  WW += (this.matrixData[3][1]*otherPoint.getY());
     			  WW += (this.matrixData[3][2]*otherPoint.getZ());
     			  WW += (this.matrixData[3][3]*otherPoint.getW());
     			
     			newPoint.setX(XX);
     			newPoint.setY(YY);
     			newPoint.setZ(ZZ);
     			newPoint.setW(WW);
     			
     			return newPoint;
     }
    
    public Point3 toPoint(){
    	
    		Point3 newPoint = new Point3(matrixData[0][0],matrixData[1][0],matrixData[2][0]);
    		return newPoint;
    	

    }
    
    public Vector3 toVector(){
    	if(colums == 1 && rows == 3){
    		Vector3 newVector = new Vector3(matrixData[0][0],matrixData[1][0],matrixData[2][0]);
    		return newVector;
    	}
    	return null;
    }
}
