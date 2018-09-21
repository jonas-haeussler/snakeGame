package snake.body;

import java.awt.Image;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Part {	
	private JLabel headLabel;
	public int x;
	public int y;
	public boolean isHead;
	public boolean isLast;
	public int direction;
	public Part (boolean isHead, boolean isLast, int x, int y) {
		this.isHead = isHead;
		this.isLast = isLast;
		this.x = x;
		this.y = y;
		try {
			
			ImageIcon snakeIcon = new ImageIcon();
			if(isHead) {
				snakeIcon.setImage(ImageIO.read(new File("C:/Arbeit/snake.swing/src/snake/game/snake.png")).getScaledInstance(40, 40 , Image.SCALE_DEFAULT));
			}
			else {
				snakeIcon.setImage(ImageIO.read(new File("C:/Arbeit/snake.swing/src/snake/game/tail.png")).getScaledInstance(40, 40, Image.SCALE_DEFAULT));
			}
			headLabel = new JLabel(snakeIcon);
//			headLabel.setLocation(x, y);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public JLabel getLabel() {
		return headLabel;
	}
	
}
