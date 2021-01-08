/*
 * Contains all of the test methods for the Book class
 * Author: Sukhraj Garcha
 * Last updated: 18/11/2020
 */

package eStoreSearch;

import org.junit.Test;
import static org.junit.Assert.*;

public class BookTest {

    // private Book testBook = null;

    // try {
    //     testBook = new Book("000123", "A Good Book", 2010, 19.99, "N/A", "N/A"); //book to use in tests

    // }catch (Exception e) {
    //     //do nothing
    // }
    

    @Test public void testGetProductID() {

        Book testBook = null;

        try {
            testBook = new Book("000123", "A Good Book", 2010, 19.99, "N/A", "N/A"); //book to use in tests

        }catch (Exception e) {
            //do nothing
        }

        String testID = testBook.getProductID();
        assertEquals("000123", testID);
    }

    @Test public void testSetAuthors() {

        Book testBook = null;

        try {
            testBook = new Book("000123", "A Good Book", 2010, 19.99, "N/A", "N/A"); //book to use in tests

        }catch (Exception e) {
            //do nothing
        }

        testBook.setAuthors("test authors");
        assertEquals("test authors", testBook.getAuthors());
    }
    
    @Test public void testToString() {

        Book testBook = null;

        try {
            testBook = new Book("000123", "A Good Book", 2010, 19.99, "N/A", "N/A"); //book to use in tests

        }catch (Exception e) {
            //do nothing
        }
        
        String result = testBook.toString();
        String expectedResult = "Product ID: 000123" + "\nDescription: A Good Book"  + "\nYear: 2010" + "\nPrice: 19.99"  +
              "\nAuthor(s): N/A"  + "\nPublisher: N/A";
        assertEquals(expectedResult, result);
    }

    @Test public void testEquals() {

        Book testBook2 = null;
        Book testBook = null;
        try {

            testBook = new Book("000123", "A Good Book", 2010, 19.99, "N/A", "N/A");
            testBook2 = new Book("000123", "A Good Book", 2010, 19.99, "N/A", "N/A"); 

            assertTrue(testBook2.equals(testBook));
            

        }catch (Exception e) {
            //do nothing
        }   
    }

    @Test public void testInheritance() {

        Book testBook = null;

        try {
            testBook = new Book("000123", "A Good Book", 2010, 19.99, "N/A", "N/A"); //book to use in tests

        }catch (Exception e) {
            //do nothing
        }

        assertEquals("000123", testBook.getProductID());
    }

    @Test public void testMatchID() {

        Book testBook = null;

        try {
            testBook = new Book("000123", "A Good Book", 2010, 19.99, "N/A", "N/A"); //book to use in tests

        }catch (Exception e) {
            //do nothing
        }

        boolean result; 

        //same ID
        result = testBook.matchID("000123");
        assertTrue(result);

        //different ID
        result = testBook.matchID("012451");
        assertFalse(result);
    }

    @Test public void testMatchYear() {

        Book testBook = null;

        try {
            testBook = new Book("000123", "A Good Book", 2010, 19.99, "N/A", "N/A"); //book to use in tests

        }catch (Exception e) {
            //do nothing
        }

        //year of test book = 2010
        boolean result;

        //exactly given year
        result = testBook.matchYear("2010");
        assertTrue(result);

        //year and above
        result = testBook.matchYear("2010-");
        assertTrue(result);

        //year and below
        result = testBook.matchYear("-2010");
        assertTrue(result);

        //within range
        result = testBook.matchYear("2009-2015");
        assertTrue(result);

        //not exact year
        result = testBook.matchYear("2015");
        assertFalse(result);

        //not ewithin range
        result = testBook.matchYear("2012-2015");
        assertFalse(result);
    }

    @Test public void testCheckAttributes() {

        Book testBook = null;

        try {
            testBook = new Book("000123", "A Good Book", 2010, 19.99, "N/A", "N/A"); //book to use in tests

        }catch (Exception e) {
            //do nothing
        }

        boolean result;
        
        //everything valid
        result = Book.checkAttributes("000123", "Test Description", 2010);
        assertTrue(result);

        //ID not 6 chars
        result = Book.checkAttributes("0012", "Test Description", 2010);
        assertFalse(result);

        //ID isn't only digits
        result = Book.checkAttributes("0012a0", "Test Description", 2010);
        assertFalse(result);

        //year not in correct range
        result = Book.checkAttributes("001223", "Test Description", 999);
        assertFalse(result);

        //empty description
        result = Book.checkAttributes("001223", "Test Description", 999);
        assertFalse(result);
    }
}