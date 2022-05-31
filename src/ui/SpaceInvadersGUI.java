package ui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.Sprite;
import thread.InvadersThread;
import thread.MissileThread;
import thread.RocketThread;
import thread.TankThread;

public class SpaceInvadersGUI {
	
	public final static int INVADERS = 40;

	@FXML
	private Pane pane;

	private Stage stage;
	private Sprite tank;
	private ArrayList<Sprite> invaders;
	private TankThread tt;

	@FXML
	public void startGame(ActionEvent event) throws IOException {
		loaderPane("game.fxml");
		pane.requestFocus();
		invaders = new ArrayList<>();
		ImageView tank = new ImageView(new Image(getClass().getResourceAsStream("../tank.png")));
		tank.setFitWidth(32.5);
		tank.setFitHeight(20);
		pane.getChildren().add(tank);
		tank.setLayoutY(pane.getHeight() - tank.getFitHeight());
		tank.setLayoutX((pane.getWidth() / 2) - (tank.getFitWidth() / 2));
		int invadersSet = 0;
		Random random = new Random();
		while(invadersSet < INVADERS) {
			if(random.nextBoolean()) {
				ImageView invader = null;
				Sprite s = null;
				switch(random.nextInt(3)) {
				case 0:
					invader = new ImageView(new Image(getClass().getResourceAsStream("../1-first-invader.png")));
					invader.setFitWidth(27.5);
					invader.setFitHeight(20);
					s = new Sprite(invader, "1-first-invader.png");
					break;
				case 1:
					invader = new ImageView(new Image(getClass().getResourceAsStream("../1-second-invader.png")));
					invader.setFitWidth(30);
					invader.setFitHeight(20);
					s = new Sprite(invader, "1-second-invader.png");
					break;
				case 2: 
					invader = new ImageView(new Image(getClass().getResourceAsStream("../1-third-invader.png")));
					invader.setFitWidth(20);
					invader.setFitHeight(20);
					s = new Sprite(invader, "1-third-invader.png");
					break;
				}
				pane.getChildren().add(invader);
				boolean stop = false;
				while(!stop) {
					invader.setLayoutY(24 + random.nextInt(145 - 24));
					invader.setLayoutX(10 + random.nextInt(781 - 39));
					stop = true;
					for(int i = 0; i < invaders.size() && stop; i++) {
						if(invader.getBoundsInParent().intersects(invaders.get(i).getBoundsInParent())) {
							stop = false;
						}
					}
				}
				invaders.add(s);
				invadersSet++;
			}
		}
		InvadersThread it = new InvadersThread(this, invaders);
		it.setDaemon(true);
		it.start();
		this.tank = new Sprite(tank, null);
	}

	@FXML
	public void onKeyReleased(KeyEvent event) {
		if (KeyCode.RIGHT == event.getCode()) {
			tank.setActiveRight(false);
		}
		if (KeyCode.LEFT == event.getCode()) {
			tank.setActiveLeft(false);
		}
	}
	
	@FXML
	public void onKeyPressed(KeyEvent event) {
		if (KeyCode.RIGHT == event.getCode() && !tank.isActiveRight()) {
			tank.setActiveRight(true);
			tt = new TankThread(tank, pane, 1);
			tt.setDaemon(true);
			tt.start();
		}
		if (KeyCode.LEFT == event.getCode() && !tank.isActiveLeft()) {
			tank.setActiveLeft(true);
			tt = new TankThread(tank, pane, -1);
			tt.setDaemon(true);
			tt.start();
		}
		if (KeyCode.SPACE == event.getCode()) {
			ImageView rocket = new ImageView(new Image(getClass().getResourceAsStream("../rocket.png")));
			rocket.setFitWidth(20);
			rocket.setFitHeight(20);
			pane.getChildren().add(rocket);
			rocket.setLayoutY(pane.getHeight() - tank.getFitHeight() - rocket.getFitHeight());
			rocket.setLayoutX(tank.getLayoutX() + (tank.getFitWidth() / 2) - (rocket.getFitWidth() / 2));
			RocketThread rt = new RocketThread(rocket, invaders);
			rt.setDaemon(true);
			rt.start();
		}
	}

	public void loaderPane(String fmxl) throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fmxl));
		fxmlLoader.setController(this);
		stage.setScene(new Scene(fxmlLoader.load()));
	}

	public void setStage(Stage s) {
		stage = s;
	}

	public void missile(ImageView shoot) {
		pane.getChildren().add(shoot);
		MissileThread mt = new MissileThread(shoot, tank.getImageView());
		mt.setDaemon(true);
		mt.start();
	}
}