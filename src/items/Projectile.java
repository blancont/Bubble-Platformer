package items;
import avatars.Hero;
import avatars.Mook;
import game.GameObject;
import game.InteractableWorld;

/**
 * The Projectile class handles everything with general projectiles, and acts as the
 * abstract class for the HeroProjectile and MookProjectile classes.
 * @author hatfiej1
 *
 */
public abstract class Projectile extends GameObject {
	
	private static final int SIZE = 20;
	private static final int SPEED = 15;
	private static final int TERMINAL_VELOCITY_Y = 5;
	
	protected boolean isActive;
	protected int activeFrames;
	protected int timer;
	
	public Projectile(InteractableWorld world, int x, int y, int direction) {
		super(x, y, SIZE, SIZE, world);
		this.direction = direction;
		
		xVelocity = SPEED;
		yAccel = 0;
		
		isActive = true;
		activeFrames = 0;
		timer = activeFrames;
		
	}

	@Override
	public void update() {
		y += yVelocity;
		x += xVelocity * direction;
		updateVelocity();
		
		if(y < 25) {
			y = 25;
		}
		
		if (timer < 0) {
			isActive = false;
		}
		
		if (timer < -200) {
			markToRemove();
		}
		timer -= 1;
	}
	
	private void updateVelocity() {
		if (xVelocity > 0) {
			xVelocity -= 0.25;	
		} else {
			xVelocity = 0;
		}
		
		if (Math.abs(yVelocity) < TERMINAL_VELOCITY_Y && !isActive) {
			yVelocity -= 0.1;
		}
		
	}

	abstract public void collideWith(Hero hero);
	abstract public void collideWith(Mook mook);
	
	@Override
	public void collideWithFloor() {
		markToRemove();
	}

	@Override
	public void collideWithCeiling() {
		markToRemove();
	}

	@Override
	public void collideWithWall() {
		markToRemove();
	}

}
