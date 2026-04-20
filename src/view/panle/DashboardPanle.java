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
        
        // // An action listener that we can use to listen for mouse
        // MouseListener popupListener = new PopupMenuListener();
        // output.addMouseListener(popupListener);
        // menuBar.addMouseListener(popupListener);

        


        // Make clicking on a game in the left panle open the gameDetailsSubPanle
        gameListPanle.setOnGameClicked(game -> toggleGameDetails(game));

        

    }

    /**
     * Shows the details of a game on the gameDetailsSubpanle
     * @param Game The game to show
     */
    public void toggleGameDetails(Game game) {

        // If we are already showing this game specifically, then 

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
