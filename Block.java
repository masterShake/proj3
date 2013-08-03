package proj3;

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
	
	/* moves block's point objects but doesn't affect game board. This must be
	implemented within gameboard.
	Negative int values for moving left or down. Positve values for moving 
	up or right.
	*/
	public void move(int horizontal, int vertical){
		topLeft.move(horizontal, vertical);
		bottomRight.move(horizontal, vertical);
	}
	
	//to be implemented later if needed
	public Point getTopRight(){
		return new Point();
	}
	
	//to be implemented later if needed
	public Point getBottomLeft(){
		return new Point();
	}
}
