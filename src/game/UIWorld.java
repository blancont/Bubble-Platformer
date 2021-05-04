package game;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;

/**
 * UIWorld is responsible for displaying the data that is kept track of
 * in InteractableWorld. While InteractableWorld handles GameObjects,
 * UIWorld handles the visual representation of stats and values.
 * @author blancont
 *
 */
public class UIWorld extends JComponent {
	
	private JLabel score;
	private JLabel lives;
	private JLabel level;
	private JButton playButton;
	boolean readyToStartGame;
	private InteractableWorld world;
	private int oldPointValue;
	private GridBagConstraints c;
	private JLabel addScore;
	private int addScoreTimer;
	private JProgressBar charge;

	public UIWorld(int width, int height, InteractableWorld world) {
		//initializes UIWorld
		readyToStartGame = true;
		oldPointValue = 0;
		addScoreTimer = 30;
		this.world = world;
		c = new GridBagConstraints();
		setSize(width / 3, height);
		setPreferredSize(new Dimension(width / 3, height));
		setLayout(new GridBagLayout());
		c.anchor = GridBagConstraints.NORTH;
		c.insets = new Insets(0, 0, 10, 0);
		c.gridwidth = GridBagConstraints.REMAINDER;

		JLabel title = new JLabel("Bubble Bobble!", SwingConstants.CENTER);
		title.setFont(new Font("Impact", Font.BOLD, 45));
		title.setPreferredSize(new Dimension(getWidth(), 100));
		title.setBackground(new Color(255, 153, 51, 127));
		title.setOpaque(true);
		add(title, c);
		
		playButton = new JButton("Start Game");
		playButton.setFont(new Font("Arial", Font.PLAIN, 30));
		playButton.addActionListener(new StartGameListener(playButton, world));
		c.gridy = 1;
		c.weighty = 1;
		c.insets = new Insets(0, 0, 0, 0);
		add(playButton, c);
		
		JLabel scoreLabel = new JLabel("Score:", SwingConstants.RIGHT);
		scoreLabel.setFont(new Font("Arial", Font.BOLD, 35));
		scoreLabel.setPreferredSize(new Dimension((int) (getWidth() * 0.45), 50));
		c.weightx = 1;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridwidth = 1;
		c.gridy = 2;
		c.weighty = 0;
		add(scoreLabel, c);
		
		score = new JLabel("Score:", SwingConstants.CENTER);
		score.setFont(new Font("Arial", Font.PLAIN, 35));
		score.setPreferredSize(new Dimension((int) (getWidth() * 0.4), 50));
		c.gridx = 1;
		c.weighty = 0;
		add(score, c);
		
		addScore = new JLabel("", SwingConstants.LEFT);
		addScore.setFont(new Font("Arial", Font.ITALIC, 20));
		addScore.setPreferredSize(new Dimension((int) (getWidth() * 0.15), 50));
		addScore.setForeground(Color.BLUE);
		c.gridx = 2;
		add(addScore, c);
		
		JLabel livesLabel = new JLabel("Lives:", SwingConstants.RIGHT);
		livesLabel.setFont(new Font("Arial", Font.BOLD, 35));
		livesLabel.setPreferredSize(new Dimension((int) (getWidth() * 0.45), 50));
		c.gridx = 0;
		c.gridy = 3;
		add(livesLabel, c);
		
		lives = new JLabel("", SwingConstants.CENTER);
		lives.setFont(new Font("Arial", Font.PLAIN, 35));
		lives.setPreferredSize(new Dimension((int) (getWidth() * 0.4), 50));
		c.gridx = 1;
		add(lives, c);
		
		charge = new JProgressBar(0, world.getCharge());
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.insets = new Insets(20, 40, 0, 40);
		c.gridx = 0;
		c.gridy = 4;
		c.weighty = 1;
		add(charge, c);
		
		level = new JLabel("Level 1", SwingConstants.CENTER);
		level.setFont(new Font("Arial", Font.BOLD, 40));
		c.insets = new Insets(0, 0, 0, 0);
		c.gridx = 0;
		c.gridy = 5;
		c.weighty = 0;
		add(level, c);

	}
	
	// Used for troubleshooting!
	protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(new Color(255, 255, 204));
        g2.fillRect(0, 0, getWidth(), getHeight());
    }
	
	void update() {
		//updates the labels
		if (world.getPoints() > oldPointValue) {
			addScoreTimer = 30;
			addScore.setText("+" + (world.getPoints() - oldPointValue));
			oldPointValue = world.getPoints();
		} else if (world.getPoints() < oldPointValue) {
			oldPointValue = world.getPoints();
		}
		score.setText("");
		for (int i = 0; i < 5 - Integer.toString(world.getPoints()).length(); i++) {
			score.setText(score.getText() + "0");
		}
		score.setText(score.getText() + world.getPoints());
		lives.setText(Integer.toString(world.getLives()));
		charge.setValue(world.getCharge());
		if (world.getCurrentLevel() != 0) {
			level.setText("Level " + world.getCurrentLevel());
		}
		if (addScoreTimer <= 0) {
			addScore.setText("");
		} else {
			addScoreTimer -= 1;
			addScore.setForeground(new Color(0, 0, 255, (int) (addScoreTimer * ((double) 255 / 30))));
		}
	}

}
