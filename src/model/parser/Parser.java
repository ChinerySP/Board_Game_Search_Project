package model.parser;

import model.Game;
import model.GameList;

import java.util.ArrayList;

/**
 *
 */
public abstract class Parser {
    abstract public ArrayList<Game> getBoardGames(int numGames);

    public abstract GameList search(String toSearch);

    public abstract Game retrieveGame(int gameID);

    public abstract GameList retrieveGameList();
}

