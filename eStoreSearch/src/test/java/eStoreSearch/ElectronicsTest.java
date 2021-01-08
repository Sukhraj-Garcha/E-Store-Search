/*
 * Contains all of the test methods for the Electronics class
 * Author: Sukhraj Garcha
 * Last updated: 18/11/2020
 */

package eStoreSearch;

import org.junit.Test;
import static org.junit.Assert.*;

public class ElectronicsTest {

    // Electronics testElectronic = null;
    
    // try {

    //     testElectronic = new Electronics("000001", "Samsung Galaxy S9", 2019, 99.99, "N/A");

    // }catch (Exception e) {
    //     //do nothing
    // }
    


    @Test public void testGetProductID() {

        Electronics testElectronic = null;
    
        try {

            testElectronic = new Electronics("000001", "Samsung Galaxy S9", 2019, 99.99, "N/A");
            
        }catch (Exception e) {
            //do nothing
        }

        String testID = testElectronic.getProductID();
        assertEquals("000001", testID);
    }

    @Test public void testSetMaker() {

        Electronics testElectronic = null;
    
        try {

            testElectronic = new Electronics("000001", "Samsung Galaxy S9", 2019, 99.99, "N/A");
            
        }catch (Exception e) {
            //do nothing
        }

        testElectronic.setMaker("test maker");
        assertEquals("test maker", testElectronic.getMaker());
    }

    @Test public void testToString() {

        Electronics testElectronic = null;
    
        try {

            testElectronic = new Electronics("000001", "Samsung Galaxy S9", 2019, 99.99, "N/A");
            
        }catch (Exception e) {
            //do nothing
        }

        String result = testElectronic.toString();
        String expectedResult = "Product ID: 000001" + "\nDescription: Samsung Galaxy S9"  + "\nYear: 2019" + "\nPrice: 99.99"  +
              "\nMaker: N/A";
        assertEquals(expectedResult, result);
    }

    @Test public void testEquals() {

        Electronics testElectronic;
        Electronics testElectronic2;

        try {

            testElectronic = new Electronics("000001", "Samsung Galaxy S9", 2019, 99.99, "N/A");
            testElectronic2 = new Electronics("000001", "Samsung Galaxy S9", 2019, 99.99, "N/A");

            assertTrue(testElectronic2.equals(testElectronic));
        }catch (Exception e) {
            //do nothing
        }
    
    }
}