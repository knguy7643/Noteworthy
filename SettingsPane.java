package application;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class SettingsPane {
  
  /* still need to edit formatting! */
	
	private Label settingsLabel;
	private Label accountLabel;
	private Label connectLabel;
	private Label aboutLabel;
	
	private Button logOut;
	
	public Node buildSettingsPane() {
		
		settingsLabel = new Label("Settings");
		
		settingsLabel.setFont(new Font("arial", 32));
		settingsLabel.setPrefSize(400,  75);
		
		accountLabel = new Label("Account/Privacy Settings");
		accountLabel.setFont(new Font("arial", 18));
		accountLabel.setPrefSize(300, 75);
		
		connectLabel = new Label("Connect Devices");
		connectLabel.setFont(new Font("arial", 18));
		connectLabel.setPrefSize(300, 75);
		
		aboutLabel = new Label("About");
		aboutLabel.setFont(new Font("arial", 18));
		aboutLabel.setPrefSize(300, 75);
		
		logOut = new Button("Log Out"); //still need to adjust placement & size
		
		VBox selections = new VBox(10);
		selections.getChildren().add(accountLabel);
		selections.getChildren().add(connectLabel);
		selections.getChildren().add(aboutLabel);

		
		BorderPane pane = new BorderPane();
		
		pane.setPrefSize(500, 725);
		pane.setMinSize(500,  725);
		pane.setMaxSize(500,  725);
		
		pane.setTop(settingsLabel);
		pane.setCenter(selections);
		pane.setBottom(logOut);
		pane.setAlignment(logOut, Pos.TOP_CENTER);
		pane.setMargin(selections, new Insets(10, 10, 10, 10));
		pane.setMargin(settingsLabel, new Insets(10, 10, 10, 10));
		
		
		return pane;
		
		
	}
	
}
