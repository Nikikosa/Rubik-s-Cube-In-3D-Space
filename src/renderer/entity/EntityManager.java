package renderer.entity;

import java.awt.Color;
import java.awt.Graphics;
import java.util.*;

import renderer.entity.builder.BasicEntityBuilder;
import renderer.entity.builder.ComplexEntityBuilder;
import renderer.input.ClickType;
import renderer.input.Mouse;
import renderer.point.MyPoint;
import renderer.point.MyVector;
import renderer.point.PointConverter;
import renderer.shapes.MyPolygon;
import renderer.shapes.Tetrahedron;

public class EntityManager {
	
	private List<IEntity> entities;
	private IEntity[][] row = new IEntity[3][10]; 
	private IEntity[][] col = new IEntity[3][10];
	private int initialX, initialY;
//	private ClickType prevMouse = ClickType.Unknown;
	private double mouseSensitivity = 2;
	private MyVector lightVector = MyVector.normalize( new MyVector(0,10,10));
	
	public EntityManager() {
		this.entities = new ArrayList<IEntity>();
	}
	
	public void init() {
		ComplexEntityBuilder.setManager(this);
		this.entities.add(ComplexEntityBuilder.createRubixsCube(100, 0, 0, 0));
		this.setLighting();
		
	}
	
	public void update(Mouse mouse) {

		int x = mouse.getX();
		int y = mouse.getY();
		if(mouse.getB() == ClickType.LeftClick) {
			
			
			int xDif = x - initialX;
			int yDif = y - initialY;
			
			this.rotate(true, 0, -yDif/mouseSensitivity, -xDif/mouseSensitivity);
			
		} else if(mouse.getB() == ClickType.RightClick) {
			
			
			int xDif = x - initialX;
			
			this.rotate(true, -xDif/2, 0, 0);
			
		}
		
		if(mouse.isScrollingUp()) {
			PointConverter.zoomIn();
		} else if (mouse.isScrollingDown()) {
			PointConverter.zoomOut();
		}
		
		mouse.resetScroll();
		
		initialX = x;
		initialY = y;
		
		
		
	
	}
	public void render(Graphics g) {
		for(IEntity entity: this.entities) {
			entity.render(g);
		}
	}
	
	private void rotate(boolean CW, double xDegrees, double yDegrees, double zDegrees) {
		for(IEntity entity: this.entities) {
			entity.rotate(CW, xDegrees, yDegrees, zDegrees, this.lightVector);
		}
	}
	
	private void setLighting() {
		for (IEntity tetra : this.entities) {
			tetra.setLighting(this.lightVector);
		}
	}
	
	public void updateRow(List<Entity> entity, int row) {
		int i = 0;
		for (Entity entities : entity) {
			this.row[row][i] = entities;
			i++;
		}
	}
	
	
}
