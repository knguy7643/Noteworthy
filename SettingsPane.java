package application;

import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class SettingsPane {
	
	private Label settingsLabel;
	private Label accountLabel;
	private Label connectLabel;
	private Label aboutLabel;
	
	private ImageView arrow1, arrow2, arrow3;
	
	private Button logOut;
	
	public Node buildSettingsPane() {
		Font btnFont = Font.font("arial", FontWeight.BOLD, 18.0);
		Font lblFont = Font.font("arial", FontWeight.BOLD, 22.0);
		
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
		Label nextPage = new Label("\uf061");
		nextPage.setFont(lblFont);
		
		arrow1 = new ImageView(new Image("arrow.png", 30, 30, true, true));
		arrow2 = new ImageView(new Image("arrow.png", 30, 30, true, true));
		arrow3 = new ImageView(new Image("arrow.png", 30, 30, true, true));
		
		arrow1.setFitHeight(40);
		arrow1.setFitWidth(40);
		arrow2.setFitHeight(40);
		arrow2.setFitWidth(40);
		arrow3.setFitHeight(40);
		arrow3.setFitWidth(40);
		
		HBox row1 = new HBox(150);
		row1.getChildren().add(accountLabel);
		row1.getChildren().add(arrow1);
		row1.setAlignment(Pos.CENTER_LEFT);
		
		HBox row2 = new HBox(240);
		row2.getChildren().add(connectLabel);
		row2.getChildren().add(arrow2);
		row2.setAlignment(Pos.CENTER_LEFT);
		
		HBox row3 = new HBox(355);
		row3.getChildren().add(aboutLabel);
		row3.getChildren().add(arrow3);
		row3.setAlignment(Pos.CENTER_LEFT);
			
		VBox selections = new VBox(10);
		selections.getChildren().add(row1);
		selections.getChildren().add(row2);
		selections.getChildren().add(row3);

		FlowPane pane = new FlowPane(Orientation.VERTICAL);
		pane.getChildren().add(settingsLabel);
		pane.setMargin(settingsLabel, new Insets(10, 20, 20, 200));
		
		pane.setPrefSize(500, 725);
		pane.setMinSize(500,  725);
		pane.setMaxSize(500,  725);
		
		pane.getChildren().add(selections);
		pane.setMargin(selections, new Insets(10, 10, 10, 10));
		
		pane.getChildren().add(logOut);
		pane.setMargin(logOut, new Insets(20, 20, 20, 200));


		return pane;
		
		
	}
	
}
