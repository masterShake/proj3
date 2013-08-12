import java.io.*;
import java.util.*;
import java.awt.*;


public class Board implements Comparable<Board> {

	private boolean[][] board;
	public HashSet<Block> blocks;
	private Board parent;
	private String move;
	private int distance;
	private int rank;


	public Board(int length, int width){
		board = new boolean[length][width];
		blocks = new HashSet<Block>();
	}

	/* We will probably make multiple constructors, but this one will 
	 * create a board by taking in an input file.
	 */
	public Board(String [] args) throws IllegalBoardException{
		short argIndex;
		if (args.length == 2){
			argIndex = 0;
		} else if (args.length > 3 || args.length < 1){
			throw new IllegalBoardException("Incorrect number of input files: " + args.length);
		}
		else {
			argIndex = 1;
		}
		InputSource initBoard = new InputSource(args[argIndex]);
		blocks = new HashSet();
		rank = -1;
		while (true) {
			String input = initBoard.readLine();
			if (input == null) {
                return;
            }

			StringTokenizer st = new StringTokenizer(input);

			if(input.matches("[0-9]{1,} [0-9]{1,}")) {
				short length = (short) Integer.parseInt(st.nextToken());
				short width = (short) Integer.parseInt(st.nextToken());
				board = new boolean[length][width];
			} else if (input.matches("[0-9]{1,} [0-9]{1,} [0-9]{1,} [0-9]{1,}")){
				this.addBlock(new Block((short)Integer.parseInt(st.nextToken()),
										(short)Integer.parseInt(st.nextToken()),
										(short)Integer.parseInt(st.nextToken()),
										(short)Integer.parseInt(st.nextToken())));
			}
        }
	}
	
	public void evaluationFunc (Solver goal) {
		int rank = 0;
		for (Block a: blocks) {
			int height = a.getHeight();
			int width = a.getWidth();
			for (Block b: goal.finalConfig) {
				if (height == b.getHeight() && width == b.getWidth()) {
					rank += distance(a, b);
				}
			}
		}
		this.rank = rank;
	}
	public int distance(Block blockA, Block blockB) {
		int first = (int) Math.abs(Math.pow(blockA.getTopLeft().x - blockB.getTopLeft().x, 2));
		int next = (int) Math.abs(Math.pow(blockA.getTopLeft().y - blockB.getTopLeft().y, 2));
		distance = (int) Math.sqrt(first + next);
		return distance;
	}
	
	public int compareTo(Board goalBoard) {
		//System.out.println("compareTo is geting called...");
		if (this.rank == goalBoard.rank) {
			return 0;
		} else if (this.rank < goalBoard.rank){
			return -10000;
		} else {
			return 1;
		}
	}

	public void addBlock(Block myBlock) {
		short xcoordTL = (short) myBlock.getTopLeft().getX();
		short ycoordTL = (short) myBlock.getTopLeft().getY();
		short xcoordBR = (short) myBlock.getBottomRight().getX();
		short ycoordBR = (short) myBlock.getBottomRight().getY();
		for (short i = xcoordTL; i <= xcoordBR; i++) {

			for (short a = ycoordTL; a <= ycoordBR; a++) {
				board[i][a] = true;
			}
		}
		blocks.add(myBlock);
	}

