/*
 * Allow user to add and search products in a list of books and electronics
 * Author: Sukhraj Garcha
 * Last update: 6/11/2020
 */
package eStoreSearch;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.HashMap;


public class App {
    public String getGreeting() {
        return "Hello world.";
    }
     /** 
        method to check if a given string can be parsed
        @param str a string to check 
        @return true if it can, false otherwise
     */
    public static boolean canParse(String str) {
        try {

            Double.parseDouble(str);
            return true;

        }catch (NumberFormatException e) {

            return false;
        }
    }
    /**
        method to get price as a string from user
        @param myScanner a scanner object for input
        @return the string entered by user
     */
    public static String askForPrice (Scanner myScanner) {

        String sPrice;

        //ask user for price until a valid option is entered
        while (true) {  

            sPrice = myScanner.nextLine();

            //two valid options
            if (sPrice.isEmpty()) {
                return sPrice;
            }

            if (canParse(sPrice)) {
                return sPrice;
            }

            //error message
            System.out.println("ERROR: please enter a numerical value for price (or leave blank)");
        }
    }
    /**
        check if the user's menu choices are valid
        @param validChoices an array of valid choices for menu options
        @param input the input the user entered
        @return -1 if not valid, 0 if it is a valid add, 1 if it is a valid search, or 2 if it is a valid quit
     */
    public static int checkMenuChoice (String[] validChoices, String input) {
        
        //loop through the array and make sure the choice is valid
        for (int i = 0; i < validChoices.length; i++) {

            //at any point, if the input matches an index of the array, the choice valid
            if (validChoices[i].equals(input.toLowerCase())) {

                //check if the command is for add, search, or quit and return corresponding value
                if (i == 0 || i == 1) {

                    return 0;
                
                }else if (i == 2 || i == 3) {

                    return 1;
                
                }else {

                    return 2;
                }
            }
        }
        //if we get to this point, the choice isn't valid
        return -1;
    }
    /**
        check if the range the user enters for year is valid
        @param yearRange the year range that the user entered
        @return true if valid, false otherwise
     */
    public static boolean checkYearRange (String yearRange) {  
        int length = yearRange.length();

        //year input not empty, xxxx, -xxxx, xxxx-, or xxxx-xxxx
        if (!yearRange.isBlank() && length != 4 && length != 5 && length != 9) {
            System.out.println("ERROR: invalid input for year range");
            return false;
        
        //only one year and not parsable
        }else if (length == 4) {

            if (!canParse(yearRange)) {
                System.out.println("ERROR: please enter a numerical value for year");
                return false;
            }

        //year input is of the form -xxxx or xxxx- 
        }else if (length == 5) {

            //the first/last character is not -
            if ( (yearRange.charAt(0) != '-') && (yearRange.charAt(length-1) != '-') ) {
                System.out.println("ERROR: please ensure input is of the form -xxxx or xxxx-");
                return false;
            
            //the actual year part is not a parsable integer
            }else if ( !canParse(yearRange.substring(1)) && !canParse(yearRange.substring(0, length-1)) ) {
                System.out.println("ERROR: please ensure the year is a numerical value");
                return false;
            }
        
        //year input is of the form xxxx-xxxx 
        }else if (length == 9) {

            String splitYears[] = yearRange.split("[-]+"); //split using '-'
            
            //first or second years are not parsable
            if ( !canParse(splitYears[0]) || !canParse(splitYears[1]) ) {
                System.out.println("ERROR: please enter a numerical value for both years");
                return false;
            }

            //second year is smaller than first year
            if ( Integer.parseInt(splitYears[0]) > Integer.parseInt(splitYears[1]) ) {
                System.out.println("ERROR: end year is smaller than start year");
                return false;
            }
        }
        //otherwise, input is valid
        return true;
    }
    /**
        check if the product entered is a valid choice
        @param validChoices an array of valid choices for product types
        @param type the input that the user entered
        @return -1 if not valid, 0 if book, 1 if electronic
     */
    public static int checkType (String[] validChoices, String type) {

        //loop through the array and check if the choice is valid
        for (int i = 0; i < validChoices.length; i++) {

            if (validChoices[i].equals(type.toLowerCase())) {

                //book command
                if (i == 0 || i == 1) {
                    
                    return 0;
                
                //electronic command
                }else {

                    return 1;
                }
            }
        }

        //invalid choice
        return -1;
    }
    public static void main(String[] args) {
        
        String fileName = "";
        //default file name 
        if (args.length == 0) {

            fileName = "products.txt";

        //if args is not empty, then the file name is the first command line argument
        }else {

            fileName = args[0];
        }

        EStoreSearch eStore = new EStoreSearch();
        Scanner myScanner = new Scanner(System.in); //scanner for input

        //read data from file and create the index HashMap
        eStore.readFromFile(fileName);
        HashMap <String, ArrayList<Integer>> index = eStore.createMap();

        //array that hold all the acceptable menu choices: 
        String[] validMenuChoices = {"add", "a", "search", "s", "quit", "q"};

        String userChoice;
        int check = 0; //variable to hold the result of the menu error check
        
        //main loop
        while (check != 2) {

            System.out.println("Enter your choice: (add, search, quit):");
            userChoice = myScanner.nextLine();

            check = checkMenuChoice(validMenuChoices, userChoice);

            //add 
            if (check == 0) {
                
                String type;
                int productType = -1; //int value that represents which product is chosen 
                String[] validProductChoices = {"book", "b", "electronic", "e"};

                //ask user for type of product until they enter a valid choice
                do {
                    System.out.println ("Enter what type of product you want to add: (book, electronic)");
                    type = myScanner.nextLine();  
                    productType = checkType(validProductChoices, type);

                    //error message if not a valid choice
                    if (productType == -1) {

                        System.out.println("ERROR: invalid product choice");
                    }

                }while (productType == -1);

                String id;
                String description; 
                int year; 
                String sYear; //year input as string before parsing
                boolean validAttribute;

                //ask for required attributes until they are valid choices
                do {
                    System.out.println("Enter product id:");
                    id = myScanner.nextLine();
                    System.out.println("Enter product description:");
                    description = myScanner.nextLine();

                    //ask user for year until a number is entered
                    System.out.println("Enter the product year:");
                    while (true) {

                        sYear = myScanner.nextLine();
                        if (canParse(sYear)) {
                            break;
                        }

                        //error message
                        System.out.println("ERROR: please enter a numerical value for year");
                    }
                  
                    year = Integer.parseInt(sYear); 

                    validAttribute = Book.checkAttributes(id, description, year);

                }while (!validAttribute);

                //ask for attributes specific to book or electronics and add the product to the corresponding list
                if (productType == 0) {

                    String sPrice; //hold price input as string before trying to parse
                    double price = 0;

                    //ask user for price 
                    System.out.println("Enter the price (blank if N/A):");
                    sPrice = askForPrice(myScanner);

                    //parse value of price only if user entered a value
                    if (!sPrice.isEmpty()) {
                         price = Double.parseDouble(sPrice); 
                    }
                   
                    System.out.println("Enter the author(s) or leave blank:");
                    String author = myScanner.nextLine();   
                    System.out.println("Enter the publisher or leave blank:");
                    String publisher = myScanner.nextLine();

                    //create book and add to list
                    Book currentBook = Book.makeNewBook(id, description, year, price, author, publisher);

                    if (currentBook != null) {
                        eStore.addBook(currentBook, false); 
                    }else {
                        System.out.println("Exception occured: book not added");
                    }
            
                    //update index map
                    eStore.updateMap(index, description); 

                }else {

                    String sPrice; //hold price input as string before trying to parse 
                    double price = 0;

                    //ask user for price 
                    System.out.println("Enter the price (blank if N/A):");
                    sPrice = askForPrice(myScanner);

                    //parse value of price only if user entered a value
                    if (!sPrice.isEmpty()) {
                         price = Double.parseDouble(sPrice); 
                    } 

                    System.out.println("Enter the maker or leave blank:");
                    String maker = myScanner.nextLine(); 

                    //create electronic and add to list
                    Electronics currentElectronic = Electronics.makeNewElectronic(id, description, year, price, maker);

                    if (currentElectronic != null) {
                        eStore.addElectronic(currentElectronic, false);
                    }else {
                        System.out.println("Exception occured: electronic not added");
                    }
                     
                    //update index map
                    eStore.updateMap(index, description); 
                }
               
            //search
            }else if (check == 1) {

                boolean validYear = false; //keep track of whether or not user input for year is valid

                //search fields
                String searchID;
                String keyWords;
                String searchYear = "";
            
                //ask user for search fields
                System.out.println("Enter the product ID you would like to search for (or leave blank):");
                searchID = myScanner.nextLine();

                System.out.println("Enter a set of keywords to search for (or leave blank)");
                keyWords = myScanner.nextLine();
                
                //ask user for year input until it is valid
                System.out.println("Enter a year range to search for (xxxx, -xxxx, xxxx-, xxxx-xxxx)");
                while (!validYear) {

                    searchYear = myScanner.nextLine();
                    validYear = checkYearRange(searchYear);
                }
                
                boolean found = false; //keep track of whether or not the search found a product

                //booleans to keep track of which search requests are blank
                boolean idBlank = searchID.isBlank();
                boolean keyWordsBlank = keyWords.isBlank();
                boolean searchYearBlank = searchYear.isBlank();

                //only ID is entered
                if (!idBlank && keyWordsBlank && searchYearBlank) {
                    
                    found = eStore.searchID(searchID);

                    if (!found) {
                        System.out.println("No product with the ID " + searchID + " exists");
                    }
                    
                //only keywords are entered
                }else if (idBlank && !keyWordsBlank && searchYearBlank) {

                    found = eStore.searchKeyWords(keyWords, index);

                    if (!found) {
                        System.out.println("No product(s) with the key words: " + keyWords + " exist");
                    }
                
                //only time period is entered
                }else if (idBlank && keyWordsBlank && !searchYearBlank) {

                    found =  eStore.searchYear(searchYear);

                    if (!found) {
                        System.out.println("No product(s) in the year " + searchYear + " exist");
                    }
                
                //id and key words entered
                }else if (!idBlank && !keyWordsBlank && searchYearBlank) {

                    found =  eStore.searchIDAndKeyWords(searchID, keyWords, index);

                    if (!found) {
                        System.out.println("No product with the ID " + searchID + " and the key words: " + keyWords + " exists");
                    }

                //id and year entered
                }else if (!idBlank && keyWordsBlank && !searchYearBlank) {

                    found =  eStore.searchIDAndYear(searchID, searchYear);

                    if (!found) {
                        System.out.println("No product with the ID " + searchID + " in the year " + searchYear + " exists");
                    }

                //key words and year entered
                }else if (idBlank && !keyWordsBlank && !searchYearBlank) {

                    found = eStore.searchWordsAndYear(keyWords, searchYear, index);
                     if (!found) {
                        System.out.println("No product(s) with key words: " + keyWords + " in the year " + searchYear + " exist");
                    }
                
                //all requests entered
                }else if (!idBlank && !keyWordsBlank && !searchYearBlank) {

                    found =  eStore.searchAllRequests(searchID, keyWords, searchYear, index);
                     if (!found) {
                        System.out.println("No product with the ID " + searchID + 
                                           " with the key words: " + keyWords + " in the year " + searchYear + " exists");
                    }
                
                //everything left blank
                }else {

                    System.out.println(eStore);
                }

            //invalid menu choice
            }else if (check != 2){ 

                 System.out.println("ERROR: invalid choice");
            }
        }

        //save data to file
        eStore.writeToFile(fileName);
    }
}