package game;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStream;
import java.util.Scanner;
import avatars.*;
import units.*;

/**
 * LevelLoader is responsible for generating levels onto InteractableWorld
 * by reading from a text file.
 * @author blancont
 *
 */
public class LevelLoader {
	
	private InteractableWorld world;

	public LevelLoader(InteractableWorld world) {
		//initializes level loader
		this.world = world;
	}
	
	boolean constructLevel(String filename) {
		//constructs level using a grid system based on a text file
		InputStream input = this.getClass().getClassLoader().getResourceAsStream(filename);
		Scanner scanner = new Scanner(input);
		world.clearContents();
		
		for (int row = 0; row < Main.HEIGHT; row++) {
			String currentLine = scanner.next();
			for (int col = 0; col < Main.WIDTH; col++) {
				if (currentLine.charAt(col) == '*') {
					world.addUnit(new FloorUnit(world, col, row));
				} else if (currentLine.charAt(col) == 'H') {
					world.addHero(new Hero(world, col, row));
				} else if (currentLine.charAt(col) == '|') {
					world.addUnit(new WallUnit(world, col, row));
				} else if (currentLine.charAt(col) == '_') {
					world.addUnit(new CeilingUnit(world, col, row));
				} else if (currentLine.charAt(col) == 'M') {
					world.addEnemy(new Mook(world, col, row));
				} else if (currentLine.charAt(col) == 'S') {
					world.addEnemy(new ShootingMook(world, col, row));
				}
			}
			if (scanner.hasNextLine()) {
				scanner.nextLine();
			}
		}
		
		scanner.close();
		return true;
	}
	
}
