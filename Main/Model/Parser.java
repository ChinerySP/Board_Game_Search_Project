import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.DoubleSummaryStatistics;
import java.util.Scanner;

/**
 *
 */
public abstract class Parser {
    /**
     *
     * @param numGames
     * @return
     */
    public ArrayList<Game> getBoardGames(int numGames) {

    }

    /**
     *
     * @param toSearch
     * @return
     */
    public ArrayList<Game> search(String toSearch) {

    }

    /**
     *
     * @param gameID the ID of the game we want to retrieve, I believe this is an int value
     * @return Returns the game object whos ID matches the param 'gameID'
     */
    public Game retrieveGame(int gameID) {

    }
}

public class XMLParser extends Parser {
    public String fileLocation;

    /**
     *  This is from the XML parser code that was given in canvas
     */
    public XMLParserUtility(String inputFileName) throws FileNotFoundException, IOException {
        File inputFileTest = new File(inputFileName);
        if (!inputFileTest.exists()) {
            throw new FileNotFoundException(inputFileName+" not found.");
        }

        // if the given file exists, we will open it, and retrieve the XML document from it
        // if the XML is malformed, throw an exception
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setNamespaceAware(true);
        dbf.setExpandEntityReferences(false);
        try {
            DocumentBuilder db = dbf.newDocumentBuilder();
            xmlDocumentTree = db.parse(inputFileName);   // retrieves the XML text into a stored dom object
        } catch (Exception ex) {
            throw new java.io.IOException("Unable to parse XML document");
        }

        currentGameList = null;   // start with unallocated list
        // at this point, if there were no exceptions, the member variable
        // xmlDocumentTree contains all of the nodes found in the XML file.
    }

    /**
     * Retrieves the entire game list from the XML object
     * @return an array list of game objects, in the order they were found in the file
     */
    public ArrayList<Game> retrieveGameList() {
        // do the work to build this list only if needed
        if (currentGameList == null) {
            currentGameList = new ArrayList<Game>();

            // retrieve the top level node in the tree, items
            Element items =  xmlDocumentTree.getDocumentElement();
            NodeList xmlGameList = items.getElementsByTagName("item");

            for (int gameNumber = 0; gameNumber < xmlGameList.getLength(); gameNumber++) {
                Node game = xmlGameList.item(gameNumber);
                currentGameList.add(parseNextGame(game));
            }
        }

        return currentGameList;
    }

    /**
     * Each child node of the main root node is an "item" node in the file. (Tagged with <item )
     * Parse all of the game attributes and fields out of the item.
     * @param xmlGameNode The game node from the DOM tree
     * @return a Game object containing the parsed attributes
     */
    private Game parseNextGame(Node xmlGameNode) {
        String bgg_id;
        Integer bgg_rank=0;
        String thumburi ="tbd";
        String title="tbd";
        Integer year = 0;
        NamedNodeMap attributes = xmlGameNode.getAttributes();  // for this item, get its attributes
        bgg_id = attributes.getNamedItem("id").getNodeValue();
        try {
            bgg_rank = Integer.parseInt(attributes.getNamedItem("rank").getNodeValue());
        } catch (NumberFormatException e) {
            bgg_rank = 0;  // let's use a default value if the data in the file is bad
        }

        title = parseTextField(xmlGameNode,"name");
        thumburi = parseTextField(xmlGameNode, "thumbnail");
        year = parseIntegerField(xmlGameNode, "yearpublished");
        return new Game(title,thumburi, year, bgg_rank, bgg_id);
    }

    /**
     * Some game data is stored as child elements in the XML <fieldname value="...">
     * Given a single game node from the DOM object, extract the given field from its child nodes.
     * @param xmlGameNode  a game node from DOM tree
     * @param fieldname the field information to extract
     * @return a string containing the field value
     */
    private String parseTextField(Node xmlGameNode, String fieldname) {
        NodeList fields = xmlGameNode.getChildNodes();
        String fieldText = "unknown";
        for (int i = 0; i < fields.getLength(); i++) {
            Node field = fields.item(i);
            if (field.getNodeName().equals(fieldname)) {
                NamedNodeMap attributes = field.getAttributes();
                fieldText = attributes.getNamedItem("value").getNodeValue();
            }
        }
        return fieldText;
    }

    /**
     * Some game data is stored as child elements in the XML <fieldname value="...">
     * Given a single game node from the DOM object, extract the given field from its child nodes,
     * as an Integer value
     * @param xmlGameNode  a game node from DOM tree
     * @param fieldname the field information to extract
     * @return the integer value found in the field, or 0 if the field is invalid
     */
    private Integer parseIntegerField(Node xmlGameNode, String fieldname) {
        NodeList fields = xmlGameNode.getChildNodes();
        Integer fieldValue = 0;
        for (int i = 0; i < fields.getLength(); i++) {
            Node field = fields.item(i);
            if (field.getNodeName().equals(fieldname)) {
                NamedNodeMap attributes = field.getAttributes();
                try {
                    fieldValue = Integer.parseInt(attributes.getNamedItem("value").getNodeValue());
                } catch (NumberFormatException e) {
                    fieldValue = 0;  // use a default value or maybe throw an exception to deal with
                }
            }
        }
        return fieldValue;
    }

    //----------- private internal attributes of a XMLParserUtility Object ---------------------
    private Document  xmlDocumentTree;  // this is the object tree parsed from the given XML File
    private ArrayList<Game> currentGameList;  // current game list, may be null
}

public class APIParser extends Parser {
    public String apiKey;
}