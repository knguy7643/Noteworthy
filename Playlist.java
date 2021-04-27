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
}
