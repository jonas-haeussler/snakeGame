package snake.body;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Food { 
	private JLabel label;
	public int x;
	public int y;
	public Food(int x, int y) {
		this.x = x;
		this.y = y;
		ImageIcon foodIcon = new ImageIcon();
		try {
			foodIcon.setImage(ImageIO.read(new File("C:/Arbeit/snake.swing/src/snake/game/food.png")).getScaledInstance(30, 30, Image.SCALE_DEFAULT));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		label = new JLabel(foodIcon);
	}
	
	public JLabel getLabel() {
		return label;
	}
	public void removeLabel() {
		label = null;
	}
}
