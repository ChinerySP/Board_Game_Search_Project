import java.util.ArrayList;

public class GameList{
    ArrayList<Game> gameData = new ArrayList<Game>();
    String listName;

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
    void addGame(Game id){
        gameData.add(id);
    }
    void deleteList(){}
    GameList(String name){listName = name;}

}