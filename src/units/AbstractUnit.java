package units;

import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import game.*;
/**
 * An abstract unit is a unit shaped like a square. These units are used to make the
 * levels, including the platforms and surrounding barriers. 
 * @author hatfiej1
 *
 */
public abstract class AbstractUnit extends GameObject {
	
	InteractableWorld world;

	public AbstractUnit(InteractableWorld world, int colNum, int rowNum) {
		super(world, colNum, rowNum, Main.UNIT_SIZE, Main.UNIT_SIZE);
	}
	
	abstract public void collideWith(GameObject obj);
	
	public void drawOn(Graphics2D g) {
		g.fillRect(x, y, width, height);
	}
	
	void moveAboveUnit(GameObject obj) {
		obj.moveTo(new Point2D.Double(obj.getX(), y - obj.getHeight()));
	}
	
	void moveBelowUnit(GameObject obj) {
		obj.moveTo(new Point2D.Double(obj.getX(), y + height));
	}
	
	void moveLeftOfUnit(GameObject obj) {
		obj.moveTo(new Point2D.Double(x - obj.getWidth(), obj.getY()));
	}
	
	void moveRightOfUnit(GameObject obj) {
		obj.moveTo(new Point2D.Double(x + width, obj.getY()));
	}
	
	public void collideWithFloor() {
		// Nothing happens
	}

	public void collideWithCeiling() {
		// Nothing happens
	}

	public void collideWithWall() {
		// Nothing happens
	}
	
}
