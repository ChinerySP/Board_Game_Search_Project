package main.view.panle;

import main.model.GameList;

import java.awt.GridBagLayout;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import main.model.Game;

import main.view.Panle;
import main.view.panle.customComponents.RoundedPanle;

// For testing
import main.view.Screen;

/**
 * A panle that holds a GameList to display to the user
 * @author Sam Whitlock
 */
public class GameListSubPanle extends Panle {

    /**
     * Main method for testing
     */
    public static void main(String[] args) {
        Screen screen = new Screen();
        screen.showPanle("sticky");
        screen.showPanle("dashboard");
        DashboardPanle dash =  (DashboardPanle) screen.getPanle("dashboard");
        GameListSubPanle right = dash.right;
        GameListSubPanle left = dash.left;
        for (int i = 0; i < 100; i++) {
            right.games.addGame(new Game());
            left.games.addGame(new Game());
            right.updateGames();
            left.updateGames();
        }
    }

    
    /**
     * Creates a GameListSubPanle to display the inputted Games
     * @param GameList games The games to display
     */
    public GameListSubPanle(GameList games) {
        super("gamelist");
        this.games = games;
        updateGames();
    }

    /**
     * Creates a blank GameListSubPanle to be populated with games later
     */
    public GameListSubPanle() {
        super("gamelist");
        this.games = new GameList("defaultBlankList");
        updateGames();
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
        for (Game game : games) {

            // The new panle that we will be adding to the list
            RoundedPanle toAdd = new RoundedPanle(Panle.CORNER_ROUNDING_RADIUS);

            // Setting the layout so that it is almost all game description and then some room for game name on the left
            toAdd.setLayout(new BoxLayout(toAdd, BoxLayout.X_AXIS));

            // Fetching the stuff to put on the screen
            JLabel name = new JLabel(game.getName());
            JLabel desc = new JLabel(game.getDescription());

            // Styling the name and description
            name.setForeground(Panle.TEXT_COLOR);
            desc.setForeground(Panle.TEXT_COLOR);

            // Making sure that it actually looks good
            toAdd.setBorder(BorderFactory.createEmptyBorder(2, 5, 2, 5));
            
            // Adding in the name of the game and the description
            toAdd.add(name); 
            toAdd.add(desc); 

            // Actually adding it in to the list
            gamePanles.add(toAdd);

        }
        
        // Showing all of the gamePanles on the screen
        for (RoundedPanle p : gamePanles) {
            this.add(p);
        }


    }

    // The games that will be displayed here
    // TODO change to pivate
    public GameList games;

    // An arraylist that holds the visual elements for each game
    private ArrayList<RoundedPanle> gamePanles;
    
}

