import java.awt.Color;

import javax.swing.JButton;

public class MyButton extends JButton {

	private int positionX;
	private int positionY;
	protected boolean up = false;
	protected boolean down = false;
	protected boolean left = false;
	protected boolean right = true;

	public MyButton(int positionX, int positionY, int snakeSize) {
		super();
		this.positionX = positionX;
		this.positionY = positionY;
		this.setBounds(positionX, positionY, snakeSize, snakeSize);
		this.setBackground(Color.GREEN);
	}

	public int getPositionX() {
		return positionX;
	}

	public void setPositionX(int positionX) {
		this.positionX = positionX;
	}

	public int getPositionY() {
		return positionY;
	}

	public void setPositionY(int positionY) {
		this.positionY = positionY;
	}

}
