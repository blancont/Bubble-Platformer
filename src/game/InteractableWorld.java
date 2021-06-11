package game;
import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.KeyStroke;
import com.sun.glass.events.KeyEvent;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import avatars.Hero;
import avatars.Mook;
import items.Fruit;
import items.Projectile;
import items.SpecialBubble;
import units.AbstractUnit;

/**
 * InteractableWorld handles all of the game's operations: updating positions,
 * checking for collisions, removing objects, and keeping track of values that
 * typically do not reset between Hero deaths (charge, lives, points, etc.).
 * @author blancont
 *
 */
public class InteractableWorld extends JComponent { 
	
	private static int CHARGE_TIME = 2000;
	private LevelLoader loader;
	private int currentLevel;
	
	private ArrayList<AbstractUnit> units;
	private ArrayList<Hero> heroes;
	private ArrayList<Mook> enemies;
	private ArrayList<Projectile> projectiles;
	private ArrayList<Fruit> fruits;
	private ArrayList<GameObject> toBeRemoved;
	private ArrayList<SpecialBubble> bubbles;
	
	private boolean readyToReset;
	private static int lives;
	private static int points;
	private int timer = 180;
	private int charge = 0;

	public InteractableWorld(int width, int height) {
		//initializes interactableworld
		loader = new LevelLoader(this);
		units = new ArrayList<AbstractUnit>();
		heroes = new ArrayList<Hero>();
		enemies = new ArrayList<Mook>();
		projectiles = new ArrayList<Projectile>();
		fruits = new ArrayList<Fruit>();
		toBeRemoved = new ArrayList<GameObject>();
		bubbles = new ArrayList<SpecialBubble>();
		
		readyToReset = false;
		lives = 5;
		points = 0;
		charge = 2000;
		this.setPreferredSize(new Dimension(width, height));
		currentLevel = 0;
		addKeyBindings();
		
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		//paints everything on the world
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		
		for (AbstractUnit unit : units) {
			unit.drawOn(g2);
		}
		for (Hero hero : heroes) {
			hero.drawOn(g2);
			if (charge >= 2000) {
				hero.setChargeToReady();
			}
		}	
		for (Mook enemy : enemies) {
			enemy.drawOn(g2);
		}
		for (Projectile proj : projectiles) {
			proj.drawOn(g2);
		}
		for (Fruit fruit : fruits) {
			fruit.drawOn(g2);
		}
		for (SpecialBubble bubble : bubbles) {
			bubble.drawOn(g2);
		}
		
	}
	
	void updatePosition() {
		// Updating...
		//updates positions of everything on screen
		for (Hero hero : heroes) {
			hero.update();
		}
		for (Mook enemy : enemies) {
			enemy.update();
			if(enemy.canRemove) {
				toBeRemoved.add(enemy);
			}
		}
		for (Projectile proj : projectiles) {
			proj.update();
			if (proj.canRemove) {
				toBeRemoved.add(proj);
			}
		}
		for (Fruit fruit : fruits) {
			fruit.update();
			if (fruit.canRemove) {
				toBeRemoved.add(fruit);
			}
		}
		for (SpecialBubble bubble : bubbles) {
			charge = 0;
			bubble.update();
			if (bubble.canRemove) {
				toBeRemoved.add(bubble);
			}
		}
		
		if (enemies.size() == 0 && currentLevel != 0) {
			timer -= 1;
		}
		if (timer <=0) {
			nextLevel();
		}
		
		// Colliding...
		// Units initiate collisions with Heroes, Enemies, and Fruits
		for (AbstractUnit unit : units) {
			for (Hero hero : heroes) {
				unit.collideWith(hero);
			}
			for (Mook enemy : enemies) {
				unit.collideWith(enemy);
				enemy.collideWith(unit);
			}
			for (Fruit fruit : fruits) {
				unit.collideWith(fruit);
			}
			for (Projectile proj : projectiles) {
				unit.collideWith(proj);
			}
		}
		// Enemies initiate collisions with Heroes
		for (Mook enemy : enemies) {
			for (Hero hero : heroes) {
				enemy.collideWith(hero);
			}
		}
		// Projectiles intiate collisions with Heroes and Enemies
		for (Projectile proj : projectiles) {
			for (Hero hero : heroes) {
				proj.collideWith(hero);
			}
			for (Mook enemy : enemies) {
				proj.collideWith(enemy);
			}
		}
		// Fruits intiate collisions with Heroes
		for (Fruit fruit : fruits) {
			for (Hero hero : heroes) {
				fruit.collideWith(hero);
			}
		}
		for (SpecialBubble bubble : bubbles) {
			for (Mook enemy : enemies) {
				bubble.collideWith(enemy);
			}
		}
		
		// Removing...
		for (GameObject obj : toBeRemoved) {
			remove(obj);
		}
		toBeRemoved.removeAll(toBeRemoved);
		if (readyToReset) {
			if (lives <= 0) {
				startGame();
			} else {
				lives -= 1;
				resetLevel();
			}
		}
		if (charge < CHARGE_TIME) {
			charge += 1;
		}
		
	}
	
