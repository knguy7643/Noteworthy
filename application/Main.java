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
	
	// Private variables for each of the different panes.
	private Node browsePane;
	private Node libraryPane;
	private Node newPlaylistPane;
	private Node searchPane;
	private Node settingsPane;
	private Node songPane;
	private Node songSheetMusicPane;
	
	// Private variables for the navigation bar.
	private Node navigationBar;
	private Button browseButton;
	private Button libraryButton;
	private Button searchButton;
	private Button settingsButton;
	
	// Private variables for the Library Pane
	private ArrayList<Playlist> playlistList;
	private Button addPlayListButton;
	private Button backToLibrary;
	private Button newPLSubmit;
	private Label libraryLabel;
	private Label newPLLabel;
	private Label playlistNameLabel;
	private ListView<String> listLibrary;
	private ListView<String> listSongs;
	private Node playListPane;
	private Playlist selectedPlaylist;
	private Song selectedSong;
	private TextField newPLTextfield;
	
	// Private variables for Settings Pane
	private Button logOut;
	private ImageView arrow1, arrow2, arrow3;
	private Label settingsLabel;
	private Label accountLabel;
	private Label connectLabel;
	private Label aboutLabel;
	
	// Private variables for Browse Pane
	private Label browseLabel;
	private Label recentlyPlayed;
	private Label newRelease;
	
	// Private variable for the Search Pane
	private Button goButton;
	private ObservableList<Song> songlist = FXCollections.observableArrayList();
	private TableView<Song> table;
	private TextField searchBox;
	
	// Private variable for the Song Pane
	private Button backToBrowse;
	private Button backToPlaylist;
	private Button backToSearch;
	private Button backToSong;
	private Button songSheetMusic;
	private Button songPlay;
	private Button songPause;
	private Button songNext;
	private Button songPrev;
	private ImageView songImageView;
	private ImageView songSheetView;
	private Label songNameLabel;
	private Slider songTime;
	
	// Private variable for the Add To Playlist Pane
	private Node addToPlaylistPane;
	
	// Action Handler to deal with the user's inputs. 
	private EventHandler<ActionEvent> actionHandler;
	
	// Private variables for the playing of music.
	private Media media;
	private MediaPlayer mediaplayer;
	
