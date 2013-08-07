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
	
	public void moveBlockVertical(Block myBlock, int margin ){
		int newXTL = (int) (myBlock.getTopLeft().getX() + margin);
		int newXBR = (int) (myBlock.getBottomRight().getX() + margin);
		int newYTL = (int) (myBlock.getTopLeft().getY());
		int newYBR = (int) (myBlock.getBottomRight().getY());
		for (int i = (int) myBlock.getTopLeft().getX();  
				i <= myBlock.getBottomRight().getX(); i++) {
			for (int a = (int) myBlock.getTopLeft().getY(); 
					a <= myBlock.getBottomRight().getY(); a++) {
				System.out.println("i: " + i + " a: " + a);
				
				board[i][a] = false;
			}
		}
		
		for (int i = newXTL; i <= newXBR  ; i++) {
			
			for (int a = newYTL; a <= newYBR; a++) {
				board[i][a] = true;
			}
		}
		myBlock.move(0, margin);
	}
	/*
	 * precondition: This block can move to intended destination
	 * negative margin means move right 
	 * positive margin means move left
	 */
	public void moveBlockHorizontal(Block myBlock, int margin){
		System.out.println(myBlock.getTopLeft().getY());
		int newXTL = (int) (myBlock.getTopLeft().getX());
		int newXBR = (int) (myBlock.getBottomRight().getX());
		int newYTL = (int) (myBlock.getTopLeft().getY()+ margin);
		int newYBR = (int) (myBlock.getBottomRight().getY() + margin);
		for (int i = (int) myBlock.getTopLeft().getX();  
				i <= myBlock.getBottomRight().getX(); i++) {
			for (int a = (int) myBlock.getTopLeft().getY(); 
					a <= myBlock.getBottomRight().getY(); a++) {
				System.out.println(myBlock.getTopLeft().getY());
				System.out.println("i: " + i + " a: " + a);
				
				board[i][a] = false;
			}
		}
		
		for (int i = newXTL; i <= newXBR  ; i++) {
			
			for (int a = newYTL; a <= newYBR; a++) {
				board[i][a] = true;
			}
		}
		myBlock.move(margin, 0);
	}


	public Board copyBoard(){
		Board copy = new Board((int) board.length, (int) board[0].length);
		for (int i = 0; i < board.length; i++) {
			for (int k = 0; k < board[0].length; k++) {
				copy.board[i][k] = this.board[i][k];
			}
		}
		copy.blocks = new HashSet(this.blocks);
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
		//create an array list of empty spaces
		ArrayList<Moves> possible = new ArrayList<Moves>();
		ArrayList<Block> emptySpace = new ArrayList<Block>();
		for (int i = 0; i < board.length; i++) {
			for (int k = 0; k < board[0].length; k++) {
				if(board[i][k] == false)
					emptySpace.add(new Block(i,k,i,k));
			}
		}
		//match blocks to empty spaces
		
		//for everyBlock, for every empty space, does the block fit in the space?
		for (Block s : blocks) {
			for (int i = 0; i < emptySpace.size(); i++) {
				//variable = top left match?
				//variable = bottom right match?
				
				//left to right move
				if(s.getTopLeft().getX() == emptySpace.get(i).getTopLeft().getX()){
					//&& s.getBottomRight().getX() == emptySpace.get(i).getBottomRight().getX()
				}
				if(s.getBottomRight().getX() == emptySpace.get(i).getBottomRight().getX()){
					//&& s.getBottomRight().getX() == emptySpace.get(i).getBottomRight().getX()
				}
				//if variableTopLeft is below or above or equal to variableBottomRight
				
				
				//top to bottom move
				if(s.getTopLeft().getY() == emptySpace.get(i).getTopLeft().getY()){
					//&& s.getBottomRight().getX() == emptySpace.get(i).getBottomRight().getX()
				}
				if(s.getBottomRight().getY() == emptySpace.get(i).getBottomRight().getY()){
					//&& s.getBottomRight().getX() == emptySpace.get(i).getBottomRight().getX()
				}
			}
		}
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
		System.out.println("before move vertical " +myBlock.getTopLeft().getY());
		test.moveBlockVertical(myBlock, -1);
		System.out.println("after move vertical " +myBlock.getTopLeft().getY());
		test.printboard();
		
		test.removeBlock(test.getBlock(new Point(0,0), new Point(1,0)));
		
		System.out.println("before method " +myBlock.getTopLeft().getY());
		test.moveBlockHorizontal(myBlock, -1);
		test.printboard();
		
		
	}
}

