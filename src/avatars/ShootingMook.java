package avatars;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;

import items.MookProjectile;
import items.Projectile;
import game.InteractableWorld;
import game.SoundEffect;

/**
 * A ShootingMook has all the properties of a Mook with the added ability to
 * shoot projectiles, just like the Hero. ShootingMooks create MookProjectiles
 * as opposed to HeroProjectiles.
 * @author blancont
 *
 */
public class ShootingMook extends Mook {

	private static final double CHANCE_TO_SHOOT = 0.015;
	public ShootingMook(InteractableWorld world, int colNum, int rowNum) {
		//initializes shooting mook
		super(world, colNum, rowNum);
		pointValue = 225;
	}
	
	@Override
	public void drawOn(Graphics2D g) {
		//draws shooting mook
		g.setColor(new Color(0, 204, 102));
		g.fillRect(x, y, width, height);
		if (isBubbled) {
			g.setColor(new Color(0, 255, 255, (int) (timer * ((double) 255 / 300))));
			g.fillRect(x - 5, y - 5, width + 10, height + 10);
		}
		g.setColor(Color.BLACK);

	}
	
	@Override
	public void update() {
		//handles updating and whether or not it shoots on this frame
		super.update();
		if (Math.random() < CHANCE_TO_SHOOT) {
			shootProjectile();
		}
	}
	
	@Override
	void shootProjectile() {
		//handles shooting
		if (!isBubbled) {
			SoundEffect.SHOOT.play();
			world.addProjectile(new MookProjectile(world, x, y, direction));
		}
	}
	

}
