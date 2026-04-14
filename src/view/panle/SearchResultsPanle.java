package view.panle;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

import model.*;
import view.*;


/**
 * The Panle that holds the search results after a search
 * @author Sam Whitlock
 */
public class SearchResultsPanle extends Panle {

    /**
     * Creates a new instance of the StickySubPanle
     * @param View The view that owns this panle
     */
    public SearchResultsPanle(View view) {
        super("searchresults", view);

        // This Panle will use a gridbag because it is my ✨favorite✨ (also so that the sizing works out but whatever)
        this.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.fill = GridBagConstraints.BOTH;
        constraints.weightx = 1.0;
        constraints.weighty = 1.0;

        // Creating a left and a right. The right side will not always be shown, but the left side will
        // The left will show the games returned by the search and the right will show details of a game once one is clicked
        resultsPanle = new GameListSubPanle(view);

        // Defining what the results panle should do if a game is clicked
        resultsPanle.setOnGameClicked(clickedGame -> {
            
            if (isShowingDetails) {

                // If we are showing the details and the game is the same one, then we need to close it
                if (gameDetailsPanle.getGame().equals(clickedGame)) {
                    hideGameDetails();
                    return;
                }

                // Otherwise, we can just update the game that is currently being displayed
                gameDetailsPanle.setGame(clickedGame);
                return;
            }

            // If we got here, we need to set the game and open the game details
            gameDetailsPanle.setGame(clickedGame);
            showGameDetails(clickedGame);
        });

        // The right side, which holds the game details (obviously)
        gameDetailsPanle = new GameDetailsSubPanle(view);

        // Setting what happens when a user wants to add a game to the list
        gameDetailsPanle.setOnNewListCreated(listName -> {

            // Creating a new list that we can add to the User
            GameList toAdd = new GameList(listName);

            // Adding it to the user's list
            // TODO Add in when .addList exists (or a .getGameLists)
            // view.getUser().addList(toAdd);
            System.out.println(String.format("Added in new list %s", listName));

        });

        // Only adding the left right now because it is the only thing that we are currently worried about
        this.add(resultsPanle, constraints);

    }
    
    /**
     * Shows the gameDetails of a specific game when it is clicked
     * @param Game toShow The game to display the information of in this Panle
     */
    public void showGameDetails(Game toShow) {

        // making sure that we don't add it multiple times
        if (isShowingDetails)
            return;

        GridBagLayout layout = (GridBagLayout) this.getLayout();

        // Shrink the left panel to take up only 50% of the space
        GridBagConstraints leftGbc = layout.getConstraints(gameDetailsPanle);
        leftGbc.weightx = 0.5;
        layout.setConstraints(gameDetailsPanle, leftGbc);

        // Making the gameDetailsSubPanle only take up half the space on the right
        GridBagConstraints rightGbc = new GridBagConstraints();
        rightGbc.gridx = 1;
        rightGbc.gridy = 0;
        rightGbc.fill = GridBagConstraints.BOTH;
        rightGbc.weightx = 0.5;
        rightGbc.weighty = 1.0;

        // Actually adding it in
        this.add(gameDetailsPanle, rightGbc);
        isShowingDetails = true;

        // Making sure that everything actually updates 
        this.revalidate();
        this.repaint();
    }
    
    /**
     * Hides the gameDetails panle so that they can go back to scrolling through the search
     */
    public void hideGameDetails() {

        // We can't do anything if it isn't being shown
        if (!isShowingDetails)
            return;

        // Getting rid of details
        this.remove(gameDetailsPanle);

        // Making the search results take up the whole space again
        GridBagLayout layout = (GridBagLayout) this.getLayout();
        GridBagConstraints leftGbc = layout.getConstraints(resultsPanle);
        leftGbc.weightx = 1.0;
        layout.setConstraints(resultsPanle, leftGbc);

        isShowingDetails = false;

        // Recalculate and redraw
        this.revalidate();
        this.repaint();
    }
    
    @Override
    public void updateTheme() {
        super.updateTheme();
        gameDetailsPanle.updateTheme();
        resultsPanle.updateTheme();
    }




    private GameDetailsSubPanle gameDetailsPanle;
    // TODO make private
    public GameListSubPanle resultsPanle;
    private boolean isShowingDetails;


}
