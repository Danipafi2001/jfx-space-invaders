package thread;

import javafx.application.Platform;
import javafx.scene.layout.Pane;
import model.Sprite;

public class TankThread extends Thread {

	private Sprite tank;
	private Pane pane;
	private double layoutX;

	public TankThread(Sprite t, Pane p, double l) {
		tank = t;
		pane = p;
		layoutX = l;
	}

	@Override
	public void run() {
		while((tank.isActiveLeft() && layoutX < 0) || (tank.isActiveRight() && layoutX >= 0)) {
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					if(layoutX>=0) {
						if(tank.getLayoutX() + layoutX < pane.getWidth() - tank.getFitWidth())
							tank.updateLayout(layoutX, 0);
						else
							tank.updateLayout(pane.getWidth() - tank.getFitWidth() - tank.getLayoutX(), 0);
					} else {
						if(tank.getLayoutX() + layoutX > 0)
							tank.updateLayout(layoutX, 0);
						else
							tank.updateLayout(0, 0);
					}
				}
			});
			try {
				Thread.sleep(5);
			} catch (InterruptedException e) {}
		}
	}
}