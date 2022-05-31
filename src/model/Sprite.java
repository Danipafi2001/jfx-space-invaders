package model;

import java.util.Random;

import javafx.geometry.Bounds;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Sprite {
	
	private ImageView iv;
	private String url;
	private boolean activeLeft, activeRight;
	
	public Sprite(ImageView i, String u) {
		iv = i;
		url = u;
		activeRight = false;
		activeLeft = false;
	}
	
	public void updateLayout(double layoutX, double layoutY) {
		iv.setLayoutX(getLayoutX() + layoutX);
		iv.setLayoutY(getLayoutY() + layoutY);
	}
	
	public void updateImage() {
		if(url.charAt(0) == '1')
			url = '2' + url.substring(1);
		else
			url = '1' + url.substring(1);
		iv.setImage(new Image(getClass().getResourceAsStream("../" + url)));
	}
	
	public ImageView getShoot(String url) {
		ImageView shoot = new ImageView(new Image(getClass().getResourceAsStream("../"+url)));
		shoot.setFitWidth(5);
		shoot.setFitHeight(15);
		shoot.setLayoutY(getLayoutY() + iv.getFitHeight());
		shoot.setLayoutX(getLayoutX() + (iv.getFitWidth() / 2) - (shoot.getFitWidth() / 2));
		return shoot;
	}
	
	public boolean getStatus() {
		Random r = new Random();
		return r.nextBoolean() && r.nextBoolean() && r.nextBoolean();
	}
	
	public void setImage(Image i) {
		iv.setImage(i);
	}
	
	public double getLayoutX() {
		return iv.getLayoutX();
	}
	
	public double getLayoutY() {
		return iv.getLayoutY();
	}
	
	public Bounds getBoundsInParent() {
		return iv.getBoundsInParent();
	}

	public boolean isActiveLeft() {
		return activeLeft;
	}
	
	public boolean isActiveRight() {
		return activeRight;
	}
	
	public void setActiveLeft(boolean a) {
		activeLeft = a;
	}
	
	public void setActiveRight(boolean a) {
		activeRight = a;
	}

	public double getFitWidth() {
		return iv.getFitWidth();
	}

	public double getFitHeight() {
		return iv.getFitHeight();
	}

	public ImageView getImageView() {
		return iv;
	}
}
