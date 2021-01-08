/** 
  * A class that defines an object of type Electronics
  * Author: Sukhraj Garcha
  * Last update: 18/11/2020
 */
package eStoreSearch;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class Electronics extends Product{

    //new attributes
    private String maker;

    /**
        no argument constructor for an Electronic
     */
     public Electronics() throws Exception {

        this("N/A", "N/A", 0, 0, "N/A");
     }

     /**
        Basic constructor for an Electronic
        @param productID the product ID of the electronic
        @param description the description of the electronic 
        @param year the year of the electronic
     */
    public Electronics (String productID, String description, int year) throws Exception {
        
        this(productID, description, year, 0, "N/A");
    }

    /**
        Constructor for an Electronic with all possible attributes
        @param productID the product ID of the electronic
        @param description the description of the electronic 
        @param year the year of the electronic
        @param price the price of the electronic
        @param maker the electronic's maker (company)
     */
    public Electronics (String productID, String description, int year, double price, String maker) throws Exception {

        super(productID, description, year, price);

        //set new attribute
        this.maker = maker;  
    }

    //accessors
    public String getMaker() {

        return maker;
    }

    //mutators for non-required attributes
    public void setMaker (String maker) {

        this.maker = maker;
    }

    /**
        toString method for an Electronics object
        @return a string that represents the data of an Electronics object
    */
    public String toString() {

        return (super.toString() + "\nMaker: " + maker);
    }

    /**
        equals method to check if two electronics are the same
        @param otherObject the object to compare with
        @return true (they are the same) or false (they are not the same)
     */
    public boolean equals (Object otherObject) {

        if (otherObject == null) {

            return false;
        
        }else if (getClass() != otherObject.getClass()) {

            return false;

        }else {

            Electronics otherElectronic = (Electronics)otherObject;
            return super.equals(otherElectronic) && (maker.equals(otherElectronic.maker));
        }
    }

     /**
        create a new electronic depending on which attributes are given
        @param productID the product ID of the electronic
        @param description the description of the electronic 
        @param year the year of the electronic
        @param price the price of the electronic
        @param maker the electronic's maker (company)
        @return a new Electronics object with the given attributes
     */
    public static Electronics makeNewElectronic (String id, String description, int year, double price, String maker) {

        Electronics newElectronic;

        //make electronic using constructor that takes all required attributes
        if (price != 0 && !maker.isBlank()) {

            try {

                newElectronic = new Electronics(id, description, year, price, maker);

            //if exception occurs, return a null electronic
            }catch (Exception e) {

                System.out.println(e.getMessage());
                newElectronic = null;
            }

        //otherwise use basic constructor and mutators to set attributes that are present
        }else {

            try {

                newElectronic = new Electronics(id, description, year);

                if (price != 0) {

                    newElectronic.setPrice(price);
                }

                if (!maker.isBlank()) {

                    newElectronic.setMaker(maker);
                }

            //if exception occurs, return a null electronic
            }catch (Exception e) {

                System.out.println(e.getMessage());
                newElectronic = null;
            }
           
        }
        
        return newElectronic;
    }
}