package thread;

import javafx.application.Platform;
import model.Sprite;
import ui.SpaceInvadersGUI;

public class InvaderThread extends Thread {

	private SpaceInvadersGUI gui;
	private Sprite invader, tank;
	private long sleep;

	public InvaderThread(SpaceInvadersGUI gui, Sprite invader, Sprite tank, long sleep) {
		this.gui = gui;
		this.invader = invader;
		this.tank = tank;
		this.sleep = sleep;
	}

	@Override
	public void run() {
		int steps = 12;
		boolean direction = true;
		while(!invader.isImpact() && !gui.isStop()) {
			if(direction) {
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						invader.setLayoutX(invader.getLayoutX() + 2.5);
						invader.moveImage();
						if(invader.getBoundsInParent().intersects(tank.getBoundsInParent()))
							gui.lostLife(invader);
						if(invader.getLayoutY() > 500 - invader.getHeight()) {
							gui.lostLife(invader);
							gui.lostLife(invader);
							gui.lostLife(invader);
						}
						gui.createMissileThread(invader.getAttack("missile.png"));
					}
				});
			} else {
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						invader.setLayoutX(invader.getLayoutX() - 2.5);
						invader.moveImage();
						if(invader.getBoundsInParent().intersects(tank.getBoundsInParent()))
							gui.lostLife(invader);
						if(invader.getLayoutY() > 500 - invader.getHeight()) {
							gui.lostLife(invader);
							gui.lostLife(invader);
							gui.lostLife(invader);
						}
						gui.createMissileThread(invader.getAttack("missile.png"));
					}
				});
			}
			if(steps == 0) {
				steps = 12;
				direction = !direction;
				try { Thread.sleep(sleep);} catch (InterruptedException e) {}
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						invader.setLayoutY(invader.getLayoutY() + 10);
						invader.moveImage();
						if(invader.getBoundsInParent().intersects(tank.getBoundsInParent()))
							gui.lostLife(invader);
						if(invader.getLayoutY() > 500 - invader.getHeight()) {
							gui.lostLife(invader);
							gui.lostLife(invader);
							gui.lostLife(invader);
						}
						gui.createMissileThread(invader.getAttack("missile.png"));
					}
				});
			} else {
				steps--;
			}
			try { Thread.sleep(sleep);} catch (InterruptedException e) {}
		}
	}
}