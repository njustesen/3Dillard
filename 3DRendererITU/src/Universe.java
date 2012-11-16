import java.util.ArrayList;


public class Universe {
	
	//ArrayList <Triangle> trianglesInUniverse;
	ArrayList <Pyramid> shapesInUniverse;
	
	public Universe(){
		shapesInUniverse = new ArrayList <Pyramid> ();
	
		for(int i = 0; i < 50; i++){
			for(int j = 0; j < 50; j++){
				Pyramid p = new Pyramid(new Point3(i*350+500, 0, j*350+500));
				shapesInUniverse.add(p);
			}
		}
	}
	
	
	public void addTriangle(Pyramid newPyramid){
		if(!shapesInUniverse.contains(newPyramid))
			shapesInUniverse.add(newPyramid);
		else System.out.println(newPyramid+" is already added to the universe");
	}
}
