package game;
import javax.swing.JFrame;

/**
 * Main creates a JFrame and adds a GamePanel to that frame. The size of
 * the GamePanel is determined here.
 * @author Joseph Hatfield and Nathaniel Blanco
 *
 */
public class Main {
	
	public static final int UNIT_SIZE = 25;
	static final int WIDTH = 40;
	static final int HEIGHT = 34;

	public static void main(String[] args) {
		//sets frame, packs it, and makes it visible. also adds game panel to the frame.
		
		JFrame frame = new JFrame();
		frame.setResizable(false);
		frame.add(new GamePanel(WIDTH * UNIT_SIZE, HEIGHT * UNIT_SIZE));
		
		frame.pack();
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}

}
