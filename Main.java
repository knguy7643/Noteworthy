package application;
	
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.control.Button;
import javafx.scene.text.Font;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

public class Main extends Application {
	
	private BorderPane root;
	
	// Private variables for each of the different panes.
	private Node browsePane;
	private Node libraryPane;
	private Node searchPane;
	private Node settingsPane;
	private Node newPlaylistPane;
	private Node songPane;
	
	// Private variables for the navigation bar.
	private Node navigationBar;
	private Button browseButton;
	private Button libraryButton;
	private Button searchButton;
	private Button settingsButton;
	
	// Private variables for the Library Pane
	private Button addPlayListButton;
	private Label libraryLabel;
	private Button newPLSubmit;
	private Label newPLLabel;
	private TextField newPLTextfield;
	private Button backToLibrary;
	private ListView<String> listLibrary;
	private Node playListPane;
	private ListView<String> listSongs;
	private Label playlistNameLabel;
	private Playlist selectedPlaylist;
	private Song selectedSong;
	
	// Private variable for the Song Pane
	private Label songNameLabel;
	private Button backToPlaylist;
	private Button backToSearch;
	private Button backToBrowse;
	private ImageView songImageView;
	private Button songSheetMusic;
	private Button songPlay;
	private Button songPause;
	private Button songNext;
	private Button songPrev;
	
	// Action Handler to deal with the user's inputs. 
	private EventHandler<ActionEvent> actionHandler;
	
	// TODO: Add other neededs variables. Delete uneeded variables. 
	private ArrayList<Playlist> playlistList;
	private Media media;
	private MediaPlayer mediaplayer;
	private MediaView mediaview;
	
// Methods Below. 
	
	@Override
	public void start(Stage primaryStage) {
		try {
			playlistList = loadPlayList("src/resources/playlist.txt");
			
			actionHandler = new ActionHandler();
	
			libraryPane = buildLibraryPane();
			searchPane = (new SearchPane()).buildSearchPane();
			//settingsPane = (new SettingsPane()).buildSettingsPane(); Marked grey since images in settings pane aren't in resource folder yet.
			newPlaylistPane = buildNewPLPane();
			
			root = new BorderPane();
			
			navigationBar = buildMenuBar();
			
			root.setBottom(navigationBar);
			root.setCenter(browsePane);
			
			Scene scene = new Scene(root,500,800);
			//scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm()); //Marked out since "application.css" wasn't present, thus causing a Null Pointer Exception.
			primaryStage.setScene(scene);
			primaryStage.setTitle("Noteworthy");
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		
		System.out.println("Starting up Noteworthy");
		
		launch(args);
	}
	
	public Node buildMenuBar() {
		
		HBox menuBar = new HBox();
		
		Font font = new Font(20);
		
		browseButton = new Button ("Browse");
		browseButton.setMaxSize(125, 75);
		browseButton.setMinSize(125, 75);
		browseButton.setOnAction(actionHandler);
		browseButton.setFont(font);
		
		libraryButton = new Button("Library");
		libraryButton.setMaxSize(125, 75);
		libraryButton.setMinSize(125, 75);
		libraryButton.setOnAction(actionHandler);
		libraryButton.setFont(font);
		
		searchButton = new Button("Search");
		searchButton.setMaxSize(125, 75);
		searchButton.setMinSize(125, 75);
		searchButton.setOnAction(actionHandler);
		searchButton.setFont(font);
		
		settingsButton = new Button("Settings");
		settingsButton.setMaxSize(125, 75);
		settingsButton.setMinSize(125, 75);
		settingsButton.setOnAction(actionHandler);
		settingsButton.setFont(font);
		
		menuBar.getChildren().addAll(browseButton, libraryButton, searchButton, settingsButton);
		
		return menuBar;
	}
	
	public Node buildLibraryPane() {
		libraryLabel = new Label("Your Library");
		libraryLabel.setFont(new Font("arial", 32));
		libraryLabel.setPrefSize(400, 75);
		libraryLabel.setMinSize(400, 75);
		libraryLabel.setMaxSize(400, 75);
		libraryLabel.setAlignment(Pos.CENTER);
		
		addPlayListButton = new Button("+");
		addPlayListButton.setFont(new Font(15));
		addPlayListButton.setMinSize(40, 40);
		addPlayListButton.setMaxSize(40, 40);
		addPlayListButton.setAlignment(Pos.CENTER);
		addPlayListButton.setOnAction(actionHandler);
		
		HBox topComponents = new HBox();
		
		topComponents.getChildren().add(libraryLabel);
		topComponents.getChildren().add(addPlayListButton);
		topComponents.setAlignment(Pos.CENTER);
		topComponents.setMaxSize(500, 100);
		topComponents.setMinSize(500, 100);
		
		BorderPane libraryPane = new BorderPane();
		
		libraryPane.setPrefSize(500, 725);
		libraryPane.setMinSize(500, 725);
		libraryPane.setMaxSize(500, 725);
		
		libraryPane.setTop(topComponents);
		
		listLibrary = new ListView<>();
		
		for (int i = 0; i < playlistList.size();i++) {
			listLibrary.getItems().add(playlistList.get(i).getPLName());
		}
		
		listLibrary.setMaxSize(450, 600);
		listLibrary.setMinSize(450, 600);
		
		listLibrary.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Object>() {

			@Override
			public void changed(ObservableValue<?> observable, Object oldValue, Object newValue) {
				System.out.println("User selected " + listLibrary.getSelectionModel().getSelectedItem() + " playlist.");
				
				playListPane = buildPlaylistPane(listLibrary.getSelectionModel().getSelectedItem());
				
				root.setCenter(playListPane);
			}
			
		});
		
		libraryPane.setCenter(listLibrary);
		
		return libraryPane;	
	}
	
