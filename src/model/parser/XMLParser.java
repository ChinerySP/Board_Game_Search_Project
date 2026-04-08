package model.parser;

import model.Game;
import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class XMLParser extends Parser {
    public String fileLocation;

    @Override
    public ArrayList<Game> getBoardGames(int numGames) {
        return null;
    }

    @Override
    public ArrayList<Game> search(String toSearch) {
        return null;
    }

    @Override
    public Game retrieveGame(int gameID) {
        for (Game g : retrieveGameList()) {
            if (g.getId() == gameID) {
                System.out.println("Matching game of ID: \"" + String.valueOf(gameID) + "\" found.");
                return g;
            }
        }

        System.out.println("No game with matching ID: \"" + String.valueOf(gameID) + "\" found in game list.");
        return null;
    }

    public XMLParser(String inputFileName) throws FileNotFoundException, IOException {
        fileLocation = inputFileName;
        File inputFileTest = new File(inputFileName);
        // check to verify the file location is valid
        if (!inputFileTest.exists()) {
            throw new FileNotFoundException(inputFileName + " not found.");
        }

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setNamespaceAware(true);
        dbf.setExpandEntityReferences(false);

        // if the file exists, open it, and retrieve the XML doc
        // if the XML is bad, throw an exception
        try {
            // create a doc builder to parse the file
            DocumentBuilder db = dbf.newDocumentBuilder();
            // parse and convert it into a DOM tree to be able to look through later
            xmlDocumentTree = db.parse(inputFileName); // retrieves the XML text into a stored dom object
        } catch (Exception ex) {
            throw new java.io.IOException("Unable to parse XML document");
        }

        // haven't set up the game list yet, is null until then
        currentGameList = null;
        // variable "xmlDocumentTree" has all nodes found in the XML file
    }

    /**
     * Retrieves the entire game list from the XML object
     * @return an array list of game objects, in the order they were found in the file
     */
    public ArrayList<Game> retrieveGameList() {
        // only need to create the game list if needed
        if (currentGameList == null) {
            currentGameList = new ArrayList<Game>();

            // retrieve the top level node in the tree, would be items
            Element items = xmlDocumentTree.getDocumentElement();
            NodeList xmlGameList = items.getElementsByTagName("item");

            // loop for each item node within the XML
            for (int gameNumber = 0; gameNumber < xmlGameList.getLength(); gameNumber++) {
                Node currentGame = xmlGameList.item(gameNumber);
                // convert the current game into a game object, add it to list
                currentGameList.add(parseNextGame(currentGame));
            }
        }
        return currentGameList;
    }

    /**
     * Each child node of the main root node is an "item" node in the file. (Tagged with <item )
     * Parse all of the game attributes and fields out of the item.
     * Converts the given game node into a Game object
     * @param xmlGameNode The game node from the DOM tree
     * @return a Game object containing the parsed attributes
     */
    private Game parseNextGame(Node xmlGameNode) {
        String bgg_id;
        Integer bgg_rank = 0;
        String thumburi = "tbd";
        String title = "tbd";
        Integer year = 0;
        NamedNodeMap attributes = xmlGameNode.getAttributes(); // for this item, get its attributes
        bgg_id = attributes.getNamedItem("id").getNodeValue();
        try {
            bgg_rank = Integer.parseInt(attributes.getNamedItem("rank").getNodeValue());
        } catch (NumberFormatException e) {
            bgg_rank = 0; // let's use a default value if the data in the file is bad
        }

        title = parseTextField(xmlGameNode, "name");
        thumburi = parseTextField(xmlGameNode, "thumbnail");
        year = parseIntegerField(xmlGameNode, "yearpublished");

        Game createdGame = new Game();
        createdGame.setName(title);
        createdGame.setThumbnail(thumburi);
        createdGame.setId(Integer.parseInt(bgg_id));

        return createdGame;

        //return new Game(title, thumburi, year, bgg_rank, bgg_id);
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
                    fieldValue = 0; // use a default value or maybe throw an exception to deal with
                }
            }
        }
        return fieldValue;
    }

    private Document xmlDocumentTree; // this is the object tree parsed from the given XML File
    private ArrayList<Game> currentGameList; // current game list, may be null
}