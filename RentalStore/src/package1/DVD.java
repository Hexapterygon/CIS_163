package package1;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

/******************************************************************
 * Class that is the object being stored by the ListEngine and displayed 
 * by the GUIRentalStore. Holds the renter name,the title of the DVD, 
 * the rental date, and the due date.
 *****************************************************************/
public class DVD implements Serializable, Comparable <DVD>{

	/*
	 * Identification used for saving the program state to a binary
	 * file
	 */
	private static long serialVersionUID = 1L;
	/*
	 * DateFormat used for formatting the various dates
	 */
	protected DateFormat df;
	/*
	 * String[] of various formats to be entered into the dateFormat.
	 * Allows for multiple date input formats
	 */
	protected String[] formats;
	/*
	 * Date the DVD was rented
	 */
	protected GregorianCalendar bought;
	/*
	 * Date the DVD is due back
	 */
	protected GregorianCalendar boughtReturn;
	/*
	 * Date the DVD is returned
	 */
	protected GregorianCalendar ReturnDate;
	/*
	 * Title of the DVD
	 */
	protected String title;
	/*
	 * Name of the renter
	 */
	protected String nameofRenter;


	/****************************************************************
	 * Constructs a DVD. Since all relevant user data is passed into
	 * the DVD class, the constructor does not initialize anything
	 * other than the String[] of formats.
	 * 
	 * @param - none
	 * @return - none
	 *****************************************************************/
	public DVD(){

		/*
		 * Creates and stores multiple potential input formats
		 */
		formats = new String[]{"MM-dd-yy","MM/dd/yy","MM-dd-yyyy",
		"MM/dd/yyyy"};
	}

	/******************************************************************
	 *Sets the day the DVD was rented using data passed in from the
	 *DialogRentDVD class.
	 *
	 * @param boughtDay - String that is user input for the rental date
	 * @return - none
	 * @throws Exception If the date is improperly formatted
	 *****************************************************************/
	public void setBought(String boughtDay) throws Exception{

		/*
		 * Uses a for:each loop to test for a format matching the user
		 * input. If no matching format is found in the String[], the 
		 * input is considered invalid and an Exception is thrown. 
		 */
		for( String format: formats){
			try{
				
				/*
				 * Catches empty input
				 */
				if(boughtDay.length() ==0){
					throw new Exception();
				}
				
				df = new SimpleDateFormat(format);
				/*
				 * Attempts to parse the user input with the current
				 * format. If the format is a mismatch, ParseException
				 * is thrown.
				 */
				Date date = df.parse(boughtDay);
				bought = new GregorianCalendar();
				bought.setTime(date);
				/*
				 *Breaks the for:each loop if the input is successfully
				 *parsed, preventing unnecessary cycles.
				 */
				break;
			}
			/*
			 * Exception that allows multiple attempts at formatting
			 * for each string in the String[] format.
			 */
			catch (ParseException e){}
		}
	}

	/******************************************************************
	 *Sets the DVD due date using data passed in from the DialogRentDVD 
	 *class.
	 *
	 * @param returnDay - String that is user input for the due date
	 * @return none
	 * @throws Exception If an incorrectly formatted date is entered
	 *****************************************************************/
	public void setBoughtReturn(String returnDay) throws Exception{

		/*
		 * Catches empty input
		 */
		if(returnDay.length() ==0){
			throw new Exception();
		}
		
		/*
		 * Follows the same logic as the setBought(String) method
		 */
		for( String format: formats){

			try{
				df = new SimpleDateFormat(format);
				Date date = df.parse(returnDay);
				boughtReturn = new GregorianCalendar();
				boughtReturn.setTime(date);
				break;
			}
			catch (ParseException e){}
		}

		/*
		 * Checks to see if the due date was set to be before the
		 * rental date. If the due date was set before the rental date,
		 * input is invalid.
		 */
		if(getBoughtReturn().compareTo(getBought()) < 0){

			throw new Exception();
		}
	}

	/******************************************************************
	 *Sets the DVD return date using data passed in from the 
	 *GUIRentalStore class.
	 *
	 * @param returnDay - String that is user input for the return date
	 * @return - none
	 * @throws Exception If an incorrectly formatted date is entered
	 *****************************************************************/
	public void setReturnDate(String returnDayDate) throws Exception{

		/*
		 * Follows the same logic as the setBought(String) method.
		 * New dates and SimpleDateFormats were instantiated to avoid
		 * conflict.
		 */
		for( String format: formats){

			try{
				SimpleDateFormat form = new SimpleDateFormat(format);
				Date date2 = form.parse(returnDayDate);
				ReturnDate = new GregorianCalendar();
				ReturnDate.setTime(date2);
				break;
			}
			catch (ParseException e){

			}
		}
	}

