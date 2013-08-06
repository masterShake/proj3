package proj3;
import java.util.*;



public class Solver {
	private HashSet<Board> visited;
	private Stack<Board> boardStack;
	
	
	public Solver(String [ ] args){
		visited = new HashSet();
		boardStack = new Stack();
		boardStack.push(new Board(args));
	}
	
	//we need to write some sort of algorithm method
	public void Algorithm(){
		Board current = boardStack.pop();
		ArrayList<Move> moves = current.possibleMoves();
		Board add;
		for (Move m: moves){
			add = current.copyBoard();
			for 
		}
		
	}
	
	public static void main (String [] args){
		Solver s = new Solver(args);
		
	}

}
