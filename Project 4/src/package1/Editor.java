package package1;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Scanner;
import java.util.Stack;

/**********************************************************************
 *Creates a simple text editor with basic functionalities
 *
 * @author Nathan Anderle 
 *
 *********************************************************************/
public class Editor implements IEditor {

	/*
	 * Linked list that stores every line in the editor as a node
	 */
	private MyLinkedList list;
	
	/*
	 * Essential value used in determining user location and
	 * operational extent
	 */
	private int current;
	
	/*
	 * Boolean that determines when the program is to exit.
	 */
	private boolean leave;

	
	/******************************************************************
	 * Constructor for the Editor class. Does basic instantiation
	 *
	 *****************************************************************/
	public Editor(){

		list = new MyLinkedList();
		current = 0;
		leave = false;
	}

	@Override
	/******************************************************************
	* Processes the specified editor command.
    * 
    *@param command the command to process
    * 
    *@throws EditorException if the command is invalid or could 
    *         result in an error situation
	 *****************************************************************/
	public void processCommand(String command) throws EditorException {

		/*
		 * Displays an error if no command is entered
		 */
		if (command.isEmpty()){
			
			try {
				throw new EditorException("Enter a valid command");
			} catch (EditorException e) {

				System.out.println(e.getMessage());
			}
		}
	 	
		/*
		 * If a command is entered, begin the switch statement based on
		 * the first character in the command string. For valid
		 * commands, the first character is the command indicator
		 */
		else{
			switch(command.charAt(0)){

			/*
			 * Insert after line
			 */
			case 'i':

				commandI(command);
				break;

			/*
			 * Insert at end of editor
			 */
			case 'e':

				commandE(command);
				break;
				
			/*
			 * Insert before line
			 */
			case 'b':

				commandB(command);
				break;
				
			/*
			 * Move the current line upwards
			 */
			case 'u':

				commandU(command);
				break;

			/*
			 * Move the current line downwards
			 */
			case 'm':

				commandM(command);
				break;

			/*
			 * Remove the specified lines
			 */
			case 'r':

				/*
				 * Reverse list
				 */
				if(command.startsWith("rev")){
					
					
					reverse();
				}
				
				else
				commandR(command);
				break;

			/*
			 * Display the specified lines
			 */
			case 'd':

				commandD(command);
				break;

			/*
			 * Display help page
			 */
			case 'h':

				help();
				break;

			/*
			 * Completely clear the editor
			 */
			case 'c':

				/*
				 * cut command
				 */
				if(command.startsWith("cut")){
						
					cut();
				}
				
				else
				commandC(command);
				break;

			/*
			 * Save the contents of the editor
			 */
			case 's':

				save(command);
				break;
				
			/*
			 * Load the contents of a previously saved editor
			 */
			case 'l':

				commandL(command);
				break;

			/*
			 * Exit the program
			 */
			case 'x':

				commandX(command);
				break;
				
			case 'p':
				
				/*
				 * paste command
				 */
				if(command.startsWith("pas")){
						
					paste();
				}
				
				else{
					
					
				}
				break;
				
			}		
			
		}
	}

	@Override
	/******************************************************************
	 * Returns the number of lines in the editor buffer/file.
	 * 
	 *
	 *****************************************************************/
	public int nbrLines() {
		
		return list.size();
	}

	@Override
	/******************************************************************
	 * Returns the line in the editor buffer at the given line number.
	 *
	 * @param lineNbr number of line to return
     * @return line at the given line number
     * @throws EditorException if the line number is not valid 
     *         (lineNbr <= 0 or lineNbr > number of line in the buffer
	 *****************************************************************/
	public String getLine(int lineNbr) throws EditorException {

		/*
		 * -1 added to correct for the actual start of the list at zero
		 */
		return list.get(lineNbr-1);
	}

	@Override
	/******************************************************************
	* Returns the line at the current line position.
    * 
    * @return the current line or null if there are no lines 
    *         (i.e., buffer is empty)
    *
	*****************************************************************/
	public String getCurrentLine() {

		return list.get(current-1);

	}

