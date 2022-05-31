package thread;

import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class MissileThread extends Thread {

	private ImageView missile, tank;

	public MissileThread(ImageView m, ImageView t) {
		missile = m;
		tank = t;
	}

	@Override
	public void run() {
		while(missile.getLayoutY() < (600 + missile.getFitHeight())) {
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					missile.setLayoutY(missile.getLayoutY() + 1);
					if(missile.getBoundsInParent().intersects(tank.getBoundsInParent())) {
						missile.setLayoutY(600 + missile.getFitHeight());
						tank.setImage(new Image(getClass().getResourceAsStream("../explosion.gif")));
					}
				}
			});
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {}
		}
	}
}