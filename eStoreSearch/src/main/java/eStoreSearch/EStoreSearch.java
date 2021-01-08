/**
  * A class that defines an object of type EStoreSearch
  * Author: Sukhraj Garcha
  * Last Updated: 6/11/2020
 */ 

 package eStoreSearch;

 import java.util.ArrayList;
 import java.util.HashMap;
 import java.util.Scanner;
 
 import java.io.PrintWriter;
 import java.io.FileOutputStream;
 import java.io.FileNotFoundException; 
 import java.io.FileInputStream;
 

 public class EStoreSearch {
 
    //attributes
    private ArrayList <Product> productList;

    /**
        no argument constructor for an EStoreSearch object
        creates an object with two empty arraylists
    */
    public EStoreSearch () {

        productList = new ArrayList <Product> ();
    }

    /**
        contructor that has all possible attributes set
        @param productList an array list of products
     */
    public EStoreSearch (ArrayList <Product> productList) {

        this.productList = new ArrayList <Product> ();
        this.productList = productList;
    }

    //accessors
    public ArrayList <Product> getProductList() {

        return new ArrayList <Product> (productList);
    }

    //mutators
    public void setProductList (ArrayList <Product> productList) {

        //clear original list
        this.productList.clear();

        //add books from given list to list
        this.productList.addAll(productList);
    }

    /**
        toString method for an EStoreSearch object
        @return a string the represents the data of the object
     */
    public String toString() {

        String data = "All Products:\n";
        
        //add all products to the data string
        for (int i = 0; i < productList.size(); i++) {

            data += productList.get(i).toString() + "\n\n";
        }

        return data + "\n";
    }

    /**
        equals method to check if two EStoreSearch objects are the same
        @param otherObject the object to compare with
        @return true (they are the same) or false (they are not the same)
     */
    public boolean equals (Object otherObject) {

       if (otherObject == null) {

            return false;
        
        }else if (getClass() != otherObject.getClass()) {

            return false;

        }else {

            EStoreSearch otherStore = (EStoreSearch)otherObject;
            return (productList.equals(otherStore.productList));
        }
    }

    /**
        check if a product with the given id already exists
        @param id the product ID to check for
        @return true if it is a duplicate, false otherwise
     */
    public boolean checkDuplicate (String id) {

        //check for duplicate product ID in list of products
        for (int i = 0; i < productList.size(); i++) {

            if (productList.get(i).matchID(id)) {

                System.out.println("ERROR: product with the ID " + id + " already exists. Product not added");
                return true;
            }
        }

        return false; //if we get to here, the ID is not a duplicate
    }  

    /**
        add a book to the list of products
        @param book the book to add 
        @param fileRead boolean that shows whether or not the data has been read from a file 
     */
    public void addBook (Book book, boolean fileRead) {

        //add book to the list only if no duplicate ID's exist
        if (!this.checkDuplicate(book.getProductID())) {

            productList.add(book);

            //only want to print this message if we are not reading from a file
            if (!fileRead) {
                System.out.println("Book succesfully added to list");
            }
        }
    }

    /**
        add an electronic to the list of electronics
        @param electronic the electronic to add
        @param fileRead boolean that shows whether or not the data has been read from a file 
     */
    public void addElectronic (Electronics electronic, boolean fileRead) {

        //add electronic to the list only if no duplicate ID's exist
        if (!this.checkDuplicate(electronic.getProductID())) {

            productList.add(electronic);

            //only want to print this message if we are not reading from a file
            if (!fileRead) {
                System.out.println("Book succesfully added to list");
            }
        }
    }

    /**
        search through list and find the product with the given id
        @param id the product ID to search for
        @return true if a product is found, false otherwise
     */
     public boolean searchID (String id) {
        
        Product tempProduct;

        //check list and ouput the product if it matches the id
        for (int i = 0; i < productList.size(); i++) {

            tempProduct = productList.get(i);
            if (tempProduct.matchID(id)) {

                System.out.println("Product found: \n" + tempProduct + "\n");
                return true; //exit method
            }
        }

        //product not found
        return false;
    }

    /**
        search the HashMap and determine which products contain a set of keywords 
        @param keyWords the set of words to search for 
        @param map the HashMap to search
        @return a list of indices of products that contain the key words in their description
     */
    private static ArrayList<Integer> computeIntersection (String keyWords, HashMap <String, ArrayList<Integer>> map) {

        //list of arraylists to hold the values associated with each key in the set of given key words
        ArrayList <ArrayList<Integer>> listOfValues = new ArrayList <ArrayList<Integer>>(); 

        //list of integers to represent indices of all products that contain all given words
        ArrayList <Integer> intersection = new ArrayList <Integer>();

        //empty list to add if no map entry exists
        ArrayList <Integer> emptyList = new ArrayList <Integer>(); 

        //split key words into individual words
        String splitWords[] = keyWords.split("[ ]+");

        //search hashmap for each word
        for (String word : splitWords) {
            
            //add the list associated with the word if an entry exists
            if (map.containsKey(word.toLowerCase())) {

                listOfValues.add( map.get(word.toLowerCase()) );

            //otherwise no entry exists and we need to add an empty list
            }else {
                
                listOfValues.add(emptyList);
            }
        }

        //determine the intersection of each list 
        intersection.addAll(listOfValues.get(0));      

        for (int i = 1; i < listOfValues.size(); i++) {

            intersection.retainAll(listOfValues.get(i));
        }
        

        return intersection;
    }

    /**
        search through list and find any products that contain the given key words
        @param keyWords set of key words to search for
        @param map a HashMap that contains a set of words matched with product indices that contain those words in their description
        @return true if any products are found, false otherwise
     */
    public boolean searchKeyWords (String keyWords, HashMap <String, ArrayList<Integer>> map) {

        ArrayList <Integer> intersection = computeIntersection(keyWords, map);

        //no products contain the given key words
        if (intersection.isEmpty()) {
            return false;
        }

        //print out all products in the intersection 
        for (int index : intersection) {

            System.out.println("Product Found:\n" + productList.get(index) + "\n");
        }

        return true; //if we get to here, a product was found
    }

    /**
        search list for products that match a given year range
        @param yearRange the year range to search for
        @return true if any products are found, false otherwise
     */
    public boolean searchYear (String yearRange) {

        Product tempProduct;
        boolean found = false;

        //check lists for products that fall within the year range
        for (int i = 0; i < productList.size(); i++) {

            tempProduct = productList.get(i);

            if (tempProduct.matchYear(yearRange)) {

                System.out.println("Product found:\n" + tempProduct + "\n");
                found = true;
            }
        }

        return found;
    }

    /**
        search through list for products that match the given id and contains the given keywords
        @param id the product ID to search for
        @param keyWords a set of key words to search for
        @param map a HashMap that contains a set of words matched with product indices that contain those words in their description
        @return true if a product is found, false otherwise
     */
    public boolean searchIDAndKeyWords (String id, String keyWords, HashMap <String, ArrayList<Integer>> map) {

        Product tempProduct;
        ArrayList <Integer> intersection = computeIntersection(keyWords, map);

        //no products contain the given key words
        if (intersection.isEmpty()) {
            return false;
        }

        //search products in intersection for the product that has the given id
        for (int index : intersection) {

            tempProduct = productList.get(index);

            if (tempProduct.matchID(id)) {

                System.out.println("Product Found:\n" + tempProduct + "\n");
                return true;
            }
        }

        return false; //product not found
    }

    /**
        search through list and find a product that matches the given ID and falls within the given time period
        @param id the product ID to search for
        @param yearRange the year range to search for
        @return true if a product is found, false otherwise
     */
    public boolean searchIDAndYear (String id, String yearRange) {

        Product tempProduct;

        //check list for a product that matches the given id and is within the given time period
        for (int i = 0; i < productList.size(); i++) {

            tempProduct = productList.get(i);

            if (tempProduct.matchID(id) && tempProduct.matchYear(yearRange)) {

                System.out.println("Product found:\n" + tempProduct + "\n");
                return true; //can exit method because every product has a unique ID
            }
        }

        //product not found
        return false;
    }

    /**
        search list for products that contain the given keywords and fall within the given time period
        @param keyWords a set of key words to search for 
        @param yearRange the year range to search for
        @param map a HashMap that contains a set of words matched with product indices that contain those words in their description
        @return true if any product(s) are found, false otherwise
     */
    public boolean searchWordsAndYear (String keyWords, String yearRange, HashMap <String, ArrayList<Integer>> map) {

        Product tempProduct;
        boolean found = false;
        
        ArrayList <Integer> intersection = computeIntersection(keyWords, map);

        //no products contain the given key words
        if (intersection.isEmpty()) {
            return false;
        }

        //search for products in the intersection that fall within the given year range
        for (int index : intersection) {

            tempProduct = productList.get(index);

            if (tempProduct.matchYear(yearRange)) {

                System.out.println("Product Found:\n" + tempProduct + "\n");
                found = true;
            }
        }

        return found;
    }

    /**
        search through list to find the product that has the given id, contains the given keywords, and falls within the given time period
        @param id the product ID to search for 
        @param keyWords a set of key words to search for 
        @param yearRange the year range to search for
        @param map a HashMap that contains a set of words matched with product indices that contain those words in their description
        @return true if a product is found, false otherwise
     */
    public boolean searchAllRequests (String id, String keyWords, String yearRange, HashMap <String, ArrayList<Integer>> map) {

        Product tempProduct;

        ArrayList <Integer> intersection = computeIntersection(keyWords, map);

        //no products contain the given key words
        if (intersection.isEmpty()) {
            return false;
        }

        //search for product in the intersection with the given id and within the year range
        for (int index : intersection) {

            tempProduct = productList.get(index);

            if (tempProduct.matchID(id) && tempProduct.matchYear(yearRange)) {

                System.out.println("Product Found:\n" + tempProduct + "\n");
                return true;
            }
        }

        return false; //product not found
    }

    /**
        helper method to remove quotes from a string
        @param str the string to remove quotes from
     */
    private static String removeQuotes(String str) {

        String newStr = str.substring(2, str.length()-1);

        return newStr;
    }

    /**
        load data into the product list from a file 
        @param fileName the file to read from
     */
    public void readFromFile(String fileName) {

        Scanner inputStream = null; 

        //open file for reading
        try {

            inputStream = new Scanner(new FileInputStream(fileName));

        }catch (FileNotFoundException e) {

            System.out.println("ERROR: unable to open file for reading");
            System.exit(0);
        }

        //check for empty file 
        if (!inputStream.hasNextLine()) {

            System.out.println("Warning: " + fileName + " is empty, no data will be loaded\n");
            return; 
        }

        String line; 
        String splitLine[];
        //attributes to read
        String type; 
        String id = ""; 
        String description = "";
        double price = 0; 
        int year = 0; 
        String authors = ""; 
        String publisher = "";
        String maker = ""; 

        //read file line by line
        while (inputStream.hasNextLine()) {
            
            //get type
            line = inputStream.nextLine();
            splitLine = line.split("[=]");
            type = removeQuotes(splitLine[1]);

            //get common attributes 
            for (int i = 0; i < 4; i++) {
                line = inputStream.nextLine(); 
                splitLine = line.split("[=]");

                //set attributes depending on which line we are on
                if (i == 0) {   
                    id = removeQuotes(splitLine[1]);

                }else if (i == 1) {
                    description = removeQuotes(splitLine[1]);

                }else if (i == 2) {
                    price = Double.parseDouble(removeQuotes(splitLine[1]));

                }else {
                    year = Integer.parseInt(removeQuotes(splitLine[1]));
                }
            }

            //get book specific attributes and add book to list
            if (type.equals("book")) {

                for (int i = 0; i < 2; i++) {
               
                    line = inputStream.nextLine();
                    splitLine = line.split("[=]");
                    
                    if (i == 0) {
                        authors = removeQuotes(splitLine[1]);

                    }else {
                        publisher = removeQuotes(splitLine[1]);
                    }
                }

                Book tempBook = Book.makeNewBook(id, description, year, price, authors, publisher);

                if (tempBook != null) {
                    this.addBook(tempBook, true);
                
                }else {

                    System.out.println("Exception occured: book not added");
                }
                
            
            //get electronic specific attributes and add electronic to list
            }else {

                line = inputStream.nextLine();
                splitLine = line.split("[=]");
                maker = removeQuotes(splitLine[1]);

                Electronics tempElectronic =  Electronics.makeNewElectronic(id, description, year, price, maker);
                
                if (tempElectronic != null) {
                    this.addElectronic(tempElectronic, true);
                }else {
                    System.out.println("Exception occured: electronic not added");
                }
            }
        }

        System.out.println("Data successfully read from " + fileName + "\n");
    }

    /**
        save all data from the product list to a file
        @param fileName the file to write to
     */
    public void writeToFile(String fileName) {

        PrintWriter outputStream = null;

        //open up file for writing
        try {

            outputStream = new PrintWriter(new FileOutputStream(fileName));

        }catch(FileNotFoundException e) {

            System.out.println("ERROR: unable to open file for writing\n");
            System.exit(0);
        }

        int counter = 0; 

        //loop through list and write data to file
        for (Product p : productList) {
            
            //create a string of all common attributes
            String commonAttributes = "productID = \"" + p.getProductID() + "\"" +
                                      "\ndescription = \"" + p.getDescription() + "\""+
                                      "\nprice = \"" + p.getPrice() + "\"" +
                                      "\nyear = \"" + p.getYear() + "\"";
            //write info of book
            if (p instanceof Book) {
                
                Book tempBook = (Book)p;
                outputStream.println("type = \"book\"");
                outputStream.println(commonAttributes);
                outputStream.println("authors = \"" + tempBook.getAuthors() + "\"");
                outputStream.print("publisher = \"" + tempBook.getPublisher() + "\"");

            //write info of electronic    
            }else {

                Electronics tempElectronic = (Electronics)p;
                outputStream.println("type = \"electronics\"");
                outputStream.println(commonAttributes);
                outputStream.print("maker = \"" + tempElectronic.getMaker() + "\"");
            }

            //print a new line only if we are not on the last product (prevents extra blank line in file);
            if (counter != productList.size() - 1) {

                outputStream.println("");
            }

            counter++;
        }

        outputStream.close();
        System.out.println("Data succesfully saved to " + fileName);
    }

    /**
        helper function to create a list of indices of products that contain a specific word in their description
        @param word the word to check for 
        @return a list of indices
     */
    private ArrayList<Integer> createValue(String word) {

        ArrayList<Integer> list = new ArrayList<Integer>();

        //check each product and see if it's description contains the given word
        for (int i = 0; i < productList.size(); i++) {

            if (productList.get(i).containsWord(word)) {

                list.add(i);
            }
        }

        return list;
    }

    /**
        create a HashMap as an index for description key words 
        @return a loaded HashMap containing a set of words paired with a list of indices 
     */
    public HashMap<String, ArrayList<Integer>> createMap () {

        HashMap<String, ArrayList<Integer>> index = new HashMap<String, ArrayList<Integer>>();
        String key; 
        ArrayList<Integer> value;
        
        for (Product p : productList) {

            String descriptionSplit[] = p.getDescription().split("[ ]+"); //split product's description 

            //create an arraylist of indices that contain each word
            for (String word : descriptionSplit) {

                key = word.toLowerCase(); 
                value = new ArrayList<Integer>(); 

                value = this.createValue(key);

                index.putIfAbsent(key, value);
            }
        }

        return index;
    }

    /**
        update the hashmap after new products are added to the list 
        @param map the HashMap to update
        @param description the new product's description
     */
    public void updateMap(HashMap<String, ArrayList<Integer>> map, String description) {

        String split[] = description.split("[ ]+"); //split the description into individual words
        String key;
        ArrayList<Integer> value;
        
        //create an arraylist of indices that contain each word
        for (String word : split) {

            key = word.toLowerCase();
            
            //if the word is already a key, then we need to update the existing list of indices
            if (map.containsKey(key)) {

                value = map.get(key); 
                value.add(productList.size()-1); //this product will be the last item in the list so we just add that index

                map.replace(key, value); //replace old entry with updated entry

            //otherwise we need to make a new entry    
            }else  {

                value = this.createValue(key);
                map.put(key, value);
            }

        }
    }
}