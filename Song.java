package application;

import javafx.scene.image.Image;

public class Song {

	public String name;
	
	public String artist;
	
	public int runtime;
	
	public String genre;
	
	public String albumImageFileName;
	
	public String sheetMusicFileName;
	
	
	// Constructor
	public Song(String name, String artist, int runtime, String genre, String album, String sheetMusic) {
		
		this.name = name;
		this.artist = artist;
		this.genre = genre;
		this.albumImageFileName = album;
		this.sheetMusicFileName = sheetMusic;
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


		public String getAlbum() {
			return albumImageFileName;
		}


		public String getSheetMusic() {
			return sheetMusicFileName;
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


		public void setAlbum(String album) {
			this.albumImageFileName = album;
		}


		public void setSheetMusic(String sheetMusic) {
			this.sheetMusicFileName = sheetMusic;
		}
		
	
}
