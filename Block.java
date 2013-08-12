import java.awt.Point;

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
	 * Could be improved
	 * 
	 */
	public int hashCode(){
		return getTopLeft().x + getTopLeft().y + getBottomRight().x + getBottomRight().y;
	}


	//to be implemented later if needed
	public Point getTopRight(){
		return new Point();
	}

	//to be implemented later if needed
	public Point getBottomLeft(){
		return new Point();
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

	//isOK this probably isn't enough code but I'm not really sure what else could be added
	public boolean isOk(Block B){
		if(B.getBottomRight().x<B.getTopLeft().x || B.getBottomRight().y<B.getTopLeft().y)
			return false;
		return true;
	}
}
