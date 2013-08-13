import java.awt.Point;


/*
 * The Block class represents a single block on the board. We used java's Point class
 * to represent x and y coordinates. The position of a specific block is immutable once
 * it is placed on the board.
 */
public class Block {

	private Point topLeft;
	private Point bottomRight;

	public Block(int topLeftX, int topLeftY, int bottomRightX, int bottomRightY){
		topLeft = new Point(topLeftX, topLeftY);
		bottomRight = new Point(bottomRightX, bottomRightY);
	}

	public Point getTopLeft(){
		return topLeft;
	}

	public Point getBottomRight(){
		return bottomRight;
	}

	public Block copyBlock(){
		return new Block( this.getTopLeft().x, this.getTopLeft().y,
				this.getBottomRight().x, this.getBottomRight().y);
	}
	
	public int getHeight() {
		return topLeft.x - bottomRight.x;
	}
	
	public int getWidth() {
		return topLeft.y - bottomRight.y;
	}

	/*
	 * Our hashCode is a sum of all the coordinates of the top left and bottom
	 * right of a block. We chose an arbitrary number to multiply the sum by to 
	 * minimize collisions.
	 */
	public int hashCode(){
		int sum = 1;
		sum = 31*sum + getTopLeft().x;
		sum = 31*sum + getTopLeft().y;
		sum = 31*sum + getBottomRight().x;
		sum = 31*sum + getBottomRight().y;
		return sum;
	}

	public String toString(){
		return "TL: " + topLeft + ", BR: " + bottomRight;
	}

	public boolean equals(Object other){
		if (other == null) return false;
		if (other == this) return true;
		if (!(other instanceof Block))return false;

		Block otherBlock = (Block) other;
		return this.topLeft.equals(otherBlock.topLeft) 
				&& this.bottomRight.equals(otherBlock.bottomRight);
	}

	/*
	 * This isOk method checks of the coordinates are well-formed by checking if the positions
	 * are appropriately greater or less than each other.
	 */
	public boolean isOk(){
		if(getBottomRight().x<getTopLeft().x || getBottomRight().y< getTopLeft().y)
			return false;
		return true;
	}
}
