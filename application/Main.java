package application;
	
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.function.Predicate;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.text.Font;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.TableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import javafx.scene.paint.Color;
import javafx.scene.text.FontWeight;
import javafx.geometry.Insets;
import javafx.scene.layout.FlowPane;
import javafx.geometry.Orientation;
import javafx.scene.control.Slider;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.application.Platform;

public class Main extends Application {
	
	private BorderPane root;
	
	// The browse pane is one of the main four panes (1/4) in the application and consists of two tables of songs, "Recently Played" and "New Releases".
	private Node paneBrowse;
	// The library pane is one of the main four panes (2/4) in the application and consists of a table that displays a list of the user's playlist and a button to create a new playlist.
	private Node paneLibrary;
	// The Create Playlist pane is utilized when the user is trying to create a new playlist.
	private Node paneCreatePlaylist;
	// The Search pane is one of the main four panes (3/4) in the application that will allow the user to search songs by name or artist name.
	private Node paneSearch;
	// The Settings pane is one of the main four panes (4/4) in the application that will provide the user with "Account/Privacy", "Connected Devices", and "About" information. 
	private Node paneSettings;
	// The Song pane is built to display the song's poster, song's name, song's progress bar, song navigation buttons, and song's sheet music button for the currently selected song.
	private Node paneSong;
	// The Song's Sheet Music Pane will display the sheet music for the currently playing song. 
	private Node paneSongSheetMusicView;
	// The Playlist pane is build to display the list of songs in the playlist 
	private Node panePlaylist;
	
	
	
	// The Navigation Bar will hold the buttons that will allow the user to move between the main four panes and will sit at the bottom of the window. 
	private Node navigationBar;
	// The Browse button lies in the navigation bar and changes the user's view to the browse pane.
	private Button navBarBrowse;
	// The Library button lies in the navigation bar and changes the user's view to the library pane.
	private Button navBarLibrary;
	// The Search button lies in the navigation bar and changes the user's view to the search pane.
	private Button searchButton;
	// The Settings button lies in the navigation bar and changes the user's view to the settings pane. 
	private Button settingsButton;
	// This navigation button will send the user back to the Browse pane.
	private Button backToBrowse;
	// This navigation button will send the user back to the Library pane.
	private Button backToLibrary;
	// This navigation button will send the user back to the Playlist pane.
	private Button backToPlaylist;
	// This navigation button will send the user back to the Search pane.
	private Button backToSearch;
	// This navigation button will send the user back to the Song pane.
	private Button backToSong;
	
	
	
	// A label to indicate to the user that they are currently in the Library pane. 
	private Label libraryPaneLabel;
	// The "Add Playlist" button will be used to display the create playlist pane.
	private Button createPlaylistButton;
	// The "Submit" button to take the user's input and creates a new playlist with the input as the playlist's name.
	private Button createPlaylistSubmitButton;
	// A label to prompt the user to create a new playlist.
	private Label createPlaylistLabel;
	// A text field to take the user's input for new playlist name.
	private TextField createPlaylistTextfield;
	// A label for the name of the currently displayed playlist.
	private Label playlistNameLabel;
	// The list of playlist stored by the library.
	private ListView<String> listLibrary;
	// The ArrayList of playlist in the library.
	private ArrayList<Playlist> playlistList;
	// Stoes a list of songs in a playlist.
	private ListView<String> listSongs;
	
	
	
	// A button in the settings pane to allow the user to "Log Out".
	private Button settingsLogOut;
	// The arrows to expand and display info for "Account/Privacy", "Connected Devices", and "About".
	private ImageView settingsArrow1, settingsArrow2, settingsArrow3;
	// A label to indicate to the user that they are currently in the Settings pane.
	private Label settingsLabel;
	// A label to indicate the section related to "Account/Privacy" information.
	private Label settingsAccountLabel;
	// A label to indicate the section related to "Connected Devices" information.
	private Label settingsConnectLabel;
	// A label to indicate the section related to "About" information.
	private Label settingsAboutLabel;
	
	
	
	// A  label to indicate to the user that they are in the Browse pane.
	private Label browseLabel;
	// A label for the "Recently Played" chart of songs. 
	private Label browseRecentlyPlayed;
	// A label for the "New Releases" chart of songs.
	private Label browseNewRelease;
	
	
	
	// An interactable list of songs. 
	private ObservableList<Song> songlist = FXCollections.observableArrayList();
	// A button to update the table based on the search string.
	private Button searchGoButton;
	// The table of searched songs.
	private TableView<Song> searchTable;
	// A text field where the user can input the string that they would like to use to search for songs or songs by an artist whose name containts the inputted string.
	private TextField searchBox;
	
	
	
	// A button to open the window with the song's sheet music.
	private Button songSheetMusic;
	// A button to begin playing the song. Known Bug: Pressing "Play" while the song is play will cause the song to start playing from the beginning. 
	private Button songPlay;
	// A button to pause the playing song.
	private Button songPause;
	// A button to move to the next song in the playlist and automatically start playing it. If this song is the last song in the playlist, it will move to the first song in the playlist.
	private Button songNext;
	// A button to move to the previous song in the playlist and automatically start playing it. If this song is the first song in the playlist, it will move to the last song in the playlist.
	private Button songPrev;
	// The ImageView object to display the song's poster iamge.
	private ImageView songImageView;
	// The ImageView object to display the song's music sheet as an image. 
	private ImageView songSheetView;
	// The label to display the name of the song.
	private Label songNameLabel;
	// The song's progress play bar.
	private Slider songTime;
	
	
	
	// Variable that should be stored using a controller object.
	private Playlist selectedPlaylist;
	private Song selectedSong;

	
	
	// The pane that prompts the user to select a playlist to add the song to.
	private Node addToPlaylistPane;
	
	
	
