package ui;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {
	
	public SpaceInvadersGUI gui;
	
	public Main() {
		gui = new SpaceInvadersGUI();
	}

	public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage) throws Exception {
		gui.setStage(primaryStage);
    	gui.loaderPane("lobby.fxml");
		primaryStage.setTitle("Space Invaders");
		primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("../icon.jpg")));
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}