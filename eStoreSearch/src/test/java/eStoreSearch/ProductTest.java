/*
 * Contains all of the test methods for the Product class
 * Author: Sukhraj Garcha
 * Last updated: 18/11/2020
 */

package eStoreSearch; 

import org.junit.Test;
import static org.junit.Assert.*;

public class ProductTest {

    // Product testProduct = null;

    // try {
    //     testProduct = new Product("182340", "Test Product", 2010);

    // }catch (Exception e) {
    //     //do nothing
    // }

    @Test public void testEquals() {
        
        Product testProduct = null;
        Product sameProduct = null;

        try {

            testProduct = new Product("182340", "Test Product", 2010);
            sameProduct = new Product("182340", "Test Product", 2010);
            assertTrue(sameProduct.equals(testProduct));

        }catch (Exception e) {

        }
        
    }

    @Test public void testContainsWord() {

        Product testProduct = null;
    
        try {
            testProduct = new Product("182340", "Test Product", 2010);

        }catch (Exception e) {
            //do nothing
        }

        boolean result;

        //description contains word
        result = testProduct.containsWord("test");
        assertTrue(result);

        //description doesn't contain word
        result = testProduct.containsWord("not");
        assertFalse(result);

        //description contains part of a word but not the full word
        result = testProduct.containsWord("produ");
        assertFalse(result);
    }
}   