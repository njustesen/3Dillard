
public class BillardScene extends Scene {

	@Override
	public void buildShapes() {
		for(int i = 0; i < 4; i++){
			for(int j = 0; j < 4; j++){
				Sphere p = new Sphere(new Point3(i*350+500, 0, j*350+500), 50);
				shapes.add(p);
			}
		}
	}

	@Override
	public void setCamera() {
		
		camera = new Camera(new Point3(2,100,2),
				new Point3(550,3,550),
				new Vector3(0,1,0), 70);
		camera.setMovementSpeed(30);
		camera.setRotationSpeed(30);
		
	}
	
}
