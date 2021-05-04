package items;

import java.awt.Color;
import java.awt.Graphics2D;

import avatars.Hero;
import game.GameObject;
import game.InteractableWorld;
import game.SoundEffect;
/**
 * The Fruit class handles how the fruit is created and interacts with the hero. The 
 * fruits are created after a bubble containing a mook is popped. The fruit can be
 * collected for points.
 * @author hatfiej1
 *
 */
public class Fruit extends GameObject {
	
	private static final int SIZE = 15;
	private static final int TERMINAL_VELOCITY_Y = 10;
	
	private boolean readyToCollect;

	public Fruit(int x, int y, InteractableWorld world) {
		//initializes fruit
		super(x, y, SIZE, SIZE, world);
		terminal_yVelocity = TERMINAL_VELOCITY_Y;
		readyToCollect = false;
		
	}

	@Override
	public void drawOn(Graphics2D g) {
		//draws fruit
		g.setColor(new Color(109,58,150));
		g.fillRect(x, y, SIZE, SIZE);
		g.setColor(Color.BLACK);

	}
	
	public void collideWith(Hero hero) {
		//checks for collision with hero and tells it what to do if it is colliding
		if (this.overlaps(hero) && readyToCollect) {
			SoundEffect.FRUIT.setToLoud();
			SoundEffect.FRUIT.play();
			readyToCollect = false;
			world.addPoints(300);
			markToRemove();
		}
	}

	@Override
	public void collideWithFloor() {
		yVelocity = 0;
		if (!canRemove) {
			readyToCollect = true;
		}
	}

	@Override
	public void collideWithCeiling() {
		// Nothing happens
	}

	@Override
	public void collideWithWall() {
		// Nothing happens
	}

}
