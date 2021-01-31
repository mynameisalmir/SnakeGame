
import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class SnakeThread extends Thread {

	Random r = new Random();
	private List<MyButton> snake;
	private List<Boolean> directions;
	private JLabel scoreLbl;
	private JPanel contentPane;
	private MyButton apple;
	private int score;
	private int snakeSize;
	private int movementSpeed;
	private int frameHeight;
	private int frameWidth;
	private final int SPEED_DECREASE = 10;

	public SnakeThread(JPanel contentPane, int snakeSize, JLabel scoreLbl, int frameWidth, int frameHeight) {
		super();
		this.frameWidth = frameWidth;
		this.frameHeight = frameHeight;
		this.contentPane = contentPane;
		this.snakeSize = snakeSize;
		this.movementSpeed = 150;
		this.scoreLbl = scoreLbl;
		this.score = 0;

		// Bootstrap game
		initList();
		initDirections();
		initiListener();
		generateApple();
	}

	public void run() {
		while (true) {
			moveSnake();
		}
	}

	// Metoda koja puni listu dugmadima
	public void initList() {
		snake = new ArrayList<>();
		int startX = frameWidth / 2 - snakeSize; // Postavi pocetnu poziciju zmije na sredinu
		int startY = frameHeight / 2 - snakeSize;

		MyButton b = new MyButton(startX, startY, snakeSize);
		contentPane.add(b);
		snake.add(b);
	}

	public void generateApple() {
		// Ako postoji jabuka makni je prije nego generises novu
		if (apple != null) {
			contentPane.remove(apple);
			contentPane.repaint();
		}

		// Promijeniti zakucane vrijednosti
		MyButton apple = new MyButton(r.nextInt(frameWidth - 50) + snakeSize, r.nextInt(frameHeight - 50) + snakeSize,
				snakeSize);
		apple.setBackground(Color.RED);
		this.apple = apple;
		contentPane.add(this.apple);
	}

	public void checkCollision(MyButton b) {
		Point snakeCenter = new Point(b.getX() + snakeSize / 2, b.getY() + snakeSize / 2);
		Point appleCenter = new Point(apple.getX() + snakeSize / 2, apple.getY() + snakeSize / 2);

		int distance = (int) Math.sqrt((appleCenter.x - snakeCenter.x) * (appleCenter.x - snakeCenter.x)
				+ (appleCenter.y - snakeCenter.y) * (appleCenter.y - snakeCenter.y));

		if (distance < snakeSize) {
			addLast();
			generateApple();
			setScore();
		}
	}

	public void setScore() {
		score++;
		scoreLbl.setText("Score: " + this.score);

		// Na svaki pomjeraj skora za 5 povecaj brzinu za vrijednost smanjenja
		if (score % 5 == 0) {
			this.movementSpeed -= SPEED_DECREASE;
		}
	}

	public void collidedItself() {
		for (MyButton myButton : snake) {
			if (myButton != snake.get(snake.size() - 1) && snake.get(snake.size() - 1).getX() == myButton.getX()
					&& snake.get(snake.size() - 1).getY() == myButton.getY()) {
				interrupt();
			}
		}
	}

	public void addLast() {
		MyButton b = handleMovement(directions.get(0), directions.get(1), directions.get(2), directions.get(3));

		snake.add(b);
		contentPane.add(snake.get(snake.size() - 1));
		collidedItself();
	}

	public void removeFirst() {
		contentPane.remove(snake.get(0));
		snake.remove(0);
	}

	public void moveSnake() {
		MyButton b = handleMovement(directions.get(0), directions.get(1), directions.get(2), directions.get(3));

		// **************************** SIMULACIJA KRETANJA *********************
		addLast(); // Dodaj na kraj novo dugme
		collidedItself(); // Provjeri da li je doslo do sudaranja sa drugim
		removeFirst(); // Makni s pocetka dugme

		contentPane.repaint();
		checkCollision(b);

		try {
			sleep(movementSpeed);
		} catch (InterruptedException e) {
			gameOver();
			stop();
		}

		// *************************************************************************

	}

	// Metoda koja postavlja kretanje dugmeta u zavisnosti od pravca kretanja
	public MyButton handleMovement(boolean up, boolean down, boolean left, boolean right) {
		MyButton b = null;

		if (right) {
			b = new MyButton(snake.get(snake.size() - 1).getX() + snakeSize, snake.get(snake.size() - 1).getY(),
					snakeSize);
		} else if (down) {
			b = new MyButton(snake.get(snake.size() - 1).getX(), snake.get(snake.size() - 1).getY() + snakeSize,
					snakeSize);
		} else if (left) {
			b = new MyButton(snake.get(snake.size() - 1).getX() - snakeSize, snake.get(snake.size() - 1).getY(),
					snakeSize);
		} else if (up) {
			b = new MyButton(snake.get(snake.size() - 1).getX(), snake.get(snake.size() - 1).getY() - snakeSize,
					snakeSize);
		}

		handleWallCollision(b);

		return b;
	}

	public void handleWallCollision(MyButton b) {
		if (b.getX() < 0) {
			b.setBounds(contentPane.getWidth() - snakeSize, b.getY(), snakeSize, snakeSize);
			return;
		}

		if (b.getX() > contentPane.getWidth() - snakeSize) {
			b.setBounds(0, b.getY(), snakeSize, snakeSize);
			return;
		}

		if (b.getY() < 0) {
			b.setBounds(b.getX(), contentPane.getHeight() - snakeSize, snakeSize, snakeSize);
			return;
		}

		if (b.getY() > contentPane.getHeight() - snakeSize) {
			b.setBounds(b.getX(), 0, snakeSize, snakeSize);
			return;
		}

	}

	// Inicijalizovanje liste dozvoljenih smjerova
	public void initDirections() {
		directions = new ArrayList<>();

		// UP
		directions.add(false);
		// DOWN
		directions.add(false);
		// LEFT
		directions.add(false);
		// RIGHT
		directions.add(true);
	}

	// Listener koji slusa na pritisak tipke
	public void initiListener() {
		contentPane.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void keyPressed(KeyEvent e) {

				switch (e.getKeyCode()) {
				case KeyEvent.VK_DOWN:
					// Ako zmija ide gore a korisnik klikne strelicu dolje prekini izvrsavanje
					if (directions.get(0)) {
						return;
					}
					setDirection(1);
					break;
				case KeyEvent.VK_UP:
					// Ako zmija ide dolje a korisnik klikne strelicu gore prekini izvrsavanje
					if (directions.get(1)) {
						return;
					}
					setDirection(0);
					break;
				case KeyEvent.VK_RIGHT:
					// Ako zmija ide lijevo a korisnik klikne strelicu desno prekini izvrsavanje
					if (directions.get(2)) {
						return;
					}
					setDirection(3);
					break;
				case KeyEvent.VK_LEFT:
					// Ako zmija ide desno a korisnik klikne strelicu lijevo prekini izvrsavanje
					if (directions.get(3)) {
						return;
					}
					setDirection(2);
					break;
				}
			}
		});
	}

	// Metoda koja postavlja smjer kretanja
	public void setDirection(int direction) {
		for (int i = 0; i < directions.size(); i++) {
			if (i == direction) {
				directions.set(i, true);
				continue;
			}
			directions.set(i, false);
		}
	}
	
	public void gameOver() {
		// Dugme za start
		JButton newGame = new JButton("New Game");
		newGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				initNewGame(newGame);
			}
		});
		newGame.setBackground(Color.RED);
		newGame.setBounds(frameWidth / 2 - 100 / 2, frameHeight / 2 - 50, 100, 50);
		contentPane.add(newGame);
		contentPane.repaint();
	}

	public void initNewGame(JButton newGame) {
		newGame.setVisible(false);
		contentPane.removeAll();

		// Score label
		scoreLbl = new JLabel("Score: 0");
		scoreLbl.setFont(new Font("Tahoma", Font.PLAIN, 20));
		scoreLbl.setForeground(Color.WHITE);
		scoreLbl.setBounds(10, 10, 117, 25);
		contentPane.add(scoreLbl);

		// Zapocinjanje igrice
		SnakeThread t = new SnakeThread(contentPane, 10, scoreLbl, frameWidth, frameHeight);
		t.start();
	}

}
