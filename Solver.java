import java.util.*;

public class Solver {
	private HashSet<Board> visited;
	private PriorityQueue<Board> boardStack;
	public ArrayList<Block> finalConfig = new ArrayList<Block>();
	private Board lastBoard;
	private boolean debuggingSolver1, debuggingSolver2, debuggingBoard;

	public Solver(String[] args) throws IllegalBoardException {
		visited = new HashSet<Board>();
		boardStack = new PriorityQueue<Board>();
		boardStack.offer(new Board(args));
		getFinalConfig(args);
		if (args[0].equals("-odebuggingSolver1")) {
			debuggingSolver1 = true;
		} else if (args[0].equals("-odebuggingSolver2")) {
			debuggingSolver2 = true;
		} else if (args[0].equals("-odebuggingBoard")) {
			debuggingBoard = true;
		} else if (args[0].equals("-ooptions")) {
			System.out.println("-odebuggingSolver1: prints all moves that have been tried so far");
			System.out.println("-odebuggingSolver2: prints all moves that are added to the priority queue and prints current board");
			System.out.println("-odebuggingBoard: calls isOk methods on board and block and prints current board and boards added");
		} else if (args.length == 3){
			System.out.println("Not a correct debugging option.");
			System.exit(1);
		}
	}


	/*
	 * Greedy search algorithm implemented with a priority queue used to find solution.
	 */
	public void searchMethod() throws Exception {

		while (!boardStack.isEmpty()) {

			Board current = boardStack.poll();
			if (debuggingSolver1) {
				System.out.println("move: " + current.getMove() + " is popped");
			}
			if (debuggingBoard) {
				System.out.println("current board is: ");
				current.printboard();
				System.out.println();
			}
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
			}else if (visited.add(current)){
				ArrayList<Move> moves = current.possibleMoves();
				if (debuggingSolver2) {
					System.out.println("Looking for moves for current board: ");
					current.printboard();
				}
				for (Move m : moves) {
					Board add = current.alterBoard(m);
					if (debuggingSolver2) {
						System.out.println("move added: " + m);
					}
					if (debuggingBoard) {
						add.isOK();
						System.out.println("board added is ");
						add.printboard();
						System.out.println();
					}
					add.evaluationFunc(this);
					boardStack.offer(add);
					add.setParent(current);
					add.setMove(m.toString());
				}
			}
		}
		System.exit(1);
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