	/*
	 * UPDATE: This method only alters the boolean array.  
	 * Blocks are now immutable, so boards are immutable as well basically. 
	 */
	public void moveBlockVertical(Block myBlock, int margin ){
		int newXTL = (int) (myBlock.getTopLeft().getX() + margin);
		int newXBR = (int) (myBlock.getBottomRight().getX() + margin);
		int newYTL = (int) (myBlock.getTopLeft().getY());
		int newYBR = (int) (myBlock.getBottomRight().getY());
		for (int i = (int) myBlock.getTopLeft().getX();  
				i <= myBlock.getBottomRight().getX(); i++) {
			for (int a = (int) myBlock.getTopLeft().getY(); 
					a <= myBlock.getBottomRight().getY(); a++) {

				board[i][a] = false;
			}
		}

		for (int i = newXTL; i <= newXBR  ; i++) {

			for (int a = newYTL; a <= newYBR; a++) {
				//System.out.println(" adding block i: " + i + " a: " + a);
				board[i][a] = true;
			}
		}
	}
	/*
	 * precondition: This block can move to intended destination
	 * negative margin means move right 
	 * positive margin means move left
	 * UPDATE: This method only alters the boolean array. 
	 * Blocks are now immutable, so boards are immutable as well basically. 
	 */
	public void moveBlockHorizontal(Block myBlock, int margin){
		//System.out.println("margin: " + margin);
		//System.out.println("TopLeft y: " + myBlock.getTopLeft().getY());
		int newXTL = (int) (myBlock.getTopLeft().getX());
		int newXBR = (int) (myBlock.getBottomRight().getX());
		int newYTL = (int) (myBlock.getTopLeft().getY()+ margin);
		int newYBR = (int) (myBlock.getBottomRight().getY() + margin);
		//System.out.println("Block TopLeft: " + myBlock.getTopLeft());
		//System.out.println("Block bottomRight: " + myBlock.getBottomRight());
		for (int i = (int) myBlock.getTopLeft().getX();  
				i <= myBlock.getBottomRight().getX(); i++) {
			for (int a = (int) myBlock.getTopLeft().getY(); 
					a <= myBlock.getBottomRight().getY(); a++) {
				//System.out.println("i: " + i + " a: " + a);

				board[i][a] = false;
			}
		}

		for (int i = newXTL; i <= newXBR  ; i++) {

			for (int a = newYTL; a <= newYBR; a++) {
				board[i][a] = true;
			}
		}
	}


	public Board alterBoard(Move m){
		Board copy = new Board((int) board.length, (int) board[0].length);
		for (int i = 0; i < board.length; i++) {
			for (int k = 0; k < board[0].length; k++) {
				copy.board[i][k] = this.board[i][k];
			}
		}
		for (Block b: blocks){
			if (b.equals(m.myBlock)){
				if (m.oneDirection.equals("up")) {
					copy.blocks.add(new Block(b.getTopLeft().x - 1, b.getTopLeft().y,
							b.getBottomRight().x - 1, b.getBottomRight().y));
					copy.moveBlockVertical(b, -1);
				} else if (m.oneDirection.equals("down")) {
					copy.blocks.add(new Block(b.getTopLeft().x + 1, b.getTopLeft().y,
							b.getBottomRight().x + 1, b.getBottomRight().y));
					copy.moveBlockVertical(b, 1);
				} else if (m.oneDirection.equals("left")) {
					copy.blocks.add(new Block(b.getTopLeft().x, b.getTopLeft().y - 1,
							b.getBottomRight().x, b.getBottomRight().y -1));
					copy.moveBlockHorizontal(b, -1);
				} else if (m.oneDirection.equals("right")) {
					copy.blocks.add(new Block(b.getTopLeft().x, b.getTopLeft().y + 1,
							b.getBottomRight().x, b.getBottomRight().y + 1));
					copy.moveBlockHorizontal(b, 1);
				}
			} else {
				copy.blocks.add(b.copyBlock());
			}
		}

		return copy;
	}

	public Block getBlock(Point topLeft, Point bottomRight){
		Block myBlock = new Block(0,0,0,0);
		for (Block b: this.blocks){
			if (b.getTopLeft().equals(topLeft) 
					&& b.getBottomRight().equals( bottomRight)){
				myBlock = b;
			}
		}
		return myBlock;
	}

	public void removeBlock(Block myBlock){
		this.blocks.remove(myBlock);
		for (int i = (int) myBlock.getTopLeft().getX(); i<= myBlock.getBottomRight().getX(); i++){
			for (int k = (int) myBlock.getTopLeft().getY(); k <= myBlock.getBottomRight().getY(); k++ ){
				board[i][k] = false;
			}
		}
	}

    	public ArrayList<Move> possibleMoves(){
    	
    		//initialize the ArrayList of Moves
	    	ArrayList<Move> m = new ArrayList<Move>();

		//match blocks to empty spaces
		for (Block s : blocks) {

			int R = 0;
			int L = 0;
			int U = 0;
			int D = 0;
			//see if current block can go left or right
			for (int i = s.getTopLeft().x; i<= s.getBottomRight().x;i++){
				//left
				if((int)s.getTopLeft().getY()==0)
					L++;
				try{
				if(board[i][(int) (s.getTopLeft().getY()-1)]==true){
					L++;
				}
				}catch(Exception e){}
				//right
				if((int)s.getBottomRight().getY() == board[0].length-1)
					R++;
				try{
				if(board[i][(int) (s.getBottomRight().getY()+1)]==true){
					R++;
				}
				}catch(Exception e){}
			//see if the block can go up or down
			}
			for (int k = (int) s.getTopLeft().getY(); k<= (int)s.getBottomRight().getY();k++){
				//up
				if(s.getTopLeft().getX()==0)
					U++;
				try{
				if(board[(int) (s.getTopLeft().getX()-1)][k]==true){
					U++;
				}
				}catch(Exception e){}
				//down
				if(s.getBottomRight().getX()== board.length-1)
					D++;
				try{
				if(board[(int) (s.getBottomRight().getX()+1)][k]==true){
					D++;
				}
				}catch(Exception e){}
			}
			if(L==0)
				m.add(new Move(s, "left"));
			if(R==0)
				m.add(new Move(s, "right"));
			if(U==0)
				m.add(new Move(s, "up"));
			if(D==0)
				m.add(new Move(s, "down"));	
		}
		return m;

	}


