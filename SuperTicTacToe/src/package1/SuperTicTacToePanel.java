package package1;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import javax.swing.*;

/**********************************************************************
 * Class that is responsible for the construction of the GUI. Acts as
 * a user intermediary to the SuperTicTacToeGame class that holds the
 * logic for the game.
 *
 *@param none
 *@return none
 *********************************************************************/
public class SuperTicTacToePanel extends JPanel {

	/*
	 * 2-dimensional array that represents the GUI board
	 * the user sees
	 */
	private JButton[][] board;

	/*
	 * 2-dimensional array of cells that is the parameter that is received
	 * from the game object and represents the TicTacToe board.
	 */
	private Cell[][] iBoard;

	/*
	 * Button that quits the game
	 */
	private JButton quitButton;

	/*
	 * Represents the "x" on the board
	 */
	private ImageIcon xIcon;

	/*
	 * Represents the "O" on the board
	 */
	private ImageIcon oIcon;

	/*
	 * Represents an empty space on the board
	 */
	private ImageIcon emptyIcon;

	/*
	 * Panel that holds the GUI board layout
	 */
	private JPanel boardPanel;

	/*
	 * Panel that holds the buttons and labels
	 */
	private JPanel optPanel;

	/*
	 * Listener that determines when a spot on the board is selected
	 */
	private ButtonListener listener;

	/*
	 * Listener that determines when the quit button is selected
	 */
	private QuitListener qlistener;
	
	/*
	 * Listener that determines when the undo button is selected
	 */
	private UndoListener ulistener;

	/*
	 * Listener that determines when the load button is selected
	 */
	private LoadListener lolistener;

	/*
	 * Listener that determines when the save button is selected
	 */
	private SaveListener slistener;

	/*
	 * Stores the user entered board size as a string
	 */
	private String input;
	
	/*
	 * Stores the user entered board size as an integer
	 */
	private int inputnum;
	
	/*
	 * Stores the user entered win size as an integer
	 */
	private int win;
	
	/*
	 * Stores the user entered win size as a string
	 */
	private String winput;
	
	/*
	 * JRadioButton that determines if O will go first
	 */
	private JRadioButton O;
	
	/*
	 * JRadioButton that determines if X will go first
	 */
	private JRadioButton X;
	
	/*
	 * Integer that is either 1 or 0. Used to determine boolean that
	 * decides order of play.
	 */
	private int isX;
	
	/*
	 * JButton that undoes the current player's previous move
	 */
	private JButton undoButton;
	
	/*
	 * JButton that loads a previously saved game
	 */
	private JButton loadButton;
	
	/*
	 * JButton that saves the current game
	 */
	private JButton saveButton;

	/*
	 * Object of the SuperTicTacToeGame class that is utilized to call
	 * methods from that class
	 */
	private SuperTicTacToeGame game;

	/*
	 * Integer that holds the user entered board size for use in the
	 * SuperTicTacToePanel class
	 */
	private int inum;

	/*
	 * Label that shows the current number of X wins
	 */
	private JLabel xwin;

	/*
	 * Label that shows the current number of O wins
	 */
	private JLabel owin;
	
	/*
	 * Label that shows the current number of catscratches
	 */
	private JLabel cat;

