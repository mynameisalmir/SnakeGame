import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class GUI extends JFrame {

	private JPanel contentPane;
	private JButton startBtn;
	private JLabel scoreLbl;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI frame = new GUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public GUI() {
		int frameWidth = 450;
		int frameHeight = 300;
		int buttonWidth = 100;
		int buttonHeight = 50;
		
		this.setTitle("Snake by mynameisalmir v1.0");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, frameWidth, frameHeight);
		contentPane = new JPanel();
		this.setResizable(false);
		contentPane.setBackground(Color.BLACK);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.setFocusable(true);
		contentPane.requestFocus();

		// Score label
		scoreLbl = new JLabel("Score: 0");
		scoreLbl.setFont(new Font("Tahoma", Font.PLAIN, 20));
		scoreLbl.setForeground(Color.WHITE);
		scoreLbl.setBounds(10, 10, 117, 25);
		contentPane.add(scoreLbl);
		
		// Dugme za start
		startBtn = new JButton("START");
		startBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				startBtn.setVisible(false);
				
				// Zapocinjanje igrice
				SnakeThread t = new SnakeThread(contentPane, 10, scoreLbl, frameWidth, frameHeight);
				t.start();
			}
		});
		startBtn.setBackground(Color.GREEN);
		startBtn.setBounds(frameWidth / 2 - buttonWidth / 2, frameHeight / 2 - buttonHeight, buttonWidth, buttonHeight);
		contentPane.add(startBtn);
		
		
	}
}
