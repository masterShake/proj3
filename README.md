Project 3 version 1.0 8/9/2013

GENERAL USAGE NOTES
-------------------

- Our sliding block puzzle solver employs a variety of datastructures to
  quickly and effectively meet the needs of the project constraints.
  The program is primarily composed of three classes: Block, Board, and
  Solver. Each Plays a unique role in constructing an environment for 
  the game, and keeping track of the many variables necessary to 
  traverse the graph and reach the goal configuration.

- The Block class serves as a constructor for pieces of the game board.
  Blocks are objects composed to two Points representing the block's
  top left coordinate and the blocks bottom right coordinate. In the
  instance where the block is a 1X1 cube, the top left and bottom right
  coordinates are the same. In order to access the x and y coordinates
  of a given point, we created a couple getter methods, getTopLeft() and
  getBottomRight(), to allow us access to those private variables. The
  block class also overwrites java's hashcode method,simplifying it to a
  4 number key based on the coordinates. This is intended to help the 
  method run faster while simultaneously improving look-up time. Lastly,
  the block class contains two boolean methods, equals() and isOK, that
  help to ensure invariance in the Block class. The equals() method
  works to ensure that no two blocks are the same, nor occupy that same
  "space" at once, while the isOk() method ensures that the coordinates
  are correct an that a block does not have a negative value.

- The Board class is considerably more complex than the Block class and
  initializes a few key data structures which merit their own bullet
  points. The next several paragraphs go into detail about these data
  structures and how their implimentation contributes to the overall
  functionality of the program.

- In order to easily and quickly access the current possitions of each
  of the blocks, we chose to employ a HashSet called "blocks" to hold 
  these objects. We found the HashSet to be prefferable to other types 
  of data structures such as an Array because it boasts constant lookup 
  time O(1) which helps the program to meet the time constraints placed 
  on it. Some of the methods that manipulate our blocks HashSet include
  addBlock() (not to be confused with ad block, a fantastic tool for web
  browsing), which populates the freshly created HashSet using input 
  from the initialConfigFile. getBlock() also employs the blocks HashSet
  by retreiving a specified block based on Points provided in the
  method's arguments. removeBlock() grabs a specified Block object out 
  of the HashSet, possibleMoves() cycles through the coordinates of the 
  blocks in the HashSet and compares them to the truth Matix to
  determine all possible moves in a given game board configuration. The
  final method in the Board class that employs the blocks HashSet is the
  boolean isOk() method witch varifies that the blocks have no negative
  coordinate values, and that the blocks fit are all contained within 
  initial length and width constraints of the Board.

- The next major data structure in the Board class is a truth matrix 
  called "board." Board is comprised of a 2D Array of boolean values and
  is primarily used to assess which coordinates are occupied or 
  unoccupied by a block at any given moment. We chose to use this data 
  structure because it offered O(1) lookup time because its indices
  perfectly matched with the coordinate points of the blocks in the 
  configuration. The constructor for the Board class initializes both
  the HashSet and the 2D truth matrix using the length and with provided
  in the first line of the initialConfigFile. The primary methods that 
  work to manipulate the board object are aptly titled 
  moveBlockVertical() and moveBlockHorizontal(). Together, these methods
  work to adjust the boolean values of the array when a new move is 
  acted upon. Most methods that manipulate the 2D array require a double
  for loop inorder to the reach the child Arrays within the parent
  Array.

- The possibleMoves() function demonstrates the true value of the 2D
  truth matrix and how to take advantage of its constant lookup time.
  The method outputs an ArrayList of Move objects (to be discussed 
  later in this document) which it passes on to the Solver class. The
  method generates Moves by running a double for loop, mixing and 
  matching the incremented integer and the coordinates of the block to
  determine if the block is adjacent to enough empty space to change 
  possition of the block and occupy the new territory.

- The last noteworthy method in the Board class that employs the Board
  2D Array object is the comprehensive isOk() method. The primary goal
  of isOk() regarding the Board object is to verify that none of the 
  blocks overlap eachother, that they all correspond with the correct
  boolean values in the Array, and that none of the blocks are out of
  bounds with respect to the 2D Array.

- Before diving into the specifics of the Solver class and the 
  algorithm behind it, it is worth mentioning a couple of details 
  regarding the Move class. Move objects are composed two elements: a
  Block object and a String. The block represents an active block 
  presently in the HashSet and the String indicates a single possible
  direction that that block may move. The 4 possible string values are
  "up", "down", "left", and "right." These Strings are interpreted in 
  the conditional statements of other methods throughout the program.

- The Solver class represents the engine of our program and houses the
  algorithm which drives the "decision making" process. This class 
  utilizes several essential data structures in its implementation. 
  Each of these structures offers the program a particular 
  functionality and are uniquely integrated into each of this class's
  methods.

- In order to keep track of configurations that our program has already
  visited, spending the least amount of lookup and insertion time
  possible, we decided that a HashSet best suited our needs.

- Verifying that the program has reached the goal configuration is a
  two part process. The first method, getFinalConfig(), runs only at the
  beginning of the program, and uses the InputSource class from project
  2 to extract information from the argument files passed to the 
  program's run configurations. The resulting Block objects are added 
  to the finalConfig ArrayList, which sits idly for the remainder of 
  program, helping the isGoal() method to query the HashSet for 
  matching goal configuration blocks.

------------------------------------------------------------------------

Running Program on Eclipse 3.5
------------------------------

Open Eclipse 3.5 and click on File > New > Java Project. Copy and paste
all 6 source files into the folder titled "src". Run Driver.java. A java
window will pop up with six numbered dots. To build nter 2 numbers with 
no spaces in between in the Console and press enter. Try not to build
three lines in the form of a triangle. 

=======================================================================

Written and developed by: 

Phoebe Lin
cs61bl-kl

Chloe Lischinsky 
cs61bl-kc

Michael Giannini
cs61bl-ju

Copyright 2013 University of California, Berkeley. All rights reserved.
This program is not meant for public distribution. Example program and 
its use are subject to compliance with rules on student academic ethical
conduct. Please contact students with questions or comments.
