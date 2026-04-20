package model;

import java.util.ArrayList;
//import org.json.simple.JSONArray;
//import org.json.simple.JSONObject;
import model.parser.XMLParser;

public class DataBase {
    public XMLParser parser;
    public ArrayList<Game> games;
    public ArrayList<User> userList;
    public String gameXML = "resources/simple1.xml";

    /**
     * Creates a blank database
     */
    public DataBase() {
        try {
            parser = new XMLParser(gameXML);
        } catch (Exception e) {
            // TODO handle this exception gracefully
            System.out.println("Could not load testing xml");
        }
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

        games = parser.retrieveGameList();
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

    public void writeToFile() {

    }
}

