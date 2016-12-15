package package1;


import java.awt.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

/**********************************************************************
 *Class that holds all of the game logic for SuperTicTacToe
 *
 * @param none
 * @return none
 *
 *********************************************************************/
public class SuperTicTacToeGame {

	/*
	 * 2-Dimensional array of cells
	 */
	private Cell[][] board;
	
	/*
	 * Used to retrieve the current state of the game
	 */
	private GameStatus status;
	
	/*
	 * Boolean that determines who's turn it is
	 */
	private boolean isX;
	
	/*
	 * Integer that holds the size of the game
	 */
	private int inum;
	
	/*
	 * Integer that holds the win size
	 */
	private int win;
	
	/*
	 * Integer used to hold the number of x wins
	 */
	private int xwin;
	
	/*
	 * Integer used to hold the number of o wins
	 */
	private int owin;
	
	/*
	 * Integer used to hold the number of catscratches
	 */
	private int cat;
	
	/*
	 * Integer used to hold who goes first
	 */
	private int resetorder;

	/*
	 * Object of the Point class used to add to the ArrayList<Point>
	 */
	private Point spot;
	
	/*
	 * ArrayList that stores all of the user's previous moves
	 */
	private ArrayList<Point> remove;

	/******************************************************************
	 * Constructor that utilizes the reset method to instantiate the
	 * various instance variables of the SuperTicTacToeGame class.
	 * 
	 * @param size Integer that is the user input board size
	 * @param winsize Integer that is the user input win size
	 * @param order Integer that is used to determine who goes first
	 *
	 *****************************************************************/
	public SuperTicTacToeGame(int size, int winsize, int order) {

		Reset(size, winsize, order);

	}

	/******************************************************************
	 *Returns the size of the board
	 *
	 * @param none
	 * @return inum The board size
	 *****************************************************************/
	public int getInum() {

		return inum;
	}

	/******************************************************************
	 *Returns the number of times x has won
	 *
	 * @param none
	 * @return xwin The number of times x has won
	 *****************************************************************/
	public int getXwins() {
		return xwin;

	}

	/******************************************************************
	 * Returns the number of times o has won
	 * 
	 * @param none
	 * @return owin The number of times o has won
	 *****************************************************************/
	public int getOwins() {
		return owin;

	}

	/******************************************************************
	 * Returns the number of catscratches
	 * 
	 * @param none
	 * @param cat The number of catscratches
	 *****************************************************************/
	public int getCat() {
		return cat;

	}

	/******************************************************************
	 * Sets the number of x wins
	 * 
	 * @param i An additional win
	 * @return none
	 *****************************************************************/
	public void setXwins(int i) {
		xwin += i;

	}

	/******************************************************************
	 * Sets the number of o wins
	 * 
	 * @param i An additional win
	 * @return none
	 *****************************************************************/
	public void setOwins(int i) {
		owin += i;

	}

	/******************************************************************
	* Sets the number of catscratches
	 * 
	 * @param i An additional catscratch
	 * @return none
	 *****************************************************************/
	public void setCat(int i) {
		cat += i;

	}

	/******************************************************************
	 * Returns whether it is x's turn or o's turn based on a boolean
	 * 
	 * @param none
	 * @return isX Boolean that determines current player turn
	 *****************************************************************/
	public boolean getisX() {

		return isX;
	}

	/******************************************************************
	 *Sets the board size to the user input size
	 *
	 * @param inum Integer that is the user entered size
	 * @return none
	 *****************************************************************/
	public void setInum(int inum) {

		this.inum = inum;
	}

	/******************************************************************
	 *Sets isX to true or false based on an integer
	 *
	 *@param choice Integer that determines X turn (O) or O turn (1)
	 *@return none
	 *****************************************************************/
	public void setisX(int choice) {
		if (choice == 0) {
			isX = true;
		} else if (choice == 1) {
			isX = false;
		}
	}

	/******************************************************************
	 * Returns the win size of the game
	 * 
	 *@param none
	 *@return win Integer that's the number of consecutive cells to win
	 *****************************************************************/
	public int getWin() {
		return win;
	}

	/******************************************************************
	 * Sets the win size to the user input for win size
	 * 
	 *@param win Integer that's the number of consecutive cells to win
	 *@return none
	 *****************************************************************/
	public void setWin(int win) {
		this.win = win;
	}
	
	/******************************************************************
	 * Gets the size of the game
	 * 
	 * @param none
	 * @return board A 2-Dimensional array of cells
	 *****************************************************************/
	public Cell[][] getBoard() {
		return board;
	}

