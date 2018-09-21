package snake.game;

import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JOptionPane;

import snake.body.Food;
import snake.body.Part;
import snake.gui.MainFrame;

public class Gameplay implements KeyListener {
	private int foodCount;
	private MainFrame mf;
	private LinkedList<Part> snakeParts = new LinkedList<>();
	Food food;
	ArrayList<Point> turnPoints;
	private long time;
	private boolean isAllowed = true;
	private int speed;
	private Clip clip;
	private Clip punch;
	private int width;
	private int height;
	private int level;

	
	public Gameplay(int x, int y) {
		width = x;
		height = y;
		try {
			File file = new File("src/snake/game/song.wav");
			File file2 = new File("src/snake/game/punch.wav");
			AudioInputStream music = AudioSystem.getAudioInputStream(file);
			AudioFormat af = music.getFormat();
			int sizeOfMusic = (int) (af.getFrameSize() * music.getFrameLength());
			byte[] audio = new byte[sizeOfMusic];
			DataLine.Info info = new DataLine.Info(Clip.class, af,sizeOfMusic);
			music.read(audio, 0, sizeOfMusic);
			AudioInputStream sound = AudioSystem.getAudioInputStream(file2);
			AudioFormat format = sound.getFormat();
			int size2 = (int) (format.getFrameSize() * sound.getFrameLength());
			byte[] b = new byte[size2];
			DataLine.Info info2 = new DataLine.Info(Clip.class, format,size2);
			sound.read(b, 0, size2);
			punch = (Clip) AudioSystem.getLine(info2);
            punch.open(format, b, 0, size2);
            clip = (Clip) AudioSystem.getLine(info);
            clip.open(af, audio, 0, sizeOfMusic);
            clip.start();
		} catch (UnsupportedAudioFileException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		speed = 1;
		time = System.currentTimeMillis();
		turnPoints = new ArrayList<Point>();
		mf = new MainFrame(x, y, this);
		Part snakeHead = new Part(true, false, x/2, y/2);
		Part snakeTail = new Part(false, true, x/2 + 40, y/2);
		snakeParts.add(snakeHead);
		snakeParts.add(snakeTail);
		mf.addPart(snakeHead);
		mf.addPart(snakeTail);

		
		snakeHead.getLabel().setSize(40, 40);
		snakeHead.getLabel().setLocation(x/2,y/2);
		snakeTail.getLabel().setSize(40,40);
		snakeTail.getLabel().setLocation(x/2+40,y/2);
		

		
	}
	public boolean run() {
		while(true) {
			if(foodCount == 0) {
				food = new Food((int)(Math.random()*(width - 200)), (int)(Math.random()*(height - 200)));
				mf.add(food.getLabel());
				food.getLabel().setSize(40, 40);
				food.getLabel().setLocation(food.x, food.y);
				foodCount++;
			}
			RunDirections.move(snakeParts.getFirst().direction, snakeParts.getFirst(), speed);
			snakeParts.getFirst().getLabel().setLocation(snakeParts.getFirst().x, snakeParts.getFirst().y);
			for (int i = 1; i < snakeParts.size(); i++) {
				RunDirections.move(snakeParts.get(i).direction, snakeParts.get(i),speed);
				snakeParts.get(i).getLabel().setLocation(snakeParts.get(i).x, snakeParts.get(i).y);
				for (int j = 0; j < turnPoints.size(); j++) {
					if(snakeParts.get(i).x == turnPoints.get(j).x && snakeParts.get(i).y == turnPoints.get(j).y) {
						snakeParts.get(i).direction = snakeParts.get(i-1).direction;
						if(i == snakeParts.size()-1) {
							turnPoints.remove(0);
						}
					}
				}
			}
			if(time <= System.currentTimeMillis() - 200) {
				isAllowed = true;
			}
			if(food.getLabel().getBounds().intersects(snakeParts.getFirst().getLabel().getBounds())) {
				if(speed < 10)
					speed++;
				food.x = (int)(Math.random()*(width - 200));
				food.y = (int)(Math.random()*(height - 200));
				food.getLabel().setLocation(food.x, food.y);
				Part tail = new Part(false, true, 0 , 0);
				if(snakeParts.getLast().direction == 0) {
					tail.y = snakeParts.getLast().y;
					tail.x = snakeParts.getLast().x + 40;
				}
				if(snakeParts.getLast().direction == 1) {
					tail.y = snakeParts.getLast().y + 40;
					tail.x = snakeParts.getLast().x;
				}
				if(snakeParts.getLast().direction == 2) {
					tail.y = snakeParts.getLast().y;
					tail.x = snakeParts.getLast().x - 40;
				}
				if(snakeParts.getLast().direction == 3) {
					tail.y = snakeParts.getLast().y - 40;
					tail.x = snakeParts.getLast().x;
				}
				tail.direction = snakeParts.getLast().direction;
				snakeParts.getLast().isLast = false;
				snakeParts.add(tail);
				mf.add(tail.getLabel());
				tail.getLabel().setSize(40,40);
				tail.getLabel().setLocation(tail.x, tail.y);
				mf.setLevel(++level);
				
			}
			if(snakeParts.getFirst().x >= width || snakeParts.getFirst().y >= height || snakeParts.getFirst().x <= 0 || snakeParts.getFirst().y <= 0) {
				clip.stop();
				punch.start();
				int msg = JOptionPane.showConfirmDialog(mf, "Wanna play again?");
				if(msg == JOptionPane.YES_OPTION)
					return true;
				return false;
			}
			for (int i = 2; i < snakeParts.size(); i++) {
				if(snakeParts.getFirst().x/40 == snakeParts.get(i).x/40 && snakeParts.getFirst().y/40 == snakeParts.get(i).y/40) {
					clip.stop();
					punch.start();
					int msg = JOptionPane.showConfirmDialog(mf, "Wanna play again?");
					if(msg == JOptionPane.YES_OPTION)
						return true;
					return false;
				}
			}
		}
	}
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void keyReleased(KeyEvent e) {
		if(isAllowed) {
			if(e.getKeyCode() == KeyEvent.VK_RIGHT && snakeParts.getFirst().direction != 2) {
				snakeParts.getFirst().direction = 2;
				turnPoints.add(new Point(snakeParts.getFirst().x, snakeParts.getFirst().y));
			}
			else if(e.getKeyCode() == KeyEvent.VK_LEFT && snakeParts.getFirst().direction != 0) {
				snakeParts.getFirst().direction = 0;
				turnPoints.add(new Point(snakeParts.getFirst().x, snakeParts.getFirst().y));
	
			}
			else if(e.getKeyCode() == KeyEvent.VK_UP && snakeParts.getFirst().direction != 1) {
				snakeParts.getFirst().direction = 1;
				turnPoints.add(new Point(snakeParts.getFirst().x, snakeParts.getFirst().y));
	
			}
			else if(e.getKeyCode() == KeyEvent.VK_DOWN && snakeParts.getFirst().direction != 3) {
				snakeParts.getFirst().direction = 3;
				turnPoints.add(new Point(snakeParts.getFirst().x, snakeParts.getFirst().y));
	
			}
			isAllowed = false;
			time = System.currentTimeMillis();
		}
	}
	public LinkedList<Part> getSnakeParts() {
		return snakeParts;
	}
	public MainFrame getMainFrame() {
		return mf;
	}
}
