package main.view.panle;

// import main.model.Game;
import main.view.Panle;

/**
 * A subpanle that shows the details of any arbitrary game.
 * @author Sam Whitlock
 */
public class GameDetailsSubPanle extends Panle {

    /**
     * Creates a new GameDetailsSubPanle that shows the details of the inputted game
     * @param Game game The game to display
     */
    public GameDetailsSubPanle(/*Game game*/) {
        super("gamedetails");

        // TODO Make this screen look pretty 
        
        // Saving the game and displaying it to the screen
        // this.toDisplay = game;
        this.update();

    }
    
    /**
     * Updates the Swing components that are being displayed to reflect the current game
     */
    public void update() {

        // TODO Display stuff here 
        // This will have to be done whenever Game gets created so that I can test it 

    }

    /**
     * Updates the game that is being displayed
     * @param gameToDisplay
     */

    
    // The game that is going to be displayed
    // private Game toDisplay;

}
