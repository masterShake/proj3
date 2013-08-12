import java.util.*;

public class Solver {
	private HashSet<Board> visited;
	private PriorityQueue<Board> boardStack;
	public ArrayList<Block> finalConfig = new ArrayList<Block>();
	private Board lastBoard;

	public Solver(String[] args) throws IllegalBoardException {
		visited = new HashSet<Board>();
		boardStack = new PriorityQueue<Board>();
		boardStack.offer(new Board(args));
		visited.add(boardStack.peek());
		getFinalConfig(args);
	}


	// we need to write some sort of algorithm method
	public void searchMethod() throws Exception {

		while (!boardStack.isEmpty()) {

			Board current = boardStack.poll();
			/*if (current.getParent() != null){
	            if (!current.getParent().equals(lastBoard)){
	                System.out.println("Circling back. Go back to board produced by move: " 
	                        + current.getParent().getMove());
	            }
	        }
	        lastBoard = current;*/
	        //System.out.println(current.getMove());
			//current.printboard();
			if (this.isGoal(current)) {
				Board currentCopy = current;
				String output = "";
				while (currentCopy.getParent() != null) {
					output = currentCopy.getMove() + "\n" + output;
					currentCopy = currentCopy.getParent();
				}
				if (output.length() == 0){
					System.out.print(output);
				} else {
					System.out.print(output.substring(0,output.length()-1));
				}
				return;
			} else if (visited.add(current)){
				ArrayList<Move> moves = current.possibleMoves();
				for (Move m : moves) {
					//System.out.println(m);
					Board add = current.alterBoard(m);
					//add.printboard();

					//add.isOK();

						//System.out.println(m);
						add.evaluationFunc(this);
						boardStack.offer(add);
						add.setParent(current);
						add.setMove(m.toString());
				}
			}
		}
		System.exit(1);
		/*if (boardStack.isEmpty()) {
			//System.out.println("empty stack");
			System.exit(1);
		}*/


		/* PRINT VISITED
		System.out.println("visited");
		for (Board b: visited){
			System.out.println();
			b.printboard();
		}
		*/
		//searchMethod();

	}


	// populates the List with blocks from the final configuration file.
	public void getFinalConfig(String[] args) throws IllegalBoardException {
		int argIndex;
		if (args.length == 2) {
			argIndex = 1;
		} else if (args.length > 3 || args.length < 1) {
			throw new IllegalBoardException("Incorrect number of input files: "
					+ args.length);
		} else {
			argIndex = 2;
		}
		InputSource initBoard = new InputSource(args[argIndex]);
		// blocks = new HashSet();
		while (true) {
			String input = initBoard.readLine();
			if (input == null) {
				return;
			}

			StringTokenizer st = new StringTokenizer(input);
			finalConfig.add(new Block((short) Integer.parseInt(st.nextToken()),
					(short) Integer.parseInt(st.nextToken()), (short) Integer
							.parseInt(st.nextToken()), (short) Integer
							.parseInt(st.nextToken())));

		}
	}

	public boolean isGoal(Board B) {
		for (int i = 0; i < finalConfig.size(); i++) {
			if (!B.blocks.contains(finalConfig.get(i))) {
				return false;
			}
		}
		return true;
	}

	public static void main(String[] args) throws Exception {
		Solver s = new Solver(args);
		s.searchMethod();
	}

}
