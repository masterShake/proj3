
import java.util.*;



public class Solver {
	private HashSet<Board> visited;
	private Stack<Board> boardStack;
	
	
	public Solver(String [ ] args) throws IllegalBoardException{
		visited = new HashSet();
		boardStack = new Stack();
		boardStack.push(new Board(args));
		visited.add(boardStack.firstElement());
	}
	
	//we need to write some sort of algorithm method
	public void Algorithm() throws Exception{
		Board current = boardStack.pop();
		ArrayList<Move> moves = current.possibleMoves();
		for (Move m: moves){
			Board add = current.copyBoard();
			if (m.oneDirection.equals("up")){
				add.moveBlockVertical(m.myBlock, -1);
			} else if (m.oneDirection.equals("down")){
				add.moveBlockVertical(m.myBlock, 1);
			} else if (m.oneDirection.equals("left")){
				add.moveBlockHorizontal(m.myBlock, -1);
			}else if (m.oneDirection.equals("right")){
				add.moveBlockHorizontal(m.myBlock, 1);
			} else {
				throw new Exception("Moves not correctly formatted");
			}
			if (!visited.contains(add)){
				boardStack.push(add);
				visited.add(add);
			}
		}
		
	}
	
	public static void main (String [] args) throws IllegalBoardException{
		Solver s = new Solver(args);
		
	}

}
