import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.io.IOException;
import java.util.Collections;



public class Main {
    public static void main(String[] args) throws FileNotFoundException {

        Token myToken = new Token();        //token class for storing values
        String readIn;      //string for reading user input for file
        String myString;    //String for reading in the token, probably redundant but didn't have time to rework code without it
        int leftParen = 0, rightParen = 0, leftBrac = 0, rightBrac = 0; //counters for the number of brackets/parenthesis
        File myPath;        // variable for getting the path to the test files
        List<String> fileNames = new ArrayList<>(); //array to store all the file names that are being tested

        //code taken from chat gpt to try and get the file path from the argument code
        if (args.length == 1)
        {
            readIn = args[0];   //reads in the 0 argument for a file read
            myPath = new File(readIn);

        //checking to make sure the path is correct and storing the file names into a String array
        if (myPath.isDirectory())
        {
            File[] files = myPath.listFiles();
            if (files != null)
            {
                for (File file: files)
                {
                    if (file.isFile())
                        fileNames.add(file.getName());
                }
            }
            else
            {
                System.out.println("Empty file path");
                System.exit(0);
            }
        }

        //loops through all the file names recorded and tries to open them
        for (String fileName: fileNames) {
            //prints out current file name
            System.out.println("File " + fileName);
            StringBuilder filePath = new StringBuilder();
            filePath.append(readIn);
            filePath.append(fileName);
            try (FileReader myReader = new FileReader(filePath.toString())) {

                int myChar;

                //referenced from https://www.baeldung.com/java-clear-stringbuilder-stringbuffer
                //for using/deleting string buffer
                StringBuilder myBuilder = new StringBuilder();

                //loop to iterate through the file until it reaches EOF
                while ((myChar = myReader.read()) != -1) {
                    char tempChar = (char) myChar;
                    if (myBuilder.toString().equals("\n") || myBuilder.toString().equals("\r"))
                        myBuilder.setLength(0);

                    myString = myBuilder.toString().replaceAll("\n", "").replaceAll("\r", "").replaceAll(" ", "").replaceAll("\t","");
                    //clearing the string of newlines and spaces between reading in different tokens
                    //if statement for checking a string
                    if (tempChar == '"') {
                        myBuilder.append(tempChar);
                        while ((myChar = myReader.read()) != -1 && myChar != '"') {
                            tempChar = (char) myChar;
                            myBuilder.append(tempChar);
                        }
                        if (myChar != -1) {
                            tempChar = (char) myChar;
                            myBuilder.append(tempChar);
                        }
                        if (myBuilder.toString().endsWith("\"")) {
                            myString = myBuilder.toString();
                            myToken.setValues(myString, 61);
                            myChar = myReader.read();
                            tempChar = (char) myChar;
                        } else
                            System.out.println("Error: did not end string " + myString);
                    }

                    //used "create" to indicate that I wanted to make an identifier
                    if (myString.equals("Create") || myString.equals("create") ) {
                        myBuilder.setLength(0);
                        while ((myChar = myReader.read()) != -1 && myChar != '\r' && myChar != '\n' && myChar != ' ') {
                            tempChar = (char) myChar;
                            myBuilder.append(tempChar);
                        }
                        if (!myToken.isId(myBuilder.toString()))
                            System.out.println("Invalid entry for identifier " + myBuilder.toString());
                        tempChar = 32;  //resets the temp character to a space
                    }
                    //if statements to check for comment blocks/lines
                    if (myBuilder.toString().equals("//")) {
                        myBuilder.append(tempChar);
                        while ((myChar = myReader.read()) != 13 && myChar != -1) {
                            tempChar = (char) myChar;
                            myBuilder.append(tempChar);
                        }
                        myString = myBuilder.toString();
                        myToken.setValues(myString, 62);
                        myBuilder.setLength(0);
                        //setting up the next read after the line comment
                        myChar = myReader.read();
                        tempChar = (char) myChar;
                    }//ends the comment checking section if statement
                    else if (myBuilder.toString().equals("/*")) {
                        while (true) {
                            myChar = myReader.read();
                            if (myChar == -1 || myBuilder.toString().endsWith("*/")) {
                                break;
                            }
                            tempChar = (char) myChar;
                            myBuilder.append(tempChar);
                        }
                        myString = myBuilder.toString();
                        if (myString.contains("*/")) {
                            myToken.setValues(myString, 63);
                            myBuilder.setLength(0);
                            //setting up the next read after the line comment
                            myChar = myReader.read();
                            tempChar = (char) myChar;
                        } else
                            System.out.println("Missing end of block comment");
                    }
                    if (tempChar == ' ' || tempChar == '\r' || tempChar == '\t') {
                        //convert the added characters into a string and passes them to the token class to check
                        myString = myBuilder.toString().replaceAll("\n", "").replaceAll("\r", "").replaceAll(" ", "").replaceAll("\t","");
                        if (!myString.isEmpty()) {
                            if (!myToken.longestMatch(myString)) {
                                myToken.badPrint(myString);
                            }
                        }
                        //clear the string builder
                        myBuilder.setLength(0);
                    } else if (tempChar == '(') {
                        tempChar = (char) myChar;
                        myBuilder.append(tempChar);
                        myString = myBuilder.toString().replaceAll("\n", "").replaceAll("\r", "").replaceAll(" ", "").replaceAll("\t","");
                        leftParen++;

                        if (!myToken.longestMatch(myString)) {
                            myToken.badPrint(myString);
                        }
                        //clear the string builder
                        myBuilder.setLength(0);
                    }//ends the left parenthesis if statement
                    else if (tempChar == ')') {
                        tempChar = (char) myChar;
                        myBuilder.append(tempChar);
                        myString = myBuilder.toString().replaceAll("\n", "").replaceAll("\r", "").replaceAll(" ", "").replaceAll("\t","");
                        rightParen++;

                        if (!myToken.longestMatch(myString)) {
                            myToken.badPrint(myString);
                        }
                        //clear the string builder
                        myBuilder.setLength(0);
                    }//ends the right parenthesis if statement
                    else if (tempChar == '[') {
                        tempChar = (char) myChar;
                        myBuilder.append(tempChar);
                        myString = myBuilder.toString().replaceAll("\n", "").replaceAll("\r", "").replaceAll(" ", "").replaceAll("\t","");
                        leftBrac++;

                        if (!myToken.longestMatch(myString)) {
                            myToken.badPrint(myString);
                        }
                        //clear the string builder
                        myBuilder.setLength(0);
                    }//ends the left bracket if statement
                    else if (tempChar == ']') {
                        tempChar = (char) myChar;
                        myBuilder.append(tempChar);
                        myString = myBuilder.toString().replaceAll("\n", "").replaceAll("\r", "").replaceAll(" ", "").replaceAll("\t","");
                        rightBrac++;

                        if (!myToken.longestMatch(myString)) {
                            myToken.badPrint(myString);
                        }
                        //clear the string builder
                        myBuilder.setLength(0);
                    }//ends the left bracket if statement
                    myBuilder.append(tempChar);

                }//ends the while statement

            } catch (IOException e) {
                e.printStackTrace();
            }

            //error checking the amount of parenthesis, rudimentary but didn't have time to implement something better
            if (leftParen < rightParen)
                System.out.println("Error: Missing (");
            else if (rightParen < leftParen)
                System.out.println("Error: Missing )");

            if (leftBrac < rightBrac)
                System.out.println("Error: Missing [");
            else if (rightBrac < leftBrac)
                System.out.println("Error: Missing ]");

            //prints out the good tokens, wipes the map for the next call
            myToken.goodPrint();
            System.out.println("\n");
            myToken.clearMap();
            rightBrac = rightParen = leftBrac = leftParen = 0;
        }

    }

    }
}