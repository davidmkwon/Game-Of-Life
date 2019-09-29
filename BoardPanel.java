import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

/*
 * A class that extends the JPanel class, adding the functionality
 * of painting the current generation of a Game of Life.
 */
public class BoardPanel extends JPanel{
	private GameOfLife game;
	
	/*
	 * Constructor that constructs a BoardPanel object. Note that the "game" is also randomized with the
	 * randomize() method. 
	 */
	public BoardPanel(GameOfLife g){
		game = g;
		randomize();
	}
	
	/*
	 * Method that randomizes the states of the Cell objects on the game board.
	 */
	public void randomize() {
		for (int i = 0; i < game.getHeight(); i++) {
			for (int j = 0; j < game.getWidth(); j++) {
				game.killCell(j, i);
			}
		}
		int currentGenNum = game.getGenNum();
		for (int i = 0; i < game.getHeight(); i++) {
			for (int j = 0; j < game.getWidth(); j++) {
				if (Math.random() < 0.5) game.setStatus(j, i, Cell.ALIVE);
			}
		}
		
	}

	/**
	 * Paints the current state of the Game of Life board onto
	 * this panel. This method is invoked for you each time you
	 * call repaint() on either this object or on the JFrame upon
	 * which this panel is placed.
	 */
	public void paintComponent(Graphics g){
		Graphics2D g2 = (Graphics2D)g;
		// TODO this is where you need to draw the current state of the board
		int x = this.getWidth() / game.getWidth();
		int y = this.getHeight() / game.getHeight();
		
		for (int i = 0; i < game.getHeight(); i++) {
			for (int j = 0; j < game.getWidth(); j++) {
				if (game.getStatus(j, i) == Cell.ALIVE) g2.fillRect(x * j,  y * i, x, y);
				else g2.drawRect(x * j, y * i, x, y);
			}
		}
		
	}
}