	/******************************************************************
	 * Method that contains the rules for determining the state of the
	 * game. That is, if the game is in progress, someone has won, or
	 * the game is catscratched.
	 * 
	 * @param none
	 * @return status The state of the game
	 *****************************************************************/
	public GameStatus getGameStatus() {

		/*
		 * Integer that holds the number of empty cells based on the 
		 * game size
		 */
		int emptyNum = getInum() * getInum();

		/*
		 * Determines a horizontal win by moving through
		 * the board and incrementing the number of x's and 0's
		 * and decrementing the number of empty cells. If the number
		 * of x's or o's is equal to win size, the respective player
		 * has won.
		 */
		for (int row = 0; row < getInum(); row++) {
			int xnum = 0;
			int onum = 0;
			for (int col = 0; col < getInum(); col++) {
				if (board[row][col] == Cell.X) {
					xnum++;
					emptyNum--;
				} else if (board[row][col] == Cell.O) {
					onum++;
					emptyNum--;
				}
			}
			if (xnum == win) {
				status = GameStatus.X_WON;
				xwin++;
			} else if (onum == win) {
				status = GameStatus.O_WON;
				owin++;
			}

		}

		/*
		 * Determines a vertical win by moving through
		 * the board and incrementing the number of x's and 0's
		 * and decrementing the number of empty cells. If the number
		 * of x's or o's is equal to win size, the respective player
		 * has won.
		 */
		for (int col = 0; col < getInum(); col++) {
			int xnum = 0;
			int onum = 0;
			for (int row = 0; row < getInum(); row++) {
				if (board[row][col] == Cell.X) {
					xnum++;
				} else if (board[row][col] == Cell.O) {
					onum++;
				}
			}
			if (xnum == win) {
				status = GameStatus.X_WON;
				xwin++;
			} else if (onum == win) {
				status = GameStatus.O_WON;
				owin++;
			}

		}

		int xnum = 0;
		int onum = 0;

		/*
		 * Determines a diagonal win by moving through
		 * the board and incrementing the number of x's and 0's
		 * and decrementing the number of empty cells. If the number
		 * of x's or o's is equal to win size, the respective player
		 * has won.
		 */
		for (int row = 0; row < getInum(); row++) {

			if (board[row][row] == Cell.X) {
				xnum++;
			} else if (board[row][row] == Cell.O) {
				onum++;
			}
		}
		if (xnum == win) {
			status = GameStatus.X_WON;
			xwin++;
		} else if (onum == win) {
			status = GameStatus.O_WON;
			owin++;
		}

		xnum = 0;
		onum = 0;
		/*
		 * Determines the alternate diagonal win by moving through
		 * the board and incrementing the number of x's and 0's
		 * and decrementing the number of empty cells. If the number
		 * of x's or o's is equal to win size, the respective player
		 * has won.
		 */
		for (int row = 0; row < getInum(); row++) {

			if (board[row][(getInum() - 1) - row] == Cell.X) {
				xnum++;
			} else if (board[row][(getInum() - 1) - row] == Cell.O) {
				onum++;
			}
		}
		if (xnum == win) {
			status = GameStatus.X_WON;
			xwin++;
		} else if (onum == win) {
			status = GameStatus.O_WON;
			owin++;
			/*
			 * Determines a catscratch if there are no more empty cells
			 * and no one has one
			 */
		} else if (xnum != win && onum != win && emptyNum == 0) {
			status = GameStatus.CATS;
			cat++;
		}

		return status;
	}

	/******************************************************************
	 * Method that inserts x's and o's into the game based upon who's
	 * turn it is. This method also keeps track of all of the moves
	 * executed in the game via an ArrayList<Point>
	 * 
	 * @param row Integer that determines the row that an x/o will
	 *        be placed
	 * @param col Integer that determines the column that an x/o will
	 * 		  be placed
	 * @return none        
	 *****************************************************************/
	public void select(int row, int col) {

		if (getisX() == true) {
			board[row][col] = Cell.X;

			spot = new Point(col, row);

			remove.add(spot);

			isX = false;
		}

		else if (getisX() == false) {
			board[row][col] = Cell.O;

			spot = new Point(col, row);

			remove.add(spot);

			isX = true;
		}

	}

	/******************************************************************
	 * Method that calls the undoOp method to undo the previous moves
	 * of the game.
	 * 
	 * @param none
	 * @return none
	 *****************************************************************/
	public void undo() {

		undoOp();

	}