	/******************************************************************
	 * Method that instantiates all of the instance variables either
	 * directly or by calling a helper method. Is called only once
	 * at first run of the game.
	 *
	 *@param none
	 *@return none
	 *****************************************************************/
	public SuperTicTacToePanel() {

		//Instantiating the listeners
		qlistener = new QuitListener();

		listener = new ButtonListener();

		ulistener = new UndoListener();
		
		slistener = new SaveListener();
		
		lolistener = new LoadListener();
		
		//Setting the icons that will be placed in the board
		xIcon = new ImageIcon("/home/anderlna/SuperTicTacToe"
				+ "/src/ImageFolder/datX.png");

		oIcon = new ImageIcon("/home/anderlna/SuperTicTacToe"
				+ "/src/ImageFolder/datO.jpg");

		emptyIcon = new ImageIcon("/home/anderlna/SuperTicTacToe"
				+ "/src/ImageFolder/datEmpty.jpg");

		//Creating a JPanel to hold the board GUI elements
		boardPanel = new JPanel();

		//Instantiating the nonboard GUI elements
		quitButton = new JButton("Quit");
		undoButton = new JButton("Undo");
		saveButton = new JButton("Save");
		loadButton = new JButton("Load");

		//Creating a borderLayout for visual appeal for the panels
		//that make up the entire class
		this.setLayout(new BorderLayout());

		//Method that asks the user who will go first
		orderofPlay();

		//Method that calls the methods that ask the user for inputs
		//and initially sets up the board
		createNewGame();

		//Instantiating  the JLabels
		owin = new JLabel("O Wins: " + game.getOwins());

		xwin = new JLabel("X Wins: " + game.getXwins());

		cat = new JLabel ("Catscratches: " + game.getCat());

		//Instantiating the panel that holds the nonboard GUI elements
		optPanel = new JPanel();

		optPanel.setLayout(new BoxLayout(optPanel,BoxLayout.Y_AXIS));

		//Tying ActionListeners to the nonboard GUI elements
		quitButton.addActionListener(qlistener);
		undoButton.addActionListener(ulistener);
		saveButton.addActionListener(slistener);
		loadButton.addActionListener(lolistener);

		//Adding the non board GUI elements to their panel and packing
		//it onto the main JPanel
		optPanel.add(owin);
		optPanel.add(xwin);
		optPanel.add(cat);
		optPanel.add(undoButton);
		optPanel.add(saveButton);
		optPanel.add(loadButton);
		optPanel.add(quitButton);

		add(optPanel,BorderLayout.EAST);

	}

	/******************************************************************
	 * Method that calls the methods that ask the user for inputs
	 * and the method that initializes the game board. Also serves as
	 * one of the primary methods of reseting the game
	 * 
	 * @param none
	 * @return none
	 *****************************************************************/
	private void createNewGame(){

		userInput();
		nextInput();
		constructorMethod();

	}

	/******************************************************************
	 * Method that uses a JOptionPane to ask the user how large the
	 * game board will be. The board must be larger than 2 but smaller
	 * than 16
	 *
	 *****************************************************************/
	private void userInput(){

		try{
			input = JOptionPane.showInputDialog(null, "Enter in the "
					+ "size of the board: ");

			if(input == null){
				//Exits the game if the user hits cancel
				System.exit(0);
			}

			inputnum = Integer.parseInt(input);


			if(inputnum < 3 || inputnum >15){

				throw new NumberFormatException();
			}
		}

		catch(NumberFormatException expected){
			//Informs the user than an invalid input was entered
			//and recalls this method to ask the question again
			JOptionPane.showMessageDialog(null, "Invalid input!");
			userInput();
		}
	}

	/******************************************************************
	 * Method that uses a JOptionPane to ask the user how many 
	 * consecutive cells are needed to win. The input must be larger
	 * than 2 and smaller than the board size
	 *
	 *@param none
	 *@return none
	 *****************************************************************/
	private void nextInput(){

		try{
			winput = JOptionPane.showInputDialog(null, "How many "
					+ "consecutive cells to win?: ");

			if(winput == null){
				//Exits the game if the user hits cancel
				System.exit(0);
			}

			win = Integer.parseInt(winput);

			if(win < 3 || win > inputnum ){

				throw new NumberFormatException();
			}

		}

		catch(NumberFormatException expected){

			//Informs the user than an invalid input was entered
			//and recalls this method to ask the question again
			JOptionPane.showMessageDialog(null, "Invalid input!");
			nextInput();

		}

	}