	// Action Handler to deal with the user's inputs from buttons. 
	private EventHandler<ActionEvent> actionHandler;
	
	
	
	// Private variables for the objectilized for the playing of music.
	private Media media;
	private MediaPlayer mediaplayer;
	
// Methods Below. 
	
	// Method starts and build the various components of our application.
	@Override
	public void start(Stage primaryStage) {
		try {
			
			// Load in the user's existing playlist library.
			playlistList = loadPlayList("src/resources/playlist.txt");
			
			// Builds the action handler to manage the application's various buttons.
			actionHandler = new ActionHandler();
	
			// Build the application main four panes.
			paneBrowse = buildBrowsePane();
			paneLibrary = buildLibraryPane();
			paneSearch = buildSearchPane();
			paneSettings = buildSettingsPane();
			
			// Build the pane for when the user seeks to create a new playlist.
			paneCreatePlaylist = buildNewPLPane();
			
			// Build application's library of songs.
			songlist = buildSongList();
			
			// This variable will serves as the parent node of our application.
			root = new BorderPane();
			
			// Build the navigation bar that will allow the user to move between the main four panes.
			navigationBar = buildMenuBar();
			
			// Set the nagivation bar at the bottom of the window and load in the browse pane to act as the home screen.
			root.setBottom(navigationBar);
			root.setCenter(paneBrowse);
			
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
	
	/* This method is used to build the navigation bar that will allow the user to move between brwosing, searching, or their own library.
	 * Written by Khoa-chau Nguyen
	 */
	public Node buildMenuBar() {
		
		// Utilizes an HBox to have the four navigation pane buttons horizontally aligned. 
		HBox menuBar = new HBox();
		
		// Create a font to maintain consistency accross the 4 navigation bar buttons.
		Font font = new Font(20);
		
		// Build the navigation bar's Browse pane button. 
		navBarBrowse = new Button ("Browse");
		navBarBrowse.setMaxSize(125, 75);
		navBarBrowse.setMinSize(125, 75);
		navBarBrowse.setOnAction(actionHandler);
		navBarBrowse.setFont(font);
		
		// Build the navigation bar's Library pane button. 
		navBarLibrary = new Button("Library");
		navBarLibrary.setMaxSize(125, 75);
		navBarLibrary.setMinSize(125, 75);
		navBarLibrary.setOnAction(actionHandler);
		navBarLibrary.setFont(font);
		
		// Build the navigation bar's Search pane button.
		searchButton = new Button("Search");
		searchButton.setMaxSize(125, 75);
		searchButton.setMinSize(125, 75);
		searchButton.setOnAction(actionHandler);
		searchButton.setFont(font);
		
		// Build the navigation bar's Settings pane button.
		settingsButton = new Button("Settings");
		settingsButton.setMaxSize(125, 75);
		settingsButton.setMinSize(125, 75);
		settingsButton.setOnAction(actionHandler);
		settingsButton.setFont(font);
		
		// Add the navigation buttons to the bar in the order of Browse -> Library -> Search -> Settings.
		menuBar.getChildren().addAll(navBarBrowse, navBarLibrary, searchButton, settingsButton);
		
		return menuBar;
	}
	
	/* This method is used to build the library pane that will display all of the user's playlist with the option to create new playlists.
	 * Written by Khoa-chau Nguyen
	 */
	public Node buildLibraryPane() {
		
		// Build a label to indicate that this is the user's library.
		libraryPaneLabel = new Label("Your Library");
		libraryPaneLabel.setFont(new Font("arial", 32));
		libraryPaneLabel.setPrefSize(400, 75);
		libraryPaneLabel.setMinSize(400, 75);
		libraryPaneLabel.setMaxSize(400, 75);
		libraryPaneLabel.setAlignment(Pos.CENTER);
		
		// Build the button for the ability for the user to create a new playlist.
		createPlaylistButton = new Button("+");
		createPlaylistButton.setFont(new Font(15));
		createPlaylistButton.setMinSize(40, 40);
		createPlaylistButton.setMaxSize(40, 40);
		createPlaylistButton.setAlignment(Pos.CENTER);
		createPlaylistButton.setOnAction(actionHandler);
		
		// Use an HBox to store the label and button components. 
		HBox topComponents = new HBox();
		
		topComponents.getChildren().add(libraryPaneLabel);
		topComponents.getChildren().add(createPlaylistButton);
		topComponents.setAlignment(Pos.CENTER);
		topComponents.setMaxSize(500, 100);
		topComponents.setMinSize(500, 100);
		
		// Construct a pane to display the list of playlist in the library. 
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
		
		// Gives the playlist objects in the library the ability when selected to open the playlist and display a list of songs in the playlist.
		listLibrary.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Object>() {

			@Override
			public void changed(ObservableValue<?> observable, Object oldValue, Object newValue) {
				System.out.println("User selected " + listLibrary.getSelectionModel().getSelectedItem() + " playlist.");
				
				panePlaylist = buildPlaylistPane(listLibrary.getSelectionModel().getSelectedItem());
				
				root.setCenter(panePlaylist);
			}
			
		});
		
		libraryPane.setCenter(listLibrary);
		
		return libraryPane;	
	}
	
	/* This method is used to build the settings pane that will allow the user to check various information regarding the application.
	 * Written by Gretchen Hollrah
	 */
	public Node buildSettingsPane() {
		
		// Declare fonts in advance for consistency in usage.
		Font btnFont = Font.font("arial", FontWeight.BOLD, 18.0);
		Font lblFont = Font.font("arial", 22.0);
		
		// Build label to indicate that this is the Settings pane.
		settingsLabel = new Label("Settings");
		settingsLabel.setFont(new Font("arial", 32));
		settingsLabel.setPrefSize(400,  75);
		
		// Build label for the "Account/Privacy Settings" section.
		settingsAccountLabel = new Label("Account/Privacy Settings");
		settingsAccountLabel.setFont(lblFont);
		settingsAccountLabel.setPrefHeight(75);
		
		// Build label for the "Connected Devices" section.
		settingsConnectLabel = new Label("Connected Devices");
		settingsConnectLabel.setFont(lblFont);
		settingsConnectLabel.setPrefHeight(75);
		
		// Build label for the "About" section.
		settingsAboutLabel = new Label("About");
		settingsAboutLabel.setFont(lblFont);
		settingsAboutLabel.setPrefHeight(75);
		
		// Build Log Out button.
		settingsLogOut = new Button("Log Out");
		settingsLogOut.setPrefSize(100, 60);
		settingsLogOut.setFont(btnFont);
		settingsLogOut.setOnAction(actionHandler);		
		
		settingsArrow1 = new ImageView(new Image(new File("src/resources/arrow.png").toURI().toString(), 30, 30, true, true));
		settingsArrow2 = new ImageView(new Image(new File("src/resources/arrow.png").toURI().toString(), 30, 30, true, true));
		settingsArrow3 = new ImageView(new Image(new File("src/resources/arrow.png").toURI().toString(), 30, 30, true, true));
		
		settingsArrow1.setFitHeight(40);
		settingsArrow1.setFitWidth(40);
		settingsArrow2.setFitHeight(40);
		settingsArrow2.setFitWidth(40);
		settingsArrow3.setFitHeight(40);
		settingsArrow3.setFitWidth(40);
		
		// Set alignment of section labels and arrows.
		HBox row1 = new HBox(192);
		row1.getChildren().add(settingsAccountLabel);
		row1.getChildren().add(settingsArrow1);
		row1.setAlignment(Pos.CENTER_LEFT);
		
		HBox row2 = new HBox(270);
		row2.getChildren().add(settingsConnectLabel);
		row2.getChildren().add(settingsArrow2);
		row2.setAlignment(Pos.CENTER_LEFT);
		
		HBox row3 = new HBox(380);
		row3.getChildren().add(settingsAboutLabel);
		row3.getChildren().add(settingsArrow3);
		row3.setAlignment(Pos.CENTER_LEFT);
			
		VBox selections = new VBox(10);
		selections.getChildren().add(row1);
		selections.getChildren().add(row2);
		selections.getChildren().add(row3);

		FlowPane pane = new FlowPane(Orientation.VERTICAL);
		pane.getChildren().add(settingsLabel);
		FlowPane.setMargin(settingsLabel, new Insets(10, 20, 20, 200));
		
		pane.setPrefSize(500, 725);
		pane.setMinSize(500,  725);
		pane.setMaxSize(500,  725);
		
		pane.getChildren().add(selections);
		FlowPane.setMargin(selections, new Insets(10, 10, 10, 10));
		
		pane.getChildren().add(settingsLogOut);
		FlowPane.setMargin(settingsLogOut, new Insets(20, 20, 20, 200));


		return pane;
		
	}
	
	/* This method is used to build the browse pane that acts as the home page of the application and will present the user with a list of recently played and newly released songs.
	 * Written by Gretchen Hollrah
	 */
	@SuppressWarnings("unchecked")
	public Node buildBrowsePane() throws IOException {
		
		// Build "Recently Played" table.
		TableView<Song> playRecent = new TableView<Song>();
		playRecent.setPrefSize(450, 200);
		TableColumn<Song, String> rSongs = new TableColumn<Song, String>("Song");
		rSongs.setMinWidth(450/3);
		rSongs.setCellValueFactory(new PropertyValueFactory<Song, String>("name"));
		TableColumn<Song, String> rArtist = new TableColumn<Song, String>("Artist");
		rArtist.setMinWidth(450/3);
		rArtist.setCellValueFactory(new PropertyValueFactory<Song, String>("artist"));
		TableColumn<Song, String> rAlbum = new TableColumn<Song, String>("Album");
		rAlbum.setMinWidth(450/3);
		rAlbum.setCellValueFactory(new PropertyValueFactory<Song, String>("album"));
		
		playRecent.setItems(songlist);
		playRecent.getColumns().addAll(rSongs, rArtist, rAlbum);
		
		playRecent.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Object>() {
			
			@Override
			public void changed(ObservableValue<?> observable, Object oldValue, Object newValue) {
				if (playRecent.getSelectionModel().getSelectedItem() != null) {
					selectedSong = playRecent.getSelectionModel().getSelectedItem();
					System.out.println("User selected " + playRecent.getSelectionModel().getSelectedItem().getName());
					paneSong = buildSongPane(selectedSong, "Browse");
					root.setCenter(paneSong);
				}
			}
		
		});
		
		// Build "New Releases" table.
		TableView<Song> newReleases = new TableView<Song>();
		newReleases.setPrefSize(450, 200);
		TableColumn<Song, String> nSongs = new TableColumn<Song, String>("Song");
		nSongs.setMinWidth(450/3);
		nSongs.setCellValueFactory(new PropertyValueFactory<Song, String>("name"));
		TableColumn<Song, String> nArtist = new TableColumn<Song, String>("Artist");
		nArtist.setMinWidth(450/3);
		nArtist.setCellValueFactory(new PropertyValueFactory<Song, String>("artist"));
		TableColumn<Song, String> nAlbum = new TableColumn<Song, String>("Album");
		nAlbum.setMinWidth(450/3);
		nAlbum.setCellValueFactory(new PropertyValueFactory<Song, String>("album"));
		newReleases.setItems(songlist);
		newReleases.getColumns().addAll(nSongs, nArtist, nAlbum);
		
		newReleases.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Object>() {
			
			@Override
			public void changed(ObservableValue<?> observable, Object oldValue, Object newValue) {
				if (newReleases.getSelectionModel().getSelectedItem() != null) {
					selectedSong = newReleases.getSelectionModel().getSelectedItem();
					System.out.println("User selected " + newReleases.getSelectionModel().getSelectedItem().getName());
					paneSong = buildSongPane(selectedSong, "Browse");
					root.setCenter(paneSong);
				}
			}
		
		});
		
