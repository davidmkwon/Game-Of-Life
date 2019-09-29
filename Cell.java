
public class Cell extends Piece {

	/*
	 * This class is the subclass to Piece, and is representative of a Cell in the Game Of Life. 
	 * It holds two final variables, DEAD and ALIVE (which are set to 0 and 1,respectively). The class includes 
	 * a constructor that takes an integer value for "type" and a default constructor. The class has a variety of
	 * methods surround "type" and the status of the cell along with a toString method. 
	 */
	
	public static final int DEAD = 0;
	public static final int ALIVE = 1;
	
	/*
	 * Constructor to make a Cell object. This constructor delegates to the Piece (superclass) constructor, setting the private instance
	 * "type" to the integer parameter status. 
	 */
	public Cell(int status) {
		super(status);
	}
	
	/*
	 * A default constructor that will set the private instance "type" to Cell.DEAD, or the integer 0. This constructor delegates
	 * to the Piece (superclass) constructor. 
	 */
	public Cell() {
		super(Cell.DEAD);
	}
	
	/*
	 * Overrides the toString method from the Piece class. This method returns the int "type" as a string, and is indirectly called 
	 * whenever printing a Piece object.
	 */
	public String toString() {
		return "" + this.type;
	}
	
	/*
	 * Returns true or false based on whether the "type" value of the Cell Object is 1 or 0. 
	 */
	public boolean isAlive() {
		return (this.type == 1);
	}
	
	/*
	 * Returns the int "type" of this Cell object. 
	 */
	public int getStatus() {
		return this.type;
	}
	
	/*
	 * Sets the value of "type" to the int value the user enters. 
	 */
	public void setStatus(int newStatus) {
		type = newStatus;
	}
	
	public static void main(String args[]) {
		Cell test = new Cell(Cell.ALIVE);
		System.out.println(test);
		test.setStatus(Cell.DEAD);
		System.out.println(test);
		System.out.println("The status of this Cell is " + test.getStatus());
		Cell test2 = new Cell();
		System.out.println("Whether this new cell is alive is " + test2.isAlive());
	}
	
}