	/*
	 * Could be improved to make board's hash faster
	 * so program will be more efficient
	 * Used in solvers Visited HashSet.
	 */
	public int hashCode(){
		return blocks.hashCode();
	}
	/*
	 * So that contains will work for a HashSet of Blocks.
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object other){
		if (other == null) return false;
		if (other == this) return true;
		if (!(other instanceof Board))return false;


		Board otherBoard = (Board) other;
		return this.blocks.equals(otherBoard.blocks);

	}

	/*
	 * isOK for Board
	 * ask sergio if boolean board has to match up with board
	 */
	public void isOK() throws IllegalBoardException {
		boolean[][] existing = new boolean[board.length][board[0].length];
		for (Block current: blocks) {
			short xcoordTL = (short) current.getTopLeft().x;
			short ycoordTL = (short) current.getTopLeft().y;
			short xcoordBR = (short) current.getBottomRight().x;
			short ycoordBR = (short) current.getBottomRight().y;
			if (xcoordTL < 0 || ycoordTL < 0 || xcoordBR < 0 || ycoordBR < 0) {
				throw new IllegalBoardException("Block:" + current + " is out of bounds");
			}
			if (xcoordTL > board.length || ycoordTL > board[0].length || xcoordBR > board.length || ycoordBR > board[0].length){
				throw new IllegalBoardException("Block:" + current + " is out of bounds");
			}
			for (short i = xcoordTL; i <= xcoordBR; i++) {
				for (short a = ycoordTL; a <= ycoordBR; a++) {
					if (!existing[i][a]) {
						existing[i][a] = true;
					} else {
						throw new IllegalBoardException("Block:" + current + " is overlapping another block");
					}
				}
			}
		}

		for (int i = 0; i < existing.length; i++ ){
			for (int k = 0; k < existing[0].length; k++){
				if (board[i][k] != existing[i][k]){
					throw new IllegalBoardException("blocks hashset does not match board boolean matrix. i: " + i + " k: " + k);
				}
			}
		}
		/* PRINT EXISTING
		for (short i = 0; i < existing.length; i++) {
				for (short k = 0; k < existing[0].length; k++) {
					System.out.print(existing[i][k] + " ");
				}
				System.out.println();
		}
		*/
	}


	/*
	 * Get method for parent board
	 */
	public Board getParent() {
		return parent;
	}

	public void setParent(Board parent) {
		this.parent = parent;
	}

	/*
	 * Get method for the previous move that gets you to current board
	 */
	public String getMove() {
		return move;
	}

	public void setMove(String move) {
		this.move = move;
	}

	/*
	 * Still needs to be implemented
	 */
	public String toString(){
		return "";
	}

	public void printboard() {
		for (short i = 0; i < board.length; i++) {
			for (short k = 0; k < board[0].length; k++) {
				System.out.print(board[i][k] + " ");
			}
			System.out.println();
		}
	}
	public static void main (String [ ] args) throws IllegalBoardException {
		Board test = new Board(args);
		test.printboard();

		Block myBlock = test.getBlock(new Point(1,1), new Point(2,2));
		System.out.println(myBlock.getTopLeft());
		System.out.println(myBlock.getBottomRight());
		System.out.println("before move vertical " + myBlock.getTopLeft().getY());
		test.moveBlockVertical(myBlock, -1);
		System.out.println("after move vertical " +myBlock.getTopLeft().getY());
		test.printboard();
		test.isOK();
		test.removeBlock(test.getBlock(new Point(0,0), new Point(1,0)));
		System.out.println("before method " +myBlock.getTopLeft().getY());
		test.moveBlockHorizontal(myBlock, -1);
		test.printboard();




	}
}
