package game;

import java.io.*;
import java.net.URL;
import javax.sound.sampled.*;

/**
 * SoundEffect handles the game's SFX. Classes that want to use SFX will
 * call the static variables in this enum and play them via the play()
 * method.
 * @author blancont
 *
 */
public enum SoundEffect {

	THEME("theme.wav"),
	FRUIT("fruit.wav"),
	DEATH("death.wav"),
	SHOOT("shoot.wav"),
	POP("pop.wav"),
	BUBBLED("bubbled.wav"),
	JUMP("jump.wav"),
	EXPLODE("explode.wav"),
	LAND("land.wav");
	
	public static enum Volume {
		MUTE, LOW, MEDIUM, HIGH
	}
	
	public static Volume volume = Volume.LOW;
	
	private Clip clip;
	
	SoundEffect(String soundFileName) {
		//sets the sound effect
		try {
			URL url = this.getClass().getClassLoader().getResource(soundFileName);
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(url);
			clip = AudioSystem.getClip();
			clip.open(audioInputStream);
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}
	}
	
	public void play() {
		//plays the sound effect
		if (volume != Volume.MUTE) {
			if (clip.isRunning()) {
				clip.stop();
			}
			clip.setFramePosition(0);
			clip.start();
		}
	}
	
	public void setToLoop() {
		//sets whether or not the sound effect loops
		clip.loop(clip.LOOP_CONTINUOUSLY);
	}
	
	public void setToLoud() {
		//sets volume to be loud
		volume = Volume.HIGH;
	}

}
