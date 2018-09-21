package snake.gui;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;

import snake.body.Part;
import snake.game.Gameplay;

public class MainFrame extends JFrame { 
	/**
	 * 
	 */
	
	private static final long serialVersionUID = 1L;
	private JLabel level;
	public MainFrame(int x, int y, Gameplay gp) {
		level = new JLabel("Level: " + 0);
		setSize(x, y);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		getContentPane().setLayout(null);
		setLocation(0, -50);
		
		level.setFont(new Font("", Font.BOLD, 50));
		getContentPane().add(level);
		level.setLocation(5, 10);
		level.setSize(500,70);
		
		
		addKeyListener(gp);
		setVisible(true);
		getContentPane().setBackground(Color.RED);

	}
	
	public void addPart(Part part) {
		getContentPane().add(part.getLabel());
	}
	public void setLevel(int l) {
		level.setText("Level: " + l);
	}

}
