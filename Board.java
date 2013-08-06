import java.io.*;
import java.util.*;
import java.awt.*;


public class Board {

	private boolean[][] board;
	private HashSet<Block> blocks;
	
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
		while (true) {
			String input = initBoard.readLine();
			if (input == null) {
                return;
            }
	
			StringTokenizer st = new StringTokenizer(input);
			
			if(input.matches("[0-9]{1,} [0-9]{1,}")) {
				short length = (short) Integer.parseInt(st.nextToken());
				System.out.println("length: " + length);
				short width = (short) Integer.parseInt(st.nextToken());
				System.out.println("width: " + width);
				board = new boolean[length][width];
			} else if (input.matches("[0-9]{1,} [0-9]{1,} [0-9]{1,} [0-9]{1,}")){
				this.addBlock(new Block((short)Integer.parseInt(st.nextToken()),
										(short)Integer.parseInt(st.nextToken()),
										(short)Integer.parseInt(st.nextToken()),
										(short)Integer.parseInt(st.nextToken())));
			}
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
	
	public void moveBlockVertical(Block myBlock, short margin ){
	
	}
	
	public void moveBlockHorizontal(Block myBlock, short margin){
		
	}
	
	public boolean canMove(Block myBlock, Point goal){
		ArrayList<Point> x = new ArrayList<Point>();
		for (short i = 0; i < board.length; i++) {
			for (short k = 0; k < board[0].length; k++) {
				if(board[i][k] == false)
					x.add(new Point(i,k));
			}
		}
		return true;
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
	}
}