	/******************************************************************
	 * Method that uses a JOptionPane with JRadioButtons to ask the
	 * user who will go first. The ActionListener for the RadioButtons
	 * uses either a 1 (O) or a 0 (X) to determine the order boolean.
	 * This method is only called the first time the game is played
	 * 
	 * @param none
	 * @return none 
	 *
	 *****************************************************************/
	private void orderofPlay(){

		//Instantiates the RadioButtons and groups them together
		X = new JRadioButton("X");
		O = new JRadioButton("O");
		ButtonGroup group = new ButtonGroup();
		group.add(X);
		group.add(O);

		//Ties the RadioButtons to an ActionListener
		ChoiceListener Rlistener = new ChoiceListener();
		X.addActionListener(Rlistener);
		O.addActionListener(Rlistener);

		String message = "Who will go first?";

		//Array of Objects that holds the user's clickable options
		Object[] optionpaneopt = {"Continue", "Quit", X, O,};

		//Integer that receives the result of the user's choice on
		//the option pane
		int n = JOptionPane.showOptionDialog(null,message, "Order "
				+ "of Play", JOptionPane.YES_NO_OPTION, 
				JOptionPane.QUESTION_MESSAGE, null, 
				optionpaneopt,optionpaneopt[1]);

		if (n == JOptionPane.YES_OPTION){

			if(getIsX() == 0){

				JOptionPane.showMessageDialog(null, "X will go first!");

			}
			else if (getIsX() == 1){

				JOptionPane.showMessageDialog(null, "O will go first!");
			}
		}

		else if (n == JOptionPane.NO_OPTION){

			System.exit(0);
			
		}
	}
	
	/******************************************************************
	 * Method that constructs the game board and instantiates the 
	 * SuperTicTacToeGame class.
	 * 
	 * @param none
	 * @return none 
	 *****************************************************************/
	public void constructorMethod(){

		//Instantiates the game logic class
		game = new SuperTicTacToeGame(inputnum, win, isX);

		//Retrieves and stores the game board size
		inum = game.getInum();

		//Creates an Array of JButtons that is the user entered size
		board = new JButton[inum][inum]; 

		boardPanel.setLayout(new GridLayout(inum,inum));

		//Sets the board up with empty icons and ties the JButtons
		//to an ActionListener
		for (int row = 0; row < inum; row++) { 
			for (int col = 0; col < inum; col++) {
				board[row][col] = new JButton ("", emptyIcon);
				board[row][col].addActionListener(listener);
				boardPanel.add(board[row][col]);
			}
		}	

		add(boardPanel, BorderLayout.CENTER);
		
	}
		
	/******************************************************************
	 * Method that serves to construct a saved board when the load
	 * option is selected. Does not instantiate new SuperTicTacToeGame
	 * because one was already instantiated upon game start.
	 * 
	 * @param none
	 * @return none 
	 *****************************************************************/
	private void loaderMethod(){
		
		inum = game.getInum();

		board = new JButton[inum][inum]; 

		boardPanel.setLayout(new GridLayout(inum,inum));

		for (int row = 0; row < inum; row++) { 
			for (int col = 0; col < inum; col++) {
				board[row][col] = new JButton ("", emptyIcon);
				board[row][col].addActionListener(listener);
				boardPanel.add(board[row][col]);
			}
		}	

		add(boardPanel, BorderLayout.CENTER);

	}

	/******************************************************************
	 * Method that retrieves whether a cell has an x,o, or is empty
	 * from the SuperTicTacToeGame class and inserts the proper icon
	 * on the GUI based on the return for every cell on the board.
	 * 
	 *  @param none
	 *  @return none
	 *****************************************************************/
	private void displayBoard() {

		iBoard = game.getBoard();

		for (int row = 0; row < inum; row++) { 
			for (int col = 0; col < inum; col++) {
				if (iBoard[row][col] == Cell.O)
					board[row][col].setIcon(oIcon);
				if (iBoard[row][col] == Cell.X)
					board[row][col].setIcon(xIcon);
				if (iBoard[row][col] == Cell.EMPTY)
					board[row][col].setIcon(emptyIcon);
			}
		}
	}

	/******************************************************************
	 * Class that is tied to the buttons on the GUI board and sets
	 * the program up to behave accordingly based on the logic in the
	 * SuperTicTacToeGame class.
	 * 
	 * @param none
	 * @return none
	 *****************************************************************/
	private class ButtonListener implements ActionListener {

