package package1;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.io.*;
import javax.swing.AbstractListModel;

/******************************************************************
 * Class that serves as the model for the Rental Store program.
 * Stores an ArrayList <DVD> and displays them through the 
 * GUIRentalStore JList. Executes the renting, returning, loading,
 * saving, sorting, and searching functions of the store. Extends 
 * AbstractListModel
 * 
 * @param - none
 * @return - none
 *****************************************************************/
public class ListEngine extends AbstractListModel{

	/*
	 * Stores currently rented DVD's and games.
	 */
	private ArrayList<DVD> listDVDs;

	/*
	 * Stores a user inputed date for searching
	 */
	private GregorianCalendar compare;

	/******************************************************************
	 * Constructs the ListEngine by initializing the ArrayList<DVD>
	 * 
	 * @param - none
	 * @return - none
	 *****************************************************************/
	public ListEngine(){

		listDVDs = new ArrayList<DVD>();

	}

	/******************************************************************
	 * Returns the DVD/Game located at loc for the JList to display.
	 * 
	 * @param loc -Integer that is the size of the ArrayList.
	 * @return Object - Returns the DVD element located at loc
	 *****************************************************************/
	public Object getElementAt(int loc){

		return listDVDs.get(loc);

	}

	/******************************************************************
	 * Returns the size of the ArrayList<DVD>
	 * 
	 * @param - none
	 * @return Integer- Returns the integer size of the ArrayList<DVD>
	 *****************************************************************/
	public int getSize(){

		return listDVDs.size();

	}

	/******************************************************************
	 * Adds a DVD/Game sent from the GUIRentalStore class to the
	 * ArrayList<DVD> and notifies the JList of this addition
	 * immediately. Also calls the update() method to sort and update
	 * the JList. 
	 * 
	 * @param d - DVD/Game containing pertinent rental information
	 * @return - none
	 *****************************************************************/
	public void addDVD(DVD d){

		listDVDs.add(d);

		/*
		 * Informs the JList that an element has been added to the
		 * model.
		 */
		this.fireIntervalAdded(this, getSize(), getSize());
		this.update();
	}

	/******************************************************************
	 * Removes the currently selected DVD/Game highlighted in the JList
	 * and notifies the JList of this removal immediately. Also calls 
	 * the update() method to sort and update the JList. 
	 * 
	 * @param remove - DVD/Game that is currently highlighted in JList
	 * @return - none
	 *****************************************************************/
	public void removeDVD(DVD remove){

		listDVDs.remove(remove);

		/*
		 * Informs the JList that an element has been removed from the
		 * model
		 */
		this.fireIntervalRemoved(this, getSize(), getSize());
		this.update();
	}

	/******************************************************************
	 * Method that searches for specific DVD's/Games in the ArrayList
	 * of DVD's based on two parameters and adds the found items into
	 * a new ArraList<DVD> for the purpose of displaying them
	 * separately.
	 * 
	 * @param determine - String user input: date or search word
	 * @param type -Integer that determines which search to use
	 * @return found - ArrayList<DVD> A list of DVDs that were found 
	 *****************************************************************/
	public ArrayList<DVD> Search(String determine, int type) 
			throws Exception{


		/*
		 * A new ArrayList<DVD> for items found using the search
		 * parameters. Fed back into GUIRentalStore to display to
		 * the user the results of the search.
		 */
		ArrayList<DVD> found = new ArrayList<DVD>();

		/*
		 * If the search is using a search word, an iterator moves
		 * through listDVDs and uses String's contain() method to see
		 * if the title of each DVD contains the user search word.
		 */
		if(type == 1){
			for (Iterator<DVD> iterator = listDVDs.iterator(); 
					iterator.hasNext();) {

				DVD d = iterator.next();

				if(d.getTitle().toLowerCase().contains(determine.
						toLowerCase())){

					/*
					 * Match found and the DVD is added to the display
					 * list.
					 */
					found.add(d);

				}
			}
		}

		/*
		 * If the search is using a date, an iterator moves through
		 * listDVDs and uses the Calendar's compareTo() method to see
		 * if the DVD's due date is after the compare date.
		 */
		if(type == 2){

			for (Iterator<DVD> iterator = listDVDs.iterator(); 
					iterator.hasNext();) {

				DVD d = iterator.next();

				if(d.getBoughtReturn().compareTo(compare) > 0){

					/*
					 * Match found and the DVD is added to the display
					 * list.
					 */
					found.add(d);
				}
			}
		}

		return found;
	}

