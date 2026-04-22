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

import model.DataBase;
import model.Game;
import model.parser.APIParser;
import model.parser.XMLParser;
import org.w3c.dom.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class GameXMLTester {
    public static void main(String[] argv)  {

        DataBase db = new DataBase();
        APIParser parser = new APIParser(db);

        System.out.println("\n");
        for (int i = 10; i < 14; i++) {
            parser.retrieveGame(i);
        }

        System.out.println("\n");
        for (Game g : db.retrieveGames()) {
            System.out.println(g);
        }
    }
}