	/******************************************************************
	 * Method that undoes the previous move of the current player by
	 * retrieving that move's x,y coordinates from an ArrayList<Point>
	 * and inserting an empty cell into that spot and then moving on
	 * to the next player's turn.
	 *
	 *****************************************************************/
	private void undoOp() {
	
		/*
		 * Uses the resetorder integer to determine the order of entries
		 * into the ArrayList. This is X has gone first.
		 */
		if (resetorder == 0) {

			if (getisX() == true && (remove.size() - 2) > -1) {
				Point cspot = remove.get(remove.size() - 2);
				remove.remove(remove.size() - 2);
				int ux = (int) (cspot.getX());
				int uy = (int) (cspot.getY());
				board[uy][ux] = Cell.EMPTY;
				setisX(1);

			}

			else if (getisX() == false && (remove.size() - 1) > -1) {
				Point cspot = remove.get(remove.size() - 1);
				remove.remove(remove.size() - 1);
				int ux = (int) (cspot.getX());
				int uy = (int) (cspot.getY());
				board[uy][ux] = Cell.EMPTY;
				setisX(0);
			}

		}


		/*
		 * Uses the resetorder integer to determine the order of entries
		 * into the ArrayList. This is O has gone first.
		 */
		else if (resetorder == 1) {

			if (getisX() == true && (remove.size() - 1) > -1) {
				Point cspot = remove.get(remove.size() - 1);
				remove.remove(remove.size() - 1);
				int ux = (int) (cspot.getX());
				int uy = (int) (cspot.getY());
				board[uy][ux] = Cell.EMPTY;
				setisX(1);

			}

			else if (getisX() == false && (remove.size() - 2) > -1) {
				Point cspot = remove.get(remove.size() - 2);
				remove.remove(remove.size() - 2);
				int ux = (int) (cspot.getX());
				int uy = (int) (cspot.getY());
				board[uy][ux] = Cell.EMPTY;
				setisX(0);
			}

		}

	}

	/******************************************************************
	 * Method that constructs the SuperTicTacToeGame and sets up the
	 * initial empty board using inputs from the SuperTicTacToePanel
	 * class.
	 * 
	 *  @param psize Integer that is the size of the board
	 *  @param pwinsize Integer that is the win size for the game
	 *  @param porder Integer that determines the player order
	 *****************************************************************/
	private void Reset(int psize, int pwinsize, int porder) {

		setWin(pwinsize);
		setInum(psize);
		setisX(porder);
		resetorder = porder;

		remove = new ArrayList<Point>();

		//Initial game status is in progress
		status = GameStatus.IN_PROGRESS;
		xwin = 0;
		owin = 0;
		cat = 0;
		
		//creates an array that holds empty cells
		board = new Cell[getInum()][getInum()];
		for (int row = 0; row < getInum(); row++)
			for (int col = 0; col < getInum(); col++)
				board[row][col] = Cell.EMPTY;

	}

	/******************************************************************
	 * Method that constructs a previously saved game using values
	 * set in the SuperTicTacToeGame by the load method from a saved
	 * text file.
	 * 
	 * @param none
	 * @return none
	 *****************************************************************/
	private void loaderReset() {

		remove = new ArrayList<Point>();

		status = GameStatus.IN_PROGRESS;
		xwin = 0;
		owin = 0;
		cat = 0;
		board = new Cell[getInum()][getInum()];
		for (int row = 0; row < getInum(); row++)
			for (int col = 0; col < getInum(); col++)
				board[row][col] = Cell.EMPTY;

	}

	/******************************************************************
	 * Reads a text file line by line that holds the necessary values
	 * for creating a SuperTicTacToe game.
	 * 
	 *  @param none
	 *  @return none
	 *****************************************************************/
	public void load() {

		try {
			Scanner fileReader = new Scanner(new File(
					"/home/anderlna/SuperTicTacToe/output.txt"));
			

			/*
			 * Sets the critical values in the SuperTicTacToeGame
			 * class to values loaded from a previous game
			 */
			setisX((Integer.parseInt(fileReader.nextLine())));
			setInum(Integer.parseInt(fileReader.nextLine()));
			setWin(Integer.parseInt(fileReader.nextLine()));
		
			/*
			 * Reinstantiates the game class using the new values
			 * loaded from a previously saved game
			 */
			loaderReset();
			

			/*
			 * Reads the coordinates for all the previous moves into
			 * the select method
			 */
		while(fileReader.hasNextLine()){
				
				select(Integer.parseInt(fileReader.nextLine()),
						Integer.parseInt(fileReader.nextLine()));
				
			}
		fileReader.close();

		} catch (IOException e) {

			System.out.println("Something horrible has happened!");
		}
	}

	/******************************************************************
	 * Saves the current state of the game by printing all of the 
	 * critical values and coordinates for previous moves into a text
	 * file line by line
	 *
	 *@param none
	 *@return none
	 *****************************************************************/
	public void save() {

		PrintWriter out = null;

		try {
			out = new PrintWriter(new BufferedWriter(new FileWriter(
					"output.txt")));
		} catch (IOException e) {
			e.printStackTrace();
		}

		/*
		 * Prints the critical values into the text file
		 */
		out.println(resetorder);
		out.println(getInum());
		out.println(getWin());


		/*
		 * Prints the coordinates for every move in the game
		 * into a text file line by line
		 */
		for (Point spot2 : remove) {
			
			out.println((int)spot2.getY());
			out.println((int)spot2.getX());
		}

		out.close();
	}
}