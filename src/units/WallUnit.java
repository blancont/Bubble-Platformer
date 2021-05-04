package units;

import game.GameObject;
import game.InteractableWorld;

/**
 * The WallUnit class creates wall units to be used for the level. A wall unit is a 
 * unit shaped like a square that is treated as a wall, 
 * with collision on all four sides. The wall collides with any kind of game object. 
 * @author hatfiej1
 *
 */
public class WallUnit extends AbstractUnit {

	public WallUnit(InteractableWorld world, int colNum, int rowNum) {
		super(world, colNum, rowNum);
	}

	@Override
	public void collideWith(GameObject obj) {
		double center = obj.getHitbox().getCenterX();
		if (this.overlaps(obj)) {
			if (center > this.getHitbox().getCenterX()) {
				moveRightOfUnit(obj);
				obj.collideWithWall();
			} else if (center < this.getHitbox().getCenterX()){
				moveLeftOfUnit(obj);
				obj.collideWithWall();
			} else {
				moveBelowUnit(obj);
				obj.collideWithCeiling();
			}
		}
	}

}
