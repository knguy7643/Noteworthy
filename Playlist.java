package application;

public class Playlist {

	public String name;
	
	public Song[] playlist;
	
	public int runtimeTotal;
	
	//Constructor
	public Playlist(Song[] playlist, int runtimeTotal) {
		
		this.playlist = playlist;
		this.runtimeTotal = runtimeTotal;
	}

	// Getters
	public String getName() {
		return name;
	}

	public Song[] getPlaylist() {
		return playlist;
	}

	public int getRuntimeTotal() {
		return runtimeTotal;
	}

	// Setters
	public void setName(String name) {
		this.name = name;
	}

	public void setPlaylist(Song[] playlist) {
		this.playlist = playlist;
	}

	public void setRuntimeTotal(int runtimeTotal) {
		this.runtimeTotal = runtimeTotal;
	}
	
	
}