		// Build label to indicate that this is the Browse pane.
		browseLabel = new Label("Browse");
		browseLabel.setFont(new Font("arial", 32));
		browseLabel.setPrefSize(400, 50);
		browseLabel.setAlignment(Pos.CENTER);
		
		// Build label for the "Recently Played" table.
		browseRecentlyPlayed = new Label("Recently Played");
		browseRecentlyPlayed.setFont(new Font("arial", 20));
		browseRecentlyPlayed.setPrefSize(400, 50);
		
		// Build label for the "New Releases" table.
		browseNewRelease = new Label("New Releases");
		browseNewRelease.setFont(new Font("arial", 20));
		browseNewRelease.setPrefSize(400, 50);
		
		ScrollPane row1 = new ScrollPane();
		ScrollPane row2 = new ScrollPane();
		ScrollPane pane = new ScrollPane();
		
		pane.setPrefSize(450, 600);
		
		row1.setPrefSize(450, 200);
		row1.setHbarPolicy(ScrollBarPolicy.NEVER);
		row2.setPrefSize(450, 200);
		row2.setHbarPolicy(ScrollBarPolicy.NEVER);
		
		row1.setContent(playRecent);
		row2.setContent(newReleases);
		
		VBox vbox = new VBox();
		vbox.getChildren().add(browseLabel);
		VBox.setMargin(browseLabel, new Insets(10, 10, 10, 10));
		vbox.getChildren().add(browseRecentlyPlayed);
		VBox.setMargin(browseRecentlyPlayed, new Insets(10, 10, 10, 10));
		vbox.getChildren().add(row1);
		VBox.setMargin(row1, new Insets(10, 10, 10, 10));
		vbox.getChildren().add(browseNewRelease);
		VBox.setMargin(browseNewRelease, new Insets(10, 10, 10, 10));
		vbox.getChildren().add(row2);
		VBox.setMargin(row2, new Insets(10, 10, 10, 10));
		
