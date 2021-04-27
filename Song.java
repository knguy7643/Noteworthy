package application;

import javafx.scene.image.Image;

public class Song {

	public String name;
	
	public String artist;
	
	public int runtime;
	
	public String genre;
	
	public Image album;
	
	public Image sheetMusic;
	
	
	// Constructor
	public Song(String name, String artist, int runtime, String genre, Image album, Image sheetMusic) {
		
		this.name = name;
		this.artist = artist;
		this.genre = genre;
		this.album = album;
		this.sheetMusic = sheetMusic;
	}
}
