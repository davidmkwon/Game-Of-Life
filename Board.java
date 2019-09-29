
public class Board {

	/*
	 * This class is representative of a game board, and has instance variables for the width, height, and a 2D array of
	 * the game pieces of the board. The class has 3 different constructors, getters and setters, and other methods
	 * regarding the Pieces on the board. Finally, there is a toString method that prints out the board as one might
	 * envision a typical board. 
	 * 
	 * There are tests run at the end. 
	 */
	private int height;
	private int width;
	private Piece[][] board;
	
	/*
	 * Constructs a Board object given an integer for the height and integer for the width. 
	 */
	public Board(int height, int width) {
		this.height = height;
		this.width = width;
		board = new Piece[height][width];
	}
	
	/*
	 * Constructs a square Board object with equal width and height given an integer for the side length. 
	 */
	public Board(int side) {
		this.height = side;
		this.width = side;
		board = new Piece[side][side];
	}
	
	/*
	 * Constructs a Board object with a predefined initial state defined in the 2D Piece array that is passed in. 
	 */
	public Board(Piece[][] board) {
		this.height = board.length;
		this.width = board[0].length;
		this.board = board;
	}
	
	/*
	 * Sets all the Piece objects in "board" to null.
	 */
	public void clear() {
		for (int i = 0; i < this.height; i++) {
			for (int j = 0; j < this.width; j++) {
				board[i][j] = null;
			}
		}
	}
	
	
	/*
	 * Sets the Piece at the given location on "board" to the Piece object the user passes in, returning the old Piece object 
	 * at the location. Note that this method delegates to the "isValidLoc" method to ensure that the coordinates the 
	 * user enters are existing coordinates on "board". If the coordinates entered are not valid coordinates, then the method 
	 * returns a dead Cell object. 
	 * 
	 * Note that although one might expect the else condition to return null, as there is no piece in an invalid location, for the
	 * purposes of my GameOfLife the method returns a dead Cell object (The exact reason is that when the mouse is dragged to a 
	 * location outside of the BoardPanel we will get a null pointer exception).
	 */
	public Piece setPiece(int x, int y, Piece piece) {
		if (this.isValidLoc(x, y)) {
			Piece temp = board[y][x];
			board[y][x] = piece;
			return temp;
		}
		else return new Cell(Cell.DEAD);
	}
	
	/*
	 * "Removes" the Piece object at the given location on "board" by replacing it with null, and returning the old Piece object
	 * at the location. This method delegates to the "isValidLoc" method to ensure that the coordinates the user enters are 
	 * existing coordinates on "board". If the coordinates entered are not valid coordinates, then the method returns a dead 
	 * Cell object (done for the same purposes as mentioned above). 
	 */
	public Piece removePiece(int x, int y) {
		if(isValidLoc(x, y)) {
			Piece temp = board[y][x];
			board[y][x] = null;
			return temp;
		}
		else return new Cell(Cell.DEAD);
	}
	
	/*
	 * Returns the Piece object at the given location on "board". The method delegates to the "isvalidLoc" method to ensure that the 
	 * coordinates the user enters are existing coordinates on "board". If the coordinates entered are not valid coordinates, then the
	 * method returns a dead Cell object (done for the same purposes as mentioned above).  
	 */
	public Piece getPiece(int x, int y) {
		if(isValidLoc(x, y)) {
			return board[y][x];
		}
		else return new Cell(Cell.DEAD); 
	}
	
	/*
	 * Returns the int value of "height" of this Board object. 
	 */
	public int getHeight() {
		return height;
	}
	
	/*
	 * Returns the int value of "width" of this Board object.
	 */
	public int getWidth() {
		return width;
	}

	/*
	 * Overrides the toString method from the Object class. This method returns "board" as a String, designed so that
	 * each Piece's type is printed between braces and the height and width of the printed board correspond to the 
	 * height and width of the board. 
	 */	
	public String toString() {
		String result = "";
		for (int i = 0; i < this.height; i++) {
			for (int j = 0; j < this.width; j++) {
				result = result + "{" + board[i][j] + "}";
			}
			result += "\n";
		}
		return result;
	}
	
	/*
	 * Returns whether there exists a Piece on "board" at the given location (is not null). 
	 */
	public boolean hasPiece(int x, int y) {
		if (this.isValidLoc(x, y)) return (board[y][x] != null);
		else return false;
	}
	
	/*
	 * Returns whether the given coordinates exist in respect to the dimensions of "board".
	 */
	public boolean isValidLoc(int x, int y) {
		return (x >= 0 && y >= 0 && x < this.width && y < this.height);
	}
	
	/*
	 * Some tests:
	 */
	public static void main(String args[]) {
		Piece[][] test2array = {{new Cell(Cell.ALIVE), new Cell(Cell.DEAD)},
							{new Cell(Cell.DEAD), new Cell(Cell.ALIVE)}};
		Board test2 = new Board(test2array);
		System.out.println(test2);
		System.out.println("Removing the piece at (1,1) which has status " + test2.removePiece(1, 1));
		System.out.println("The new Board is: ");
		System.out.println(test2);
		
		
		Board test = new Board(10);
		System.out.println(test);
		System.out.println("The height of this Board is " + test.getHeight());
		System.out.println("The width of this Board is " + test.getWidth());
		System.out.println("Whether the coordinates (1, 1) is a valid location on this board is " + test.isValidLoc(1, 1));
		System.out.println("Whether there exists a Piece at (-2, -3) is " + test.hasPiece(-2, -3));
		System.out.println("Whether there exists a Piece at (3, 2) is " + test.hasPiece(3, 2));
		System.out.println();

		
		Board test3 = new Board(4, 5);
		System.out.println(test3);
		System.out.println("The piece at (0, 0) is " + test.getPiece(0, 0));
		System.out.println("Setting what is at (1,1), which is " + test3.setPiece(1, 1, new Cell(Cell.DEAD)) + " to a dead Cell");
		System.out.println(test3);
		test3.clear();
		System.out.println("The board when is cleared looks like this: \n" + test3);
	
	}
	
}
