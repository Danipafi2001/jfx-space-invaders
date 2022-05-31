package ui;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Random;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.Sprite;
import thread.InvaderThread;
import thread.MissileThread;
import thread.RocketThread;
import thread.TankThread;

public class SpaceInvadersGUI {

	//Hasta 50
	public final static int INVADERS = 10;
	//De 100 a 600, entre más bajo más dificl
	public final static long DIFFICULT = 100;

	private Sprite tank;
	private ArrayList<Sprite> invaders;

	@FXML
	private Pane space;
	@FXML
	private Label score, highScore;
	@FXML
	private HBox lifes;
	@FXML
	private Button playAgain;
	private Stage stage;
	private Random random;
	private boolean stop;

	public SpaceInvadersGUI() {
		random = new Random();
		invaders = new ArrayList<>();
	}

	@FXML
	public void startGame(ActionEvent event) throws IOException {
		startGame(DIFFICULT);
	}

	private void startGame(long sleep) throws IOException {
		random = new Random();
		invaders = new ArrayList<>();
		stop = false;
		loaderPane("game.fxml");
		space.requestFocus();
		createTank("tank.png");
		createInvaders();
		playAgain.setVisible(false);
		try {
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream("../high-score.txt"));
			try {
				while(true) {
					highScore.setText((String)ois.readObject());
				}
			} catch(EOFException e) {

			} catch (ClassNotFoundException e) {

			} 
			ois.close();
		}catch(IOException e) {
		} 
	}

	private void createTank(String url) {
		tank = new Sprite(url);
		tank.setLayoutX((space.getWidth() / 2) - (tank.getWidth() / 2));
		tank.setLayoutY(space.getHeight() - tank.getHeight());
		space.getChildren().add(tank.getImageView());
	}

	private void createInvaders() {
		for(int i = 0; i < INVADERS; i++) {
			Sprite invader = new Sprite();
			space.getChildren().add(invader.getImageView());
			boolean stop = false;
			while(!stop) {
				invader.setLayoutX(30 + random.nextInt(711));
				invader.setLayoutY(random.nextInt(161));
				stop = true;
				for(int j = 0; j < invaders.size() && stop; j++)
					stop &= !invader.getBoundsInParent().intersects(invaders.get(j).getBoundsInParent());	
			}
			InvaderThread invaderThread = new InvaderThread(this, invader, tank, DIFFICULT);
			invaderThread.setDaemon(true);
			invaderThread.start();
			invaders.add(invader);
		}
	}

	@FXML
	public void onKeyPressed(KeyEvent event) {
		if (KeyCode.RIGHT == event.getCode() && !tank.isMovingUpRight()) {
			tank.setMovingUpRight(true);
			createTankThread(1);
		}
		else if (KeyCode.LEFT == event.getCode() && !tank.isMovingDownLeft()) {
			tank.setMovingDownLeft(true);
			createTankThread(-1);
		}
		else if (KeyCode.SPACE == event.getCode()) {
			Sprite rocket = tank.getAttack("rocket.png");
			space.getChildren().add(rocket.getImageView());
			RocketThread rocketThread = new RocketThread(this, rocket, invaders);
			rocketThread.setDaemon(true);
			rocketThread.start();
		}
	}

	private void createTankThread(double layoutX) {
		TankThread tankThread = new TankThread(this, tank, layoutX);
		tankThread.setDaemon(true);
		tankThread.start();
	}

	@FXML
	public void onKeyReleased(KeyEvent event) {
		if (KeyCode.RIGHT == event.getCode())
			tank.setMovingUpRight(false);
		else if (KeyCode.LEFT == event.getCode())
			tank.setMovingDownLeft(false);
	}

	public void createMissileThread(Sprite attack) {
		if(random.nextBoolean() && random.nextBoolean() && random.nextBoolean()) {
			if(random.nextBoolean() && random.nextBoolean() && random.nextBoolean()) {
				space.getChildren().add(attack.getImageView());
				MissileThread missileThread = new MissileThread(this, attack, tank);
				missileThread.setDaemon(true);
				missileThread.start();
			}
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

	public void lostLife(Sprite missile) {
		if(lifes.getChildren().size() > 0) {
			lifes.getChildren().remove(0);
			tank.explosionByImpact();
			missile.explosionByImpact();
			if(lifes.getChildren().size() > 0)
				createTank("tank.png");
			else {
				try {
					endGame();
				} catch (FileNotFoundException e) {
				}
			}
		}
	}

	public void endGame() throws FileNotFoundException {
		stop = true;
		playAgain.setVisible(true);
		playAgain.requestFocus();
		if(Integer.parseInt(score.getText()) > Integer.parseInt(highScore.getText())) {
			try {
				ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("../high-score.txt"));
				oos.writeObject(score.getText());
				oos.close();
			} catch(IOException e) {
				
			}
		}
	}

	public boolean isStop() {
		return stop;
	}

	public void plusScore(int plus) {
		int score = Integer.parseInt(this.score.getText());
		this.score.setText(String.valueOf(score + plus));
	}
}