// the new Move class returns and block and a string
public class Move {
  public Block myBlock;
	public String oneDirection;
	private String repr;

	public Move(Block b, String d) {
		myBlock = b;
		oneDirection = d;
		repr = "";
		int newX, newY;
		if (oneDirection.equals("up")) {
			newX = ((int) myBlock.getTopLeft().x) - 1;
			newY = ((int) myBlock.getTopLeft().y);
		} else if (oneDirection.equals("down")) {
			newX = ((int) myBlock.getTopLeft().x) + 1;
			newY = ((int) myBlock.getTopLeft().y);
		} else if (oneDirection.equals("left")) {
			newX = ((int) myBlock.getTopLeft().x);
			newY = ((int) myBlock.getTopLeft().y) - 1;
		} else {
			newX = ((int) myBlock.getTopLeft().x);
			newY = ((int) myBlock.getTopLeft().y) + 1;
		}
		repr = myBlock.getTopLeft().x + " " + myBlock.getTopLeft().y + " " + newX + " " + newY;
	}
	
	public String toString() {
		return repr;
	}
}