// Methods Below. 
	
	// Method starts and build the various components of our application.
	@Override
	public void start(Stage primaryStage) {
		try {
			playlistList = loadPlayList("src/resources/playlist.txt");
			
			actionHandler = new ActionHandler();
	
			browsePane = buildBrowsePane();
			libraryPane = buildLibraryPane();
			searchPane = buildSearchPane();
			settingsPane = buildSettingsPane();
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
	
	// This method is used to build the navigation bar that will allow the user to move between brwosing, searching, or their own library.
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
	
	// This method is used to build the library pane that will display all of the user's playlist with the option to create new playlists.
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
	
	// This method is used to build the settings pane that will allow the user to check various information regarding the application.
	public Node buildSettingsPane() {
		Font btnFont = Font.font("arial", FontWeight.BOLD, 18.0);
		Font lblFont = Font.font("arial", 22.0);
		
		settingsLabel = new Label("Settings");
		
		settingsLabel.setFont(new Font("arial", 32));
		settingsLabel.setPrefSize(400,  75);
		
		accountLabel = new Label("Account/Privacy Settings");
		accountLabel.setFont(lblFont);
		accountLabel.setPrefHeight(75);
		
		connectLabel = new Label("Connect Devices");
		connectLabel.setFont(lblFont);
		connectLabel.setPrefHeight(75);
		
		aboutLabel = new Label("About");
		aboutLabel.setFont(lblFont);
		aboutLabel.setPrefHeight(75);
		
		logOut = new Button("Log Out");
		logOut.setPrefSize(100, 60);
		logOut.setFont(btnFont);
		logOut.setOnAction(actionHandler);		
		
		arrow1 = new ImageView(new Image(new File("src/resources/arrow.png").toURI().toString(), 30, 30, true, true));
		arrow2 = new ImageView(new Image(new File("src/resources/arrow.png").toURI().toString(), 30, 30, true, true));
		arrow3 = new ImageView(new Image(new File("src/resources/arrow.png").toURI().toString(), 30, 30, true, true));
		
		arrow1.setFitHeight(40);
		arrow1.setFitWidth(40);
		arrow2.setFitHeight(40);
		arrow2.setFitWidth(40);
		arrow3.setFitHeight(40);
		arrow3.setFitWidth(40);
		
		HBox row1 = new HBox(192);
		row1.getChildren().add(accountLabel);
		row1.getChildren().add(arrow1);
		row1.setAlignment(Pos.CENTER_LEFT);
		
		HBox row2 = new HBox(270);
		row2.getChildren().add(connectLabel);
		row2.getChildren().add(arrow2);
		row2.setAlignment(Pos.CENTER_LEFT);
		
		HBox row3 = new HBox(380);
		row3.getChildren().add(aboutLabel);
		row3.getChildren().add(arrow3);
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
		
		pane.getChildren().add(logOut);
		FlowPane.setMargin(logOut, new Insets(20, 20, 20, 200));


		return pane;
		
	}
	
	// This method is used to build the browse pane that acts as the home page of the application and will present the user with a list of recently played and newly released songs.
	@SuppressWarnings("unchecked")
	public Node buildBrowsePane() throws IOException {
		
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
		
		playRecent.setItems(buildSongList());
		playRecent.getColumns().addAll(rSongs, rArtist, rAlbum);
		
		playRecent.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Object>() {
			
			@Override
			public void changed(ObservableValue<?> observable, Object oldValue, Object newValue) {
				if (playRecent.getSelectionModel().getSelectedItem() != null) {
					selectedSong = playRecent.getSelectionModel().getSelectedItem();
					System.out.println("User selected " + playRecent.getSelectionModel().getSelectedItem().getName());
					songPane = buildSongPane(selectedSong, "Browse");
					root.setCenter(songPane);
				}
			}
		
		});
		
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
		newReleases.setItems(buildSongList());
		newReleases.getColumns().addAll(nSongs, nArtist, nAlbum);
		
		newReleases.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Object>() {
			
			@Override
			public void changed(ObservableValue<?> observable, Object oldValue, Object newValue) {
				if (newReleases.getSelectionModel().getSelectedItem() != null) {
					selectedSong = newReleases.getSelectionModel().getSelectedItem();
					System.out.println("User selected " + newReleases.getSelectionModel().getSelectedItem().getName());
					songPane = buildSongPane(selectedSong, "Browse");
					root.setCenter(songPane);
				}
			}
		
		});
		
		browseLabel = new Label("Browse");
		browseLabel.setFont(new Font("arial", 32));
		browseLabel.setPrefSize(400, 50);
		browseLabel.setAlignment(Pos.CENTER);
		
		recentlyPlayed = new Label("Recently Played");
		recentlyPlayed.setFont(new Font("arial", 20));
		recentlyPlayed.setPrefSize(400, 50);
		
		newRelease = new Label("New Releases");
		newRelease.setFont(new Font("arial", 20));
		newRelease.setPrefSize(400, 50);
		
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
		vbox.getChildren().add(recentlyPlayed);
		VBox.setMargin(recentlyPlayed, new Insets(10, 10, 10, 10));
		vbox.getChildren().add(row1);
		VBox.setMargin(row1, new Insets(10, 10, 10, 10));
		vbox.getChildren().add(newRelease);
		VBox.setMargin(newRelease, new Insets(10, 10, 10, 10));
		vbox.getChildren().add(row2);
		VBox.setMargin(row2, new Insets(10, 10, 10, 10));
		
		pane.setContent(vbox);
		
		return pane;
	
		
	}
	
	// This method is used to build the playlist pane that displays the songs contained by the playlist.
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
	
	// This method is used to build the song pane where the user can play/pause songs and open the song's sheet music.
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
		songNameLabel.relocate(125, 420);
		
		songPlay = new Button("|>");
		songPlay.setFont(new Font(15));
		songPlay.setOnAction(actionHandler);
		
		songPause = new Button("||");
		songPause.setFont(new Font(15));
		songPause.setOnAction(actionHandler);
		
		songPrev = new Button("<<");
		songPrev.setFont(new Font(15));
		songPrev.setOnAction(actionHandler);
		
		songNext = new Button(">>");
		songNext.setFont(new Font(15));
		songNext.setOnAction(actionHandler);
		
		songSheetMusic = new Button("Sheet Music");
		songSheetMusic.setFont(new Font(15));
		songSheetMusic.setOnAction(actionHandler);
		
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
	
	// This method is used to build the pane that will display the selected song's sheet music.
	public Node buildSongSheetMusicPane(Song s) {
		Pane sheetPane = new Pane();
		
		backToSong = new Button("<-");
		backToSong.setFont(new Font(15));
		backToSong.setOnAction(actionHandler);
		backToSong.relocate(10, 10);
		
		songSheetView = new ImageView();
		
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
	
	// This method is used to build the pane where the user will be asked the name for their new playlist.
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
	
	// This method is used to build the search pane where the user will be able to search through the application's database for select songs.
	public Node buildSearchPane() throws IOException {
		
		actionHandler = new ActionHandler();
		
		Label searchLabel = new Label("Search");
		searchLabel.setFont(new Font("arial", 32));
		searchLabel.setPrefSize(400, 75);
		searchLabel.setMinSize(400, 75);
		searchLabel.setMaxSize(400, 75);
		searchLabel.setAlignment(Pos.CENTER);
		
		searchBox = new TextField();
		searchBox.setOnAction(actionHandler);
		
		goButton = new Button("Go");
		goButton.setOnAction(actionHandler);
		
		VBox top = new VBox();
		HBox center = new HBox();
		
		top.getChildren().add(searchLabel);
		center.getChildren().addAll(searchBox, goButton);
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
		
		table = buildTable();
		searchPane.setCenter(table);
		
		table.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Object>() {

			@Override
			public void changed(ObservableValue<?> observable, Object oldValue, Object newValue) {
				
				if (table.getSelectionModel().getSelectedItem() != null) {
				addToPlaylistPane = buildAddToPlaylistPane(table.getSelectionModel().getSelectedItem());
				
				System.out.println("User selected " + table.getSelectionModel().getSelectedItem().getName());
				
				root.setCenter(addToPlaylistPane);
				}

				return;
			}
			
		});
		
		return searchPane;	
	}

	// The next 6 methods are involved in the search pane's song table.
	@SuppressWarnings("unchecked")
	public TableView<Song> buildTable() throws IOException {
	
		table = new TableView<Song>();
		table.setMaxSize(450.0, 600.0);
		table.setPrefSize(450.0, 600.0);
		
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
        
        table.setItems(buildSongList());
        table.getColumns().addAll(songCol, artistCol, albumCol);
        table.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
		
		return table;
	}

	private final class ImageCellFactory
		implements Callback<TableColumn<Song, String>,
							TableCell<Song, String>>
	{
		public TableCell<Song, String>	call(TableColumn<Song, String> v)
		{
			return new ImageCell();
		}
	}
	
	private final class ImageCell
	extends TableCell<Song, String>
{
	public void	updateItem(String value, boolean isEmpty)
	{
		super.updateItem(value, isEmpty);

		if (isEmpty || (value == null))
		{
			setGraphic(null);
			setAlignment(Pos.CENTER);

			return;
		}
		
		ImageView	image = createFXIcon(value, 100, 100);

		setGraphic(image);
		setAlignment(Pos.CENTER);
	}
	
	public ImageView	createFXIcon(String url, double w, double h)
	{
		Image	image = new Image("resources/" + url, w, h, false, true);

		return new ImageView(image);
	}
}
	
	public Node buildAddToPlaylistPane(Song song) {
		
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
	
	public ObservableList<Song> buildSongList() throws IOException {
		
		BufferedReader input = new BufferedReader(new FileReader("src/resources/songlist.txt"));
		
		String songInfo;
		
		while((songInfo = input.readLine()) != null) {
			
			String[] songInfoArray = songInfo.split("/");
			
			Song song = new Song(songInfoArray[0], songInfoArray[1], Integer.parseInt(songInfoArray[2]), songInfoArray[3], songInfoArray[4], songInfoArray[5], songInfoArray[6]);
			
			songlist.add(song);
		}
		
		input.close();
		
		return songlist;
	}

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
	
	// This action handler deals with the many inputs given by the user and properly assess the changes to the application.
	private final class ActionHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent e) {
			Object source = e.getSource();
			
			// If the user selects the browse button on the navigation bar, the application will change to the browse pane.
			if (source == browseButton) { 
				System.out.println("User selected: Browse");
				
				root.setCenter(browsePane);
			}
			// If the user selects the library button on the navigation bar, the application will change to the library pane.
			else if (source == libraryButton) {
				System.out.println("User selected: Library");
				
				root.setCenter(libraryPane);
			}
			// If the user selects the search button on the navigation bar, the application will change to the search pane.
			else if (source == searchButton) {
				System.out.println("User selected: Search");
				
				table.getSelectionModel().clearSelection();
				searchBox.clear();
				updateFilter();
				
				root.setCenter(searchPane);
			}
			// If the user selects the settings button on the navigation bar, the application will change to the settings pane.
			else if (source == settingsButton) {
				System.out.println("User selected: Settings");
				
				root.setCenter(settingsPane);
			}
			// If the user selects the button to add a new playlist in their library, the application will change to the pane where the user can create a new playlist..
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
			else if (source == backToBrowse) {
				root.setCenter(browsePane);
			}
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
				
				if (mediaplayer.getStatus() != Status.PLAYING) {
					mediaplayer.play();
				}
			}
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
			else if (source == songPause) {
				if (mediaplayer.getStatus() == Status.PLAYING) {
					mediaplayer.pause();
				}
			}
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
			else if (source == songSheetMusic) {
				songSheetMusicPane = buildSongSheetMusicPane(selectedSong);
				
				root.setCenter(songSheetMusicPane);
			}
			else if (source == searchBox || source == goButton) {
				
				updateFilter();
			}
			else if (source == backToSong) {
				root.setCenter(buildSongPane(selectedSong, "Playlist"));
			}
			else if (source == logOut) {
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
	
	// This method is used to udpate the table of the search pane.
	private void	updateFilter()
	{
	
		Predicate<Song>		predicate = new FilterPredicate();

		table.setItems(new FilteredList<Song>(songlist, predicate));
	}
	
	// This method is used to load in any of the user's existing playlists.
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
	
	// This method is used to save the user's playlist when they log out of the application.
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

	// This method is called on by slider to display the progress of the playing song.
	public void updateValues() {
		Platform.runLater(new Runnable() {
			public void run() {
				songTime.setValue(mediaplayer.getCurrentTime().toMillis() / mediaplayer.getTotalDuration().toMillis() * 100);
			}
		});
	}
}
