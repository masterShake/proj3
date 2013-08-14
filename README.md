Project 3 version 1.0 8/9/2013

			  GENERAL USAGE NOTES


Division of labor
------------------

- Our group collectively spent roughly 50 hours developing this program.
  Each group member spent about the same amount of time as the other 
  group members. Many, if not most of the classes and methods developed
  were the result of a collaborative effort

Design
------------------

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

  NOTE: There are several methods in the Board class used to 
  heuristically evaluate the each of the possible moves. These include:
  evaluationFunc(), distance(), and compareTo(). Their implimentation
  and methodolgy are essential to the functionality of the search
  algorithm in the Solver class and are discussed later in this 
  document.

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

- To solve sliding block puzzles, the program uses a heuristic greedy
  search algorithm called searchMethod() to evaluate the relevance of
  possible moves and act accordingly. The first operation that the 
  algorithm performs is a while loop that polls the priority queue for 
  unattempted possible moves. Within the loop, the algorithm first
  determines with our program has stumbled upon the goal configuration.
  If that is not the case, the algorithm adds the current board to the 
  visited HashSet, then proceeds to evaluate the relevance of the next
  possible moves.

- The heuristic evaluation of possible moves is conducted by 3 methods
  in the Board class; evaluationFunc(), distance(), and an overwrite of
  java's built in compareTo method for evaluating the possible states in
  our priority queue. These methods work in tandem to assign a numeric
  value to a game board's state based on how much closer a single move
  will bring the computer to solving the puzzle. The evaluationFunc
  method accepts a Solver object as an argument and evaluates the
  dimensions of each block in the current board, and compares the to 
  blocks in the final configurations. The method then updates the 
  object's "rank" variable with sum of the distances of blocks with 
  matching dimensions to the final configuration goal state. This means
  that every Board object in the priority queue is assigned a rank 
  variable that the program can rapidly poll, thus increasing the 
  algorithm's efficiency. In order to accurately assess the distance of 
  a block to its position in the final configuration, the 
  evaluationFunc() method calls on the distance() method. The distance()
  method essentially performs the pythagorean theorem given two 
  coordinates. The compareTo() method then simply compares the rank of
  the current game state with the final configuration and assignes a 
  possitive integer value to any possible move that brings the algorithm
  "closer" to solving the puzzle.

Evaluating tradeoffs
--------------------

- This entire project was an exercise in the tradeoffs when dealing with
  different data structures. Given the time and memory constraints on 
  solving a puzzle, the team was required to constantly consider the run
  time and memory demands of every data structure when choosing the best
  way to implement our program. In this section of the document, we 
  discuss, in greate detail, the group's reasoning for the various data
  structures in place and the cost and benefits that they afforded the 
  program.

- The first class that was developed for the project was the Block
  class, which required the team to make critical decisions about the
  many types of variables that would constitute a Block object. In order
  to represent the coordinates of a block on our 2 dimensional plain, 
  the team chose to use java's built in Point class, each Block object
  is composed of two Points representing the position of the top left
  corner of the Block and the bottom right corner. Only two Points were
  necessary to extrapolate a complete picture of the dimensions of the
  block. Using the Point class came with some tradeoffs. Firstly, 
  retrieving point data required two getter methods called getTopLeft()
  and getBottomRight(). It is somewhat inefficient to need to call a 
  method every time we need information from one of our Block objects,
  however, the team decided that this was preferable to other options,
  such as a integers, because it allowed us to easily group together 
  two coordinates, and it also afforded what we considered to be the 
  fewest number of variables attatched to a Block object, a dicision 
  that also simplified the code and development process.

- In determining which data structures were necessary to represent the 
  Board objects, the team was forced to consider all the possible and 
  poperties that the Board may required, and the memory and speed
  constraints that accompanied those structures. In doing so, the Board
  object ended up being slightly more bloated than the team had hoped,
  but still managed to achieve the program's objectives. When developing
  these structures, the team was heavy focused on keeping lookup time as
  low as possible.

- For storing the blocks of a Board object, the team decided that a 
  HashSet was the most appropriate choice. The HashSet afforded the 
  program constant lookup time, thus improving performance. Very few
  other data structures, such as an ArrayList, would have allowed for 
  such quick performance.

- Although the HashSet of Block objects could be queried in constant
  time, determining which moves were possible in a given configuration
  would have still required several loops through the HashSet and other 
  calculations to find those possible moves. That is why the team 
  decided that it would be to the programs great benefit to have a 2D 
  Array of boolean values representing occupied and unoccupied 
  coordinates on the game board. This allowed us to more quickly 
  determine the possible moves and act on them. The 2D array was more
  idea than, say, an ArrayList of empty squares because the indecies of
  the 2D Array perfectly matched the x & y coordinates of the blocks!
  This allowed us to query the Array using the values of a given Block 
  object in constant time, where as an ArrayList of empty squares would
  have had a lookup time of O(N).

- In order to trace our moves back to the starting possition so as to 
  return the steps required to solve the puzzle, every Board object 
  pointed to a parent Board. The team realized that keeping track of the
  steps necessary to reach a goal configuration could have been 
  accomplished in a variety of ways, such as maintaining a stack or 
  queue of previous moves. However, these methods would have have 
  required additional data structures and would have likely slowed to 
  performance of the program. A simple pointer to an related object is
  the most efficient way to determining the moves made because it 
  required only one loop executed only one time at once the puzzle was
  solved. Using additional data structures would have required constant
  updates after every additional move.

- The Solver class contains several unique and lightweight data
  structures that help to facilitate the program's high performance.

- A given puzzle might have hundreds of thousands of possible game 
  states. This presents a unique problem for the program in that these
  states need to be stored and accessed in the least amount of time
  possible. To keep tabs on the states that the program has already 
  encountered, a HashSet is an obvious choice. Therefore, the program
  implements a HashSet named "visited" in the Solver class to do just
  that. Implementing any data structure that would require linear time
  to look up visited game states would result in an overwhelming number
  of calculations. There is essentially no better data structure than a
  HashSet for keeping track of these items.

- Because the group determined that a bredth-first greedy search 
  algorithm offered a relatively simple implementation as well as high
  performance, it was necessary to implement of queue of unvisited game
  states. However, Bredth-first greedy was not sufficient enough to
  accomplish the tasks required within the time limit. Therefore it was
  deemed necessary to implement a heuristic comparison of possible moves
  and the data structure would need to be able to access this heuristic
  value. That is why the group decided that a PriorityQueue was superior
  to a regular queue. This allows the program to compare the value of 
  all possible moves in the queue and select the best one to act upon.

- Finally, a simple ArrayList was all that was required to keep track of
  the position of the blocks in the goal configuration since this 
  usually contains many fewer Block objects than the initial
  configuration. The group realized that another HashSet might have 
  offered slightly improved performance, but would have come with the
  overhead of the hashcode.


Disclaimer
--------------------

Program Development
--------------------


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
