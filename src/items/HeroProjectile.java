package items;

import java.awt.Color;
import java.awt.Graphics2D;

import avatars.Hero;
import avatars.Mook;
import game.InteractableWorld;
/**
 * The HeroProjectile class handles the specificities with the projectile being shot
 * from a hero. For example, the hero's projectile has a different color than the 
 * projectile of a mook. It also can only hurt a mook.
 * @author hatfiej1
 *
 */
public class HeroProjectile extends Projectile {

	public HeroProjectile(InteractableWorld world, int x, int y, int direction) {
		super(world, x, y, direction);
		activeFrames = 35;
		timer = activeFrames;
	}

	@Override
	public void drawOn(Graphics2D g) {
		if (isActive) {
			g.setColor(new Color(102, 204, 255));
		} else {
			g.setColor(new Color(51, 204, 255, 40));
		}
		g.fillOval(x, y, width, height);
		g.setColor(Color.BLACK);
		
	}
	
	@Override
	public void collideWith(Hero hero) {
		// Nothing happens
	}

	@Override
	public void collideWith(Mook mook) {
		if (this.overlaps(mook) && isActive) {
			mook.collideWithProjectile();
		}
	}

}