	@Override
	/******************************************************************
	*Returns the current line number.
    * 
    * @return the current line number
	******************************************************************/
	public int getCurrentLineNbr() {
		
		return current;
	}

	/******************************************************************
	 * Method used to set the number of the last line specifically
	 * 
	 * @param num Number used to set the current line to the last
	 *            position
	 *****************************************************************/
	private void setLastLineNbr(int num){		

		current = num;

	}

	/******************************************************************
	 *Sets the current line number by adding num to current. This
	 *method essentially increments the current position
	 *
	 * @param num Number used to increment the current position in the
	 *            text editor.
	 *
	 *****************************************************************/
	private void setCurrentLineNbr(int num){		

		current += num;

	}

	/******************************************************************
	 * The insert command. Inserts the user entered line after the 
	 * current position.
	 * 
	 * @param command User entered command and input
	 *
	 *****************************************************************/
	private void commandI(String command){

		/*
		 * Splits the command and the input and trims extra spaces
		 */
		String stuff[] = command.split("i", 2);

		String inputa = stuff[1].trim();

		list.add(getCurrentLineNbr(), inputa);

		setCurrentLineNbr(1);

	}

	/******************************************************************
	 *The add-to-end command. Inserts the user entered line at the 
	 *end of the text editor buffer.
	 *
	 * @param command User entered command and input
	 *
	 *****************************************************************/
	private void commandE(String command){

		String stuff5[] = command.split("e", 2);

		String inpute = stuff5[1].trim();

		list.add(inpute);
		
		/*
		 *Sets the final line to the size of the linked list 
		 */
		setLastLineNbr(list.size());

	}

	/******************************************************************
	 * The insert before command. Inserts the user entered line before
	 * the current position in the text editor
	 * 
	 * @param command User entered command and input
	 *****************************************************************/
	private void commandB(String command){

		String stuff2[] = command.split("b", 2);

		String inputb  = stuff2[1].trim();

		/*
		 * If the list is empty, the insert before command behaves like
		 * a regular insert after.
		 */
		if(list.size() == 0){

			list.add(getCurrentLineNbr(), inputb);
			setCurrentLineNbr(1);
		}
		/*
		 * If the list is not empty, the line is inserted before the
		 * current position
		 */
		else{
			list.add(getCurrentLineNbr() -1, inputb);
			//setCurrentLineNbrUp(1);
		}	
	}

	/******************************************************************
	 * The move up command. Moves the user's current position up by a
	 * specified amount.
	 * 
	 * @param command User specified command and movement distance
	 *
	 *****************************************************************/
	private void commandU(String command){

		int inputg;
		String stuff7[] = command.split("u", 2);

		try{

			/*
			 * If the user does not specify a distance to move, the
			 * distance defaults to 1
			 */
			if(stuff7[1].equals("")){
				inputg  = 1;
			}

			/*
			 * If the user does enter values after the command, attempt
			 * to parse them into integers
			 */
			else
				inputg  = Integer.parseInt(stuff7[1].trim());

			moveUp(inputg);
		}
		
		/*
		 * Non-numeric inputs generate and invalid command error
		 */
		catch(NumberFormatException e){

			System.out.println("Invalid command");
		}	
	}

	/******************************************************************
	 * Performs the movements of the U command
	 * 
	 * @param dist Distance that the current position will be moved
	 *             upwards
	 *****************************************************************/
	private void moveUp(int dist){

		/*
		 * Moves the current position by subtracting the distance from
		 * the current position. Only valid when there is a list and
		 * a place to move
		 */
		if( current > 1 && (current-dist) >= 1){

			current = current-dist;

		}
		else if(current >= 0){

			try {
				throw new EditorException("Invalid move number");
			} catch (EditorException e) {

				System.out.println(e.getMessage());

			}
		}
	}