	public Node buildPlaylistPane(String plName) {
		
		BorderPane songListPane = new BorderPane();
		
		playlistNameLabel = new Label(plName);
		playlistNameLabel.setFont(new Font("arial", 32));
		playlistNameLabel.setPrefSize(400, 75);
		playlistNameLabel.setMinSize(400, 75);
		playlistNameLabel.setMaxSize(400, 75);
		playlistNameLabel.setAlignment(Pos.CENTER);
		
		HBox topComponents = new HBox();
		topComponents.getChildren().add(backToLibrary);
		topComponents.getChildren().add(playlistNameLabel);
		topComponents.setAlignment(Pos.CENTER);
		topComponents.setMaxSize(500, 100);
		topComponents.setMinSize(500, 100);
		
		selectedPlaylist = new Playlist();
		
		for (int i = 0; i < playlistList.size(); i++) {
			if (plName.equalsIgnoreCase(playlistList.get(i).getPLName())) {
				selectedPlaylist = playlistList.get(i);
				break;
			}
		}
		
		listSongs = new ListView<>();
		
		for (int j = 0; j < selectedPlaylist.size(); j++) {
			listSongs.getItems().add(selectedPlaylist.getSong(j).getName());
		}
		
		listSongs.setMaxSize(450, 600);
		listSongs.setMinSize(450, 600);
		
		listSongs.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Object>() {

			@Override
			public void changed(ObservableValue<? extends Object> observable, Object oldValue, Object newValue) {
				System.out.println("User selected " + listSongs.getSelectionModel().getSelectedItem() + " song.");
				
				for (int i = 0; i < selectedPlaylist.size(); i++) {
					if (listSongs.getSelectionModel().getSelectedItem().equalsIgnoreCase(selectedPlaylist.getSong(i).getName())) {
						selectedSong = selectedPlaylist.getSong(i);
					}
				}
				
				songPane = buildSongPane(selectedSong, "Playlist");
				
				root.setCenter(songPane);
				
			}
			
		});
		
		songListPane.setCenter(listSongs);
		songListPane.setTop(topComponents);
		
