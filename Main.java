package application;
	
import javafx.application.Application;
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
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;

public class Main extends Application {
	
	private BorderPane root;
	
	// Private variables for each of the different panes.
	private Node browsePane;
	private Node libraryPane;
	private Node searchPane;
	private Node settingsPane;
	private Node newPlaylistPane;
	
	// Private variables for the navigation bar.
	private Node navigationBar;
	private Button browseButton;
	private Button libraryButton;
	private Button searchButton;
	private Button settingsButton;
	
	// Private variables for the Library Pane
	private Button addPlayListButton;
	private Label libraryLabel;
	
	// TODO: Add other neededs variables. Delete uneeded variables. 
	private Playlist[] playlistList;
	
	// Action Handler to deal with the user's inputs. 
	private EventHandler<ActionEvent> actionHandler;
	
	@Override
	public void start(Stage primaryStage) {
		try {
			playlistList = loadPlayList("playlist.txt");
			
			actionHandler = new ActionHandler();
	
			libraryPane = buildLibraryPane();
			searchPane = (new SearchPane()).buildSearchPane();
			settingsPane = (new SettingsPane()).buildSettingsPane();
			
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
		
		ScrollPane playlistList = new ScrollPane();
		playlistList.setVbarPolicy(ScrollBarPolicy.ALWAYS);
		playlistList.setPrefViewportHeight(600);
		playlistList.setPrefViewportWidth(450);
		playlistList.setMaxSize(450, 600);
		playlistList.setMinSize(450, 600);
		playlistList.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
		
		BorderPane libraryPane = new BorderPane();
		
		libraryPane.setPrefSize(500, 725);
		libraryPane.setMinSize(500, 725);
		libraryPane.setMaxSize(500, 725);
		
		libraryPane.setTop(topComponents);
		libraryPane.setCenter(playlistList);
		
		return libraryPane;	
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
				System.out.println("Open create playlsit pane.");
				
				root.setCenter(newPlaylistPane);
			}
			
		}
	}
	
	public Playlist[] loadPlayList(String filename) {
		return null;
	}
	
}
