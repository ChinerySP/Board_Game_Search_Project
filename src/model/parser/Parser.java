package model;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;

/**
 *
 */
public abstract class Parser {
    abstract public ArrayList<Game> getBoardGames(int numGames);

    public abstract ArrayList<Game> search(String toSearch);

    public abstract Game retrieveGame(int gameID);

    // TODO implement (this was requested by the Database class)
    public abstract ArrayList<Game> retrieveGameList();
}

