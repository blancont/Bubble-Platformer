package units;
import game.GameObject;
import game.InteractableWorld;
/**
 * The CeilingUnit class creates ceiling units to be used for the level. A ceiling unit 
 * is a unit shaped like a square that is treated as a ceiling, with collision on 
 * all four sides. The ceiling collides with any kind of game object.Even if a game 
 * object is on top of a ceiling, the game object will be pushed down.
 * @author hatfiej1
 *
 */
public class CeilingUnit extends AbstractUnit {

	public CeilingUnit(InteractableWorld world, int colNum, int rowNum) {
		super(world, colNum, rowNum);
	}

	@Override
	public void collideWith(GameObject obj) {
		if (this.overlaps(obj)) {
			moveBelowUnit(obj);
			obj.collideWithCeiling();
		}
	}

}
