package application;
	
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.control.Button;


public class Main extends Application {
	
	private Node navigationBar;
	
	private BorderPane root;
	
	private Button browseButton;
	private Button libraryButton;
	private Button searchButton;
	private Button settingsButton;
	
	private Node browsePane;
	private Node libraryPane;
	private Node searchPane;
	private Node settingsPane;
	
	private Playlist[] playlistList;
	
	private EventHandler<ActionEvent> actionHandler;
	
	@Override
	public void start(Stage primaryStage) {
		try {
			
			libraryPane = (new LibraryPane()).buildLibraryPane(playlistList);
			
			root = new BorderPane();
			
			actionHandler = new ActionHandler();
			
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
		
		browseButton = new Button ("Browse");
		browseButton.setMaxSize(125, 75);
		browseButton.setMinSize(125, 75);
		browseButton.setOnAction(actionHandler);
		
		libraryButton = new Button("Library");
		libraryButton.setMaxSize(125, 75);
		libraryButton.setMinSize(125, 75);
		libraryButton.setOnAction(actionHandler);
		
		searchButton = new Button("Search");
		searchButton.setMaxSize(125, 75);
		searchButton.setMinSize(125, 75);
		searchButton.setOnAction(actionHandler);
		
		settingsButton = new Button("Settings");
		settingsButton.setMaxSize(125, 75);
		settingsButton.setMinSize(125, 75);
		settingsButton.setOnAction(actionHandler);
		
		menuBar.getChildren().addAll(browseButton, libraryButton, searchButton, settingsButton);
		
		return menuBar;
	}
	
	private final class ActionHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent e) {
			Object source = e.getSource();
			
			if (source == browseButton) {
				System.out.println("User selected: Browse");
			}
			else if (source == libraryButton) {
				System.out.println("User selected: Library");
				
				root.setCenter(libraryPane);
			}
			else if (source == searchButton) {
				System.out.println("User selected: Search");
			}
			else if (source == settingsButton) {
				System.out.println("User selected: Settings");
			}
			
		}
	}
	
}
