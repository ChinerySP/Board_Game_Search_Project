package model;

import java.io.IOException;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
//import org.json.simple.JSONArray;
//import org.json.simple.JSONObject;
import model.parser.APIParser;
import model.parser.XMLParser;
import java.nio.file.Files;
import java.nio.file.Path;

public class DataBase {
    public XMLParser XMLparser;
    public APIParser APIparser;
    public ArrayList<Game> games;
    public ArrayList<User> userList;
    public String gameXML = "resources/simple1.xml";

    /**
     * Creates a blank database
     */
    public DataBase() {
        try {
            XMLparser = new XMLParser(gameXML, this);
        } catch (Exception e) {
            // TODO handle this exception gracefully
            System.out.println("Could not load testing xml");
        }
        APIparser = new APIParser(this);
        games = new ArrayList<>();
        userList = new ArrayList<>();
    }

    /**
     *
     * @param keywords list of keywords to look for within the game
     * @return
     */
    public GameList searchGames(String[] keywords) {
        GameList results = new GameList("Search Results");

        games = new ArrayList<Game>();

        for (int i = 10; i < 15; i++) {
            games.add(APIparser.retrieveGame(i));
        }

        if (games == null) {
            System.out.println("Got a null");
            return null;
        }
        for (Game g : games) {
            for (String word : keywords) {
                System.out.println(String.format("Comparing %s and %s", g.name, word));
                if (g.name.contains(word)) {
                    System.out.println("Found a match");
                    results.addGame(g);
                }
            }
        }

        return results;
    }

    public void saveGames() {
        games = XMLparser.retrieveGameList();
    }
}

