/*
 * Contains all of the test methods for the App (main) class 
 * Author: Sukhraj Garcha
 * Last updated: 15/10/2020
 */

package eStoreSearch;

import org.junit.Test;
import static org.junit.Assert.*;

public class AppTest {

    @Test public void testAppHasAGreeting() {
        App classUnderTest = new App();
        assertNotNull("app should have a greeting", classUnderTest.getGreeting());
    }

    @Test public void testCheckMenuChoice() {

        String[] validChoices = {"add", "a", "search", "s", "quit", "q"};
        int result;

        //invalid choice
        result = App.checkMenuChoice(validChoices, "");
        assertEquals(-1, result);
        result = App.checkMenuChoice(validChoices, "        ");
        assertEquals(-1, result);
        result = App.checkMenuChoice(validChoices, "asfg");
        assertEquals(-1, result);

        //valid add 
        result = App.checkMenuChoice(validChoices, "add");
        assertEquals(0, result);
        result = App.checkMenuChoice(validChoices, "a");
        assertEquals(0, result);
        
        //valid search
        result = App.checkMenuChoice(validChoices, "search");
        assertEquals(1, result);
        result = App.checkMenuChoice(validChoices, "s");
        assertEquals(1, result);

        //valid quit
        result = App.checkMenuChoice(validChoices, "quit");
        assertEquals(2, result);
        result = App.checkMenuChoice(validChoices, "q");
        assertEquals(2, result);

        //case checks
        result = App.checkMenuChoice(validChoices, "QuIt");
        assertEquals(2, result);
        result = App.checkMenuChoice(validChoices, "A");
        assertEquals(0, result);
    }

    @Test public void testCheckType() {

        String[] validChoices = {"book", "b", "electronic", "e"};
        int result; 

        //invalid choice
        result = App.checkType(validChoices, "");
        assertEquals(-1, result);
        result = App.checkType(validChoices, "     ");
        assertEquals(-1, result);
        result = App.checkType(validChoices, "asffa");
        assertEquals(-1, result);

        //valid book
        result = App.checkType(validChoices, "book");
        assertEquals(0, result);
        result = App.checkType(validChoices, "b");
        assertEquals(0, result);

        //valid electronic
        result = App.checkType(validChoices, "electronic");
        assertEquals(1, result);
        result = App.checkType(validChoices, "e");
        assertEquals(1, result);

        //case checks
        result = App.checkType(validChoices, "BoOk");
        assertEquals(0, result);
        result = App.checkType(validChoices, "E");
        assertEquals(1, result);
    }


    /**
        test to make sure the checkYearRange function error checks user input for time period properly
     */
    @Test public void testCheckYearRange() {
        
        boolean result;
        
        //4 chars but not a numerical value
        result = App.checkYearRange("asdd");
        assertFalse(result);

        //not atleast 4 chars
        result = App.checkYearRange("201");
        assertFalse(result);

        //5 chars but not a numerical value
        result = App.checkYearRange("200a+");
        assertFalse(result);

        //5 chars but doesn't have '-' after
        result = App.checkYearRange("2010+");
        assertFalse(result);

        //5 chars but doesn't have '-' before
        result = App.checkYearRange("]2020");
        assertFalse(result);

        //5 chars but not a numerical value
        result = App.checkYearRange("202aa");
        assertFalse(result);

        //xxxx-xxxx but either year is not a numerical value
        result = App.checkYearRange("200a-2020");
        assertFalse(result);
        result = App.checkYearRange("2014-203a");
        assertFalse(result);

        //xxxx-xxxx but second year is smaller than first
        result = App.checkYearRange("2020-2014");
        assertFalse(result);

        //valid choices
        result = App.checkYearRange("2011");
        assertTrue(result);
        result = App.checkYearRange("2011-");
        assertTrue(result);
        result = App.checkYearRange("-2011");
        assertTrue(result);
        result = App.checkYearRange("2011-2020");
        assertTrue(result);
        
    }
}