	/******************************************************************
	 * Sets the title of the DVD using data passed in from the 
	 * DialogRentDVD class.
	 * 
	 * @param usrTitle - String that is user input for the title
	 * @return - none
	 * @throws Exception If the String is empty
	 *****************************************************************/
	public void setTitle(String usrTitle) throws Exception{

		/*
		 * Input is invalid if an empty String is passed in.
		 */
		if(usrTitle.length() == 0){

			throw new Exception();
		}

		title = usrTitle;
	}

	/******************************************************************
	 * Sets the name of the renter using data passed in from the 
	 * DialogRentDVD class
	 * 
	 * @param usrName - String that is the user's input for renter name
	 * @return - none
	 * @throws Exception If the String is empty
	 *****************************************************************/
	public void setNameofRenter(String usrName) throws Exception{

		/*
		 * Input is invalid if an empty String is passed in.
		 */
		if(usrName.length() == 0){
			throw new Exception();
		}
		nameofRenter = usrName;
	}

	/******************************************************************
	 * Gets the date that the DVD was rented on
	 * 
	 * @param - none
	 * @return bought - GregorianCalendar representative of rental date
	 *****************************************************************/
	public GregorianCalendar getBought(){

		return bought;
	}

	/******************************************************************
	 * Gets the due date for the DVD
	 * 
	 * @param - none
	 * @return - boughtReturn GregorianCalendar that is the due date
	 *****************************************************************/
	public GregorianCalendar getBoughtReturn(){

		return boughtReturn;
	}

	/******************************************************************
	 * Gets the date the DVD was returned
	 * 
	 * @param -none
	 * @return ReturnDate - GregorianCalendar that is date returned
	 *****************************************************************/
	public GregorianCalendar getReturnDate(){

		return ReturnDate;
	}

	/******************************************************************
	 * Gets the title of the DVD
	 * 
	 * @param - none
	 * @return title - String that is the title of the DVD
	 *****************************************************************/
	public String getTitle(){

		return title;
	}

	/******************************************************************
	 * Uses the compareTo() method in the Calendar class to determine
	 * if the return date is before the rental date. If the return date
	 * is before the rental date, the input is considered invalid.
	 * 
	 * @param -none
	 * @return -none
	 * @throws Exception If the return date is before the due date
	 *****************************************************************/
	public void compareRentalReturn() throws Exception{

		if(getReturnDate().compareTo(getBought()) < 0){

			throw new Exception();
		}

	}

	/******************************************************************
	 * Method used to determine how much an individual will be charged
	 * based on the due date and the return date. On time DVD's and 
	 * games are $1.20 and $5.00, respectively.
	 * 
	 * @param none
	 * @ return String -Message 
	 *****************************************************************/
	public String determineLateFee(){

		/*
		 * Initialize an empty String for the message
		 */
		String cost = "";

		/*
		 * If the DVD is late
		 */
		if(getReturnDate().compareTo(getBoughtReturn()) > 0){

			cost = "$2.00";

		}

		/*
		 * If the DVD is early or on time
		 */
		else if(getReturnDate().compareTo(getBoughtReturn()) <= 0){

			cost = "$1.20";

		}

		/*
		 * Returns a String message to be displayed on a JDialog from
		 * the GUIRentalStore class
		 */
		return "Thanks, " + getNameofRenter() +", for returning "
		+ getTitle() + ". You owe " + cost;

	}

	/******************************************************************
	 * Gets the name of the renter
	 * 
	 * @param - none
	 * @ return nameofRenter - String that is the name of the renter
	 *****************************************************************/
	public String getNameofRenter(){

		return nameofRenter;
	}

	/******************************************************************
	 * Overrides the Object to String so that when a DVD is displayed,
	 * it has a visually appealing format that is simple and easy to
	 * follow.
	 * 
	 * @param - none
	 * @return - String
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
		", " + "<font color='red'>Due Back On: </font>"+ jazz2;

	}

	/******************************************************************
	 * Overrides the compareTo() method of the comparable interface
	 * so that the ListEngine can sort the DVD's using
	 * collections.sort. Sorts by due date and then by title
	 * 
	 * @param  o - DVD a DVD object to be compared against this one 
	 * @return - order Integer that is used to determine date order
	 *****************************************************************/
	public int compareTo(DVD o) {

		int order = 0;

		/*
		 * Checks to see if this DVD's date is before, after, or the
		 * same as the other's.
		 */
		order = this.getBoughtReturn().compareTo(o.getBoughtReturn());

		/*
		 * If the dates are the same, sort by alphabetical title. 
		 */
		if(order == 0){

			order = this.getTitle().compareTo(o.getTitle());
		}

		return order;
	}	
}