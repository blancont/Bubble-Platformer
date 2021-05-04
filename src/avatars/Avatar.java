package avatars;
import game.GameObject;
import game.InteractableWorld;
import items.Projectile;

/**
 * An Avatar is a GameObject that is capable of performing jumps, shooting and
 * interacting with projectiles.
 * @author blancont
 *
 */

public abstract class Avatar extends GameObject {
	
	boolean isOnAPlatform;
	double jumpSpeed;

	public Avatar(InteractableWorld world, int colNum, int rowNum, int width, int height) {
		//initializes avatar
		super(world, colNum, rowNum, width, height);
		isOnAPlatform = false;
		jumpSpeed = 0;
		
	}
	
	abstract void shootProjectile();
	public abstract void collideWithProjectile();
	public abstract void collideWithWall();
	abstract void die();
	
	void jump() {
		//handles jumping
		if (isOnAPlatform) {
			y -= 1;
			yVelocity = -jumpSpeed;
			isOnAPlatform = false;
		}
		
	}
	
	public void collideWithFloor() {
		//handles floor collision values
		yVelocity = 0;
		if (!isOnAPlatform) {
			isOnAPlatform = true;
		}
	}
	
	public void collideWithCeiling() {
		//handles ceiling collision values
		yVelocity = 0;
	}
	
	@Override
	public void update() {
		//default update handler
		super.update();
		if (yVelocity > 1) {
			isOnAPlatform = false;
		}
	}

}
