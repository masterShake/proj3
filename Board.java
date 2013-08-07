import java.io.*;
import java.util.*;
import java.awt.*;


public class Board {

	private boolean[][] board;
	private HashSet<Block> blocks;
	
	
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
	
	//the new Move class returns and block and a string
	private static class Move{
		public Block myBlock;
		public String oneDirection;

		public Move(Block b, String d){
			myBlock = b;
			oneDirection = d;
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
			for (int i = (int) s.getTopLeft().getX(); i<= (int)s.bottomRight.getX();i++){
				//left
				if((int)s.topLeft.y==0){
					L+=1;
				}
				try{
				if(board[i][(int) (s.getTopLeft().getY()-1)]==true){
					L+=1;
				}
				}catch(Exception e){}
				//right
				if((int)s.bottomRight.y == board[0].length-1)
					R++;
				try{
				if(board[i][(int) (s.getBottomRight().getY()+1)]==true){
					R+=1;
				}
				}catch(Exception e){}
			//see if the block can go up or down
			}
			for (int k = (int) s.getTopLeft().getY(); k<= (int)s.getBottomRight().getY();k++){
				//up
				if(s.getTopLeft().getX()==0){
					U++;
				}
				try{
				if(board[(int) (s.getTopLeft().getX()-1)][k]==true || (int)s.getTopLeft().getX()==0){
					U+=1;
				}
				}catch(Exception e){}
				//down
				if(s.getBottomRight().getX()== board.length-1)
					D++;
				
				try{
				if(board[(int) (s.getBottomRight().getX()+1)][k]==true || (int)s.getBottomRight().getX()==board[0].length){
					D+=1;
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

