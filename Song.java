package application;

import javafx.scene.image.Image;

public class Song {

	private String name;
	
	private String artist;
	
	private int runtime;
	
	private String genre;
	
	private Image albumCover;
	
	private Image sheetMusic;
	
	public void updateName(String newName)
	{
		name = newName;
	}
	
	public String getName()
	{
		return name;
	}
	
	public void updateArtist(String newArtist)
	{
		artist = newArtist;
	}
	
	public String getArtist()
	{
		return artist;
	}
	
	public void updateRuntime(int newRuntime)
	{
		runtime = newRuntime;
	}
	
	public int getRuntime()
	{
		return runtime;
	}
	
	public void updateGenre(String newGenre)
	{
		genre = newGenre;
	}
	
	public String getGenre() 
	{
		return genre;
	}
	
	public void updateAlbum (Image newAlbum)
	{
		albumCover = newAlbum;
	}
	
	public Image getAlbumCover()
	{
		return albumCover;
	}
	
	public void updateSheetMusic(Image newMusic)
	{
		sheetMusic = newMusic;
	}
	
	public Image getSheetMusic()
	{
		return sheetMusic;
	}
}