	/******************************************************************
	 * Method that sets the compare GregorianCalendar using data
	 * passed in from the GUIRentalStore class.
	 * 
	 * @param determine - String User entered date
	 * @return - none
	 *****************************************************************/
	public void setBeforeDate(String determine){

		/*
		 * Creates and stores multiple potential input formats
		 */
		String[] formats = new String[]{"MM-dd-yy","MM/dd/yy",
				"MM-dd-yyyy","MM/dd/yyyy"};

		/*
		 * Uses a for:each loop to test for a format matching the user
		 * input. If no matching format is found in the String[], the 
		 * input is considered invalid and an Exception is thrown.
		 */
		for( String format: formats){

			try{
				SimpleDateFormat form = new SimpleDateFormat(format);

				/*
				 * Attempts to parse the user input with the current
				 * format. If the format is a mismatch, ParseException
				 * is thrown.
				 */
				Date date2 = form.parse(determine);
				compare = new GregorianCalendar();
				compare.setTime(date2);

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
	 * Gets the compare date to be used in comparisons for searches.
	 * 
	 * @param - none
	 * @return  compare - GregorianCalendar date used in searching
	 *****************************************************************/
	public GregorianCalendar getBeforeDate(){

		return compare;
	}

	/******************************************************************
	 * Method that ultimately informs the JList of what has occurred
	 * every time an object is added, removed, or sorted. Is also 
	 * responsible for sorting via the Collections class.
	 * 
	 * @param - none
	 * @return - none
	 *****************************************************************/
	public void update(){

		/*
		 * Sorts the DVDs by utilizing the overridden compareTo() method
		 * in the DVD class. Sorts by due date first and then by
		 * alphabetical order of the title.
		 */
		Collections.sort(listDVDs);
		this.fireContentsChanged(this, getSize(), getSize());
	}

	/******************************************************************
	 * Loads a text file as a listDVDs utilizing BufferedReader.
	 * 
	 * @param filename - String The filepath of the file to be loaded
	 * @return none
	 * @throws Exception if something goes wrong in the reading process
	 *****************************************************************/
	public void loadText(String filename)throws Exception{


		/*
		 * Uses an iterator to empty out the current list of DVD's
		 * so that the loaded file is not added to the current
		 * session
		 */
		for (Iterator<DVD> iterator = listDVDs.iterator(); 
				iterator.hasNext();) {

			DVD d = iterator.next();
			iterator.remove();

		}
		BufferedReader in = new BufferedReader(new FileReader
				(filename));

		FileWriter fstream = new FileWriter("file.out");
		BufferedWriter out = new BufferedWriter(fstream);

		/*
		 * Retrieves the first line of the file which is an integer
		 * representing the number of DVDs/games in the file
		 */
		String line = in.readLine(); // <-- read whole line
		int loop = Integer.parseInt(line);

		/*
		 * Uses the integer to determine the number of times to loop.
		 */
		for(int i = 1; i <= loop; i++){

			/*
			 * Retrieves the next line after the integer which is
			 * the class name of the saved object. This can be used
			 * to determine if the object is a game or a DVD.
			 */
			String nxLine = in.readLine();

			/*
			 * If the object is a game, add all of the components 
			 * of the Game class line by line using the
			 * BufferedReader's readLine() method.
			 */
			if(nxLine.equals("package1.Game")){

				Game bDVD = new Game();
				bDVD.setBought(in.readLine());
				bDVD.setBoughtReturn(in.readLine());
				bDVD.setTitle(in.readLine());
				bDVD.setNameofRenter(in.readLine());
				bDVD.setPlayerType(PlayerType.valueOf(in.readLine()));
				/*
				 * Add the loaded Game to the ArrayList<DVD>
				 */
				addDVD(bDVD);
			}

			/*
			 * If the object is a DVD, add all of the components 
			 * of the DVD class line by line using the
			 * BufferedReader's readLine() method.
			 */
			else if(nxLine.equals("package1.DVD")){
				DVD bDVD = new DVD();
				bDVD.setBought(in.readLine());
				bDVD.setBoughtReturn(in.readLine());
				bDVD.setTitle(in.readLine());
				bDVD.setNameofRenter(in.readLine());
				/*
				 * Add the loaded DVD to the ArrayList<DVD>
				 */
				addDVD(bDVD);
			}

		}
		out.close();

	}

	/******************************************************************
	 * Saves listDVDs to a text file using PrintWriter and for loop
	 * 
	 * @param filename - String filepath of the file to be saved.
	 * @return - none
	 * @throws IOException if something goes wrong in the save process
	 *****************************************************************/
	public void saveText(String filename)throws IOException{


		PrintWriter out = new PrintWriter(new BufferedWriter
				(new FileWriter(filename)));

		/*
		 * Saves the size of listDVDs as the first line
		 */
		out.println(listDVDs.size());

		for (int i = 0; i < listDVDs.size(); i++) {

			/*
			 * listDVDs is an ArrayList<DVD>
			 */
			DVD dvdUnit = listDVDs.get(i);   	

			/*
			 * Output the class name. 
			 */
			out.println(dvdUnit.getClass().getName()); 

			/*
			 *  Output the rentOn date to a file in a readable format.
			 */
			out.println(DateFormat.getDateInstance(DateFormat.SHORT).
					format(dvdUnit.getBought().getTime()));

			/*
			 *  Output the return date to a file in a readable format.
			 */
			out.println(DateFormat.getDateInstance(DateFormat.SHORT).
					format(dvdUnit.getBoughtReturn().getTime()));

			/*
			 *  Output the Title of the DVD
			 */
			out.println(dvdUnit.getTitle());

			/*
			 *  Output the name of the renter
			 */
			out.println(dvdUnit.getNameofRenter());

			/* See if the current dvdUnit is a game, if so output the
			 * player. 
			 */   
			if (dvdUnit instanceof Game)
				out.println(((Game)dvdUnit).getPlayerType());
		}
		out.close();
	}

	/******************************************************************
	 * Loads from a binary file using FileInputStream and 
	 * ObjectInputStream
	 * 
	 * @param - none
	 * @return - none
	 * @throws - Exception If something goes wrong with loading
	 *****************************************************************/
	public void loadBinary() throws Exception{

		/*
		 * Opens file for streaming
		 */
		FileInputStream fis = new FileInputStream("BinarySave");
		/*
		 * Streams object from the lines in the file
		 */
		ObjectInputStream ois = new ObjectInputStream(fis);
		/*
		 * Casts the object as an ArrayList<DVD> to load
		 */
		listDVDs = ((ArrayList<DVD>)ois.readObject());
		ois.close();
		fis.close();

		/*
		 * Updates the view to the newly loaded state
		 */
		update();
	}

	/******************************************************************
	 * Saves to a binary file using FileOutputSteam and 
	 * ObjectOutputStream
	 * 
	 * @param - none
	 * @return - none
	 *****************************************************************/
	public void saveBinary(){

		try{
			/*
			 * Creates a new file to stream to
			 */
			FileOutputStream fos= new FileOutputStream("BinarySave");
			/*
			 * Allows objects to stream to the file
			 */
			ObjectOutputStream oos= new ObjectOutputStream(fos);
			/*
			 * Write listDVDs to the binary file
			 */
			oos.writeObject(listDVDs);
			oos.close();
			fos.close();
		}catch(IOException ioe){
			ioe.printStackTrace();
		}

	}

}