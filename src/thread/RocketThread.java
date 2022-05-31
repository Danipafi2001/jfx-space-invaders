package thread;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import javafx.application.Platform;
import model.Sprite;
import ui.SpaceInvadersGUI;

public class RocketThread extends Thread {

	private SpaceInvadersGUI gui;
	private Sprite attack;
	private ArrayList<Sprite> enemies;

	public RocketThread(SpaceInvadersGUI gui, Sprite attack, ArrayList<Sprite> enemies) {
		this.gui = gui;
		this.attack = attack;
		this.enemies = enemies;
	}

	@Override
	public void run() {
		while(attack.getLayoutY() > 0 && !attack.isImpact() && !gui.isStop()) {
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					attack.setLayoutY(attack.getLayoutY() - 1);
					for(int i = 0; i < enemies.size() && !attack.isImpact(); i++) {
						if(attack.getBoundsInParent().intersects(enemies.get(i).getBoundsInParent())) {
							enemies.get(i).explosionByImpact();
							attack.explosionByImpact();
							enemies.get(i).explosionByImpact();
							enemies.remove(enemies.get(i));
							i = enemies.size();
							gui.plusScore(10);
						}
					}
					if(enemies.size() == 0) {
						try {
							gui.endGame();
						} catch (FileNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			});
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {}
		}
		attack.explosionByImpact();
	}
}