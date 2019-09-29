import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.JButton;
import javax.swing.Timer;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JSlider;
import java.awt.event.MouseMotionAdapter;
import javax.swing.ImageIcon;
import javax.swing.Box;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

/*
 * Displays generations of John Conway's Game of Life.
 * Allows a user of the program to step through one generation
 * at a time or to run the generations based on a timer.
 */
public class GameOfLifeDisplay extends JFrame {

	private JPanel contentPane;
	private JLabel txtGeneration = new JLabel();
	private int delay = 100; // This instance is used to change the speed of the Timer.
	private boolean draw = false; // This instance is used to check whether the Eraser Tool or Draw Tool is chosen by the user.
	private boolean rainbow = false; // This SPECIAL instance is used to check whether the rainbow tool has been activated by the user.
	private Color rainbowColor = Color.red; // This instance keeps track of the current color used in the rainbow tool.
	
	/*
	 * This method changes "rainbowColor" to the next corresponding Color. 
	 */
	private void rainbowColorChange() {
		if (rainbowColor == Color.red) rainbowColor = Color.orange;
		else if (rainbowColor == Color.orange) rainbowColor = Color.yellow;
		else if (rainbowColor == Color.yellow) rainbowColor = Color.green;
		else if (rainbowColor == Color.green) rainbowColor = Color.blue;
		else if (rainbowColor == Color.blue) rainbowColor = Color.magenta;
		else if (rainbowColor == Color.magenta) rainbowColor = Color.pink;
		else if (rainbowColor == Color.pink) rainbowColor = Color.black;
		else if (rainbowColor == Color.black) rainbowColor = Color.red;
	}
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GameOfLifeDisplay frame = new GameOfLifeDisplay();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame. Adds a button panel to the frame and
	 * initializes the usage of each button.
	 */
	public GameOfLifeDisplay() {
		GameOfLife g = new GameOfLife(50);		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 900, 800);
		contentPane = new JPanel();
		contentPane.setForeground(Color.BLACK);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		/*
		 * Adds the panel which displays the Game of Life
		 * board. See the BoardPanel class for details.
		 */
		JPanel boardPanel = new BoardPanel(g);
		boardPanel.setForeground(Color.BLACK);
		boardPanel.setBackground(Color.BLACK);
		
		/*
		 * Creates the button panel
		 */
		JPanel buttonPanel = new JPanel();
		contentPane.add(buttonPanel, BorderLayout.NORTH);
		
		Box verticalBox = Box.createVerticalBox();
		buttonPanel.add(verticalBox);
		
