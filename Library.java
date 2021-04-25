package application;

import java.util.List;

public class Library {

	private List<Song> library;
	
	public void addSong(Song newSong)
	{
		for(int i = 0; i < library.size(); ++i)
		{
			if(library.get(i).equals(newSong))
			{
				break;
			}
		}
		library.add(newSong);
	}
	
	public void deleteSong(Song delSong)
	{
		for(int i = 0; i < library.size(); ++i)
		{
			if(library.get(i).equals(delSong))
			{
				library.remove(i);
			}
		}
	}
}