		/**************************************************************
		 * Determines what the GUI will do when a JButton on the game
		 * board is selected
		 * 
		 * @param none
		 * @param none
		 *************************************************************/
		public void actionPerformed(ActionEvent e){

			//Iterates through the entire board
			for (int row = 0; row < inum; row++) { 
				for (int col = 0; col < inum; col++) {
					if (board[row][col] == e.getSource() && game.getBoard()[row][col] == Cell.EMPTY) {
						// tell the game which button was selected and
						//place an x or an o if the cell is empty
						game.select(row, col);
						GameStatus stat = game.getGameStatus();
						
						//Retrieves the game status 
						if(stat == GameStatus.X_WON){
							String victory = "X";
							//Updates the JLabels
							updateLabel(stat);
							//Gives user an JOptionPane to determine
							//what to do next after game over
							endGame(victory);	
						}

						else if(stat == GameStatus.O_WON){
							String victory = "O";
							updateLabel(stat);
							endGame(victory);
						}
						else if(stat == GameStatus.CATS){
							String victory = "No one";
							updateLabel(stat);
							endGame(victory);
						}
						//Game continues if not catscratch, x win, or
						//owin
						else if(stat == GameStatus.IN_PROGRESS){

						}
						//Displays the board icons
						displayBoard();
					}
				}
			}
		}

		/**************************************************************
		 * Helper method that updates the JLabels. Uses the Scanner
		 * class and a delimiter to retrieve the current number that
		 * the label is representing as a string, converts the number
		 * string to an integer, and increments that number by one and
		 * updates the JLabel
		 * 
		 * @param pstat Status of the game after each move
		 * @param none
		 *************************************************************/
		private void updateLabel(GameStatus pstat){

			GameStatus stat = pstat;
			if(stat == GameStatus.X_WON){
				Scanner scan = new Scanner(xwin.getText()).useDelimiter("[^0-9]+");
				game.setXwins(scan.nextInt());
				scan.close();
				xwin.setText("X Wins: "+game.getXwins());
			}
			else if(stat ==GameStatus.O_WON){

				Scanner scan = new Scanner(owin.getText()).useDelimiter("[^0-9]+");
				game.setOwins(scan.nextInt());
				scan.close();
				owin.setText("O Wins: "+game.getOwins());
			}

			else if(stat == GameStatus.CATS){

				Scanner scan = new Scanner(cat.getText()).useDelimiter("[^0-9]+");
				game.setCat(scan.nextInt());
				scan.close();
				cat.setText("Catscratches: "+game.getCat());
			}
		}
	}

	/******************************************************************
	 * Method that generates a JOptionPane informing the user of the 
	 * results of the game and then giving them several options to
	 * continue.
	 * 
	 * @param victor String that changes the text based on who one
	 * @return none
	 *****************************************************************/
	private void endGame(String victor){

		JPanel quit = new JPanel();

		Object[] options = {"New Game","Save and Quit", "Quit "
				+ "without Saving"};

		int n = JOptionPane.showOptionDialog(quit, victor+ " is the "
				+ "WINNER!",victor+ " Wins!", 
				JOptionPane.YES_NO_CANCEL_OPTION, 
				JOptionPane.QUESTION_MESSAGE,
				null,
				options,
				options[2]);

		if (n == JOptionPane.YES_OPTION){
			//Calls the reset method to reset the game
			gameReset();

		}

		else if (n == JOptionPane.NO_OPTION){

			//Calls the save method to save the current game state into
			//a file and then exits the game
			game.save();
			System.exit(0);
		}

		else if (n == JOptionPane.CANCEL_OPTION){
			//Exits the game
			System.exit(0);
		}

	}

	/******************************************************************
	 * Reset method that removes the old game board and reinstantiates
	 * a new board with different initial values
	 * 
	 * @param none
	 * @return none
	 *****************************************************************/
	private void gameReset(){

		boardPanel.removeAll();
		boardPanel.revalidate();
		boardPanel.repaint();
		createNewGame();
		boardPanel.revalidate();
		boardPanel.repaint();

	}

	/******************************************************************
	 * Shows a JOptionPane with various options when the user selects
	 * the quit button.
	 * 
	 * @param none
	 * @return none
	 *****************************************************************/
	private class QuitListener implements ActionListener {


