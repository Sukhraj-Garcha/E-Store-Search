/** 
  * A class that defines an object of type Product (used for inheritance)
  * Author: Sukhraj Garcha
  * Last update: 18/11/2020
 */

package eStoreSearch;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class Product {

    //attributes 
    protected String productID;
    protected String description; 
    protected double price;
    protected int year;

    /**
        No argument constructor for a Product
        Sets all attributes to default values
    */
    public Product () throws Exception {

        this("N/A", "N/A", 0, 0);
    }

    /**
        Basic constructor for a Product
        @param productID the product ID of the Product
        @param description the description of the Product
        @param year the year of the Product
    */
    public Product (String productID, String description, int year) throws Exception {
        
       this(productID, description, year, 0);
    }

    /**
        Constructor for a Product that has all possible attributes set
        @param productID the product ID of the Product
        @param description the description of the Product
        @param year the year of the Product
        @param price the price of the Product
    */
    public Product (String productID, String description, int year, double price) throws Exception{

        //validity testing
        if (checkAttributes(productID, description, year)) {

            //set given values
            this.productID = productID;
            this.description = description;
            this.year = year;
            this.price = price;
                   
        }else {
            
            throw new Exception("Fatal error: invaid attributes");
        }
    }

    //accessors
    public String getProductID() {

        return productID;
    }

    public String getDescription() {

        return description;
    }

    public int getYear() {

        return year;
    }

    public double getPrice() {

        return price;
    }

    //mutator for non-required attribute
    public void setPrice (double price) {

        this.price = price;
    }

    /**
        toString method for a Product object
        @return a string that represents the data of a Product
    */
    public String toString() {

        return ("Product ID: " + productID + "\nDescription: " + description + "\nYear: " + year + "\nPrice: " + price);
    }

    /**
        equals method to check if two Products are the same
        @param otherObject an object to compare with
        @return true if they are the same, false otherwise
     */
    public boolean equals (Object otherObject) {

        if (otherObject == null) {

            return false;
        
        }else if (getClass() != otherObject.getClass()) {

            return false;

        }else {

            Product otherProduct = (Product)otherObject;
            return  (productID.equals(otherProduct.productID) && description.equals(otherProduct.description) && year == otherProduct.year
                    && price == otherProduct.price);
        }
    }

    /**
        check if a given ID is the same as this product's productID
        @param id the id to compare
        @return true if they match, false otherwise
    */
    public boolean matchID (String id) {

        return (productID.equals(id));
    }

    /**
        check if a product's description contains a specific word
        @param word the word to check the description for
     */
    public boolean containsWord (String word) {
        
        Pattern p = Pattern.compile("\\b"+word+"\\b"); //specify regex pattern (\\b specifies word boundary so programming != program)
        Matcher m = p.matcher(description.toLowerCase());

        if (!m.find()) {
            return false;
        }

        return true;
    }

    /**
        check if a product falls within a given year range
        @param yearRange the year range to check 
        @return true if the product's year is within the range, false otherwise
     */
    public boolean matchYear (String yearRange) {

        int length = yearRange.length();
        int year1;
        int year2;
        boolean match = false;

        //if a single year
        if (length == 4) {

            year1 = Integer.parseInt(yearRange);
            match = (year == year1);  

        //of the form -xxxx or xxxx-
        }else if (length == 5) {
            
            //-xxxx
            if (yearRange.charAt(0) == '-') {
                
                //parse integer after '-' char
                year1 = Integer.parseInt( yearRange.substring(1) );
                match = (year <= year1);
            
            //xxxx-
            }else {

                //parse integer excluding '-' char
                year1 = Integer.parseInt( yearRange.substring(0, length-1) );
                match = (year >= year1);
            }
        
        //of the form xxxx-xxxx
        }else {

            String splitYears[] = yearRange.split("[-]");
            //parse both years
            year1 = Integer.parseInt(splitYears[0]);
            year2 = Integer.parseInt(splitYears[1]);

            match = (year >= year1 && year <= year2);
        }
        
        return match;
    }
    
    /**
        check if the entered attributes are valid
        @param id the product's product ID
        @param description the description of the product
        @param year the year of the product
        @return true if valid, false if not
     */
    public static boolean checkAttributes (String id, String description, int year) {
        
        boolean valid = true;

        //check if the id is 6 digits
        if (id.length() < 6 || id.length() > 6) {

            System.out.println("ERROR: product ID must be 6 digits");
            valid = false;
        }
        //check if the id contains only digits
        if (!id.matches("[0-9]+")) {

            System.out.println("ERROR: product ID must contain only digits");
            valid = false;
        }
        //check if year is in the valid range
        if (year < 1000 || year > 9999) {

            System.out.println("ERROR: year must be in the range 1000-9999");
            valid = false;   
        }
        //check if the description is not empty
        if (description.isBlank()) {

            System.out.println("ERROR: description must not be blank");
            valid = false;
        }

       return valid;
    }
}   