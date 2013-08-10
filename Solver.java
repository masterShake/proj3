import java.util.*;

public class Solver {
	private HashSet<Board> visited;
	private Stack<Board> boardStack;
	private ArrayList<Block> finalConfig = new ArrayList<Block>();

	public Solver(String[] args) throws IllegalBoardException {
		visited = new HashSet<Board>();
		boardStack = new Stack<Board>();
		boardStack.push(new Board(args));
		visited.add(boardStack.firstElement());
		getFinalConfig();
	}

	// we need to write some sort of algorithm method
	public void searchMethod() throws Exception {
		if (boardStack.isEmpty()) {
			System.exit(1);
		}
		Board current = boardStack.pop();
		if (this.isGoal(current)) {
			Board currentCopy = current;
			String output = "";
			while (currentCopy.getParent() != null) {
				output = currentCopy.getMove() + "\n" + output;
				currentCopy = currentCopy.getParent();
			}
			System.out.println(output);
			System.exit(1);
		}
		ArrayList<Move> moves = current.possibleMoves();
		for (Move m : moves) {
			Board add = current.copyBoard();
			if (m.oneDirection.equals("up")) {
				add.moveBlockVertical(m.myBlock, -1);
			} else if (m.oneDirection.equals("down")) {
				add.moveBlockVertical(m.myBlock, 1);
			} else if (m.oneDirection.equals("left")) {
				add.moveBlockHorizontal(m.myBlock, -1);
			} else if (m.oneDirection.equals("right")) {
				add.moveBlockHorizontal(m.myBlock, 1);
			} else {
				throw new Exception("Moves not correctly formatted");
			}
			if (!visited.contains(add)) {
				boardStack.push(add);
				visited.add(add);
				add.setParent(current);
				add.setMove(m.toString());
			}
		}
		searchMethod();

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
