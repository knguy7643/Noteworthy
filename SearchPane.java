/*package application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.function.Predicate;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class SearchPane {


	private TableView<Song> table;
	private ObservableList<Song> songlist = FXCollections.observableArrayList();
	private Button goButton;
	private TextField searchBox;
	private EventHandler<ActionEvent> actionHandler;
	
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
		searchPane.setCenter(buildTable());
		
		return searchPane;	
	}

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
        
        table.setItems(buildSongList());
        table.getColumns().addAll(songCol, artistCol, albumCol);
        table.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
		
		return table;
	}
	
	public Node buildAddToPlaylistPane() {
		
		Label addLabel = new Label("Your Library");
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
		//libraryPane.setCenter(playlistListLibrary);
		
		ListView<Playlist> listPlaylists = new ListView<>();
		
		listPlaylists.setMaxSize(450, 600);
		listPlaylists.setMinSize(450, 600);
		
		addToPlaylist.setCenter(listPlaylists);
		
		return addToPlaylist;	
	}
	
	public ObservableList<Song> buildSongList() throws IOException {
		
		BufferedReader input = new BufferedReader(new FileReader("songlist.txt"));
		
		String songInfo;
		
		while((songInfo = input.readLine()) != null) {
			
			String[] songInfoArray = songInfo.split("/");
			
			Song song = new Song(songInfoArray[0], songInfoArray[1], Integer.parseInt(songInfoArray[2]), songInfoArray[3], songInfoArray[4], songInfoArray[5]);
			
			songlist.add(song);
		}
		
		input.close();
		
		return songlist;
	}


	private final class FilterPredicate
		implements Predicate<Song>
	{
		public boolean	test(Song song)
		{
			
			if (searchBox.getText().equalsIgnoreCase(song.getName()) || searchBox.getText().equalsIgnoreCase(song.getArtist())) {
				
				return true;
			}
			
			return false;
		}
	}
	
	private final class ActionHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent e) {
			Object source = e.getSource();
			
			if (source == searchBox || source == goButton) {
				
				updateFilter();
				
			//	root.setCenter(buildAddToPlaylistPane());
			
			}
		}
		
		private void	updateFilter()
		{
		
			Predicate<Song>		predicate = new FilterPredicate();

			table.setItems(new FilteredList<Song>(songlist, predicate));
		}
		
		
	}
}
*/