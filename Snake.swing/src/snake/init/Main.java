package snake.init;

import java.awt.Toolkit;

import snake.game.Gameplay;
public class Main {
	public static void main(String[] args) {
		Gameplay gp = new Gameplay(Toolkit.getDefaultToolkit().getScreenSize().width, Toolkit.getDefaultToolkit().getScreenSize().height +50);
		boolean b = gp.run();
		while(b == true) {
			b = false;
			gp.getMainFrame().dispose();
			gp = new Gameplay(Toolkit.getDefaultToolkit().getScreenSize().width , Toolkit.getDefaultToolkit().getScreenSize().height + 50);
			b = gp.run();
		}
		System.exit(0);
	}
}
