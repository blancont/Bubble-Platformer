package units;
import game.GameObject;
import game.InteractableWorld;
/**
 * The FloorUnit class creates floor units to be used for the level. A floor unit is 
 * a unit shaped like a square that is treated as a floor, with collision on the top, 
 * left, and right sides. The floor collides with any kind of game object. When a 
 * mook is bubbled, the mook will still be stopped by a floor unit above it.
 * @author hatfiej1
 *
 */
public class FloorUnit extends AbstractUnit {

	public FloorUnit(InteractableWorld world, int colNum, int rowNum) {
		super(world, colNum, rowNum);
	}

	@Override
	public void collideWith(GameObject obj) {
		double top = obj.getY();
		double bottom = top + obj.getHeight();
		if (this.overlaps(obj) && obj.yVelocity > 0) {
			if (bottom < y + height) {
				moveAboveUnit(obj);
				obj.collideWithFloor();
			}
			if (top > y){
				moveBelowUnit(obj);
				obj.collideWithCeiling();
			}
		}
	}
	
}