	/******************************************************************
	 * The move down command. Moves the current position down by a 
	 * specified distance.
	 * 
	 * @param command User specified command and movement distance
	 *
	 *****************************************************************/
	private void commandM(String command){

		int inputf;
		String stuff6[] = command.split("m", 2);

		try{

			if(stuff6[1].equals("")){
				inputf  = 1;
			}

			else
				inputf  = Integer.parseInt(stuff6[1].trim());

			moveDown(inputf);
		}
		catch(NumberFormatException e){

			System.out.println("Invalid command");

		}
	}

	/******************************************************************
	 * Performs the movements of the move down command.
	 * 
	 * @param dist Distance that the current position will shift down
	 *
	 *****************************************************************/
	private void moveDown(int dist){

		/*
		 * Moves the current position downwards only if the current
		 * position and the current position plus the distance to move
		 * are less than the size of the list.
		 */
		if(current < list.size() && (dist + current)<= list.size()){

			current = current + dist;
		}

		else{

			/*
			 * Gives an error message if the user tries to move past
			 * the end of the list.
			 */
			try {
				throw new EditorException("Invalid move number");
			} catch (EditorException e) {

				System.out.println(e.getMessage());

			}	
		}
	}

	/******************************************************************
	 * The remove command. Deletes a specified number of lines after 
	 * and including the current position.
	 * 
	 * @param command User specified command and removal range.
	 *
	 *****************************************************************/
	private void commandR(String command){

		int inputh;
		String stuff8[] = command.split("r", 2);

		try{

			if(stuff8[1].equals("")){
				inputh  = 1;
			}
			else

				inputh  = Integer.parseInt(stuff8[1].trim());

			if(list.size() > 0)
				remove(inputh);
		}
		catch(NumberFormatException e){

			System.out.println("Invalid command");
			
		}
	}

	/******************************************************************
	 * Performs the remove operations of the remove command.
	 * 
	 * @param dist Distance from the current position that will see
	 *             deletion
	 *
	 *****************************************************************/
	private void remove(int dist){

		/*
		 * Runs only if the distance is less than or equal to the
		 * number of lines including and after the current position
		 */
		if(dist <= (list.size()-current + 1)){
			
			/*
			 * Runs the remove operation so long as the full distance
			 * from the current position has not been traversed.
			 */
			for(int i = 0; i < dist; i++){
				
				/*
				 * If the current position is at the top of the list,
				 * remove the line at the current position (correcting
				 * for the offset of the Linked List index 0)
				 */
				if (current == 1){

					list.remove(current-1);

					/*
					 * If the item deleted at the top of the list was 
					 * the last item, reset the current position to 0
					 * by subtracting one from the current position
					 */
					if(list.size() == 0){

						setCurrentLineNbr(-1);
					}
					/*
					 * If there are more items in the list, keep the
					 * current position the same. This makes the next
					 * element in the list the new current position
					 */
					else{

						setCurrentLineNbr(0);
					}
				}

				/*
				 * If the current position is somewhere in the middle
				 * of the list, remove the element at the current 
				 * position and let subsequent elements "file upwards"
				 * while keeping the position the same.
				 */
				else if(current < list.size()){	

					list.remove(current-1);
					setCurrentLineNbr(0);
				}

				/*
				 * If the current position is at the bottom of the list
				 * remove the element at the current position and move
				 * the current position to the previous element.
				 */
				else if(current == list.size()){

					list.remove(current-1);
					setCurrentLineNbr(-1);
				}
			}
		}
		
		/*
		 * Throw an error if something is invalid. 
		 */
		else{

			try {
				throw new EditorException("Invalid delete");
			} catch (EditorException e) {

				System.out.println(e.getMessage());
			}	
		}
	}

