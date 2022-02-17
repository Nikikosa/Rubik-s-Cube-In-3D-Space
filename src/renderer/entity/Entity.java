package renderer.entity;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import renderer.point.MyVector;
import renderer.shapes.MyPolygon;
import renderer.shapes.Tetrahedron;

public class Entity implements IEntity{

	private List<Tetrahedron> tetrahedrons;
	private MyPolygon[] polygons;
	
	public Entity(List<Tetrahedron> tetrahedrons) {
		this.tetrahedrons = tetrahedrons;
		List<MyPolygon> tempList = new ArrayList<MyPolygon>();
		for (Tetrahedron tetra : this.tetrahedrons) {
			tempList.addAll(Arrays.asList(tetra.getPolygons()));
		}
		this.polygons = new MyPolygon[tempList.size()];
		this.polygons = tempList.toArray(this.polygons);
		this.sortPolygons();
	}
	
	@Override
	public void render(Graphics g) {
		
		for (MyPolygon poly : this.polygons) {
			poly.render(g);
		}
	}

	
	
	private void sortPolygons() {
		MyPolygon.sortPolygons(this.polygons);
	}

	@Override
	public void rotate(boolean CW, double xDegrees, double yDegrees, double zDegrees, MyVector lightVector) {
		for (Tetrahedron tetra : this.tetrahedrons) {
			tetra.rotate(CW, xDegrees, yDegrees, zDegrees, lightVector);
			
		}
		this.sortPolygons();
	}
	
	@Override
	public void setLighting(MyVector lightVector) {
		for (Tetrahedron tetra : this.tetrahedrons) {
			tetra.setLighting(lightVector);
			
		}
	}

	
	

}
