package model;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Scanner;
//import org.json.simple.JSONArray;
//import org.json.simple.JSONObject;
import model.parser.APIParser;
import model.parser.XMLParser;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

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
    private ArrayList<Game> games;
    public ArrayList<User> userList;
    public String gameXML = "resources/simple1.xml";

    /**
     * Creates a blank database
     */
    public DataBase() {
        try {
            XMLparser = new XMLParser(gameXML, this);
            APIparser = new APIParser(this);
        } catch (Exception e) {
            // TODO handle this exception gracefully
            System.out.println("Could not load testing xml");
        }
        games = retrieveGames();
        userList = new ArrayList<>();
        File myObj = new File("resources/userData.txt");
        // try-with-resources: Scanner will be closed automatically
        try (Scanner myReader = new Scanner(myObj)) {
            while (myReader.hasNextLine()) {
                String[] data = myReader.nextLine().split(" ");
                User oldUser = new User(data[0], data[1]);
                userList.add(oldUser);
            }
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
        }
    }

    /**
     *
     * @param keywords list of keywords to look for within the game
     * @return
     */
    public GameList searchGames(String[] keywords) {
        GameList results = new GameList("Search Results");

        saveGames();
        for (Game g : games) {
            for (String word : keywords) {
                System.out.println(String.format("Comparing \"%s\" and \"%s\"...", g.name, word));
                if (g.name.contains(word)) {
                    System.out.println("Match found!");
                    results.addGame(g);
                }
            }
        }

        return results;
    }

    public void saveGames() {
        games = XMLparser.retrieveGameList();
    }

    /**
     *
     * @param str The string of the XML we want to add to the XML
     * @param ID The ID of the game we are adding. This should be handeled by the API/XML parser to get
     * @param filename file name of where we want to store the data.
     */
    public boolean saveXMLStringToFile(String str, int ID, String filename) {
        for (Game g : retrieveGames()) {
            if (ID == g.getId()) {
                System.out.println("Board game: \"" + g.getName() + "\" of ID: " + g.getId() + " already saved!");
                return false;
            }
        }
        System.out.println("Saving XML string to " + filename);
        System.out.println("ID: " + ID);
        File saveFile = new File(filename);

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db;
        try {
            db = dbf.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        }

        Document doc = null;
        try {
            doc = db.parse(saveFile);
        } catch (SAXException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Document newDoc = null;
        try {
            newDoc = db.parse(new InputSource(new StringReader(str)));
        } catch (SAXException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Node newNode = doc.importNode(newDoc.getDocumentElement(), true);
        doc.getDocumentElement().appendChild(newNode);

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = null;
        try {
            transformer = transformerFactory.newTransformer();
        } catch (TransformerConfigurationException e) {
            throw new RuntimeException(e);
        }

        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(saveFile);
        try {
            transformer.transform(source, result);
        } catch (TransformerException e) {
            throw new RuntimeException(e);
        }

        return true;
    }

    public ArrayList<Game> retrieveGames() {
        saveGames();
        return games;
    }
}

