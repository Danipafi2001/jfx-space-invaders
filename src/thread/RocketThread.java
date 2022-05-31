package thread;

import java.util.ArrayList;

import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.Sprite;

public class RocketThread extends Thread {

	private ImageView rocket;
	private ArrayList<Sprite> invaders;

	public RocketThread(ImageView r, ArrayList<Sprite> i) {
		rocket = r;
		invaders = i;
	}

	@Override
	public void run() {
		while(rocket.getLayoutY() > (-1 * rocket.getFitHeight())) {
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					rocket.setLayoutY(rocket.getLayoutY() - 1);
					for(int i = 0; i < invaders.size(); i++) {
						if(rocket.getBoundsInParent().intersects(invaders.get(i).getBoundsInParent())) {
							rocket.setLayoutY(-1 * rocket.getFitHeight());
							invaders.get(i).setImage(new Image(getClass().getResourceAsStream("../explosion.gif")));
							invaders.remove(i);
							i = invaders.size();
						}
					}
				}
			});
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {}
		}
	}
}