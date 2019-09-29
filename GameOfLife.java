
public class GameOfLife {
	
	/*
	 * This class is representative of Conway's "Game Of Life". The class keeps track of the current generation
	 * of the board along with the generation number. There are 3 different constructors, a variety of game-specific 
	 * methods, and finally a toString method that prints the Game Of Life board as one might envision it. 
	 * 
	 *  There is testing done at the end of the class. 
	 */
	Board board;
	int genNum;
	
	/*
	 * Constructs a GameOfLife object given integer values for the height and width of "board". "genNum" is set
	 * to 1, and the constructor then calls "clear()" which sets all the Cell objects on "board" to dead Cells. 
	 */
	public GameOfLife(int height, int width) {
		board = new Board(new Cell[height][width]);
		genNum = 1;
		clear();
	}
	
	
	/*
	 * Constructs a GameOfLife object with a square board (equal height and width) given an integer value for the 
	 * side length of "board". "genNum" is set to 1, and the constructor then calls "clear()" which sets all the Cell 
	 * objects on "board" to dead Cells. 
	 */
	public GameOfLife(int sideLength) {
		board = new Board(new Cell[sideLength][sideLength]);
		genNum = 1;
		clear();
	}
	
	/*
	 * Constructs a GameOfLife object given a predefined initial state for "board". "genNum" is set to 1, and the 
	 * constructor then calls "clear()" which sets all the Cell objects on "board" to dead Cells. 
	 */
	public GameOfLife(Cell[][] initial) {
		board = new Board(initial);
		genNum = 1;
		clear();
	}
	
	/*
	 * Returns the number of living Cell objects (type = Cell.ALIVE or 1) directly next to or diagonal from the 
	 * Cell object at the specified coordinates entered by the user. The method uses a helper variable, "counter",
	 * which keeps track of the total number of living neighbors. 
	 */
	public int countNeighbors(int x, int y) {
		int counter = 0;
		for (int i = x - 1; i <= x + 1; i++) {
			for (int j = y - 1; j <= y + 1; j++) {
				if (i != x || j != y) {
					if (board.getPiece(i, j) == null) {}
					else if (((Cell) board.getPiece(i, j)).isAlive()) counter++;
				}
			}
		}
		return counter;
	}
	
	
	/*
	 * This method updates "board" to the next generation so that it's Cell objects are either living or dead 
	 * depending on their prior status and their number of neighbors. The method increases "genNum" by 1. Note 
	 * that a copy of the current Board is made for reference to the previous generation. 
	 */
	public void nextGen() {
		genNum++;
		Cell[][] original = new Cell[this.getHeight()][this.getWidth()];
		GameOfLife orig = new GameOfLife(original);
		for (int i = 0; i < board.getHeight(); i++) {
			for (int j = 0; j < board.getWidth(); j++) {
				orig.setStatus(j, i, this.getStatus(j, i));
			}
		}
		for (int i = 0; i < board.getHeight(); i++) {
			for (int j = 0; j < board.getWidth(); j++) {
				if (orig.willLive(j, i)) this.setStatus(j, i, Cell.ALIVE);
				else this.setStatus(j, i, Cell.DEAD);
			}
		}
		
	}
	
	/*
	 * This method changes the status of the Cell Object at the specified coordinates to Cell.ALIVE if it was previously
	 * dead, and vice versa. 
	 */
	public void toggleCell(int x, int y) {
		if (this.getStatus(x, y) == Cell.DEAD) this.setStatus(x, y, Cell.ALIVE);
		else this.setStatus(x, y, Cell.DEAD);
	}
	
	/*
	 * This method sets the status of the Cell object at the specified coordinates to Cell.DEAD. 
	 */
	public void killCell(int x, int y) {
		this.setStatus(x, y, Cell.DEAD);
	}
	
	/*
	 * This method sets the status of the Cell object at the specified coordinates to Cell.ALIVE.
	 */
	public void reviveCell(int x, int y) {
		board.getPiece(x, y).setType(Cell.ALIVE);
	}
	
	/*
	 * This method returns the int value of "genNum".
	 */
	public int getGenNum() {
		return genNum;
	}

