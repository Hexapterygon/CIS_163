package package1;

/******************************************************************
 * The Game class is a DVD. Most methods in the Game class are
 * identical to those that are in the DVD Class. The
 * determineLateFee() and the toString() methods are overridden.
 * 
 *  @param -none
 *  @return - none
 *****************************************************************/
public class Game extends DVD {

	/*
	 * The enumerated type that stores the types of platers
	 */
	private PlayerType player;

	/******************************************************************
	 * Constructor for the Game class.
	 * 
	 * @param none
	 * @return none
	 *****************************************************************/
	public void Game(){

	}

	/******************************************************************
	 * Sets the PlayerType to the user selected option.
	 * 
	 * @param usrType - PlayerType that is the type of player for the
	 *                  game. Passed in from the DialogRentGame class.
	 *****************************************************************/
	public void setPlayerType(PlayerType usrType){

		player = usrType;

	}

	/******************************************************************
	 * Gets the playerType stored in this game
	 * 
	 * @param none
	 * @return player - PlayerType the playerType in this game 
	 *****************************************************************/
	public PlayerType getPlayerType(){

		return player;
	}

	/******************************************************************
	 * Overridden method of the DVD class. Method used to determine how 
	 * much an individual will be charged based on the due date and 
	 * the return date. On time games are $5.00 and 
	 * $10.00, respectively.
	 * 
	 * @param - none
	 * @return - String Message to be displayed upon return
	 *****************************************************************/
	public String determineLateFee(){

		/*
		 * Initialize an empty String for the message
		 */
		String cost = "";

		/*
		 * If a game is late
		 */
		if(getReturnDate().compareTo(getBoughtReturn()) > 0){

			cost = "$10.00";

		}
		/*
		 * If a game is early or on time
		 */
		else if(getReturnDate().compareTo(getBoughtReturn()) <= 0){

			cost = "$5.00";

		}

		/*
		 * Returns a String message to be displayed on a JDialog from
		 * the GUIRentalStore class
		 */
		return "Thanks, " + getNameofRenter() +", for returning "+ 
		getTitle() + ". You owe " + cost;

	}
	/******************************************************************
	 * Overriden method of the DVD class. Overrides the Object toString 
	 * so that when a DVD is displayed, it has a visually appealing 
	 * format that is simple and easy to follow.
	 * 
	 * @param none
	 * @return String A nicely formated string of the Game components
	 *****************************************************************/
	public String toString(){


		/*
		 *Puts the rental and due dates into two nicely formated
		 *Strings.
		 */
		df.setCalendar(bought);
		String jazz = df.format(bought.getTime());
		df.setCalendar(boughtReturn);
		String jazz2 = df.format(boughtReturn.getTime());


		/*
		 * Returns the components of the DVD class. Categories are 
		 * highlighted in red using html codes.
		 */
		return "<html><font color='red'>Name: </font>" + 
		getNameofRenter() + ", <font color='red'>Title: </font>" + 
		getTitle() + ", <font color='red'>Rented On: </font>"+ jazz + 
		", <font color='red'>Due Back On: </font>"+ jazz2 + ", "
		+ "<font color='red'>Type of Player: </font>"+ 
		getPlayerType();

	}
}