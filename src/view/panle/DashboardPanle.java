package view.panle;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.Box;
import javax.swing.JMenuItem;
import javax.swing.JLabel;
import javax.swing.JPopupMenu;
import java.awt.event.*;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPopupMenu;

import view.*;
import view.panle.customComponents.RoundedPopupMenu;
import model.*;


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

        // Making everything default to growing to fit the space we want to give it
        gameListPanle.setPreferredSize(new Dimension(0, gameListPanle.getPreferredSize().height));
        gameListListPanle.setPreferredSize(new Dimension(0, gameListListPanle.getPreferredSize().height));
        gameDetailsPanle.setPreferredSize(new Dimension(0, gameDetailsPanle.getPreferredSize().height));

        // Setting up the right side, one that holds all of the lists that the user owns
        gameListListPanle.setTitle("Your Lists");

        // Actually showing them on the screen
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 1.0;
        gbc.gridx = 0;
        gbc.weightx = 0.5;
        this.add(gameListPanle, gbc);
        gbc.gridx = 1;
        this.add(gameListListPanle, gbc);

        // We aren't currently showing details, so we can set the flag to false
        isShowingDetails = false;
        
        // Make clicking on a game in the left panle open the gameDetailsSubPanle
        gameListPanle.setOnGameClicked(game -> toggleGameDetails(game));

        

    }

    /**
     * Shows the details of a game on the gameDetailsSubpanle
     * @param Game The game to show
     */
    public void toggleGameDetails(Game game) {

        // If we clicked on the same game then we need to hide the panle
        if (gameDetailsPanle.getGame() == game) { // == is okay because they will always be the exact same object
            hideGameDetails();
            System.out.println("We got the thing to do the thing");
            return;
        }

        // Regardless of whether we are showing the panle already, we need to update the game it is holding
        gameDetailsPanle.setGame(game);

        // Toggling
        if (!isShowingDetails) {
            showGameDetails();
        } else {
            hideGameDetails();
        }

        // Making sure everything is updated
        gameDetailsPanle.update();

    }
    
    /**
     * Hides the gameDetails panle regardless of what was being shown (or if it was even shown at all)
     */
    public void hideGameDetails() {
        // Hiding the panle
        this.remove(gameDetailsPanle);

        // Adding the collections panle back in
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 1.0;
        gbc.gridx = 1;
        gbc.weightx = 0.5;
        this.add(gameListListPanle, gbc);
        this.revalidate();

        isShowingDetails = false;
    }

    /**
     * Shows the gameDetails panle regardless of what was being shown before
     */
    public void showGameDetails() {
        // Hiding the panle
        this.remove(gameListListPanle);

        // Adding the collections panle back in
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 1.0;
        gbc.gridx = 1;
        gbc.weightx = 0.5;
        this.add(gameDetailsPanle, gbc);
        this.revalidate();

        isShowingDetails = true;
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
     * Gets the panle ready to be shown by updating the panles it contains and hiding the gameDetails panle
     */
    @Override
    public void getSet() {

        gameListListPanle.updateLists();
        gameListPanle.updateGames();
    }
    
    /**
     * Sets the user that is being displayed
     * @param User newUser The new user to display
     */
    public void setUser(User newUser) {
        user = newUser;

        // Updating (as long as the user actually exists)
        if (this.user != null) {
            // Showing the user's favorites (or whatever the default is if they deleted it)
            // This could go out of bounds, but that is so unlikely that I haven't added anythign about it right now
            gameListPanle.setGameList(user.getGameLists().get(0)); 
            gameListPanle.updateGames();

            // Setting the lists that are displayed
            gameListListPanle.setGameListList(user.getGameLists()); 
            gameListListPanle.updateLists(); // Or whatever your update method is called

            // 3. Force the dashboard to redraw
            this.revalidate();
            this.repaint();
        }
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
