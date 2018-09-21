package snake.game;

import java.awt.Point;

import snake.body.Part;

public class RunDirections {

	public static void move(int direction, Part part, int speed) {
			Point loc = new Point();
				loc.x = part.x;
				loc.y = part.y;
				if(direction == 0) {
						part.x = part.getLabel().getLocation().x - 20;
				}
				else if(direction == 1) {
						part.y = part.getLabel().getLocation().y - 20;
				}
				else if(direction == 2) {
						part.x = part.getLabel().getLocation().x + 20;
				}
				else if(direction == 3) {
						part.y = part.getLabel().getLocation().y + 20;
				}
			try {
				Thread.sleep(10-speed, 1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

}