		pane.setContent(vbox);
		
		return pane;
	
		
	}
	
	/* This method is used to build the playlist pane that displays the songs contained by the playlist.
	 * Written by Khoa-chau Nguyen
	 */
	public Node buildPlaylistPane(String playlistName) {
		
		BorderPane songListPane = new BorderPane();
		
		// Build label to display the name of the given playlist.
		playlistNameLabel = new Label(playlistName);
		playlistNameLabel.setFont(new Font("arial", 32));
		playlistNameLabel.setPrefSize(400, 75);
		playlistNameLabel.setMinSize(400, 75);
		playlistNameLabel.setMaxSize(400, 75);
		playlistNameLabel.setAlignment(Pos.CENTER);
		
		// Utilize an HBox to store the label for the playlist's name and the navigation button to return to the Library pane. 
		HBox topComponents = new HBox();
		topComponents.getChildren().add(backToLibrary);
		topComponents.getChildren().add(playlistNameLabel);
		topComponents.setAlignment(Pos.CENTER);
		topComponents.setMaxSize(500, 100);
		topComponents.setMinSize(500, 100);
		
		// Sets the selected playlist as the playlist whose name matches the given playlist name. This could be removed by the presence of a controller to maintain the selected playlist. 
		selectedPlaylist = new Playlist();
		
		for (int i = 0; i < playlistList.size(); i++) {
			if (playlistName.equalsIgnoreCase(playlistList.get(i).getPLName())) {
				selectedPlaylist = playlistList.get(i);
				break;
			}
		}
		
		// Build the list of song from the songs in the selected playlist.
		listSongs = new ListView<>();
		
		for (int j = 0; j < selectedPlaylist.size(); j++) {
			listSongs.getItems().add(selectedPlaylist.getSong(j).getName());
		}
		
		listSongs.setMaxSize(450, 600);
		listSongs.setMinSize(450, 600);
		
		// Makes the list of song objects interactable. When selected, a song pane with the selected song should open. 
		listSongs.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Object>() {

			@Override
			public void changed(ObservableValue<? extends Object> observable, Object oldValue, Object newValue) {
				System.out.println("User selected " + listSongs.getSelectionModel().getSelectedItem() + " song.");
				
				for (int i = 0; i < selectedPlaylist.size(); i++) {
					if (listSongs.getSelectionModel().getSelectedItem().equalsIgnoreCase(selectedPlaylist.getSong(i).getName())) {
						selectedSong = selectedPlaylist.getSong(i);
					}
				}
				
				paneSong = buildSongPane(selectedSong, "Playlist");
				
				root.setCenter(paneSong);
				
			}
			
		});
		
		// Sets positioning of playlist pane's components.
		songListPane.setCenter(listSongs);
		songListPane.setTop(topComponents);
		
		return songListPane;
	}
	
	/* This method is used to build the song pane where the user can play/pause songs and open the song's sheet music.
	 * Written by Khoa-chau Nguyen
	 */
	public Node buildSongPane(Song song, String prevLoc) {
		
		Pane pane = new Pane();
		
		// Depending on how the user reaches the song pane, it will apply a back button to return to the pane prior to arriving at the song pane.
		if (prevLoc.equalsIgnoreCase("Playlist")) { // Sends the user back to the playlist.
			backToPlaylist = new Button("<-");
			backToPlaylist.setFont(new Font(15));
			backToPlaylist.setOnAction(actionHandler);
			backToPlaylist.relocate(10, 10);
			pane.getChildren().add(backToPlaylist);
		}
		else if (prevLoc.equalsIgnoreCase("Search")) { // Sends the user back to the Search pane.
			backToSearch = new Button("<-");
			backToSearch.setFont(new Font(15));
			backToSearch.setOnAction(actionHandler);
			backToSearch.relocate(10, 10);
			pane.getChildren().add(backToSearch);
		}
		else if (prevLoc.equalsIgnoreCase("Browse")) { // Sends the user back to the Browse pane.
			backToBrowse = new Button("<-");
			backToBrowse.setFont(new Font(15));
			backToBrowse.setOnAction(actionHandler);
			backToBrowse.relocate(10, 10);
			pane.getChildren().add(backToBrowse);
		}
		
		// File path for the song's poster. 
		String songImageFilename = "src/resources/" + selectedSong.getAlbum();
		
		Image songImage = new Image(new File(songImageFilename).toURI().toString());
		
		// Build the song's poster object. 
		songImageView = new ImageView();
		songImageView.setImage(songImage);
		songImageView.relocate(125, 120);
		songImageView.setFitHeight(250);
		songImageView.setFitWidth(250);
		songImageView.setPreserveRatio(true);
		
		// Build the label for the song's name.
		songNameLabel = new Label(selectedSong.getName());
		songNameLabel.setFont(new Font(25));
		songNameLabel.setMaxWidth(250);
		songNameLabel.setMinWidth(250);
		songNameLabel.setAlignment(Pos.CENTER);
		songNameLabel.relocate(125, 420);
		
		// Build the song's play button.
		songPlay = new Button("|>");
		songPlay.setFont(new Font(15));
		songPlay.setOnAction(actionHandler);
		
		// Build the song's pause button.
		songPause = new Button("||");
		songPause.setFont(new Font(15));
		songPause.setOnAction(actionHandler);
		
		// Build the song's previous song button.
		songPrev = new Button("<<");
		songPrev.setFont(new Font(15));
		songPrev.setOnAction(actionHandler);
		
		// Build the song's next song button.
		songNext = new Button(">>");
		songNext.setFont(new Font(15));
		songNext.setOnAction(actionHandler);
		
		// Build the song's sheet music button.
		songSheetMusic = new Button("Sheet Music");
		songSheetMusic.setFont(new Font(15));
		songSheetMusic.setOnAction(actionHandler);
		
		// Add the song's buttons to form a navigation bar for the song.
		HBox songNav = new HBox(10);
		songNav.getChildren().add(songPrev);
		songNav.getChildren().add(songPlay);
		songNav.getChildren().add(songPause);
		songNav.getChildren().add(songNext);
		songNav.getChildren().add(songSheetMusic);
		songNav.relocate(115, 550);
		songNav.setMaxWidth(300);
		songNav.setMinWidth(300);
		songNav.setMaxHeight(40);
		songNav.setMinHeight(40);
		
		// Build the song's progress bar. 
		songTime = new Slider();
		songTime.relocate(115, 475);
		songTime.setMaxWidth(300);
		songTime.setMinWidth(300);
		songTime.setMaxHeight(40);
		songTime.setMinHeight(40);
	
		pane.getChildren().add(songImageView);
		pane.getChildren().add(songNameLabel);
		pane.getChildren().add(songNav);
		pane.getChildren().add(songTime);
		
		return pane;
	}
	
	/* This method is used to build the pane that will display the selected song's sheet music.
	 * Written by Khoa-chau Nguyen
	 */
	public Node buildSongSheetMusicPane(Song s) {
		Pane sheetPane = new Pane();
		
		// Build the button to send the user back to the song's song pane. 
		backToSong = new Button("<-");
		backToSong.setFont(new Font(15));
		backToSong.setOnAction(actionHandler);
		backToSong.relocate(10, 10);
		
		songSheetView = new ImageView();
		
		// File path for the song's sheet music.
		String songSheetFilename = "src/resources/" + selectedSong.getSheetMusic();
		
		Image songSheetImage = new Image(new File(songSheetFilename).toURI().toString());
		
		songSheetView = new ImageView();
		songSheetView.setImage(songSheetImage);
		songSheetView.relocate(10, 50);
		songSheetView.setFitHeight(600);
		songSheetView.setFitWidth(480);
		
		sheetPane.getChildren().add(backToSong);
		sheetPane.getChildren().add(songSheetView);
		
		return sheetPane;
	}
	
	/* This method is used to build the pane where the user will be asked the name for their new playlist.
	 * Written by Khoa-chau Nguyen
	 */
	public Node buildNewPLPane() {
		Pane playlistPane = new Pane();
		
		// Build the submit button for creating a new playlist.
		createPlaylistSubmitButton = new Button("Submit");
		createPlaylistSubmitButton.setFont(new Font(11));
		createPlaylistSubmitButton.setOnAction(actionHandler);
		
		// Build label to indicate the user is creating a new playlist.
		createPlaylistLabel = new Label("Create A New Playlist");
		createPlaylistLabel.setFont(new Font(30));
		createPlaylistLabel.relocate(115, 300);
		
		// Build text field where the user can enter the name of the new playlist.
		createPlaylistTextfield = new TextField("Enter New Playlist Name");
		createPlaylistTextfield.setMinWidth(200);
		
		// Build the button to send the user back to the Library pane.
		backToLibrary = new Button("<-");
		backToLibrary.setFont(new Font(15));
		backToLibrary.setOnAction(actionHandler);
		backToLibrary.relocate(10, 10);
		
		HBox textField = new HBox();
		textField.getChildren().add(createPlaylistTextfield);
		textField.getChildren().add(createPlaylistSubmitButton);
		textField.relocate(130, 340);
		
		playlistPane.getChildren().add(createPlaylistLabel);
		playlistPane.getChildren().add(textField);
		playlistPane.getChildren().add(backToLibrary);
		
		return playlistPane;
	}
	
	/* This method is used to build the search pane where the user will be able to search through the application's database for select songs.
	 * Written by Stephen Cain
	 */
	public Node buildSearchPane() throws IOException {
		
		Label searchLabel = new Label("Search");
		searchLabel.setFont(new Font("arial", 32));
		searchLabel.setPrefSize(400, 75);
		searchLabel.setMinSize(400, 75);
		searchLabel.setMaxSize(400, 75);
		searchLabel.setAlignment(Pos.CENTER);
		
		searchBox = new TextField();
		searchBox.setOnAction(actionHandler);
		
		searchGoButton = new Button("Go");
		searchGoButton.setOnAction(actionHandler);
		
		VBox top = new VBox();
		HBox center = new HBox();
		
		top.getChildren().add(searchLabel);
		center.getChildren().addAll(searchBox, searchGoButton);
		center.setAlignment(Pos.CENTER);
		top.getChildren().add(center);
		top.setAlignment(Pos.CENTER);
		top.setMaxSize(500, 100);
		top.setMinSize(500, 100);
			
		BorderPane searchPane = new BorderPane();
		
		searchPane.setPrefSize(500, 725);
		searchPane.setMinSize(500, 725);
		searchPane.setMaxSize(500, 725);
		
		searchPane.setTop(top);
		
		searchTable = buildTable();
		searchPane.setCenter(searchTable);
		
		searchTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Object>() {

			@Override
			public void changed(ObservableValue<?> observable, Object oldValue, Object newValue) {
				
				if (searchTable.getSelectionModel().getSelectedItem() != null) {
				addToPlaylistPane = buildAddToPlaylistPane(searchTable.getSelectionModel().getSelectedItem());
				
				System.out.println("User selected " + searchTable.getSelectionModel().getSelectedItem().getName());
				
				root.setCenter(addToPlaylistPane);
				}

				return;
			}
			
		});
		
		return searchPane;	
	}

	/* The next 6 methods are involved in the search pane's song table.
	 * Written by Stephen Cain
	 */
	@SuppressWarnings("unchecked")
	public TableView<Song> buildTable() throws IOException {
	
		searchTable = new TableView<Song>();
		searchTable.setMaxSize(450.0, 600.0);
		searchTable.setPrefSize(450.0, 600.0);
		
		TableColumn<Song, String> songCol = new TableColumn <Song, String>("Song");
		songCol.setMinWidth(450.0/3.0);
		songCol.setCellValueFactory(new PropertyValueFactory<Song, String>("name"));

        TableColumn<Song, String> artistCol = new TableColumn<Song, String> ("Artist");
        artistCol.setMinWidth(450.0/3.0);
        artistCol.setCellValueFactory(new PropertyValueFactory<Song, String>("artist"));

        TableColumn<Song, String> albumCol = new TableColumn<Song, String>("Album");
        albumCol.setMinWidth(450.0/3.0);
        albumCol.setCellValueFactory(new PropertyValueFactory<Song, String>("album"));
        albumCol.setCellFactory(new ImageCellFactory());
        
        searchTable.setItems(songlist);
        searchTable.getColumns().addAll(songCol, artistCol, albumCol);
        searchTable.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
		
		return searchTable;
	}

	/* Help facilite the updating of the search table.
	 * Written by Stephen Cain
	 */
	private final class ImageCellFactory implements Callback<TableColumn<Song, String>, TableCell<Song, String>> {
		public TableCell<Song, String>	call(TableColumn<Song, String> v)
		{
			return new ImageCell();
		}
	}
	
	/* ImageCell element of the search table.
	 * Written by Stephen Cain
	 */
	private final class ImageCell extends TableCell<Song, String> {
		public void	updateItem(String value, boolean isEmpty) {
			super.updateItem(value, isEmpty);

			if (isEmpty || (value == null)) {
				setGraphic(null);
				setAlignment(Pos.CENTER);

				return;
			}
		
			ImageView	image = createFXIcon(value, 100, 100);

			setGraphic(image);
			setAlignment(Pos.CENTER);
		}
	
		public ImageView	createFXIcon(String url, double w, double h) {
			Image	image = new Image("resources/" + url, w, h, false, true);

			return new ImageView(image);
		}
	}
	
	/* This method is used to build a pane that prompts the user to select a playlist to add the select song to.
	 * Written by Stephen Cain
	 */
	public Node buildAddToPlaylistPane(Song song) {
		
		// Build a label that prompts the user to select a playlist.
		Label addLabel = new Label("Select Playlist");
		addLabel.setFont(new Font("arial", 32));
		addLabel.setPrefSize(400, 75);
		addLabel.setMinSize(400, 75);
		addLabel.setMaxSize(400, 75);
		addLabel.setAlignment(Pos.CENTER);
		
		HBox topComponents = new HBox();
		
		topComponents.getChildren().add(addLabel);
		topComponents.setAlignment(Pos.CENTER);
		topComponents.setMaxSize(500, 100);
		topComponents.setMinSize(500, 100);
		
		ScrollPane addToPlaylistLibrary = new ScrollPane();
		addToPlaylistLibrary.setVbarPolicy(ScrollBarPolicy.ALWAYS);
		addToPlaylistLibrary.setPrefViewportHeight(600);
		addToPlaylistLibrary.setPrefViewportWidth(450);
		addToPlaylistLibrary.setMaxSize(450, 600);
		addToPlaylistLibrary.setMinSize(450, 600);
		addToPlaylistLibrary.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
		
		BorderPane addToPlaylist = new BorderPane();
		
		addToPlaylist.setPrefSize(500, 725);
		addToPlaylist.setMinSize(500, 725);
		addToPlaylist.setMaxSize(500, 725);
		
		addToPlaylist.setTop(topComponents);
		
		ListView<String> listPlaylists = new ListView<String>();
		
		for (int i = 0; i < playlistList.size();i++) {
			listPlaylists.getItems().add(playlistList.get(i).getPLName());
		}
		
		listPlaylists.setMaxSize(450, 600);
		listPlaylists.setMinSize(450, 600);
		
		addToPlaylist.setCenter(listPlaylists);
		
		listPlaylists.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Object>() {

			@Override
			public void changed(ObservableValue<?> observable, Object oldValue, Object newValue) {
				
				for (int i = 0; i < playlistList.size(); i++) {
					
					if (playlistList.get(i).getPLName().equals(listPlaylists.getSelectionModel().getSelectedItem())) {
						
						playlistList.get(i).addSong(song);
						
						System.out.println(song.getName() + " added to " + listPlaylists.getSelectionModel().getSelectedItem());
						
						root.setCenter(buildPlaylistPane(listPlaylists.getSelectionModel().getSelectedItem()));
					}
				}
			}
		});
		
		return addToPlaylist;	
	}
	
	/* This method will build the application's collection of songs.
	 * Written by Stephen Cain
	 */
	public ObservableList<Song> buildSongList() throws IOException {
		
		BufferedReader input = new BufferedReader(new FileReader("src/resources/songlist.txt"));
		
		String songInfo;
		
		// Reads the text file and parses each line to construct each song as a Song object.
		while((songInfo = input.readLine()) != null) {
			
			String[] songInfoArray = songInfo.split("/");
			
			Song song = new Song(songInfoArray[0], songInfoArray[1], Integer.parseInt(songInfoArray[2]), songInfoArray[3], songInfoArray[4], songInfoArray[5], songInfoArray[6]);
			
			songlist.add(song);
		}
		
		input.close();
		
		return songlist;
	}

	/* This class help facilite the updating of the seaarch table based on the input given by the user.
	 * Written by Stephen Cain
	 */
	public final class FilterPredicate
		implements Predicate<Song>
	{
		public boolean	test(Song song)
		{
			
			if (searchBox.getText().equalsIgnoreCase(song.getName()) || searchBox.getText().equalsIgnoreCase(song.getArtist())) {
				
				return true;
			}
			
			else if (searchBox.getText().equalsIgnoreCase("") || searchBox.getText().equalsIgnoreCase("")) {
				
				return true;
			}
			
			return false;
		}
	}
	
	/* This action handler deals with the many inputs given by the user and properly assess the changes to the application.
	 * Written by Khoa-chau Nguyen
	 */
	private final class ActionHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent e) {
			Object source = e.getSource();
			
			// If the user selects the browse button on the navigation bar, the application will change to the browse pane.
			if (source == navBarBrowse) { 
				System.out.println("User selected: Browse");
				
				root.setCenter(paneBrowse);
			}
			
			// If the user selects the library button on the navigation bar, the application will change to the library pane.
			else if (source == navBarLibrary) {
				System.out.println("User selected: Library");
				
				root.setCenter(paneLibrary);
			}
			
			// If the user selects the search button on the navigation bar, the application will change to the search pane.
			else if (source == searchButton) {
				System.out.println("User selected: Search");
				
				searchTable.getSelectionModel().clearSelection();
				searchBox.clear();
				updateFilter();
				
				root.setCenter(paneSearch);
			}
			
			// If the user selects the settings button on the navigation bar, the application will change to the settings pane.
			else if (source == settingsButton) {
				System.out.println("User selected: Settings");
				
				root.setCenter(paneSettings);
			}
			
			// If the user selects the button to add a new playlist in their library, the application will change to the pane where the user can create a new playlist..
			else if (source == createPlaylistButton) {
				System.out.println("Open create playlist pane.");
				
				root.setCenter(paneCreatePlaylist);
			}
			
			// If the user selects the back button, the application will change back to the library pane.
			else if (source == backToLibrary) {
				System.out.println("User selected: Library");
				
				root.setCenter(paneLibrary);
			}
			
			// If the user selects the submit button, the application will read the text input and create a new playlist with that name.
			else if (source == createPlaylistSubmitButton) {
				System.out.println("Creating new playlist.");
				
				Playlist tempPL = new Playlist();
				
				tempPL.setName(createPlaylistTextfield.getText());
				
				playlistList.add(tempPL);
				
				System.out.print(playlistList.get(2).getPLName());
				
				paneLibrary = buildLibraryPane();
				
				root.setCenter(paneLibrary);
			}
			
			// If the user selects the back button, the application will change back to the playlist pane.
			else if (source == backToPlaylist) {
				System.out.println("Return to playlsit from song.");
				
				root.setCenter(panePlaylist);
			}
			
			// If the user selects the back button, the application will change back to the browse pane.
			else if (source == backToBrowse) {
				root.setCenter(paneBrowse);
			}
			
			// If the user selects the play button on the song pane, the application will assign the song's audio file to the player.
			else if (source == songPlay) {
				
				media = new Media(new File("src/resources/" + selectedSong.getAudioFile()).toURI().toString());
				mediaplayer = new MediaPlayer(media);
				mediaplayer.setVolume(30);
				mediaplayer.setOnEndOfMedia(new Runnable() {
					public void run() {
						songNext.fire();
					}
				});
				
				mediaplayer.currentTimeProperty().addListener(new InvalidationListener() {
					public void invalidated(Observable ov) {
						updateValues();
					}
				});
				
				if (mediaplayer.getStatus() == Status.PLAYING) {
					mediaplayer.pause();
				}
				else {
					mediaplayer.play();
				}
			}
			// If the user selects the previous button on the song pane, the application will assign the previous song's audio file to the player. If the song is the first song in the playlist, it will move to the last song.
			else if (source == songPrev) {
				if (mediaplayer.getStatus() == Status.PLAYING) {
					mediaplayer.pause();
				}
				
				int idxCurrSong = selectedPlaylist.getIndex(selectedSong);
				
				if (idxCurrSong == 0) {
					selectedSong = selectedPlaylist.getSong(selectedPlaylist.size() - 1);
					
					root.setCenter(buildSongPane(selectedSong, "Playlist"));
				}
				else {
					selectedSong = selectedPlaylist.getSong(idxCurrSong - 1); 
					
					root.setCenter(buildSongPane(selectedSong, "Playlist"));
				}
 				
				media = new Media(new File("src/resources/" + selectedSong.getAudioFile()).toURI().toString());
				mediaplayer = new MediaPlayer(media);
				mediaplayer.setVolume(30);
				mediaplayer.setOnEndOfMedia(new Runnable() {
					public void run() {
						songNext.fire();
					}
				});
				
				mediaplayer.currentTimeProperty().addListener(new InvalidationListener() {
					public void invalidated(Observable ov) {
						updateValues();
					}
				});
				
				if (mediaplayer.getStatus() == Status.PLAYING) {
					mediaplayer.pause();
				}
				
				mediaplayer.play();
				
			}
			
			// If the user selects the pause button on the song pane, the application check if the player is currently playing and only pause if currently playing.
			else if (source == songPause) {
				if (mediaplayer.getStatus() == Status.PLAYING) {
					mediaplayer.pause();
				}
			}
			
			// If the user selects the next button on the song pane, the application will assign the next song's audio file to the player. If the song is the last song in the playlist, it will move to the first song.
			else if (source == songNext) {
				
				int idxCurrSong = selectedPlaylist.getIndex(selectedSong);
				
				if (idxCurrSong + 1 < selectedPlaylist.size()) {
					selectedSong = selectedPlaylist.getSong(idxCurrSong + 1);
					
					root.setCenter(buildSongPane(selectedSong, "Playlist"));
				}
				else if (idxCurrSong + 1 >= selectedPlaylist.size()) {
					selectedSong = selectedPlaylist.getSong(0);
					
					root.setCenter(buildSongPane(selectedSong, "Playlist"));
				}
				
				media = new Media(new File("src/resources/" + selectedSong.getAudioFile()).toURI().toString());
				mediaplayer = new MediaPlayer(media);
				mediaplayer.setVolume(30);
				mediaplayer.setOnEndOfMedia(new Runnable() {
					public void run() {
						songNext.fire();
					}
				});
				
				mediaplayer.currentTimeProperty().addListener(new InvalidationListener() {
					public void invalidated(Observable ov) {
						updateValues();
					}
				});
				
				if (mediaplayer.getStatus() == Status.PLAYING) {
					mediaplayer.pause();
				}
				
				mediaplayer.play();
				
			}
			
			// If the user selects the sheet music button on the song pane, the application will open the sheet music for the song.
			else if (source == songSheetMusic) {
				paneSongSheetMusicView = buildSongSheetMusicPane(selectedSong);
				
				root.setCenter(paneSongSheetMusicView);
			}
			
			// Updates the search table for the user when utilziing the search function.
			else if (source == searchBox || source == searchGoButton) {	
				updateFilter();
			}
			
			// If the user selects the back button on the sheet music pane, the application will return to the song pane.
			else if (source == backToSong) {
				root.setCenter(buildSongPane(selectedSong, "Playlist"));
			}
			
			// If the user selects the Log Out button, the application sign the user out and save their library in txt document format.
			else if (source == settingsLogOut) {
				try {
					savePlayList("src/resources/playlist.txt");
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				
				Dialog<String> dialog = new Dialog<String>();
				dialog.setTitle("Log Out");
				dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
				dialog.setContentText("User has successfully logged out.");
				
				dialog.showAndWait();
			}
		}
	}
	
	/* This method is used to udpate the table of the search pane.
	 * Written by Stephen Cain
	 */
	private void	updateFilter()
	{
	
		Predicate<Song>		predicate = new FilterPredicate();

		searchTable.setItems(new FilteredList<Song>(songlist, predicate));
	}
	
	/* This method is used to load in any of the user's existing playlists.
	 * Written by Khoa-chau Nguyen
	 */
	public ArrayList<Playlist> loadPlayList(String filename) throws IOException {
		
		/* The first line of the text file will consist of the number of playlist.
		 * The second line of the text file is the name of the playlist. Playlist will be ended by a line with just a '/' character.
		 * All lines of text between the line for the playlist name and the '/' are the songs of the playlist.
		 * The line for each song is broken down into songName/songArtist/songRuntime/songGenre/songImageFilename/songSheetMusicImageFilename/songAudioFile
		 */
		
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
	
	/* This method is used to save the user's playlist when they log out of the application.
	 * Written by Khoa-chau Nguyen
	 */
	public void savePlayList(String filename) throws IOException {
		BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
		
		writer.write(Integer.toString(playlistList.size()));
		writer.newLine();
	
		for (int i = 0; i < playlistList.size(); i++) {
			
			writer.write(playlistList.get(i).getPLName());
			writer.newLine();
			
			for (int j = 0; j < playlistList.get(i).size(); j++) {
				Song song = playlistList.get(i).getSong(j);
				
				writer.write(song.getName() + "/" + song.getArtist() + "/" + Integer.toString(song.getRuntime()) + "/" + song.getGenre() + "/" + song.getAlbum() + "/" + song.getSheetMusic() + "/" + song.getAudioFile());
				writer.newLine();
			}
			writer.write("/");
			writer.newLine();
		}
		
		writer.close();
		
	}

	/* This method is called on by slider to display the progress of the playing song.
	 * Written by Khoa-chau Nguyen
	 */
	public void updateValues() {
		Platform.runLater(new Runnable() {
			public void run() {
				songTime.setValue(mediaplayer.getCurrentTime().toMillis() / mediaplayer.getTotalDuration().toMillis() * 100);
			}
		});
	}
}
