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
     *
     * @param keywords list of keywords to look for within the game
     * @return
     */
    public GameList searchGames(String[] keywords) {
        GameList results = null;

        games = parser.retrieveGameList();
        for (Game g : games) {
            for (String word : keywords) {
                if (g.name.contains(word)) {
                    results.addGame(g);
                }
            }
        }

        return results;
    }

    public void writeToFile() {

    }
}

