package model.parser;

import model.*;
import model.Game;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;

public class APIParser extends Parser {
    private String apiKey = "bb3c4d44-93a4-4a7e-80ce-4c0c226caa98";
    private DataBase dataBase;

    /**
     * Constructor. Just saves the database reference when initialized
     * @param db datbase reference
     */
    public APIParser(DataBase db) {
        dataBase = db;
    }

    /**
     * Retrieves a game from the API given an ID.
     * @param gameID ID of the game you want to retrieve
     * @return The game object found of the given ID 'gameID' parameter
     */
    @Override
    public Game retrieveGame(int gameID) {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://boardgamegeek.com/xmlapi/boardgame/" + gameID))
                .header("Authorization", "Bearer " + apiKey)
                .GET()
                .build();
        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        if (dataBase.saveXMLStringToFile(response.body(), gameID, dataBase.gameXML)) {
            try {
                dataBase.XMLparser.addGameToList(response.body());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        //System.out.println(response.body());

        return dataBase.XMLparser.retrieveGame(gameID);
    }

    @Override
    public GameList retrieveGameList() {
        return null;
    }

    @Override
    public ArrayList<Game> getBoardGames(int numGames) {
        return null;
    }

    /**
     * Returns a game list given the string to search
     * @param toSearch What you want to search
     * @return game list object with a list of the games found in a search.
     */
    @Override
    public GameList search (String toSearch) {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://boardgamegeek.com/xmlapi/search?search=" + toSearch))
                .header("Authorization", "Bearer " + apiKey)
                .GET()
                .build();
        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        //System.out.println(response.body());

        GameList results = new GameList("SearchResults");
        ArrayList<Integer> idList = dataBase.XMLparser.parseAPISearch(response.body());

        //go through the list and get search results
        for (int i = 0; i < idList.size(); i++) {
            boolean gameExists = false;

            //first check if this game exists in our database
            for (Game g : dataBase.retrieveGames()) {
                if (g.getId() == idList.get(i)) {
                    results.addGame(g);
                    gameExists = true;
                }
            }

            //if the game doesn't exist, use the APIParser to get it
            if (!gameExists) {
                results.addGame(retrieveGame(idList.get(i)));
            }
        }

        return results;
    }
}
