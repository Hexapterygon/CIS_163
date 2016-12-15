package package1;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.*;

/******************************************************************
 * Class that serves as the view for the program. Also contains
 * the main method for driving the program. Navigable with a
 * JMenuBar and JMenuItems and displays the DVDs/Games using a JList
 *****************************************************************/
public class GUIRentalStore extends JFrame implements ActionListener {

	/*
	 * Menu bar for the GUI
	 */
	private JMenuBar bar;
	/*
	 * Option on the menu bar for saving, loading, and exiting
	 */
	private JMenu fileMen;
	/*
	 * Option on the menu bar for renting, returning and searching
	 */
	private JMenu actionMen;
	/*
	 * Menu Item that opens a binary file
	 */
	private JMenuItem openSerial;
	/*
	 * Menu item that saves a binary file
	 */
	private JMenuItem saveSerial;
	/*
	 * Menu item that opens a text file
	 */
	private JMenuItem openText;
	/*
	 * Menu item that saves a text file
	 */
	private JMenuItem saveText;
	/*
	 * Menu item that exits the program
	 */
	private JMenuItem exit;
	/*
	 * Menu item that rents a DVD
	 */
	private JMenuItem rentDVD;
	/*
	 * Menu item that rents a game
	 */
	private JMenuItem rentGame;
	/*
	 * Menu item that returns a DVD
	 */
	private JMenuItem returnDVD;
	/*
	 *  Menu item that executes a String search
	 */
	private JMenuItem Search;
	/*
	 * Menu item that executes a date search
	 */
	private JMenuItem SearchDate;
	/*
	 * Integer that holds the type of search
	 */
	private int searchType;
	/*
	 * A separator
	 */
	private JSeparator separator;
	/*
	 * Another separator
	 */
	private JSeparator separator2;
	/*
	 * Yet another separator
	 */
	private JSeparator separator3;
	/*
	 * Still yet another separator
	 */
	private JSeparator separator4;
	/*
	 * Scroll pane for when the JList gets too large
	 */
	private JScrollPane scroll;
	/*
	 * Engine that performs the logic for the view
	 */
	private ListEngine engine;
	/*
	 * Allows the user to pick a file
	 */
	private JFileChooser choose;
	/*
	 * Creates the interactive display window for the view
	 */
	private JList<DVD> view;


	/******************************************************************
	 * Main method that is used to run the rental store program as a
	 * java application. Makes the viewer class visible to the user.
	 * 
	 * @param args String[]: Commands that can be passed into the main
	 * @return None
	 *****************************************************************/
	public static void main(String[] args){

		GUIRentalStore view = new GUIRentalStore();
		view.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		view.getContentPane();
		view.setPreferredSize(new Dimension(800,300));
		view.pack();
		view.setVisible(true);
		view.setLocationRelativeTo(null);


	}

	/******************************************************************
	 * Constructor method for the viewer class GUIRentalStore. Creates
	 * a user interactive GUI for the rental store program by
	 * instantiating all of the GUI components and adding them to the 
	 * class JFrame.
	 * 
	 * @param None
	 * @return None
	 *****************************************************************/
	public GUIRentalStore(){	

		super("Rental Store!");

		engine = new ListEngine();

		view = new JList<DVD>(engine);


		searchType = 0;

		scroll = new JScrollPane();

		bar = new JMenuBar();
		fileMen = new JMenu("File");
		actionMen = new JMenu("Action");

		separator = new JSeparator();
		separator2 = new JSeparator();
		separator3 = new JSeparator();
		separator4 = new JSeparator();

		openSerial = new JMenuItem("Open Serial...");
		saveSerial = new JMenuItem("Save Serial...");
		openText = new JMenuItem("Open Text...");
		saveText = new JMenuItem("Save Text...");
		exit = new JMenuItem("Exit");

		rentDVD = new JMenuItem("Rent DVD");
		rentGame = new JMenuItem("Rent Game");
		returnDVD = new JMenuItem("Return");
		Search = new JMenuItem("Search by String...");
		SearchDate = new JMenuItem("Search by Date...");

		fileMen.add(openSerial);
		fileMen.add(saveSerial);
		fileMen.add(separator);
		fileMen.add(openText);
		fileMen.add(saveText);
		fileMen.add(separator2);
		fileMen.add(exit);

		actionMen.add(rentDVD);
		actionMen.add(rentGame);
		actionMen.add(separator3);
		actionMen.add(returnDVD);
		actionMen.add(separator4);
		actionMen.add(Search);
		actionMen.add(SearchDate);

		openSerial.addActionListener(this);
		saveSerial.addActionListener(this);
		openText.addActionListener(this);
		saveText.addActionListener(this);
		exit.addActionListener(this);

		rentDVD.addActionListener(this);
		rentGame.addActionListener(this);
		returnDVD.addActionListener(this);
		Search.addActionListener(this);
		SearchDate.addActionListener(this);

		bar.add(fileMen);
		bar.add(actionMen);

		scroll.getViewport().setView(view);

		this.setJMenuBar(bar);
		this.add(scroll);

	}

