package application;

import javafx.scene.control.Label;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.text.Font;
import javafx.scene.paint.Color;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;

public class LibraryPane {

	private Label libraryLabel;
	
	public Node buildLibraryPane(Playlist[] list) {
		
		libraryLabel = new Label("Your Library");
		libraryLabel.setFont(new Font("arial", 32));
		libraryLabel.setPrefSize(500, 75);
		libraryLabel.setMinSize(500, 75);
		libraryLabel.setMaxSize(500, 75);
		libraryLabel.setAlignment(Pos.CENTER);
		
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
		
		libraryPane.setTop(libraryLabel);
		libraryPane.setCenter(playlistList);
		
		return libraryPane;	
	}
	
}
