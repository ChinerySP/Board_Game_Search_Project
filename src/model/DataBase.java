package model;

import javax.swing.text.html.parser.Parser;
import java.util.ArrayList;
//import org.json.simple.JSONArray;
//import org.json.simple.JSONObject;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import model.*;
import model.User;
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
    public ArrayList<Game> searchGames(ArrayList<String> keywords) {
        ArrayList<Game> results = null;

        games = parser.retrieveGameList();
        for (Game g : games) {
            for (String word : keywords) {
                if (g.name.contains(word)) {
                    results.add(g);
                }
            }
        }

        return results;
    }

    public void writeToFile() {

    }
}

