package package1;

import java.io.IOException;
import java.util.Scanner;

/**********************************************************************
 * EditorCLI class provides a text-based user interface to the editor
 * supporting a read-process-print loop.
 * 
 * @author Nathan Anderle
 *
 *********************************************************************/
public class EditorCLI {
	
  /*******************************************************************
   *Main method that runs the program.
   *
   *@param args An array of Strings that can be fed into the program
   *            from outside upon launch.
   *******************************************************************/
	public static void main(String[] args) {

		/*
		 * New instance of the text editor
		 */
		Editor edit = new Editor();

		/*
		 * Brief welcom message
		 */
		System.out.println("Welcome!\nType h for help\n\n");

		/*
		 * Loop that runs the program until the user decides to exit
		 */
		try{	
			while(edit.exit() != true){
				String input;
				System.out.print("ted> ");
				Scanner scan = new Scanner(System.in);
				input = scan.nextLine();

				edit.processCommand(input);
			}
			
			System.exit(0);
		}
		
		/*
		 * Catches nasty critical errors and gracefully exits.
		 */
		catch(EditorException e){
			
			System.out.println("A critical error has occurred");
			System.exit(0);

		}
	}
}
