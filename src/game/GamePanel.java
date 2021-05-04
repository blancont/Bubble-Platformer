package game;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 * The GamePanel is where the entire game is constantly updated. After every few
 * milliseconds, GamePanel calls the methods that update its InteractableWorld
 * and UIWorld, then repaints the window to display the new drawn graphics.
 * @author blancont
 *
 */
public class GamePanel extends JPanel {
	
	InteractableWorld world;
	UIWorld ui;
	
	public GamePanel(int width, int height) {
		//initializes panel
		world = new InteractableWorld(width, height);
		ui = new UIWorld(width, height, world);
		this.add(world);
		this.add(ui, BorderLayout.EAST);

		Timer repaintTimer = new Timer(1000 / 60, new ActionListener() {
			//handles updating the game, going to next frame
			public void actionPerformed(ActionEvent arg0) {
				world.updatePosition();
				ui.update();
				repaint();
			}
		});
		repaintTimer.start();
	}

}
