package package1;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.swing.*;

/******************************************************************
 * Class that is responsible for displaying the dialog window that 
 * allows the user to input information about the DVD being rented.
 * This information includes the name of the renter, the title of 
 * the DVD, the date rented, and the due date.
 * 
 * @param - none
 * @return - none
 *****************************************************************/
public class DialogRentDVD extends JDialog implements ActionListener{
	
	/*
	 * Where the user enters the DVD title
	 */
	private JTextField titleTxt;
	/*
	 * Where the user enters the name of the renter
	 */
	private JTextField renterTxt;
	/*
	 * Where the user enters the rental date
	 */
	private JTextField rentedOnTxt;
	/*
	 * Where the user enters the due date
	 */
	private JTextField dueBackOnTxt;
	/*
	 * Labels where the user enters the DVD title
	 */
	private JLabel titleLbl;
	/*
	 * Labels where the user enters the name of the renter
	 */
	private JLabel renterLbl;
	/*
	 * Labels where the user enters the rental date
	 */
	private JLabel rentedOnLbl;
	/*
	 * Labels where the user enters the due date
	 */
	private JLabel dueBackOnLbl;
	/*
	 * Button that enters the user entered data into the DVD class.
	 */
	private JButton okButton;
	/*
	 * Button that discards the dialog window and any entered data
	 */
	private JButton cancelButton;
	/*
	 * Boolean used to determine when the window has been closed and
	 * the data is "safe" to enter into the DVD class
	 */
	private boolean closeStatus;
	/*
	 * Object of the DVD class used to create a DVD from user entered
	 * data
	 */
	private DVD unit;



	/******************************************************************
	 * Constructor that accepts three parameters from the
	 * GUIRentalStore class. Constructs the dialog window using the
	 * GUIRentalStore's JFrame, gives it a title as set in the
	 * GUIRentalStore, and accepts a blank DVD to instantiate.
	 * 
	 * @param parent - JFrame used to construct the dialog window
	 * @param name - String that is set as the dialog window title
	 * @param d - DVD used to store rental data 
	 * @return - none
	 *****************************************************************/
	public DialogRentDVD(JFrame parent, String name, DVD d){
		/*
		 * Calling the parent constructor using the parameters to build    
		 * the dialog window
		 */
		super(parent,name,true);

		/*
		 * Sets the spawn location of the dialog window close to the 
		 * main GUI.
		 */
		this.setLocationRelativeTo(parent);

		/*
		 * Makes it so that the DVD for DialogRentDVD and the DVD passed
		 * in from GUIRentalStore point to the same address
		 */
		unit = d;

		/*
		 * Method that does the bulk of the construction work for the
		 * dialog window
		 */
		init();

	}

	/******************************************************************
	 * Initialization method that is responsible for initializing the
	 * instance variables of the DialogRentDVD class as well as
	 * packing and making the dialog window visible.
	 * 
	 * @param - none
	 * @return - none
	 *****************************************************************/
	private void init(){

		/*
		 * Ensures no data will be entered into DVD until okay button 
		 * is hit.
		 */
		closeStatus = false;

		/*
		 * Creates a GregorianCalendar of today's date
		 * and tomorrow's date as a default for the rentedOn and 
		 * dueBackOn JTextFields.
		 */
		Calendar cal = new GregorianCalendar();
		Calendar calT = new GregorianCalendar();
		/*
		 * Get current date
		 */
		cal.getInstance(getLocale());
		calT.getInstance(getLocale());
		/*
		 * Date format
		 */
		SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
		/*
		 * Formatting the date
		 */
		df.setCalendar(cal);
		df.setCalendar(calT);
		String today = df.format(cal.getTime());
		/*
		 * Getting tomorrow's date by adding a day
		 */
		calT.add(Calendar.DAY_OF_MONTH, 1);
		String tomorrow = df.format(calT.getTime());

		/*
		 * Creates two JPanels. j is for the labels and textfields.
		 * b is for the buttons
		 */
		JPanel j = new JPanel();
		JPanel b = new JPanel();

		/*
		 * Creates a border layout for the dialog window
		 */
		this.setLayout(new BorderLayout());
		/*
		 * Creates a Grid layout for the label/textfield panel
		 * such that there is one label and one textfield in each
		 * of the five rows on the panel
		 */
		j.setLayout(new GridLayout(5,2,75,30));

		renterTxt = new JTextField();
		renterLbl = new JLabel("Your Name:");
		titleTxt = new JTextField();
		titleLbl = new JLabel("Title of Movie:");
		rentedOnTxt = new JTextField(today);
		rentedOnLbl = new JLabel("Rented on Date:");
		dueBackOnTxt = new JTextField(tomorrow);
		dueBackOnLbl = new JLabel("Due Back On:");

		okButton = new JButton("OK");
		cancelButton = new JButton("Cancel");

		okButton.addActionListener(this);
		cancelButton.addActionListener(this);

		j.add(renterLbl);
		j.add(renterTxt);
		j.add(titleLbl);
		j.add(titleTxt);
		j.add(rentedOnLbl);
		j.add(rentedOnTxt);
		j.add(dueBackOnLbl);
		j.add(dueBackOnTxt);

		b.add(okButton);
		b.add(cancelButton);

		this.add(j);
		this.add(b, BorderLayout.SOUTH);

		pack();
		setVisible(true);


	}

