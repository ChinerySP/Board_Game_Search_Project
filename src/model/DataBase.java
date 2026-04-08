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

public class DataBase {
    public Parser parser;
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

        try {
            games = parser.retrieveGameList(gameXML);
            for (Game g : games) {
                for (String word : keywords) {
                    if (g.name.contains(word)) {
                        results.add(g);
                    }
                }
            }

        } catch (FileNotFoundException e1) {
            System.err.println("Unable to open file: " + gameXML);
            System.err.println("Exiting program.");
            System.exit(1);
        } catch (IOException e2) {
            System.err.println("Unable to parse the XML document contained in: " + gameXML);
            System.err.println("Exiting program.");
            System.exit(2);
        }
    }

    public void writeToFile() {

    }
}

