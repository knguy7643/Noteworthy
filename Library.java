package application;

import java.util.List;

public class Library {
	
	public String title;
	public String albumImageFileName;
	public Song[] setList;
	
	public Library(List<String> item) {
		
		title = new String(item.get(0));
		albumImageFileName = new String(item.get(1));
	}
	public String getTitle() {
		return title;
	}
	public String getAlbumImage() {
		return albumImageFileName;
	}
	public Song[] getSongList() {
		return setList;
	}


	public void setTitle(String title) {
		this.title = title;
	}
	public void setAlbumImage(String image) {
		this.albumImageFileName = image;
	}
	public void setSetList(Song[] list) {
		this.setList = list;
	}
}