	private void nextLevel() {
		//sets and loads next level
		if (loader.constructLevel("levels/Level" + (currentLevel + 1) + ".txt")) {
			currentLevel += 1;
		} else {
			loader.constructLevel("levels/Level1.txt");
			currentLevel = 1;
		}
		timer = 180;
	}

	public void addUnit(AbstractUnit unit) {
		//adds a unit for building world
		units.add(unit);
	}
	
	void addHero(Hero hero) {
		//adds a hero to the map
		heroes.add(hero);
	}
	
	void addEnemy(Mook mook) {
		//adds a mook to the map
		enemies.add(mook);
	}
	
	public void addProjectile(Projectile proj) {
		//adds a projectile to where necessary
		projectiles.add(proj);
	}
	
	public void addFruit(Fruit fruit) {
		//adds fruit on bubble pop
		fruits.add(fruit);
	}
	
	public void addSpecialBubble(SpecialBubble bubble) {
		//adds special bubble
		bubbles.add(bubble);
	}

	void clearContents() {
		//clears everything from the screen
		units.removeAll(units);
		heroes.removeAll(heroes);
		enemies.removeAll(enemies);
		projectiles.removeAll(projectiles);
		fruits.removeAll(fruits);
		
	}
	
	public void remove(GameObject obj) {
		//removes a single object from the screen
		units.remove(obj);
		heroes.remove(obj);
		enemies.remove(obj);
		projectiles.remove(obj);
		fruits.remove(obj);
		bubbles.remove(obj);
		
	}
	
	private void addKeyBindings() {
		//adds dev keybindings to change world
		getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_U, 0, false), "Go up one level");
		getActionMap().put("Go up one level", new AbstractAction() {
			public void actionPerformed(ActionEvent arg0) {
				if (loader.constructLevel("levels/Level" + (currentLevel + 1) + ".txt")) {
					currentLevel += 1;
				}
			}
		});
		getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_D, 0, false), "Go down one level");
		getActionMap().put("Go down one level", new AbstractAction() {
			public void actionPerformed(ActionEvent arg0) {
				if (loader.constructLevel("levels/Level" + (currentLevel - 1) + ".txt")) {
					currentLevel -= 1;
				}
			}
		});
		
	}

	public void markToReset() {
		//sets boolean to make sure the world is ready to be reset
		readyToReset = true;
		
	}
	
	private void resetLevel() {
		//resets level
		loader.constructLevel("levels/Level" + currentLevel + ".txt");
		readyToReset = false;
		
	}

	public void addPoints(int value) {
		//adds points
		points += value;
		
	}
	
	int getPoints() {
		//returns current score
		return points;
	}
	
	int getLives() {
		//returns current lives
		return lives;
	}
	
	int getCurrentLevel() {
		//returns current level
		return currentLevel;
	}
	
	public int getCharge() {
		//returns the charge of the charge shot
		return charge;
	}
	
	void startGame() {
		//loads everything needed to start game, including initial values
		loader.constructLevel("levels/Level1.txt");
		currentLevel = 1;
		lives = 5;
		points = 0;
		charge = 0;
		readyToReset = false;
	}

}
