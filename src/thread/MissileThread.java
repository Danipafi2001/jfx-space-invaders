package thread;

import javafx.application.Platform;
import model.Sprite;
import ui.SpaceInvadersGUI;

public class MissileThread extends Thread {

	private SpaceInvadersGUI gui;
	private Sprite missile, tank;
	
	public MissileThread(SpaceInvadersGUI gui, Sprite missile, Sprite tank) {
		this.gui = gui;
		this.missile = missile;
		this.tank = tank;
	}

	@Override
	public void run() {
		while(missile.getLayoutY() < 500 && !missile.isImpact() && !gui.isStop()) {
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					missile.setLayoutY(missile.getLayoutY() + 1);
					if(missile.getBoundsInParent().intersects(tank.getBoundsInParent())) {
						if(!missile.isImpact())
							gui.lostLife(missile);
					}
				}
			});
			try { Thread.sleep(5);} catch (InterruptedException e) {}
		}
		missile.explosionByImpact();
	}
}