	/******************************************************************
	 * Calls the loadBinary() in the ListEngine class and loads a
	 * binary file that is a previous state of the ListEngine.
	 * 
	 * @param - none
	 * @return - none
	 *****************************************************************/
	private void selectOpenSerial(){

		System.out.println("OpenSerial");

		int result = JOptionPane.showConfirmDialog(this,"Open "
				+ "from a Binary File?",
				"Open Serial",JOptionPane.YES_NO_OPTION);

		if(result == JOptionPane.YES_OPTION){

			try{
				engine.loadBinary();

			}

			catch(Exception e){

				JOptionPane.showMessageDialog(this,"\tI/O Error "
						+ "\nAn error has occurred","Error",
						JOptionPane.WARNING_MESSAGE);
			}
		}
		else if(result == JOptionPane.NO_OPTION){

		}

	}

	/******************************************************************
	 * Calls the saveBinary() in the ListEngine class and saves off
	 * the ArrayList<DVD> as a binary file
	 * 
	 * @param - none
	 * @return - none
	 *****************************************************************/
	private void selectSaveSerial(){

		System.out.println("SaveSerial");

		int result = JOptionPane.showConfirmDialog(this,"Save to a "
				+ "Binary File?","Open Serial",
				JOptionPane.YES_NO_OPTION);

		if(result == JOptionPane.YES_OPTION){

			engine.saveBinary();
		}
		else if(result == JOptionPane.NO_OPTION){

		}
	}

	/******************************************************************
	 * Calls the loadText() in the ListEngine and uses a JFileChooser
	 * to select the file. Loads an ArrayList<DVD> from a text file.
	 * 
	 * @param - none;
	 * @return - none
	 *****************************************************************/
	private void selectOpenText(){

		System.out.println("OpenText");

		/*
		 * JFileChooser and a defualt directory
		 */
		choose = new JFileChooser("/home/anderlna/RentalStore");

		int returnVal = choose.showOpenDialog(this);
		if(returnVal == JFileChooser.APPROVE_OPTION) {

			try{
				engine.loadText(choose.getSelectedFile().getName());
			}
			catch(Exception e){

				JOptionPane.showMessageDialog(this,"\tI/O Error \nAn "
						+ "error has occurred","Error",
						JOptionPane.WARNING_MESSAGE);
			}
		}
	}

	/******************************************************************
	 * Calls the saveText() in the ListEngine and saves the 
	 * ArrayList<DVD> as a text file.
	 * 
	 * @param - none
	 * @return - none
	 *****************************************************************/
	private void selectSaveText(){

		/*
		 * JFileChooser and a defualt directory
		 */
		choose = new JFileChooser("/home/anderlna/RentalStore"){

			/*
			 * approveSelection() override prompts the user to make
			 * sure they want to overwrite an already existing file
			 */
			@Override
			public void approveSelection(){
				File f = getSelectedFile();
				if(f.exists() && getDialogType() == SAVE_DIALOG){
					int result = JOptionPane.showConfirmDialog(this,
							"The file " + "exists, overwrite?",
							"Existing file",
							JOptionPane.YES_NO_CANCEL_OPTION);

					switch(result){
					case JOptionPane.YES_OPTION:
						super.approveSelection();
						return;
					case JOptionPane.NO_OPTION:
						return;
					case JOptionPane.CLOSED_OPTION:
						return;
					case JOptionPane.CANCEL_OPTION:
						cancelSelection();
						return;
					}
				}
				super.approveSelection();
			}
		};        

		int returnVal = choose.showSaveDialog(this);
		if(returnVal == JFileChooser.APPROVE_OPTION) {

			try{
				engine.saveText(choose.getSelectedFile().getName());
				System.out.println("SaveText");
			}

			catch(Exception e){
				JOptionPane.showMessageDialog(this,"\tI/O Error "
						+ "\nAn error has occurred","Error",
						JOptionPane.WARNING_MESSAGE);
			}

		}





	}

