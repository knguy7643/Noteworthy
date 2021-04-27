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

	// Getters
	public String getName() {
		
		return name;
	}

	public String getArtist() {
		
		return artist;
	}
	
	public int getRuntime() {
		
		return runtime;
	}
	
	public String getGenre() {
		
		return genre;
	}
	
	public Image getAlbum() {
		
		return album;
	}
	
	public Image getSheetMusic() {
		
		return sheetMusic;
	}
	
	// Setters
	public void setName(String name) {
		
		this.name = name;
	}

	public void setArtist(String artist) {
		
		this.artist = artist;
	}

	public void setRuntime(int runtime) {
		
		this.runtime = runtime;
	}

	public void setGenre(String genre) {
		
		this.genre = genre;
	}

	public void setAlbum(Image album) {
		
		this.album = album;
	}

	public void setSheetMusic(Image sheetMusic) {
		
		this.sheetMusic = sheetMusic;
	}
}