	/******************************************************************
	 * The display command. Displays the lines in the text editor
	 * between and including the two user specified positions.
	 * 
	 * @param command User specified command and bounds inputs
	 *
	 *****************************************************************/
	private void commandD(String command){

		int inputi;
		int inputj;
		/*
		 * Splits the command into three parts: command, lower bound,
		 * and upper bound
		 */
		String stuff9[] = command.split(" ", 3);

		try{

			/*
			 * If no bounds are specified, bounds default to the 
			 * beginning and and end of the text file.
			 */
			if(stuff9.length == 1 && stuff9[0].equals("d")){
				inputi = 0;
				inputj = list.size();

				/*
				 * Error handling if-statement that checks to make
				 * sure all of the values are valid.
				 */
				if(inputi >= 0 && inputj <= list.size() && inputi 
						<= inputj){
					
					list.display(current, inputi, inputj);
				}
			}

			/*
			 * If the user specified a command and two inputs, attempt
			 * to parse the second two inputs as integers.
			 */
			else if (stuff9.length == 3){
				inputi  = Integer.parseInt(stuff9[1].trim());
				inputj  = Integer.parseInt(stuff9[2].trim());

				if(inputi >= 0 && inputj <= list.size() && inputi
						<= inputj){

					/*
					 * Correct for 0 index offesets  using -1
					 */
					list.display(current, inputi-1, inputj-1);
				}
				
				/*
				 *Throw an error if the input structure is wrong 
				 */
				else{

					try {
						throw new EditorException("Invalid display "
								+ "number");
						
					} catch (EditorException e) {

						System.out.println(e.getMessage());
					}

				}
			}
		}
		/*
		 * Catch error for proper structure, but incorrect inputs
		 */
		catch(NumberFormatException e){
			
			System.out.println("Invalid command");

		}		
	}

	/******************************************************************
	 * The clear command. Completely clears the text editor and resets
	 * the current position.
	 * 
	 * @param User specified command.
	 *
	 *****************************************************************/
	private void commandC(String command){

		int done = 0;

		/*
		 *Prompt user with a scanner and while loop before clearing 
		 *the editor.
		 */
		while( done != 1){

			String input;
			System.out.print("Clear the editor? (y/n) ");
			Scanner scan = new Scanner(System.in);
			input = scan.next();
			input = input.toLowerCase();

			if(input.toLowerCase().equals("y")){
				list.clear();
				current = 0;
			}

			if(input.toLowerCase().equals("n") || 
					input.toLowerCase().equals("y") ){

				done = 1;
			}
		}
	}

	/******************************************************************
	 * Load command. Loads a previous text editor instance from a 
	 * specified file.
	 * 
	 * @param command User specified command and file
	 *
	 *****************************************************************/
	private void commandL(String command){

		String stuff4[] = command.split("l", 2);

		String inputd = stuff4[1].trim();

		int finish = 0;

		/*
		 * Prompt the user before loading a new text editor using a 
		 * while loop and scanner.
		 */
		while( finish != 1){
			String input;
			System.out.print("Load this file? (y/n) ");
			Scanner scan = new Scanner(System.in);
			input = scan.next();
			input = input.toLowerCase();

			if(input.toLowerCase().equals("y")){

				/*
				 * Clear the current instance of the list and reset the
				 * current position prior to loading the saved instance
				 */
				list = null;
				current = 1;
				load(inputd);
			}

			if(input.toLowerCase().equals("n") || 
					input.toLowerCase().equals("y") ){

				finish = 1;
			}
		}
	}

	/******************************************************************
	 * Exit command. Exits the program.
	 * 
	 * @param command User specified command
	 *
	 *****************************************************************/
	private void commandX(String command){


		int exit = 0;

		/*
		 * Use a while loop to prompt the user prior to exiting.
		 */
		while( exit != 1){
			
			String input;
			System.out.print("Exit the program? (y/n) ");
			Scanner scan = new Scanner(System.in);
			input = scan.next();
			input = input.toLowerCase();

			if(input.toLowerCase().equals("y")){

				leave = true;

				exit();
				exit = 1;
			}

			else if(input.toLowerCase().equals("n")){

				exit = 1;
			}
		}
	}

