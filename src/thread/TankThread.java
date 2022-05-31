package thread;

import javafx.application.Platform;
import model.Sprite;
import ui.SpaceInvadersGUI;

public class TankThread extends Thread {

	private SpaceInvadersGUI gui;
	private Sprite tank;
	private double layoutX;

	public TankThread(SpaceInvadersGUI gui, Sprite tank, double layoutX) {
		this.gui = gui;
		this.tank = tank;
		this.layoutX = layoutX;
	}
	
	@Override
	public void run() {
		while((tank.isMovingUpRight() && layoutX > 0) || (tank.isMovingDownLeft() && layoutX < 0) && !gui.isStop()) {
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					if(layoutX>0) {
						if(tank.getLayoutX() + layoutX < 800 - tank.getWidth())
							tank.setLayoutX(tank.getLayoutX() + layoutX);
						else
							tank.setLayoutX(800 - tank.getWidth());
					} else {
						if(tank.getLayoutX() + layoutX > 0)
							tank.setLayoutX(tank.getLayoutX() + layoutX);
						else
							tank.setLayoutX(0);
					}
				}
			});
			try {
				Thread.sleep(5);
			} catch (InterruptedException e) {}
		}
	}
}