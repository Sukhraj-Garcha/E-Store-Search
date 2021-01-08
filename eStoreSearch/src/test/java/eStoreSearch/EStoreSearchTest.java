/*
 * Contains all of the test methods for the EStoreSearch class
 * Author: Sukhraj Garcha
 * Last updated: 18/11/2020
 */

package eStoreSearch;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.io.PrintWriter;
import java.io.FileOutputStream;
import java.io.FileNotFoundException; 
import java.io.FileInputStream;
 


public class EStoreSearchTest {

    private static EStoreSearch testStore = new EStoreSearch(); //EStore object for testing

    //populate the lists of the test store with products
    private static void populateLists() {

        Book b1 = null;
        Book b2 = null;
        Electronics e1 = null;
        Electronics e2 = null;

        try {
            b1 = new Book ("000123", "The Lost Hero", 2010);
            b2 = new Book ("000432", "68000 Microprocessor", 2015);

            e1 = new Electronics ("123456", "Samsung Galaxy S9", 2019);
            e2 = new Electronics ("667812", "Xbox One", 2017);
        }catch (Exception e) {
            //do nothing
        }

        testStore.addBook(b1, false);
        testStore.addBook(b2, false);
        testStore.addElectronic(e1,false);
        testStore.addElectronic(e2, false);
    }
    

    @Test public void testAddBook() {

        EStoreSearch testAdd = new EStoreSearch();
        Book add1 = null; 

        try {
            add1 = new Book ("710017", "Testing add book", 2009);

        }catch (Exception e) {
            //do nothing
        }

        //add book normally 
        testAdd.addBook(add1, false);
        assertEquals(1, testAdd.getProductList().size());

        //try to add book with existing ID
        Book add2 = null; 

        try {
            add2 = new Book ("710017", "Testing adding a duplicate ID", 2010);

        }catch (Exception e) {
            //do nothing
        }

        testAdd.addBook(add2, false);
        assertEquals(1, testStore.getProductList().size()); ///size of list should not change
    } 

    @Test public void testAddElectronic() {

        Electronics add1 = null;
        
        try {
            add1 = new Electronics ("001234", "Testing add electronics", 2009);

        }catch (Exception e) {
            //do nothing
        }
        //add electronic normally 
        testStore.addElectronic(add1, false);
        assertEquals(1, testStore.getProductList().size());

        //try to add electronic with existing ID
        Electronics add2 = null;
        
        try {
            add2 = new Electronics ("001234", "Testing adding a duplicate ID", 2010);

        }catch (Exception e) {
            //do nothing
        }
        testStore.addElectronic(add2, false);
        assertEquals(1, testStore.getProductList().size()); ///size of list should not change
    }

    @Test public void testSearchID() {

        populateLists(); 
        boolean result;

        //product on list
        result = testStore.searchID("000123");
        assertTrue(result); 

        //product not on list
        result = testStore.searchID("432123");
        assertFalse(result);
    }

    @Test public void testSearchKeyWords() {

        populateLists(); 
        HashMap <String, ArrayList<Integer>> testMap = testStore.createMap(); 
        boolean result;

        //product on list (tests case insensitivity too)
        result = testStore.searchKeyWords("lost hero", testMap);
        assertTrue(result); 

        //product not on list
        result = testStore.searchKeyWords("random search words", testMap);
        assertFalse(result);
    }

    @Test public void testSearchYear() {

        populateLists(); 
        boolean result;

        //product on list 
        result = testStore.searchYear("2015");
        assertTrue(result);

        //product not on list
        result = testStore.searchYear("-2000");
        assertFalse(result);
    }

    @Test public void testSearchIDAndKeyWords() {

        populateLists();
        HashMap <String, ArrayList<Integer>> testMap = testStore.createMap(); 
        boolean result;

        //product on list
        result = testStore.searchIDAndKeyWords("000432", "68000 Microprocessor", testMap);
        assertTrue(result);

        //product not on list
        result = testStore.searchIDAndKeyWords("000432", "random searching", testMap);
        assertFalse(result);
    } 

    @Test public void testSearchIDAndYear() {

        populateLists();
        boolean result;

        //product on list
        result = testStore.searchIDAndYear("123456", "2015-");
        assertTrue(result);

        //product not on list
        result = testStore.searchIDAndYear("769012", "2015-");
        assertFalse(result);
    }

    @Test public void testSearchWordsAndYear() {

        populateLists();
        HashMap <String, ArrayList<Integer>> testMap = testStore.createMap(); 
        boolean result;

        //product on list
        result = testStore.searchWordsAndYear("xbox", "2012-2019", testMap);
        assertTrue(result);

        //product not on list
        result = testStore.searchWordsAndYear("random search", "2012-2019", testMap);
        assertFalse(result);
    } 

    @Test public void testSearchAllRequests() {

        populateLists(); 
        HashMap <String, ArrayList<Integer>> testMap = testStore.createMap(); 
        boolean result;

        //product on list
        result = testStore.searchAllRequests("123456", "galaxy samsung", "2019", testMap);
        assertTrue(result);

        //product not on list
        result = testStore.searchAllRequests("971456", "galax sams", "2019", testMap);
        assertFalse(result);

    }

    @Test public void testReadAndWriteToFile() {

        //save data to a test file
        populateLists(); 
        testStore.writeToFile("testFile.txt");

        //read data from saved file into new EStoreSearch object
        EStoreSearch testReading = new EStoreSearch(); 
        testReading.readFromFile("testFile.txt");

        //new estore should have product with id "001234" loaded into it
        boolean found = testReading.searchID("001234");
        assertTrue(found);

        //new estore should have product with id "000432" loaded into it
        found = testReading.searchID("000432");
        assertTrue(found);
    }

    @Test public void testCreatingHashMap() {

        populateLists();
        HashMap <String, ArrayList<Integer>> testMap = testStore.createMap();

        //check for key that should be in the map
        assertTrue(testMap.containsKey("hero"));

        //check for key that shouldn't be in the map
        assertFalse(testMap.containsKey("check"));
    } 

    @Test public void testUpdatingMap() {

        //initial set up
        populateLists();
        HashMap <String, ArrayList<Integer>> testMap = testStore.createMap();

        Book newBook = null;
        
        try {
            newBook = new Book("888888", "should update map", 2025);

        }catch (Exception e) {
            //do nothing
        }

        //map should not have a key for "should" "update" and "map" at this point
        assertFalse(testMap.containsKey("should"));
        assertFalse(testMap.containsKey("update"));
        assertFalse(testMap.containsKey("map"));

        //add new book and update map
        testStore.addBook(newBook, false);
        testStore.updateMap(testMap, newBook.getDescription());

        //map should now have a key for "should" "update" and "map" 
        assertTrue(testMap.containsKey("should"));
        assertTrue(testMap.containsKey("update"));
        assertTrue(testMap.containsKey("map"));
    }
}  