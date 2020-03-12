package org.snailysis.audio;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class Music {
	
	private static String uri;
	private static MediaPlayer media;
	
	private static void loadMusic() throws LineUnavailableException, IOException, UnsupportedAudioFileException {
		
		//file = new File("res/org/snailysis/music/MusicBackGround.wav");
		//clip  = AudioSystem.getClip();
		//clip.open(AudioSystem.getAudioInputStream(file));
	        uri =  Music.class.getResource("/org/snailysis/music/ArcadeMusicBackGround.mp3").toString();
		media = new MediaPlayer(new Media(uri));
		media.setCycleCount(MediaPlayer.INDEFINITE);
	}
	
	public static void setOffMusic() {
		//clip.close();
		media.stop();
	}
	
	public static void setOnMusic() throws LineUnavailableException, IOException, UnsupportedAudioFileException {
		Music.loadMusic();
		//clip.start();
		media.play();
	}

}
