package application;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.TextField;
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

public Node buildSearchPane() {
		
		//actionHandler = new ActionHandler();
		
		Label searchLabel = new Label("Search");
		searchLabel.setFont(new Font("arial", 32));
		searchLabel.setPrefSize(400, 75);
		searchLabel.setMinSize(400, 75);
		searchLabel.setMaxSize(400, 75);
		searchLabel.setAlignment(Pos.CENTER);
		
		TextField searchBox = new TextField();
		
		Button searchButton = new Button("Go");
		
		VBox top = new VBox();
		HBox center = new HBox();
		
		top.getChildren().add(searchLabel);
		center.getChildren().addAll(searchBox, searchButton);
		center.setAlignment(Pos.CENTER);
		top.getChildren().add(center);
		top.setAlignment(Pos.CENTER);
		top.setMaxSize(500, 100);
		top.setMinSize(500, 100);
		
		ScrollPane searchResults = new ScrollPane();
		searchResults.setVbarPolicy(ScrollBarPolicy.ALWAYS);
		searchResults.setPrefViewportHeight(600);
		searchResults.setPrefViewportWidth(450);
		searchResults.setMaxSize(450, 600);
		searchResults.setMinSize(450, 600);
		searchResults.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
		
		BorderPane searchPane = new BorderPane();
		
		searchPane.setPrefSize(500, 725);
		searchPane.setMinSize(500, 725);
		searchPane.setMaxSize(500, 725);
		
		searchPane.setTop(top);
		searchPane.setCenter(searchResults);
		
		return searchPane;	
	}
}
