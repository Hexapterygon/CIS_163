package package1;

import java.io.Serializable;
import java.util.Stack;

/**********************************************************************
 * The MyLinkedList class implements a simple linked list.
 * 
 * @author Nathan Anderle
 *********************************************************************/
public class MyLinkedList implements ILinkedList, Serializable {

	/*
	 * The first element in the linked list.
	 */
	private Node front;
	
	/*
	 * The last element in the linked list. 
	 */
	private Node rear;
	
	/*
	 * The number of items in the linked list.
	 */
	private int size;
	
	/*
	 * A stack of nodes for reversing the list
	 */
	private Stack pile;

	/******************************************************************
	 * Initializes a newly constructed MyLinkedList object.
	 *****************************************************************/
	public MyLinkedList() {
		front = null;
		rear = null;
		size = 0;
		pile = new Stack();
	}

	/******************************************************************
	 * Adds the specified element to the end of the list.
	 * 
	 * @param element the element to add to the list
	 * 
	 * @throws NullPointerException if the specified element is null
	 *****************************************************************/
	@Override
	public void add(String element){

		/*
		 * Throws an error if the user attempts to enter an empty value
		 */
		if( element.equals(null)){

			throw new NullPointerException();
		}

		/*
		 * Add an element when there is no pre-existing list.
		 */
		if (front == null) {
			rear = front = new Node(element);
			pile.push(front);
			size++;
		}
		
		/*
		 * Add an element to the end of the list
		 */
		else {
			
			rear.setNext (new Node(element));
			rear = rear.getNext();
			pile.push(rear);
			size++;
		}
	}

	/******************************************************************
	 * Inserts the given element at the specified position in the list.
	 * 
	 * @param index index at which the specified element is to be 
	 *              inserted
	 *              
	 * @param element element to be inserted
	 * 
	 * @throws IndexOutOfBoundsException if the index is out of range 
	 *         (index < 0 || index > size())
	 *         
	 * @throws NullPointerException if the specified element is null
	 *****************************************************************/
	@Override
	public void add(int index, String element){

		/*
		 * Throws exception if user attempts to enter element before
		 * or after list.
		 */
		if(index < 0 || index > size()){

			throw new IndexOutOfBoundsException();
		}
		
		/*
		 * Throws an exception if the user attempts to enter an empty
		 * element
		 */
		if( element.equals(null)){

			throw new NullPointerException();
		}

		/*
		 * adds an element at the top of an empty list
		 */
		if(index == 0 && size() == 0){
			
			Node temp = new Node(element);
			temp.setNext(front);
			
			rear = front = temp;
			pile.push(rear);
			size++;
		}
		/*
		 * Adds an element to the top of the list
		 */
		else if( index == 0){

			Node temp = new Node(element);
			temp.setNext(front);

			/*
			 * If there is an element in the list when an item is added
			 * at the top, set rear to the "old" front
			 */
			if(size() != 0){

				rear = front;

			}

			/*
			 * Set the top pointer to the new item
			 */
			front = temp;
			pile.push(front);
			size++;
		}
		
		/*
		 * If the item is to be added to the end of the list, create
		 * a "current" node at the top and cycle it down to the bottom
		 */
		else if (index == size){

			Node curr = front;
			int count = 0;
			while (count < size-1) {

				curr = curr.getNext();
				count++;

			}

			/*
			 * Set the rear pointer to the "current" node that was 
			 * cycled to the bottom. Create a temporary node
			 * with user specified input, and set it as the next item
			 * after rear. Set rear to the new item.
			 */
			rear = curr;
			Node temp = new Node(element);
			temp.setData(element);
			rear.setNext(temp);
			rear = temp;
			pile.push(rear);
			size++;


		}

		/*
		 * If an item is to be added to the middle of the list, create
		 * a "current" node that starts at the top of the list and
		 * cycle it to the proper index.
		 */
		else{

			Node curr = front;
			int count = 0;
			while (count < index-1) {
				curr = curr.getNext();
				count++;
			}

			/*
			 * Create a temporary node with user spcified data
			 */
			Node temp = new Node(element);
			temp.setData(element);

			/*
			 * Set the next node for the temporary node to the next
			 * node for the current
			 */
			
			temp.setNext(curr.getNext());
			
			/*
			 * Insert the temporary node after the current node.
			 */
			curr.setNext(temp);
			pile.push(curr);
			size++;

		}
	}