	/******************************************************************
	 * The help command. Displays the the various commands available
	 * in the text editor for the user's use.
	 *
	 *****************************************************************/
	private void help(){

		System.out.println("\nHELP:\nCommands can are typed with the "
				+ "syntax 'command char' <sentence> or <number>.\nFor "
				+ "example, an insert command is input as follows: i "
				+ "This will be inserted.\n\n "
				+ "COMMAND LIST:\n\t");

		System.out.println("b <sentence> \t--Insert sentence "
				+ "before the current line; "
				+ "\n\t\t  make the inserted line the current line."
				+ "\n\ni <sentence> \t--Insert sentence after the "
				+ "current line;\n\t\t  make the inserted line "
				+ "the current line.\n");

		System.out.println("m <n> \t\t--Move current line indicator "
				+ "down n positions.\n\nu <n> \t\t--Move current line "
				+ "indicator up n positions.\n\nr <n> \t\t--Remove n "
				+ "lines starting at the current "
				+ "line\n\nd n1 n2 \t--Display lines "
				+ "from line n1 to line n2 (inclusive)\n");

		System.out.println("c\t\t--Clear (remove) all lines "
				+ "in the buffer/file\n\ns <filename> \t--Save the "
				+ "contents to the specified file.\n\nl <filename> "
				+ "\t--Load the contents of the specified file into "
				+ "\n\t\t  editor buffer replacing the current "
				+ "contents\n");

		System.out.println("h\t\t--Display a help page of editor "
				+ "commands\n\nx \t\t--Exit the editor\nrev \t\t--"
				+ "Reverses the lines in the buffer/file.\n\ncut n1 "
				+ "n2 \t--Cut lines from the file from line n1 to "
				+ "line n2 (inclusive)\n\npas \t\t--Paste the "
				+ "clipboard contents before the current line "
				+ "position\n\n");

	}

	/******************************************************************
	 * Returns a boolean value that is used to tell the main method
	 * when to stop the program and exit
	 * 
	 * @return leave Boolean value that determines exit state
	 *
	 *****************************************************************/
	public boolean exit(){

		return leave;

	}

	/******************************************************************
	 * The save command. Saves the current instance of the text editor
	 * to a serializable.
	 * 
	 * @param command User specified command and filepath
	 *
	 *****************************************************************/
	private void save(String command){

		String stuff3[] = command.split("s", 2);

		String inputc = stuff3[1].trim();

		try
		{
			FileOutputStream fileOut =
					new FileOutputStream(inputc);
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(list);
			out.close();
			fileOut.close();
			System.out.println("Serialized data is saved in " + inputc);
		}

		catch(IOException i)
		{
			System.out.println("An error has occurred");
		}
	}

	/******************************************************************
	 * Operates the load command. Loads a previously saved instance of
	 * the text editor back into program.
	 * 
	 * @param file Filepath to the saved file to be loaded.
	 *
	 *****************************************************************/
	private void load(String file){

		try
		{
			FileInputStream fileIn = new FileInputStream(file);
			ObjectInputStream in = new ObjectInputStream(fileIn);
			list = (MyLinkedList) in.readObject();
			in.close();
			fileIn.close();
		}catch(IOException i)
		{
			System.out.println("An error has occurred");
			list = new MyLinkedList();
			
		}catch(ClassNotFoundException c)
		{
			System.out.println("MyLinkedList not found");
			c.printStackTrace();
			return;
		}
	}
	
	/******************************************************************
	 * Reverse command. Reverses the order of the linked list and 
	 * displays the content backwards
	 *
	 *****************************************************************/
	private void reverse() {
			
	System.out.println("Reverse logic");
		
	}

	/******************************************************************
	 * Cut command. Cuts the elements between an upper and lower bound
	 *
	 *****************************************************************/
	private void cut() {
		
		System.out.println("cut logic");
		
	}
	
	/******************************************************************
	 * Paste command. Pastes the cut lines into the editor at the
	 * specified position.
	 *
	 *****************************************************************/
	private void paste(){
		
		System.out.println("paste logic");
	}
}