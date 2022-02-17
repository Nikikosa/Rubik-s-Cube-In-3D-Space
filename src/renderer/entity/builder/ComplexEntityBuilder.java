package renderer.entity.builder;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import renderer.entity.Entity;
import renderer.entity.EntityManager;
import renderer.entity.IEntity;
import renderer.point.MyPoint;
import renderer.shapes.MyPolygon;
import renderer.shapes.Tetrahedron;

public class ComplexEntityBuilder {
	private static EntityManager manager;
	
	public static void setManager(EntityManager managerNew) {
		manager = managerNew;
	}
	
	public static IEntity createRubixsCube(double size, double centerX, double centerY,double centerZ) {
		List<Tetrahedron> tetras = new ArrayList<Tetrahedron>();
		
		
		double cubeSpacing = 10;
		MyPolygon polyRed, polyWhite, polyBlue, polyGreen, polyOrange, polyYellow;
		
		for (int i = -1; i < 2; i++) {
			double cubeCenterX = i * (size + cubeSpacing) + centerX;
			for (int j = -1; j < 2; j++) {
				double cubeCenterY = j * (size + cubeSpacing) + centerY;
				for (int k =-1; k < 2; k++) {
					double cubeCenterZ = k * (size + cubeSpacing) + centerZ;
					MyPoint p1 = new MyPoint(cubeCenterX - size / 2, cubeCenterY - size / 2, cubeCenterZ - size / 2);
					MyPoint p2 = new MyPoint(cubeCenterX - size / 2, cubeCenterY - size / 2, cubeCenterZ + size / 2);
					MyPoint p3 = new MyPoint(cubeCenterX - size / 2, cubeCenterY + size / 2, cubeCenterZ - size / 2);
					MyPoint p4 = new MyPoint(cubeCenterX - size / 2, cubeCenterY + size / 2, cubeCenterZ + size / 2);
					MyPoint p5 = new MyPoint(cubeCenterX + size / 2, cubeCenterY - size / 2, cubeCenterZ - size / 2);
					MyPoint p6 = new MyPoint(cubeCenterX + size / 2, cubeCenterY - size / 2, cubeCenterZ + size / 2);
					MyPoint p7 = new MyPoint(cubeCenterX + size / 2, cubeCenterY + size / 2, cubeCenterZ - size / 2);
					MyPoint p8 = new MyPoint(cubeCenterX + size / 2, cubeCenterY + size / 2, cubeCenterZ + size / 2);
					
					
						polyRed = new MyPolygon(Color.RED, p5,p6,p8,p7);
						polyWhite = new MyPolygon(Color.WHITE,p2,p4,p8,p6);
						polyBlue = new MyPolygon(Color.BLUE,p3,p7,p8,p4);
						polyGreen = new MyPolygon(new Color(0,204,0),p1,p2,p6,p5);
						polyOrange = new MyPolygon(new Color(255,120,30),p1,p3,p4,p2);
						polyYellow = new MyPolygon(Color.YELLOW,p1,p5,p7,p3);
					
					
					if (k== -1 || k == 0) {
						polyWhite = new MyPolygon(Color.BLACK,p2,p4,p8,p6);
					}
					if (k == 1 || k == 0) {
						polyYellow = new MyPolygon(Color.BLACK,p1,p5,p7,p3);
					}
					if (j == -1 || j == 0) {
						polyBlue = new MyPolygon(Color.BLACK,p3,p7,p8,p4);
					}
					if (j == 0 || j == 1) {
						polyGreen = new MyPolygon(Color.BLACK,p1,p2,p6,p5);
					}
					if (i== -1 || i == 0) {
						polyRed = new MyPolygon(Color.BLACK, p5,p6,p8,p7);
					}
					if (i == 1 || i == 0) {
						polyOrange = new MyPolygon(Color.BLACK,p1,p3,p4,p2);
					}
					
					Tetrahedron tetra = new Tetrahedron(polyRed, polyWhite, polyBlue, polyGreen, polyOrange, polyYellow);
					tetras.add(tetra);
					
				}
			}
			// TODO 
		}
		
		return new Entity(tetras);
	}
	
}
