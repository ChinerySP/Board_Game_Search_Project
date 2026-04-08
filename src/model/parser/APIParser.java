package model.parser;

import model.Game;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;

public class APIParser extends Parser {
    public String apiKey;

    //Still not 100% sure how to use the API, but I think this should return a string of the game(?)
    //I think in XML format
    @Override
    public Game retrieveGame(int gameID) {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://boardgamegeek.com/xmlapi/boardgame/" + gameID))
                .build();
        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println(response.body());

        return null;
    }

    @Override
    public ArrayList<Game> retrieveGameList() {
        return null;
    }

    @Override
    public ArrayList<Game> getBoardGames(int numGames) {
        return null;
    }

    //I think this also should return an XML of the search results(?)
    @Override
    public ArrayList<Game> search (String toSearch) {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://boardgamegeek.com/xmlapi/search?search=" + toSearch))
                .build();
        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println(response.body());

        return null;
    }
}