	/******************************************************************
	 * Method that rigorously checks for invalid inputs and retrieves
	 * the user entered data from the GUI and feeds it into the DVD
	 * class. Error checking is achieved by catching errors thrown by
	 * the DVD class, displaying an appropriate message, preventing
	 * the invalid input attempt from executing, and allowing the user
	 * to try again.
	 * 
	 * @param - none
	 * @return - none
	 *****************************************************************/
	private void retrieveData(){

		/*
		 * Integer that is used to keep track of what JTextfield is
		 * responsible for the invalid input. Initialized to 4 because
		 * it is outside the range of set values.
		 */
		int textfield = 4;

		/*
		 * String that holds the warning window message
		 */
		String message;

		/*
		 * String that holds the warning window title 
		 */
		String title;

		/*
		 * Attempts to enter the user-typed renter name by sending it
		 * into the DVD class from the JTextField.
		 */
		try{
			unit.setNameofRenter(renterTxt.getText());
		}
		catch(Exception e){

			/*
			 * Sets the text field value to zero, sets a unique warning
			 * String and calls the errorPane method to construct an 
			 * error window and conduct more error handling.
			 */
			textfield = 0;
			message = "Invalid Name! \nEnter a name!"
					+ " (ex: Paul)";
			title = "Invalid Name";
			errorPane(message, title, textfield);

		}

		/*
		 * The if-statements prior to the try attempts in this method
		 * use the textfield value to prevent subsequent exceptions 
		 * from being thrown if one was already thrown. In this case, 
		 * if an error occurs in entering a renter name, all 
		 *subsequent data entry attempts are skipped
		 */
		if(textfield != 0){
			try{
				unit.setTitle(titleTxt.getText());
			}
			catch(Exception e){

				textfield = 1;

				message = "Invalid Movie Title! \nEnter a valid title"
						+ " (ex: Avengers)";
				title = "Invalid Title";
				errorPane(message, title, textfield);

			}
		}

		/*
		 * Attempts to put the user entered rental date into the 
		 * DVD class
		 */
		if(textfield != 1 && textfield != 0){
			try{
				unit.setBought(rentedOnTxt.getText());
				
				//System.out.println(unit.getBought());
			}

			catch(Exception e){

				textfield = 2;

				message = "Invalid Rental Date! \nEnter a valid date "
						+ "(ex: 10/10/2014)";
				title = "Invalid Rental Date";
				errorPane(message, title, textfield);

			}
		}

		/*
		 * Attempts to put the user entered due date into the DVD class.
		 */
		if(textfield != 2 && textfield != 1 && textfield != 0){
			try{
				unit.setBoughtReturn(dueBackOnTxt.getText());

			}

			catch(Exception e){

				textfield = 3;

				message = "Invalid Return Date! \nEnter a valid date"
						+ " (ex: 10/10/2014)";
				title = "Invalid Return Date";
				errorPane(message, title, textfield);
			}

		}

	}

	/******************************************************************
	 * Method responsible for the creation of the warning JOptionPane,
	 * disposal of the invalid text through the deleteInvalid() method,
	 * and the re-packing of DialogRentDVD to allow the user to try
	 * again.
	 * 
	 * @param message - String that holds the warning window message
	 * @param title - String that holds the warning window message
	 * @param textfield - Integer that indicates what JTextfield 
	 *                             produced the error
	 * @return none
	 *****************************************************************/
	private void errorPane(String message, String title, 
			int textfield){

		JOptionPane.showMessageDialog(this,message,title,
				JOptionPane.WARNING_MESSAGE);
		/*
		 * Removes the invalid input	
		 */
		deleteInvalid("",textfield);

		/*
		 * Disposes the DialogRentDVD and then immediately re-packs it
		 * so that the user can see their first incorrect input in the
		 * form of a blank jTextField. Sets closeStatus back to false 
		 * to prevent invalid data from entering the DVD class.
		 */
		dispose();
		closeStatus = false;
		pack();
		setVisible(true);

	}

	/******************************************************************
	 * Method that is responsible for removing invalid inputs from the
	 * JTextFields. Since the only invalid inputs for the renter name
	 * and the title are empty inputs, this method only removes invalid
	 * inputs for the date JTextFields by setting their text to an 
	 * empty string.
	 * 
	 * @param delete - String that is empty
	 * @param textfield - Integer that marks the invalid textfield.
	 *****************************************************************/
	private void deleteInvalid(String delete, int textfield){

		if(textfield == 2){
			rentedOnTxt.setText(delete);
		}
		else if(textfield == 3){
			dueBackOnTxt.setText(delete);
		}

	}

	/******************************************************************
	 * Returns the boolean value for when it is safe to enter data into
	 * the DVD class
	 * 
	 * @param - none
	 * @return CloseStatus - Boolean that's used to determine user
	 *                     data safety.
	 *****************************************************************/
	public boolean getCloseStatus(){

		return closeStatus;
	}

	/*****************************************************************
	 * Method that listens for a component defined action.
	 * 
	 * @param e - ActionEvent An event spawned by a user interacting 
	 *          with a component.
	 * @return - none
	 *****************************************************************/
	public void actionPerformed(ActionEvent e){

		/*
		 * Cancel button is clicked
		 */
		if(e.getSource() == cancelButton){

			this.dispose();
			closeStatus = false;

		}
		/*
		 * Ok button is clicked
		 */
		if(e.getSource() == okButton){

			closeStatus = true;
			retrieveData();
			this.dispose();
		}

	}

}