	/******************************************************************
	 * Exits the program after a prompt.
	 *****************************************************************/
	private void selectExit(){

		int result = JOptionPane.showConfirmDialog(this,"Are you sure "
				+ "you want to exit?","Exit",JOptionPane.YES_NO_OPTION);

		if(result == JOptionPane.YES_OPTION){

			System.exit(EXIT_ON_CLOSE);
		}
		else if(result == JOptionPane.NO_OPTION){

		}
	}

	/******************************************************************
	 * Creates a new, blank DVD, allows DialogRentDVD to edit it, and
	 * sends it into ListEngine
	 * 
	 * @param - none
	 * @return - none
	 *****************************************************************/
	private void selectRentDVD(){

		DVD bDVD = new DVD();
		String dTitle = "Rent a DVD!";
		DialogRentDVD DVDBox = new DialogRentDVD(this, dTitle, bDVD);

		if(DVDBox.getCloseStatus()){
			engine.addDVD(bDVD);
		}
	}

	/******************************************************************
	 * Creates a new, blank game, allows DialogRentGame to edit it, and
	 * sends it into ListEngine.
	 * 
	 * @param - none
	 * @return - none
	 *****************************************************************/
	private void selectRentGame(){

		Game bGame = new Game();
		String gTitle = "Rent a Game!";
		DialogRentGame gameBox = new DialogRentGame(this, gTitle, 
				bGame);

		if(gameBox.getCloseStatus()){
			engine.addDVD(bGame);
		}
	}

	/******************************************************************
	 * Returns a DVD/ removes it from the ListEngine by creating 
	 * a dialog box that allows the user to enter in a date.
	 *****************************************************************/
	private void selectReturnDVD(){

		/*
		 * Creates dialog box if there is something in the view
		 */
		if(view.getSelectedValue() != null){
			String n = JOptionPane.showInputDialog(this,"Enter a "
					+ "return date!","Return",
					JOptionPane.QUESTION_MESSAGE);

			/*
			 * Sets the return date and compares it to make sure it
			 * is not smaller than the rental date
			 */
			if(n != null){
				try{
					view.getSelectedValue().setReturnDate(n);
					view.getSelectedValue().compareRentalReturn();

					/*
					 * Prints out cost and message when DVD is
					 * returned
					 */
					JOptionPane.showMessageDialog(this, 
							view.getSelectedValue().determineLateFee(), 
							"Thanks!", JOptionPane.PLAIN_MESSAGE);

					engine.removeDVD(view.getSelectedValue());
				}
				catch(Exception e){
					JOptionPane.showMessageDialog(this,"\tInvalid Date!"
							+ "\nEnter a return date AFTER Rental "
							+ "Date!","Invalid Return Attempt",
							JOptionPane.WARNING_MESSAGE);

					selectReturnDVD();
				}
			}
		}
	}

