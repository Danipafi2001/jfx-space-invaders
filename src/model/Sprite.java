package model;

import java.util.Random;

import javafx.geometry.Bounds;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Sprite {

	private String url;
	private ImageView imageView;
	private boolean movingUpRight, movingDownLeft, impact;

	public Sprite(String url) {
		this.url = url;
		imageView = new ImageView(new Image(getClass().getResourceAsStream("../" + url)));
		switch(url) {
		case "tank.png":
			imageView.setFitWidth(32.5);
			imageView.setFitHeight(20);
			break;
		case "rocket.png":
			imageView.setFitWidth(20);
			imageView.setFitHeight(20);
			break;
		case "missile.png":
			imageView.setFitWidth(5);
			imageView.setFitHeight(15);
			break;
		}
	}

	public Sprite() {
		Random random = new Random();
		switch(random.nextInt(3)) {
		case 0:
			url = "1-first-invader.png";
			imageView = new ImageView(new Image(getClass().getResourceAsStream("../" + url)));
			imageView.setFitWidth(27.5);
			imageView.setFitHeight(20);
			break;
		case 1:
			url = "1-second-invader.png";
			imageView = new ImageView(new Image(getClass().getResourceAsStream("../" + url)));
			imageView.setFitWidth(30);
			imageView.setFitHeight(20);
			break;
		case 2: 
			url = "1-third-invader.png";
			imageView = new ImageView(new Image(getClass().getResourceAsStream("../" + url)));
			imageView.setFitWidth(20);
			imageView.setFitHeight(20);
			break;
		}
	}

	public void moveImage() {
		if(impact == false) {
			if(url.charAt(0) == '1')
				url = '2' + url.substring(1);
			else
				url = '1' + url.substring(1);
			imageView.setImage(new Image(getClass().getResourceAsStream("../" + url)));
		}
	}

	public void explosionByImpact() {
		imageView.setImage(new Image(getClass().getResourceAsStream("../explosion.gif")));
		impact = true;
	}

	public Sprite getAttack(String url) {
		Sprite attack = new Sprite(url);
		switch(url) {
		case "rocket.png":
			attack.setLayoutX(getLayoutX() + (getWidth() / 2) - (attack.getWidth() / 2));
			attack.setLayoutY(getLayoutY() - attack.getHeight());
			break;
		case "missile.png":
			attack.setLayoutX(getLayoutX() + (getWidth() / 2) - (attack.getWidth() / 2));
			attack.setLayoutY(getLayoutY() + getHeight());
			break;
		}
		return attack;
	}

	public ImageView getImageView() {
		return imageView;
	}

	public double getWidth() {
		return imageView.getFitWidth();
	}

	public double getHeight() {
		return imageView.getFitHeight();
	}

	public double getLayoutX() {
		return imageView.getLayoutX();
	}

	public void setLayoutX(double layoutX) {
		imageView.setLayoutX(layoutX);
	}

	public double getLayoutY() {
		return imageView.getLayoutY();
	}

	public void setLayoutY(double layoutY) {
		imageView.setLayoutY(layoutY);
	}

	public boolean isMovingUpRight() {
		return movingUpRight;
	}

	public void setMovingUpRight(boolean movingUpRight) {
		this.movingUpRight = movingUpRight;
	}

	public boolean isMovingDownLeft() {
		return movingDownLeft;
	}

	public void setMovingDownLeft(boolean movingDownLeft) {
		this.movingDownLeft = movingDownLeft;
	}

	public Bounds getBoundsInParent() {
		return imageView.getBoundsInParent();
	}

	public boolean isImpact() {
		return impact;
	}
}
