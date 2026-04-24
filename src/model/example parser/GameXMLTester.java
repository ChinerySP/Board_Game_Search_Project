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

import model.DataBase;
import model.Game;
import model.parser.APIParser;
import org.w3c.dom.*;

public class GameXMLTester {
    public static void main(String[] argv)  {

        DataBase db = new DataBase();
        APIParser parser = new APIParser(db);

    }
}