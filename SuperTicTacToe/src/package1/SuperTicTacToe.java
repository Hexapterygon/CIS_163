package package1;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.*;






/**********************************************************************
 * Driver class that creates the JFrame upon which the SuperTicTacToe
 * game is played and packs the GUI panel onto the JFrame. 
 * The SuperTicTacToe game is an interactive GUI based 
 * tictactoe game that allows users a large degree of freedom in
 * deciding the rules and mechanics of gameplay.
 * 
 * @author nathan
 * @version 1.0
 * @param none
 * @return none
 *********************************************************************/
public class SuperTicTacToe {

	/******************************************************************
	 * Main method that drives the program. Creates a JFrame,
	 * instantiates a new object of the SuperTicTacToePanel class,
	 * and packs the object in the JFrame.
	 * 
	 * @param none
	 * @return none
	 *****************************************************************/
	public static void main (String args[]) {
		
		JFrame frame = new JFrame ("SuperTicTacToe");
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
				
		SuperTicTacToePanel panel = new SuperTicTacToePanel();
		
		frame.getContentPane().add(panel);
		
		frame.pack();
		frame.setVisible(true);
				
	}
	

	
	
}