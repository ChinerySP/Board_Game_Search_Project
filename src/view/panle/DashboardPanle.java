package view.panle;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.concurrent.ConcurrentHashMap;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import view.*;
import view.panle.*;
import model.*;
import view.panle.customComponents.RoundedPanle;


/**
 * The dashboard that acts as the home page for each user. Displays the user's favorites list and all of their lists so that they can access them
 * @author Sam Whitlock
 */
public class DashboardPanle extends Panle {

    /**
     * Creates a new instance of the StickySubPanle
     * @param View The view that owns this Panle
     */
    public DashboardPanle(View view) {
        super("dashboard", view);

        // This panle will use a gridbag so that we can vary the size of each panle 
        this.setLayout(new GridBagLayout());

        // The Panles that this screen will show
        gameListPanle = new GameListSubPanle(view);
        gameListListPanle = new GameListListSubPanle(view);
        gameDetailsPanle = new GameDetailsSubPanle(view);

        // Setting up the right side, one that holds all of the lists that the user owns
        gameListListPanle.setTitle("Your Lists");

        // Actually showing them on the screen
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.weightx = 0.5;
        this.add(gameListPanle, gbc);
        gbc.gridx = 1;
        this.add(Box.createHorizontalStrut(DISTANCE_BETWEEN_COMPONENTS));
        gbc.gridx = 2;
        this.add(gameListListPanle);

        // We aren't currently showing details, so we can set the flag to false
        isShowingDetails = false;

        // Making them all have a preffered size of 0, leaving it to gridbaglayout to set the sizing
        gameListPanle.setPreferredSize(new Dimension(0, gameListPanle.getHeight()));
        gameListListPanle.setPreferredSize(new Dimension(0, gameListListPanle.getHeight()));
        gameDetailsPanle.setPreferredSize(new Dimension(0, gameDetailsPanle.getHeight()));

        // Make clicking on a game in the left panle open the gameDetailsSubPanle
        // gameListPanle.setOnGameClicked(game ->);

        

    }

    /**
     * Shows the details of a game on the gameDetailsSubpanle
     * @param Game The game to show
     */
    public void showGameDetails(Game game) {

        // Regardless of whether we are showing the panle already, we need to update the game it is holding
        gameDetailsPanle.setGame(game);

        // If we aren't showing the gameDetails, we need to 
        if (!isShowingDetails) {
            
            // Hiding the collections display
            this.remove(gameListListPanle);

            // Showing the gameDetails
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = 2;
            gbc.weightx = 0.5;
            this.add(gameDetailsPanle, gbc);

        }
        
        // Making sure everything is updated
        gameDetailsPanle.update();

    }


    // Getters and setters
    /**
     * Returns the user that is being displayed
     * @return User
     */
    public User getUser() {
        return user;
    }
    
    /**
     * Sets the user that is being displayed
     * @param User newUser The new user to display
     */
    public void setUser(User newUser) {
        user = newUser;
    }

    @Override
    public void updateTheme() {
        super.updateTheme();
        gameDetailsPanle.updateTheme();
        gameListListPanle.updateTheme();
        gameListPanle.updateTheme();
    }
    


    // The user that is currently being displayed
    private User user;

    // Whether or not we are displaying game details at the moment
    private boolean isShowingDetails;       

    // The Panles that will be shown on this screen
    private GameListSubPanle gameListPanle;
    private GameListListSubPanle gameListListPanle;
    private GameDetailsSubPanle gameDetailsPanle;


}
