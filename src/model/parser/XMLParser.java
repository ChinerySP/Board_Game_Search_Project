package model.parser;

import model.DataBase;
import model.Game;
import model.GameList;
import org.w3c.dom.*;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

public class XMLParser extends Parser {
    public String fileLocation;
    private DataBase dataBase;

    @Override
    public ArrayList<Game> getBoardGames(int numGames) {
        return null;
    }

    @Override
    public GameList search(String toSearch) {
        return null;
    }

    /**
     * Finds and returns a game with the corresponding ID
     * @param gameID the ID of the game we want to find
     * @return returns the gaame (g) which was located using the gameID
     */
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

    /**
     * Constructor for the parser that loads in the XML from the save file
     * @param inputFileName File path to the XML save which the constructor parses and initializes
     * @param dataBase Reference to the database which created the parser. Stored and used to talk back to database.
     * @throws FileNotFoundException Exception thrown when the input file is not found
     * @throws IOException Exception thrown when XML file is malformed
     */
    public XMLParser(String inputFileName, DataBase dataBase) throws FileNotFoundException, IOException {
        this.dataBase = dataBase;
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
     * Adds and saves an XML string to the file and updates the game list
     * @param xmlContent the raw XML as a string which we will save
     * @throws IOException Exception thrown when XML string is bad
     */
    public void addGameToList(String xmlContent) throws IOException {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setNamespaceAware(true);
        dbf.setExpandEntityReferences(false);

        //creates a document to parse using the xml string
        try {
            DocumentBuilder db = dbf.newDocumentBuilder();
            InputSource is = new InputSource(new StringReader(xmlContent));
            xmlDocumentTree = db.parse(is);
        } catch (Exception ex) {
            throw new IOException("Unable to parse XML string");
        }

        // retrieve the top level node in the tree, would be boardgame
        Element items = xmlDocumentTree.getDocumentElement();
        NodeList xmlGameList = items.getElementsByTagName("boardgame");

        // loop for each item node within the XML
        for (int gameNumber = 0; gameNumber < xmlGameList.getLength(); gameNumber++) {
            Node currentGame = xmlGameList.item(gameNumber);
            // convert the current game into a game object, add it to list
            Game ng = parseNextGame(currentGame);
            retrieveGameList().addGame(ng);
            dataBase.saveGames();
        }
    }

    /**
     * Retrieves the entire game list from the XML object
     * @return an array list of game objects, in the order they were found in the file
     */
    public GameList retrieveGameList() {
        // only need to create the game list if needed
        if (currentGameList == null) {
            currentGameList = new GameList("list");

            // retrieve the top level node in the tree, would be boardgames
            Element items = xmlDocumentTree.getDocumentElement();
            NodeList xmlGameList = items.getElementsByTagName("boardgame");

            //System.out.println(xmlGameList.getLength());
            // loop for each item node within the XML
            for (int gameNumber = 0; gameNumber < xmlGameList.getLength(); gameNumber++) {
                Node currentGame = xmlGameList.item(gameNumber);
                // convert the current game into a game object, add it to list
                currentGameList.addGame(parseNextGame(currentGame));
            }
        }
        return currentGameList;
    }


    public GameList parseSearch(String xmlSearchContent) {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        return null;
    }

    /**
     * Each child node of the main root node is an "item" node in the file. (Tagged with <item )
     * Parse all of the game attributes and fields out of the item.
     * Converts the given game node into a Game object
     * @param xmlGameNode The game node from the DOM tree
     * @return a Game object containing the parsed attributes
     */
    private Game parseNextGame(Node xmlGameNode) {
        NamedNodeMap attributes = xmlGameNode.getAttributes();

        String bgg_id = "0";
        int bgg_rank = 0;
        String thumburi = "";
        String title = "";
        int year = 0;
        String desc = "";

        // ID
        Node idNode = attributes.getNamedItem("objectid");
        if (idNode != null) {
            bgg_id = idNode.getNodeValue();
        }

        title = parseTextField(xmlGameNode, "name");
        thumburi = parseTextField(xmlGameNode, "thumbnail");
        desc = parseTextField(xmlGameNode, "description");

        Game createdGame = new Game();
        createdGame.setName(title);
        createdGame.setDescription(desc);
        createdGame.setThumbnail(thumburi);
        createdGame.setId(Integer.parseInt(bgg_id));

        return createdGame;
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

        for (int i = 0; i < fields.getLength(); i++) {
            Node field = fields.item(i);
            if (field.getNodeType() == Node.ELEMENT_NODE && field.getNodeName().equals(fieldname)) {
                return field.getTextContent().trim();
            }
        }

        return "unknown";
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
    private GameList currentGameList; // current game list, may be null
}