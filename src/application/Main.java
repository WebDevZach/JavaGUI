package application;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

public class Main extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {

		VBox layout1 = new VBox();
		layout1.setSpacing(35);
		BorderPane borderPane = new BorderPane();
		borderPane.setTop(layout1);
		layout1.setPadding(new Insets(30, 0, 0, 150));
		Scene scene1 = new Scene(borderPane, 400, 300);

		VBox layout3 = new VBox();
		VBox layout4 = new VBox();
		VBox layout5 = new VBox();

		
		Scene scene3 = new Scene(layout3, 400, 300);
		Scene scene4 = new Scene(layout4, 400, 300);
		Scene scene5 = new Scene(layout5, 400, 300);

		
		
		Button switchToMenuFromAddBookButton = new Button("Menu");
		switchToMenuFromAddBookButton.setOnAction(e -> primaryStage.setScene(scene1));

		Button twoswitchToScene1Button = new Button("Menu");
		twoswitchToScene1Button.setOnAction(e -> primaryStage.setScene(scene1));

		Button threeswitchToScene1Button = new Button("Menu");
		threeswitchToScene1Button.setOnAction(e -> primaryStage.setScene(scene1));

		Button fourswitchToScene1Button = new Button("Menu");
		fourswitchToScene1Button.setOnAction(e -> primaryStage.setScene(scene1));

		
		
	
		
		BorderPane addNewBookLayout = new BorderPane();
		StackPane stackPane = new StackPane();
		
		stackPane.getChildren().add(switchToMenuFromAddBookButton);
		addNewBookLayout.setBottom(stackPane);
		stackPane.setPadding(new Insets(0, 0, 20, 300));

		Scene addNewBookscene = new Scene(addNewBookLayout, 400, 300);
		
		
		GridPane gridPane = new GridPane();
		gridPane.setHgap(10);
		gridPane.setVgap(10);
		
		Label bookTitleLabel = new Label("Title");
		Label authorLabel = new Label("Author");
		
		TextField bookTitleTf = new TextField();
		TextField authorTf = new TextField();
		
		gridPane.add(bookTitleLabel, 0, 0);
		gridPane.add(authorLabel, 0, 1);
		gridPane.add(bookTitleTf, 1, 0);
		gridPane.add(authorTf, 1, 1);
		
		addNewBookLayout.setCenter(gridPane);
		gridPane.setPadding(new Insets(100, 0, 0, 90));
		
		
		
		Button addButton = new Button(" Add a new book ");
		addButton.setOnAction(e -> primaryStage.setScene(addNewBookscene));
		layout1.getChildren().add(addButton);

		Button rentButton = new Button("     Rent a book    ");
		rentButton.setOnAction(e -> primaryStage.setScene(scene3));
		layout1.getChildren().add(rentButton);

		Button returnButton = new Button("   Return a book   ");
		returnButton.setOnAction(e -> primaryStage.setScene(scene4));
		layout1.getChildren().add(returnButton);

		Button searchButton = new Button("  Look up a book  ");
		searchButton.setOnAction(e -> primaryStage.setScene(scene5));
		layout1.getChildren().add(searchButton);

		
		

		layout3.getChildren().add(twoswitchToScene1Button);
		layout4.getChildren().add(threeswitchToScene1Button);
		layout5.getChildren().add(fourswitchToScene1Button);

		
		
		
		primaryStage.setScene(scene1);
		primaryStage.setResizable(false);
		primaryStage.setTitle("Scene Toggling Example");
		primaryStage.show();
	}
}