	/*
	 * Sets all the status's of the Cell objects on "board" to Cell.DEAD and sets "genNum" back to 1. 
	 */
	public void clear() {
		genNum = 1; 
		for (int i = 0; i < board.getHeight(); i++) {
			for (int j = 0; j < board.getWidth(); j++) {
				board.setPiece(j, i, new Cell(Cell.DEAD));
			}
		}
	}
	
	/*
	 * Returns the int value of the height of "board". 
	 */
	public int getHeight() {
		return board.getHeight();
	}

	/*
	 * Returns the int value of the width of "board". 
	 */
	public int getWidth() {
		return board.getWidth();
	}
	
	/*
	 * Returns the status (or "type") of the Cell object at the given coordinates. 
	 */
	public int getStatus(int x, int y) {
		return board.getPiece(x, y).getType();
	}
	
	/*
	 * Sets the status (or "type") of the Cell object at the given coordinates to the integer "status" the
	 * user enters.
	 */
	public void setStatus(int x, int y, int status) {
		board.getPiece(x, y).setType(status);
	}
	
	/*
	 * Returns true or false based on whether the Cell object at the given coordinates will be alive or dead in
	 * the next generation. This method is based off of the rules for Conway's Game Of Life. 
	 */
	public boolean willLive(int x, int y) {
		if (this.getStatus(x, y) == Cell.ALIVE) {
			if (this.countNeighbors(x, y) >= 4 || this.countNeighbors(x, y) <= 1) return false;
			else return true;
		}
		else {
			if (this.countNeighbors(x, y) >= 4 || this.countNeighbors(x, y) <= 1 || this.countNeighbors(x, y) == 2) return false;
			else return true;
		}
	}
	
	/*
	 * Overrides the toString method from the Object class. This method returns "board" as a String, designed so that
	 * each Cell's status is printed between braces and the height and width of the printed board correspond to the 
	 * height and width of the board.  
	 */
	public String toString() {
		String result = "";
		for (int j = 0; j < board.getHeight(); j++) {
			for (int i = 0; i < board.getWidth(); i++) {
				result = result + "{" + this.getStatus(i, j) + "}";
			}
			result += "\n";
		}
		return result;
	}
	
	public static void main(String[] args) {
		GameOfLife test = new GameOfLife(5);
		System.out.println(test);
		System.out.println("This board has dimensions " + test.getHeight() + " x " + test.getWidth());
		test.setStatus(2, 1, Cell.ALIVE);
		test.setStatus(3, 2, Cell.ALIVE);
		test.setStatus(1, 3, Cell.ALIVE);
		test.setStatus(2, 3, Cell.ALIVE);
		test.reviveCell(3, 3);
		test.killCell(1, 3);
		test.toggleCell(2, 2);
		System.out.println("The updated Board is \n" + test);
		System.out.println("The Cell at (1, 2) has a status of " + test.getStatus(1, 2) + "\n");
		
		GameOfLife test2 = new GameOfLife(6, 7);
		System.out.println("New Board: \n" + test2);
		test2.setStatus(3, 3, Cell.ALIVE);
		test2.setStatus(4, 3, Cell.ALIVE);
		test2.setStatus(5, 3, Cell.ALIVE);
		System.out.println("The updated Board is: \n" + test2);
		System.out.println("The number of living neighbors of the Cell at (4, 3) is " + test.countNeighbors(4, 3));
		System.out.println("Whether the Cell at (4,3) will live is " + test.willLive(1, 2));
		test2.nextGen();
		System.out.println("The next generation is \n" + test2);
		test2.nextGen();
		System.out.println("The next generaiton is \n" + test2);
		System.out.println("The current generation number is " + test2.getGenNum() + "\n");
		
		Cell[][] test3Array = {{new Cell(Cell.ALIVE), new Cell(Cell.DEAD), new Cell(Cell.ALIVE)},
							{new Cell(Cell.DEAD), new Cell(Cell.ALIVE), new Cell(Cell.ALIVE)},
							{new Cell(Cell.ALIVE), new Cell(Cell.DEAD), new Cell(Cell.DEAD)}};
		GameOfLife test3 = new GameOfLife(test3Array);
		System.out.println(test3);
		test3.toggleCell(1, 1);
		test3.reviveCell(2, 2);
		System.out.println(test3);
		test3.nextGen();
		System.out.println(test3);
		test3.clear();
		System.out.println("The Board when cleared is: \n" + test3 + "with a generation number of " + test3.getGenNum());

	}
	
}
