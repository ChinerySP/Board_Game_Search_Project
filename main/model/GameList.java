package main.model;
import java.util.ArrayList;
import java.util.Iterator;

public class GameList implements Iterable<Game>{
    /**
     *Allowes the Gamelists to be iterated through.
     */
    public Iterator<Game> iterator() {
        return gameData.iterator();
    }

    /**
     * stores a list of games in an arraylist
     */
    ArrayList<Game> gameData = new ArrayList<Game>();
    String listName;

    /**
     * takes in an id and compares games in the gamedata array intill it finds it and can remove it
     */
    int deleteGame(int id){
        Game temp = new Game();
        temp.setId(id);
        int count = 0;
        for(Game i : gameData){
            if(0 == i.compareTo(temp)){
                gameData.remove(count);
                return id;
            }
            count++;
        }
        return -1;
    }

    /**
     * add game should be able to find a game from the database based off an id
     */
    void addGame(int id){
        //gameData.add(searchGames(id)); // not function yet, but should pull game from data base and add it
    }
    void addGame(Game game){
        gameData.add(game); //used to add game to list via varable containing the game
    }
    void deleteList(){}
    GameList(String name){listName = name;}

}