	/******************************************************************
	 * Takes a user input string and uses it to search the 
	 * ArrayList<DVD> for titles than contain that string.
	 * 
	 * @param - none
	 * @return - none
	 *****************************************************************/
	private void selectsearchString(){ //add to UML

		String foundDVDs = "";

		String n = JOptionPane.showInputDialog(this,"Enter a String "
				+ "used to return titles containing that String",
				"Return Included",JOptionPane.QUESTION_MESSAGE);

		if( n != null && n.length() != 0){

			try{

				/*
				 * Uses a for each loop to add each search hit
				 * to string to be displayed for the user
				 */
				for(DVD d:engine.Search(n,searchType)){

					foundDVDs += "\n"+d.toString();
				}
			}
			/*
			 * Throws a terrifying error when things go
			 * horribly wrong
			 */
			catch(Exception e){
				JOptionPane.showMessageDialog(this,"A critical error "
						+ "has occurred","Critical Error",
						JOptionPane.WARNING_MESSAGE);
				System.exit(0);

			}

		}

		/*
		 * If there are matches, it puts this out
		 */
		if(foundDVDs.length() != 0){
			int x = JOptionPane.showConfirmDialog(this, "Found: " + 
					foundDVDs, "Search String!", JOptionPane.
					PLAIN_MESSAGE);

		}
		/*
		 * If there are no matches, it puts this out
		 */
		else if(foundDVDs.length() == 0){
			int x = JOptionPane.showConfirmDialog(this, 
					"No matches found.","String Search!", 
					JOptionPane.PLAIN_MESSAGE);
		}
	}

	/******************************************************************
	 * Takes a user input date and searches the ArrayList<DVD> for 
	 * due dates after the user input date
	 *****************************************************************/
	private void selectsearchDate(){ //add to UML

		String foundDVDs = "";

		/*
		 * Asks the user for the date 
		 */
		String n = JOptionPane.showInputDialog(this,"Enter a Date to "
				+ "search for all movies past due",
				"Search Date",JOptionPane.QUESTION_MESSAGE);


		try{

			/*
			 * If the user does not hit cancel or enters nothing
			 */
			if( n != null){

				engine.setBeforeDate(n);

				/*
				 * Compares and writes to a string the search hits
				 * The math function finds the number of days past due
				 */
				for(DVD d:engine.Search(n,searchType)){

					foundDVDs += "\n"+d.toString() + ", <html><font "
							+ "color='red'>Days Past Due: </font>" +
							Math.abs((d.getBoughtReturn().
									getTimeInMillis()-
									engine.getBeforeDate().
									getTimeInMillis())/(1000*60*60*24));
				}

				/*
				 * Shows a message for the positive hits
				 */
				if(foundDVDs.length() != 0){
					int x = JOptionPane.showConfirmDialog(this,"Found: "
							+ "" + foundDVDs, "Date Search!", 
							JOptionPane.PLAIN_MESSAGE);

				}
				/*
				 * Shows a message for no hits
				 */
				else if(foundDVDs.length() == 0){
					int x = JOptionPane.showConfirmDialog(this, "No "
							+ "matches found.","Date Search!", 
							JOptionPane.PLAIN_MESSAGE);
				}
			}
		}
		catch(Exception e){

			/*
			 * Output for invalid date entry
			 */
			JOptionPane.showMessageDialog(this,"Invalid Return Date! "
					+ "\nEnter a valid date"
					+ " (ex: 10/10/2014)","Invalid Date",
					JOptionPane.WARNING_MESSAGE);
			selectsearchDate();

		}

	}

	/******************************************************************
	 * Method that listens for user actions applied to the JMenuItems.
	 * Will execute the proper method based on the option that the 
	 * user picks
	 * 
	 * @param e ActionEvent: Event that indicates a component action has
	 * 						 occurred
	 * 
	 * @return None
	 *****************************************************************/
	public void actionPerformed(ActionEvent e){

		if(e.getSource() == openSerial){
			this.selectOpenSerial();
		}

		if(e.getSource() == saveSerial){
			this.selectSaveSerial();
		}

		if(e.getSource() == openText){
			this.selectOpenText();
		}

		if(e.getSource() == saveText){
			this.selectSaveText();
		}

		if(e.getSource() == exit){
			this.selectExit();
		}

		if(e.getSource() == rentDVD){
			this.selectRentDVD();
		}

		if(e.getSource() == rentGame){
			this.selectRentGame();
		}

		if(e.getSource() == returnDVD){
			this.selectReturnDVD();
		}

		if(e.getSource() == Search){

			searchType = 1;
			this.selectsearchString();
		}

		if(e.getSource() == SearchDate){

			searchType = 2;
			this.selectsearchDate();
		}

	}
}