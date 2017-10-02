package game;

import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class MusicPlayer {

	private final String BG2 = "lib/audio/shapeofyou_001.wav";
	private final String EXPLOSION = "lib/audio/explosion_001.wav";
	private final String DEATH = "lib/audio/death_002.wav";
	private final String COLLECT = "lib/audio/collect_001.wav";
	
	public void playSound(String fileName) {
	    try {
	        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(fileName).getAbsoluteFile());
	        Clip clip = AudioSystem.getClip();
	        clip.open(audioInputStream);
	        clip.start();
	    } catch(Exception ex) {
	        System.out.println("Error with playing sound.");
	        ex.printStackTrace();
	    }
	}
	
	public void playSoundLooped(String fileName) {
		Thread musicThread = new Thread() {
			public void run() {
			    try {
			        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(fileName).getAbsoluteFile());
			        Clip clip = AudioSystem.getClip();
			        clip.open(audioInputStream);
			        clip.loop(10);
			    } catch(Exception ex) {
			        System.out.println("Error with playing sound.");
			        ex.printStackTrace();
			    }
			}
		};
		musicThread.start();
	}
	
	public void playBackgroundMusic() {
		playSoundLooped(BG2);
	}
	
	public void playExplosion() {
		playSound(EXPLOSION);
	}
	
	public void playCollection() {
		playSound(COLLECT);
	}
	
	public void playDeath() {
		playSound(DEATH);
	}
	
}
