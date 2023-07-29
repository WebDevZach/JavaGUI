package application;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        StackPane layout1 = new StackPane();
        layout1.getChildren().add(new Button("Switch to Scene 2"));
        Scene scene1 = new Scene(layout1, 400, 300);

        StackPane layout2 = new StackPane();
        layout2.getChildren().add(new Button("Switch to Scene 1"));
        Scene scene2 = new Scene(layout2, 400, 300);

        Button switchToScene2Button = new Button("Switch to Scene 2");
        switchToScene2Button.setOnAction(e -> primaryStage.setScene(scene2));

        Button switchToScene1Button = new Button("Switch to Scene 1");
        switchToScene1Button.setOnAction(e -> primaryStage.setScene(scene1));

        layout1.getChildren().add(switchToScene2Button);
        layout2.getChildren().add(switchToScene1Button);

        primaryStage.setScene(scene1);
        primaryStage.setTitle("Scene Toggling Example");
        primaryStage.show();
    }
}
