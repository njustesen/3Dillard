import java.util.ArrayList;


public class Sphere extends Shape3D{

	public Sphere(Point3 anchor, int size) {
		super(anchor, size);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void build() {
		int N = 24;
		int M = 24;
		
		ArrayList<Point3> points = new ArrayList<Point3>();
		
		// M lines of latitude (horizontal)
		for(int m = 0; m < M; m++){
			
			// N lines of longitude (vertical)
			for(int n = 0; n < N; n++){
				
				// (x, y, z) = (sin(Pi * m/M) cos(2Pi * n/N), sin(Pi * m/M) sin(2Pi * n/N), cos(Pi * m/M))
				Point3 p = new Point3(	size * Math.sin(Math.PI * m/M) * Math.cos(2*Math.PI * n/N), 
										size * Math.sin(Math.PI * m/M) * Math.sin(2*Math.PI * n/N), 
										size * Math.cos(Math.PI * m/M));
				
				points.add(p);
				
			}
			
		}
		
		// Connect points
		for(Point3 p : points){
			
			int index = points.indexOf(p);
			
			if (index + N < points.size()){
				
				Point3 b = points.get(index+1);
				Point3 c = points.get(index+N);
				Triangle3 t = new Triangle3(p, b, c);
				triangles.add(t);
				
			}
		}
		
		// Connect points
		for(Point3 p : points){
			
			int index = points.indexOf(p);
			
			if (index - N > 0){
				Point3 b = points.get(index-1);
				Point3 c = points.get(index-N);
				Triangle3 t = new Triangle3(p, b, c);
				triangles.add(t);
			}
		}
		
	}

}
