package items;

import java.awt.Color;
import java.awt.Graphics2D;

import avatars.Hero;
import avatars.Mook;
import game.InteractableWorld;

/**
 * The MookProjectile class handles the specificities with the projectile being shot
 * from a mook. For example, the mook's projectile has a different color than the 
 * projectile of a hero. It also can only hurt a hero.
 * @author hatfiej1
 *
 */
public class MookProjectile extends Projectile {
	
	public MookProjectile(InteractableWorld world, int x, int y, int direction) {
		super(world, x, y, direction);
		activeFrames = 20;
		timer = activeFrames;
	}
	
	@Override
	public void drawOn(Graphics2D g) {
		if (isActive) {
			g.setColor(new Color(0, 102, 0));
		} else {
			g.setColor(new Color(0, 102, 0, 40));
		}
		g.fillOval(x, y, width, height);
		g.setColor(Color.BLACK);
		
	}
	
	public void collideWith(Hero hero) {
		if(this.overlaps(hero) && isActive) {
			hero.collideWithProjectile();
		}
	}

	@Override
	public void collideWith(Mook mook) {
		//Nothing happens
	}

}
