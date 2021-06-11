package game;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.KeyStroke;

/**
 * StartGameListener listens for when the Start Game / Restart Game button
 * in UIWorld is pressed. When that gets pressed, this class tells InteractableWorld
 * to start the game and its music at the very beginning.
 * @author blancont
 *
 */
public class StartGameListener implements ActionListener {

	private JButton startButton;
	private InteractableWorld world;
	
	public StartGameListener(JButton startButton, InteractableWorld world) {
		//initializes start button
		this.startButton = startButton;
		this.world = world;
		startButton.getInputMap().put(KeyStroke.getKeyStroke("SPACE"), "none");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		//tells the world what to do if the button has been pressed
		startButton.setText("Restart Game");
		world.startGame();
		SoundEffect.THEME.setToLoop();
		SoundEffect.THEME.play();
	}

}
