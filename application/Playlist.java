package application;

import java.util.ArrayList;

public class Playlist {

	public String name;
	
	public ArrayList<Song> playlist;
	
	public int runtimeTotal;
	
	//Constructor
	public Playlist(ArrayList<Song> playlist, int runtimeTotal) {
		
		this.playlist = playlist;
		this.runtimeTotal = runtimeTotal;
	}
	
	public Playlist() {
		this.name = "";
		this.playlist = new ArrayList<Song>();
		this.runtimeTotal = 0;
	}
	
	public void setName(String s) {
		this.name = s;
	}
	
	public void addSong(Song song) {
		playlist.add(song);
		runtimeTotal = runtimeTotal + song.getRuntime();
	}
	
	public String getPLName() {
		return name;
	}
	
	public Song getSong(int i) {
		return playlist.get(i);
	}
	
	public int size() {
		return playlist.size();
	}
	
	public int getIndex(Song s) {
		
		int temp = -1;
		
		for (int i = 0; i < playlist.size();i++) {
			if (playlist.get(i).getName().equalsIgnoreCase(s.getName())) {
				temp = i;
			}
		}
		
		return temp;
	}
}
