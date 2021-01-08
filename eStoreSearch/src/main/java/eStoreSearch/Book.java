/** 
  * A class that defines an object of type Book
  * Author: Sukhraj Garcha
  * Last update: 18/11/2020
 */
package eStoreSearch;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class Book extends Product {

    //new attributes
    private String authors;
    private String publisher;

    /**
        no arguement constructor for a Book
     */
    public Book () throws Exception {

        this("N/A", "N/A", 0, 0, "N/A", "N/A");
    }

    /**
        Basic constructor for a Book
        @param productID the product ID of the book
        @param description the description of the book
        @param year the year of the book
     */
    public Book (String productID, String description, int year) throws Exception {
        
        this(productID, description, year, 0, "N/A", "N/A");
    }

    /**
        Constructor for a Book that has all possible attributes set
        @param productID the product ID of the book
        @param description the description of the book
        @param year the year of the book
        @param price the price of the book
        @param authors the book's authors
        @param publisher the book's publisher
     */
    public Book (String productID, String description, int year, double price, String authors, String publisher) throws Exception {

        super(productID, description, year, price);

        //set new attributes
        this.authors = authors;
        this.publisher = publisher;
    }

    //accessors
    public String getAuthors() {

        return authors;
    }

    public String getPublisher() {

        return publisher;
    }

    //mutators for non-required attributes
    public void setAuthors (String authors) {

        this.authors = authors;
    }

    public void setPublisher (String publisher) {

        this.publisher = publisher;
    }

    /**
        toString method for a Book object
        @return a string that represents the data of a Book
     */
    public String toString() {

        return (super.toString() + "\nAuthor(s): " + authors + "\nPublisher: " + publisher);
    }

    /**
        equals method to check if two books are the same
        @param otherObject the object to compare with
        @return true (they are the same) or false (they are not the same)
     */
   public boolean equals (Object otherObject) {

        if (otherObject == null) {

            return false;
        
        }else if (getClass() != otherObject.getClass()) {

            return false;

        }else {

            Book otherBook = (Book)otherObject;
            return super.equals(otherBook) && (authors.equals(otherBook.authors))
                                           && (publisher.equals(otherBook.publisher)); 
        }
    }

    /**
       create a new book depending on which attributes are given
       @param id the product ID
       @param description the book's description
       @param year the year of the book
       @param price the price of the book
       @param authors the author's of the book
       @param publisher the publisher of the book
       @return a new Book object with the given attributes
     */
    public static Book makeNewBook (String id, String description, int year, double price, String authors, String publisher) {

        Book newBook; 

        //make book using constructor that takes all required attributes
        if (price != 0 && !authors.isBlank() && !publisher.isBlank()) {

            try {

                newBook = new Book(id, description, year, price, authors, publisher);

            //if exception occurs, return a null book
            }catch (Exception e) {

                
                System.out.println(e.getMessage());
                newBook = null;
            }

        //otherwise use basic constructor and mutators to set attributes that are present
        }else { 

            try {

                newBook = new Book(id, description, year);

                if (price != 0) {

                    newBook.setPrice(price);
                }

                if (!authors.isBlank()) {

                    newBook.setAuthors(authors);
                }

                if (!publisher.isBlank()) {

                    newBook.setPublisher(publisher);
                }

            //if exception occurs, return a null book
            }catch (Exception e) {

                System.out.println(e.getMessage());
                newBook = null;
            }
        }

        return newBook;
    }
}