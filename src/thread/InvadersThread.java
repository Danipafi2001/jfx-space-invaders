package thread;

import java.util.ArrayList;

import javafx.application.Platform;
import model.Sprite;
import ui.SpaceInvadersGUI;

public class InvadersThread extends Thread {

	private SpaceInvadersGUI gui;
	private ArrayList<Sprite> invaders;

	public InvadersThread(SpaceInvadersGUI g, ArrayList<Sprite> i) {
		gui = g;
		invaders = i;
	}

	@Override
	public void run() {
		int steps = 12;
		boolean direction = true;
		while(invaders.size() > 0) {
			if(direction) {
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						for(int i = 0; i < invaders.size(); i++) {
							invaders.get(i).updateLayout(2.5, 0);
							invaders.get(i).updateImage();
							if(invaders.get(i).getStatus())
								gui.missile(invaders.get(i).getShoot("missile.png"));
						}
					}
				});
			} else {
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						for(int i = 0; i < invaders.size(); i++) {
							invaders.get(i).updateLayout(-2.5, 0);
							invaders.get(i).updateImage();
							if(invaders.get(i).getStatus())
								gui.missile(invaders.get(i).getShoot("missile.png"));
						}
					}
				});
			}
			if(steps == 0) {
				steps = 12;
				direction = !direction;
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {}
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						for(int i = 0; i < invaders.size(); i++) {
							invaders.get(i).updateLayout(0, 10);
							if(invaders.get(i).getStatus())
								gui.missile(invaders.get(i).getShoot("missile.png"));
						}
					}
				});
			} else {
				steps--;
			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {}
		}
	}
}