		/**************************************************************
		 * Shows a JOptionPane with various options when the user 
		 * selects the quit button.
		 * 
		 * @param none
		 * @return none
		 *************************************************************/
		public void actionPerformed(ActionEvent e) {

			JPanel quit = new JPanel();

			Object[] options = {"Save and Quit","Quit without saving", 
					"Cancel"};

			int n = JOptionPane.showOptionDialog(quit, "Are you "
					+ "sure you want to quit?","Quit", 
					JOptionPane.YES_NO_CANCEL_OPTION, 
					JOptionPane.QUESTION_MESSAGE,
					null,
					options,
					options[2]);

			if (n == JOptionPane.YES_OPTION){

				game.save();
				System.exit(0);

			}

			else if (n == JOptionPane.NO_OPTION){

				System.exit(0);

			}

			else if (n == JOptionPane.CANCEL_OPTION){

			}
		}
	}

	/******************************************************************
	 * Uses the setIsX() method to put an integer that evaluates
	 * who will initially start the game
	 * 
	 * @param none
	 * @return none
	 *
	 *****************************************************************/
	private class ChoiceListener implements ActionListener{

		/*************************************************************
		 Uses the setIsX() method to put an integer that evaluates
		 * who will initially start the game
		 * 
		 * @param none
		 * @return none
		 *************************************************************/
		public void actionPerformed(ActionEvent e){

			Object source = e.getSource();

			if(source == X){
				setIsX(0);

			}
			else if(source == O){
				setIsX(1);

			}
		}
	}

	/******************************************************************
	 * Undoes the current user's previous move when the undo button
	 * is selected
	 * 
	 * @param none
	 * @return none
	 *
	 *****************************************************************/
	private class UndoListener implements ActionListener {

		/**************************************************************
		 * Undoes the current user's previous move when the undo button
		 * is selected
		 * 
		 * @param none
		 * @return none
		 *
		 *************************************************************/
		public void actionPerformed(ActionEvent e){

			game.undo();
			displayBoard();
		}
	}

	/******************************************************************
	 * Saves the current game when the save button is selected
	 * 
	 * @param none
	 * @return none
	 *
	 *****************************************************************/
	private class SaveListener implements ActionListener {

		/**************************************************************
		 * Saves the current game when the save button is selected
		 * 
		 * @param none
		 * @return none
		 *
		 *************************************************************/
		public void actionPerformed(ActionEvent e){
			
			game.save();

		}
	}

	/******************************************************************
	 * Loads a saved game when the load button is selected
	 * 
	 * @param none
	 * @return none
	 *
	 *****************************************************************/
	private class LoadListener implements ActionListener {

		/**************************************************************
		 * Loads a saved game when the load button is selected
		 * 
		 * @param none
		 * @return none
	     *
		 *************************************************************/
		public void actionPerformed(ActionEvent e){

			String message = "Load a new game?";
			Object[] optionpaneopt = {"Continue", "Cancel"};

			//Integer that receives the result of the user's choice on
			//the option pane
			int n = JOptionPane.showOptionDialog(null,message, "Load",
					JOptionPane.YES_NO_OPTION, 
					JOptionPane.QUESTION_MESSAGE, null, 
					optionpaneopt,optionpaneopt[1]);

			if (n == JOptionPane.YES_OPTION){

				boardPanel.removeAll();
				boardPanel.revalidate();
				boardPanel.repaint();
				game.load();
				loaderMethod();
				boardPanel.revalidate();
				boardPanel.repaint();
				displayBoard();
			
			}

			else if (n == JOptionPane.NO_OPTION){
				
			}			
		}
	}

	/******************************************************************
	 * Gets who goes first
	 *
	 *****************************************************************/
	public int getIsX() {
		return isX;
	}

	/******************************************************************
	 * Sets who goes first
	 *
	 *****************************************************************/
	public void setIsX(int isX) {
		this.isX = isX;
	}
	
	/******************************************************************
	 * Gets size of the board
	 *
	 *****************************************************************/
	public int getInputnum() {
		return inputnum;
	}

	/******************************************************************
	 *Sets size of the board
	 *
	 *****************************************************************/
	public void setInputnum(int inputnum) {
		this.inputnum = inputnum;
	}

	/******************************************************************
	 *Gets win size
	 *
	 *****************************************************************/
	public int getWin() {
		return win;
	}

	/******************************************************************
	 * Sets win size
	 *
	 *****************************************************************/
	public void setWin(int win) {
		this.win = win;
	}
}
