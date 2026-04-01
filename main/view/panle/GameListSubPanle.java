package main.view.panle;

import main.model.GameList;

import java.util.ArrayList;

import main.model.Game;

import main.view.Panle;
import main.view.panle.customComponents.RoundedPanle;

/**
 * A panle that holds a GameList to display to the user
 * @author Sam Whitlock
 */
public class GameListSubPanle extends Panle {
    
    /**
     * Creates a GameListSubPanle to display the inputted Games
     * @param GameList games The games to display
     */
    public GameListSubPanle(GameList games) {
        super("gamelist");
        this.games = games;

        // TODO Make actual visual elements
    }

    /**
     * Creates a blank GameListSubPanle to be populated with games later
     */
    public GameListSubPanle() {
        super("gamelist");
        this.games = new GameList("defaultBlankList");
        
        

        // TODO Make actual visual elements
    }

    /**
     * Changes the GameList that is being displayed
     * @param GameList newList The new GameList to show
     */
    public void setGameList(GameList newList) {
        this.games = newList;
    }

    /**
     * Updates the visual game panel elements to reflect the GameList associated with this panle
     */
    public void updateGames() {

        // Clearing the current visuals
        gamePanles = new ArrayList<>();
        
        // Iterating over the game panels and recreating them
        for (Game game : GameList) {
                    
        }


    }

    // The games that will be displayed here
    private GameList games;

    // An arraylist that holds the visual elements for each game
    private ArrayList<RoundedPanle> gamePanles;
    
}
