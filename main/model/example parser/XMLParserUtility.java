import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

/**
 * This example code walks the elements of a DOM tree.  XML documents are retrieved using
 * Apache's XERCES Dom parser (inluded in Java).   A document tree or DOM is stored
 * as a hierarchical collection of linked nodes.
 *
 * In this example, I will simply walk elements of the tree looking for particular nodes based
 * on the xml in the example file simple1.xml.
 *
 * The xml contained in this file is:
 * <items termsofuse="https://boardgamegeek.com/xmlapi/termsofuse">
 * <item id="379005" rank="1">
 * <thumbnail value="https://cf.geekdo-images.com/cFHnRZtfgB9XNWaw46bQ4Q__thumb/img/tPedQESQaqWqwZakkmsBMx0YIZI=/fit-in/200x150/filters:strip_icc()/pic7302419.png"/>
 * <name value="The Lord of the Rings Adventure Book Game"/>
 * <yearpublished value="2023"/>
 * </item>
 * <item id="358661" rank="2">
 * <thumbnail value="https://cf.geekdo-images.com/vmFLMcfXt-c4sd8y6-579g__thumb/img/-m1uihAm8wcfhpITYpN3DmXBV9s=/fit-in/200x150/filters:strip_icc()/pic6738525.jpg"/>
 * <name value="Andromeda's Edge"/>
 * <yearpublished value="2024"/>
 * </item>
 * </items>
 *
 * There are two items in the document.
 * Each item is a Game.  The <item tag has 2 attributes, id and rank
 * and it has three child nodes. Each child node has a tag such as thumbnail, name, yearpublished
 * Each of these child nodes has an attribute named "value".  These values become fields in our Game object
 *
 */

public class XMLParserUtility {
    /**
     * Constructor used to create a new XML parser object attached to the given file. If the file does not
     * exist, then this will throw an exception for the user to deal with
     * @param inputFileName filename containing xml text
     * @throws FileNotFoundException, IOException
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
