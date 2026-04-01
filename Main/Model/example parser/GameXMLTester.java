/**
 * CS321, Beth Allen
 *
 * This example code walks the elements of a DOM tree.  XML documents are retrieved using
 * The default DOM parsing features included in Java.   A document tree or DOM is stored
 * as a hierarchical collection of linked nodes.
 *
 * An XMLParserUtility object is created and assigned to our input file.
 * It will retrieve an ArrayList of Game objects.
 *
 */

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class GameXMLTester {

    public static void main(String[] argv)  {

        String inputFileName = "c:\\temp\\simple1.xml";

        /******* UNCOMMENT if you don't wish to use a hardcoded filename to test
        System.out.println("Enter the name of the test input file: ");
        Scanner keyboard = new Scanner(System.in);
        inputFileName = keyboard.next();
        ************/

        try {
            /* This code segment sets up a new DOM parser, and makes sure the current
                version of Java supports a DOM parser.
             */
            XMLParserUtility myParser = new XMLParserUtility(inputFileName);
            ArrayList<Game>  myGameList = myParser.retrieveGameList();

            if (myGameList != null) {
                System.out.println("\nThere were " + myGameList.size() + " game objects found in the file.");
                for (Game g : myGameList) {
                    System.out.println(g.toString());
                }
            }
            else {
                System.out.println("Nothing was retrieved from the file.");
            }
            System.out.println("\nExiting Test program.");

        } catch (FileNotFoundException e1) {
            System.err.println("Unable to open file: " + inputFileName);
            System.err.println("Exiting program.");
            System.exit(1);
        } catch (IOException e2) {
            System.err.println("Unable to parse the XML document contained in: " + inputFileName);
            System.err.println("Exiting program.");
            System.exit(2);
        }

    }


}