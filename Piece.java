
public abstract class Piece {
	
	/*
	 * This class is an abstract class representative of a piece. The class holds one instance variable, "type", and 
	 * has a few methods surrounding "type" along with a toString method. This class is the superclass to Cell.
	 */
	
	int type;
	
	/*
	 * Constructor to make a Piece object. Note that because Piece is an abstract class, this constructor will not (or cannot)
	 * be called outside of the Piece hierarchy--this constructor is only used in the Cell class.
	 */
	public Piece(int type) {
		this.type = type;
	}
	
	/*
	 * Returns the integer value of "type", taking no parameters
	 */
	int getType() {
		return type;
	}
	
	/*
	 * Sets the value of "type" to the int value the user enters. 
	 */
	void setType(int newType) {
		type = newType;
	}
	
	/*
	 * Overrides the toString method from the Object class. This method returns the int "type" as a string, and is indirectly called 
	 * whenever printing a Piece object. 
	 */
	public String toString() {
		return "" + type;
	}
	
	// No test code because Piece objects are not instantiable.
}
