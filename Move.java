// the new Move class returns and block and a string
public class Move {
  public Block myBlock;
	public String oneDirection;

	public Move(Block b, String d) {
		myBlock = b;
		oneDirection = d;
	}
	
	public String toString() {
		String rtn = "";
		int newX, newY;
		if (oneDirection.equals("up")) {
			newX = ((int) myBlock.getTopLeft().getX()) - 1;
			newY = ((int) myBlock.getTopLeft().getY());
		} else if (oneDirection.equals("down")) {
			newX = ((int) myBlock.getTopLeft().getX()) + 1;
			newY = ((int) myBlock.getTopLeft().getY());
		} else if (oneDirection.equals("left")) {
			newX = ((int) myBlock.getTopLeft().getX());
			newY = ((int) myBlock.getTopLeft().getY()) - 1;
		} else {
			newX = ((int) myBlock.getTopLeft().getX());
			newY = ((int) myBlock.getTopLeft().getY()) + 1;
		}
		rtn = myBlock.getTopLeft().getX() + " " + myBlock.getTopLeft().getY() + " " + newX + " " + newY;
		return rtn;
	}
}
