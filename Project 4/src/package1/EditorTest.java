package package1;

import static org.junit.Assert.*;

import org.junit.Test;

/*********************************************************************
 * EditorTest class contains unit tests for Editor class.
 * 
 * @author Nathan Anderle
 *
 * 
 * 
 *
 *********************************************************************/
public class EditorTest {

	@Test
	public void testInsertCommand() throws EditorException {
		Editor ed = new Editor();
		ed.processCommand("i Java is an OO language.");
		ed.processCommand("i Ruby is another OO language.");
		assertEquals("Ruby is another OO language.", 
				ed.getCurrentLine());
		assertEquals("Java is an OO language.", ed.getLine(1));
		assertEquals(2, ed.nbrLines());
	}
	
	@Test
	public void testInsertAfterCommand() throws EditorException {
		Editor ed = new Editor();
		ed.processCommand("b Java is an OO language.");
		ed.processCommand("b Ruby is another OO language.");
		assertEquals("Ruby is another OO language.", 
				ed.getCurrentLine());
		assertEquals("Ruby is another OO language.", ed.getLine(1));
		assertEquals(2, ed.nbrLines());
	}
	
	@Test
	public void testInsertBeforeAfterCommand() throws EditorException {
		Editor ed = new Editor();
		ed.processCommand("i Java is an OO language.");
		ed.processCommand("b Ruby is another OO language.");
		ed.processCommand("i Random test case.");
		assertEquals("Random test case.", ed.getCurrentLine());
		assertEquals("Random test case.", ed.getLine(2));
		assertEquals("Java is an OO language.", ed.getLine(3));
		assertEquals("Ruby is another OO language.", ed.getLine(1));
		assertEquals(3, ed.nbrLines());
	}
	
	@Test
	public void testInsertEnd() throws EditorException {
		Editor ed = new Editor();
		ed.processCommand("b Java is an OO language.");
		ed.processCommand("b Ruby is another OO language.");
		ed.processCommand("e Add at end");
		assertEquals("Add at end", ed.getCurrentLine());
		assertEquals("Ruby is another OO language.", ed.getLine(1));
		assertEquals(3, ed.nbrLines());
	}
	
	@Test
	public void testInsertEndBefore() throws EditorException {
		Editor ed = new Editor();
		ed.processCommand("e Java is an OO language.");
		ed.processCommand("b Ruby is another OO language.");
		ed.processCommand("i Add at end");
		assertEquals("Add at end", ed.getCurrentLine());
		assertEquals("Ruby is another OO language.", ed.getLine(1));
		assertEquals("Java is an OO language.", ed.getLine(3));
		assertEquals(3, ed.nbrLines());
	}

	@Test 
	public void testRemoveCommand()throws EditorException {
	   Editor ed = new Editor();
	   ed.processCommand("i Java is cool.");
	   ed.processCommand("i Python is cooler.");
	   ed.processCommand("i Ruby is hot.");
	   ed.processCommand("i COBOL is old.");
	   assertEquals(4, ed.nbrLines());
	   ed.processCommand("u 2");
	   ed.processCommand("r 2");
	   assertEquals(2, ed.nbrLines());
	   assertEquals("COBOL is old.", ed.getCurrentLine());
	}
	
	@Test 
	public void testMoveUp()throws EditorException {
	   Editor ed = new Editor();
	   ed.processCommand("i Java is cool.");
	   ed.processCommand("i Python is cooler.");
	   ed.processCommand("i Ruby is hot.");
	   ed.processCommand("i COBOL is old.");
	   assertEquals(4, ed.nbrLines());
	   ed.processCommand("u 3");
	   assertEquals("Java is cool.", ed.getCurrentLine());
	}
	
	@Test 
	public void testMoveDown()throws EditorException {
	   Editor ed = new Editor();
	   ed.processCommand("b Java is cool.");
	   ed.processCommand("b Python is cooler.");
	   ed.processCommand("b Ruby is hot.");
	   ed.processCommand("b COBOL is old.");
	   assertEquals(4, ed.nbrLines());
	   ed.processCommand("m 3");
	   assertEquals("Java is cool.", ed.getCurrentLine());
	}

}

