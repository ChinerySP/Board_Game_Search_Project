package main.model;
import java.util.ArrayList;
import java.util.Iterator;

public class GameList implements Iterable<Game>{
    /**
     *Allowes the Gamelists to be iterated through.
     * @return iterator of gameData
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
     * @prama id Int id of game to be Deleted
     * @return id of game deleted (mainly for testing)
     */
    public int deleteGame(int id){
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
     * @param id Interder value for the Id of the game
     */
    public void addGame(int id){
        //gameData.add(searchGames(id)); // not function yet, but should pull game from data base and add it
    }
    public void addGame(Game game){
        gameData.add(game); //used to add game to list via varable containing the game
    }
    /**
     * Delete list removes all the games from inside itself
     */
    public void deleteList(){
        for(int i = gameData.size(); i > 0; i--){
            gameData.removeFirst();
        }
    }
    public GameList(String name){listName = name;}

}