		/*
		 * Creates a button that delegates to the "randomize()" method in the BoardPanel class. The BoardPanel
		 * is then repainted. 
		 */
		JButton btnRandomize = new JButton("Randomize");
		btnRandomize.setFont(new Font("Times New Roman", Font.BOLD, 15));
		buttonPanel.add(btnRandomize);
		btnRandomize.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				((BoardPanel) boardPanel).randomize();
				repaint();
			}
		});
		
		/*
		 * Creates a button that delegates to the "nextGen()" method in the GameOfLife class. The method also
		 * updates "txtGeneration" to display the new generation number. If "rainbow" is true, then the 
		 * method will set the foreground color of boardPanel to "rainbowColor" and then call "rainbowColorChange()". 
		 * The BoardPanel is then repainted. 
		 */
		JButton nextGenButton = new JButton("Next Generation");
		nextGenButton.setFont(new Font("Times New Roman", Font.BOLD, 15));
		buttonPanel.add(nextGenButton);
		nextGenButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				g.nextGen();
				txtGeneration.setText("Gen " + g.getGenNum());
				if (rainbow) {
					boardPanel.setForeground(rainbowColor);
					rainbowColorChange();
				}
				repaint();
			}
			
		});
		
		/*
		 * Creates a button that will delegate to "clear()" in the GameOfLife class, set "txtGeneration" back to "Gen 1", 
		 * and then repaints the BoardPanel. 
		 */
		JButton btnClear = new JButton("Clear");
		btnClear.setFont(new Font("Times New Roman", Font.BOLD, 15));
		buttonPanel.add(btnClear);
		btnClear.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				g.clear();
				txtGeneration.setText("Gen " + g.getGenNum());
				repaint();
			}
			
		});
		
		/*
		 * Creates a button that switches between "Draw Tool" and "Erase Tool" whenever the user presses it, and
		 * correspondingly switches "draw" between true and false.
		 */
		JButton btnDraworErase = new JButton("Draw Tool");
		btnDraworErase.setFont(new Font("Times New Roman", Font.BOLD, 15));
		buttonPanel.add(btnDraworErase);
		btnDraworErase.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if((btnDraworErase).getText().equals("Draw Tool")) {
					btnDraworErase.setText("Erase Tool");
					draw = true;
				}
				else {
					btnDraworErase.setText("Draw Tool");
					draw = false;
				}
			}
		});
		
		/*
		 * creates a Timer and defines what will occur when
		 * it is run when the user clicks the "start" button
		 * 
		 * Note that if "rainbow" is true, the foreground color of boardPaneil will be set to "rainbowColor" and
		 * then rainbowColorChange() will be called. 
		 */
		Timer timer = new Timer(delay, new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO what should happen each time the Timer is fired off
				g.nextGen();
				txtGeneration.setText("Gen " + g.getGenNum());
				if (rainbow) {
					boardPanel.setForeground(rainbowColor);
					rainbowColorChange();
				}
				repaint();
			}
			
		});
		

		/*
		 * creates a button that allows the game to run on 
		 * a timer. The label toggles between "Start" and "Stop"
		 */
		JButton startStopButton = new JButton("Start");
		startStopButton.setFont(new Font("Times New Roman", Font.BOLD, 15));
		buttonPanel.add(startStopButton);
		startStopButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(startStopButton.getText().equals("Start")){
					startStopButton.setText("Stop");
					timer.start();
					// TODO start the generations 
				}
				else{
					startStopButton.setText("Start");
					timer.stop();
					// TODO stop the generations
				}
				
			}
			
		});
		
		/*
		 * Creates a label that indicates that the following slider is for adjusting the speed.
		 */
		JLabel lblSpeed = new JLabel("Speed:");
		lblSpeed.setFont(new Font("Times New Roman", Font.BOLD, 15));
		buttonPanel.add(lblSpeed);		
		
		/*
		 * Creates a slider that is used to change the value of "delay" proportionally to the location of the slider, 
		 * which is used in Timer. 
		 */
		JSlider slider = new JSlider();
		buttonPanel.add(slider);
		slider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				delay = 2000 / (slider.getValue() + 1);
				timer.setDelay(delay);
			}
		});
		
		/*
		 * displays the generation number
		 */
		txtGeneration.setText("Gen 1");
		buttonPanel.add(txtGeneration);
		txtGeneration.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 15));
		
		/*
		 * Creates a MouseEvent that checks to see if the user has dragged their mouse across one or more Cells. 
		 * If "draw" is true, the Cell's status will become Cell.ALIVE. If false, the Cell's status will become
		 * Cell.DEAD. The BoardPanel is then repainted. 
		 */
		boardPanel.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent arg0) {
				if(draw) {
					int scalex = (boardPanel.getWidth()) / g.getWidth();
					int scaley = (boardPanel.getHeight()) / g.getHeight();

					int x = arg0.getX() / scalex;
					int y = arg0.getY() / scaley;
					g.reviveCell(x, y);
					repaint();
				}
				else {
					int scalex = (boardPanel.getWidth()) / g.getWidth();
					int scaley = (boardPanel.getHeight()) / g.getHeight();

					int x = arg0.getX() / scalex;
					int y = arg0.getY() / scaley;
					g.killCell(x, y);
					repaint();
				}
			}
		});
		
		/* 
		 * Creates a MouseEvent that checks to see if the user has clicked on a specific Cell. If the Cell was
		 * previously alive, it will become dead, and vice versa. 
		 */
		boardPanel.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				int scalex = (boardPanel.getWidth()) / g.getWidth();
				int scaley = (boardPanel.getHeight()) / g.getHeight();
				
				int x = arg0.getX() / scalex;
				int y = arg0.getY() / scaley;
				g.toggleCell(x, y);
				
				repaint();
			}
		});
		
		contentPane.add(boardPanel, BorderLayout.CENTER);
		
		/*
		 * Adds the panel for the colors
		 */
		JPanel colorPanel = new JPanel();
		contentPane.add(colorPanel, BorderLayout.SOUTH);
		
		
		/*
		 * Creates a button that switches between "RAINBOW (off)" and "RAINBOW (on)". "rainbow" will switch between 
		 * true and false correspondingly. 
		 */
		JButton btnRainbowoff = new JButton("RAINBOW (off)");
		btnRainbowoff.setFont(new Font("Times New Roman", Font.BOLD, 15));
		btnRainbowoff.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (btnRainbowoff.getText().equals("RAINBOW (off)")) {
					btnRainbowoff.setText("RAINBOW (on)");
					rainbow = true;
				}
				else {
					btnRainbowoff.setText("RAINBOW (off)");
					rainbow = false;
				}
			}
		});
		colorPanel.add(btnRainbowoff);
		
		/*
		 * Creates a button that sets the foreground color of boardPanel to Color.pink. Note that 
		 * this button will also set "rainbow" to false. 
		 */
		JButton btnPink = new JButton("PINK");
		btnPink.setFont(new Font("Times New Roman", Font.BOLD, 15));
		btnPink.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				boardPanel.setForeground(Color.pink);
				rainbow = false;
				btnRainbowoff.setText("RAINBOW (off)");
				repaint();
			}
		});
		btnPink.setForeground(Color.PINK);
		colorPanel.add(btnPink);
		
		/*
		 * Creates a button that sets the foreground color of boardPanel to Color.green. Note that 
		 * this button will also set "rainbow" to false. 
		 */
		JButton btnGreen = new JButton("GREEN");
		btnGreen.setFont(new Font("Times New Roman", Font.BOLD, 15));
		btnGreen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boardPanel.setForeground(Color.green);
				repaint();
				rainbow = false;
				btnRainbowoff.setText("RAINBOW (off)");
			}
		});
		btnGreen.setForeground(Color.GREEN);
		colorPanel.add(btnGreen);
		
		/*
		 * Creates a button that sets the foreground color of boardPanel to Color.blue. Note that 
		 * this button will also set "rainbow" to false.
		 */
		JButton btnBlue = new JButton("BLUE");
		btnBlue.setFont(new Font("Times New Roman", Font.BOLD, 15));
		btnBlue.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boardPanel.setForeground(Color.blue);
				repaint();
				rainbow = false;
				btnRainbowoff.setText("RAINBOW (off)");
			}
		});
		btnBlue.setForeground(Color.BLUE);
		colorPanel.add(btnBlue);
		
		/*
		 * Creates a button that sets the foreground color of boardPanel to Color.red. Note that 
		 * this button will also set "rainbow" to false.
		 */
		JButton btnRed = new JButton("RED");
		btnRed.setFont(new Font("Times New Roman", Font.BOLD, 15));
		btnRed.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boardPanel.setForeground(Color.red);
				repaint();
				rainbow = false;
				btnRainbowoff.setText("RAINBOW (off)");
			}
		});
		btnRed.setForeground(Color.RED);
		colorPanel.add(btnRed);
		
		/*
		 * Creates a button that sets the foreground color of boardPanel to Color.black. Note that 
		 * this button will also set "rainbow" to false.
		 */
		JButton btnBlack = 	new JButton("BLACK");
		btnBlack.setFont(new Font("Times New Roman", Font.BOLD, 15));
		btnBlack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boardPanel.setForeground(Color.black);
				repaint();
				rainbow = false;
				btnRainbowoff.setText("RAINBOW (off)");
			}
		});
		colorPanel.add(btnBlack);
		
		/*
		 * Creates a button that will create a "glider gun" in specific coordinates. The BoardPanel is 
		 * then repainted.
		 */
		JButton btnGliderGun = new JButton("GLIDER GUN");
		btnGliderGun.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				g.setStatus(3, 7, Cell.ALIVE);
				g.setStatus(3, 8, Cell.ALIVE);
				g.setStatus(4, 7, Cell.ALIVE);
				g.setStatus(4, 8, Cell.ALIVE);
				g.setStatus(13, 7, Cell.ALIVE);
				g.setStatus(13, 8, Cell.ALIVE);
				g.setStatus(13, 9, Cell.ALIVE);
				g.setStatus(14, 6, Cell.ALIVE);
				g.setStatus(14, 10, Cell.ALIVE);
				g.setStatus(15, 5, Cell.ALIVE);
				g.setStatus(16, 5, Cell.ALIVE);
				g.setStatus(15, 11, Cell.ALIVE);
				g.setStatus(16, 11, Cell.ALIVE);
				g.setStatus(17, 8, Cell.ALIVE);
				g.setStatus(18, 6, Cell.ALIVE);
				g.setStatus(18, 10, Cell.ALIVE);
				g.setStatus(19, 7, Cell.ALIVE);
				g.setStatus(19, 8, Cell.ALIVE);
				g.setStatus(19, 9, Cell.ALIVE);
				g.setStatus(20, 8, Cell.ALIVE);
				g.setStatus(23, 7, Cell.ALIVE);
				g.setStatus(23, 6, Cell.ALIVE);
				g.setStatus(23, 5, Cell.ALIVE);
				g.setStatus(24, 7, Cell.ALIVE);
				g.setStatus(24, 6, Cell.ALIVE);
				g.setStatus(24, 5, Cell.ALIVE);
				g.setStatus(25, 4, Cell.ALIVE);
				g.setStatus(25, 8, Cell.ALIVE);
				g.setStatus(27, 4, Cell.ALIVE);
				g.setStatus(27, 3, Cell.ALIVE);
				g.setStatus(27, 9, Cell.ALIVE);
				g.setStatus(27, 8, Cell.ALIVE);
				g.setStatus(37, 5, Cell.ALIVE);
				g.setStatus(37, 6, Cell.ALIVE);
				g.setStatus(38, 5, Cell.ALIVE);
				g.setStatus(38, 6, Cell.ALIVE);
				repaint();
			}
		});
		btnGliderGun.setFont(new Font("Times New Roman", Font.BOLD, 15));
		colorPanel.add(btnGliderGun);
	}

}