	/******************************************************************
	 * Removes the element at the specified position in this list.
	 * 
	 * @param index the index of the element to be removed
	 * 
	 * @return the element previously at the specified position
	 * 
	 * @throws IndexOutOfBoundsException if the index is out of range 
	 *         (index < 0 || index >= size())
	 *****************************************************************/
	@Override
	public String remove(int index){


		/*
		 * Throws exception if user attempts to delete element before
		 * or after list.
		 */
		if(index < 0 || index > size()){

			throw new IndexOutOfBoundsException();
		}

		/*
		 * Empty list; return null
		 */
		if(front == null){

			return null;
		}

		/*
		 * Delete a one item list
		 */
		if(front == rear && index == 0){

			String remove = front.getData();
			front = rear = null;
			size--;
			return remove;
		}

		/*
		 * Delete the first item in the list
		 */
		if( index == 0){

			String remove = front.getData();
			front = front.getNext();
			size--;
			return remove;
		}

		/*
		 * Delete item in the middle of the list using similar logic 
		 * as adding to the list
		 */
		else{
			Node curr = front;
			int count = 0;
			while (count < index-1) {
				curr = curr.getNext();
				count++;
			}
			String remove = curr.getNext().getData();
			
			/*
			 * Circumvent the item to be deleted and point to the far
			 * item from the current item.
			 */
			curr.setNext(curr.getNext().getNext());
			size--;

			/*
			 * Sets the new rear pointer if the next item is the end
			 * of the list.
			 */
			if (curr.getNext() == null)
				rear = curr;

			return remove;
		}
	}
	
	/******************************************************************
	 * Returns the element at the specified position in this list.
	 * Return null if the index is out of range 
	 * (index < 0 || index >= size())
	 * 
	 * @param index index of the element to return
	 * 
	 * @return the element at the specified position in this list
	 * 
	 * @throws IndexOutOfBoundsException if the index is out of range 
	 *         (index < 0 || index >= size())
	 *****************************************************************/
	@Override
	public String get(int index) {

		if(index < 0 || index >= size()){

			throw new IndexOutOfBoundsException();
		}

		/*
		 * Cycles through the list to find the element at the specific
		 * index
		 */
		else{
			Node curr = front;
			int count = 0;
			while (count < index) {
				curr = curr.getNext();
				count++;
			}
			return curr.getData();
		}
	}

	/******************************************************************
	 * Returns true if this list contains no elements.
	 * 
	 * @return true if this list contains no elements and false 
	 *              otherwise
	 *****************************************************************/
	@Override
	public boolean isEmpty() {
		
		return size == 0;
	}

	/******************************************************************
	 * Returns the number of elements in this list.
	 * 
	 * @return the number of elements in this list
	 *****************************************************************/
	@Override
	public int size() {
		
		return size;
	}

	/******************************************************************
	 * Removes all of the elements from this list. The list will be 
	 * empty after this call returns.
	 *****************************************************************/
	@Override
	public void clear() {
		
		front = null;
		rear = null;
		size = 0;
	}

	/******************************************************************
	 * Displays the files in the linked list by cycling through the
	 * list with a while loop
	 * 
	 * @param current The number of the current line
	 * @param low lower bound in the display loop
	 * @param high upper bound in the display loop
	 *****************************************************************/
	public void display(int current, int low, int high){

		System.out.println("Start of File");
		
		/*
		 * Starts the line numbers at the specified index 
		 */
		int linenum = low + 1;

	
		Node temp = front;
		
		for(int i = 0; i < low; i++){
			
			temp = temp.getNext();
			
		}
		/*
		 * Cycle through the linked list using a temporary node and a 
		 * while loop
		 */
		while (low <= high && temp != null) {

			/*
			 * If the line number is equal to the current line, print
			 * an asterisk along with the data in the element.
			 */
			if(linenum == current){

				System.out.println("\t*"+linenum+":" + temp.getData());
				linenum++;
				low++;
				temp = temp.getNext();
			}
			
			/*
			 * Print the element at the given line number
			 */
			else{
				System.out.println("\t "+linenum+":" + temp.getData());
				linenum++;
				low++;
				temp = temp.getNext();
			}
		}
	}
	
	/******************************************************************
	 * Displays the link list backwards by popping nodes out of a stack
	 * 
	 * 
	 *****************************************************************/
	public void displayReverse(){
		
		int i = 0;
		
		while(i < pile.size()){
			
			Node temp = (Node)pile.pop();
			
			System.out.println(temp.getData());
			
		}
	}
}