		return songListPane;
	}
	
	public Node buildSongPane(Song song, String prevLoc) {
		
		Pane pane = new Pane();
		
		if (prevLoc.equalsIgnoreCase("Playlist")) {
			backToPlaylist = new Button("<-");
			backToPlaylist.setFont(new Font(15));
			backToPlaylist.setOnAction(actionHandler);
			backToPlaylist.relocate(10, 10);
			pane.getChildren().add(backToPlaylist);
		}
		else if (prevLoc.equalsIgnoreCase("Search")) {
			backToSearch = new Button("<-");
			backToSearch.setFont(new Font(15));
			backToSearch.setOnAction(actionHandler);
			backToSearch.relocate(10, 10);
			pane.getChildren().add(backToSearch);
		}
		else if (prevLoc.equalsIgnoreCase("Browse")) {
			backToBrowse = new Button("<-");
			backToBrowse.setFont(new Font(15));
			backToBrowse.setOnAction(actionHandler);
			backToBrowse.relocate(10, 10);
			pane.getChildren().add(backToBrowse);
		}
		
		String songImageFilename = "src/resources/" + selectedSong.getAlbum();
		
		Image songImage = new Image(new File(songImageFilename).toURI().toString());
		
		songImageView = new ImageView();
		songImageView.setImage(songImage);
		songImageView.relocate(125, 120);
		songImageView.setFitHeight(250);
		songImageView.setFitWidth(250);
		songImageView.setPreserveRatio(true);
		
		songNameLabel = new Label(selectedSong.getName());
		songNameLabel.setFont(new Font(25));
		songNameLabel.setMaxWidth(250);
		songNameLabel.setMinWidth(250);
		songNameLabel.setAlignment(Pos.CENTER);
		songNameLabel.relocate(125, 390);

		pane.getChildren().add(songImageView);
		pane.getChildren().add(songNameLabel);
		
		songPlay = new Button("|>");
		songPlay.setFont(new Font(15));
		songPlay.relocate(450, 450);
		songPlay.setOnAction(actionHandler);
		
		HBox songNav = new HBox();
		
		pane.getChildren().add(songPlay);
		
		return pane;
	}
	
	public void play(Song s) {
		
		media = new Media(new File("src/resources/" + selectedSong.getAudioFile()).toURI().toString());
		
		mediaplayer = new MediaPlayer(media);
		
		mediaplayer.setVolume(30);
		
		mediaview = new MediaView(mediaplayer);
		
		mediaplayer.play();
		
		System.out.println("Playing" + selectedSong.getName());
	}
	
	public Node buildNewPLPane() {
		Pane newPlaylistPane = new Pane();
		
		newPLSubmit = new Button("Submit");
		newPLSubmit.setFont(new Font(11));
		newPLSubmit.setOnAction(actionHandler);
		
		newPLLabel = new Label("Create A New Playlist");
		newPLLabel.setFont(new Font(30));
		newPLLabel.relocate(115, 300);
		
		newPLTextfield = new TextField("Enter New Playlist Name");
		newPLTextfield.setMinWidth(200);
		
		backToLibrary = new Button("<-");
		backToLibrary.setFont(new Font(15));
		backToLibrary.setOnAction(actionHandler);
		backToLibrary.relocate(10, 10);
		
		HBox textField = new HBox();
		textField.getChildren().add(newPLTextfield);
		textField.getChildren().add(newPLSubmit);
		textField.relocate(130, 340);
		
		newPlaylistPane.getChildren().add(newPLLabel);
		newPlaylistPane.getChildren().add(textField);
		newPlaylistPane.getChildren().add(backToLibrary);
		
		return newPlaylistPane;
	}
	
	private final class ActionHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent e) {
			Object source = e.getSource();
			
			if (source == browseButton) {
				System.out.println("User selected: Browse");
				
				root.setCenter(browsePane);
			}
			else if (source == libraryButton) {
				System.out.println("User selected: Library");
				
				root.setCenter(libraryPane);
			}
			else if (source == searchButton) {
				System.out.println("User selected: Search");
				
				root.setCenter(searchPane);
			}
			else if (source == settingsButton) {
				System.out.println("User selected: Settings");
				
				root.setCenter(settingsPane);
			}
			else if (source == addPlayListButton) {
				System.out.println("Open create playlist pane.");
				
				root.setCenter(newPlaylistPane);
			}
			else if (source == backToLibrary) {
				System.out.println("User selected: Library");
				
				root.setCenter(libraryPane);
			}
			else if (source == newPLSubmit) {
				System.out.println("Creating new playlist.");
				
				Playlist tempPL = new Playlist();
				
				tempPL.setName(newPLTextfield.getText());
				
				playlistList.add(tempPL);
				
				System.out.print(playlistList.get(2).getPLName());
				
				libraryPane = buildLibraryPane();
				
				root.setCenter(libraryPane);
			}
			else if (source == backToPlaylist) {
				System.out.println("Return to playlsit from song.");
				
				root.setCenter(playListPane);
			}
			else if (source == songPlay) {
				play(selectedSong);
			}
		}
	}
	
	public ArrayList<Playlist> loadPlayList(String filename) throws IOException {
		
		ArrayList<Playlist> playlistList = new ArrayList<Playlist>();
		
		BufferedReader input = new BufferedReader(new FileReader(filename));
		
		Playlist playlist;
		
		int numPlaylist = Integer.parseInt(input.readLine());
		
		for (int i = 0; i < numPlaylist; i++) {
			String plName = input.readLine();

			playlist = new Playlist();
			playlist.setName(plName);
			
			String songStringFormat = input.readLine();
			
			boolean run = true;
			
			if (songStringFormat.equalsIgnoreCase("/")) {
				run = false;
			}
			
			while (run == true) {
				
				String[] songInfo = songStringFormat.split("/");
				
				Song song = new Song(songInfo[0], songInfo[1], Integer.parseInt(songInfo[2]), songInfo[3], songInfo[4], songInfo[5], songInfo[6]);
				
				playlist.addSong(song);
				
				songStringFormat = input.readLine();
				
				if (songStringFormat.equalsIgnoreCase("/")) {
					run = false;
				}
				
			}
			
			playlistList.add(playlist);
			
		}
		
		input.close();
		
		return playlistList;
	}
	
	public void savePlayList(String filename) throws IOException {
		BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
		
		writer.write(Integer.toString(playlistList.size()));
		writer.newLine();
	
		for (int i = 0; i < playlistList.size(); i++) {
			
			writer.write(playlistList.get(i).getPLName());
			writer.newLine();
			
			for (int j = 0; j < playlistList.get(i).size(); j++) {
				Song song = playlistList.get(i).getSong(j);
				
				writer.write(song.getName() + "/" + song.getArtist() + "/" + Integer.toString(song.getRuntime()) + "/" + song.getGenre() + "/" + song.getAlbum() + "/" + song.getSheetMusic());
				writer.newLine();
			}
			writer.write("/");
			writer.newLine();
		}
		
		writer.close();
		
	}

}
