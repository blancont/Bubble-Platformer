package avatars;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import game.InteractableWorld;
import game.Main;
import game.SoundEffect;
import items.Fruit;
import items.Projectile;
import units.AbstractUnit;

/**
 * A Mook is a noncontrollable Avatar that kills the Hero whenever it or its projectile
 * comes in contact. Mooks are able to be bubbled and free themselves from these bubbles
 * after a period of time. Mooks change direction at random intervals, when hitting a wall,
 * and when hitting the Hero's shield. Mooks jump at random intervals as well.
 * @author blancont
 *
 */
public class Mook extends Avatar {

	private static final int WIDTH = Main.UNIT_SIZE + 10;
	private static final int HEIGHT = Main.UNIT_SIZE + 10;
	private static final int JUMP_SPEED = 20;
	private static final int TERMINAL_VELOCITY_X = 4;
	private static final int BUBBLED_FRAMES = 300;
	private static final double CHANGE_MOVEMENT_CHANCE = 0.01;

	boolean isBubbled;
	int timer;
	int pointValue;
	private boolean turningAwayFromShield;
	private int turningAwayCount;
	
	public Mook(InteractableWorld world, int colNum, int rowNum) {
		//initializes mook
		super(world, colNum, rowNum, WIDTH, HEIGHT);
		jumpSpeed = JUMP_SPEED;
		terminal_xVelocity = TERMINAL_VELOCITY_X;
		
		xAccel = 1.5;
		direction = 1;
		if (Math.random() < 0.5) {
			reverseDirection();
		}
		
		isBubbled = false;
		timer = BUBBLED_FRAMES;
		pointValue = 150;
		turningAwayFromShield = false;
		turningAwayCount = 10;
		
	}

	@Override
	public void drawOn(Graphics2D g) {
		//draws mook
		g.setColor(Color.BLUE);
		g.fillRect(x, y, WIDTH, HEIGHT);
		if (isBubbled) {
			g.setColor(new Color(0, 255, 255, (int) (timer * ((double) 255 / 300))));
			g.fillRect(x - 5, y - 5, WIDTH + 10, HEIGHT + 10);
		}
		g.setColor(Color.BLACK);

	}
	
	@Override
	public void update() {
		//updates mook, handling movement
		super.update();
		if (isBubbled) {
			timer -= 1;
			if (timer <= 0) {
				isBubbled = false;
				timer = BUBBLED_FRAMES;
				xAccel = 1.5;
				direction = 1;
				if (Math.random() < 0.5) {
					reverseDirection();
				}
				yAccel = GRAVITY;
			}
		} else {
			if (Math.random() < CHANGE_MOVEMENT_CHANCE) {
				jump();
			}
			if (Math.random() < CHANGE_MOVEMENT_CHANCE) {
				reverseDirection();
			}
		}
	}
	
	@Override
	void shootProjectile() {
		// Nothing happens
	}
	
	public void collideWithProjectile() {
		//handles what to do if hit with a projectile by the hero
		if (!isBubbled) {
			SoundEffect.BUBBLED.setToLoud();
			SoundEffect.BUBBLED.play();
			isBubbled = true;
			yVelocity = 0;
			xAccel = 0;
			yAccel = -0.1;
			world.addPoints(pointValue / 3);
		}
	}
	
	public void collideWithWall() {
		//handles what to do on collision with a wall
		reverseDirection();
	}
	
	void die() {
		//handles what to do on death
		world.addFruit(new Fruit(x, y, world));
		markToRemove();
	}

	public void collideWith(Hero hero) {
		//handles collision with hero and what to do
		if (this.overlaps(hero)) {
			if (!isBubbled) {
				hero.collideWithMook();
				if (hero.getShielding() && !turningAwayFromShield) {
					turningAwayFromShield = true;
					reverseDirection();
				}
			}
			else if (!canRemove){
				SoundEffect.POP.play();
				world.addPoints(pointValue);
				die();
			}
		}
		if (turningAwayFromShield) {
			if (turningAwayCount <= 0) {
				turningAwayCount = 10;
				turningAwayFromShield = false;
			}
			turningAwayCount -= 1;
		}
	}

	public void collideWith(AbstractUnit unit) {
		//handles unit collision
		if (this.overlaps(unit)) {
			if (isBubbled) {
				yVelocity = 0;
				yAccel = 0;
			}
		}
	}
	
}

