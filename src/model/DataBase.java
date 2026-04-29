package model;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringReader;

import java.util.ArrayList;
import java.util.Scanner;

import model.parser.APIParser;
import model.parser.XMLParser;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class DataBase {
    public XMLParser XMLparser;
    public APIParser APIparser;
    private GameList games;
    public ArrayList<User> userList;
    public String gameXML = "resources/simple1.xml";
    public String userdata = "resources/userData.txt";

    /**
     * Constructor which creates a blank database.
     * Initializes the parsers and passes a reference to the database
     */
    public DataBase() {
        try {
            XMLparser = new XMLParser(gameXML, this);
        } catch (Exception e) {
            // TODO handle this exception gracefully
            System.out.println("Could not load testing xml.");
        }
        APIparser = new APIParser(this);
        games = new GameList("games");
        games = retrieveGames();
        userList = new ArrayList<>();

        File myObj = new File(userdata);
        // try-with-resources: Scanner will be closed automatically
        try (Scanner myReader = new Scanner(myObj)) {
            while (myReader.hasNextLine()) {
                ArrayList<String> data = new ArrayList<String>();
                for(int i = 0; i < 4; i++) {
                    data.add(myReader.nextLine());
                }
                User oldUser = new User(data.get(0), data.get(1));
                if(data.get(2).equals("false"))
                    oldUser.setDarkMode(false);
                if(data.get(3).equals("false"))
                    oldUser.setAPI(false);
                userList.add(oldUser);
            }
        } catch (FileNotFoundException e) {
            System.out.println("*** ERROR: Could not find user data file. ***");
        }
    }

    /**
     * Searches the current game list using a list of keywords.
     * @param keywords list of keywords to look for within the game
     * @return A game list containing the games which match the results
     */
    public GameList searchGames(String[] keywords, Boolean APIMode) {
        GameList results = new GameList("Search Results");

        if (!APIMode) {
            saveGames();
            for (Game g : games) {
                for (String word : keywords) {
                    if (g.name.toLowerCase().contains(word.toLowerCase())) {
                        results.addGame(g);
                    }
                }
            }

            return results;
        }
        else {
            //For each word in the keywords, search the API for that keyword
            //With each returned game list, add each game to the results

            for (String word : keywords) {
                //attempt to use the API to get results
                GameList searchResults = APIparser.search(word);

                //check if we get a response

                //yes response
                if (searchResults == null) {

                    //just get from our database
                    saveGames();
                    for (Game g : games) {
                        for (String term : keywords) {
                            if (g.name.toLowerCase().contains(term.toLowerCase())) {
                                results.addGame(g);
                            }
                        }
                    }
                }
                //yes response
                else {
                    for (Game g : searchResults) {
                        results.addGame(g);
                    }
                }
            }
            return results;
        }
    }

    /**
     * Grabs the game list from XMLParser, and saves it to the games list
     */
    public void saveGames() {
        games = XMLparser.retrieveGameList();
    }

    /**
     * This method takes in the XML as a raw string and saves it to the savefile
     * This function is exclusively used by the API parser, which is what retrieves the XML from online
     * The ID is also retrieved by the API parser, and is retrieved before the data is saved
     * @param str The string of the XML we want to add to the XML
     * @param ID The ID of the game we are adding
     * @param filepath file (location) of where we want to store the data.
     */
    public boolean saveXMLStringToFile(String str, int ID, String filepath) {
        for (Game g : retrieveGames()) {
            if (ID == g.getId()) {
                return false;
            }
        }

        //create a file with the filepath
        File saveFile = new File(filepath);

        //create the document builder stuff
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db;
        try {
            db = dbf.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        }

        //create the document with the saveFile we are creating/updating
        Document doc = null;
        try {
            doc = db.parse(saveFile);
        } catch (SAXException | IOException e) {
            throw new RuntimeException(e);
        }

        //create a new document with our str we want to add to the file
        Document newDoc = null;
        try {
            newDoc = db.parse(new InputSource(new StringReader(str)));
        } catch (SAXException | IOException e) {
            throw new RuntimeException(e);
        }

        //import the new document as another node on the doc we want to save to
        Node newNode = doc.importNode(newDoc.getDocumentElement(), true);
        doc.getDocumentElement().appendChild(newNode);

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = null;
        try {
            transformer = transformerFactory.newTransformer();
        } catch (TransformerConfigurationException e) {
            throw new RuntimeException(e);
        }

        //saves the file
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(saveFile);
        try {
            transformer.transform(source, result);
        } catch (TransformerException e) {
            throw new RuntimeException(e);
        }

        //if its successful
        return true;
    }

    /**
     * This methods function is to simply return the games List.
     * But it must save the games which the XML Parser hasn't stored yet.
     * @return returns the current game list as an array list of games
     */
    public GameList retrieveGames() {
        saveGames();
        return games;
    }
}

