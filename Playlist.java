package application;

import java.util.List;

public class Playlist {

	private String name;
	
	private List<Song> playlist;
	
	private int runtime;
	
	public void updateName(String newName)
	{
		name = newName;
	}
	
	public String getName()
	{
		return name;
	}
	
	public void addSong(Song newSong)
	{
		for(int i = 0; i < playlist.size(); ++i)
		{
			if(playlist.get(i).equals(newSong))
			{
				break;
			}
		}
		playlist.add(newSong);
		runtime += newSong.getRuntime();
	}
	
	public void deleteSong(Song delSong)
	{
		for(int i = 0; i < playlist.size(); ++i)
		{
			if(playlist.get(i).equals(delSong))
			{
				playlist.remove(i);
				runtime -= delSong.getRuntime();
			}
		}
	}
	
	public Song getSong(int i)
	{
		return playlist.get(i);
	}
	
	public List<Song> getPlaylist()
	{
		return playlist;
	}
	
	public int getRuntime()
	{
		return runtime;
	}
}
